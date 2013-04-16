package org.easit.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.easit.dao.EasitAccountDao;
import org.easit.dao.model.EasitAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class EasitAccountDaoJpa extends GenericDaoJpa<EasitAccount> implements EasitAccountDao {

    private static final Logger logger = LoggerFactory.getLogger(EasitAccountDaoJpa.class);

    public void createAccount(EasitAccount user, PasswordEncoder passwordEncoder) throws UsernameAlreadyInUseException {
	try {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    save(user);

	} catch (Exception e) {
	    throw new UsernameAlreadyInUseException(user.getUsername());
	}
    }

    public List<EasitAccount> getAccounts() {
	List<EasitAccount> results = entityManager.createQuery("from EasitAccount").getResultList();

	if (results != null && results.size() > 0)
	    return results;
	else
	    return null;
    }

    public EasitAccount findAccountByUsername(String username) {
	try {
	    String queryString = "SELECT ac FROM EasitAccount ac WHERE ac.username = :username";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("username", username);

	    return (EasitAccount) querySelect.getSingleResult();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return null;
    }

    public int changeAccountSettings(EasitAccount user, PasswordEncoder passwordEncoder) {
	try {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    String queryString = "UPDATE EasitAccount ac SET ac.username = :username, ac.password = :password, ac.firstName = :firstName,"
		    + "ac.lastName = :lastName, ac.email = :email, ac.userToken = :userToken WHERE ac.username = :username";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("username", user.getUsername());
	    querySelect.setParameter("password", user.getPassword());
	    querySelect.setParameter("firstName", user.getFirstName());
	    querySelect.setParameter("lastName", user.getLastName());
	    querySelect.setParameter("email", user.getEmail());
	    querySelect.setParameter("userToken", user.getUserToken());
	    return querySelect.executeUpdate();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return -1;
    }

    @Override
    public EasitAccount findAccountByUserToken(String userToken) {
	try {
	    String queryString = "SELECT ac FROM EasitAccount ac WHERE ac.userToken = :userToken";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("userToken", userToken);

	    return (EasitAccount) querySelect.getSingleResult();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return null;
    }
}
