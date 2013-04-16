package org.easit.dao.jpa;

import javax.persistence.Query;

import org.easit.dao.EasitProfileDao;
import org.easit.dao.model.EasitProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EasitProfileDaoJpa extends GenericDaoJpa<EasitProfile> implements EasitProfileDao {

    private static final Logger logger = LoggerFactory.getLogger(EasitProfileDaoJpa.class);

    public void updateProfile(EasitProfile profile) {
	Query querySelect;
	try {
	    querySelect = entityManager.createNativeQuery("INSERT INTO Profile " + "(userId, age, technologySkills, visualSkills, hearingSkills) VALUES (?1,?2,?3,?4,?5)"
		    + " ON DUPLICATE KEY UPDATE age = ?2, technologySkills = ?3, visualSkills = ?4, hearingSkills = ?5");
	    querySelect.setParameter(1, profile.getUserId());
	    querySelect.setParameter(2, profile.getAge());
	    querySelect.setParameter(3, profile.getTechnologySkills());
	    querySelect.setParameter(4, profile.getVisualSkills());
	    querySelect.setParameter(5, profile.getHearingSkills());
	    querySelect.executeUpdate();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	}
    }

    public EasitProfile getProfileByUserId(long userId) {
	try {
	    String queryString = "SELECT p FROM Profile p WHERE p.userId = :userId";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("userId", userId);
	    return (EasitProfile) querySelect.getSingleResult();
	} catch (Exception e) {
	    return null;
	}
    }

}
