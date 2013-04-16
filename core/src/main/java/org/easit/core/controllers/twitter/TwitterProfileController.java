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
package org.easit.core.controllers.twitter;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TwitterProfileController {

    @Inject
    private ConnectController connectController;

    @Inject
    private ConnectionRepository connectionRepository;

    @RequestMapping(value = "/twitter", method = RequestMethod.GET)
    public String home(Principal currentUser, Model model) {
	Connection<Twitter> connection = connectionRepository.findPrimaryConnection(Twitter.class);
	if (connection == null) {
	    return "redirect:/connect/twitter";
	}

	return "redirect:twitter/timeline";
    }

    @RequestMapping(value = "/disconnect/twitter", method = RequestMethod.DELETE)
    public RedirectView removeConnection(NativeWebRequest request) {
	RedirectView redirect = connectController.removeConnection("twitter", connectionRepository.findPrimaryConnection(Twitter.class).getKey().getProviderUserId(), request);

	request.setAttribute("connectedToTwitter", connectionRepository.findConnections("twitter").size() > 0, WebRequest.SCOPE_SESSION);
	request.setAttribute("connectedToAny", connectionRepository.findAllConnections().size() > 0, WebRequest.SCOPE_SESSION);

	return redirect;
    }

}