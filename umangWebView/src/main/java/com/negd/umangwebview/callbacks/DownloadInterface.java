package com.negd.umangwebview.callbacks;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.negd.umangwebview.R;
import com.negd.umangwebview.ui.UmangWebActivity;
import com.negd.umangwebview.utils.Constants;
import com.negd.umangwebview.utils.FileUtils;
import com.negd.umangwebview.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class DownloadInterface {

    private String TAG = "DownloadInterface";
    private UmangWebActivity mAct;
    private String JSON_FILE_NAME = "ebooks_download.txt";
    private String mFoldername = "";
    public static String json;
    public static String chapterDownloadResponse;


    public DownloadInterface(UmangWebActivity mAct) {
        this.mAct = mAct;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mAct, toast, Toast.LENGTH_SHORT).show();
    }

    public static String digiUrl;
    public static String digiJson;

    @JavascriptInterface
    public void downloadFile(String url, String jsonString) {
        Log.d(TAG, "downloadFile ====>>" + url + "====" + jsonString);
        digiUrl = url;
        digiJson = jsonString;

        if (ContextCompat.checkSelfPermission(mAct,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mAct,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Utils.openPermissionSettingsDialog(mAct, mAct.getResources().getString(R.string.allow_write_storage_permission_help_text));

            } else {
                ActivityCompat.requestPermissions(mAct,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_READ_EXTERNAL_STORATE_DIGI);
            }
        } else {

            //startDigiLockerDownload();
        }
    }

    @JavascriptInterface
    public void downloadFileDept(String url, String jsonString) {
        Log.d(TAG, "downloadFileDept ====>>" + url + "====" + jsonString);
        digiUrl = url;
        digiJson = jsonString;

        if (ContextCompat.checkSelfPermission(mAct,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mAct,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    Toast.makeText(this, "Please allow the permission so that app can work properly", Toast.LENGTH_LONG).show();
                Utils.openPermissionSettingsDialog(mAct, mAct.getResources().getString(R.string.allow_write_storage_permission_help_text));

            } else {
                ActivityCompat.requestPermissions(mAct,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_READ_EXTERNAL_STORATE_DIGI);
            }
        } else {

            //startDigiLockerDownloadDept();
        }


    }

    @JavascriptInterface
    public void shareFile(String email) {
        mAct.openEmailShareWithAttachment(email);
    }

    @JavascriptInterface
    public void startChapterDownLoad(String json, String response) {

    }

    public static String chapterId;

    @JavascriptInterface
    public void openChapter(String chapterId) {

//        this.app = (FBReaderApplication) mAct.getApplication();

        Log.d(TAG, "openChapter......................." + chapterId);
        this.chapterId = chapterId;


        if (ContextCompat.checkSelfPermission(mAct,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "read permission not granted...........................");

            if (ActivityCompat.shouldShowRequestPermissionRationale(mAct,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d(TAG, "show permission dialog...........................");
//                    Toast.makeText(this, "Please allow the permission so that app can work properly", Toast.LENGTH_LONG).show();
                Utils.openPermissionSettingsDialog(mAct, mAct.getResources().getString(R.string.allow_read_storage_permission_help_text));

            } else {
                Log.d(TAG, "ask read permission...........................");
                ActivityCompat.requestPermissions(mAct,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_READ_EXTERNAL_STORATE);
            }
        } else {

            //openChapter();
        }

    }

    @JavascriptInterface
    public String getChaptersFromBookId(String bookId) throws InterruptedException {
        JSONArray jarr = new JSONArray();
//        String userId = mAct.getViewModel().getDataManager().getStringPreference(AppPreferencesHelper.PREF_USER_ID, "");
//        DataManager dataManager = mAct.getViewModel().getDataManager();
//        CompositeDisposable compositeDisposable = mAct.getViewModel().getCompositeDisposable();
//        CommonWebViewModel viewModel = mAct.getViewModel();
//
//        final CountDownLatch latch = new CountDownLatch(1);
//        compositeDisposable.add(dataManager
//                .getChaptersDataFromBookId(userId, bookId)
//                .subscribeOn(viewModel.getSchedulerProvider().io())
//                .observeOn(viewModel.getSchedulerProvider().ui())
//                .subscribe(response -> {
//                    try {
//                        for (int i = 0; i < response.size(); i++) {
//                            JSONObject job = new JSONObject();
//                            job.put("id", response.get(i).getcId());
//                            jarr.put(job);
//                        }
//                    } catch (Exception e) {
//                        Log.e(e.toString());
//                    }
//                    latch.countDown();
//                }, throwable -> {
//                    Log.e(throwable.getMessage(), throwable);
//                }));
//        latch.await();
        return jarr.toString();
    }

    @JavascriptInterface
    public String getAllBooksData() throws InterruptedException {

        JSONArray jarr = new JSONArray();
//        String userId = mAct.getViewModel().getDataManager().getStringPreference(AppPreferencesHelper.PREF_USER_ID, "");
//        DataManager dataManager = mAct.getViewModel().getDataManager();
//        CompositeDisposable compositeDisposable = mAct.getViewModel().getCompositeDisposable();
//        CommonWebViewModel viewModel = mAct.getViewModel();
//        final CountDownLatch latch = new CountDownLatch(1);
//        compositeDisposable.add(dataManager
//                .getUniqueBooksData(userId)
//                .subscribeOn(viewModel.getSchedulerProvider().io())
//                .observeOn(viewModel.getSchedulerProvider().ui())
//                .subscribe(dataAlist -> {
//                    try {
//                        for (int i = 0; i < dataAlist.size(); i++) {
//                            JSONObject job = new JSONObject();
//                            job.put(AppConstants.BOOK_CLASS, dataAlist.get(i).getBookClass());
//                            job.put(AppConstants.BOOK_ID, dataAlist.get(i).getBookId());
//                            job.put(AppConstants.BOOK_IMAGE, dataAlist.get(i).getBookImage());
//                            job.put(AppConstants.BOOK_LANG, dataAlist.get(i).getBookLang());
//                            job.put(AppConstants.BOOK_NAME, dataAlist.get(i).getBookName());
//                            job.put(AppConstants.BOOK_SUBJECT, dataAlist.get(i).getBookSub());
//                            job.put(AppConstants.BOOK_CATEGORY, dataAlist.get(i).getBookCategory());
//                            job.put(AppConstants.BOOK_SUB_CATEGORY, dataAlist.get(i).getCategory());
//                            jarr.put(job);
//                        }
//                    } catch (Exception e) {
//                        Log.e(e.toString());
//                    }
//                    latch.countDown();
//                    Log.d(TAG, "getAllBooksData......................" + jarr.toString());
//                }, throwable -> {
//                    Log.e(throwable.getMessage(), throwable);
//                }));
//        latch.await();
        return jarr.toString();
    }

    @JavascriptInterface
    public String getChapterDataFromBookId(String bookId) throws InterruptedException {
        Log.d(TAG, "bookId........................" + bookId);

        JSONArray jarr = new JSONArray();
//        String userId = mAct.getViewModel().getDataManager().getStringPreference(AppPreferencesHelper.PREF_USER_ID, "");
//        DataManager dataManager = mAct.getViewModel().getDataManager();
//        CompositeDisposable compositeDisposable = mAct.getViewModel().getCompositeDisposable();
//        CommonWebViewModel viewModel = mAct.getViewModel();
//        final CountDownLatch latch = new CountDownLatch(1);
//        compositeDisposable.add(dataManager
//                .getChaptersDataFromBookId(userId, bookId)
//                .subscribeOn(viewModel.getSchedulerProvider().io())
//                .observeOn(viewModel.getSchedulerProvider().ui())
//                .subscribe(dataAlist -> {
//                    try {
//                        for (int i = 0; i < dataAlist.size(); i++) {
//                            JSONObject job = new JSONObject();
//                            job.put(AppConstants.CH_ID, dataAlist.get(i).getcId());
//                            job.put(AppConstants.CH_CLASS_BOOK, dataAlist.get(i).getcClassBook());
//                            job.put(AppConstants.CH_EPUB_LINK, dataAlist.get(i).getcEpubLink());
//                            job.put(AppConstants.CH_TITLE, dataAlist.get(i).getcTitle());
//                            job.put(AppConstants.CH_NO, dataAlist.get(i).getcNo());
//                            job.put(AppConstants.CH_ENM_LAYOUT, dataAlist.get(i).getcEnmLay());
//                            job.put(AppConstants.CH_ALL_DATA, dataAlist.get(i).getcAllData());
//                            job.put(AppConstants.CH_ENM_TYPE, dataAlist.get(i).getcEnmType());
//                            job.put(AppConstants.CH_HASH_KEY, dataAlist.get(i).getcHashKey());
//                            job.put(AppConstants.BOOK_NAME, dataAlist.get(i).getBookName());
//                            job.put(AppConstants.BOOK_IMAGE, dataAlist.get(i).getBookImage());
//                            job.put(AppConstants.BOOK_SUBJECT, dataAlist.get(i).getBookSub());
//                            job.put(AppConstants.CH_PATH, dataAlist.get(i).getcPath());
//                            job.put(AppConstants.CH_IS_DOWNLOADED, "" + dataAlist.get(i).isDownloaded());
//                            jarr.put(job);
//                        }
//                    } catch (Exception e) {
//                        Log.e(e.toString());
//                    }
//                    latch.countDown();
//                }, throwable -> {
//                    Log.e(throwable.getMessage(), throwable);
//                }));
//        latch.await();
        return jarr.toString();
    }

    public static String deleteChapterId;
    public static String chapterDeleteCallback;

    @JavascriptInterface
    public void deleteChapter(String chapterId, String responseCallback) {
        Log.d(TAG, "chapterId............................" + chapterId);

//        deleteChapterId = chapterId;
//        chapterDeleteCallback = responseCallback;
//
//        if (ContextCompat.checkSelfPermission(mAct,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(mAct,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                ((CommonWebViewActivity) mAct).sendChapterDeleteCallback("FAIL", chapterDeleteCallback);
//                CommonUtils.openPermissionSettingsDialog(mAct, mAct.getResources().getString(R.string.allow_write_storage_permission_for_delete_help_text));
//
//            } else {
//                ActivityCompat.requestPermissions(mAct,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        AppConstants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_DELETE);
//            }
//        } else {
//
//            deleteFile();
//
//        }

    }



    @JavascriptInterface
    public void openPDF(String pdfPath) {
        //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
        File file = new File(pdfPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        Uri uri = FileProvider.getUriForFile(mAct,
                mAct.getPackageName() + ".fileprovider",
                file);

        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            mAct.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mAct, mAct.getResources().getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public String getUserProfileEmail() {
        //String emailId = mAct.getViewModel().getDataManager().getStringPreference(AppPreferencesHelper.PREF_USER_EMAIL,"");
        return "emailId";
    }

    private String panJson;
    public static String panCallbackMethod;
    private File panFolder;

    @JavascriptInterface
    public void downloadFilePAN(String json, String callbackMethod) {

        Log.d(TAG, "downloadFilePAN json.........." + json);
        Log.d(TAG, "downloadFilePAN callbackMethod.........." + callbackMethod);

        panJson = json;
        this.json=json;
        panCallbackMethod = callbackMethod;

        if (ContextCompat.checkSelfPermission(mAct,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mAct,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ((UmangWebActivity) mAct).sendPANFileDownloadCallback(callbackMethod, "F");
                Utils.openPermissionSettingsDialog(mAct, mAct.getResources().getString(R.string.allow_write_storage_permission_help_text));

            } else {
                ActivityCompat.requestPermissions(mAct,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN);
            }
        } else {


            panFolder = new File(FileUtils.getBaseStorage2(mAct), "UMANG/PAN");
            if (!panFolder.exists()) {
                panFolder.mkdirs();
            }

            JSONObject job = null;
            try {
                job = new JSONObject(panJson);
                if(!job.optString("url").toLowerCase().contains("http") ){
                    Toast.makeText(mAct, mAct.getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                    return;
                }

                File file = new File(panFolder, job.optString("formname"));
                if (file.exists()) {
                    handlePANFile(mAct, panJson);
                } else {
                    startDownloadFilePAN();
                }
            } catch (Exception e1) {
                startDownloadFilePAN();
            }
        }
    }

    @JavascriptInterface
    public void downloadPDF(String json, String foldername, String callbackMethod) {

        Log.d(TAG, "downloadFilePAN json.........." + json);
        Log.d(TAG, "downloadFilePAN callbackMethod.........." + callbackMethod);

        panJson = json;
        panCallbackMethod = callbackMethod;
        mFoldername = foldername;

        if (ContextCompat.checkSelfPermission(mAct,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mAct,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                mAct.sendPANFileDownloadCallback(callbackMethod, "F");
                Utils.openPermissionSettingsDialog(mAct, mAct.getResources().getString(R.string.allow_write_storage_permission_help_text));

            } else {
                ActivityCompat.requestPermissions(mAct,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_DOWNLOAD_PDF);
            }
        } else {

            panFolder = new File(FileUtils.getBaseStorage2(mAct), "UMANG/" + foldername);
            if (!panFolder.exists()) {
                panFolder.mkdirs();
            }

            JSONObject job = null;
            try {
                job = new JSONObject(panJson);

                if(!job.optString("url").toLowerCase().contains("http")){
                    Toast.makeText(mAct, mAct.getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                    return;
                }

                File file = new File(panFolder, job.optString("formname"));
                if (file.exists()) {
                    handlePANFile(mAct, panJson);
                } else {
                    new DownloadPANFileTask(mAct, panJson, mFoldername).execute();
                }
            } catch (Exception e1) {
                new DownloadPANFileTask(mAct, panJson, mFoldername).execute();
            }


        }


    }

    public void startDownloadFile() {
        new DownloadPANFileTask(mAct, panJson, mFoldername).execute();
    }

    @JavascriptInterface
    public void shareEbookWithChapterId(String chapter_id) {

//        String userId = mAct.getViewModel().getDataManager().getStringPreference(AppPreferencesHelper.PREF_USER_ID, "");
//        DataManager dataManager = mAct.getViewModel().getDataManager();
//        CompositeDisposable compositeDisposable = mAct.getViewModel().getCompositeDisposable();
//        CommonWebViewModel viewModel = mAct.getViewModel();
//
//        compositeDisposable.add(dataManager
//                .getChapterDetailFromChapterId(userId, chapter_id)
//                .subscribeOn(viewModel.getSchedulerProvider().io())
//                .observeOn(viewModel.getSchedulerProvider().ui())
//                .subscribe(bookBean -> {
//                    if (bookBean != null) {
//
//                        if (checkIfBookExistInStorage(mAct, chapter_id)) {
//                            Log.d(TAG, "book exist in storage.............................");
//                            try {
//
//                                File f = new File(bookBean.getcPath());
//
//                                Uri capturedURI = FileProvider.getUriForFile(mAct,
//                                        mAct.getPackageName() + ".fileprovider",
//                                        f);
//
//
//                                Intent shareIntent = new Intent();
//                                shareIntent.setAction(Intent.ACTION_SEND);
//                                shareIntent.putExtra(Intent.EXTRA_TEXT, bookBean.getcTitle() + ", " + bookBean.getBookName());
//
//
//                                shareIntent.putExtra(Intent.EXTRA_STREAM, capturedURI);
//                                shareIntent.setType("*/*");
//                                mAct.startActivity(Intent.createChooser(shareIntent, mAct.getResources().getText(R.string.share_txt)));
//                            } catch (Exception e) {
//                                Log.e(e.toString());
//                            }
//                        } else {
//                            Log.d(TAG, "book is not in storage.............................");
//                            deleteChapterFromDB(userId, chapter_id);
//                        }
//
//
//                    } else {
//                        Toast.makeText(mAct, mAct.getResources().getString(R.string.book_not_found), Toast.LENGTH_SHORT).show();
//                    }
//                }, throwable -> {
//                    Log.e(throwable.getMessage(), throwable);
//                }));



    }

    public static String mFileURL;
    public static String mFileName;

    @JavascriptInterface
    public void downloadPDF(String name, String url) {
        mFileName = name;
        mFileURL = url;


        if (ContextCompat.checkSelfPermission(mAct,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mAct,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Utils.openPermissionSettingsDialog(mAct, mAct.getResources().getString(R.string.allow_write_storage_permission_help_text));

            } else {
                ActivityCompat.requestPermissions(mAct,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_FOR_DOWNLOAD_PDF_IN_DOWNLOAD);
            }
        } else {

            handleDownload(mAct, url, name);

        }

    }



    public void startDownloadFilePAN() {

        try {
            JSONObject job = new JSONObject(json);
            if(job.optString("url").toLowerCase().contains("http")){
                new DownloadPANFileTask(mAct, panJson).execute();
            }else{
                Toast.makeText(mAct, mAct.getString(R.string.please_try_again), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mAct, mAct.getString(R.string.please_try_again), Toast.LENGTH_LONG).show();
        }


    }

    private class DownloadPANFileTask extends AsyncTask<Void, Integer, String> {

        private Context context;
        private String json;
        private ProgressDialog mProgressDialog;

        public DownloadPANFileTask(Context context, String json) {
            this.context = context;
            this.json = json;
            mProgressDialog = new ProgressDialog(context);

            panFolder = new File(FileUtils.getBaseStorage2(context), "UMANG/PAN");
            if (!panFolder.exists()) {
                panFolder.mkdirs();
            }

        }


        public DownloadPANFileTask(Context context, String json, String foldername) {
            this.context = context;
            this.json = json;
            mProgressDialog = new ProgressDialog(context);

            panFolder = new File(FileUtils.getBaseStorage2(context), "UMANG/" + foldername);
            if (!panFolder.exists()) {
                panFolder.mkdirs();
            }

        }

        public DownloadPANFileTask(String json, Context context) {
            this.context = context;
            this.json = json;
            mProgressDialog = new ProgressDialog(context);

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.setTitle(context.getResources().getString(R.string.downloading_file));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                JSONObject job = new JSONObject(json);



                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();


                Request request = new Request.Builder()
                        .url(job.optString("url").replace("http:","https:"))
                        .get()
                        .build();


                Response response = client.newCall(request).execute();

                if (response.code() == 200) {

                    ResponseBody rbody = response.body();


                    long contentLength = rbody.contentLength();

                    Log.d(TAG, "contentLength: " + contentLength);

                    BufferedSource source = rbody.source();

                    File file = new File(panFolder, job.optString("formname"));
                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                    Buffer sinkBuffer = sink.buffer();

                    long totalBytesRead = 0;
                    int bufferSize = 8 * 1024;
                    // int progress = 0;
                    long bytesRead = 0;

                    while ((bytesRead = source.read(sinkBuffer, bufferSize)) != -1) {
                        totalBytesRead += bytesRead;
                        publishProgress((int) (totalBytesRead * 100 / contentLength));

                    }

                    sink.writeAll(source);
                    sink.flush();
                    sink.close();

                }


            } catch (Exception e) {

                mAct.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mAct, mAct.getString(R.string.please_try_again), Toast.LENGTH_LONG).show();
                    }
                });

                return null;

            }

            return null;
        }


        @Override
        protected void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(values[0].intValue());
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            try {

                JSONObject job = new JSONObject(json);

                File f = new File(panFolder, job.optString("formname"));
                if (f.exists()) {
                    f.delete();
                }
            } catch (Exception e1) {
            }

            mAct.sendPANFileDownloadCallback(panCallbackMethod, "F");

            try {
                mProgressDialog.dismiss();
                Toast.makeText(context, context.getResources().getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            mAct.sendPANFileDownloadCallback(panCallbackMethod, "S");

            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
            }

            handlePANFile(mAct, json);


        }
    }

    private void handlePANFile(Context context, String json) {
        try {
            JSONObject job = new JSONObject(json);
            if (job.optString("type").equalsIgnoreCase("email")) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{job.optString("email")});
                intent.putExtra(Intent.EXTRA_SUBJECT, job.optString("subject"));
                intent.putExtra(Intent.EXTRA_TEXT, job.optString("mailbody"));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                File file = new File(panFolder, job.optString("formname"));

                Uri uri = FileProvider.getUriForFile(mAct,
                        mAct.getPackageName() + ".fileprovider",
                        file);
                ;
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(intent);


            } else if (job.optString("type").equalsIgnoreCase("download")) {

                File file = new File(panFolder, job.optString("formname"));
                Toast.makeText(mAct, mAct.getResources().getString(R.string.download_success) + " : " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

            } else {

                File file = new File(panFolder, job.optString("formname"));
                Intent target = new Intent(Intent.ACTION_VIEW);
                Uri uri = FileProvider.getUriForFile(mAct,
                        mAct.getPackageName() + ".fileprovider",
                        file);
                target.setDataAndType(uri, "application/pdf");
                target.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    context.startActivity(target);
                } catch (Exception e) {
                    Toast.makeText(context, context.getResources().getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
                }

            }
        } catch (Exception e1) {
        }
    }



    private static String fName;

    public boolean handleDownload(final Context context, final String fromUrl, final String toFilename) {
        fName=fromUrl;

        if (Build.VERSION.SDK_INT < 9) {
            throw new RuntimeException("Method requires API level 9 or above");
        }
        try {
            final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fromUrl));
            if (Build.VERSION.SDK_INT >= 11) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE ).setAllowedOverRoaming(true);
                request.setAllowedOverRoaming(false);
                request.setDescription("Downloading " + toFilename);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, toFilename + ".pdf");

            mAct.registerReceiver(attachmentDownloadCompleteReceive, new IntentFilter(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            try {
                dm.enqueue(request);
            } catch (SecurityException e) {
                if (Build.VERSION.SDK_INT >= 11) {
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                }

                dm.enqueue(request);
            }
            Toast.makeText(context, context.getResources().getString(R.string.downloading_file), Toast.LENGTH_SHORT).show();
            return true;
        } catch (IllegalArgumentException e) {
            // show the settings screen where the user can enable the download manager app again
//            openAppSettings(context, CustomAdvancedWebView.PACKAGE_NAME_DOWNLOAD_MANAGER);

            return false;
        }

    }

    /**
     * Attachment download complete receiver.
     * <p/>
     * 1. Receiver gets called once attachment download completed.
     * 2. Open the downloaded file.
     */
    BroadcastReceiver attachmentDownloadCompleteReceive = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, 0);


                openDownloadedAttachment(context, downloadId);
            }
        }
    };

    public static boolean isActivityForIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    /**
     * Used to open the downloaded attachment.
     *
     * @param context    Content.
     * @param downloadId Id of the downloaded file to open.
     */
    private  void openDownloadedAttachment(final Context context, final long downloadId) {
        try {

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            Cursor cursor = downloadManager.query(query);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                @SuppressLint("Range") String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                @SuppressLint("Range") String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {

                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setDataAndType(Uri.parse(downloadLocalUri), downloadMimeType);
                        //browserIntent.setPackage("com.adobe.reader");

                        Intent chooser = Intent.createChooser(browserIntent, mAct.getString(R.string.open_multi));
                        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional


                        if(isActivityForIntentAvailable(mAct,chooser)){
                            mAct.startActivity(chooser);
                        }else{
                            Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(fName));
                            mAct.startActivity(browserIntent2);
                        }




                        mAct.unregisterReceiver(attachmentDownloadCompleteReceive);
                    } catch (Exception ex) {

                        Intent browserIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(fName));
                        mAct.startActivity(browserIntent3);
                    }
                }
            }
            cursor.close();

        }catch (Exception ex){

            try{
                Intent browserIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(fName));
                mAct.startActivity(browserIntent3);
            }catch (Exception e){

            }

        }



    }
}
