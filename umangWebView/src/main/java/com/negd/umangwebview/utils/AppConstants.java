package com.negd.umangwebview.utils;



public final class AppConstants {

    public static final String DB_NAME = "UMANG_DB_NEW";

    public static final String PREF_NAME = "_preferences_new";

    public static final String SECURE_PREF_NAME ="_secure_umang_pref_new";

    protected static final String ALGORITHM = "AES";

    protected static final int ITERATIONS = 2;

    public static final String LOGOUT_USER_ERROR_CODE = "90001";

    //slider constants
    public static final String BANNER_ID="banner_id";
    public static final String ACTION_TYPE="action_type";
    public static final String ACTION_URL="action_url";
    public static final String DESCRIPTION="desc";
    public static final String SERVICE_TYPE="service_type";

    public static final String FILE_NOT_FOUND = "FNF";
    public static final String NON_PDF_FILE = "NPF";
    public static final String PERMISSION_NOT_GRANTED = "PNG";
    public static final String IO_EXCEPTION = "IOE";
    public static final String PDF_FILE_SIZE_EXCEEDED = "PLIM";
    public static final String IMAGE_SIZE_EXCEEDED = "ILIM";
    public static final String IMAGE_SIZE_TOO_SMALL = "ISTM";


    //digilocker constants
    public static final String redirectURL = "https://localhost/callback";
    public static final String ISSUED_DOC_TYPE = "I";
    public static final String UPLOADED_DOC_TYPE = "U";


    //callback results constants
    public static final int INTENT_DIGILOCKER_FOLDER = 2050;
    public static final int INTENT_DIGILOCKER_LOGINFLOW = 2051;
    public static final int INTENT_DIGILOCKER_LOGINFLOW_UPLOAD = 2052;
    public static final int INTENT_FILTER_SERVICE = 2053;
    public static final int INTENT_FILTER_CATEGORY = 2054;


    public static final String ALL = "all";
    public static final String CENTRAL = "central";
    public static final String STATE = "state";
    public static final String CENTRAL_GOVERNMENT_SERVICE_ID = "99";
    public static final String KEY_STATE_DATA = "data";

    //service chsannels
    public static final String CHANNEL_ID_PROMO = "in.gov.umang.negd.g2c.notif.channel.promo";
    public static final String CHANNEL_ID_TRANS = "in.gov.umang.negd.g2c.notif.channel.trans";
    public static final String CHANNEL_ID_DOWNLOADS = "in.gov.umang.negd.g2c.notif.channel.downloads";
    public static final String CHANNEL_ID_OTHERS = "in.gov.umang.negd.g2c.notif.channel.others";
    public static final String CHANNEL_ID_LIVE_CHAT = "in.gov.umang.negd.g2c.notif.channel.livechat";

    // SET intent for EULA
    public static final String EULA_I_AGREE = "agree";

    public static final String INTENT_BOOKPATH = "book_path";
    public static final String INTENT_ISREFLOWABLE = "is_reflowable";

    public static final int SET_RESULT = 100;
    public static final int INTENT_LOGIN_OTP_SCREEN = 101;
    public static final int INTENT_STATE_SCREEN = 201;
    public static final int INTENT_EULA_SCREEN = 202;
    public static final int INTENT_PROFILE = 1213;
    public static final int INTENT_LANGUAGE_SCREEN = 204;
    public static final String FROM_SCREEN = "Splash";




    private int SELECT_QUALIFICATION_REQUEST = 2010;
    private int SELECT_OCCUPATION_REQUEST = 2002;
    private int SELECT_STATE_REQUEST = 2003;
    private int SELECT_DISTRICT_REQUEST = 2004;
    public static final int VERIFY_MPIN_RESULT_CODE = 2001;
    public static int RESULT_VALIDATE_ALT_MOB = 1001;
    public static final int CAMERA_INTENT_CODE = 2016;
    public static final int GALLERY_INTENT_CODE = 2019;
    public static int RESULT_NEW_PHONE_VERIFICATION = 12;


    //Rating savedInstance for Side Activity
    public static final String RATING_SAVE_INSTANCE = "rating_saved_instance";
    public static final String RATING_PLAYSTORE_INSTANCE = "rating_playstore";


    public static final String TERMS_AND_CONDITION_URL = "https://web.umang.gov.in/uaw/tnc.html";
    public static final String PRIVACY_POLICY_URL = "https://web.umang.gov.in/uaw/pvcp.html";
    public static final String REFUND_POLICY_URL = "https://stgweb.umang.gov.in/uaw/cr/v1/en/index.htm";
    public static final String OPEN_SOURCE_URL = "https://web.umang.gov.in/uaw/ops.html";
    public static final String USER_MANUAL_URL = "https://web.umang.gov.in/uaw/usrman.html";
    public static final String FAQ_URL = "https://web.umang.gov.in/uw/#/mobFaqsHome";



    public static final String REC_TYPE = "rectype";
    public static final String MOBILE_NUMBER = "mobileNumberStr";
    public static final String ALTER_MOBILE_NUMBER = "amno";
    public static final String EMAIL = "email";

