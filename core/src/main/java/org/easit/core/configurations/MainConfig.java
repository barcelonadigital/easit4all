/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.easit.core.configurations;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.sql.DataSource;

import org.easit.core.plugins.PluginManager;
import org.easit.core.plugins.PluginManagerFactory;
import org.easit.core.preferences.PreferencesDataManager;
import org.easit.core.preferences.PreferencesDataManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main configuration class for the application. Turns on @Component scanning,
 * loads externalized application.properties, and sets up the database.
 * 
 */
@Configuration
@EnableTransactionManagement
public class MainConfig {

    @Autowired
    Environment environment;

    @Autowired
    DataSource dataSource;

    @Bean
    public PlatformTransactionManager transactionManager() {
	return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
	return new JdbcTemplate(dataSource);
    }

    /**
     * 	Load properties from environment
     * @return
     */
    @Bean
    public PreferencesDataManager preferenceManager() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException,
	    InvocationTargetException {
	Properties props = new Properties();
	if (environment.getProperty("preferenceSource.name").equals("server")) {
	    props.put("url", environment.getProperty("preferenceServer.url").toString());
	}
	props.put("common", environment.getProperty("preferenceServer.common").toString());
	props.put("reg", environment.getProperty("preferenceServer.reg").toString());

	return PreferencesDataManagerFactory.createPreferenceManager(environment.getProperty("preferenceSource.name").toString(), props, environment);
    }

    @Bean
    @Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
    public PluginManager pluginManager() {
	return PluginManagerFactory.createPluginsManager(environment.getProperty("plugins"));
    }
}
