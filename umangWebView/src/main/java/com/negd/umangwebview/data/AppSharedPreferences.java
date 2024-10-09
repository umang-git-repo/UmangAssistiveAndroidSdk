package com.negd.umangwebview.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AppSharedPreferences{

    private static AppSharedPreferences instance;
    private final SharedPreferences encryptedSharedPreferences;

    // Private constructor to prevent direct instantiation
    private AppSharedPreferences(Context context, String prefFileName) throws GeneralSecurityException, IOException {
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        encryptedSharedPreferences = EncryptedSharedPreferences.create(
                context,
                prefFileName,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    // Public method to provide a global point of access to the singleton instance
    public static synchronized AppSharedPreferences getInstance(Context context) throws GeneralSecurityException, IOException {
        if (instance == null) {
            instance = new AppSharedPreferences(context.getApplicationContext(), "UmangSdkPrefs");
        }
        return instance;
    }

    public void writeStringPreference(String key, String value) {
        encryptedSharedPreferences.edit().putString(key, value).apply();
    }

    public String getStringPreference(String key, String defaultValue) {
        return encryptedSharedPreferences.getString(key, defaultValue != null ? defaultValue : "");
    }

    public void deleteAllPreference() {
        clearAllPref();
    }

    private void clearAllPref() {
        encryptedSharedPreferences.edit().clear().apply();
    }
}