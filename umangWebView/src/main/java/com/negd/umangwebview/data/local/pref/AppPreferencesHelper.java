package com.negd.umangwebview.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.negd.umangwebview.di.PreferenceInfo;
import com.negd.umangwebview.utils.AppConstants;
import com.negd.umangwebview.utils.DeviceUtils;
import com.negd.umangwebview.utils.LogUtil;

import javax.inject.Inject;


public class AppPreferencesHelper implements IPreferencesHelper {

    private final SharedPreferences mPrefs;
    private final Context mContext;

    public static String PREF_IS_CHAT_CONNECTED="PrefChatConnected";
    public static String PREF_CHAT_JABBER_ID="PrefChatJabberId";



    public static String PREF_EULA_DISPLAYED = "PrefEulaDisplayed";
    public static String PREF_SYSTEM_LOCALE = "PrefSystemLocale";
    public static String PREF_SELECTED_LOCALE = "PrefSelectedLocale";
    public static String PREF_SHOW_NOTIFICATIONS = "PrefShowNotifications";
    public static String PREF_SHOW_NOTIF_OFF_LAYOUT = "PrefShowNotifOffLayout";
    public static String PREF_SHOW_BANNER = "PrefShowBanner";
    public static String PREF_TOUR_DISPLAYED = "PrefTourDisplayed";
    public static String PREF_LANGUAGE_SELECTED = "PrefLanguageSelected";
    public static String PREF_NOTIF_ENABLE_TYPE = "PrefNotifEnableType";
    public static String PREF_REGION_ID = "PrefRegionId";
    public static String PREF_SELECTED_STATE_ID = "PrefSelectedStateId";
    public static String PREF_SELECTED_STATE_ID_HOME = "PrefSelectedStateIdHome";
    public static String PREF_SELECTED_STATE_ID_DIRECTORY = "PrefSelectedStateIdDirectory";
    public static String PREF_SELECTED_STATE_ABBR = "PrefSelectedStateAbbr";
    public static String PREF_SELECTED_STATE_EMBLEM = "PrefSelectedStateEmblem";
    public static String PREF_SELECTED_ALL_SERVICES_STATE_EMBLEM = "PrefSelectedAllServicesStateEmblem";
    public static String PREF_SELECTED_ALL_SERVICES_STATE_ID = "PrefSelectedAllServicesStateId";
    //    public static String PREF_LANGUAGE_SCREEN_SHOWN = "PrefLanguageScreenShown";
    public static String PREF_HINT_SHOWN = "PrefHintShown";
    public static String PREF_WEB_HINT_SHOWN = "PrefWebHintShown";
    public static String PREF_LOGIN_HINT_SHOWN = "PrefLoginHintShown";
    public static String PREF_DEFAULT_TAB = "PrefDefaultTab";
    public static String PREF_FONT_SIZE = "PrefFontSize";
    public static String PREF_SHOW_PROFILE_FOOTER = "PrefShowProfileFooter";
    public static String PREF_STATE_DIALOG_CANCELED = "PrefStateDialogCanceled";
    public static String PREF_KEEP_ME_LOGGED_IN = "PrefKeepMeLoggedIn";
    public static String PREF_FINGERPRINT_AUTHENTICATION = "PrefFingerprintAuthentication";
    public static String PREF_SHOW_SERVICE_DIRECTORY = "PrefShowServiceDirectory";

    public static String PREF_REQUEST_IMAGE_FOR = "PrefRequestImageFor";

    public static String PREF_PROFILE_SAVED_SKIPPED = "PrefProfileSavedSkip";

    public static String PREF_DIGILOCKER_ENABLED = "PrefDigilockerEnabled";
    public static String PREF_JV_WADH_VER = "PrefJVWadhVer";

