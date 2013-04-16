package org.easit.core.plugins.impl;

import java.util.Properties;

import org.easit.core.plugins.Plugin;
import org.easit.dao.model.EasitAccount;

/**
 * Plugin Class implementation
 *
 */
public class PluginImpl implements Plugin {

    private boolean loaded;
    private String name;
    private Properties props;

    @Override
    public void load() {
	this.loaded = true;
    }

    @Override
    public void unload() {
	this.loaded = false;
    }

    @Override
    public void register(String name, Properties props) {
	this.name = name;
	this.props = props;
	this.loaded = false;

    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public boolean isLoaded() {
	return loaded;
    }

    @Override
    public Properties getProperties() {
	return props;
    }

    @Override
    public Object execute(EasitAccount res, Object... args) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean view() {
	// TODO Auto-generated method stub
	return false;
    }

}
