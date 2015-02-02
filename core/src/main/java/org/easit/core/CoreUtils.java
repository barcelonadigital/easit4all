package org.easit.core;

import java.util.Iterator;
import java.util.Map;

import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public class CoreUtils {

	
	/**
	 * Json parser
	 * @param json
	 * @param out
	 * @return
	 * @throws JSONException
	 */
	public static Map<String,String> parse(JSONObject json , Map<String,String> out) throws JSONException{
		Iterator<String> keys = json.keys();
		while(keys.hasNext()){
			String key = keys.next();
			String val = null;
			try{
				JSONObject value = json.getJSONObject(key);
				parse(value,out);
			}catch(Exception e){
				val = json.getString(key);
			}

			if(val != null){
				out.put(key,val);
			}
		}
		return out;
	}
	
}
