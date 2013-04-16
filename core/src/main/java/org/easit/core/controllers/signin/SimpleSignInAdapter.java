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
package org.easit.core.controllers.signin;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.easit.core.handlers.AfterLoginSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

public class SimpleSignInAdapter implements SignInAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSignInAdapter.class);

    private final RequestCache requestCache;

    private AfterLoginSuccessHandler loginSuccess;

    @Inject
    public SimpleSignInAdapter(RequestCache requestCache, AfterLoginSuccessHandler loginSuccess) {
	this.requestCache = requestCache;
	this.loginSuccess = loginSuccess;
    }

    public String signIn(String localUserId, NativeWebRequest request) {
	HttpServletRequest req = request.getNativeRequest(HttpServletRequest.class);
	Authentication auth = SignInUtils.signin(localUserId);
	String originalUrl = extractOriginalUrl(request);

	try {
	    loginSuccess.setAttributesInSession(req, auth);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return originalUrl;
    }

    private String extractOriginalUrl(NativeWebRequest request) {
	HttpServletRequest nativeReq = request.getNativeRequest(HttpServletRequest.class);
	HttpServletResponse nativeRes = request.getNativeResponse(HttpServletResponse.class);
	SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
	if (saved == null) {
	    return null;
	}
	requestCache.removeRequest(nativeReq, nativeRes);
	removeAutheticationAttributes(nativeReq.getSession(false));
	return saved.getRedirectUrl();
    }

    private void removeAutheticationAttributes(HttpSession session) {
	if (session == null) {
	    return;
	}
	session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
	return null;
    }

}
