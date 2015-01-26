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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.easit.core.CoreUtils;
import org.easit.core.handlers.AfterLoginSuccessHandler;
import org.easit.core.preferences.impl.ServerPreferencesManager;
import org.easit.dao.EasitAccountDao;
import org.easit.dao.model.EasitAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;



@Controller
public class SigninController {
	
	private static final Logger logger = LoggerFactory.getLogger(SigninController.class);
	
	private final EasitAccountDao accountRepository;
	private AfterLoginSuccessHandler loginSuccess;

	@Inject
	private Environment environment;

	@Inject
	public SigninController(EasitAccountDao accountRepository, AfterLoginSuccessHandler loginSuccess) {
		this.accountRepository = accountRepository;
		this.loginSuccess = loginSuccess;
	}

	//Deprecated
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView signin(String error) {
		ModelAndView modelAndView = new ModelAndView();

		if (error != null && !error.isEmpty())
			modelAndView.addObject("param.error", error);

		return modelAndView;
	}

	//Redirect the user to the GPII authorization (STEP 1) 
	@RequestMapping(value = "/oauth_signin", method = RequestMethod.GET)
	public String user_authorization(WebRequest request) {
		
		// Redirect the user to request preferences access
		String redirectUrl = environment.getProperty("flowManager.url") + environment.getProperty("flowManager.authorization");
		
		//Add the parameters
		redirectUrl += "?response_type=code";
		redirectUrl += "&client_id=" + environment.getProperty("flowManager.client_id");
		redirectUrl += "&redirect_uri=" + environment.getProperty("project.url") + "/oauth_signin/authorize_callback";
		redirectUrl += "&scope=scope_1";
		redirectUrl += "&state=RANDOM-STRING";
		
		logger.info("REDIRECT URL: " + redirectUrl); 
		return "redirect:" + redirectUrl;
	}


	//Receive the authorization callback
	@RequestMapping(value = "/oauth_signin/authorize_callback", method = RequestMethod.GET)
	public String authorization_callback_post(WebRequest request, HttpServletRequest requestHttp, HttpServletResponse responseHttp) throws Exception{		

		//Exchange the authorization code for an access token
		String access_token = exchange_code_for_access_token(request);

		//Retrieve the user gpiiToken
		String gpiiToken = getGpiiToken(access_token);

		//Login the user
		EasitAccount account = accountRepository.findAccountByUserToken(gpiiToken);
		account.setAccessToken(access_token);
		accountRepository.update(account);
		
		if (account != null) {
			Authentication auth = SignInUtils.signin(account.getUsername());
			ProviderSignInUtils.handlePostSignUp(account.getUsername(), request);        
			loginSuccess.onAuthenticationSuccess(requestHttp, responseHttp, auth);
		}
		return "home";
	}

	/**
	 * Internal helpers
	 * Exchange the authorization code for an access token
	 */
	private String exchange_code_for_access_token( WebRequest request ) throws Exception{
		// Exchange the authorization code for an access token
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(environment.getProperty("flowManager.url") + environment.getProperty("flowManager.exchange_accesstoken"));

		// add header 
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
		urlParameters.add(new BasicNameValuePair("code", request.getParameter("code")));
		urlParameters.add(new BasicNameValuePair("redirect_uri", environment.getProperty("project.url") + "/oauth_signin/authorize_callback"));
		urlParameters.add(new BasicNameValuePair("client_id", environment.getProperty("flowManager.client_id")));
		urlParameters.add(new BasicNameValuePair("client_secret", environment.getProperty("flowManager.client_secret")));
		post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));

		//Exchange the authorization code for an access token
		HttpResponse response = client.execute(post);
		
		//NOT Correct answer
		if (response.getStatusLine().getStatusCode() != 200) {

			logger.info("ERROR:");
			logger.info("URL target: " + environment.getProperty("flowManager.url") + environment.getProperty("flowManager.exchange_accesstoken"));
			
			logger.info(post.toString());
			for (Header header : post.getAllHeaders()) {
				logger.info(header.getName() + " : " + header.getValue());				
			}
			
			String content = EntityUtils.toString(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			logger.info("");
			logger.info(content);
			logger.info("");
			
			logger.info("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			throw new Exception("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
		
		BufferedReader rd = new BufferedReader(  new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		logger.info("ACCESS:" + result);

		//Extract the access token
		JSONObject json = new JSONObject(result.toString());
		Map<String,String> output = new HashMap<String, String>();
		CoreUtils.parse( json, output);
		return output.get("access_token");
	}

	/**
	 * Internal helpers
	 * Return with the 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String getGpiiToken( String accessToken ) throws Exception{

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet( environment.getProperty("flowManager.url") + environment.getProperty("flowManager.gpii_token"));

		// add the access token to the request header
		request.addHeader("Authorization", "Bearer " + accessToken);
		HttpResponse response = client.execute(request);

		//NOT Correct answer
		if (response.getStatusLine().getStatusCode() != 200) {
			logger.info("ERROR:");
			logger.info("URL target" + environment.getProperty("flowManager.url") + environment.getProperty("flowManager.gpii_token"));
			
			logger.info(request.toString());
			for (Header header : request.getAllHeaders()) {
				logger.info(header.getName() + " : " + header.getValue());				
			}
						
			throw new Exception("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

		//Correct answer 
		BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		logger.info("GPII:" + result);
				
		//Extract the Gpii token from the response
		JSONObject json = new JSONObject(result.toString());
		Map<String,String> output = new HashMap<String, String>();
		CoreUtils.parse( json, output);
		return output.get("gpii_token");
	}

}