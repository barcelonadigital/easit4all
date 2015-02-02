package org.easit.core.preferences;

import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.EasitApplicationPreferences;
import org.springframework.core.env.Environment;

/**
 * Preferences Manager Interface
 */
public interface PreferencesDataManager {

	EasitApplicationPreferences createDefaultPreferences( Environment environment, EasitAccount user ) throws Exception;
    
    void insertOrUpdatePreferences(EasitApplicationPreferences preferences, EasitAccount user) throws Exception;

    EasitApplicationPreferences loadPreferences(EasitAccount user) throws Exception;
    

}
