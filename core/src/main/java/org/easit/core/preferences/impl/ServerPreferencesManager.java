package org.easit.core.preferences.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.easit.core.CoreUtils;
import org.easit.core.Easit4allException;
import org.easit.core.preferences.PreferencesDataManager;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.EasitApplicationPreferences;
import org.easit.dao.model.c4a.PreferencesC4A;
import org.easit.dao.model.c4a.PreferencesValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * Server Preferences Manager Implementation
 *
 */
public class ServerPreferencesManager implements PreferencesDataManager {

	private static final Logger logger = LoggerFactory.getLogger(ServerPreferencesManager.class);

	
	@Inject
	private Environment environment;
	
	// font name types
	private static final String timesFont = "TIMES NEW ROMAN";
	private static final String sansFont = "COMIC SANS";
	private static final String arialFont = "ARIAL";
	private static final String verdanaFont = "VERDANA";
	private static final String serifFace = "SERIF";
	private static final String sansserifFace = "SANS SERIF";
	private static final double defaultLineSpacing = 1.5;
	private static final boolean defaultLayout = false;
	private static final boolean defaultLinks = false;
	private static final boolean defaultInputsLarger = false;
	private static final boolean defaultToc = false;

	private String psURL;
	private String psCommon;
	private String psRegister;

	public ServerPreferencesManager(String urlPreferenceServer, String urlCommon, String urlSave) {
		psRegister = urlSave;
		psURL = urlPreferenceServer;
		psCommon = urlCommon;
	}


	/**
	 * Create the default prefs
	 */
	public void createDefaultPreferences( Environment environment, EasitAccount user ){
		try{
					
			//Create the default preferences
			EasitApplicationPreferences defaultPref = new EasitApplicationPreferences(  environment.getProperty("textFont"), environment.getProperty("theme"), Integer.parseInt(environment.getProperty("textSize")),
					Integer.parseInt(environment.getProperty("lineSpacing")), Boolean.parseBoolean(environment.getProperty("layout")), Boolean.parseBoolean(environment.getProperty("toc")),
					Boolean.parseBoolean(environment.getProperty("links")), Boolean.parseBoolean(environment.getProperty("inputsLarger")), Integer.parseInt(environment.getProperty("magnification")),
					Boolean.parseBoolean(environment.getProperty("invertImages")), environment.getProperty("tracking"));
			
			//Convert it to string
			String strPrefs = localPrefsToServerPrefs(defaultPref);
			
			//Prepare request and send it
			DefaultHttpClient httpClient = new DefaultHttpClient();
			StringEntity input = new StringEntity(strPrefs);
			input.setContentEncoding("UTF8");
			input.setContentType("application/json");
			HttpPost postRequest = new HttpPost(psRegister + user.getUserToken());
			postRequest.setEntity(input);

			//Send request
			HttpResponse response = httpClient.execute(postRequest);	
			response.getStatusLine().getStatusCode();
			

			//Create the def prefs
			
		} catch (Exception e) {
			throw new Easit4allException(e.getMessage());
		}
	}


	@Override
	public EasitApplicationPreferences loadPreferences(EasitAccount user) throws Exception {
		EasitApplicationPreferences appPreferences = null;
		try {    
			// Get preferences from server
			String strPrefs = getPreferencesFromServer(user);

			//Try to insert them
			if (!strPrefs.isEmpty() && strPrefs.toLowerCase().indexOf("error") == -1) {
				appPreferences = serverPrefsToLocalPrefs(strPrefs);
			}
		} catch (Exception e) {
			throw new Easit4allException(e.getMessage());
		}
		return appPreferences;
	}

	@Override
	public void insertOrUpdatePreferences(EasitApplicationPreferences preferences, EasitAccount user) throws Exception {
		
		/*try {
			PreferencesC4A prefsC4A = null;
			String strPrefs = "";

			// Get preferences from server
			strPrefs = getPreferencesFromServer(user);

			//Try to insert them
			if (!strPrefs.isEmpty() && strPrefs.toLowerCase().indexOf("error") == -1) {
				prefsC4A = parseToObjectPreferences(strPrefs);		
				strPrefs = mergePreferences(prefsC4A, preferences);
			} 
			else { // Insert preferences
				prefsC4A = new PreferencesC4A();
				strPrefs = mergePreferences(prefsC4A, preferences);
			}

			// Send preferences to server
			sendToServer(user, strPrefs);
		} catch (Exception e) {
			throw new Easit4allException(e.getMessage());
		}*/
	}

