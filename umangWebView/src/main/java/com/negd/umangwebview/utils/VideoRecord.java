package com.negd.umangwebview.utils;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.FileProvider;

import com.negd.umangwebview.R;
import com.negd.umangwebview.UmangAssistiveAndroidSdk;
import com.negd.umangwebview.ui.UmangWebActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoRecord {

    private String TAG = "VideoRecord";
    private Uri fileUri;
    private Activity act;
    public static final int REQUEST_VIDEO_CAPTURE = 98;
    private boolean isFromCamera = false;
    private final boolean isBotActivity;

    public VideoRecord(Activity act, boolean isBotActivity) {
        this.act = act;
        this.isBotActivity = isBotActivity;
    }

    public void startVideoRecording() {
        createChooserDialog();

    }

    private void createChooserDialog() {
        final Dialog dialog = new Dialog(this.act);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_file_dialog_option);
        dialog.setCancelable(true);
        dialog.show();

        LinearLayoutCompat camera = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_camera_txt);
        LinearLayoutCompat gallery = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_gallery_txt);
        LinearLayoutCompat facebook = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_fb_txt);
        LinearLayoutCompat google = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_google_txt);
        LinearLayoutCompat twitter = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_twitter_txt);
        LinearLayoutCompat removePic = (LinearLayoutCompat) dialog.findViewById(R.id.remove_pic_txt);

        removePic.setVisibility(View.GONE);
        facebook.setVisibility(View.GONE);
        google.setVisibility(View.GONE);
        twitter.setVisibility(View.GONE);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                isFromCamera = true;
                getFromCamera();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                isFromCamera = false;
                getFromGallery();
            }
        });


    }

    private void getFromGallery() {
        Intent intent = new Intent();
        fileUri = getOutputMediaFile(MEDIA_TYPE_VIDEO);
        intent.setType("video/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.act.startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        UmangAssistiveAndroidSdk.openingIntent = true;
    }

    private void getFromCamera() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFile(MEDIA_TYPE_VIDEO);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
        takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        takeVideoIntent.putExtra(MediaStore.Video.Media.MIME_TYPE, "video/mp4");


        if (takeVideoIntent.resolveActivity(this.act.getPackageManager()) != null) {
            this.act.startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            UmangAssistiveAndroidSdk.openingIntent = true;
        }
    }

    public Uri getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted

        if (Environment.getExternalStorageState() != null) {
            // this works for Android 2.2 and above
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "SMW_VIDEO");

            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {

                    return null;
                }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
            File mediaFile;
            if (type == MEDIA_TYPE_VIDEO) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "VID_" + timeStamp + ".mp4");
            } else {
                return null;
            }

            Uri capturedURI = FileProvider.getUriForFile(act,
                    act.getPackageName() + ".fileprovider",
                    mediaFile);

            return capturedURI;
        }

        return null;
    }

    public void onVideoRecordComplete(Uri filePath) {
        Log.d(TAG, "onVideoRecordComplete called " + filePath + " // ");
        if(filePath == null){
            filePath = fileUri;
        }
        try {
            Uri tempPath;
            File videoFile;
            if (isFromCamera) {
                tempPath = filePath;
                videoFile = FileUtils.from(act,filePath);
            } else {
                tempPath = filePath;
                videoFile = new File(PathUtils.getRealPath(act,tempPath));
            }

            if (!videoFile.exists()) {
                Toast.makeText(act, R.string.please_try_again, Toast.LENGTH_LONG).show();
            } else {
                if (videoFile.length() <= 10000000) {
                    //fileToBase64(videoFile);
                    videoConvertTest(videoFile);
                } else {


                    if (isBotActivity) {
                        //((BotViewActivity)act).sendVideoSuccess("fail"+"#"+videoFile.length());
                    } else {
                        ((UmangWebActivity)act).senVideoSuccessToWeb("fail"+"#"+videoFile.length());
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private void fileToBase64(final File file) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String encodedString = null;

                int size = (int) file.length();
                byte[] bytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();
                } catch (FileNotFoundException e) {

                } catch (IOException e) {
                }

                encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                if (isBotActivity) {
                    //((BotViewActivity)act).sendVideoSuccess(encodedString+"#"+size);
                } else {
                    ((UmangWebActivity)act).senVideoSuccessToWeb(encodedString+"#"+size);
                }


            }
        });
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = this.act.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private String getRealPathFromForVidURI(Uri contentURI) {
        String result;
        Cursor cursor = this.act.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {

            cursor.moveToFirst();
            int idxV = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME);

            result = cursor.getString(idxV);
            cursor.close();
        }
        return result;
    }

    private void videoConvertTest(final File file) {
        new FileConvertAsync().execute(file);
    }

    class FileConvertAsync extends AsyncTask<File, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isBotActivity) {
               // ((BotViewActivity) act).showLoading();
            } else {
                ((UmangWebActivity) act).showLoading();
            }
        }

        @Override
        protected String doInBackground(File... files) {
            try {
                StringBuilder sb = new StringBuilder();
                int fSize =(int) files[0].length();
                byte fileContent[] = new byte[fSize];
                FileInputStream fin = new FileInputStream(files[0]);

                while (fin.read(fileContent) >= 0) {
                    String s = Base64.encodeToString(fileContent, Base64.DEFAULT);
                    sb = sb.append(s);
                }

                return sb.toString()+"#"+fSize;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (false) {

            } else {
                ((UmangWebActivity) act).hideLoading();

                if (s != null) {
                    ((UmangWebActivity) act).senVideoSuccessToWeb(s);
                } else {
                    ((UmangWebActivity) act).sendVideoFailToWeb("");
                }
            }
        }
    }
}
