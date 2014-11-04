package org.easit.core.preferences;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import org.easit.core.preferences.impl.DataBasePreferencesManager;
import org.easit.core.preferences.impl.ServerPreferencesManager;

/**
 * Preferences Manager Factory
 */

public class PreferencesDataManagerFactory {

    private static HashMap<String, Class> registeredDataSources = new HashMap<String, Class>();

    static {
	registerSource("server", ServerPreferencesManager.class);
	registerSource("database", DataBasePreferencesManager.class);
    }

    public static void registerSource(String sourceId, Class prefsData) {
	registeredDataSources.put(sourceId, prefsData);
    }

    /**
     * @param source specifies the existence of a preferences server
     * @return the source to be used to fetch and update the user preferences (database or preference server)
     */
    public static PreferencesDataManager createPreferenceManager(String sourceId, Properties props) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
	    IllegalArgumentException, InvocationTargetException {
	if (sourceId == null || sourceId.isEmpty() || registeredDataSources.get(sourceId) == null)
	    return null;
	else {
	    if (registeredDataSources.get(sourceId).equals(ServerPreferencesManager.class)) {
		Constructor<ServerPreferencesManager> constructor =
		ServerPreferencesManager.class.getConstructor(String.class, String.class, String.class);
		return (ServerPreferencesManager) constructor.newInstance(props.getProperty("url").toString(), props.getProperty("common").toString(), props.getProperty("reg").toString());
	    } else {
		if (registeredDataSources.get(sourceId).equals(DataBasePreferencesManager.class))
		    return DataBasePreferencesManager.class.newInstance();
		else
		    return null;
	    }
	}
    }
}
