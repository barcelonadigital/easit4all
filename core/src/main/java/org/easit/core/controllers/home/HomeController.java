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
package org.easit.core.controllers.home;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easit.core.plugins.PluginManager;
import org.easit.dao.EasitAccountDao;
import org.easit.dao.LogOperationsDao;
import org.easit.dao.model.EasitAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final Provider<ConnectionRepository> connectionRepositoryProvider;

    @Inject
    private LogOperationsDao operationsRepository;

    @Inject
    private EasitAccountDao accountRepository;

    @Inject
    private PluginManager pluginManager;

    @Autowired
    public HomeController(Provider<ConnectionRepository> connectionRepositoryProvider) {
	this.connectionRepositoryProvider = connectionRepositoryProvider;
    }

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response, Principal currentUser, Model model) {
	EasitAccount res = accountRepository.findAccountByUsername(currentUser.getName());
	ModelAndView modelAndView = new ModelAndView("home");
	modelAndView.addObject("connectionsToProviders", getConnectionRepository().findAllConnections());
	modelAndView.addObject("newsLoaded", true);

	// News plugin
	if (pluginManager.isLoaded("news"))
	    modelAndView.addObject("newsLoaded", true);
	else
	    modelAndView.addObject("newsLoaded", false);

	// cosmSensors plugin
	if (pluginManager.isLoaded("cosmSensors")) {
	    modelAndView.addObject("cosmSensorsLoaded", true);
	    pluginManager.getPlugin("cosmSensors").execute(res, modelAndView);
	} else
	    modelAndView.addObject("cosmSensorsLoaded", false);

	modelAndView.addObject("home", true);
	return modelAndView;
    }

    private ConnectionRepository getConnectionRepository() {
	return connectionRepositoryProvider.get();
    }

    @RequestMapping("/newsPlugin")
    public @ResponseBody
    Map<String, Object> getNews(@RequestParam String provider, HttpServletResponse response, EasitAccount acc, Model model, Principal currentUser) {
	EasitAccount res = accountRepository.findAccountByUsername(currentUser.getName());
	Map<String, Object> map = new HashMap<String, Object>();
	// response.setContentType("text/event-stream");
	if (pluginManager.isLoaded("news")) {
	    map.put("newsLoaded", true);
	    pluginManager.getPlugin("news").execute(res, connectionRepositoryProvider, operationsRepository, map, provider);
	}
	return map;
    }
}
