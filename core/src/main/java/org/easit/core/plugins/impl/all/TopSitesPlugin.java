package org.easit.core.plugins.impl.all;

import java.util.Properties;
import javax.inject.Provider;
import org.easit.core.plugins.Plugin;
import org.easit.dao.LogOperationsDao;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.PSMetadata;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.servlet.ModelAndView;

public class TopSitesPlugin implements Plugin {

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

    // connectionRepositoryProvider, operationsRepository, modelAndView
    public Object execute(EasitAccount res, Object... args) {
	Provider<ConnectionRepository> connectionRepositoryProvider = null;
	LogOperationsDao operationsRepository = null;
	ModelAndView modelAndView = null;
	/*
	 * for(Object arg : args) {
	 * if(arg.getClass().equals(LogOperationsDao.class)) {
	 * operationsRepository = (LogOperationsDao) arg; }
	 * if(arg.getClass().equals(ModelAndView.class)) { modelAndView =
	 * (ModelAndView) arg; } else connectionRepositoryProvider =
	 * (Provider<ConnectionRepository>) arg; }
	 */
	connectionRepositoryProvider = (Provider<ConnectionRepository>) args[0];
	operationsRepository = (LogOperationsDao) args[1];
	modelAndView = (ModelAndView) args[2];
	return execute(res, connectionRepositoryProvider, operationsRepository, modelAndView);
    }

    private Object execute(EasitAccount res, Provider<ConnectionRepository> connectionRepositoryProvider, LogOperationsDao operationsRepository, ModelAndView modelAndView) {
	if (getConnectionRepository(connectionRepositoryProvider).findPrimaryConnection(Facebook.class) != null)
	    modelAndView.addObject("topFacebook", operationsRepository.findTop2OperationsByProviderId(res.getId(), PSMetadata.PROVIDER_FACEBOOK, this.maxResults));

	if (getConnectionRepository(connectionRepositoryProvider).findPrimaryConnection(Twitter.class) != null)
	    modelAndView.addObject("topTwitter", operationsRepository.findTop2OperationsByProviderId(res.getId(), PSMetadata.PROVIDER_TWITTER, this.maxResults));

	return modelAndView;
    }

    private ConnectionRepository getConnectionRepository(Provider<ConnectionRepository> connectionRepositoryProvider) {
	return connectionRepositoryProvider.get();
    }

    public Properties getProperties() {
	// TODO Auto-generated method stub
	return null;
    }

}
