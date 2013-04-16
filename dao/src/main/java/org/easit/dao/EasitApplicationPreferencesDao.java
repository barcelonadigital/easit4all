package org.easit.dao;

import org.easit.dao.model.EasitApplicationPreferences;

public interface EasitApplicationPreferencesDao extends GenericDao<EasitApplicationPreferences> {

    public void updatePreferences(EasitApplicationPreferences prefs, long userId);

    public EasitApplicationPreferences getPreferencesByUserId(long userId);
}
