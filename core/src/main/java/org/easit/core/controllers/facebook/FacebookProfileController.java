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
package org.easit.core.controllers.facebook;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class FacebookProfileController {

    private final Facebook facebook;

    @Inject
    private ConnectionRepository connectionRepository;

    @Inject
    private ConnectController connectController;

    @Inject
    public FacebookProfileController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String home(Model model, Principal user) {
	Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
	if (connection == null) {
	    return "redirect:/connect/facebook";
	}
	if (connection.hasExpired() || !connection.test()) {
	    connectionRepository.removeConnection(connection.getKey());
	    return "redirect:/connect/facebook";
	}
	model.addAttribute("profile", connection.getApi().userOperations().getUserProfile());
	return "redirect:/facebook/feed";
    }

    @RequestMapping(value = "/facebook/profile", method = RequestMethod.GET)
    public String profile(Model model) {
	model.addAttribute("profile", facebook.userOperations().getUserProfile());
	return "facebook/profile";
    }

    @RequestMapping(value = "/disconnect/facebook", method = RequestMethod.DELETE)
    public RedirectView removeConnection(NativeWebRequest request) {
	RedirectView redirect = connectController.removeConnection("facebook", connectionRepository.findPrimaryConnection(Facebook.class).getKey().getProviderUserId(), request);

	request.setAttribute("connectedToFacebook", connectionRepository.findConnections("facebook").size() > 0, WebRequest.SCOPE_SESSION);
	request.setAttribute("connectedToAny", connectionRepository.findAllConnections().size() > 0, WebRequest.SCOPE_SESSION);

	return redirect;
    }

}
