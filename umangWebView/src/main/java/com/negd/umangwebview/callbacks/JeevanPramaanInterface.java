package com.negd.umangwebview.callbacks;

import static com.negd.umangwebview.utils.Constants.BIOMETRIC_DEVICE_INFO;
import static com.negd.umangwebview.utils.Constants.BIO_RD_INFO_REQUEST_CODE;
import static com.negd.umangwebview.utils.Constants.DEVICE_SCAN_REQUEST_CODE;
import static com.negd.umangwebview.utils.Constants.FACE_SCAN_REQUEST_CODE;
import static com.negd.umangwebview.utils.Constants.FACE_SCAN_XML_REQUEST_CODE;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.negd.umangwebview.R;
import com.negd.umangwebview.listeners.IJeevanPramaanListener;
import com.negd.umangwebview.ui.UmangWebActivity;
import com.negd.umangwebview.ui.jeevan_pramaan.JPDeviceSelectActivity;
import com.negd.umangwebview.utils.AppConstants;
import com.negd.umangwebview.utils.AppLogger;
import com.negd.umangwebview.utils.CommonUtils;
import com.negd.umangwebview.utils.Constants;

import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class JeevanPramaanInterface {

    private UmangWebActivity activity;
    private Uri mCapturedImageURI;
    private IJeevanPramaanListener listener;
    private String TAG = JeevanPramaanInterface.class.getSimpleName();
    private final ActivityResultLauncher<Intent> bioRdInfoRequestLauncher;
    private final ActivityResultLauncher<Intent> biometricDeviceInfoRequestLauncher;
    private final ActivityResultLauncher<Intent> deviceScanRequestLauncher;
    private final ActivityResultLauncher<Intent> faceScanRequestLauncher;
    private final ActivityResultLauncher<Intent> faceScanXMLRequestLauncher;

    public JeevanPramaanInterface(UmangWebActivity activity, IJeevanPramaanListener listener) {
        this.activity = activity;
        this.listener = listener;
        // Initialize the launchers
        bioRdInfoRequestLauncher = listener.getContext().registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleJeevanPramaanResult(result, BIO_RD_INFO_REQUEST_CODE)
        );

        biometricDeviceInfoRequestLauncher = listener.getContext().registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleJeevanPramaanResult(result, BIOMETRIC_DEVICE_INFO)
        );

        deviceScanRequestLauncher = listener.getContext().registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleJeevanPramaanResult(result, DEVICE_SCAN_REQUEST_CODE)
        );

        faceScanRequestLauncher = listener.getContext().registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleJeevanPramaanResult(result, FACE_SCAN_REQUEST_CODE)
        );

        faceScanXMLRequestLauncher = listener.getContext().registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleJeevanPramaanResult(result, FACE_SCAN_XML_REQUEST_CODE)
        );
    }

    /**
     * To check if Biometric device connected or not
     *
     * @return {"is_device_connected":"true"} / {"is_device_connected":"false"}
     */
    @JavascriptInterface
    public String checkDeviceConnected() {
        AppLogger.d(TAG, "checkDeviceConnected");
        if (checkIfBiometricDeviceConnected()) {
            return "{\"is_device_connected\":\"true\"}";
        } else {
            return "{\"is_device_connected\":\"false\"}";
        }
    }

    /**
     * Show Device Select Activity
     *
     * @param startScanning    Boolean flag to start scanning
     * @param successCallback  String callback for success in device selection
     * @param failureCallback  String callback for failure in device selection
     */
    @JavascriptInterface
    public void showDeviceChooserRD(boolean startScanning, String successCallback, String failureCallback) {
        AppLogger.d(TAG, "showDeviceChooserRD " + startScanning + " // " + successCallback + " // " + failureCallback);
        listener.setCallbackFunctions(successCallback, failureCallback);
        showDeviceChooserRD(successCallback, failureCallback);
    }

    /**
     * To Start biometric scanning by starting selected device intent.
     */
    @JavascriptInterface
    public void startScannerRD(String deviceType) {
        AppLogger.d(TAG, "startScannerRD-" + deviceType);
        showBioDeviceConnectionInfo();
    }

    /**
     * To redirect user to Play store for Mantra Device RD service.
     */
    @JavascriptInterface
    public void installMantraPackageRD() {
        AppLogger.d(TAG, "installMantraPackageRD");
        listener.redirectToPlayStore("com.mantra.rdservice");
    }

    /**
     * To redirect user to Play store for Morpho Device RD service.
     */
    @JavascriptInterface
    public void installMorphoPackageRD() {
        AppLogger.d(TAG, "installMorphoPackageRD");
        listener.redirectToPlayStore("com.scl.rdservice");
    }

    /**
     * To check if Aadhaar FaceRD service installed or not.
     *
     * @return {"is_device_connected":"true"} / {"is_device_connected":"false"}
     */
    @JavascriptInterface
    public String isFaceRdInstalled() {
        AppLogger.d(TAG, "isFaceRdInstalled");
        if (checkIfFaceRdInstalled()) {
            return "{\"is_device_connected\":\"true\"}";
        } else {
            return "{\"is_device_connected\":\"false\"}";
        }
    }

    /**
     * To Start face scanning by starting Aadhaar FaceRD Capture Intent.
     */
    @JavascriptInterface
    public void startFaceScannerRD() {
        AppLogger.d(TAG, "startFaceScannerRD");
        startFaceRdScanIntent(faceScanXMLRequestLauncher);
    }

    /**
     * To Start face scanning by starting Aadhaar FaceRD Capture Intent for XML.
     */
    @JavascriptInterface
    public void startFaceScannerRDForXml() {
        AppLogger.d(TAG, "startFaceScannerRDForXml");
        startFaceRdScanIntent(faceScanRequestLauncher);
    }

    /**
     * To redirect user to Play store for FaceRD Service.
     */
    @JavascriptInterface
    public void openFaceRdServicePlayStore() {
        AppLogger.d(TAG, "openRdServicePlayStore");
        listener.redirectToPlayStore("in.gov.uidai.facerd");
    }

    public void startBiometricSelectionScreen(String successCallback, String failureCallback) {
        Intent intent = new Intent(listener.getContext(), JPDeviceSelectActivity.class);
        intent.putExtra("successCallback", successCallback);
        intent.putExtra("failureCallback", failureCallback);

        listener.startActivityForResultWithLauncher(intent, bioRdInfoRequestLauncher);
        // Uncomment the below code if using startActivityForResult instead
    /*
    listener.startActivityForResult(
        new Intent(listener.getContext(), JPDeviceSelectActivity.class)
            .putExtra("successCallback", successCallback)
            .putExtra("failureCallback", failureCallback),
        BIO_RD_INFO_REQUEST_CODE
    );
    */
    }

    public void handleRdFaceCapture(Intent intent) throws Exception {
        String pidDataXML = intent.getStringExtra("response");
        if (pidDataXML != null) {
            if (pidDataXML.isEmpty()) {
                AppLogger.d(TAG, "Error occurred in PID DATA XML..................");
                listener.showInfoPopup("Not able to capture your face properly. Please try again.", 0, false);
                return;
            }

            if (pidDataXML.startsWith("ERROR:-")) {
                AppLogger.d(TAG, "ERROR............." + pidDataXML);
                listener.showInfoPopup("An error occurred while capturing your face. Please try again.", 0, false);
                return;
            }

            DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
            try {
                db.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                db.setFeature("http://xml.org/sax/features/external-general-entities", false);
                db.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            } catch (Exception ex) {
                // Handle the exception
            }

            Document inputDocument = db.newDocumentBuilder().parse(new InputSource(new StringReader(pidDataXML)));
            NodeList nodes = inputDocument.getElementsByTagName("PidData");
            if (nodes != null) {
                Element element = (Element) nodes.item(0);
                NodeList respNode = inputDocument.getElementsByTagName("Resp");
                if (respNode != null) {
                    Node respData = respNode.item(0);
                    NamedNodeMap attsResp = respData.getAttributes();
                    String errCodeStr = "0";
                    String errInfoStr = "";

                    Node errCode = attsResp.getNamedItem("errCode");
                    if (errCode != null) {
                        errCodeStr = errCode.getNodeValue();
                    }

                    Node errInfo = attsResp.getNamedItem("errInfo");
                    if (errInfo != null) {
                        errInfoStr = errInfo.getNodeValue();
                    }

                    if (Integer.parseInt(errCodeStr) > 0) {
                        AppLogger.d(TAG, "Capture error :- " + errCodeStr + " , " + errInfoStr);
                        listener.showInfoPopup(errInfoStr,0, false);
                        return;
                    }
                }
            }

            XPath xPath = XPathFactory.newInstance().newXPath();
            String errorCode = (String) xPath.compile("/PidData/Resp/@errCode").evaluate(inputDocument, XPathConstants.STRING);

            if (errorCode.equalsIgnoreCase("0")) {
                AppLogger.d(TAG, "SCAN RESULT ==== success");
                createPidDataJson("false", inputDocument, xPath);
            } else {
                AppLogger.d(TAG, "Error Info->....." + errorCode);
            }
        } else {
            AppLogger.d(TAG, "Scan Failure.................");
        }
    }

    // Handle RD Face Capture XML
    public void handleRdFaceCaptureXML(Intent intent) throws Exception {
        String pidDataXML = intent.getStringExtra("response");
        if (pidDataXML != null) {
            if (pidDataXML.isEmpty()) {
                AppLogger.d(TAG, "Error occurred in PID DATA XML..................");
                listener.showInfoPopup("Not able to capture your face properly. Please try again.",0, false);
                return;
            }

            if (pidDataXML.startsWith("ERROR:-")) {
                AppLogger.d(TAG, "ERROR............." + pidDataXML);
                listener.showInfoPopup("An error occurred while capturing your face. Please try again.",0, false);
                return;
            }

            DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
            try {
                db.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                db.setFeature("http://xml.org/sax/features/external-general-entities", false);
                db.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            } catch (Exception ex) {
                // Handle the exception
            }

            Document inputDocument = db.newDocumentBuilder().parse(new InputSource(new StringReader(pidDataXML)));
            NodeList nodes = inputDocument.getElementsByTagName("PidData");
            if (nodes != null) {
                Element element = (Element) nodes.item(0);
                NodeList respNode = inputDocument.getElementsByTagName("Resp");
                if (respNode != null) {
                    Node respData = respNode.item(0);
                    NamedNodeMap attsResp = respData.getAttributes();
                    String errCodeStr = "0";
                    String errInfoStr = "";

                    Node errCode = attsResp.getNamedItem("errCode");
                    if (errCode != null) {
                        errCodeStr = errCode.getNodeValue();
                    }

                    Node errInfo = attsResp.getNamedItem("errInfo");
                    if (errInfo != null) {
                        errInfoStr = errInfo.getNodeValue();
                    }

                    if (Integer.parseInt(errCodeStr) > 0) {
                        AppLogger.d(TAG, "Capture error :- " + errCodeStr + " , " + errInfoStr);
                        listener.showInfoPopup(errInfoStr, 0, false);
                        return;
                    }
                }
            }

            XPath xPath = XPathFactory.newInstance().newXPath();
            String errorCode = (String) xPath.compile("/PidData/Resp/@errCode").evaluate(inputDocument, XPathConstants.STRING);

            if (errorCode.equalsIgnoreCase("0")) {
                AppLogger.d(TAG, "SCAN RESULT ==== success");
                listener.loadWebUrl("javascript:scanResult(" + pidDataXML + ")");
            } else {
                AppLogger.d(TAG, "Error Info->....." + errorCode);
            }
        } else {
            AppLogger.d(TAG, "Scan Failure.................");
        }
    }


    // Check if biometric device is connected
    private boolean checkIfBiometricDeviceConnected() {
        UsbManager manager = (UsbManager) listener.getContext().getSystemService(Context.USB_SERVICE);
        HashMap<String, android.hardware.usb.UsbDevice> deviceList = manager.getDeviceList();
        return deviceList.size() > 0;
    }

    // Show device chooser RD
    private void showDeviceChooserRD(String successCallback, String failureCallback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(listener.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(listener.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show permission rationale dialog
                CommonUtils.openPermissionSettingsDialog(
                        listener.getContext(),
                        listener.getContext().getResources().getString(R.string.allow_write_storage_permission_help_text)
                );
            } else {
                ActivityCompat.requestPermissions(
                        listener.getContext(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        AppConstants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_MORPHO_RD
                );
            }
        } else {
            startBiometricSelectionScreen(successCallback, failureCallback);
        }
    }

    // Show bio device connection info
    public void showBioDeviceConnectionInfo() {
        UsbManager manager = (UsbManager) listener.getContext().getSystemService(Context.USB_SERVICE);
        HashMap<String, android.hardware.usb.UsbDevice> deviceList = manager.getDeviceList();
        AppLogger.i(TAG, "DeviceDetected == >> " + deviceList.size());

        if (deviceList.size() > 0) {
            startDeviceScanIntent();
        } else {
            listener.getContext().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        listener.loadWebUrl("javascript:showWrongDeviceError()");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
            * Check if the Face RD service is installed.
            *
            * @return true if the Face RD service is installed
     */
    public boolean checkIfFaceRdInstalled() {
        try {
            PackageInfo packageInfo = listener.getContext().getPackageManager().getPackageInfo("in.gov.uidai.facerd", 0);
            return packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Jeevan Praman (JP)
     * Method to scan connected Bio Devices
     */
    private void startDeviceScanIntent() {
        try {
            String pidOption = generatePidOptXml();
            if (pidOption != null) {
                AppLogger.e(TAG, "pidOptions..." + pidOption);
                Intent intent = new Intent();
                intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                intent.putExtra("PID_OPTIONS", pidOption);
                listener.startActivityForResultWithLauncher(intent, deviceScanRequestLauncher);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace with appropriate logging
        }
    }

    private void startFaceRdScanIntent(ActivityResultLauncher<Intent> launcher) {
        try {
            String pidOption = generatePidOptXml();
            if (pidOption != null) {
                AppLogger.i(TAG, "pidOptions..." + pidOption);
                Intent faceCaptureIntent = new Intent();
                String txnId = UUID.randomUUID().toString() + new Date().getTime();
                String pidOptions = createPidOptions(txnId, "auth");
                faceCaptureIntent.setAction("in.gov.uidai.rdservice.face.CAPTURE")
                        .putExtra("request", pidOptions);
                PackageManager packageManager = listener.getContext().getPackageManager();
                List<?> activities = packageManager.queryIntentActivities(faceCaptureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) {
                    listener.startActivityForResultWithLauncher(faceCaptureIntent, launcher);
                } else {
                    listener.showInfoPopup("RD service not found.", 0, false);
                }
            }
        } catch (Exception e) {
            AppLogger.i(TAG, "Error..." + e.toString());
        }
    }

    /**
     * Jeevan Praman (JP)
     * Method to open RD service
     *
     * @param successCallback
     * @param failureCallback
     */
    public void getDeviceInfoRD(String successCallback, String failureCallback) {
        UsbManager manager = (UsbManager) listener.getContext().getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        AppLogger.i("DeviceDetected == >>", "" + deviceList.size());

        if (deviceList.size() > 0) {
            try {
                listener.startActivityForResultWithLauncher(
                        new Intent("in.gov.uidai.rdservice.fp.INFO"),
                        biometricDeviceInfoRequestLauncher);
            } catch (ActivityNotFoundException e) {
                listener.showToastMessage(R.string.bio_other_device_txt1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            listener.getContext().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.loadWebUrl("javascript:wrongDeviceSelected(\"biometric\")");
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
    public void handleBiometricDeviceInfo(Intent intent) throws Exception {
        String rdInfo = intent.getStringExtra("RD_SERVICE_INFO");
        AppLogger.i(TAG, "rd_info...." + rdInfo);

        if (rdInfo != null && rdInfo.contains("NOTREADY")) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // disable external entities
            try {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            CommonUtils.showInfoDialogHtml(
                    listener.getContext(),
                    "<b>" + factory.newDocumentBuilder().parse(new InputSource(new StringReader(rdInfo)))
                            .getElementsByTagName("RDService")
                            .item(0).getAttributes().getNamedItem("info").getNodeValue() + "</b><br /><br />" +
                            "Please make sure you have selected the correct RD service app for the connected biometric device."
            );
            AppLogger.d(TAG, "not ready..........." + rdInfo);
        } else {
            String deviceInfoXml = intent.getStringExtra("DEVICE_INFO");
            AppLogger.d(TAG, "DEVICE_INFO...." + deviceInfoXml);

            if (deviceInfoXml != null) {
                if (deviceInfoXml.isEmpty()) {
                    AppLogger.d(TAG, "Error occurred in DeviceInfo DATA XML..............");
                    return;
                }
                if (deviceInfoXml.startsWith("ERROR:-")) {
                    AppLogger.d(TAG, "ERROR.............." + deviceInfoXml);
                    return;
                }
            }

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document inputDocument = builder.parse(new InputSource(new StringReader(rdInfo)));
            String status = inputDocument.getElementsByTagName("RDService")
                    .item(0).getAttributes().getNamedItem("status").getNodeValue();

            if (deviceInfoXml != null && !deviceInfoXml.isEmpty()) {
                AppLogger.d(TAG, "DEVICE INFO XML............." + deviceInfoXml);
            }

            if (status != null && status.equals("READY")) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc2 = dBuilder.parse(new InputSource(new StringReader(deviceInfoXml)));

                // XPath to retrieve the content of the <FamilyAnnualDeductibleAmount> tag
                XPathFactory xpathFactory = XPathFactory.newInstance();
                String deviceMake = (String) xpathFactory.newXPath().compile("/DeviceInfo/@dpId").evaluate(doc2, XPathConstants.STRING);
                AppLogger.d(TAG, "deviceMake : " + deviceMake);

                String rdsId = (String) xpathFactory.newXPath().compile("/DeviceInfo/@rdsId").evaluate(doc2, XPathConstants.STRING);
                AppLogger.d(TAG, "rdsId : " + rdsId);

                String rdsVer = (String) xpathFactory.newXPath().compile("/DeviceInfo/@rdsVer").evaluate(doc2, XPathConstants.STRING);
                AppLogger.d(TAG, "rdsVer : " + rdsVer);

                String dc = (String) xpathFactory.newXPath().compile("/DeviceInfo/@dc").evaluate(doc2, XPathConstants.STRING);
                AppLogger.d(TAG, "dc : " + dc);

                String mc = (String) xpathFactory.newXPath().compile("/DeviceInfo/@mc").evaluate(doc2, XPathConstants.STRING);
                AppLogger.d(TAG, "mc : " + mc);

                String deviceModel = (String) xpathFactory.newXPath().compile("/DeviceInfo/@mi").evaluate(doc2, XPathConstants.STRING);
                AppLogger.d(TAG, "deviceModel : " + deviceModel);

                String deviceVendor = (String) xpathFactory.newXPath().compile("/DeviceInfo/@dpId").evaluate(doc2, XPathConstants.STRING);
                AppLogger.d(TAG, "deviceVendor : " + deviceVendor);

                String serialNumber = (String) xpathFactory.newXPath().compile("/DeviceInfo/@srno").evaluate(doc2, XPathConstants.STRING);
                AppLogger.d(TAG, "serialNumber : " + serialNumber);

                final String connectedDeviceValues = deviceMake.trim() + deviceModel.trim() + deviceVendor.trim() + serialNumber.trim();
                listener.getContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject();
                            obj.put("make", deviceMake);
                            obj.put("model", deviceModel);
                            obj.put("srno", serialNumber);
                            obj.put(AppConstants.RD_SLD, rdsId);
                            obj.put(AppConstants.RD_VER, rdsVer);
                            obj.put(AppConstants.DPLD, deviceMake);
                            obj.put(AppConstants.DC, dc);
                            obj.put(AppConstants.MI, deviceModel);
                            obj.put(AppConstants.MC, mc);
                            AppLogger.d(TAG, "Device JSON Obj === " + obj);
                            String strWithEscape = obj.toString().replace("\"", "\\\\\"");
                            listener.loadWebUrl("javascript:" + listener.getSuccessCallbackFunction() + "(\"" + strWithEscape + "\")");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /**
     * Jeevan Praman (JP)
     * Method to send Fingerprints to Web
     *
     * @param intent The Intent containing PID data.
     * @throws Exception for any parsing errors.
     */
    public void handleUserFingerPrint(Intent intent) throws Exception {
        String pidDataXML = intent.getStringExtra("PID_DATA");
        AppLogger.d(TAG, "pid data....." + pidDataXML);

        if (pidDataXML == null || pidDataXML.isEmpty()) {
            AppLogger.d(TAG, "Error occurred in PID DATA XML..................");
            listener.showInfoPopup("Not able to get your fingerprint properly. Please try again.",0, false);
            return;
        }

        if (pidDataXML.startsWith("ERROR:-")) {
            AppLogger.d(TAG, "ERROR............." + pidDataXML);
            listener.showInfoPopup("An error occurred while capturing your fingerprint. Please try again.",0, false);
            return;
        }

        // Disable external entities in XML parsing
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        } catch (Exception ex) {
            // Log or handle exception as needed
        }

        Document inputDocument = dbFactory.newDocumentBuilder().parse(new InputSource(new StringReader(pidDataXML)));
        NodeList nodes = inputDocument.getElementsByTagName("PidData");

        if (nodes.getLength() > 0) {
            Element element = (Element) nodes.item(0);
            Node respNode = inputDocument.getElementsByTagName("Resp").item(0);

            NamedNodeMap attsResp = respNode.getAttributes();
            String errCode = attsResp.getNamedItem("errCode") != null ? attsResp.getNamedItem("errCode").getNodeValue() : "0";
            String errInfo = attsResp.getNamedItem("errInfo") != null ? attsResp.getNamedItem("errInfo").getNodeValue() : "Unknown Error";

            if (Integer.parseInt(errCode) > 0) {
                AppLogger.d(TAG, "Capture error: " + errCode + ", " + errInfo);
                listener.showInfoPopup(errInfo,0, false);
                return;
            }
        }

        // Continue with processing if no errors
        Document doc2 = dbFactory.newDocumentBuilder().parse(new InputSource(new StringReader(pidDataXML)));
        XPath xpath = XPathFactory.newInstance().newXPath();

        String resp = (String) xpath.compile("/PidData/Resp/@errCode").evaluate(doc2, XPathConstants.STRING);
        AppLogger.d("errCode", "-->" + resp);

        if (resp.equals("0")) {
            JSONObject deviceInfo = new JSONObject();
            deviceInfo.put(AppConstants.HMAC, xpath.compile("/PidData/Hmac/text()").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.SKEY, xpath.compile("/PidData/Skey/text()").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.DATA, xpath.compile("/PidData/Data/text()").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.CI, xpath.compile("/PidData/Skey/@ci").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.RD_SLD, xpath.compile("/PidData/DeviceInfo/@rdsId").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.RD_VER, xpath.compile("/PidData/DeviceInfo/@rdsVer").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.DPLD, xpath.compile("/PidData/DeviceInfo/@dpId").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.DC, xpath.compile("/PidData/DeviceInfo/@dc").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.MI, xpath.compile("/PidData/DeviceInfo/@mi").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put(AppConstants.MC, xpath.compile("/PidData/DeviceInfo/@mc").evaluate(doc2, XPathConstants.STRING));
            deviceInfo.put("pidDataBio", "true");

            String strWithEscape = deviceInfo.toString();
            AppLogger.d(TAG, "SCAN RESULT ====  " + strWithEscape);
            listener.loadWebUrl("javascript:scanResult(" + strWithEscape + ")");
        } else {
            AppLogger.d(TAG, "Error Info->....." + resp);
        }
    }

    private String createPidOptions(String txnId, String purpose) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<PidOptions ver=\"1.0\" env=\"P\">\n" +
                "   <Opts fCount=\"\" fType=\"\" iCount=\"\" iType=\"\" pCount=\"\" pType=\"\" format=\"\" pidVer=\"2.0\" timeout=\"\" otp=\"\" wadh=\"sgydIC09zzy6f8Lb3xaAqzKquKe9lFcNR9uTvYxFp+A=\" posh=\"\" />\n" +
                "   <CustOpts>\n" +
                "      <Param name=\"txnId\" value=\"" + txnId + "\"/>\n" +
                "      <Param name=\"purpose\" value=\"" + purpose + "\"/>\n" +
                "   </CustOpts>\n" +
                "</PidOptions>";
    }

    /**
     * Jeevan Praman (JP)
     * Method to generate connected bio device PID
     *
     * @return
     */
    private String generatePidOptXml() {
        String tmpOptXml = "";
        String pidFormate = "0";
        String environment = "P";
        int fingsToCap = 1;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//            // disable external entities CHECK HERE
            try {
                docFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                docFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                docFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            } catch (Exception ex) {

            }


            docFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = null;

            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);

            Element rootElement = doc.createElement("PidOptions");
            doc.appendChild(rootElement);

            Attr attrVer = doc.createAttribute("ver");
            attrVer.setValue("1.0");
            rootElement.setAttributeNode(attrVer);

            Element opts = doc.createElement("Opts");
            rootElement.appendChild(opts);

            Attr attr = doc.createAttribute("fCount");
            attr.setValue(String.valueOf(fingsToCap));
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("fType");
            attr.setValue("2");
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("iCount");
            attr.setValue("0");
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("iType");
            attr.setValue("0");
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("pCount");
            attr.setValue("0");
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("pType");
            attr.setValue("0");
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("format");
            attr.setValue(pidFormate);
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("pidVer");
            attr.setValue("2.0");
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("timeout");
            attr.setValue("5000");
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("otp");
            attr.setValue("");

            attr = doc.createAttribute("env");
            attr.setValue(environment);
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("wadh");
            attr.setValue(mywadh(""));
            opts.setAttributeNode(attr);

            attr = doc.createAttribute("posh");
            attr.setValue("UNKNOWN");
            opts.setAttributeNode(attr);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);

            tmpOptXml = writer.getBuffer().toString().replaceAll("\n|\r", "");
            tmpOptXml = tmpOptXml.replaceAll("&lt;", "<").replaceAll("&gt;", ">");

            tmpOptXml = tmpOptXml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

            return tmpOptXml;
        } catch (ParserConfigurationException e) {
            return "ERROR in PID formation";
        } catch (TransformerConfigurationException e) {
            return "ERROR in PID formation";
        } catch (TransformerException e) {
            return "ERROR  in PID formation";
        }

    }

    /**
     * Jeevan Praman (JP)
     * Method to get the wadh for JP
     *
     * @param myval
     * @return
     */
    public String mywadh(String myval) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
        }

        String ra = "F";
        String rc = "Y";
        String lr = "Y";
        String de = "N";
        String pfr = "N";

        String jvwadh = "2.5";

        String text = jvwadh + ra + rc + lr + de + pfr;
        if (md != null) {
            if (text != null) {
                try {
                    md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
                } catch (UnsupportedEncodingException e) {
                }
            } else
                return "";

            byte[] digest = md.digest();
            myval = Base64.encodeToString(digest, Base64.NO_WRAP);
        } else {
            return "";
        }
        return myval;
    }
    private void createPidDataJson(String pidDataBio, Document document, XPath xPath) throws Exception {
        AppLogger.d(TAG, "SCAN RESULT ====  createPidDataJson");
        XPathExpression expImei = xPath.compile("/PidData/DeviceInfo/@dpId");
        String dpid = (String) expImei.evaluate(document, XPathConstants.STRING);

        XPathExpression expci = xPath.compile("/PidData/Skey/@ci");
        String ci = (String) expci.evaluate(document, XPathConstants.STRING);

        XPathExpression expSkey = xPath.compile("/PidData/Skey/text()");
        String Skey = (String) expSkey.evaluate(document, XPathConstants.STRING);

        XPathExpression expHmac = xPath.compile("/PidData/Hmac/text()");
        String Hmac = (String) expHmac.evaluate(document, XPathConstants.STRING);

        XPathExpression expData = xPath.compile("/PidData/Data/text()");
        String Data = (String) expData.evaluate(document, XPathConstants.STRING);

        XPathExpression experror = xPath.compile("/PidData/Resp/@errCode");
        String errorCode = (String) experror.evaluate(document, XPathConstants.STRING);

        XPathExpression expdc = xPath.compile("/PidData/DeviceInfo/@dc");
        String dc = (String) expdc.evaluate(document, XPathConstants.STRING);

        XPathExpression expmi = xPath.compile("/PidData/DeviceInfo/@mi");
        String mi = (String) expmi.evaluate(document, XPathConstants.STRING);

        XPathExpression expmc = xPath.compile("/PidData/DeviceInfo/@mc");
        String mc = (String) expmc.evaluate(document, XPathConstants.STRING);

        XPathExpression exprdsId = xPath.compile("/PidData/DeviceInfo/@rdsId");
        String rdsId = (String) exprdsId.evaluate(document, XPathConstants.STRING);

        XPathExpression exprdsVer = xPath.compile("/PidData/DeviceInfo/@rdsVer");
        String rdsVer = (String) exprdsVer.evaluate(document, XPathConstants.STRING);


        JSONObject dataJson = new JSONObject();
        dataJson.put(AppConstants.HMAC, Hmac);
        dataJson.put(AppConstants.SKEY, Skey);
        dataJson.put(AppConstants.DATA, Data);
        dataJson.put(AppConstants.CI, ci);
        dataJson.put(AppConstants.RD_SLD, rdsId);
        dataJson.put(AppConstants.RD_VER, rdsVer);
        dataJson.put(AppConstants.DPLD, dpid);
        dataJson.put(AppConstants.DC, dc);
        dataJson.put(AppConstants.MI, mi);
        dataJson.put(AppConstants.MC, mc);
        dataJson.put("pidDataBio", pidDataBio);

        Gson gson = new Gson();
        String strWithEscape = gson.toJson(dataJson);
        listener.loadWebUrl("javascript:scanResult($strWithEscape)");
    }

    private void handleJeevanPramaanResult(ActivityResult result, int requestCode) {
        if (listener.getContext().isFinishing()) {
            return;
        }

        if (result.getResultCode() == AppCompatActivity.RESULT_CANCELED || result.getResultCode() == AppCompatActivity.RESULT_FIRST_USER) {
            return;
        }

        Intent resultIntent = result.getData();
        if (resultIntent == null) {
            return;
        }

        switch (requestCode) {
            case BIO_RD_INFO_REQUEST_CODE:
                try {
                    String deviceSelected = resultIntent.getStringExtra("deviceSelected");
                    String successCallback = resultIntent.getStringExtra("successCallback");
                    String failureCallback = resultIntent.getStringExtra("failureCallback");
                    getDeviceInfoRD(successCallback != null ? successCallback : "", failureCallback != null ? failureCallback : "");
                } catch (Exception e) {
                    AppLogger.e(TAG, "ERROR -> BIO_RD_INFO_REQUEST_CODE", e);
                }
                break;

            case BIOMETRIC_DEVICE_INFO:
                try {
                    // Send device info to web
                    handleBiometricDeviceInfo(resultIntent);
                } catch (Exception e) {
                    listener.showInfoPopup("Please check your biometric device is properly connected and try again.", 0, false);
                    AppLogger.e(TAG, "Error while deserialize device info: " + e);
                }
                break;

            case DEVICE_SCAN_REQUEST_CODE:
                try {
                    // Handle device info
                    handleUserFingerPrint(resultIntent);
                } catch (Exception e) {
                    listener.showInfoPopup("Not able to capture your fingerprint properly, please try again.", 0, false);
                    AppLogger.e(TAG, "Error while user fingerprint capture: " + e);
                }
                break;

            case FACE_SCAN_REQUEST_CODE:
                try {
                    // Handle face capture info from RD service capture
                    handleRdFaceCapture(resultIntent);
                } catch (Exception e) {
                    listener.showInfoPopup("Not able to capture your fingerprint properly, please try again.", 0, false);
                    AppLogger.e(TAG, "Error while user face capture: " + e);
                }
                break;

            case FACE_SCAN_XML_REQUEST_CODE:
                try {
                    // Handle face capture info from RD service capture and send result back as XML
                    handleRdFaceCaptureXML(resultIntent);
                } catch (Exception e) {
                    listener.showInfoPopup("Not able to capture your fingerprint properly, please try again.", 0, false);
                    AppLogger.e(TAG, "Error while user face capture: " + e);
                }
                break;

            default:
                // Handle unexpected request codes, if needed
                break;
        }
    }

}
