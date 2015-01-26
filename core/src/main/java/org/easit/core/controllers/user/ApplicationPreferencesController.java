package org.easit.core.controllers.user;

import java.net.URLDecoder;
import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.easit.core.preferences.PreferencesDataManager;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.EasitApplicationPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApplicationPreferencesController {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationPreferencesController.class);

	@Inject
	private PreferencesDataManager preferencesData;
	
	@Inject
	private Environment environment; 

	@RequestMapping(value = "/user/applicationPreferences", method = RequestMethod.GET)
	public String showPreferencesGet(Principal currentUser, Model model) {
		return "applicationPreferences";
	}

	@RequestMapping(value = "/user/applicationPreferences", method = RequestMethod.POST)
	public String showPreferencesPost(Principal currentUser, HttpServletRequest request, String content) {

		String con = null;
		EasitApplicationPreferences prefs = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			// fetch cookie values
			for (Cookie co : request.getCookies()) {
				if (co.getName().equals("fluid-ui-settings")) {
					con = co.getValue();
					break;
				}
			}

			// Convert JSON to Application preferences
			prefs = mapper.readValue(URLDecoder.decode(con, "UTF-8"), EasitApplicationPreferences.class);

			// Update preferences (in server or in database)
			// userId for database preferences, userName for server preferences
			preferencesData.insertOrUpdatePreferences(prefs, (EasitAccount) request.getSession().getAttribute("user"));

			// Store preferences into session
			request.getSession().setAttribute("preferences", prefs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			// e.printStackTrace();
		}
		
		return "applicationPreferences";
	}

}