    public static final String QUESTION_ID = "quesId";
    public static final String ANSWER = "ans";
    public static final String QUESTION_ANS_LIST = "quesAnsList";
    public static final String IVR = "ivr";


    // Actions
    public static final String LOGOUT_USER = "logout_user";
    public static final String FINISH = "finish";

    //PATTERN
    public static String MobilePattern = "[0-9]{10}";
    public static String MPINPattern = "[0-9]{4}";

    public static final int SMS_PERMISSION = 1;

    public static final String IMAGE = "image";

    //    ImagePickerRequest
    public static int CAMERA_REQUEST = 1888;
    public static int GALLERY_REQUEST = 1122;

    public static final String TYPE_LOGIN_OTP= "login_otp";
    public static final String TYPE_REGISTER_OTP= "regsiter_otp";



    public static final String TAB_STATE_ID = "tab_state_id";
    public static final String TAB_STATE_NAME = "tab_state_name";
    public static final String SOURCE_TAB = "source_tab";
    public static final String SOURCE_SECTION = "source_section";
    public static final String SOURCE_STATE = "source_state";
    public static final String SOURCE_BANNER = "source_banner";

    public static final String KEY_MOBILE_WEB_IDENTIFIER = "Web-View";
    public static final String APP_VER = "ver";


    public static final int FACEBOOK_IMAGE_INTENT_CODE = 2029;
    public static final int SELECT_SERVICE_INTENT_CODE = 2045;
    public static final int SELECT_CATEGORY_INTENT_CODE = 2046;
    public static final int TRANSACTION_FILTER_INTENT = 2049;


    //permission variables
    public static final int MY_PERMISSIONS_CAMERA_AND_STORAGE = 1101;
    public static final int MY_PERMISSIONS_LOCATION = 1102;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE = 1103;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORATE = 1104;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORATE_BOOKS = 1105;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORATE_DIGI = 1106;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORATE_GALLERY = 1107;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_DELETE = 1108;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_MORPHO = 1109;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NPS_FILE_SAVE = 1110;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_ISSUED_DOC = 1111;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_UPLOAD_DOC = 1112;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORATE_FOR_UPLOAD_DOC = 1113;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_DOC = 1114;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_DOC_BYTES = 1115;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN = 1116;
    public static final int MY_PERMISSIONS_RECORD_AUDIO = 1117;
    public static final int MY_PERMISSIONS_RECORD_VIDEO = 1118;
    public static final int MY_PERMISSIONS_SELECT_IMAGES = 1119;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_SHARE = 1120;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_PDF = 1121;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_PHOTO = 1122;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_SIGN = 1123;
    public static final int BIOMETRIC_DEPT_AUTH = 1124;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_FILE = 1125;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_EXCEL_DOC = 1126;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORATE_COMMON_METHOD = 1127;
    public static final int PERMISSIONS_READ_CALL_LOGS_COMMON_METHOD = 1128;
    public static final int PERMISSIONS_READ_SMS_LOGS_COMMON_METHOD = 1129;
    public static final int PERMISSIONS_SEND_SMS_COMMON_METHOD = 1130;
    public static final int PERMISSIONS_READ_SMS_TRAI_METHOD = 1131;
    public static final int BIO_DEVICE_REGISTERATION_STEP = 1132;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_MORPHO_RD = 1133;
    public static final int PERMISSIONS_READ_SMS_OTP_TRAI_METHOD = 1134;
    public static final int PERMISSIONS_READ_PHONE_STATE_METHOD2 = 113499;
    public static final int PERMISSIONS_READ_PHONE_STATE_METHOD = 1135;
    public static final int MY_PERMISSIONS_SMS_AND_PHONE = 1136;
    public static final int MY_PERMISSIONS_READ_SMS_AND_PHONE = 1137;
    public static final int INTENT_CALL_TRAI_CALL_LOG_ACTIVITY = 1138;
    public static final int INTENT_SMS_TRAI_SMS_LOG_ACTIVITY = 1139;
    public static final int PERMISSIONS_READ_CALL_LOGS_COMMON_METHOD_TRAI = 1140;
    public static final int PERMISSIONS_READ_SMS_LOGS_COMMON_METHOD_TRAI = 1141;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_DOWNLOAD_PDF = 1142;
    public static final int INTENT_CALL_RATE_TRAI_RETURN = 1143;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NDL_DELETE = 1144;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NDL_DOWNLOAD = 1145;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NDL_OPEN = 1146;
    public static final int INTENT_OPTIMIE_DIALOG_TRAI = 1147;
    public static final int PERMISSIONS_MY_CALL_ENABLE = 1148;
    public static final int INTENT_MHA_FILE = 1149;
    public static final int PERMISSIONS_CHECK_REGISTERED_NUMBRE = 1150;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_FOR_DOWNLOAD_PDF_IN_DOWNLOAD = 1151;
    public static final int INTENT_MHA_FILE_WITH_NAME = 1152;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_DIGILOCKER = 1153;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_DOC_NATIVE = 1154;

    public static final int NOTIFICATION_FILTER_REQUEST_CODE = 10;

