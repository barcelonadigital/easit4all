package org.easit.dao.model.c4a;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreferencesC4A {

    protected Map<String, List<PreferencesValue>> properties = new LinkedHashMap<String, List<PreferencesValue>>();

    @JsonAnyGetter
    public Map<String, List<PreferencesValue>> any() {
	return properties;
    }

    @JsonAnySetter
    public void set(String name, List<PreferencesValue> value) {
	properties.put(name, value);
    }

}
