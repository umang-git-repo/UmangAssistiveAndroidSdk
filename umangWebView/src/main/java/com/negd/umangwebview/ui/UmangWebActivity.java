package com.negd.umangwebview.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.MailTo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.negd.umangwebview.BuildConfig;
import com.negd.umangwebview.R;
import com.negd.umangwebview.UmangAssistiveAndroidSdk;
import com.negd.umangwebview.callbacks.CameraInterface;
import com.negd.umangwebview.callbacks.CommonInterface;
import com.negd.umangwebview.callbacks.ContactInterface;
import com.negd.umangwebview.callbacks.DownloadInterface;
import com.negd.umangwebview.callbacks.LocationInterface;
import com.negd.umangwebview.callbacks.MHAInterface;
import com.negd.umangwebview.databinding.ActivityUmangWebBinding;
import com.negd.umangwebview.utils.AudioRecord;
import com.negd.umangwebview.utils.Constants;
import com.negd.umangwebview.utils.FileUtils;
import com.negd.umangwebview.utils.ImageSelect;
import com.negd.umangwebview.utils.ImageUtils;
import com.negd.umangwebview.utils.Utils;
import com.negd.umangwebview.utils.VideoRecord;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import id.zelory.compressor.Compressor;


public class UmangWebActivity extends AppCompatActivity implements CustomDialog.DialogButtonClickListener {

    private static final String TAG = UmangWebActivity.class.getSimpleName();
    private String deptUrl=null;
    private String deptId=null;
    private String serviceId=null;

    private ActivityUmangWebBinding binding;
    private ProgressDialog mProgressDialog;

    //custom webview
    public AgentWeb mAgentWeb;

    //callbacks specific params
    public static String callBackSuccessFunction = "";
    public static String callBackFailureFunction = "";
    public static String requestImageFor = "";
    public static String imageSelectedPath = "";
    public static String fileSizeStr = "200";

    public String inputJson;
    public String userId;

    private View videoView;
    private android.webkit.WebChromeClient.CustomViewCallback videoViewCallback;
    private CustomWebChromeClient customWebchromeClient;
    private WebViewClient mWebViewClient;

    protected int mRequestCodeFilePicker = 51426;

    private String loaderColor="#00599f";
    private boolean isError=false;
    private String lastURL = "";


    //image crop variables
    public int CropImageId = 0;
    public String MaxSize = "300";
    public boolean mIsCrop;
    public int mCropHeight;
    public int mCropWidth;
    public String mTypes="";
    public String mCropType;
    public String mJsonParams;


    /**
     * File upload callback for platform versions prior to Android 5.0
     */
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    /**
     * File upload callback for Android 5.0+
     */
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;

    protected String mUploadableFileTypes = "*/*";

    //location
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;


    public CommonInterface commonInterface;
    public CameraInterface cameraInterface;
    public LocationInterface locationInterface;
    public ContactInterface contactInterface;
    public DownloadInterface downloadInterface;
    public MHAInterface mhaInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityUmangWebBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        //set toolbar
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //back button listener
        binding.imgLogo.setOnClickListener(view -> onBackPressed());

        //fused location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //get intent values and set UI
        setIntentValues();

        //custom web chrome client
        customWebchromeClient=new CustomWebChromeClient();

        //adding version code
        binding.txtVersion.setText("Powered by UMANG");

        binding.txtVersion2.setText("Version : "+BuildConfig.VERSION_NAME);

        //clear focus if activity recreating
        clearFocus();

        //set web view client
        setWebViewClient();

