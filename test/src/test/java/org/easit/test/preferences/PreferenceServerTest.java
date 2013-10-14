package org.easit.test.preferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.*;

public class PreferenceServerTest {

    @Test
    public void getPreferencesSecurityPass() {
	String strPrefs = "";
	String res = "";
	try {
	    // connect
	    HttpURLConnection conn = connectSecurityGatewayTest("http://172.20.10.118:8280/services/Entitlement_prova/easit1/settings/12345/net/172.20.100.127");
	    assert (conn.getResponseCode() == 200);

	    // Get preferences
	    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	    while ((res = br.readLine()) != null) {
		strPrefs += res;
	    }
	    conn.disconnect();
	    assert (strPrefs.length() > 0);
	} catch (Exception e) {
	    assert (false);
	}
    }

    @Test
    public void connectSecurityGatewayPass() {
	HttpURLConnection conn = connectSecurityGatewayTest("http://172.20.10.118:8280/services/Entitlement_prova/easit1/settings/12345/net/172.20.100.127");
	try {
	    assert (conn.getResponseCode() == 200);
	} catch (IOException e) {
	    assert (false);
	}
    }

    @Test
    public void connectSecurityGatewayNoPass() {
	HttpURLConnection conn = connectSecurityGatewayTest("http://172.20.10.118:8280/services/Entitlement_prova/easit1/settings/12345/net/172.0.0.0");
	try {
	    assert (conn.getResponseCode() != 200);
	} catch (IOException e) {
	    assert (false);
	}
    }

    public HttpURLConnection connectSecurityGatewayTest(String urlConn) {
	try {
	    HttpURLConnection conn = null;

	    // Prepare request
	    URL url = new URL(urlConn);

	    // Create connection
	    conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    return conn;

	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}

    }

}
