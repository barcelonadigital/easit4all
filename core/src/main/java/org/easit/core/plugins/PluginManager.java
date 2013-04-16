package org.easit.core.plugins;

import java.util.List;
import java.util.Properties;

/**
 * Plugin Manager interface
 *
 */
public interface PluginManager {

    void registerPlugin(String pluginName, Properties pluginProps);

    void load(String pluginName);

    Plugin getPlugin(String plugin);

    boolean isLoaded(String pluginName);

    boolean unload(String pluginName);

    boolean view(String pluginName);

    List<Plugin> listPlugins();

}
