package com.negd.umangwebview.data.remote;


import com.negd.umangwebview.BuildConfig;

/**
 * All api end points need to be mention here
 */
    public final class ApiEndPoint {

    //BASE URL's
    public static final String DOMAIN = "https://" + BuildConfig.HOST;
    public static final String BASE_URL = DOMAIN + BuildConfig.BASE_URL_NEW;
//    public static final String BASE_URL_OPEN = DOMAIN + "/t/negd.gov.in/umang/coreapi/opn/" + BuildConfig.BASE_URL_CONTEXT;
    public static final String BASE_URL_TRAI = DOMAIN + "/t/negd.gov.in/umang/depttapi/traiApi/ws1/";
    public static final String BASE_URL_SKEY = "https://app.umangapp.in/umangApiShard/op1/";
    public static final String BASE_URL_BEARER = DOMAIN + "/umang/modsapi/refreshtkn/ws2/";
    public static final String BASE_URL_STATS = DOMAIN + "/t/negd.gov.in/api/core/service/stats/1.0/serviceStatus/";




    //Some api constants
    protected static final String authBearer = "Bearer fdd7e307-a1ed-3337-82a3-48e65eb33bbe";

    protected static final String authBearerTrai = "Bearer 5fe9e884-14e1-3294-943b-b6daab40b04e";
    public static final String authBearerNPS = "Bearer c3aefaa0-5a1b-36d4-9e9f-eace669e7925";
    public static final String authBearerEPFO = "Bearer 5fe9e884-14e1-3294-943b-b6daab40b04e";

    //API URL's
    public static final String SKEY_URL = BASE_URL_SKEY + "skey";
    public static final String INIT_URL = BASE_URL + "initv2";
    public static final String REFRESH_BEARER_URL = BASE_URL_BEARER + "tuk";
    public static final String LOGIN_MOBILE_NUMBER = BASE_URL + "lgv1";
    public static final String INIT_OTP = BASE_URL + "initotp";
    public static final String FETCH_HOME_SCREEN = BASE_URL + "fhomescreenv4";
    public static final String FETCH_INFO_HOME_SCREEN = BASE_URL + "finfoscreen";
    public static final String FETCH_BANNER_DATA = BASE_URL + "fhs";
    public static final String FETCH_TRANS_HISTORY = BASE_URL + "fustl";
    public static final String FETCH_TRANS_HISTORY_DETAIL = BASE_URL + "ftld";
    public static final String REGISTRATION = BASE_URL + "rgtv1";
    static final String VALIDATE_OTP_REGISTRATION = BASE_URL + "valotpu2";
    static final String FORGOT_MPIN = BASE_URL + "fmpin";
    static final String UPDATE_MPIN = BASE_URL + "umpinu2";
    static final String IVR_CALL = BASE_URL + "ivrotp";
    public static final String SERVICE_DIRECTORY_URL = BASE_URL + "fdirserv1";
    public static final String SERVICE_DIRECTORY_SUB_SERVICE_URL = BASE_URL + "fdirdeptser";
    public static final String FETCH_BIOMETRIC_DEVICES_LIST = BASE_URL + "biolist";
    public static final String FETCH_PROFILE = BASE_URL + "fp";
    public static final String UPDATE_PROFILE = BASE_URL + "updatep";
    public static final String FETCH_STATE_QUAL_OCCU = BASE_URL + "fstqu";
    public static final String FETCH_CITY = BASE_URL + "fcity";
    public static final String VERIFY_MPIN = BASE_URL + "vmpin";
    public static final String RESEND_EMAIL_VERIFICATION = BASE_URL + "reever";
    public static final String TRAI_MYCALL_FEEDBACK_URL = BASE_URL_TRAI + "submit";
    public static final String UPDATE_NOTIF_SETTINGS = BASE_URL + "uns";
    public static final String SEARCH_NEW_URL = BASE_URL + "applangsrch";
    public static final String SEARCH_KEYWORD_URL = BASE_URL + "appkeysearch";
    public static final String TRENDING_SEARCH = BASE_URL + "ftal";
    public static final String SEND_FEEDBACK = BASE_URL + "feedback";
    public static final String FETCH_DEPARTMENT_SERVICES = BASE_URL + "fthds";
    public static final String NPS_LOGOUT_API = DOMAIN + "/umang/depttapi/npsApi/ws1/flogout";
    public static final String EPFO_LOGOUT_API = DOMAIN + "/umang/depttapi/epfoApi/ws1/flogout";
    public static final String SET_UNSET_FAV = BASE_URL + "setunsetfav";
    public static final String UPDATE_GCM = BASE_URL + "updategcm";
    public static final String FETCH_DEPT_MSG = BASE_URL + "fdptmsg";
    public static final String UPDATE_MPIN_2 = BASE_URL + "umpinu2";
    public static final String SET_MPIN_WITH_TOKEN = BASE_URL + "smpinu2";
    public static final String CHANGE_MPIN = BASE_URL + "changempin";
    public static final String FETCH_LOGGEDIN_SESSIONS = BASE_URL + "fcurrentsess";
    public static final String DELETE_SESSIONS = BASE_URL + "delsess";
    public static final String DELETE_PROFILE = BASE_URL + "delete";
    public static final String FETCH_RECOVERY_OPTION = BASE_URL + "frecopt";
    public static final String UPDATE_SECURITY_QUESTION = BASE_URL + "usecques";
    public static final String UPDATE_MOBILE = BASE_URL + "umobile";
    public static final String FETCH_STATE_DATA_URL = BASE_URL + "fstdata";
    public static final String FETCH_USER_RATING = BASE_URL + "fur";
    public static final String RATE_DEPARTMENT = BASE_URL + "ratedpt";
    public static final String VALIDATE_OTP_ALTERNATIVE = BASE_URL + "valotpamem";
    public static final String REQUEST_OTP_ALTERNATIVE = BASE_URL + "initotpamem";
    public static final String GET_STATS = "https://apigw.umangapp.in/getServiceStats";
    public static final String VERIFY_SECURITY_QUESTION = BASE_URL + "vsecques";
    public static final String LOGOUT_PROFILE = BASE_URL + "logout";
    public static final String LINK_SOCIAL_ACCOUNT = BASE_URL + "linksa";
    public static final String UNLINK_ACCOUNT = BASE_URL + "uadhrv1";

    //Revamped api for home screen
    public static final String SKEY_NEW = "https://apigw.umangapp.in/coreapi/2.0/skey";
    public static final String INIT_NEW = "https://apigw.umangapp.in/coreapi/2.0/initv2";

//    public static final String ALL_SERVICES_NEW = BASE_URL_HOME + "allservices";
//    public static final String FLAGSHIP_SERVICES = BASE_URL_HOME + "flagshipschemes";
//    public static final String FAVORITE_SERVICES = BASE_URL_HOME + "userfavservice";
//    public static final String RECENT_SERVICES = BASE_URL_HOME + "userrecentservice";
//    public static final String NEW_SERVICES = BASE_URL_HOME + "newandupdatedservicecard";
//    public static final String TRENDING_SERVICES = BASE_URL_HOME + "trendingservicecard";
//    public static final String TOP_RATED_SERVICES = BASE_URL_HOME + "topratedservicecard";
//    public static final String SUGGESTED_SERVICES = BASE_URL_HOME + "suggestedservicecard";
//    public static final String ALL_BANNERS = BASE_URL_HOME + "fetchbanners";
//    public static final String GET_CATEGORIES = BASE_URL_HOME + "fetchcategory";
//    public static final String USER_PROFILE = BASE_URL_HOME + "fetchprofile";


    //Digilocker Api's URL
//    public static final String REFRESH_BEARER_URL_DIGI = BASE_URL_BEARER + "tukmods";
//    public static final String DIGI_INIT_AUTH = BASE_URL_DIGI_OPEN + "initauth";
//    public static final String DIGI_GET_ISSUED_DOCS = BASE_URL_DIGI_OPEN + "getlistissueddoc";
//    public static final String DIGI_DOWNLOAD_FILE = BASE_URL_DIGI + "doc";
//    public static final String DIGI_GET_UPLOADED_DOCS = BASE_URL_DIGI_OPEN + "getlistuploaddocs";
//    public static final String DIGI_LOGOUT = BASE_URL_DIGI_OPEN + "unlink";
//    public static final String DIGI_UPLOAD_FILE = BASE_URL_DIGI + "uploadfile";
//    public static final String DIGI_GET_ISSUERS_LIST = BASE_URL_DIGI_OPEN + "getlistofissuers";
//    public static final String DIGI_GET_LIST_DOC_ISSUERS_PROVIDER = BASE_URL_DIGI_OPEN + "getlistdocprovidedissuer";
//    public static final String DIGI_GET_SEARCH_FORM_FIELDS = BASE_URL_DIGI_OPEN + "getsearchparamdoc";
//    public static final String DIGI_PULL_DOCUMENT = BASE_URL_DIGI_OPEN + "pulldocument";
//    public static final String DIGI_REFRRESH_ACCESS_TOKEN = BASE_URL_DIGI + "rtkn";


    //DIGILOCKER Login API
//    public static final String DIGI_LOGIN_API=BASE_URL_DIGI_OPEN+ "initauthdigi";



    //BBPS API
//    public static final String BBPS_TOEKN_URL = BASE_URL_BBPS + "/getOauthToken";
//    public static final String BBPS_BILL_FETCH_URL = BASE_URL_BBPS + "/billfetchrequest";
//    public static final String BBPS_GET_BILLER_BY_CATEGORY_NAME_URL = BASE_URL_BBPS2 + "/dynbiller";
//    public static final String BBPS_GET_BILLER_BY_CATEGORY_REGION_URL = BASE_URL_BBPS + "/getBillerByRegion";
//    public static final String BBPS_BILL_PAYMENT_REQUEST_URL = BASE_URL_BBPS + "/billpaymentrequest";
//    public static final String BBPS_SEARCH_TRANSACTION_REQUEST_URL = BASE_URL_BBPS + "/status/billpayment";
//    public static final String BBPS_REGISTER_COMPLAINT_REQUEST_URL = BASE_URL_BBPS + "/raiseComplaint";
//    public static final String BBPS_COMPLAINT_STATUS_REQUEST_URL = BASE_URL_BBPS + "/checkComplaintStatus";
//    public static final String BBPS_GET_REGIONS_REQUEST_URL = BASE_URL_BBPS + "/getRegions";
//    public static final String BBPS_GET_OPERATOR_LIST_URL = BASE_URL_BBPS + "/getOperatorList";
//    public static final String BBPS_GET_OPERATOR_CIRCLE_LIST_URL = BASE_URL_BBPS + "/getCircleList";
//    public static final String BBPS_GET_USER_OPERATOR_CIRCLE_LIST_URL = BASE_URL_BBPS + "/getOperatorAndCircleInfo";
//    public static final String BBPS_GET_OPERATOR_PACK_LIST_URL = BASE_URL_BBPS + "/getPackType";
//    public static final String BBPS_GET_OPERATOR_PACK_INFO_URL = BASE_URL_BBPS + "/getMobilePlans";

    //dynamic form api
    public static final String DYNAMIC_FORM_API = "https://stgweb.umang.gov.in/cms/dynamic-form-builder";
    public static final String DYNAMIC_FORM_API2 = DOMAIN +"/umangmasterdata/dynamicForm/getData";


    //JP API'S
    public static final String JP_OTP_GENERATE_API="https://web.umang.gov.in/jp/api/deptt/createSession";
    public static final String JP_OTP_VARIFY_API="https://web.umang.gov.in/jp/api/deptt/validateOtp";
    public static final String JP_PENSION_TYPE_API="https://web.umang.gov.in/jp/api/deptt/pensionType";
    public static final String JP_SANCTIONING_AUTHORITY_API="https://web.umang.gov.in/jp/api/deptt/sanctionAuth";
    public static final String JP_DISTRIBUTED_AGENCY_API="https://web.umang.gov.in/jp/api/deptt/distAgency";
    public static final String JP_BANK_NAME_API="https://web.umang.gov.in/jp/api/deptt/bankName";
    public static final String JP_GENRATE_CERTIFICATE_API="https://web.umang.gov.in/jp/api/deptt/genJeevanPramaan";
    public static final String JP_VIEW_CERTIFICATE_SEND_OTP_API="https://web.umang.gov.in/jp/api/deptt/viewlc";
    public static final String JP_VIEW_CERTIFICATE_OTP_VERIFICATION_API="https://web.umang.gov.in/jp/api/deptt/lcvalidate";
    public static final String JP_GET_CERTIFICATE_PDF_FILE_API = "https://web.umang.gov.in/jp/api/deptt/jppdf";
    private ApiEndPoint(){
        // This class is not publicly instantiable
    }
}