    public static String PREF_USP_DATA_LOADED = "PrefUspDataLoaded";
    public static String PREF_INIT_RESPONSE = "PrefInitResponse";
    //    public static String PREF_USP_IMG_LOADED = "PrefUspImgLoaded";
    public static String PREF_SPLASH_CONFIG = "PrefSplashConfig";
    public static String PREF_APP_VERSION_CODE = "PrefAppVersionCode";
    public static String PREF_APP_VERSION_CODE_MESSAGE = "PrefAppVersionCodeMessage";
    public static String PREF_APP_VERSION_FORCE_UPDATE = "PrefAppVersionForceUpdate";
    public static String PREF_SNACKBAR_VERSION_CODE = "PrefSnackbarVersionCode";
    public static String PREF_SNACKBAR_SHOWN_VERSION_CODE = "PrefSnackbarShownVersionCode";
    public static String PREF_TERMS_AND_CONDITIONS_URL = "PrefTermsAndConditionsUrl";
    public static String PREF_PRIVACY_POLICY_URL = "PrefPrivacyPolicyUrl";
    public static String PREF_REFUND_POLICY_URL = "PrefRefundPolicyUrl";
    public static String PREF_ENABLE_AADHAAR = "PrefEnableAadhaar";
    public static String PREF_AADHAAR_MSG_INIT = "PrefAadhaarMsgInit";
    public static String PREF_AADHAAR_MSG_PROFILE = "PrefAadhaarMsgProfile";
    public static String PREF_AADHAAR_MSG_REG = "PrefAadhaarMsgReg";
    public static String PREF_AADHAAR_LINK1 = "PrefAadhaarLink1";
    public static String PREF_AADHAAR_LINK2 = "PrefAadhaarLink2";
    public static String PREF_RECOVERY_OPTION_INTERVAL = "PrefRecoveryOptionInterval";
    public static String PREF_MPIN_INTERVAL = "PrefMpinInterval";
    public static String PREF_RECOVERY_OPTION_DAYS_INTERVAL = "PrefRecoveryOptionDaysInterval";
    public static String PREF_MPIN_DAYS_INTERVAL = "PrefMpinDaysInterval";
    public static String PREF_RECOVERY_OPTION_SET = "PrefRecoveryOptionSet";
    public static String PREF_RECOVERY_OPTION_PREVIOUS_DATE = "PrefRecoveryOptionPreviousDate";
    public static String PREF_RECOVERY_OPTION_APP_OPEN_COUNT = "PrefRecoveryOptionAppOpenCount";

    public static String PREF_AADHAAR_NUMBER = "PrefAadhaarNumber";
    public static String PREF_MOBILE_NUMBER = "PrefMobileNumber";
    public static String PREF_LAST_MODIFIED_DATE = "PrefLastModDate";
    public static String PREF_INFO_LAST_MODIFIED_DATE = "PrefInfoLastModDate";
    public static String PREF_CLEAR_LAST_MODIFIED_DATE_FOR_HOME_V1 = "PrefClearLastModDateForHomeScreenV1";
    public static String PREF_CLEAR_LAST_MODIFIED_DATE_FOR_HOME_V3 = "PrefClearLastModDateForHomeScreenV3";
    public static String PREF_CLEAR_LAST_MODIFIED_DATE_FOR_HOME_V4 = "PrefClearLastModDateForHomeScreenV4";
    public static String PREF_CLEAR_LAST_MODIFIED_DATE_FOR_HOME_MULTI_CAT = "PrefClearLastModDateForHomeScreenMultiCat";
    public static String PREF_CLEAR_LAST_MODIFIED_DATE_FOR_INFO_HOME_MULTI_CAT = "PrefClearLastModDateForInfoHomeScreenMultiCat";
    public static String PREF_LAST_MODIFIED_SERVICE_DIR_DATE = "PrefLastModServiceDirDate";
    public static String PREF_TOKEN = "PrefToken";

    public static final String PREF_IS_DIGI_LOGIN = "is_digi_login";
    public static final String PREF_DIGI_CODE = "digi_code";

    public static String PREF_TEMP_ALT_MNO = "PrefTempAltMno";
    public static String PREF_TEMP_EMAIL = "PrefTempEmail";

    /**
     * Checks if user is logged in or not
     */
    public static String PREF_MPIN_SET = "PrefMpinSet";

