package org.easit.core.preferences.impl;

import javax.inject.Inject;

import org.easit.core.preferences.PreferencesDataManager;
import org.easit.dao.EasitApplicationPreferencesDao;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.EasitApplicationPreferences;
import org.springframework.core.env.Environment;

/**
 * DataBase Preferences Manager Implementation
 *
 */
public class DataBasePreferencesManager implements PreferencesDataManager {

    @Inject
    private EasitApplicationPreferencesDao preferencesRepository;

    /**
     * Create the default prefs
     */
    public EasitApplicationPreferences createDefaultPreferences( Environment environment, EasitAccount user ){
		//Create the default preferences
		EasitApplicationPreferences defaultPref = new EasitApplicationPreferences(  environment.getProperty("textFont"), environment.getProperty("theme"), Integer.parseInt(environment.getProperty("textSize")),
				Integer.parseInt(environment.getProperty("lineSpacing")), Boolean.parseBoolean(environment.getProperty("layout")), Boolean.parseBoolean(environment.getProperty("toc")),
				Boolean.parseBoolean(environment.getProperty("links")), Boolean.parseBoolean(environment.getProperty("inputsLarger")), Integer.parseInt(environment.getProperty("magnification")),
				Boolean.parseBoolean(environment.getProperty("invertImages")), environment.getProperty("tracking"));
		
		//Insert it the database
		insertOrUpdatePreferences( defaultPref,  user);
		
		return defaultPref; 
    }
    
    /**
     * Insert (in case no preferences created) or Update existing preferences
     */
    @Override
    public void insertOrUpdatePreferences(EasitApplicationPreferences preferences, EasitAccount user) {
	preferencesRepository.updatePreferences(preferences, user.getId());
    }

    @Override
    public EasitApplicationPreferences loadPreferences(EasitAccount user) throws Exception {
	return preferencesRepository.getPreferencesByUserId(user.getId());
    }
}