        //set webview
        setWebView();


    }

    @Override
    public void onBackPressed() {
        if(mAgentWeb!=null && mAgentWeb.getWebCreator()!=null) {

            mAgentWeb.getWebCreator().getWebView().requestFocus();

            WebView webView = mAgentWeb.getWebCreator().getWebView();
            if (inCustomView()) {
                hideCustomView();

            } else if (lastURL != null) {
                try {
                    //Log.d(TAG, webView.getUrl().toString() + "====" + lastURL);
                    if (webView.isFocused() && webView.canGoBack()) {

                        if (webView.getUrl().toString().equalsIgnoreCase(lastURL) || webView.getUrl().toString().equalsIgnoreCase(lastURL+"/")) {
                            finish();
                        } else {

                            try{
                                    webView.goBack();
                            }catch (Exception ex){
                                webView.goBack();
                            }
                        }


                    } else {
                        finish();
                    }
                } catch (Exception e) {
                    //AppLogger.e(e.toString());
                    finish();
                }
            } else {
                finish();
            }
        }
    }

    public void hideCustomView() {
        customWebchromeClient.onHideCustomView();
    }

    /**
     * Method to set the intent value and configuration for the UI
     */
    private void setIntentValues(){
        Intent intent=getIntent();

        if(intent!=null){

            if(intent.hasExtra(Constants.DEPT_URL)){
                this.deptUrl=intent.getStringExtra(Constants.DEPT_URL);
            }

            if(intent.hasExtra(Constants.DEPT_ID)){
                this.deptId=intent.getStringExtra(Constants.DEPT_ID);
            }

            if(intent.hasExtra(Constants.SERVICE_ID)){
                this.serviceId=intent.getStringExtra(Constants.DEPT_ID);
            }

            if(intent.hasExtra(Constants.DEPT_NAME)){
                binding.headerTxt.setText(intent.getStringExtra(Constants.DEPT_NAME));
            }

            if(intent.hasExtra(Constants.BACK_BUTTON_COLOR)){
                binding.imgLogo.setColorFilter(Color.parseColor(getIntent().getStringExtra(Constants.BACK_BUTTON_COLOR)), PorterDuff.Mode.SRC_IN);
            }

            if(intent.hasExtra(Constants.HEADER_TEXT_COLOR)){
                binding.headerTxt.setTextColor(Color.parseColor(getIntent().getStringExtra(Constants.HEADER_TEXT_COLOR)));
            }

            if(intent.hasExtra(Constants.HEADER_COLOR)){
                binding.toolBar.setBackgroundColor(Color.parseColor(getIntent().getStringExtra(Constants.HEADER_COLOR)));
            }

            if(intent.hasExtra(Constants.LOADER_COLOR)){
                loaderColor=intent.getStringExtra(Constants.LOADER_COLOR);
            }


        }else{
            Toast.makeText(this,"something went wrong !! please check your configuration .",Toast.LENGTH_LONG).show();
        }
    }


    @SuppressLint("NewApi")
    protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond, final boolean allowMultiple) {
        if (mFileUploadCallbackFirst != null) {
            mFileUploadCallbackFirst.onReceiveValue(null);
        }
        mFileUploadCallbackFirst = fileUploadCallbackFirst;

        if (mFileUploadCallbackSecond != null) {
            mFileUploadCallbackSecond.onReceiveValue(null);
        }
        mFileUploadCallbackSecond = fileUploadCallbackSecond;

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);

        i.setType("image/*");
        if (allowMultiple) {
            if (Build.VERSION.SDK_INT >= 18) {
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
        }

        i.setType(mUploadableFileTypes);

        startActivityForResult(Intent.createChooser(i, "Choose a file"), mRequestCodeFilePicker);
    }

    public boolean inCustomView() {
        return (videoView != null);
    }

    /**
     * clear webview focus
     */
    private void clearFocus(){
        try{

            if(mAgentWeb !=null)
                mAgentWeb.getWebCreator().getWebView().setOnKeyListener(new View.OnKeyListener(){
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((keyCode == KeyEvent.KEYCODE_BACK) && mAgentWeb.getWebCreator().getWebView().canGoBack()) {
                            mAgentWeb.getWebCreator().getWebView().clearFocus();
                            //onBackPressed();
                            return false;
                        }
                        return false;
                    }
                });
        }catch (Exception ex){
            Log.e("Error clear focus","error in clear focus");
        }
    }


    /**
     * Set chrome client
     */
    private void setWebViewClient(){
        mWebViewClient= new WebViewClient(){

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if(error.getDescription().toString().equalsIgnoreCase("net::ERR_INTERNET_DISCONNECTED"))
                    isError=true;


                else if( error.getDescription().toString().equalsIgnoreCase("net::ERR_CACHE_MISS"))
                {

                    if(Utils.isNetworkConnected(UmangWebActivity.this)) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(UmangWebActivity.this);
                builder.setMessage("SSL Certificate error . Do you want to continue ?");
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();

            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                //isError=true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {

                String url=request.getUrl().toString().trim();

                if (url.contains("https://www.google.com/maps/")) {
                    Uri IntentUri = Uri.parse(url);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, IntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    return true;
                }

                else if (url.startsWith("http:") || url.startsWith("https:")) {
                    webView.loadUrl(url);
                    return true;
                }else if (url.startsWith("tel:")) {
                    Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(tel);
                    return true;
                }else if (url.startsWith("mailto:")) {
                    MailTo mt = MailTo.parse(url);
                    Intent mail = new Intent(Intent.ACTION_SEND);
                    mail.setType("application/octet-stream");
                    mail.putExtra(Intent.EXTRA_EMAIL, new String[]{mt.getTo()});
                    mail.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
                    mail.putExtra(Intent.EXTRA_TEXT, mt.getBody());
                    startActivity(mail);
                    return true;
                }else if (url.startsWith("geo:")) {
                    Uri gmmIntentUri = Uri.parse(url);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    try {
                        startActivity(mapIntent);
                    } catch (Exception e) {
                        Toast.makeText(UmangWebActivity.this, "Map app not found", Toast.LENGTH_SHORT).show();
                        Log.e("Map exception",e.toString());
                    }
                    return true;
                }
                return true;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view,url,favicon);
                //do you  work
                if(isError){
                    isError=false;

                    if(Utils.isNetworkConnected(UmangWebActivity.this)) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }

                Log.i("Info", "BaseWebActivity onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                try {
                    view.setVisibility(View.VISIBLE);
                    if (lastURL.length() == 0 || url.contains("#/")) {
                        lastURL = url;
                    }
                } catch (Exception e) {
                    Log.i("Info", "BaseWebActivity onPageStarted");
                }
                super.onPageFinished(view, url);
            }
        };
    }


    /**
     * Method to set webview dynamically to top main layout
     */
    private void setWebView(){

        mAgentWeb= AgentWeb.with(this)
                .setAgentWebParent(binding.main, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(Color.parseColor(loaderColor))
                .setMainFrameErrorView(R.layout.web_error_page, R.id.retryWebBtn)
                .setWebChromeClient(customWebchromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecurityType(AgentWeb.SecurityType.DEFAULT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(deptUrl);


        commonInterface = new CommonInterface(this);
        cameraInterface = new CameraInterface(this);
        locationInterface = new LocationInterface(this);
        contactInterface = new ContactInterface(this);
        downloadInterface= new DownloadInterface(this);
        mhaInterface= new MHAInterface(this);

        //set java script interfaces
        Map<String, Object> callbackInterfaces = new HashMap();
        callbackInterfaces.put("Common", commonInterface);
        callbackInterfaces.put("Camera", cameraInterface);
        callbackInterfaces.put("Location", locationInterface);
        callbackInterfaces.put("Contacts", contactInterface);
        callbackInterfaces.put("FileDownload", downloadInterface);
        callbackInterfaces.put("MHA", mhaInterface);

        mAgentWeb.getJsInterfaceHolder().addJavaObjects(callbackInterfaces);

        //check for IMD
        if (deptUrl.contains("imd"))
            mAgentWeb.getWebCreator().getWebView().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        else
            mAgentWeb.getWebCreator().getWebView().setLayerType(View.LAYER_TYPE_HARDWARE, null);

        mAgentWeb.getAgentWebSettings().getWebSettings().setJavaScriptEnabled(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setDomStorageEnabled(true);


        mAgentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);

        mAgentWeb.getAgentWebSettings().getWebSettings().setAppCacheEnabled(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setAppCachePath(
                getApplicationContext().getCacheDir().getAbsolutePath());


        mAgentWeb.getAgentWebSettings().getWebSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        if(!Utils.isNetworkConnected(this)){
            mAgentWeb.getAgentWebSettings().getWebSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }


        mAgentWeb.getAgentWebSettings().getWebSettings().setGeolocationEnabled(true);

    }

    public void downloadBase64File(String fileName, String fileDataByte) {

        try {

            //input stream from file bytes
            InputStream stream = new ByteArrayInputStream(Base64.decode(fileDataByte.getBytes(), Base64.DEFAULT));

            //storage directory for save
            File storageDir = FileUtils.getBaseStorage2(this);

            //create umang directory for download if not exist
            File umangFolder = new File(storageDir, "UMANG");
            if (!umangFolder.exists()) {

                umangFolder.mkdirs();
            }

            //create download file
            File file = new File(umangFolder + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            try {
                try {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = stream.read(buffer)) != -1) {
                        fos.write(buffer, 0, read);
                    }
                    fos.flush();
                    Toast.makeText(this,getString(R.string.download_success) + " : " + file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                } catch (Exception ed) {

                } finally {
                    fos.close();

                }
            } catch (Exception e) {

                try {
                    if (file != null) {
                        if (file.exists()) {
                            file.delete();

                        }
                    }
                } catch (Exception ec) {

                }
            } finally {
                fos.close();

            }

        } catch (Exception e) {

        }
    }

    /**
     * Method to download Base64 PDF file
     *
     * @param fileName     -file name
     * @param fileDataByte -file dataa in bytes
     * @param type         - file type
     */
    public void downloadBase64FileForPDF(String fileName, String fileDataByte, String type) {
        try {
            InputStream stream = new ByteArrayInputStream(Base64.decode(fileDataByte.getBytes(), Base64.DEFAULT));
            File storageDir = FileUtils.getBaseStorage2(this);

            File umangFolder = new File(storageDir, "UMANG");
            if (!umangFolder.exists()) {

                umangFolder.mkdirs();
            }

            File file = new File(umangFolder + File.separator + fileName.replaceAll("/", "_"));
            FileOutputStream fos = new FileOutputStream(file);
            try {

                try {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = stream.read(buffer)) != -1) {
                        fos.write(buffer, 0, read);
                    }
                    fos.flush();
                    if (file != null) {

                        if (type.equalsIgnoreCase("excel")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);

                            Uri uri = FileProvider.getUriForFile(this,
                                    getPackageName() + ".fileprovider",
                                    file);
                            intent.setDataAndType(uri, "application/vnd.ms-excel");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(this,getString(R.string.no_app_found),Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri uri = FileProvider.getUriForFile(this,
                                    getPackageName() + ".fileprovider",
                                    file);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(uri, "application/pdf");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            try {
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(this,getString(R.string.no_app_found),Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                } catch (Exception e) {
                    try {
                        if (file != null) {
                            if (file.exists()) {
                                file.delete();

                            }
                        }
                    } catch (Exception ed) {

                    }
                } finally {
                    fos.close();


                }
            } catch (Exception e) {

                try {
                    if (file != null) {
                        if (file.exists()) {
                            file.delete();

                        }
                    }
                } catch (Exception ec) {

                }
            } finally {
                fos.close();

            }

        } catch (Exception e) {

        }
    }


    /**
     * Method to download file bytes for PDF
     *
     * @param fileName--    file name
     * @param fileDataByte- bytes array of pdf
     */
    public void downloadFileBytesPDF(String fileName, byte[] fileDataByte) {
        try {

            InputStream stream = new ByteArrayInputStream(fileDataByte);
            File storageDir = FileUtils.getBaseStorage2(this);


            File umangFolder = new File(storageDir, "UMANG");
            if (!umangFolder.exists()) {

                umangFolder.mkdirs();
            }
            File file = new File(umangFolder + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            try {

                try {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = stream.read(buffer)) != -1) {
                        fos.write(buffer, 0, read);
                    }
                    fos.flush();
                    Toast.makeText(this,getString(R.string.download_success)+ " : " + file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                    if (file != null) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri uri = FileProvider.getUriForFile(this,
                                getPackageName() + ".fileprovider",
                                file);
                        intent.setDataAndType(uri, "application/pdf");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(this,getString(R.string.no_app_found),Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception ed) {

                    try {
                        if (file != null) {
                            if (file.exists()) {
                                file.delete();

                            }
                        }
                    } catch (Exception e) {

                    }

                } finally {
                    fos.close();

                }
            } catch (Exception e) {

                try {
                    if (file != null) {
                        if (file.exists()) {
                            file.delete();

                        }
                    }
                } catch (Exception ec) {

                }
            } finally {
                fos.close();

            }

        } catch (Exception e) {

        }
    }


    /**
     * @implNote -From CommonInterface Callback
     * Start audio recording
     */
    private AudioRecord audioRecord;

    public void startRecordAudio() {
        audioRecord = new AudioRecord(this, false);
        audioRecord.onAudioClick();
    }


    /**
     * Method to send web for Audio fail
     */
    public void sendAudioFailToWeb() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriAudioFailCallback + "(\"fail\")");
            }
        });
    }


    /**
     * Method to send Base64 audio file to web
     *
     * @param base64AudioFile
     */
    public void sendAudioSuccessToWeb(String base64AudioFile) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mAgentWeb.getWebCreator().getWebView().evaluateJavascript(CommonInterface.m4agriAudioSuccessCallback + "(\"" + base64AudioFile.replaceAll("\\s+", "") + "\")", null);
        } else
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriAudioSuccessCallback + "(\"" + base64AudioFile.replaceAll("\\s+", "") + "\")");
    }


    /**
     * Method to send web for Video fail
     */
    public void sendVideoFailToWeb(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msg.equalsIgnoreCase("")) {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriVideoFailureCallback + "(\"fail\")");
                } else {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriVideoFailureCallback + "(\"" + msg + "\")");
                }
            }
        });
    }


    /**
     * Method to send Base64 video to web
     *
     * @param base64VideoFile
     */
    public void senVideoSuccessToWeb(String base64VideoFile) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            try {
                mAgentWeb.getWebCreator().getWebView().evaluateJavascript(CommonInterface.m4agriVideoSuccessCallback + "(\'" + base64VideoFile.replaceAll("\\s+", "") + "\')", null);
            } catch (Exception e) {
            }
        } else
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriVideoSuccessCallback + "(\"" + base64VideoFile.replaceAll("\\s+", "") + "\")");
    }


    public void hideLoading() {

        if(!isFinishing()){
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
        }

    }

    public void showLoading() {
        hideLoading();

        if(!isFinishing())
            mProgressDialog = Utils.showLoadingDialog(this);
    }

    /**
     * Start video recording
     */
    private VideoRecord videoRecord;

    public void startRecordVideo() {
        videoRecord = new VideoRecord(this, false);
        videoRecord.startVideoRecording();
    }

    /**
     * Method to send web for Images fail
     */
    public void sendImagesFailToWeb() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriImageFailureCallback + "(\"fail\")");
            }
        });
    }

    /**
     * Select images
     */
    public ImageSelect imageSelect;

    public void selectImages() {
        imageSelect = new ImageSelect(this, false);
        imageSelect.getImages();
    }


    /**
     * Method to download Base64 file and share
     *
     * @param fileName      - file name
     * @param fileDataByte- file bytes
     */
    public void downloadBase64FileForShare(String fileName, String fileDataByte) {
        try {
            InputStream stream = new ByteArrayInputStream(Base64.decode(fileDataByte.getBytes(), Base64.DEFAULT));
            File storageDir = FileUtils.getBaseStorage2(this);

            File umangFolder = new File(storageDir, "UMANG");
            if (!umangFolder.exists()) {
                umangFolder.mkdirs();
            }

            File file = new File(umangFolder + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            try {

                try {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = stream.read(buffer)) != -1) {
                        fos.write(buffer, 0, read);
                    }
                    fos.flush();
                    Toast.makeText(this,getString(R.string.download_success)+ " : " + file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                    if (file != null && file.exists()) {

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("application/pdf");
                        Uri uri = FileProvider.getUriForFile(this,
                                this.getPackageName() + ".fileprovider",
                                file);
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_txt)));
                    }
                } catch (Exception e) {
                    try {
                        if (file != null) {
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                    } catch (Exception ed) {
                    }
                } finally {
                    fos.close();


                }
            } catch (Exception e) {
                try {
                    if (file != null) {
                        if (file.exists()) {
                            file.delete();

                        }
                    }
                } catch (Exception ec) {
                }
            } finally {
                fos.close();

            }

        } catch (Exception e) {

        }
    }

    /**
     * Send logs to web
     *
     * @param json
     */
    public void sendLogs(final String json) {
        mAgentWeb.getWebCreator().getWebView().post(new Runnable() {
            @Override
            public void run() {

                String callback = callBackSuccessFunction;
                callBackSuccessFunction = "";

                if (callback != null && !callback.isEmpty()) {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callback + "(\'" + json + "\')");
                    //Log.e("javascript ", "javascript:" + callback + "(\'" + json + "\')");
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case Constants.MY_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        &&  (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                )) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED
                    ) {

                        //initializedMySpeedEngine();
                    }

                } else {
                    //initializedMySpeedEngine();
                }
                return;
            }
            case Constants.MY_PERMISSIONS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d(TAG, "Location Permission granted.................................");
                    getLastLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "Location Permission denied........................................");
                    sendLocationCallBack("F", "", LocationInterface.locationResponse);

                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_location_permission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.MY_PERMISSIONS_CAMERA_AND_STORAGE: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    // All Permissions Granted
                    commonInterface.openCameraIntent();
                } else {
                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_camera_and_storage_permission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //downloadInterface.startDownloadChapterService();
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    JSONObject responseJson = new JSONObject();

                    try {
                        responseJson.put("status", "F");
                    } catch (Exception e) {
                    }

                    JSONObject pd = new JSONObject();
                    try {
                        responseJson.put("pd", pd);
                    } catch (Exception e) {
                    }

                    //sendChapterDownloadCallback("F", responseJson.toString(), DownloadInterface.chapterDownloadResponse);

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.MY_PERMISSIONS_READ_EXTERNAL_STORATE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //downloadInterface.openChapter();
                } else {

                    Log.d(TAG, "Read permission Permission denied........................................");
                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_read_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);

                }
                return;
            }
            case Constants.MY_PERMISSIONS_READ_EXTERNAL_STORATE_GALLERY: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    commonInterface.openGalleryIntent();
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_read_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);


                }
                return;
            }case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_DELETE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //downloadInterface.deleteFile();
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    //sendChapterDeleteCallback("FAIL", chapterDeleteCallback);

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_write_storage_peermission_for_delete_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);

                }
                return;
            }
            case Constants.MY_PERMISSIONS_READ_EXTERNAL_STORATE_DIGI: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //downloadInterface.startDigiLockerDownloadDept();

                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);

                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_MORPHO_RD: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //TODO need to implement
                    //openBiometricDeviceScreen(CommonInterface.jp_successCallback, CommonInterface.jp_failureCallback);

                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);

                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NPS_FILE_SAVE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    downloadBase64File(CommonInterface.nps_fileName, CommonInterface.nps_fileData.split(",")[1]);
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_DOC: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        downloadBase64FileForPDF(CommonInterface.nps_fileName, CommonInterface.nps_fileData.split(",")[1], "pdf");
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);


                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_EXCEL_DOC: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        downloadBase64FileForPDF(CommonInterface.nps_fileName, CommonInterface.nps_fileData.split(",")[1], "excel");
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);


                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_SHARE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        downloadBase64FileForShare(CommonInterface.share_fileName, CommonInterface.share_fileData.split(",")[1]);
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);


                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_DOC_BYTES: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        downloadFileBytesPDF(CommonInterface.nps_fileName, CommonInterface.nps_fileData_bytes);
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);


                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_DOWNLOAD_PDF: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        //downloadInterface.startDownloadFile();
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {

                    Log.d(TAG, "Permission denied........................................");
                    //sendPANFileDownloadCallback(DownloadInterface.panCallbackMethod, "F");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);


                }
                return;
            }
            case Constants.MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        startRecordAudio();
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);


                }
                return;
            }
            case Constants.MY_PERMISSIONS_RECORD_VIDEO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        startRecordVideo();
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_camera_and_storage_permission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.MY_PERMISSIONS_SELECT_IMAGES: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        selectImages();
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_camera_and_storage_permission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_PHOTO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        commonInterface.getPhotoFile(callBackSuccessFunction, callBackFailureFunction);
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_read_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_PDF: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        commonInterface.proofImage(callBackSuccessFunction, callBackFailureFunction);
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_read_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_SIGN: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        commonInterface.getSignatureFile(callBackSuccessFunction, callBackFailureFunction);
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_read_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_COMMON_METHOD: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        commonInterface.getDocument(callBackSuccessFunction, callBackFailureFunction, MaxSize, mTypes, mIsCrop, mCropHeight, mCropWidth, mCropType);
                    } catch (Exception e) {
                        //Log.e(e.toString());
                    }
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_read_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.PERMISSIONS_READ_CALL_LOGS_COMMON_METHOD: {

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.READ_CALL_LOG, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                ) {
                    // All Permissions Granted
                    //traiInterface.getCallLogs(callBackSuccessFunction, mJsonParams);
                } else {
                    // Permission Denied

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_read_calllogs_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return;
            }
            case Constants.PERMISSIONS_READ_CALL_LOGS_COMMON_METHOD_TRAI: {

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.READ_CALL_LOG, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                ) {
                    // All Permissions Granted
                    //traiInterface.getCallLogNumber(callBackSuccessFunction, mJsonParams);
                } else {
                    // Permission Denied

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_read_calllogs_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return;
            }
            case Constants.PERMISSIONS_READ_SMS_LOGS_COMMON_METHOD: {

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.READ_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                ) {
                    // All Permissions Granted
                   // traiInterface.getSMSLogs(callBackSuccessFunction, mJsonParams);
                } else {

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_sms_and_phone_permission_help_text_read_sms),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);

                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        //sendBase64Logs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.PERMISSIONS_READ_SMS_LOGS_COMMON_METHOD_TRAI: {

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.READ_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                ) {
                    // All Permissions Granted
                    //traiInterface.getSMSLogDetail(callBackSuccessFunction, mJsonParams);
                } else {
                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_sms_contact_and_phone_permission_help_text_read_sms),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        //sendBase64Logs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.MY_PERMISSIONS_SMS_AND_PHONE: {

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                ) {
                    // All Permissions Granted
                    //traiInterface.sendSMS(callBackSuccessFunction, mJsonParams);
                } else {
                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_sms_and_phone_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            /*
             * This case will check permission is granted and call sendSMS to send message in baclground
             */
            case Constants.PERMISSIONS_SEND_SMS_COMMON_METHOD: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //traiInterface.sendSMS(callBackSuccessFunction, mJsonParams);
                } else {
                    Log.d(TAG, "Permission denied........................................");
                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_send_sms_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.PERMISSIONS_READ_SMS_TRAI_METHOD: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //traiInterface.hasOTPReadPermission(callBackSuccessFunction);
                } else {
                    Log.d(TAG, "Permission denied........................................");
                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.allow_sms_and_phone_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.PERMISSIONS_READ_SMS_OTP_TRAI_METHOD: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //traiInterface.startOTPAutoRead(callBackSuccessFunction, mJsonParams);
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_read_otp_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);

                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.PERMISSIONS_READ_PHONE_STATE_METHOD2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //traiInterface.startOTPAutoRead(callBackSuccessFunction, mJsonParams);
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_read_otp_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);

                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.PERMISSIONS_READ_PHONE_STATE_METHOD: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //traiInterface.getOperatorList(callBackSuccessFunction);

                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_phone_state_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);


                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }

            case Constants.PERMISSIONS_CHECK_REGISTERED_NUMBRE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                   // traiInterface.checkRegisteredNumber(callBackSuccessFunction);

                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.denied_phone_state_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NDL_DELETE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //ndliInterface.deleteDocument(inputJson, userId, callBackSuccessFunction);
                } else {

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_read_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);

                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }

            case Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NDL_DOWNLOAD: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //ndliInterface.downloadFile(inputJson, userId, callBackSuccessFunction);
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);

                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NDL_OPEN: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //ndliInterface.openDocument(inputJson, userId, callBackSuccessFunction);
                } else {
                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_FOR_DOWNLOAD_PDF_IN_DOWNLOAD: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try{
                        //downloadInterface.downloadPDF(DownloadInterface.mFileName, DownloadInterface.mFileURL);
                    }catch (Exception ex){
                        //LogUtil.printStackTrace(ex);
                    }

                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
            case Constants.PERMISSIONS_MY_CALL_ENABLE: {

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.READ_CALL_LOG, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    //traiInterface.setMyCallsStatus(mJsonParams, callBackSuccessFunction);

                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
//                    openDialog(getResources().getString(R.string.permission_required),
//                            getResources().getString(R.string.allow_read_calllogs_contact_permission_help_text),
//                            getResources().getString(R.string.open_settings),
//                            getResources().getString(R.string.cancel),
//                            TYPE);

                    try {
                        JSONObject object = new JSONObject();
                        object.put("status", "f");
                        object.put("message", "permission_denied");
                        sendLogs(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return;
            }
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_DIGILOCKER: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //commonInterface.getDigilockerDocument(digiLockerResponseType, callBackSuccessFunction);
                } else {

                    Log.d(TAG, "Permission denied........................................");

                    // Permission Denied
                    String TYPE = "PERMISSION";
                    openDialog(getResources().getString(R.string.permission_required),
                            getResources().getString(R.string.denied_write_storage_peermission_help_text),
                            getResources().getString(R.string.open_settings),
                            getResources().getString(R.string.cancel),
                            TYPE);
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (this.isFinishing()) {
            return;
        }

        if (requestCode == mRequestCodeFilePicker) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst.onReceiveValue(intent.getData());
                        mFileUploadCallbackFirst = null;
                    } else if (mFileUploadCallbackSecond != null) {
                        Uri[] dataUris = null;

                        try {
                            if (intent.getDataString() != null) {
                                dataUris = new Uri[]{Uri.parse(intent.getDataString())};
                            } else {
                                if (Build.VERSION.SDK_INT >= 16) {
                                    if (intent.getClipData() != null) {
                                        final int numSelectedFiles = intent.getClipData().getItemCount();

                                        dataUris = new Uri[numSelectedFiles];

                                        for (int i = 0; i < numSelectedFiles; i++) {
                                            dataUris[i] = intent.getClipData().getItemAt(i).getUri();
                                        }
                                    }
                                }
                            }
                        } catch (Exception ignored) {
                        }

                        mFileUploadCallbackSecond.onReceiveValue(dataUris);
                        mFileUploadCallbackSecond = null;
                    }
                }
            } else {
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                    mFileUploadCallbackFirst = null;
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                    mFileUploadCallbackSecond = null;
                }
            }
        }

        else if (requestCode == Constants.BIO_RD_INFO_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                if (intent != null) {
                    String deviceSelected = intent.getStringExtra("deviceSelected");
                    String successCallback = intent.getStringExtra("successCallback");
                    String failureCallback = intent.getStringExtra("failureCallback");

                    getDeviceInfoRD(successCallback, failureCallback);
                }
            } catch (Exception e) {

            }

        } else if (requestCode == Constants.BIOMETRIC_DEVICE_INFO && resultCode == RESULT_OK) {
            try {
                if (intent != null) {
                    //send device info to web
                    handleBiometricDeviceInfo(intent);
                }
            } catch (Exception e) {

                Utils.showInfoDialog(this,"Please check your biometric device is properly connected and try again.");


            }
        } else if (requestCode == Constants.DEVICE_SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                if (intent != null) {
                    //handle device info
                    handleUserFingerPrint(intent);
                }
            } catch (Exception e) {

                Utils.showInfoDialog(this,"Not able to capture your fingerprint properly, please try again");

            }
        } else if (requestCode == 2016 && resultCode == RESULT_CANCELED) {

            sendResultCanceled();

        } else if (requestCode == 2016 && resultCode == RESULT_OK) {

            handleImageResult();

        } else if (requestCode == 2019 && resultCode == RESULT_OK) {

            handleImageResultForUri(intent);

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            //crop activity result
            final CropImage.ActivityResult result = CropImage.getActivityResult(intent);

            //method to handle crop activity result and send the result to web
            handleCropActivityResult(result, resultCode);
        } else if (requestCode == 1001 && resultCode == RESULT_OK) {
            try {
                //TODO need to check
                //setupHeaderData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == Constants.DIGI_AUTH_RESULT_CODE && resultCode == RESULT_OK) {

            //viewModel.handleDigiAuthRequestCode();

        } else if (requestCode == videoRecord.REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            if (intent == null) {
                videoRecord.onVideoRecordComplete(null);
            } else {
                videoRecord.onVideoRecordComplete((Uri) intent.getData());
            }

        } else if (requestCode == ImageSelect.REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {

            handleImageGalleryRequestCode(intent);

        } else if (requestCode == ImageSelect.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            handleImageCaptureResultCode();

        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_IDENTITY_PROOF) {

            handleIdentityProofResult(intent);

        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_PDF_FILE){

            handlePdfFile(intent);

        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_PHOTO_PROOF) {

            handlePhotoProofResult(intent);

        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_COMMON_DOCUMENT) {

            handleCommonDocument(intent);
        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_PASSPORT_PHOTO) {

            handlePassportPhotoResult(intent);

        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_SIGNATURE_FILE) {

            handleSignatureFileResult(intent);

        } else if (requestCode == Constants.BIOMETRIC_DEPT_AUTH && resultCode == RESULT_OK) {

            handleBioMetricAuthDepResult(intent);

        }  else if (requestCode == Constants.BIO_DEVICE_REGISTERATION_STEP && resultCode == RESULT_OK) {

            handleBioMetricRegistrationStepsResult(intent);

        } else if (requestCode == Constants.BARCODE_SCAN_REQUEST_CODE && resultCode == RESULT_OK){

            handleBarcodeScannerResult(intent);

        } else if(requestCode == Constants.DIGI_GET_DOC_REQUEST_CODE && resultCode == RESULT_OK){

            //viewModel.handleDigiLockerGetDocsResult(intent);

        } else if (requestCode == Constants.REQUEST_COMMON_CAMERA_PHOTO && resultCode == RESULT_OK) {

            handleCommonImageCaptureResult();

        } else if (requestCode == Constants.REQUEST_COMMON_GALLERY_PHOTO && resultCode == RESULT_OK) {

            handleCommonGalleryImageResult(intent);

        } else if (requestCode == Constants.INTENT_CALL_TRAI_CALL_LOG_ACTIVITY && resultCode == RESULT_OK) {

            String data = intent.getStringExtra("message");
            sendLogs(data);
        }  else if (requestCode == Constants.INTENT_SMS_TRAI_SMS_LOG_ACTIVITY && resultCode == RESULT_OK) {

            String data = intent.getStringExtra("message");
            sendLogs(data);

        } else if (requestCode == Constants.INTENT_MHA_FILE && resultCode == RESULT_OK) {

            sendMHAFile(intent.getData(), false);

        } else if (requestCode == Constants.INTENT_MHA_FILE_WITH_NAME && resultCode == RESULT_OK) {

            sendMHAFile(intent.getData(), true);

        }  else if (requestCode == Constants.INTENT_CALL_RATE_TRAI_RETURN) {

            //viewModel.handleIntentForCallRate(resultCode, intent);

        } else if (requestCode == Constants.INTENT_OPTIMIE_DIALOG_TRAI) {

            //viewModel.handleIntentForBatteryOptimize(resultCode, intent);

        } else if (requestCode == Constants.DIGI_UPLOAD_DOC_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //commonInterface.uploadDigilockerDocument(mJsonParams);
            } else {
                //TODO send fail login response
            }
        } else{

        }
    }




    private String deviceInfoSuccessCallback;

    /**
     * Jeevan Praman (JP)
     * Method to open RD service
     *
     * @param successCallback
     * @param failureCallback
     */
    public void getDeviceInfoRD(final String successCallback, final String failureCallback) {
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();

        if (deviceList.size() > 0) {
            try {
                deviceInfoSuccessCallback = successCallback;
                Intent intent1 = new Intent("in.gov.uidai.rdservice.fp.INFO");
                startActivityForResult(intent1, Constants.BIOMETRIC_DEVICE_INFO);
                UmangAssistiveAndroidSdk.openingIntent = true;
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this,getString(R.string.bio_other_device_txt1),Toast.LENGTH_LONG).show();
            } catch (Exception e) {
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:wrongDeviceSelected(\"" + "biometric" + "\")");
                }
            });
        }
    }


    /**
     * Jeevan Praman (JP) send device info to web
     * Method to send Bio Metric Device Info to web
     *
     * @param intent
     * @throws Exception
     */
    void handleBiometricDeviceInfo(Intent intent) throws Exception {
        String rd_info = intent.getStringExtra("RD_SERVICE_INFO");
        if (rd_info != null && rd_info.contains("NOTREADY")) {


            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // disable external entities
            try {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            } catch (Exception ex) {
                Log.e("error", "parsing");
            }

            Utils.showInfoDialogHtml(this, "<b>" + factory.newDocumentBuilder().parse(new InputSource(new StringReader(rd_info))).getElementsByTagName("RDService").item(0).getAttributes().getNamedItem("info").getNodeValue() + "</b><br /><br />" + "Please make sure you have selected the correct RD service app for the connected biometric device.");
        } else {


            String DeviceInfoXml = intent.getStringExtra("DEVICE_INFO");
           

            if (DeviceInfoXml != null) {
                if (DeviceInfoXml.equals("") || DeviceInfoXml.isEmpty()) {
                    //Log.d(TAG, "Error occurred in DeviceInfo DATA XML..............");
                    return;
                }
                if (DeviceInfoXml.startsWith("ERROR:-")) {
                    //Log.d(TAG, "ERROR.............." + DeviceInfoXml);
                    return;
                }
            }

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = builderFactory.newDocumentBuilder();
            Document inputDocument = null;
            inputDocument = builder.parse(new InputSource(new StringReader(rd_info)));
            String Stat = inputDocument.getElementsByTagName("RDService").item(0).getAttributes().getNamedItem("status").getNodeValue();
            if (DeviceInfoXml != null && !DeviceInfoXml.equals("") && !DeviceInfoXml.isEmpty()) {
                //Log.d(TAG, "DEVICE INFO XML............." + DeviceInfoXml);
            }
            if (Stat != null) {
                if (Stat.equals("READY")) {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc2 = dBuilder.parse(new InputSource(new StringReader(DeviceInfoXml)));

                    // XPath to retrieve the content of the <FamilyAnnualDeductibleAmount> tag
                    XPath xpath2 = XPathFactory.newInstance().newXPath();

                    XPathExpression expImei = xpath2.compile("/DeviceInfo/@dpId");
                    final String deviceMake = (String) expImei.evaluate(doc2, XPathConstants.STRING);
                    Log.d(TAG, "deviceMake : " + deviceMake);

                    XPathExpression exprdsId = xpath2.compile("/DeviceInfo/@rdsId");
                    final String rdsId = (String) exprdsId.evaluate(doc2, XPathConstants.STRING);
                    Log.d(TAG, "rdsId : " + rdsId);

                    XPathExpression exprdsVer = xpath2.compile("/DeviceInfo/@rdsVer");
                    final String rdsVer = (String) exprdsVer.evaluate(doc2, XPathConstants.STRING);
                    Log.d(TAG, "rdsVer : " + rdsVer);

                    XPathExpression expdc = xpath2.compile("/DeviceInfo/@dc");
                    final String dc = (String) expdc.evaluate(doc2, XPathConstants.STRING);
                    Log.d(TAG, "dc : " + dc);

                    XPathExpression expmc = xpath2.compile("/DeviceInfo/@mc");
                    final String mc = (String) expmc.evaluate(doc2, XPathConstants.STRING);
                    Log.d(TAG, "mc : " + mc);

                    XPathExpression expImi = xpath2.compile("/DeviceInfo/@mi");
                    final String deviceModel = (String) expImi.evaluate(doc2, XPathConstants.STRING);
                    Log.d(TAG, "deviceModel : " + deviceModel);

                    XPathExpression expven = xpath2.compile("/DeviceInfo/@dpId");
                    String deviceVendor = (String) expven.evaluate(doc2, XPathConstants.STRING);
                    Log.d(TAG, "deviceVendor : " + deviceVendor);

                    XPathExpression expsr = xpath2.compile("/DeviceInfo/@srno");
                    final String serialNumber = (String) expsr.evaluate(doc2, XPathConstants.STRING);
                    Log.d(TAG, "serialNumber : " + serialNumber);

                    String connecteddevicevalues = deviceMake.trim() + deviceModel.trim() + deviceVendor.trim() + serialNumber.trim();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("make", deviceMake);
                                obj.put("model", deviceModel);
                                obj.put("srno", serialNumber);

                                obj.put(Constants.RD_SLD, rdsId);
                                obj.put(Constants.RD_VER, rdsVer);
                                obj.put(Constants.DPLD, deviceMake);
                                obj.put(Constants.DC, dc);
                                obj.put(Constants.MI, deviceModel);
                                obj.put(Constants.MC, mc);

                                Log.d(TAG, "Device JSON Obj === " + obj.toString());
                                String strWithEscape = obj.toString().replaceAll("\"", "\\\\\\\"");
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + deviceInfoSuccessCallback + "(\"" + strWithEscape + "\")");
                            } catch (Exception e) {
                                //Log.e(TAG, "Error in device info", e.toString());
                            }

                        }
                    });
                }
            }
        }
    }


    /**
     * Jeevan Praman (JP)
     * Method to send Finger prints to Web
     *
     * @param intent
     * @throws Exception
     */
    void handleUserFingerPrint(Intent intent) throws Exception {
        String pidDataXML = intent.getStringExtra("PID_DATA");
        Log.d(TAG, "pid data....." + pidDataXML);
        if (pidDataXML != null) {
            if (pidDataXML.equals("") || pidDataXML.isEmpty()) {
                Log.d(TAG, "Error occurred in PID DATA XML..................");
                Utils.showInfoDialog(this,"Not able to get your fingerprint properly. Please try again.");
                return;
            }
            if (pidDataXML.startsWith("ERROR:-")) {
                Log.d(TAG, "ERROR............." + pidDataXML);
                Utils.showInfoDialog(this,"An error occurred while capturing your fingerprint. Please try again.");
                return;
            }
            DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
            // disable external entities

            try {
                db.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                db.setFeature("http://xml.org/sax/features/external-general-entities", false);
                db.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            } catch (Exception ex) {

            }


            Document inputDocument = db.newDocumentBuilder().parse(new InputSource(new StringReader(pidDataXML)));
            NodeList nodes = inputDocument.getElementsByTagName("PidData");
            if (nodes != null) {
                Element element = (Element) nodes.item(0);
                NodeList respNode = inputDocument.getElementsByTagName("Resp");
                if (respNode != null) {
                    Node respData = respNode.item(0);
                    NamedNodeMap attsResp = respData.getAttributes();
                    String errCodeStr = "";
                    String errInfoStr = "";
                    Node errCode = attsResp.getNamedItem("errCode");
                    if (errCode != null) {
                        errCodeStr = errCode.getNodeValue();
                    } else errCodeStr = "0";
                    Node errInfo = attsResp.getNamedItem("errInfo");
                    if (errInfo != null) {
                        errInfoStr = errInfo.getNodeValue();
                    }
                    if (Integer.parseInt(errCodeStr) > 0) {
                        Log.d(TAG, "Capture error :- " + errCodeStr + " , " + errInfoStr);
                        Utils.showInfoDialog(this,errInfoStr);
                        return;
                    }
                }
            }
        }
        if (pidDataXML != null) {   //deviceInfoXML != null &&
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // disable external entities

            try {
                dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            } catch (Exception ex) {

            }

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc2 = dBuilder.parse(new InputSource(new StringReader(pidDataXML)));


            // XPath to retrieve the content of the <FamilyAnnualDeductibleAmount> tag
            XPath xpath2 = XPathFactory.newInstance().newXPath();

            XPathExpression expResp = xpath2.compile("/PidData/Resp/@errCode");
            String resp = (String) expResp.evaluate(doc2, XPathConstants.STRING);
            Log.e("errCode", "-->" + resp);

            if (resp.equalsIgnoreCase("0")) {

                XPathExpression expImei = xpath2.compile("/PidData/DeviceInfo/@dpId");
                String dpid = (String) expImei.evaluate(doc2, XPathConstants.STRING);
                Log.e("dpId", "-->" + dpid);


                XPathExpression expci = xpath2.compile("/PidData/Skey/@ci");
                String ci = (String) expci.evaluate(doc2, XPathConstants.STRING);
                Log.e("ci", "-->" + ci);

                XPathExpression expSkey = xpath2.compile("/PidData/Skey/text()");
                String Skey = (String) expSkey.evaluate(doc2, XPathConstants.STRING);
                Log.e("Skey", "-->" + Skey);

                XPathExpression expHmac = xpath2.compile("/PidData/Hmac/text()");
                String Hmac = (String) expHmac.evaluate(doc2, XPathConstants.STRING);
                Log.e("Hmac", "-->" + Hmac);

                XPathExpression expData = xpath2.compile("/PidData/Data/text()");
                String Data = (String) expData.evaluate(doc2, XPathConstants.STRING);
                Log.e("Data", "-->" + Data);

                XPathExpression experror = xpath2.compile("/PidData/Resp/@errCode");
                String errorCode = (String) experror.evaluate(doc2, XPathConstants.STRING);
                Log.e("errorCode", "-->" + errorCode);

                XPathExpression expdc = xpath2.compile("/PidData/DeviceInfo/@dc");
                String dc = (String) expdc.evaluate(doc2, XPathConstants.STRING);

                XPathExpression expmi = xpath2.compile("/PidData/DeviceInfo/@mi");
                String mi = (String) expmi.evaluate(doc2, XPathConstants.STRING);

                XPathExpression expmc = xpath2.compile("/PidData/DeviceInfo/@mc");
                String mc = (String) expmc.evaluate(doc2, XPathConstants.STRING);

                XPathExpression exprdsId = xpath2.compile("/PidData/DeviceInfo/@rdsId");
                String rdsId = (String) exprdsId.evaluate(doc2, XPathConstants.STRING);

                XPathExpression exprdsVer = xpath2.compile("/PidData/DeviceInfo/@rdsVer");
                String rdsVer = (String) exprdsVer.evaluate(doc2, XPathConstants.STRING);


                JSONObject dataJson = new JSONObject();
                dataJson.put(Constants.HMAC, Hmac);
                dataJson.put(Constants.SKEY, Skey);
                dataJson.put(Constants.DATA, Data);
                dataJson.put(Constants.CI, ci);
                dataJson.put(Constants.RD_SLD, rdsId);
                dataJson.put(Constants.RD_VER, rdsVer);
                dataJson.put(Constants.DPLD, dpid);
                dataJson.put(Constants.DC, dc);
                dataJson.put(Constants.MI, mi);
                dataJson.put(Constants.MC, mc);

                String strWithEscape = "";
                strWithEscape = dataJson.toString();
                Gson gson = new Gson();
                strWithEscape = gson.toJson(dataJson);
                Log.d(TAG, "SCAN RESULT ====  " + strWithEscape);
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:scanResult(" + strWithEscape + ")");


            } else {

                XPathExpression experror = xpath2.compile("/PidData/Resp/@errCode");
                String errorCode = (String) experror.evaluate(doc2, XPathConstants.STRING);
                Log.e("errorCode", "-->" + errorCode);
                Log.d(TAG, "Error Info->....." + resp + " & Error Code-> " + errorCode);
            }

        } else {
            Log.d(TAG, "Scan Failure.................");
            //getNavigator().onFingerPrintError("Please check your device ");
        }
    }


    /**
     * Method to send result canceled to Web
     */
    public void sendResultCanceled() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"Action cancelled\")");
            }
        });
    }

    /**
     * Method to handle image crop and resizeing
     * then send to web
     */
    public void handleImageResult() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("UmangSdkPref", 0);
                    requestImageFor= pref.getString(Constants.PREF_REQUEST_IMAGE_FOR,"");
                    if (requestImageFor.equalsIgnoreCase("edistrict")) {
                        try {

                            String tempUri= pref.getString(Constants.PREF_CAMERA_IMAGE_URI,imageSelectedPath);
                            Bitmap bitmap = ImageUtils.rescaleImage(UmangWebActivity.this, Uri.parse(tempUri), 500);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            final InputStream imageStream = new ByteArrayInputStream(stream.toByteArray());
                            if (isSizeGood(imageStream, Integer.getInteger((UmangWebActivity.this).MaxSize, 200))) {
                                //final InputStream imageStream = getContentResolver().openInputStream(resultUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                String encodedStr = ImageUtils.encodeImage(selectedImage);
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackSuccessFunction + "(\"data:image/png;base64," + encodedStr + "\")");
                            } else {
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_EXCEEDED + "\")");
                            }
                        } catch (Exception e) {
                            //Log.e(e.toString());
                        }
                    } else {
                        String tempUri= pref.getString(Constants.PREF_CAMERA_IMAGE_URI,imageSelectedPath);
                        CropImage.activity(Uri.parse(tempUri))
                                .setAspectRatio(500, 500)
                                .setFixAspectRatio(true)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(UmangWebActivity.this);
                    }

                }
            });
        } catch (Exception e) {
            //Log.e(e.toString());
            Toast.makeText(this,getString(R.string.please_try_again),Toast.LENGTH_LONG).show();
        }
    }


    /**
     * check the size of Input stream  with max size
     *
     * @param inputStream InputStream of file.
     * @param maxsize     Maximum size required  for check
     * @return true if InputStream size is less then Max size
     * @throws IOException
     */
    private boolean isSizeGood(InputStream inputStream, int maxsize) throws IOException {
        if (inputStream.available() / 1024 <= maxsize) {
            return true;
        }
        return false;
    }


    /**
     * check the size of Input stream  with max size
     *
     * @param arr     byteArray of file.
     * @param maxsize Maximum size required  for check
     * @return true if byteArray size is less then Max size
     * @throws IOException
     */
    private boolean isSizeGood(byte[] arr, int maxsize) {
        if (arr.length / 1024 <= maxsize) {
            return true;
        }
        return false;
    }


    /**
     * Method to handle image result for URI
     *
     * @param intent
     */
    public void handleImageResultForUri(Intent intent) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri uri = intent.getData();
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("UmangSdkPref", 0);
                    requestImageFor= pref.getString(Constants.PREF_REQUEST_IMAGE_FOR,"");
                    if (requestImageFor.equalsIgnoreCase("edistrict")) {
                        try {


                            Bitmap bitmap = ImageUtils.rescaleImage(UmangWebActivity.this, uri, 500);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            final InputStream imageStream = new ByteArrayInputStream(stream.toByteArray());

                            if (isSizeGood(imageStream, Integer.getInteger((UmangWebActivity.this).MaxSize, 200))) {
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                String encodedStr = ImageUtils.encodeImage(selectedImage);

                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackSuccessFunction + "(\"data:image/png;base64," + encodedStr + "\")");
                            } else {
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_EXCEEDED + "\")");
                            }

                        } catch (IOException e) {
                            //Log.e(e.toString());
                        }
                    } else {
                        CropImage.activity(Uri.parse(uri.toString()))
                                .setAspectRatio(500, 500)
                                .setFixAspectRatio(true)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(UmangWebActivity.this);
                    }
                }
            });
        } catch (Exception e) {
            //Log.e(e.toString());
            Toast.makeText(this,getString(R.string.please_try_again),Toast.LENGTH_LONG).show();
            //activity.showToast(activity.getResources().getString(R.string.please_try_again));
        }
    }



    /**
     * Method to handle result from CropActivity
     *
     * @param result
     * @param resultCode
     */
    public void handleCropActivityResult(final CropImage.ActivityResult result, int resultCode) {
        if (resultCode == RESULT_OK) {

            if (!isFinishing()) {
                // loadurl on UI main thread
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Uri resultUri = result.getUri();
                        if (CropImageId == 1) {
                            CropImageId = 0;

                            Bitmap bitma = null;
                            try {

                                String name = String.format("Pan1-%s.jpg", Calendar.getInstance().getTimeInMillis());
                                File outputDir = new File(getFilesDir(), "Pan");
                                if (!outputDir.exists()) {
                                    outputDir.mkdirs(); // should succeed
                                }
                                File outputFile = new File(outputDir, name);

                                Bitmap bmp = BitmapFactory.decodeFile(resultUri.getPath(), null);

                                boolean isWidthMax = false;
                                if (bmp != null) {
                                    int height = bmp.getHeight();
                                    int width = bmp.getWidth();

                                    if (height > width) {
                                        isWidthMax = false;
                                    } else {
                                        isWidthMax = true;
                                    }
                                }
                                int maxHeight = 842;
                                int maxWidth = 595;

                                if (isWidthMax) {
                                    maxHeight = 595;
                                    maxWidth = 842;
                                }

                                Compressor comp = new Compressor(UmangWebActivity.this)
                                        .setMaxWidth(maxWidth)
                                        .setMaxHeight(maxHeight)
                                        .setQuality(60)
                                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                                Environment.DIRECTORY_PICTURES).getAbsolutePath());

                                File file = comp.compressToFile(FileUtils.from(UmangWebActivity.this, resultUri));

                                byte[] b = new byte[(int) file.length()];
                                try {
                                    FileInputStream fileInputStream = new FileInputStream(file);
                                    fileInputStream.read(b);
                                    for (int i = 0; i < b.length; i++) {
                                        System.out.print((char) b[i]);
                                    }
                                } catch (FileNotFoundException e) {
                                    System.out.println("File Not Found.");
                                    e.printStackTrace();
                                } catch (IOException e1) {
                                    System.out.println("Error Reading The File.");
                                    e1.printStackTrace();
                                }

                                String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                                callWebService(encoded, "JPG");
                            } catch (IOException e) {
                                //Log.e(e.toString());
                            }

                            return;
                        } else if (CropImageId == 2) {
                            CropImageId = 0;
                            Bitmap bitma = null;
                            try {

                                String name = String.format("Pan1-%s.jpg", Calendar.getInstance().getTimeInMillis());
                                File outputDir = new File(getFilesDir(), "Pan");
                                if (!outputDir.exists()) {
                                    outputDir.mkdirs(); // should succeed
                                }
                                File outputFile = new File(outputDir, name);

                                File OutDir = new File(getFilesDir(), "Pan");

                                Compressor comp = new Compressor(UmangWebActivity.this)
                                        .setMaxWidth(213)
                                        .setMaxHeight(213)
                                        .setQuality(60)
//                                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                        .setDestinationDirectoryPath(OutDir.getAbsolutePath());

                                File uriFile = FileUtils.from(UmangWebActivity.this, resultUri);

                                File file = comp.compressToFile(FileUtils.from(UmangWebActivity.this, resultUri));

                                byte[] b = new byte[(int) file.length()];
                                try {
                                    FileInputStream fileInputStream = new FileInputStream(file);
                                    fileInputStream.read(b);
                                    for (int i = 0; i < b.length; i++) {
                                        System.out.print((char) b[i]);
                                    }
                                } catch (FileNotFoundException e) {
                                    System.out.println("File Not Found.");
                                    e.printStackTrace();
                                } catch (IOException e1) {
                                    System.out.println("Error Reading The File.");
                                    e1.printStackTrace();
                                }

                                String encoded = Base64.encodeToString(b, Base64.DEFAULT);

                                callWebService(encoded, "JPG");

                            } catch (IOException e) {
                                //Log.e(e.toString());
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
                            } catch (OutOfMemoryError e) {
                                //Log.e(e.toString());
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_EXCEEDED + "\")");

                            }

                            return;
                        } else if (CropImageId == 3) {
                            CropImageId = 0;

                            byte[] imgArray = compressDocumentImage(resultUri, 0, 60);

                            //Log.e("Sizeee before sig", "" + imgArray.length);
                            imgArray = setDpi(imgArray, 600);
                            //Log.e("Sizeee after sig", "" + imgArray.length);
                            if (isSizeGood(imgArray, 60)) {

                                Bitmap blackAndWhite = createBlackAndWhite(BitmapFactory.decodeByteArray(imgArray, 0, imgArray.length));
                                String encodedStr = ImageUtils.encodeImage(blackAndWhite);
                                String mimetype = getMineType(resultUri);


                                callWebService(encodedStr, mimetype);
                            } else {
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_EXCEEDED + "\")");
                            }
                            return;
                        } else if (CropImageId == 4) {
                            CropImageId = 0;
                            BitmapFactory.Options o = new BitmapFactory.Options();
                            o.inJustDecodeBounds = true;
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri), null, o);
                                if (bitmap != null) {
                                    //Log.e("Bitmap", bitmap.getHeight() + "");
                                } else {
                                    //Log.e("Bitmap", "Bitmap is nulll ");
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            return;
                        } else if (CropImageId == 5) {
                            CropImageId = 0;
                            //byte[] imageArray = compressDocumentImage(resultUri, 500, Integer.parseInt(activity.MaxSize));
                            byte[] imageArray = new byte[0];
                            try {
                                imageArray = ImageUtils.getBytesFromUri(resultUri, UmangWebActivity.this);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //ImageUtils.showImageBitmapInDialog(imageArray,activity);

                            //Log.e("Sizeee", "" + imageArray.length);
                            try {
                                if (isSizeGood(imageArray, Integer.valueOf(MaxSize))) {
                                    String encodedStr = Base64.encodeToString(imageArray, Base64.DEFAULT);
                                    String mimetype = getMineType(resultUri);
                                    callWebService(encodedStr, mimetype);
                                } else {
                                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_EXCEEDED + "\")");
                                }
                            } catch (NumberFormatException e) {
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
                            } catch (Exception e) {
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
                            }

                            return;
                        }

                        imageSelectedPath = resultUri.toString();
                        try {

                            Bitmap bitmap = ImageUtils.rescaleImage(UmangWebActivity.this, resultUri, 500);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            final InputStream imageStream = new ByteArrayInputStream(stream.toByteArray());
                            //final InputStream imageStream = getContentResolver().openInputStream(resultUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedStr = ImageUtils.encodeImage(selectedImage);

                            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackSuccessFunction + "(\"data:image/png;base64," + encodedStr + "\")");

                        } catch (IOException e) {
                            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
                            //Log.e(e.toString());
                        }

                    }
                });
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
            if (!isFinishing()) {
                // loadurl on UI main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"Action cancelled\")");
                    }
                });
            }
        } else {
            if (!isFinishing()) {
                // loadurl on UI main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"Action cancelled\")");
                    }
                });
            }
        }

    }

    private void callWebService(String encodedStr, String mimetype) {

        byte[] imageBytes = Base64.decode(encodedStr, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        String name = String.format("Pansig-%s.jpg", Calendar.getInstance().getTimeInMillis());
        File outputDir = new File(getFilesDir(), "Pan");
        if (!outputDir.exists()) {
            outputDir.mkdirs(); // should succeed
        }
        File outputFile = new File(outputDir, name);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);
            decodedImage.compress(Bitmap.CompressFormat.JPEG, 100 /* ignored for PNG */, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                }
            }
        }

        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackSuccessFunction + "(\"data:image/" + mimetype + ";base64," + encodedStr + "\")");
    }


    /**
     * Method to compress document image
     *
     * @param resultUri
     * @param sizeInPixel
     * @param requiredSize
     * @return
     */
    private byte[] compressDocumentImage(Uri resultUri, int sizeInPixel, int requiredSize) {
        try {
            Bitmap bmp = ImageUtils.rescaleImage(UmangWebActivity.this, resultUri, sizeInPixel);
            int compressedImg = 60;
            byte[] compressedImageByte = ImageUtils.compressImage(bmp, compressedImg);

            while ((compressedImageByte.length / 1024) > requiredSize) {
                compressedImg = compressedImg - 5;
                compressedImageByte = ImageUtils.compressImage(bmp, compressedImg);
            }

            return compressedImageByte;
        } catch (FileNotFoundException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.FILE_NOT_FOUND + "\")");
           // Log.e(e.toString());
            return null;
        }
    }


    /**
     * method to set dpi
     *
     * @param imageData
     * @param dpi
     * @return
     */
    private byte[] setDpi(byte[] imageData, int dpi) {
        imageData[13] = 1;
        imageData[14] = (byte) (dpi >> 8);
        imageData[15] = (byte) (dpi & 0xff);
        imageData[16] = (byte) (dpi >> 8);
        imageData[17] = (byte) (dpi & 0xff);
        return imageData;
    }


    /**
     * Create a Black&White Bitmap
     *
     * @param src
     * @return
     */
    public Bitmap createBlackAndWhite(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

                // use 128 as threshold, above -> white, below -> black
                if (gray > 128)
                    gray = 255;
                else
                    gray = 0;
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return bmOut;
    }

    /**
     * check mime type from Uri and return.
     *
     * @param data URI of file
     * @return mime type as string  for example:- jpg,jpeg,pdf,png etc.
     */
    private String getMineType(Uri data) {
        try {
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cR.getType(data));

            if (type == null) {
                type = MimeTypeMap.getFileExtensionFromUrl(data.getPath());
            }
            return type;
        } catch (Exception e) {
            //Log.e(e.toString());
            return "";
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }


    /**
     * Method to handle Gallery selected images result
     *
     * @param intent
     */
    public void handleImageGalleryRequestCode(Intent intent) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String encodedStr = "";

                    //If Single image selected then it will fetch from Gallery
                    if (intent.getData() != null) {
                        try {
                            Uri mImageUri = intent.getData();
                            Bitmap bitmap = ImageUtils.rescaleImage(UmangWebActivity.this, mImageUri, 400);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                            final InputStream imageStream = new ByteArrayInputStream(stream.toByteArray());
                            //final InputStream imageStream = getContentResolver().openInputStream(resultUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            int size = sizeOf(selectedImage);

                            encodedStr = encodedStr + ImageUtils.encodeImage(selectedImage) + "#" + size + ",";
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                                try {
                                    encodedStr = encodedStr.substring(0, encodedStr.length() - 1);
                                    mAgentWeb.getWebCreator().getWebView().evaluateJavascript(CommonInterface.m4agriImageSuccessCallback + "(\'" + encodedStr.replaceAll("\\s+", "") + "\')", null);
                                } catch (Exception e) {
                                    //Log.e(e.toString());
                                }
                            } else
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriImageSuccessCallback + "(\"" + encodedStr.replaceAll("\\s+", "") + "\")");

                        } catch (Exception e) {
                            sendImagesFailToWeb();
                        }

                    } else {
                        if (intent.getClipData() != null) {
                            try {
                                ClipData mClipData = intent.getClipData();
                                if (mClipData.getItemCount() <= 3) {
                                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                                        ClipData.Item item = mClipData.getItemAt(i);
                                        Uri uri = item.getUri();

                                        Bitmap bitmap = ImageUtils.rescaleImage(UmangWebActivity.this, uri, 400);
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                                        final InputStream imageStream = new ByteArrayInputStream(stream.toByteArray());
                                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                        int size = sizeOf(selectedImage);
                                        encodedStr = encodedStr + ImageUtils.encodeImage(selectedImage) + "#" + size + ",";

                                    }
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                                        try {
                                            encodedStr = encodedStr.substring(0, encodedStr.length() - 1);
                                            mAgentWeb.getWebCreator().getWebView().evaluateJavascript(CommonInterface.m4agriImageSuccessCallback + "(\'" + encodedStr.replaceAll("\\s+", "") + "\')", null);
                                        } catch (Exception e) {
                                            //Log.e(e.toString());
                                        }
                                    } else
                                        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriImageSuccessCallback + "(\"" + encodedStr.replaceAll("\\s+", "") + "\")");
                                } else {
                                    Toast.makeText(UmangWebActivity.this,"Maximum of three images can be selected",Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                sendImagesFailToWeb();
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            //Log.e(e.toString());
            Toast.makeText(UmangWebActivity.this,getString(R.string.please_try_again),Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Method to handle image capture result from camera
     */
    public void handleImageCaptureResultCode() {
        // loadurl on UI main thread
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Bitmap bitmap = ImageUtils.rescaleImage(UmangWebActivity.this, imageSelect.mCapturedImageURI, 400);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                        final InputStream imageStream = new ByteArrayInputStream(stream.toByteArray());
                        //final InputStream imageStream = getContentResolver().openInputStream(resultUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        int size = sizeOf(selectedImage);
                        String encodedStr = ImageUtils.encodeImage(selectedImage) + "#" + size;
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                            try {
                                mAgentWeb.getWebCreator().getWebView().evaluateJavascript(CommonInterface.m4agriImageSuccessCallback + "(\'" + encodedStr.replaceAll("\\s+", "") + "\')", null);
                            } catch (Exception e) {
                                //Log.e(e.toString());
                            }
                        } else
                            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + CommonInterface.m4agriImageSuccessCallback + "(\"" + encodedStr.replaceAll("\\s+", "") + "\")");
                    } catch (Exception e) {
                        sendImagesFailToWeb();
                    }

                }
            });
        } catch (Exception e) {
            //Log.e(e.toString());
            sendImagesFailToWeb();
            Toast.makeText(UmangWebActivity.this,getString(R.string.please_try_again),Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Method to handle Identity Proof image
     *
     * @param intent
     */
    public void handleIdentityProofResult(Intent intent) {
        String mimeType = getMineType(intent.getData());
        try {
            InputStream inputStream = getContentResolver().openInputStream(intent.getData());
            if (mimeType.equalsIgnoreCase("pdf")) {
                if (isSizeGood(inputStream, 300)) {
                    byte[] targetArray = new byte[inputStream.available()];
                    inputStream.read(targetArray);
                    String encodedStr = Base64.encodeToString(targetArray, Base64.DEFAULT);
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackSuccessFunction + "(\"data:application/pdf;base64," + encodedStr + "\")");
                } else {
                    String url = "javascript:" + callBackFailureFunction + "(\"" + Constants.PDF_FILE_SIZE_EXCEEDED + "\")";
                    //Log.e("PDF", url);
                    mAgentWeb.getWebCreator().getWebView().loadUrl(url);
                }
            } else {
                InputStream inputStream2 = getContentResolver().openInputStream(intent.getData());

                if (isSizeGood(inputStream2, 300)) {
                    if (!checkMinSize(inputStream2, 20)) {
                        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_TOO_SMALL + "\")");
                        return;
                    }
                    byte[] targetArray = new byte[inputStream2.available()];
                    inputStream2.read(targetArray);
                    String encodedStr = Base64.encodeToString(targetArray, Base64.DEFAULT);
                    callWebService(encodedStr, "JPG");
                    return;
                }

                Bitmap orignalBitmap = decodeSampledBitmapFromInputStream(inputStream, inputStream2, 500, 500);

                CropImageId = 1;
                CropImage.activity(getImageUri(this, orignalBitmap))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }

        } catch (FileNotFoundException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.FILE_NOT_FOUND + "\")");
            //Log.e(e.toString());
        } catch (IOException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
            //Log.e(e.toString());
        } catch (Exception e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.PERMISSION_NOT_GRANTED + "\")");
            //Log.e(e.toString());
        }
    }

    /**
     * check the size of Input stream  with max size
     *
     * @param inputStream InputStream of file.
     * @param minvalue    Maximum size required  for check
     * @return true if InputStream size is less then Max size
     * @throws IOException
     */
    private boolean checkMinSize(InputStream inputStream, int minvalue) throws IOException {
        if (inputStream.available() / 1024 >= minvalue) {
            return true;
        }
        return false;
    }

    /**
     * Decode input stream with BitmapFactory.Options
     *
     * @param in
     * @param copyOfin
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromInputStream(InputStream in,
                                                     InputStream copyOfin, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(copyOfin, null, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * Compress image and Generate  Bimap Image from  Uri
     *
     * @param inContext
     * @param inImage
     * @return
     */

    public Uri getImageUri(Context inContext, Bitmap inImage) throws Exception {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            File file = new File(getFilesDir().getAbsolutePath() + "/.images/");
            if (!file.isDirectory()) {
                file.mkdir();
            }
            File file1 = new File(file, Calendar.getInstance().getTimeInMillis() + ".jpg");

            OutputStream os = new BufferedOutputStream(new FileOutputStream(file1));
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

            Uri uri = Uri.fromFile(file1);
            return uri;
        } else {
            Utils.openPermissionSettingsDialog(this, getString(R.string.denied_write_storage_peermission_help_text));
            throw new Exception("Storage permission not granted");
        }
    }


    /**
     * Method to handle PDF file selection result
     *
     * @param intent
     */
    public void handlePdfFile(Intent intent) {
        String mimeType = getMineType(intent.getData());
        try {
            InputStream inputStream = getContentResolver().openInputStream(intent.getData());
            if (mimeType.equalsIgnoreCase("pdf")) {
                //Log.d(TAG, "File Size Check === " + fileSizeStr);
                if (isSizeGood(inputStream, Integer.parseInt(fileSizeStr))) {
                    byte[] targetArray = new byte[inputStream.available()];
                    inputStream.read(targetArray);
                    String encodedStr = Base64.encodeToString(targetArray, Base64.DEFAULT);
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        try {
                            encodedStr = encodedStr.substring(0, encodedStr.length() - 1);
                            mAgentWeb.getWebCreator().getWebView().evaluateJavascript(callBackSuccessFunction + "(\'data:application/pdf;base64," + encodedStr.replaceAll("\\s+", "") + "\')", null);
                        } catch (Exception e) {
                            //Log.e(e.toString());
                        }
                    } else
                        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackSuccessFunction + "(\'data:application/pdf;base64," + encodedStr.replaceAll("\\s+", "") + "\')");

                } else {
                    String url = "javascript:" + callBackFailureFunction + "(\"" + Constants.PDF_FILE_SIZE_EXCEEDED + "\")";
                    //Log.e(TAG, url);
                    mAgentWeb.getWebCreator().getWebView().loadUrl(url);
                }
            } else {
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.NON_PDF_FILE + "\")");
            }

        } catch (FileNotFoundException e) {

            if (mAgentWeb.getWebCreator().getWebView() != null)
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.FILE_NOT_FOUND + "\")");
            //Log.e(e.toString());

        } catch (IOException e) {
            if (mAgentWeb.getWebCreator().getWebView() != null)
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
            //Log.e(e.toString());
        } catch (Exception e) {
            if (mAgentWeb.getWebCreator().getWebView() != null)
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.PERMISSION_NOT_GRANTED + "\")");
            //Log.e(e.toString());
        }
    }

    /**
     * Method to handle photo proof
     */
    public void handlePhotoProofResult(Intent intent) {
        try {
            final Uri imageUri = intent.getData();
            //Log.e("Select image URI", imageUri.getPath());
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);

            if (isSizeGood(imageStream, 30)) {
                if (!checkMinSize(imageStream, 6)) {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_TOO_SMALL + "\")");
                    return;
                }
                byte[] targetArray = new byte[imageStream.available()];
                imageStream.read(targetArray);
                String encodedStr = Base64.encodeToString(targetArray, Base64.DEFAULT);
                callWebService(encodedStr, "JPG");
                return;
            }

            final InputStream imageStream2 = getContentResolver().openInputStream(imageUri);
            CropImageId = 2;
            CropImage.activity(imageUri)
                    .setFixAspectRatio(true)
                    .setOutputCompressQuality(70)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        } catch (FileNotFoundException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.FILE_NOT_FOUND + "\")");
        } catch (Exception e) {

            try {
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.PERMISSION_NOT_GRANTED + "\")");
            }catch (Exception ex){

            }
        }
    }


    /**
     * Method to handle common document
     *
     * @param intent
     */
    public void handleCommonDocument(Intent intent) {
        String mimeType = getMineType(intent.getData());
        try {
            InputStream inputStream = getContentResolver().openInputStream(intent.getData());
            if (mimeType.equalsIgnoreCase("pdf")) {
                if (isSizeGood(inputStream, Integer.valueOf(MaxSize))) {
                    byte[] targetArray = new byte[inputStream.available()];
                    inputStream.read(targetArray);
                    String encodedStr = Base64.encodeToString(targetArray, Base64.DEFAULT);
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackSuccessFunction + "(\"data:application/pdf;base64," + encodedStr + "\")");

                } else {
                    String url = "javascript:" + callBackFailureFunction + "(\"" + Constants.PDF_FILE_SIZE_EXCEEDED + "\")";
                    Log.e("PDF", url);
                    mAgentWeb.getWebCreator().getWebView().loadUrl(url);
                }
            } else {
                InputStream inputStream2 = getContentResolver().openInputStream(intent.getData());
                Bitmap orignalBitmap = decodeSampledBitmapFromInputStream(inputStream, inputStream2, 500, 500);

                if (mIsCrop) {
                    boolean aspectRatio = true;
                    if (mCropWidth == 0 || mCropHeight == 0) {
                        aspectRatio = false;
                    }
                    CropImageId = 5;
                    CropImage.ActivityBuilder builder = CropImage.activity(getImageUri(UmangWebActivity.this, orignalBitmap));

                    if (mCropType.equalsIgnoreCase("aspect")) {
                        builder.setAspectRatio(mCropHeight, mCropWidth);
                        builder.setFixAspectRatio(aspectRatio);

                    } else if (mCropType.equalsIgnoreCase("pixel")) {
                        builder.setMinCropResultSize(mCropWidth, mCropHeight);

                    }
                    builder.setGuidelines(CropImageView.Guidelines.ON)
                            .start(UmangWebActivity.this);
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    if (mimeType.equalsIgnoreCase("jpg") || mimeType.equalsIgnoreCase("jpeg")) {
                        orignalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    } else {
                        orignalBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    }
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    callWebService(encoded, mimeType);
                }
            }

        } catch (FileNotFoundException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.FILE_NOT_FOUND + "\")");
            //Log.e(e.toString());

        } catch (IOException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
            //Log.e(e.toString());
        } catch (NumberFormatException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
        } catch (Exception e) {

            if (mAgentWeb.getWebCreator().getWebView() != null)
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.PERMISSION_NOT_GRANTED + "\")");

            //Log.e(e.toString());
        }
    }

    /**
     * Handle Passport photo result
     *
     * @param intent
     */
    public void handlePassportPhotoResult(Intent intent) {
        try {
            final Uri imageUri = intent.getData();
            //Log.e("Select image URI", imageUri.getPath());
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final InputStream imageStream2 = getContentResolver().openInputStream(imageUri);
            Bitmap orignalBitmap = decodeSampledBitmapFromInputStream(imageStream, imageStream2, 500, 500);
            CropImageId = 4;
            CropImage.activity(getImageUri(this, orignalBitmap))
                    .setAspectRatio(135, 145)
                    .setFixAspectRatio(true)
                    .setMaxCropResultSize(600, 600)
                    .setOutputCompressQuality(70)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        } catch (FileNotFoundException e) {
            //Log.e(e.toString());
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.FILE_NOT_FOUND + "\")");
        } catch (Exception e) {
            //Log.e(e.toString());
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.PERMISSION_NOT_GRANTED + "\")");
        }
    }


    /**
     * Method to handle signature file
     *
     * @param intent
     */
    public void handleSignatureFileResult(Intent intent) {
        final Uri imageUri = intent.getData();
        //Log.e("Select image URI", imageUri.getPath());
        final InputStream imageStream;
        try {
            imageStream = getContentResolver().openInputStream(imageUri);

            if (isSizeGood(imageStream, 60)) {

                if (!checkMinSize(imageStream, 3)) {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_TOO_SMALL + "\")");
                    return;
                }
                byte[] targetArray = new byte[imageStream.available()];
                imageStream.read(targetArray);
                String encodedStr = Base64.encodeToString(targetArray, Base64.DEFAULT);
                callWebService(encodedStr, "JPG");
                return;
            }

            final InputStream imageStream2 = getContentResolver().openInputStream(imageUri);
            Bitmap orignalBitmap = decodeSampledBitmapFromInputStream(imageStream, imageStream2, 400, 300);
            CropImageId = 3;
            CropImage.activity(getImageUri(this, orignalBitmap))
                    .setOutputCompressQuality(70)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        } catch (FileNotFoundException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.FILE_NOT_FOUND + "\")");
            //Log.e(e.toString());
        } catch (Exception e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.PERMISSION_NOT_GRANTED + "\")");
            //Log.e(e.toString());
        }
    }


    /**
     * Method to handle biometric auth for department
     *
     * @param intent
     */
    public void handleBioMetricAuthDepResult(Intent intent) {
        String strWithEscape = "";
        if (intent.getExtras().containsKey("BIO_AUTH_DATA")) {
            strWithEscape = intent.getStringExtra("BIO_AUTH_DATA");
        }
        //Log.d(TAG, "SCAN RESULT ====  " + strWithEscape);
        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:scanResult(\"" + strWithEscape.replaceAll("\"", "\\\\\\\"") + "\")");
    }


    /**
     * Method to handle Biometric device registration steps result
     */
    public void handleBioMetricRegistrationStepsResult(Intent intent) {
        if (intent.getExtras().containsKey("MORPHO_SELECTED")) {
            getDeviceInfoRD(intent.getStringExtra("SUCCESS_CALLBACK"), intent.getStringExtra("FAILURE_CALLBACK"));
        }
        if (intent.getExtras().containsKey("MANTRA_SELECTED")) {
            getDeviceInfoRD(intent.getStringExtra("SUCCESS_CALLBACK"), intent.getStringExtra("FAILURE_CALLBACK"));
        }
    }

    /**
     * Method to handle barcode scan result
     *
     * @param intent
     */
    public void handleBarcodeScannerResult(Intent intent) {
        if (intent.getStringExtra("scanResult") != null) {
            sendLogs(intent.getStringExtra("scanResult"));
        }
    }


    /**
     * method to handle common image capture
     */
    public void handleCommonImageCaptureResult() {
        // loadurl on UI main thread
        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("UmangSdkPref", 0);
                        String tempUri= pref.getString(Constants.PREF_CAMERA_IMAGE_URI,imageSelectedPath);
                        Uri uri = Uri.parse(tempUri);
                        Bitmap bitmap = ImageUtils.rescaleImage(UmangWebActivity.this, Uri.parse(tempUri), 500);

                        if (mIsCrop) {
                            boolean aspectRatio = true;
                            if (mCropWidth == 0 || mCropHeight == 0) {
                                aspectRatio = false;
                            }
                            CropImageId = 5;
                            CropImage.ActivityBuilder builder = CropImage.activity(getImageUri(UmangWebActivity.this, bitmap));

                            if (mCropType.equalsIgnoreCase("aspect")) {
                                builder.setAspectRatio(mCropHeight, mCropWidth);
                                builder.setFixAspectRatio(aspectRatio);

                            } else if (mCropType.equalsIgnoreCase("pixel")) {
                                builder.setMinCropResultSize(mCropWidth, mCropHeight);

                            }
                            builder.setGuidelines(CropImageView.Guidelines.ON)
                                    .start(UmangWebActivity.this);
                        } else {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            final InputStream imageStream = new ByteArrayInputStream(stream.toByteArray());
                            if (isSizeGood(imageStream, Integer.getInteger(MaxSize, 200))) {
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                String encodedStr = ImageUtils.encodeImage(selectedImage);
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackSuccessFunction + "(\"data:image/png;base64," + encodedStr + "\")");
                            } else {
                                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IMAGE_SIZE_EXCEEDED + "\")");
                            }
                        }
                    } catch (IOException e) {
                        //Log.e(e.toString());
                        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
                    } catch (Exception e) {
                        //Log.e(e.toString());
                        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + Constants.IO_EXCEPTION + "\")");
                    }
                }
            });
        } catch (Exception e) {
            //Log.e(e.toString());

            if (true) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UmangWebActivity.this,getString(R.string.please_try_again),Toast.LENGTH_LONG).show();
                    }
                });
            }


        }
    }

    /**
     * Method to handle gallery image result
     *
     * @param intent
     */
    void handleCommonGalleryImageResult(Intent intent) {
        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Uri uri = intent.getData();

                    Bitmap bitmap = null;
                    try {
                        bitmap = ImageUtils.rescaleImage(UmangWebActivity.this, uri, 500);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (mIsCrop) {
                        boolean aspectRatio = true;
                        if (mCropWidth == 0 || mCropHeight == 0) {
                            aspectRatio = false;
                        }
                        CropImageId = 5;
                        CropImage.ActivityBuilder builder = CropImage.activity(uri);

                        if (mCropType.equalsIgnoreCase("aspect")) {
                            builder.setAspectRatio(mCropHeight, mCropWidth);
                            builder.setFixAspectRatio(aspectRatio);

                        } else if (mCropType.equalsIgnoreCase("pixel")) {
                            builder.setMinCropResultSize(mCropWidth, mCropHeight);

                        }

                        builder.setMaxCropResultSize(2000, 2000);
                        builder.setGuidelines(CropImageView.Guidelines.ON)
                                .start(UmangWebActivity.this);
                    } else {
                        String mimetype = getMineType(uri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        final InputStream imageStream = new ByteArrayInputStream(stream.toByteArray());

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                        if (mimetype.equalsIgnoreCase("jpg") || mimetype.equalsIgnoreCase("jpeg")) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        } else {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        }
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        callWebService(encoded, mimetype);
                    }
                }
            });
        } catch (Exception e) {

        }
    }


    /**
     * Method to show custom dialog
     *
     * @param title  - dialog title
     * @param msg    - dialog message
     * @param yesBtn - dialog yes button text
     * @param noBtn  - dialog no button text
     * @param type   - type for which dialog opened
     */
    public void openDialog(String title, String msg, String yesBtn, String noBtn, String type) {
        CustomDialog dialog = CustomDialog.newInstance(title, msg, yesBtn, noBtn, type);
        dialog.setDialogButtonClickListener(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager());
    }

    @Override
    public void onOkClick(String type) {
        switch (type) {
            case "PERMISSION":
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                break;
            case "LOCATION_DISABLE":
                Intent intent2 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent2);
                break;
            case "FBREADER":
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Constants.FBREADER_PACKAGE));
                startActivity(i);
                break;

        }
    }

    @Override
    public void onCancelClick(String type) {

        switch (type){
            case "LOCATION_DISABLE":
                sendLocationCallBack("F", "", LocationInterface.locationResponse);
                break;
        }
    }



    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    sendLocationCallBack("S", "" + location.getLatitude() + "," + location.getLongitude(), LocationInterface.locationResponse);
                                }
                            }
                        }
                );
            } else {
                String TYPE = "PERMISSION";
                openDialog(getResources().getString(R.string.permission_required),
                        getResources().getString(R.string.denied_location_permission_help_text),
                        getResources().getString(R.string.ok),
                        getResources().getString(R.string.cancel_caps),
                        TYPE);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            sendLocationCallBack("S", "" + mLastLocation.getLatitude() + "," + mLastLocation.getLongitude(), LocationInterface.locationResponse);
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                Constants.MY_PERMISSIONS_LOCATION
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    /**
     *  From: - Location Interface
     *  Method to send location to web
     * @param action
     * @param latlng
     * @param s
     */
    public void sendLocationCallBack(final String action, final String latlng, final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (action == "S") {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + s + "(\"" + latlng + "\")");
                } else {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + s + "(\"0.0,0.0\")");

                }
            }
        });
    }


    /**
     * Method to send Contacts to WEB
     *
     * @param action
     * @param json
     * @param s
     * @param f
     */
    public void sendContacts(final String action, final String json, final String s, final String f) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (action == "S") {
                    String strWithEscape = json.replaceAll("\"", "\\\\\\\"");

                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + s + "(\"" + strWithEscape + "\")");
                } else {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + f + "(\"FAIL\")");

                }
            }
        });
    }


    public  void openEmailShareWithAttachment(String emailAddrss) {

        String downloadFilePath = "file:////storage/emulated/0/Download/android-app-testing.pdf";
        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.setType("application/octet-stream");
        mail.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mail.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddrss});
        mail.putExtra(Intent.EXTRA_SUBJECT, "");
        mail.putExtra(Intent.EXTRA_TEXT, "");
        mail.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + downloadFilePath));
        startActivity(mail);
    }


    public void sendPANFileDownloadCallback(final String methodName, final String response) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {


                if(mAgentWeb !=null)
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + methodName + "(\"" + response + "\")");

            }
        });
    }


    class CustomWebChromeClient extends WebChromeClient{

        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.d("CUSTOMADVANCED", "openFileChooser====" + acceptType);
            openFileInput(uploadMsg, null, false);
        }

        @SuppressWarnings("all")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
            if (Build.VERSION.SDK_INT >= 21) {


                final boolean allowMultiple = fileChooserParams.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE;

                openFileInput(null, filePathCallback, allowMultiple);

                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            //super.onGeolocationPermissionsShowPrompt(origin, callback);
            callback.invoke(origin, true, false);
        }


        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }


        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (videoView != null) {
                callback.onCustomViewHidden();
                return;
            }
            getSupportActionBar().hide();
            runOnUiThread(new Runnable() {
                public void run() {

                    //stuff that updates ui
                    UmangWebActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    UmangWebActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            });
            videoView = view;
            if(mAgentWeb !=null)
                mAgentWeb.getWebCreator().getWebView().setVisibility(View.GONE);
            binding.customViewContainer.setVisibility(View.VISIBLE);
            binding.customViewContainer.addView(view);
            videoViewCallback = callback;

        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.

            getSupportActionBar().show();

            runOnUiThread(new Runnable() {
                public void run() {

                    //stuff that updates ui
                    UmangWebActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    UmangWebActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            });
            if (videoView == null)
                return;

            if(mAgentWeb !=null)
                mAgentWeb.getWebCreator().getWebView().setVisibility(View.VISIBLE);

            binding.customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            videoView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            binding.customViewContainer.removeView(videoView);
            videoViewCallback.onCustomViewHidden();

            videoView = null;
        }
    }

    /**
     * Method to convert inputstream to base64
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private String convertTobase64(InputStream inputStream) throws IOException {

        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        output64.close();

        return output.toString();
    }


    public void sendMHAFile(Uri data, boolean needFileName) {
        try {
            ContentResolver cR = getContentResolver();
            String type = cR.getType(data);

            InputStream inputStream = getContentResolver().openInputStream(data);

            byte[] targetArray = new byte[inputStream.available()];
            if (isSizeGood(targetArray, Integer.getInteger(MaxSize, 5000))) {

                try {
                    String encodedStr = convertTobase64(inputStream);

                    encodedStr = encodedStr.replaceAll("\\s+", "");
                    if (needFileName) {

                        Cursor returnCursor = getContentResolver().query(data, null, null, null, null);
                        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        returnCursor.moveToFirst();
                        String fileName = returnCursor.getString(nameIndex);
                        returnCursor.close();

                        Toast.makeText(this, "File name:-" + fileName, Toast.LENGTH_SHORT).show();
                        JSONObject obj = new JSONObject();
                        obj.put("fileName", fileName);
                        obj.put("fileData", "data:" + type + ";base64," + encodedStr);

                        mAgentWeb.getWebCreator().getWebView().evaluateJavascript("javascript:" + callBackSuccessFunction + "(" + obj.toString() + ")", null);
                    } else {
                        mAgentWeb.getWebCreator().getWebView().evaluateJavascript("javascript:" + callBackSuccessFunction + "(\"data:" + type + ";base64," + encodedStr + "\")", null);
                    }

                } catch (Exception e) {
                    mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + "IO_EXCEPTION" + "\")");
                }
            } else {
                mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + "FILE_SIZE_EXCEEDED" + "\")");
            }
        } catch (FileNotFoundException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + "FILE_NOT_FOUND" + "\")");
        } catch (IOException e) {
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:" + callBackFailureFunction + "(\"" + "IO_EXCEPTION" + "\")");
        }
    }

}