    /**
     * Checks if user has set his MPIN or not
     */
    public static String PREF_USER_MPIN_SET = "PrefUserMpinSet";
    public static String PREF_SHOW_MPIN_DIALOG = "PrefShowMpinDialog";
    public static String PREF_MPIN_DIALOG_MANDATORY = "PrefMpinDialogMandatory";
    public static String PREF_NEW_USER = "PrefNewUser";
    public static String PREF_USER_ID = "PrefUserId";
    public static String PREF_USER_EMAIL = "PrefUserEmail";
    public static String PREF_USER_NAME = "PrefUserName";
    public static String PREF_SHOW_LOGIN_WITH_OTP_HINT = "PrefShowLoginWithOtpHint";
    public static String PREF_NEW_INFO_USER = "PrefNewInfoUser";
    public static String PREF_FCM_ID = "PrefFCMId";
    public static String PREF_MPIN_TEMP = "PrefMPINTEMP";
    public static String PREF_USER_INFO_JSON = "PrefUserInfo";
    public static String PREF_FCM_UPDATED_AT_BACKEND = "PrefFCMUpdatedAtBackend";
    public static String PREF_FACEBOOK_ID = "PrefFacebookID";
    public static String PREF_GOOGLE_ID = "PrefGoogleID";
    public static String PREF_TWITTER_ID = "PrefTwitterID";
    public static String PREF_FACEBOOK_PROFILE_NAME = "PrefFacebookProfileName";
    public static String PREF_GOOGLE_PROFILE_NAME = "PrefGoogleProfileName";
    public static String PREF_GOOGLE_PROFILE_PIC_URL = "PrefGoogleProfilePic";
    public static String PREF_TWITTER_PROFILE_NAME = "PrefTwitterProfileName";
    public static String PREF_TWITTER_PROFILE_PIC_URL = "PrefTwitterProfilePic";

    public static String PREF_APP_SHARE_TEXT = "PrefAppShareTxt";
    public static String PREF_APP_EMAIL_SUPPORT = "PrefEmailSupportTxt";
    public static String PREF_APP_PHONE_SUPPORT = "PrefPhoneSupportTxt";
    public static String PREF_OPEN_SOURCE_LICENSES = "PrefOpenSourceLicenses";
    public static String PREF_USER_MANUAL = "PrefUserManual";
    public static String PREF_FAQ = "PrefFaq";
    public static String PREF_FACEBOOK_LINK = "PrefFacebookLink";
    public static String PREF_GOOGLEPLUS_LINK = "PrefGoogleLink";
    public static String PREF_TWITTER_LINK = "PrefTwitterLink";
    public static String PREF_DYNAMIC_TABS = "PrefDynamicTabs";
    public static String PREF_DYNAMIC_INFO_TABS = "PrefDynamicInfoTabs";
    public static String PREF_RECENT_SEARCHES_NAME = "PrefRecentSearchesList";
    public static String PREF_USER_SOC_INFO_JSON = "PrefSocInfo";
    public static String PREF_USER_ADHR_INFO_JSON = "PrefAdhrInfo";
    public static String PREF_ADHR_REG_LBLS = "PrefAdhrRegLbls";
    public static String PREF_PROFILE_COMPLETENESS = "PrefPC";
    public static String PREF_PROFILE_COMPLETENESS_PERCENTAGE = "PrefPer";
    public static String PREF_SHOW_PROFILE_COMPLETENESS = "PrefPCSHOW";
    public static String PREF_FIELDS_MASTER = "PrefFieldsMaster";

    public static String PREF_DIGI_FORGOT_USERNAME = "PrefDigiForgotUsername";
    public static final String PREF_DIGI_FORGOT_PSWD = "PrefDigiForgotPassword";

    public static String PREF_IMAGE_URI = "PrefImageUri";

    public static final String LOGIN_RESPONSE = "LoginResponse";

    public static String PREF_DIGI_OAUTH_URL = "PrefDigiOAuthUrl";

    public static String PREF_BIG_QUERY_ENABLED = "PrefBigQueryEnabled";
    public static String PREF_AUTH_BIG_QUERY_ENABLED = "PrefAuthBigQueryEnabled";

    public static String PREF_TEMP_CITIES = "PrefTempCities";
    public static String PREF_TEMP_STATEID = "PrefTempStateID";

    public static String PREF_SELECTED_NOTIF_SOUND_URI = "PrefSelectedNotifSoundUri";

    public static String PREF_BEARER = "PrefBear";
    public static String PREF_BEARER_DIGI = "PrefBearDigi";
    public static String PREF_BEARER_EXPIRY = "PrefBearEx";
    public static String PREF_BEARER_EXPIRY_DIGI = "PrefBearExDigi";
    public static String PREF_FEEDBACK_TXT = "PrefFeedbackTxt";
    public static String PREF_FEEDBACK_TYPE = "PrefFeedbackType";
    public static String PREF_NPS_BEARER = "PrefNpsBearer";
    public static String PREF_NPS_BEARER_EXPIRY = "PrefNpsBearerExpiry";

