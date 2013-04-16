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

import javax.inject.Inject;

import org.springframework.social.ApiException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class FacebookAfterConnectInterceptor implements ConnectInterceptor<Facebook> {

    @Inject
    private ConnectionRepository connectionRepository;

    @Override
    public void preConnect(ConnectionFactory<Facebook> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
	parameters.set("approval_prompt", "force");
	if (StringUtils.hasText(request.getParameter(POST_TO_WALL_PARAMETER))) {
	    request.setAttribute(POST_TO_WALL_ATTRIBUTE, Boolean.TRUE, WebRequest.SCOPE_SESSION);
	}
    }

    @Override
    public void postConnect(Connection<Facebook> connection, WebRequest request) {
	request.setAttribute("connectedToFacebook", connection.test(), WebRequest.SCOPE_SESSION);
	request.setAttribute("connectedToAny", connectionRepository.findAllConnections().size() > 0, WebRequest.SCOPE_SESSION);
	if (request.getAttribute(POST_TO_WALL_ATTRIBUTE, WebRequest.SCOPE_SESSION) != null) {
	    try {
		connection.updateStatus("Connected!");
	    } catch (ApiException e) {
		// Do nothing: No need to break down if the post-connect post
		// can't be made.
	    }
	    request.removeAttribute(POST_TO_WALL_ATTRIBUTE, WebRequest.SCOPE_SESSION);
	}
    }

    private static final String POST_TO_WALL_PARAMETER = "postToWall";

    private static final String POST_TO_WALL_ATTRIBUTE = "facebookConnect." + POST_TO_WALL_PARAMETER;
}
