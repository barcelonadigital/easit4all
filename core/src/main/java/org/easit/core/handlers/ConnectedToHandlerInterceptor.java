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
package org.easit.core.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easit.core.plugins.Plugin;
import org.easit.core.plugins.PluginManager;
import org.easit.dao.LogOperationsDao;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.PSMetadata;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ConnectedToHandlerInterceptor extends HandlerInterceptorAdapter {

    private LogOperationsDao operationsRepository;
    private PluginManager pluginManager;

    public ConnectedToHandlerInterceptor(LogOperationsDao operationsRepository, PluginManager pluginManager) {
	this.operationsRepository = operationsRepository;
	this.pluginManager = pluginManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

	if (request.getUserPrincipal() != null) {
	    String requestURI = request.getRequestURI();
	    String webNameUri = request.getSession().getAttribute("webNameUri").toString();
	    String shortURI = requestURI.substring(webNameUri.length());

	    // Sets uri for every request
	    request.setAttribute("uri", shortURI);

	    // Load user account settings from session attributes
	    EasitAccount acc = (EasitAccount) request.getSession().getAttribute("user");

	    // Plugin: log operations (Leave here) + current section in horizontal menu
	    if (!requestURI.equals(webNameUri) && !requestURI.equals(webNameUri + "gmail")) {

		ArrayList<String> currentOperation = currentOperation(shortURI);
		String providerId = currentOperation.get(0);
		String operation = currentOperation.get(1);
		String currentSection;

		if (operation.equals(""))
		    currentSection = webNameUri + providerId;
		else
		    currentSection = webNameUri + providerId + "/" + operation;

		if (!requestURI.contains(webNameUri + PSMetadata.USER_SETTINGS_PAGE) && !requestURI.contains(webNameUri + PSMetadata.CONNECT_PAGE)
			&& !requestURI.contains(webNameUri + PSMetadata.DISCONNECT_PAGE) && !requestURI.contains(webNameUri + PSMetadata.ABOUT_PAGE)
			&& !requestURI.contains(webNameUri + PSMetadata.CONTACT_PAGE) && !requestURI.contains(PSMetadata.SEARCH) && !requestURI.contains(PSMetadata.NEWS_PLUGIN)) {
		    if (pluginManager.isLoaded("logOperations")) {
			for (Plugin plugin : pluginManager.listPlugins()) {
			    if (plugin.getName().equalsIgnoreCase("logOperations")) {
				plugin.execute(acc, providerId, operation, operationsRepository);
				break;
			    }
			}
		    }
		}

		request.setAttribute("currentSection", currentSection);
	    }

	    // Plugin: favourites(Leave here)
	    if (pluginManager.isLoaded("favourites")) {
		List<String> disconnectedProvs = new ArrayList<String>();
		if (!(Boolean.parseBoolean(request.getSession().getAttribute("connectedToTwitter").toString())))
		    disconnectedProvs.add(PSMetadata.PROVIDER_TWITTER);
		if (!(Boolean.parseBoolean(request.getSession().getAttribute("connectedToFacebook").toString())))
		    disconnectedProvs.add(PSMetadata.PROVIDER_FACEBOOK);

		request.setAttribute("favourites", pluginManager.getPlugin("favourites").execute(acc, disconnectedProvs, operationsRepository));
		request.setAttribute("favouritesLoaded", true);
	    } else
		request.setAttribute("favouritesLoaded", false);
	}
	return true;
    }

    private ArrayList<String> currentOperation(String so) {
	ArrayList<String> currentOption = new ArrayList<String>();
	// String so = requestURI.substring(WEB_NAME.length()); 
	int firstIndex = so.indexOf("/");
	if (firstIndex == -1) { // if no operation (user profile)
	    currentOption.add(so); // providerId
	    currentOption.add(""); // operation
	} else {
	    currentOption.add(so.substring(0, firstIndex)); // providerId
	    if (so.substring(firstIndex + 1).indexOf("/") > 0) { // if action exists
		currentOption.add(so.substring(firstIndex + 1).substring(0, so.substring(firstIndex + 1).indexOf("/")));
	    } else {
		currentOption.add(so.substring(so.indexOf("/", firstIndex) + 1)); // onlyoperation
	    }
	    // operation = so.substring(so.indexOf("/", firstIndex)+1);
	}
	return currentOption;
    }

}
