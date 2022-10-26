package com.negd.umangwebview.utils;

public class Constants {

   //constants for web activity intent
   public static final String DEPT_URL="url";
   public static final String DEPT_NAME="name";
   public static final String DEPT_ID="deptId";
   public static final String SERVICE_ID="serviceId";
   public static final String BACK_BUTTON_COLOR="backButtonColor";
   public static final String HEADER_TEXT_COLOR="headerTextColor";
   public static final String HEADER_COLOR="headerColor";
   public static final String LOADER_COLOR="loaderColor";
   public static final String SWIPE_LOADER_COLOR="swipeLoaderColor";

   //choose document codes
   public static final int REQUEST_IDENTITY_PROOF = 666;
   public static final int REQUEST_PHOTO_PROOF = 667;
   public static final int REQUEST_SIGNATURE_FILE = 668;
   public static final int REQUEST_PDF_FILE = 669;
   public static final int REQUEST_PASSPORT_PHOTO = 670;
   public static final int REQUEST_COMMON_DOCUMENT = 671;
   public static final int REQUEST_COMMON_CAMERA_PHOTO = 672;
   public static final int REQUEST_COMMON_GALLERY_PHOTO = 673;
   public static final int BIO_RD_INFO_REQUEST_CODE = 711;
   public static final int DEVICE_SCAN_REQUEST_CODE = 744;
   public static final int BIOMETRIC_DEVICE_INFO = 010102;
   public static int DIGI_AUTH_RESULT_CODE = 6000;
   public static final int BARCODE_SCAN_REQUEST_CODE = 3310;




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


   //Preference constants
   public static String PREF_WEB_KEY_VALUE = "PrefWebKeyValue";
   public static String PREF_CAMERA_IMAGE_URI = "PrefCameraImgURI";
   public static String PREF_REQUEST_IMAGE_FOR = "PrefRequestImageFor";

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

   public static final String FILE_NOT_FOUND = "FNF";
   public static final String NON_PDF_FILE = "NPF";
   public static final String PERMISSION_NOT_GRANTED = "PNG";
   public static final String IO_EXCEPTION = "IOE";
   public static final String PDF_FILE_SIZE_EXCEEDED = "PLIM";
   public static final String IMAGE_SIZE_EXCEEDED = "ILIM";
   public static final String IMAGE_SIZE_TOO_SMALL = "ISTM";
   public static final int DIGI_GET_DOC_REQUEST_CODE = 3710;
   public static final int DIGI_UPLOAD_DOC_REQUEST_CODE = 3711;


   public static final int MY_PERMISSIONS_REQUEST_CODE = 99;
   public static final String FBREADER_PACKAGE = "org.geometerplus.zlibrary.ui.android";

}
