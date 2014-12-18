package org.easit.core.preferences.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import org.easit.core.Easit4allException;
import org.easit.core.preferences.PreferencesDataManager;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.EasitApplicationPreferences;
import org.easit.dao.model.c4a.PreferencesC4A;
import org.easit.dao.model.c4a.PreferencesValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * Server Preferences Manager Implementation
 *
 */
public class BKP_ServerPreferencesManager implements PreferencesDataManager {

	private static final Logger logger = LoggerFactory.getLogger(BKP_ServerPreferencesManager.class);

	
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

	public BKP_ServerPreferencesManager(String urlPreferenceServer, String urlCommon, String urlSave) {
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
			PreferencesC4A prefsC4A = new PreferencesC4A();
			String strPrefs = mergePreferences(prefsC4A, defaultPref);
			
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
			PreferencesC4A prefsC4A = null;

			//Try to insert them
			if (!strPrefs.isEmpty() && strPrefs.toLowerCase().indexOf("error") == -1) {
				prefsC4A = parseToObjectPreferences(strPrefs);
				appPreferences = convertToUIOptionsFluid(prefsC4A);
			}
		} catch (Exception e) {
			throw new Easit4allException(e.getMessage());
		}
		return appPreferences;
	}

	@Override
	public void insertOrUpdatePreferences(EasitApplicationPreferences preferences, EasitAccount user) throws Exception {
		try {
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
		}
	}

	private void sendToServer(EasitAccount user, String strPrefs) throws UnsupportedEncodingException, IOException, ClientProtocolException {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		//Prepare request
		StringEntity input = new StringEntity(strPrefs);
		input.setContentEncoding("UTF8");
		input.setContentType("application/json");
		HttpPost postRequest = new HttpPost(psURL + user.getUserToken());
		postRequest.setEntity(input);

		//Send request
		HttpResponse response = httpClient.execute(postRequest);	
		response.getStatusLine().getStatusCode();

		//Clear connection
		httpClient.getConnectionManager().shutdown();
	}

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

	private PreferencesC4A parseToObjectPreferences(String strPrefs) throws JsonParseException, JsonMappingException, IOException {
		// Map json to Object preferences
		ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.CREATOR, Visibility.NONE);

		//Preferences hack parsing 
		int fromIndex = strPrefs.indexOf("\"preferences\":");
		int toIndex = strPrefs.length() - 1;
		strPrefs = strPrefs.substring(fromIndex +  "\"preferences\":".length() , toIndex);

		PreferencesC4A prefsC4A = mapper.readValue(strPrefs, PreferencesC4A.class);
		return prefsC4A;
	}

	private String getIdentifiers(String strPrefs) {
		int end = 0, ini = 0;
		String strPrefsAux = "";
		ini = strPrefs.indexOf("_id");

		if (ini > 0) {
			end = strPrefs.indexOf(",");
			strPrefsAux = strPrefs.substring(ini - 1, end + 1);
		}

		ini = strPrefs.indexOf("_rev");
		if (ini > 0) {
			end = strPrefs.indexOf(",", ini);
			strPrefsAux += strPrefs.substring(ini - 1, end + 1);
		}

		return strPrefsAux;
	}

	private String mergePreferences(PreferencesC4A prefsC4A, EasitApplicationPreferences preferences) throws JsonGenerationException, JsonMappingException, IOException {

		// Fontsize
		List<PreferencesValue> list1 = new ArrayList<PreferencesValue>();
		PreferencesValue val1 = new PreferencesValue();
		val1.setValue(preferences.getTextSize() * 10.0);
		list1.add(val1);
		prefsC4A.set(psCommon + "fontSize", list1);

		// foregroundColor
		List<PreferencesValue> list4 = new ArrayList<PreferencesValue>();
		PreferencesValue val4 = new PreferencesValue();
		val4.setValue(getForeGroundColor(preferences.getTheme()));
		list4.add(val4);
		prefsC4A.set(psCommon + "foregroundColor", list4);

		// backgroundColor
		List<PreferencesValue> list5 = new ArrayList<PreferencesValue>();
		PreferencesValue val5 = new PreferencesValue();
		val5.setValue(getBackgroundColor(preferences.getTheme()));
		list5.add(val5);
		prefsC4A.set(psCommon + "backgroundColor", list5);

		String[] font = convertFontNameLong(preferences.getTextFont());

		// fontFaceFontName
		List<PreferencesValue> list2 = new ArrayList<PreferencesValue>();
		PreferencesValue val2 = new PreferencesValue();
		List<String> object = new ArrayList<String>();
		object.add(font[0]);
		val2.setValue(object);
		list2.add(val2);
		prefsC4A.set(psCommon + "fontFaceFontName", list2);

		// fontFaceGenericFontFace
		List<PreferencesValue> list3 = new ArrayList<PreferencesValue>();
		PreferencesValue val3 = new PreferencesValue();
		val3.setValue(font[1]);
		list3.add(val3);
		prefsC4A.set(psCommon + "fontFaceGenericFontFace", list3);

		// magnification
		List<PreferencesValue> list6 = new ArrayList<PreferencesValue>();
		PreferencesValue val6 = new PreferencesValue();
		val6.setValue((int) Math.round(preferences.getMagnification()));
		list6.add(val6);
		prefsC4A.set(psCommon + "magnification", list6);

		// tracking
		List<PreferencesValue> list7 = new ArrayList<PreferencesValue>();
		PreferencesValue val7 = new PreferencesValue();
		val7.setValue(preferences.getTracking());
		list7.add(val7);
		prefsC4A.set(psCommon + "tracking", list7);

		// invertImages
		List<PreferencesValue> list8 = new ArrayList<PreferencesValue>();
		PreferencesValue val8 = new PreferencesValue();
		val8.setValue(preferences.isInvertImages());
		list8.add(val8);
		prefsC4A.set(psCommon + "invertImages", list8);

		// links
		List<PreferencesValue> list9 = new ArrayList<PreferencesValue>();
		PreferencesValue val9 = new PreferencesValue();
		val9.setValue(preferences.isLinks());
		list9.add(val9);
		prefsC4A.set(psCommon + "links", list9);	

		// toc
		List<PreferencesValue> list10 = new ArrayList<PreferencesValue>();
		PreferencesValue val10 = new PreferencesValue();
		val10.setValue(preferences.isToc());
		list10.add(val10);
		prefsC4A.set(psCommon + "toc", list10);

		// inputs larger
		List<PreferencesValue> list11 = new ArrayList<PreferencesValue>();
		PreferencesValue val11 = new PreferencesValue();
		val11.setValue(preferences.isInputsLarger());
		list11.add(val11);
		prefsC4A.set(psCommon + "inputsLarger", list11);

		// layout
		List<PreferencesValue> list12 = new ArrayList<PreferencesValue>();
		PreferencesValue val12 = new PreferencesValue();
		val12.setValue(preferences.isLayout());
		list12.add(val12);
		prefsC4A.set(psCommon + "layout", list12);

		// line spacing
		List<PreferencesValue> list13 = new ArrayList<PreferencesValue>();
		PreferencesValue val13 = new PreferencesValue();
		val13.setValue(preferences.getLineSpacing());
		list13.add(val13);
		prefsC4A.set(psCommon + "lineSpacing", list13);	

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

	/**
	 * For fontSize, supposing that it comes as px from C4A, convert it to em 1px = 0.0625 em
	 * 0.875 = 14px (default fontsize app) 
	 * @param prefsC4A
	 * @return
	 */
	private EasitApplicationPreferences convertToUIOptionsFluid(PreferencesC4A prefsC4A) {
		EasitApplicationPreferences prefs = new EasitApplicationPreferences();
		prefs.setTextSize(Double.valueOf(prefsC4A.any().get(psCommon + "fontSize").get(0).getValue() + "") / 10.0);
		String fontName = prefsC4A.any().get(psCommon + "fontFaceFontName").get(0).getValue().toString();
		prefs.setTextFont(convertFontName(fontName.substring(1, fontName.lastIndexOf("]"))));
		prefs.setTheme(convertThemeName(prefsC4A.any().get(psCommon + "foregroundColor").get(0).getValue().toString(), prefsC4A.any().get(psCommon + "backgroundColor").get(0).getValue().toString()));
		prefs.setMagnification((Integer) prefsC4A.any().get(psCommon + "magnification").get(0).getValue());
		prefs.setTracking(prefsC4A.any().get(psCommon + "tracking").get(0).getValue().toString());
		prefs.setInvertImages((Boolean) prefsC4A.any().get(psCommon + "invertImages").get(0).getValue());
		prefs.setInputsLarger((Boolean) prefsC4A.any().get(psCommon + "inputsLarger").get(0).getValue());
		prefs.setLayout((Boolean) prefsC4A.any().get(psCommon + "layout").get(0).getValue());
		prefs.setLineSpacing(Double.valueOf(prefsC4A.any().get(psCommon + "lineSpacing").get(0).getValue() + ""));
		prefs.setLinks((Boolean) prefsC4A.any().get(psCommon + "links").get(0).getValue());
		prefs.setToc((Boolean) prefsC4A.any().get(psCommon + "toc").get(0).getValue());

		return prefs;
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
