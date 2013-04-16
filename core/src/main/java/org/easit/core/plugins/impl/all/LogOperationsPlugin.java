package org.easit.core.plugins.impl.all;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.easit.core.plugins.Plugin;
import org.easit.dao.LogOperationsDao;
import org.easit.dao.model.EasitAccount;

public class LogOperationsPlugin implements Plugin {

    private String name;

    private boolean view;

    private boolean loaded;

    public void register(String name, Properties props) {
	this.name = name;
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

    public Object execute(EasitAccount res, Object... args) {
	String providerId = args[0].toString();
	String operation = args[1].toString();
	LogOperationsDao operationsRepository = (LogOperationsDao) args[2];

	return execute(res, providerId, operation, operationsRepository);
    }

    private Object execute(EasitAccount acc, String providerId, String operation, LogOperationsDao operationsRepository) {
	operationsRepository.save(acc.getId(), providerId, operation, getTimeNow());
	return true;
    }

    private Timestamp getTimeNow() {
	Calendar calendar = Calendar.getInstance();
	java.util.Date now = calendar.getTime();
	return new Timestamp(now.getTime());
    }

    @Override
    public Properties getProperties() {
	// TODO Auto-generated method stub
	return null;
    }

}
