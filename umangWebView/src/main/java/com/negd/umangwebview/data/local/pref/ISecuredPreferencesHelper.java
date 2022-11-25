package com.negd.umangwebview.data.local.pref;

public interface ISecuredPreferencesHelper {

    void writeEncryptedStringPreference(String key,String value);

    String getEncryptedStringPreference(String key,String defaultValue);

    void deleteSecuredPreference();
}