    public static final int UPDATE_QUES_REQUEST_CODE = 1011;

    public static final int ACCOUNT_RECOVERY_REQUEST_CODE = 1021;

    public static final int SELECT_DEPARTMENT_REQUEST_CODE = 1012;

    public static final int BARCODE_SCAN_REQUEST_CODE = 3310;

    public static final int DIGI_GET_DOC_REQUEST_CODE = 3710;

    public static final int DIGI_UPLOAD_DOC_REQUEST_CODE = 3711;

    public static final int APP_UPDATE_CODE = 37112;

    public static int DIGI_LOGIN_REPONSE = 12121;


    public static final String DONT_DELETE_NOTIF = "DontDeleteNotif";

    public static final String FBREADER_PACKAGE = "org.geometerplus.zlibrary.ui.android";

    //book download constants
    public static final String BOOK_ID = "bookId";
    public static final String BOOK_CLASS = "bookCls";
    public static final String BOOK_IMAGE = "bookImage";
    public static final String BOOK_LANG = "bookLng";
    public static final String BOOK_NAME = "bookName";
    public static final String BOOK_SUBJECT = "bookSub";
    public static final String CH_ID = "id";
    public static final String CH_CLASS_BOOK = "classBook";
    public static final String CH_EPUB_LINK = "epubLink";
    public static final String CH_TITLE = "chapterTitle";
    public static final String CH_NO = "chapterNo";
    public static final String CH_ENM_LAYOUT = "enmLayout";
    public static final String CH_ALL_DATA = "allData";
    public static final String CH_ENM_TYPE = "enmType";
    public static final String CH_HASH_KEY = "$$hashKey";
    public static final String CH_PATH = "path";
    public static final String CH_IS_DOWNLOADED = "isDownloaded";
    public static final String BOOK_CATEGORY = "bookCategory";
    public static final String BOOK_SUB_CATEGORY = "category";
    public static final String TITLE_TEXT = "titletext";
    public static final String MPIN = "mpin";

    public static final int DOWNLOAD_NOTIFICATION_ID = 5001;
    public static final int DOWNLOAD_NDLI_DOC_NOTIFICATION_ID = 6001;
    public static final boolean DOWNLOADING_CANCELLED = false;
    public static final int TRAI_MY_CALL_NOTIFICATION_ID = 7001;

    //digi constatns
    public static String DIGI_DEPT_ID = "0";
    public static String DIGI_SERVICE_ID = "0";
    public static String DIGI_SUB_SERVICE_ID = "0";

    //JP Device Info
    public static final String RD_SLD = "rdsId";
    public static final String RD_VER = "rdsVer";
    public static final String DPLD = "dpId";
    public static final String DC = "dc";
    public static final String MI = "mi";
    public static final String MC = "mc";
    public static final String AMEM_FLAG = "amemflag";
    public static final String HMAC = "hmac";
    public static final String SKEY = "skey";
    public static final String DATA = "pidData";
    public static final String CI = "ci";
    public static final String KI = "ki";
    public static final String TS = "ts";
    public static final String AUTHTYPE = "authType";
    public static final String TID = "tid";
    public static final String SIZE = "size";
    public static final String UDC = "udc";

    public static final String SIM_ICC = "sim_icc";

    public static final String DIGILOCKER_URI = "umang://digilocker";
    public static final String BBPS_URI = "umang://bbps";
    public static final String BBPS_LANDING_URL = "https://web.umang.gov.in/bbps/api/deptt/bbpsHtml";
    public static final String BBPS_LANDING_STG_URL = "https://stgweb.umang.gov.in/bbps/api/deptt/bbpsHtml";

    public static final String REFRESH_SCREEN = "refresh";
    public static final String ENGLISH_LOCALE = "en";
    public static final String HINDI_LOCALE = "hi";
    public static final String ASSAMESE_LOCALE = "as";
    public static final String BENGALI_LOCALE = "bn";
    public static final String GUJARATI_LOCALE = "gu";
    public static final String KANNADA_LOCALE = "kn";
    public static final String MALAYALAM_LOCALE = "ml";
    public static final String MARATHI_LOCALE = "mr";
    public static final String ORIYA_LOCALE = "or";
    public static final String PUNJABI_LOCALE = "pa";
    public static final String TAMIL_LOCALE = "ta";
    public static final String TELUGU_LOCALE = "te";
    public static final String URDU_LOCALE = "ur";

    public static final String SANSKRIT_LOCALE = "sk";
    public static final String NEPALI_LOCALE = "ne";
    public static final String MANIPURI_LOCALE = "ma";
    public static final String MAITHALI_LOCALE = "mi";
    public static final String SINDHI_LOCALE = "si";
    public static final String SANTHALI_LOCALE = "sn";
    public static final String KOKANI_LOCALE = "ko";
    public static final String KASHMIRI_LOCALE = "ka";
    public static final String DOGRI_LOCALE = "dg";
    public static final String BODO_LOCALE = "bo";

    public static final float FONT_SMALL = 0.85f;
    public static final float FONT_NORMAL = 1.0f;
    public static final float FONT_LARGE = 1.15f;


}