    public static String PREF_DIGILOCKER_LINKED = "PrefDigilockerLinked";
    public static String PREF_UPLOADED_DIRECTORY = "PrefDigilockerUploadDirectory";

    public static String PREF_QUERY_FEEDBACK_TXT = "PrefQueryFeedbackTxt";
    public static String PREF_QUERY_FEEDBACK_TYPE = "PrefQueryFeedbackType";

    public static String PREF_ISSUE_FEEDBACK_TXT = "PrefIssueFeedbackTxt";
    public static String PREF_ISSUE_FEEDBACK_TYPE = "PrefIssueFeedbackType";


    public static String PREF_TEMP_AADHAAR_INFO = "PrefAadhaarTempInfo";
    public static String PREF_LOCAL_IMG_URI = "PrefLocalImgUri";
    public static String PREF_PC_TIMEOUT = "PrefPCTimeOut";

    public static String PREF_WEBPAGE_LOADING_BOOLEAN = "PrefWebpageLoadingBoolean";

    public static String PREF_PERMISSION_DIALOG_SHOWN = "PrefPermissionDialogShown";

    public static String PREF_CHAT_INIT_CALLED = "PrefChatInitCalled";

    public static String PREF_CHAT_TRIGGER = "PrefChatTrigger";
    public static String PREF_CHAT_USER_ID = "PrefChatUserId";
    public static final String PREF_CHAT_USER_PSWD = "PrefChatUserPass";
    public static String PREF_CHAT_INIT_SENT = "PrefChatInitSent";
    public static String PREF_CHAT_TOKEN = "PrefChatToken";
    public static String PREF_CHAT_AGENT_ID = "PrefChatAgentId";

    public static String PREF_NOTIF_DIALOG_CANCELLED = "PrefNotifDialogCancelled";

    public static String PREF_DIGI_USERNAME = "PrefDigiUsername";
    public static final String PREF_DIGI_PSWD = "PrefDigiPass";
    public static String PREF_NODE = "PrefNode";
    public static String PREF_DIGI_ACCESS_TOKEN = "PrefDigiAccessToken";
    public static String PREF_DIGI_REFRESH_TOKEN = "PrefDigiRefreshToken";
    public static String PREF_DIGI_LOGIN_CODE = "PrefDigiLoginCode";
    public static String PREF_WEB_KEY_VALUE = "PrefWebKeyValue";
    public static String PREF_APP_NEW_UPDATE_DIALOG = "PrefNewUpdateDialog";
    public static String PREF_CAMERA_IMAGE_URI = "PrefCameraImgURI";
    public static String PREF_BIO_DEVICE_REGISTER_STEP_DONT_SHOW_CHECK_FOR_MANTRA = "PrefBioDeviceRegStepMantra";
    public static String PREF_BIO_DEVICE_REGISTER_STEP_DONT_SHOW_CHECK_FOR_MORPHO = "PrefBioDeviceRegStepMorpho";
    public static String PREF_BIO_DONT_SHOW_MORPHO = "PrefBioDontShowMorpho";
    public static String PREF_BIO_DONT_SHOW_MANTRA = "PrefBioDontShowMantra";
    public static String PREF_DO_NOT_SHOW_AGAIN_BIO_INFO = "PrefDoNotShowAgainBioInfo";

    public static String PREF_STATE_HAS_SERVICES = "PrefStateHasServices";
    public static String PREF_DIGI_UPLOADED_SORT_BY = "PrefDigiUploadedSortBy";
    public static String PREF_DIGI_ISSUED_SORT_BY = "PrefDigiIssuedSortBy";
    public static String PREF_DIGI_UPLOADED_LIST_GRID = "PrefDigiUploadedListGrid";
    public static String PREF_DIGI_ISSUED_LIST_GRID = "PrefDigiIssuedListGrid";

    public static String PREF_TRAI_MYCALL_STATUS = "PrefMyCallStatus";
    public static String PREF_TRAI_MYCALL_TIME = "PrefMyCallTIME";
    public static String PREF_TRAI_MYCALL_REDIRECT_URL = "PrefMyCallRedirectUrl";
    public static String PREF_TRAI_MYCALL_PROMPT_TYPE = "PrefMyCallPrompt";
    public static String PREF_TRAI_MYCALL_LAST_DIALOG_TIME = "PrefMyCallLastDialogTime";

