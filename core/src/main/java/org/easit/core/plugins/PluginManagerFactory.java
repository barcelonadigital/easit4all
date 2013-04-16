package org.easit.core.plugins;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.easit.core.plugins.impl.PluginManagerImpl;

/**
 * Plugin Manager Factory Class
 *
 */
public class PluginManagerFactory {

    public static PluginManager createPluginsManager(String pluginNames) {
	PluginManager manager = new PluginManagerImpl();
	String[] names = parsePluginNames(pluginNames);
	HashMap<String, Properties> propsPlugin = parsePluginProps(names);

	for (String n : names) {
	    manager.registerPlugin(n, propsPlugin.get(n));
	}

	return manager;
    }

    private static HashMap<String, Properties> parsePluginProps(String[] names) {
	Properties props = System.getProperties();
	String delimiter = ".";
	HashMap<String, Properties> propsPlugin = new HashMap<String, Properties>();

	for (String name : names) {
	    String keyComparator = "plugin." + name + "."; // properties after "."
	    Properties prop = new Properties();
	    Enumeration eProps = props.propertyNames();
	    while (eProps.hasMoreElements()) {
		String key = (String) eProps.nextElement();
		if (key.contains(keyComparator)) {
		    String value = props.getProperty(key);
		    prop.put(key.substring(key.lastIndexOf(delimiter) + 1), value);
		}
	    }
	    propsPlugin.put(name, prop);
	}
	return propsPlugin;
    }

    private static String[] parsePluginNames(String pluginNames) {
	return pluginNames.split(",");
    }

}
