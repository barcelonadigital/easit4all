package org.easit.dao.jpa;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.easit.dao.LogOperationsDao;
import org.easit.dao.model.LogOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LogOperationsDaoJpa extends GenericDaoJpa<LogOperations> implements LogOperationsDao {

    private static final Logger logger = LoggerFactory.getLogger(LogOperationsDaoJpa.class);

    @SuppressWarnings("unchecked")
    public List<LogOperations> findTop5OperationsByUserId(long userId, List<String> disProvs, int maxResults) {
	StringBuffer queryString = new StringBuffer();
	Query querySelect = null;
	try {
	    queryString.append("select lo from LogOperations lo where lo.userId = :userId ");
	    if (!disProvs.isEmpty()) {
		queryString.append("and lo.providerId not in (");
		for (String prov : disProvs) {
		    queryString.append("'" + prov + "'");
		    if (!disProvs.get(disProvs.size() - 1).equals(prov)) {
			queryString.append(", ");
		    }
		}
		queryString.append(") ");
	    }
	    queryString.append("group by lo.operation, lo.providerId order by count(lo) desc");
	    querySelect = entityManager.createQuery(queryString.toString());
	    querySelect.setParameter("userId", userId);
	    if (maxResults > 0)
		querySelect.setMaxResults(maxResults);

	    return querySelect.getResultList();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    public List<LogOperations> findTop2OperationsByProviderId(long userId, String providerId, int maxResults) {
	try {
	    String queryString = "select lo from LogOperations lo where lo.userId = :userId " + "and lo.providerId = :providerId group by lo.operation order by count(lo) desc";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("userId", userId);
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

    public void save(long userId, String providerId, String operation, Timestamp time) {

	Query saveQuery;
	try {
	    saveQuery = entityManager.createNativeQuery("INSERT INTO LogOperations " + "(userId, providerId, operation, date) VALUES (?1,?2,?3,?4)");
	    saveQuery.setParameter(1, userId);
	    saveQuery.setParameter(2, providerId);
	    saveQuery.setParameter(3, operation);
	    saveQuery.setParameter(4, time);
	    saveQuery.executeUpdate();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	}

    }

    public Date getLastDate(long userId, String providerId, String operation) {
	try {
	    String queryString = "select lo.date from LogOperations lo where lo.userId = :userId " + "and lo.providerId = :providerId and lo.operation = :operation order by lo.date desc";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("userId", userId);
	    querySelect.setParameter("providerId", providerId);
	    querySelect.setParameter("operation", operation);
	    querySelect.setMaxResults(1);
	    Timestamp dt = (Timestamp) querySelect.getResultList().get(0);
	    return new java.util.Date(dt.getTime());
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	}
	return null;
    }

}
