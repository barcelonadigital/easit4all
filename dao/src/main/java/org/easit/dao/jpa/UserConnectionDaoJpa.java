package org.easit.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.easit.dao.UserConnectionDao;
import org.easit.dao.model.UserConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class UserConnectionDaoJpa extends GenericDaoJpa<UserConnection> implements UserConnectionDao {

    private static final Logger logger = LoggerFactory.getLogger(UserConnectionDaoJpa.class);

    public List<UserConnection> getUserConnectionsByProvider(String username, String providerId) {
	return getUserConnectionsByProvider(username, providerId, 0);
    }

    public List<UserConnection> getUserConnectionsByProvider(String username, String providerId, int maxResults) {
	try {
	    String queryString = "SELECT uc FROM UserConnection uc WHERE uc.userId = :username AND uc.providerId = :providerId ORDER BY uc.rank ";
	    // String queryString = "from UserConnection";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("username", username);
	    querySelect.setParameter("providerId", providerId);
	    if (maxResults > 0)
		querySelect.setMaxResults(maxResults);

	    return querySelect.getResultList();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	}
	return null;
    }

    public UserConnection getMainConnection(String username, String providerId) {
	List<UserConnection> connections = getUserConnectionsByProvider(username, providerId);
	if (connections != null && connections.size() > 0) {
	    return connections.get(0);
	}
	return null;
    }

    public void updateRefreshToken(String name, String refreshToken, String provider) {
	try {
	    String queryString = "UPDATE UserConnection uc SET uc.refreshToken = :refreshToken WHERE uc.userId = :userId and uc.providerId = :providerId";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("refreshToken", refreshToken);
	    querySelect.setParameter("userId", name);
	    querySelect.setParameter("providerId", provider);
	    querySelect.executeUpdate();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	}
    }

}
