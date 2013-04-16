package org.easit.core.plugins.impl.all;

import java.util.Properties;

import org.easit.core.plugins.Plugin;
import org.easit.dao.model.EasitAccount;
import org.springframework.web.servlet.ModelAndView;

public class CosmSensorsPlugin implements Plugin {

    private String name;

    private boolean view;

    private boolean loaded;

    private String feed;

    public void register(String name, Properties props) {
	this.name = name;
	this.view = Boolean.parseBoolean(props.getProperty("view").toString());
	this.feed = props.getProperty("feed").toString();
	this.loaded = false;
    }

    public String getFeed() {
	return feed;
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

    public void uploadConfiguration(Properties props) {
	register(this.name, props);
    }

    public Object execute(EasitAccount res, Object... args) {
	ModelAndView modelAndView = (ModelAndView) args[0];
	return execute(res, modelAndView);
    }

    private Object execute(EasitAccount res, ModelAndView modelAndView) {
	modelAndView.addObject("feedId", feed);
	return modelAndView;
    }

    @Override
    public Properties getProperties() {
	// TODO Auto-generated method stub
	return null;
    }

}
