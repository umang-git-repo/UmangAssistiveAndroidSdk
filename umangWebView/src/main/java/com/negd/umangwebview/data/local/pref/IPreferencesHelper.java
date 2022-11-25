package com.negd.umangwebview.data.local.pref;

public interface IPreferencesHelper {

    void writeStringPreference(String key,String value);

    String getStringPreference(String key,String defaultValue);

    void deleteAllPreference();
}
