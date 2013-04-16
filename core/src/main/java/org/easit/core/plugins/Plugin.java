package org.easit.core.plugins;

import java.util.Properties;

import org.easit.dao.model.EasitAccount;

/**
 * Plugin interface
 *
 */
public interface Plugin {
    
    String getName();

    boolean isLoaded();

    boolean view();

    void load();

    void unload();

    void register(String name, Properties props);// ?

    Properties getProperties();

    Object execute(EasitAccount res, Object... args);
}
