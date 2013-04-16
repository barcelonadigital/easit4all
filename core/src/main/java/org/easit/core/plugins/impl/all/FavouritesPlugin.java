package org.easit.core.plugins.impl.all;

import java.util.List;
import java.util.Properties;

import org.easit.core.plugins.Plugin;
import org.easit.dao.LogOperationsDao;
import org.easit.dao.model.EasitAccount;

public class FavouritesPlugin implements Plugin {

    private String name;

    private boolean view;

    private boolean loaded;

    private int maxResults;

    public void register(String name, Properties props) {
	this.name = name;
	this.maxResults = Integer.parseInt(props.getProperty("maxResults").toString());
	this.view = Boolean.parseBoolean(props.getProperty("view").toString());
	this.loaded = false;

    }

    public boolean view() {
	return view;
    }

    public String getName() {
	return name;
    }

    public boolean isLoaded() {
	return loaded;
    }

    public void load() {
	this.loaded = true;

    }

    public void unload() {
	this.loaded = false;
    }

    // test!!!
    public void uploadConfiguration(Properties props) {
	register(this.name, props);
    }

    public int getMaxResults() {
	return maxResults;
    }

    public Object execute(EasitAccount res, Object... args) {
	List<String> disconnectedProviders = (List<String>) args[0];
	LogOperationsDao operationsRepository = (LogOperationsDao) args[1];
	return execute(res, operationsRepository, disconnectedProviders);
    }

    private Object execute(EasitAccount acc, LogOperationsDao operationsRepository, List<String> disProvs) {
	return operationsRepository.findTop5OperationsByUserId(acc.getId(), disProvs, this.maxResults);
    }

    @Override
    public Properties getProperties() {
	// TODO Auto-generated method stub
	return null;
    }

}
