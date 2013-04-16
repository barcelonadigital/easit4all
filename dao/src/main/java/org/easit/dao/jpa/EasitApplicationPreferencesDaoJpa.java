package org.easit.dao.jpa;

import javax.persistence.Query;

import org.easit.dao.EasitApplicationPreferencesDao;
import org.easit.dao.model.EasitApplicationPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EasitApplicationPreferencesDaoJpa extends GenericDaoJpa<EasitApplicationPreferences> implements EasitApplicationPreferencesDao {

    private static final Logger logger = LoggerFactory.getLogger(EasitApplicationPreferencesDaoJpa.class);

    @Override
    public void updatePreferences(EasitApplicationPreferences prefs, long userId) {
	Query querySelect;
	try {
	    querySelect = entityManager.createNativeQuery("INSERT INTO ApplicationPreferences "
		    + "(userId, textFont, theme, textSize, lineSpacing, layout, toc, links, inputsLarger, invertImages, tracking, magnification) " + "VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)"
		    + " ON DUPLICATE KEY UPDATE textFont = ?2, theme = ?3, textSize = ?4, lineSpacing = ?5, layout = ?6, "
		    + "toc = ?7, links = ?8, inputsLarger = ?9, invertImages = ?10, tracking = ?11, magnification = ?12");
	    querySelect.setParameter(1, userId);
	    querySelect.setParameter(2, prefs.getTextFont());
	    querySelect.setParameter(3, prefs.getTheme());
	    querySelect.setParameter(4, prefs.getTextSize());
	    querySelect.setParameter(5, prefs.getLineSpacing());
	    querySelect.setParameter(6, prefs.isLayout());
	    querySelect.setParameter(7, prefs.isToc());
	    querySelect.setParameter(8, prefs.isLinks());
	    querySelect.setParameter(9, prefs.isInputsLarger());
	    querySelect.setParameter(10, prefs.isInvertImages());
	    querySelect.setParameter(11, prefs.getTracking());
	    querySelect.setParameter(12, prefs.getMagnification());

	    querySelect.executeUpdate();

	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	}

    }

    @Override
    public EasitApplicationPreferences getPreferencesByUserId(long userId) {
	try {
	    String queryString = "SELECT af FROM ApplicationPreferences af WHERE af.userId = :userId";
	    Query querySelect = entityManager.createQuery(queryString);
	    querySelect.setParameter("userId", userId);
	    return (EasitApplicationPreferences) querySelect.getSingleResult();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	    return null;
	}
    }

}