    public static String PREF_TRAI_MYCALL_BATTERY_DIALOG = "PrefMyCallBatteryDialog";
    public static String PREF_DIGILOCKER_ISSUER_MESSAGE = "PrefDigiLockerIssue";

    public static String PREF_FCM_TOEKN_WITHOUT_ENCY = "PrefFcmTokenWtotEncy";

    public static String PREF_PLATFORM_UNDER_MAINTENANCE = "PrefPlatformUnderMaintenance";
    public static final String PREF_MESSAGE_BOARD_DISPLAYED = "PrefMessageBoardDisplayed";

    public static final String PREF_IS_FIRST_TIME= "PrefIsFirstTime";

    public static final String PREF_IS_NEW_NOTIFICATION= "PrefIsNewNotification";
    public static String PREF_IS_CATEGORY_SET= "PrefIsCategorySet";
    public static String PREF_IS_FROM_SPLASH= "PrefIsFromSplash";
    public static String PREF_IS_DATA_FETCHED= "PrefIsDataFetched";



    public static final String PREF_USER_LAT= "PrefUserLat";
    public static final String PREF_USER_LON = "PrefUserLon";
    public static final String PREF_OS_PROTECTED = "PrefOsProtected";
    public static final String PREF_COWIN_MOBILE = "cowin_mobile";
    public static final String PREF_COWIN_TOKEN = "cowin_token";
    public static final String PREF_USER_CURRENT_STATE = "current_state";


