package com.negd.umangwebview.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.FileProvider;

import com.negd.umangwebview.R;
import com.negd.umangwebview.UmangAssistiveAndroidSdk;
import com.negd.umangwebview.ui.UmangWebActivity;

import java.io.File;

public class ImageSelect {
   private Activity act;
   public static Uri mCapturedImageURI;
   public static int REQUEST_IMAGE_CAPTURE = 47;
   public static int REQUEST_IMAGE_GALLERY = 52;
   private final boolean isBotActivity;

   public ImageSelect(Activity act, boolean isBotActivity) {
      this.act = act;
      this.isBotActivity = isBotActivity;
   }

   public void getImages() {
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

            getFromCamera();
         }
      });

      gallery.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            dialog.dismiss();

            getFromGallery();
         }
      });


   }

   private void getFromCamera() {
      try {
         File imageStorageDir = new File(
                 Environment.getExternalStoragePublicDirectory(
                         Environment.DIRECTORY_PICTURES)
                 , act.getPackageName());

         if (!imageStorageDir.exists()) {
            // Create AndroidExampleFolder at sdcard
            imageStorageDir.mkdirs();
         }

         // Create camera captured image file path and name
         File file = new File(
                 imageStorageDir + File.separator + "IMG_"
                         + String.valueOf(System.currentTimeMillis())
                         + ".jpg");

         mCapturedImageURI = FileProvider.getUriForFile(act,
                 act.getPackageName()+".fileprovider",
                 file);

         final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

         captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

         act.startActivityForResult(captureIntent, REQUEST_IMAGE_CAPTURE);
         UmangAssistiveAndroidSdk.openingIntent = true;

      } catch (Exception e) {

         if (isBotActivity) {
            //((BotViewActivity) this.act).sendImagesFail();
         } else {
            ((UmangWebActivity) this.act).sendImagesFailToWeb();
         }
      }
   }

   private void getFromGallery() {
      try {

         File imageStorageDir = new File(
                 Environment.getExternalStoragePublicDirectory(
                         Environment.DIRECTORY_PICTURES)
                 , act.getPackageName());

         if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
         }

         File file = new File(
                 imageStorageDir + File.separator + "IMG_"
                         + String.valueOf(System.currentTimeMillis())
                         + ".jpg");

//            mCapturedImageURI = Uri.fromFile(file);
         mCapturedImageURI = FileProvider.getUriForFile(act,
                 act.getPackageName()+".fileprovider",
                 file);

         Intent galleyintent = new Intent(Intent.ACTION_GET_CONTENT);
         galleyintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
         galleyintent.addCategory(Intent.CATEGORY_OPENABLE);
         galleyintent.setType("image/*");

         act.startActivityForResult(galleyintent, REQUEST_IMAGE_GALLERY);
         UmangAssistiveAndroidSdk.openingIntent = true;

      } catch (Exception e) {

      }

   }
}
