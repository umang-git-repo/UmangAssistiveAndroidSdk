package com.negd.umangwebview.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;
import com.negd.umangwebview.di.SecuredPreferenceInfo;
import java.security.SecureRandom;
import javax.inject.Inject;
import at.favre.lib.armadillo.Armadillo;
import at.favre.lib.armadillo.PBKDF2KeyStretcher;


public class AppSecuredPreferencesHelper implements ISecuredPreferencesHelper {

    private final SharedPreferences mPrefs;

    public static String EN_PREF_U_S = "n_u_s";
    public static String EN_PREF_U_X_K = "n_u_x_k";
    public static String EN_PREF_U_MD_S = "n_u_md_s";
    public static String EN_PREF_U_MP_S = "n_u_mp_s";
    public static String EN_PREF_D_S = "n_d_s";
    public static String EN_PREF_D_X= "n_d_x";
    public static String EN_PREF_D_M = "n_d_m";
    public static String EN_PREF_U_K = "n_u_k";
    public static String EN_PREF_C_K = "n_c_k";
    public static String EN_PREF_D_K = "n_d_k";
    public static String EN_PREF_W_A = "n_w_a";
    public static String EN_PREF_M_P = "m_p";

    @Inject
    public AppSecuredPreferencesHelper(Context context, @SecuredPreferenceInfo String prefFileName) {
        mPrefs = Armadillo.create(context, prefFileName)
                .encryptionFingerprint(context)
                .keyStretchingFunction(new PBKDF2KeyStretcher())
                .secureRandom(new SecureRandom())
                .enableKitKatSupport(true)
                .build();
    }

    @Override
    public void writeEncryptedStringPreference(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

    @Override
    public String getEncryptedStringPreference(String key, String defaultValue) {
        return mPrefs.getString(key, defaultValue);
    }

    @Override
    public void deleteSecuredPreference() {
        mPrefs.edit().clear().apply();
    }
}
