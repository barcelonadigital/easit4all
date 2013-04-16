package org.easit.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Load specific properties to configure the application
 * 
 */
public class WebApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static Log logger = LogFactory.getLog(WebApplicationContextInitializer.class);

    public void initialize(ConfigurableApplicationContext applicationContext) {
	try {
	    final Properties applicationProperties = System.getProperties();
	    String propFile = applicationContext.getEnvironment().getProperty("propFile");
	    InputStream fis = applicationContext.getResource(propFile).getInputStream();

	    applicationProperties.load(fis);
	    fis.close();
	    System.setProperty("config", propFile);
	} catch (IOException e) {
	    logger.debug("Error while initialising web application context");
	    logger.error(e.getMessage());
	}

    }

}
