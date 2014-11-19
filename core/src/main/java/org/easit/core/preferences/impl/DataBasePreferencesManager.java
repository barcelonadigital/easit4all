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
    public void createDefaultPreferences( Environment environment, EasitAccount user ){
    	//Create the default prefs
    	int i = 1;
    	i++;
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