    public AppPreferencesHelper(Context mContext) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        this.mContext = mContext;
    }


    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        mContext = context;
    }

    @Override
    public void writeStringPreference(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

    @Override
    public String getStringPreference(String key, String defaultValue) {
        return mPrefs.getString(key, defaultValue);
    }

    @Override
    public void deleteAllPreference() {
      //  clearAllPref();
    }

    public void clearAllPref() {

        String token = "";
        try {
            token = getStringPreference(AppPreferencesHelper.PREF_FCM_ID, "");
        } catch (Exception e) {
        }

        String splashConfig = "";
        String appVerCode = "";
        String appVerMsg = "";
        String appForceUpdate = "";
        String termsAndCondition = "";
        String privacyPolicy = "";
        String openSource = "";
        String userManual = "";
        String faq = "";
        String facebookLink = "";
        String googleLink = "";
        String twitterLink = "";
        String selectedLocale = "";
        String dynamicTab = "";
        String fontSize = "";
        String defaultTab = "";
        String shareTxt = "";
        String emailSupport = "";
        String phoneSupport = "";
        String permissionDialogShown = "";
        String attentionDialog = "";
        String infoLDate = "";
        String loginHint = "";
        String dynamicInfoTab = "";
        String showLoginWithOtpHint = "";
        String jvwadh = "";
        String snackbarVersionCode = "";
        String recoveryOptionInterval = "";
        String recoveryOptionDaysInterval = "";
        String mpinInterval = "";
        String mpinDaysInterval = "";
        String enableAadhaar = "";
        String aadhaarMsgInit = "";
        String aadhaarMsgProfile = "";
        String digiEn = "";
        String digiUserName = "";
        String digiPassword = "";
        String u_s = "";
        String u_x_k = "";
        String u_md_s = "";
        String u_mp_s = "";
        String w_a = "";
        String d_s = "";
        String d_x = "";
        String d_m = "";
        String u_k = "";
        String c_k = "";
        String d_k = "";
        String node= "";

        try {
            node = getStringPreference(AppPreferencesHelper.PREF_NODE, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }

        try {
            splashConfig = getStringPreference(AppPreferencesHelper.PREF_SPLASH_CONFIG, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            appVerCode = getStringPreference(AppPreferencesHelper.PREF_APP_VERSION_CODE, DeviceUtils.getAppVersionCode(mContext));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            appVerMsg = getStringPreference(AppPreferencesHelper.PREF_APP_VERSION_CODE_MESSAGE, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }
        try {
            appForceUpdate = getStringPreference(AppPreferencesHelper.PREF_APP_VERSION_FORCE_UPDATE, "false");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            termsAndCondition = getStringPreference(AppPreferencesHelper.PREF_TERMS_AND_CONDITIONS_URL, AppConstants.TERMS_AND_CONDITION_URL);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            privacyPolicy = getStringPreference(AppPreferencesHelper.PREF_PRIVACY_POLICY_URL, AppConstants.PRIVACY_POLICY_URL);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            openSource = getStringPreference(AppPreferencesHelper.PREF_OPEN_SOURCE_LICENSES, AppConstants.OPEN_SOURCE_URL);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }
        try {
            userManual = getStringPreference(AppPreferencesHelper.PREF_USER_MANUAL, AppConstants.USER_MANUAL_URL);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }
        try {
            faq = getStringPreference(AppPreferencesHelper.PREF_FAQ, AppConstants.FAQ_URL);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }

        try {
            selectedLocale = getStringPreference(AppPreferencesHelper.PREF_SELECTED_LOCALE, "en");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            dynamicTab = getStringPreference(AppPreferencesHelper.PREF_DYNAMIC_TABS, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }
        try {
            fontSize = getStringPreference(AppPreferencesHelper.PREF_FONT_SIZE, "normal");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }
        try {
            defaultTab = getStringPreference(AppPreferencesHelper.PREF_DEFAULT_TAB, "normal");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }
        try {
            shareTxt = getStringPreference(AppPreferencesHelper.PREF_APP_SHARE_TEXT, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }
        try {
            emailSupport = getStringPreference(AppPreferencesHelper.PREF_APP_EMAIL_SUPPORT, "");
        } catch (Exception e) {

        }
        try {
            phoneSupport = getStringPreference(AppPreferencesHelper.PREF_APP_PHONE_SUPPORT, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            permissionDialogShown = getStringPreference(AppPreferencesHelper.PREF_PERMISSION_DIALOG_SHOWN, "false");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            attentionDialog = getStringPreference(AppPreferencesHelper.PREF_APP_NEW_UPDATE_DIALOG, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            infoLDate = getStringPreference(AppPreferencesHelper.PREF_INFO_LAST_MODIFIED_DATE, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            loginHint = getStringPreference(AppPreferencesHelper.PREF_LOGIN_HINT_SHOWN, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            dynamicInfoTab = getStringPreference(AppPreferencesHelper.PREF_DYNAMIC_INFO_TABS, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            showLoginWithOtpHint = getStringPreference(AppPreferencesHelper.PREF_SHOW_LOGIN_WITH_OTP_HINT, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);

        }
        try {
            jvwadh = getStringPreference(AppPreferencesHelper.PREF_JV_WADH_VER, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);


        }
        try {
            snackbarVersionCode = getStringPreference(AppPreferencesHelper.PREF_SNACKBAR_VERSION_CODE, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            recoveryOptionInterval = getStringPreference(AppPreferencesHelper.PREF_RECOVERY_OPTION_INTERVAL, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            recoveryOptionDaysInterval = getStringPreference(AppPreferencesHelper.PREF_RECOVERY_OPTION_DAYS_INTERVAL, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
                mpinInterval = getStringPreference(AppPreferencesHelper.PREF_MPIN_INTERVAL, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            mpinDaysInterval = getStringPreference(AppPreferencesHelper.PREF_MPIN_DAYS_INTERVAL, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            enableAadhaar = getStringPreference(AppPreferencesHelper.PREF_ENABLE_AADHAAR, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            aadhaarMsgInit = getStringPreference(AppPreferencesHelper.PREF_AADHAAR_MSG_INIT, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            aadhaarMsgProfile = getStringPreference(AppPreferencesHelper.PREF_AADHAAR_MSG_PROFILE, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            digiEn = getStringPreference(AppPreferencesHelper.PREF_DIGILOCKER_ENABLED, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            digiUserName = getStringPreference(AppPreferencesHelper.PREF_DIGI_FORGOT_USERNAME, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        try {
            digiPassword = getStringPreference(AppPreferencesHelper.PREF_DIGI_FORGOT_PSWD, "");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }



        SharedPreferences.Editor edit = mPrefs.edit();
        edit.clear();
        edit.commit();



        try {
            if (!token.equalsIgnoreCase("")) {
                writeStringPreference(AppPreferencesHelper.PREF_FCM_ID, token);
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }

        try {
            writeStringPreference(AppPreferencesHelper.PREF_SPLASH_CONFIG, splashConfig);
            writeStringPreference(AppPreferencesHelper.PREF_NODE, node);
            writeStringPreference(AppPreferencesHelper.PREF_APP_VERSION_CODE, appVerCode);
            writeStringPreference(AppPreferencesHelper.PREF_APP_VERSION_CODE_MESSAGE, appVerMsg);
            writeStringPreference(AppPreferencesHelper.PREF_APP_VERSION_FORCE_UPDATE, appForceUpdate);
            writeStringPreference(AppPreferencesHelper.PREF_TERMS_AND_CONDITIONS_URL, termsAndCondition);
            writeStringPreference(AppPreferencesHelper.PREF_PRIVACY_POLICY_URL, privacyPolicy);
            writeStringPreference(AppPreferencesHelper.PREF_OPEN_SOURCE_LICENSES, openSource);
            writeStringPreference(AppPreferencesHelper.PREF_USER_MANUAL, userManual);
            writeStringPreference(AppPreferencesHelper.PREF_FAQ, faq);
            writeStringPreference(AppPreferencesHelper.PREF_FACEBOOK_LINK, facebookLink);
            writeStringPreference(AppPreferencesHelper.PREF_GOOGLEPLUS_LINK, googleLink);
            writeStringPreference(AppPreferencesHelper.PREF_TWITTER_LINK, twitterLink);
            writeStringPreference(AppPreferencesHelper.PREF_SELECTED_LOCALE, selectedLocale);
            writeStringPreference(AppPreferencesHelper.PREF_DYNAMIC_TABS, dynamicTab);
            writeStringPreference(AppPreferencesHelper.PREF_FONT_SIZE, fontSize);
            writeStringPreference(AppPreferencesHelper.PREF_DEFAULT_TAB, defaultTab);
            writeStringPreference(AppPreferencesHelper.PREF_APP_SHARE_TEXT, shareTxt);
            writeStringPreference(AppPreferencesHelper.PREF_APP_EMAIL_SUPPORT, emailSupport);
            writeStringPreference(AppPreferencesHelper.PREF_APP_PHONE_SUPPORT, phoneSupport);
            writeStringPreference(AppPreferencesHelper.PREF_PERMISSION_DIALOG_SHOWN, permissionDialogShown);
            writeStringPreference(AppPreferencesHelper.PREF_APP_NEW_UPDATE_DIALOG, attentionDialog);
            writeStringPreference(AppPreferencesHelper.PREF_INFO_LAST_MODIFIED_DATE, infoLDate);
            writeStringPreference(AppPreferencesHelper.PREF_LOGIN_HINT_SHOWN, loginHint);
            writeStringPreference(AppPreferencesHelper.PREF_DYNAMIC_INFO_TABS, dynamicInfoTab);
            writeStringPreference(AppPreferencesHelper.PREF_SHOW_LOGIN_WITH_OTP_HINT, showLoginWithOtpHint);
            writeStringPreference(AppPreferencesHelper.PREF_JV_WADH_VER, jvwadh);
            writeStringPreference(AppPreferencesHelper.PREF_SNACKBAR_VERSION_CODE, snackbarVersionCode);
            writeStringPreference(AppPreferencesHelper.PREF_RECOVERY_OPTION_INTERVAL, recoveryOptionInterval);
            writeStringPreference(AppPreferencesHelper.PREF_RECOVERY_OPTION_DAYS_INTERVAL, recoveryOptionDaysInterval);
            writeStringPreference(AppPreferencesHelper.PREF_MPIN_INTERVAL, mpinInterval);
            writeStringPreference(AppPreferencesHelper.PREF_MPIN_DAYS_INTERVAL, mpinDaysInterval);
            writeStringPreference(AppPreferencesHelper.PREF_ENABLE_AADHAAR, enableAadhaar);
            writeStringPreference(AppPreferencesHelper.PREF_AADHAAR_MSG_INIT, aadhaarMsgInit);
            writeStringPreference(AppPreferencesHelper.PREF_AADHAAR_MSG_PROFILE, aadhaarMsgProfile);
            writeStringPreference(AppPreferencesHelper.PREF_DIGILOCKER_ENABLED, digiEn);
            writeStringPreference(AppPreferencesHelper.PREF_DIGI_FORGOT_USERNAME, digiUserName);
            writeStringPreference(AppPreferencesHelper.PREF_DIGI_FORGOT_PSWD, digiPassword);
           } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

}