	/**
	 * Get the user preferences from the GPII
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private String getPreferencesFromServer(EasitAccount user) throws Exception {
		String strPrefs = "";
		String res = "";

		// Get the user preferences
		String str_url = environment.getProperty("flowManager.preferences_url") +"/" + user.getUsername();
		str_url +=	environment.getProperty("flowManager.preferences") + "/";
		str_url += "{\"OS\":{\"id\":\"web\"},\"solutions\":[{\"id\":\"com.bdigital.easit4all\"}]}";
		
		URL url = new URL(str_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		//Correct answer
		if (conn.getResponseCode() != 200) {
			throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());
		}

		// Get preferences
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		while ((res = br.readLine()) != null) {
			strPrefs += res;
		}
		logger.info("Preferences loaded: " + strPrefs);
		conn.disconnect();
		return strPrefs;
	}
	
	/**
	 * Convert the server prefs to local prefs
	 * @param serverPrefs
	 * @return
	 * @throws JSONException 
	 */
	private EasitApplicationPreferences serverPrefsToLocalPrefs(String serverPrefs) throws JSONException{
		EasitApplicationPreferences prefs = new EasitApplicationPreferences();
		
		//TODO: Handle the case if it is not exist
		//Preferences hack parsing 
		int fromIndex = serverPrefs.indexOf("{\"com.bdigital.easit4all\":");
		int toIndex = serverPrefs.length() - 1;
		serverPrefs = serverPrefs.substring(fromIndex +  "{\"com.bdigital.easit4all\":".length() , toIndex);
		
		
		//Convert the server prefs to local prefs
		JSONObject json = new JSONObject(serverPrefs.toString());
		Map<String,String> output = new HashMap<String, String>();
		CoreUtils.parse(json, output);
		
		prefs.setTextSize( Double.valueOf(output.get("fontSize"))/ 10.0);
		prefs.setTextFont(convertFontName(output.get("fontFaceFontName")));
		prefs.setTheme(convertThemeName(output.get("foregroundColor"), output.get("backgroundColor")));
		prefs.setMagnification( Integer.valueOf(output.get("magnification")));
		prefs.setTracking(output.get("tracking"));
		prefs.setInvertImages(Boolean.valueOf( output.get("invertImages")));
		prefs.setInputsLarger(Boolean.valueOf( output.get("inputsLarger")));
		prefs.setLayout( Boolean.valueOf( output.get("layout")));
		prefs.setLineSpacing( Double.valueOf( output.get("lineSpacing")));
		prefs.setLinks(Boolean.valueOf( output.get("links")));
		prefs.setToc(Boolean.valueOf(output.get("toc")));
		return prefs;		
	}
	
	
	
	/**
	 * Convert the  local prefs to server prefs  
	 * @param serverPrefs
	 * @return
	 */
	private String localPrefsToServerPrefs(EasitApplicationPreferences localPrefs){
		
		return null;		
	}
	
	
	private String convertThemeName(String foregroundColor, String backgroundColor) {
		if (foregroundColor.equalsIgnoreCase("default") || backgroundColor.equalsIgnoreCase("default"))
			return "default";
		else if (foregroundColor.equalsIgnoreCase("yellow") && backgroundColor.equalsIgnoreCase("black"))
			return "yb";
		else if (foregroundColor.equalsIgnoreCase("black") && backgroundColor.equalsIgnoreCase("yellow"))
			return "by";
		else if (foregroundColor.equalsIgnoreCase("black") && backgroundColor.equalsIgnoreCase("white"))
			return "bw";
		else if (foregroundColor.equalsIgnoreCase("white") && backgroundColor.equalsIgnoreCase("black"))
			return "wb";
		else
			return "default";
	}

	private String convertFontName(String fontName) {
		String name = null;
		if (fontName == null)
			return "default";
		else {
			switch (fontName.toUpperCase()) {
			case timesFont:
				name = "times";
				break;
			case sansFont:
				name = "comic";
				break;
			case arialFont:
				name = "arial";
				break;
			case verdanaFont:
				name = "verdana";
				break;
			default:
				name = "default";
				break;
			}
		}
		return name;
	}
	
	private String[] convertFontNameLong(String fontName) {
		String[] font = new String[2];
		switch (fontName.toLowerCase()) {
		case "times":
			font[0] = timesFont;
			font[1] = serifFace;
			break;
		case "comic":
			font[0] = sansFont;
			font[1] = sansserifFace;
			break;
		case "arial":
			font[0] = arialFont;
			font[1] = serifFace;
			break;
		case "verdana":
			font[0] = verdanaFont;
			font[1] = sansserifFace;
			break;
		default:
			font[0] = "default";
			font[1] = "default";
			break;
		}

		return font;
	}
}
