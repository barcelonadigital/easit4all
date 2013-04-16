package org.easit.core.plugins.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.easit.core.plugins.Plugin;
import org.easit.core.plugins.PluginManager;
import org.easit.core.plugins.impl.all.CosmSensorsPlugin;
import org.easit.core.plugins.impl.all.FavouritesPlugin;
import org.easit.core.plugins.impl.all.LogOperationsPlugin;
import org.easit.core.plugins.impl.all.NewsPlugin;
import org.easit.core.plugins.impl.all.TopSitesPlugin;

/**
 * Plugin Manager Implementation Class
 *
 */
public class PluginManagerImpl implements PluginManager {

    private List<Plugin> plugins = new ArrayList<Plugin>();

    @Override
    public void registerPlugin(String pluginName, Properties pluginProps) {
	Plugin plugin = null;
	switch (pluginName) {
	case "logOperations":
	    plugin = new LogOperationsPlugin();
	    break;
	case "topSites":
	    plugin = new TopSitesPlugin();

	    break;
	case "favourites":
	    plugin = new FavouritesPlugin();
	    break;
	case "cosmSensors":
	    plugin = new CosmSensorsPlugin();
	    break;
	case "news":
	    plugin = new NewsPlugin();
	    break;
	default:
	    plugin = new PluginImpl();
	    break;
	}
	plugin.register(pluginName, pluginProps);
	plugins.add(plugin);
    }

    @Override
    public Plugin getPlugin(String pluginName) {
	for (Plugin plugin : plugins) {
	    if (plugin.getName().equalsIgnoreCase(pluginName)) {
		return plugin;
	    }
	}
	return null;
    }

    @Override
    public void load(String pluginName) {
	getPlugin(pluginName).load();
    }

    @Override
    public boolean isLoaded(String pluginName) {
	Plugin plugin = getPlugin(pluginName);
	if (plugin != null)
	    return plugin.isLoaded();
	return false;
    }

    @Override
    public boolean unload(String pluginName) {
	Plugin plugin = getPlugin(pluginName);
	if (plugin != null) {
	    plugin.unload();
	    return plugin.isLoaded();
	}
	return false;
    }

    @Override
    public boolean view(String pluginName) {
	Plugin plugin = getPlugin(pluginName);
	if (plugin != null) {
	    return plugin.view();
	}
	return false;
    }

    @Override
    public List<Plugin> listPlugins() {
	return plugins;
    }
}
