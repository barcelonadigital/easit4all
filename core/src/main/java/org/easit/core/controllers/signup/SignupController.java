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
package org.easit.core.controllers.signup;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.easit.core.controllers.signin.SignInUtils;
import org.easit.core.handlers.AfterLoginSuccessHandler;
import org.easit.core.message.Message;
import org.easit.core.message.MessageType;
import org.easit.dao.EasitAccountDao;
import org.easit.dao.jpa.UsernameAlreadyInUseException;
import org.easit.dao.model.EasitAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
// @RequestMapping("/signup")
// @SessionAttributes("person")
public class SignupController {

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    private final EasitAccountDao accountRepository;
    private PasswordEncoder passwordEncoder;
    private AfterLoginSuccessHandler loginSuccess;

    @Inject
    public SignupController(EasitAccountDao accountRepository, PasswordEncoder pass, AfterLoginSuccessHandler loginSuccess) {
	this.accountRepository = accountRepository;
	this.passwordEncoder = pass;
	this.loginSuccess = loginSuccess;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public SignupForm signupForm(WebRequest request) {
	Connection<?> connection = ProviderSignInUtils.getConnection(request);
	if (connection != null) {
	    request.setAttribute("message", new Message(MessageType.INFO, "Your " + StringUtils.capitalize(connection.getKey().getProviderId())
		    + " account is not associated with a Spring Social Showcase account. If you're new, please sign up."), WebRequest.SCOPE_REQUEST);
	    return SignupForm.fromProviderUser(connection.fetchUserProfile());
	} else {
	    return new SignupForm();
	}
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request, HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
	try {	    
        	if (formBinding.hasErrors())
        	    return "signup";
        	
        	EasitAccount account;
        	account = createAccount(form, formBinding);
        
        	if (account != null) {
        	    Authentication auth = SignInUtils.signin(account.getUsername());
        	    ProviderSignInUtils.handlePostSignUp(account.getUsername(), request);        
        	    loginSuccess.onAuthenticationSuccess(requestHttp, responseHttp, auth);
        	}
        	return "home";
	} catch (UsernameAlreadyInUseException ex) {
	    formBinding.rejectValue("username", "user.duplicateUsername", "already in use");
	    new UsernameAlreadyInUseException(form.getUsername());
	    return "signup";
	}
	catch (Exception ex1) {
	    logger.error(ex1.getMessage());
	    return "signup";
	}	
    }

    // internal helpers
    private EasitAccount createAccount(SignupForm form, BindingResult formBinding) throws UsernameAlreadyInUseException {
	EasitAccount account = new EasitAccount(form.getUsername(), form.getPassword(), form.getFirstName(), form.getLastName(), form.getEmail(), "");	
	accountRepository.createAccount(account, passwordEncoder);
	
	// applicationFeatures.createPreferences(form.getUsername());	
	return account;

    }

}
