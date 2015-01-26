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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
	public EasitApplicationPreferences createDefaultPreferences( Environment environment, EasitAccount user ) throws Exception {
					
		//Create the default preferences
		EasitApplicationPreferences defaultPref = new EasitApplicationPreferences(  environment.getProperty("textFont"), environment.getProperty("theme"), Integer.parseInt(environment.getProperty("textSize")),
				Integer.parseInt(environment.getProperty("lineSpacing")), Boolean.parseBoolean(environment.getProperty("layout")), Boolean.parseBoolean(environment.getProperty("toc")),
				Boolean.parseBoolean(environment.getProperty("links")), Boolean.parseBoolean(environment.getProperty("inputsLarger")), Integer.parseInt(environment.getProperty("magnification")),
				Boolean.parseBoolean(environment.getProperty("invertImages")), environment.getProperty("tracking"));
			
		
		//Save it
		insertOrUpdatePreferences(defaultPref, user);
		return defaultPref;
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
			logger.error(e.getMessage());
			throw new Easit4allException(e.getMessage());
		}
		return appPreferences;
	}

	@Override
	public void insertOrUpdatePreferences(EasitApplicationPreferences preferences, EasitAccount user) throws Exception {		
		
		//Convert the prefs to 
		String strPrefs = localPrefsToServerPrefs(preferences);
		
		//Prepare request and send it
		DefaultHttpClient client = new DefaultHttpClient();
		StringEntity input = new StringEntity(strPrefs);
		input.setContentEncoding("UTF8");
		input.setContentType("application/json");
		
		HttpPost postRequest = new HttpPost( environment.getProperty("flowManager.url")  + "/oldpreferences/" + user.getUserToken());
		postRequest.setEntity(input);
		HttpResponse response = client.execute(postRequest);	

		//NOT Correct answer
		if (response.getStatusLine().getStatusCode() != 200) {
			logger.info("ERROR:");
			logger.info("URL target" + environment.getProperty("flowManager.url")  + "/oldpreferences/" + user.getUserToken());
			throw new Exception("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
		
		//Clear connection
		client.getConnectionManager().shutdown();
	}

	/**
	 * Get the user preferences from the GPII
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private String getPreferencesFromServer(EasitAccount user) throws Exception {
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet( environment.getProperty("flowManager.url") + environment.getProperty("flowManager.preferences"));

		// add the access token to the request header
		request.addHeader("Authorization", "Bearer " + user.getAccessToken());
		HttpResponse response = client.execute(request);

		//NOT Correct answer
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

		//Correct answer 
		BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		logger.info("User preferences:" + result);
		return result.toString();
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
		int fromIndex = serverPrefs.indexOf("\"com.bdigital.easit4all\":");
		int toIndex = serverPrefs.length() - 1;
		serverPrefs = serverPrefs.substring(fromIndex +  "{\"com.bdigital.easit4all\":".length() , toIndex);
		
		
		//Convert the server prefs to local prefs
		JSONObject json = new JSONObject(serverPrefs.toString());
		Map<String,String> output = new HashMap<String, String>();
		CoreUtils.parse(json, output);
		
		prefs.setTextSize( Double.valueOf(output.get("fontSize"))/ 10.0);
		
		//If not recieve the theme prefs
		String frontFaceName = output.get("fontFaceFontName") == null ? "default" : output.get("fontFaceFontName");
		prefs.setTextFont(convertFontName(frontFaceName));
		
		//If not recieve the theme prefs
		String foregroundColor = output.get("foregroundColor") == null ? "default" : output.get("foregroundColor");
		String backgroundColor = output.get("backgroundColor") == null ? "default" : output.get("backgroundColor");
		prefs.setTheme(convertThemeName(foregroundColor, backgroundColor));
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
	private String localPrefsToServerPrefs(EasitApplicationPreferences localPrefs) throws Exception{
		
		PreferencesC4A prefsC4A = new PreferencesC4A();
		String term_common = "http://registry.gpii.net/common/";
		String term_prov_common = "http://registry.gpii.net/provisional-common/";

		// Fontsize
		List<PreferencesValue> list1 = new ArrayList<PreferencesValue>();
		PreferencesValue val1 = new PreferencesValue();
		val1.setValue(localPrefs.getTextSize());
		list1.add(val1);
		prefsC4A.set(term_common + "fontSize", list1);

		// foregroundColor
		List<PreferencesValue> list4 = new ArrayList<PreferencesValue>();
		PreferencesValue val4 = new PreferencesValue();
		val4.setValue(getForeGroundColor(localPrefs.getTheme()));
		list4.add(val4);
		prefsC4A.set(term_prov_common + "foregroundColor", list4);

		// backgroundColor
		List<PreferencesValue> list5 = new ArrayList<PreferencesValue>();
		PreferencesValue val5 = new PreferencesValue();
		val5.setValue(getBackgroundColor(localPrefs.getTheme()));
		list5.add(val5);
		prefsC4A.set(term_prov_common + "backgroundColor", list5);

		// fontName
		List<PreferencesValue> list2 = new ArrayList<PreferencesValue>();
		PreferencesValue val2 = new PreferencesValue();
		String[] font = convertFontNameLong(localPrefs.getTextFont());
		val2.setValue( localPrefs.getTextFont() );
		list2.add(val2);
		prefsC4A.set(term_prov_common + "fontName", list2);
		
		// magnification
		List<PreferencesValue> list6 = new ArrayList<PreferencesValue>();
		PreferencesValue val6 = new PreferencesValue();
		val6.setValue((int) Math.round(localPrefs.getMagnification()));
		list6.add(val6);
		prefsC4A.set(term_common + "magnification", list6);

		// tracking
		List<PreferencesValue> list7 = new ArrayList<PreferencesValue>();
		PreferencesValue val7 = new PreferencesValue();
		val7.setValue(localPrefs.getTracking());
		list7.add(val7);
		prefsC4A.set(term_prov_common + "magnifierFollows", list7);

		// invertImages
		List<PreferencesValue> list8 = new ArrayList<PreferencesValue>();
		PreferencesValue val8 = new PreferencesValue();
		val8.setValue(localPrefs.isInvertImages());
		list8.add(val8);
		prefsC4A.set(term_common + "invertColours", list8);

		// links
		List<PreferencesValue> list9 = new ArrayList<PreferencesValue>();
		PreferencesValue val9 = new PreferencesValue();
		val9.setValue(localPrefs.isLinks());
		list9.add(val9);
		prefsC4A.set(term_prov_common + "emphasizeLinks", list9);	

		// toc
		List<PreferencesValue> list10 = new ArrayList<PreferencesValue>();
		PreferencesValue val10 = new PreferencesValue();
		val10.setValue(localPrefs.isToc());
		list10.add(val10);
		prefsC4A.set(term_prov_common + "tableOfContents", list10);

		// inputs larger
		List<PreferencesValue> list11 = new ArrayList<PreferencesValue>();
		PreferencesValue val11 = new PreferencesValue();
		val11.setValue(localPrefs.isInputsLarger());
		list11.add(val11);
		prefsC4A.set(term_prov_common + "inputsLarger", list11);

		// layout
		List<PreferencesValue> list12 = new ArrayList<PreferencesValue>();
		PreferencesValue val12 = new PreferencesValue();
		val12.setValue(localPrefs.isLayout());
		list12.add(val12);
		prefsC4A.set(term_prov_common + "simplifyLayout", list12);

		// line spacing
		List<PreferencesValue> list13 = new ArrayList<PreferencesValue>();
		PreferencesValue val13 = new PreferencesValue();
		val13.setValue(localPrefs.getLineSpacing());
		list13.add(val13);	
		prefsC4A.set(term_prov_common + "lineSpacing", list13);	

		StringWriter strPreferences = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().without(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);
		mapper.writeValue(strPreferences, prefsC4A);

		return strPreferences.toString();
	}
	
	
	private String getForeGroundColor(String theme) {
		if (theme == null || theme.equalsIgnoreCase("default"))
			return "default";
		else if (theme.charAt(0) == 'b')
			return "black";
		else if (theme.charAt(0) == 'y')
			return "yellow";
		else if (theme.charAt(0) == 'w')
			return "white";
		else
			return "default";
	}
	
	private String getBackgroundColor(String theme) {
		if (theme == null || theme.equalsIgnoreCase("default"))
			return "default";
		else if (theme.charAt(1) == 'b')
			return "black";
		else if (theme.charAt(1) == 'y')
			return "yellow";
		else if (theme.charAt(1) == 'w')
			return "white";
		else
			return "default";
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
