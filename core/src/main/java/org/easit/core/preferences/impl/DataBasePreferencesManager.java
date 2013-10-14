package org.easit.core.preferences.impl;

import javax.inject.Inject;

import org.easit.core.preferences.PreferencesDataManager;
import org.easit.dao.EasitApplicationPreferencesDao;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.EasitApplicationPreferences;

/**
 * DataBase Preferences Manager Implementation
 *
 */
public class DataBasePreferencesManager implements PreferencesDataManager {

    @Inject
    private EasitApplicationPreferencesDao preferencesRepository;

    /**
     * Insert (in case no preferences created) or Update existing preferences
     */
    @Override
    public void insertOrUpdatePreferences(EasitApplicationPreferences preferences, EasitAccount user, String clientIpAddr) {
	preferencesRepository.updatePreferences(preferences, user.getId());
    }

    @Override
    public EasitApplicationPreferences loadPreferences(EasitAccount user, String clientIpAddr) throws Exception {
	return preferencesRepository.getPreferencesByUserId(user.getId());
    }
}
