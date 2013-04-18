package org.easit.core.handlers;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.easit.core.plugins.Plugin;
import org.easit.core.plugins.PluginManager;
import org.easit.core.preferences.PreferencesDataManager;
import org.easit.dao.EasitAccountDao;
import org.easit.dao.EasitProfileDao;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.EasitApplicationPreferences;
import org.easit.dao.model.PSMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

public class AfterLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(AfterLoginSuccessHandler.class);
    
    @Inject
    private ConnectionRepository connectionRepository;

    @Inject
    private EasitAccountDao accountRepository;

    @Inject
    private Environment environment;

    @Inject
    private PreferencesDataManager preferencesManager;

    @Inject
    private PluginManager pluginManager;

    @Inject
    private EasitProfileDao profileRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	setAttributesInSession(request, authentication);
	deletePreferencesCookie(response);
	super.onAuthenticationSuccess(request, response, authentication);
    }

    public void setAttributesInSession(HttpServletRequest request, Authentication authentication) throws IOException, ServletException {
	if (authentication != null) {

	    // Set connection attributes
	    setSocialConnectionAttributesInSession(request);

	    // Set url names attributes
	    setWebNamesAttributesInSession(request);

	    // Set user attribute
	    EasitAccount acc = accountRepository.findAccountByUsername(authentication.getName());
	    request.getSession().setAttribute("user", acc);

	    // Set profile preferences attributes in session
	    setProfileAttributesInSession(request, acc);

	    // Application Preferences
	    setPreferencesAttributesInSession(request, acc);

	    // Plugins
	    loadPlugins();
	}
    }

    /**
     * Set technology skills if exists
     * 
     * @param request
     * @param acc
     */
    private void setProfileAttributesInSession(HttpServletRequest request, EasitAccount acc) {
	try {
	    request.getSession().setAttribute("techSkills", profileRepository.getProfileByUserId(acc.getId()).getTechnologySkills());
	} catch (Exception e) {
	    request.getSession().setAttribute("techSkills", "-");
	}
    }

    private void loadPlugins() {
	// load required plugins (set as 'true' in application.properties file)
	for (Plugin plugin : pluginManager.listPlugins()) {
	    if (plugin.view()) {
		plugin.load();
	    }
	}
    }

    private void setPreferencesAttributesInSession(HttpServletRequest request, EasitAccount acc) throws JsonGenerationException, JsonMappingException, IOException {
	// Load preferences and set them as session attributes
	EasitApplicationPreferences preferences = null;
	String json = null;

	try {
	    // load preferences from Server or Database
	    preferences = preferencesManager.loadPreferences(acc);
	    logger.info("Loading user preferences from the  database/url server");

	} catch (Exception e1) {
	    // TODO Auto-generated catch block
	    logger.error("Problem loading user preferences from the  database/url server");
	}

	// load preferences from file
	if (preferences == null) {
	    System.out.println("Loading default user preferences");
	    preferences = loadPreferencesFromFile();
	}

	ObjectMapper mapper = new ObjectMapper();
	json = mapper.writeValueAsString(preferences);

	// sets application preferences in session for the default settings of the UIComponent
	request.getSession().setAttribute("settings", json);
    }

    private void setWebNamesAttributesInSession(HttpServletRequest request) {
	// Set "/web_name/"
	String web_name = environment.getProperty("project.webNameUri");
	request.getSession().setAttribute("webNameUri", web_name);

	// Set "web_name"
	String simpleWebName = environment.getProperty("project.webName");
	request.getSession().setAttribute("web_name", simpleWebName);

	// Set "localhost:8080"
	request.getSession().setAttribute("host", request.getHeader("host"));
    }

    private void setSocialConnectionAttributesInSession(HttpServletRequest request) {
	request.getSession().setAttribute("connectedToTwitter", connectionRepository.findConnections("twitter").size() > 0);
	request.getSession().setAttribute(
		"connectedToFacebook",
		connectionRepository.findConnections("facebook").size() > 0 && !connectionRepository.findPrimaryConnection(Facebook.class).hasExpired()
			&& connectionRepository.findPrimaryConnection(Facebook.class).getApi().isAuthorized());

	request.getSession().setAttribute("connectedToAny", connectionRepository.findAllConnections().size() > 0);

	// Set session variables of the different services
	request.getSession().setAttribute("FACEBOOK_LIMIT_RESULT", PSMetadata.FACEBOOK_LIMIT_RESULT);
	request.getSession().setAttribute("TWITTER_LIMIT_RESULT", PSMetadata.TWITTER_LIMIT_RESULT);
    }

    // create new ApplicationPreferences object based on file properties
    // (application.properties)
    private EasitApplicationPreferences loadPreferencesFromFile() {
	return new EasitApplicationPreferences(environment.getProperty("textFont"), environment.getProperty("theme"), Integer.parseInt(environment.getProperty("textSize")),
		Integer.parseInt(environment.getProperty("lineSpacing")), Boolean.parseBoolean(environment.getProperty("layout")), Boolean.parseBoolean(environment.getProperty("toc")),
		Boolean.parseBoolean(environment.getProperty("links")), Boolean.parseBoolean(environment.getProperty("inputsLarger")), Integer.parseInt(environment.getProperty("magnification")),
		Boolean.parseBoolean(environment.getProperty("invertImages")), environment.getProperty("tracking"));
    }

    /**
     * Delete cookie "fluid-uiComponet" even if it doesn't exists
     * 
     * @param response
     */
    private void deletePreferencesCookie(HttpServletResponse response) {
	Cookie cookie = new Cookie("fluid-ui-settings", null); // Not necessary, but saves bandwidth.
	cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
	cookie.setPath("/");
	response.addCookie(cookie);
    }
}
