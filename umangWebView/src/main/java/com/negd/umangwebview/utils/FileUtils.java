package com.negd.umangwebview.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public final class FileUtils {

   private static final String TAG=FileUtils.class.getSimpleName();

   private static final int EOF = -1;
   private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

   private FileUtils() {
   }


   //open file from local
   public static void openFile(Context context, File url) throws IOException {
      try {
         // Create URI
         Uri uri = FileProvider.getUriForFile(
                 context,
                 context.getApplicationContext()
                         .getPackageName() + ".fileprovider", url);


         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
         // Check what kind of file you are trying to open, by comparing the url with extensions.
         // When the if condition is matched, plugin sets the correct intent (mime) type,
         // so Android knew what application to use to open the file
         if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
         } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
         } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
         } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
         } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
         } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
         } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
         } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
         } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
         } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
         } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
         } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
         }

         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(intent);
      }catch (Exception ex){

      }
   }


   //method to get bytes into units
   public static String convertBytesIntoUnits(double bytes) {
      double convBytes = bytes + 'd';
      if (bytes < 1024) {

         String size = String.valueOf(bytes);
         return size + " bytes";
      } else if (bytes < 1048576) {
         convBytes = bytes / 1024;//kb
         String size = String.valueOf(roundTwoDecimals(convBytes));
         return size + " KB";
      } else if (bytes < 1073741824) {
         convBytes = bytes / 1024 / 1024;//mb

         String size = String.valueOf(roundTwoDecimals(convBytes));

         return size + " MB";
      } else {
         convBytes = bytes / 1024 / 1024 / 1024;//gb

         String size = String.valueOf(roundTwoDecimals(convBytes));
         return size + " GB";
      }
   }


   public static double roundTwoDecimals(double d) {

      try{
         DecimalFormat twoDForm = new DecimalFormat("#.#");
         return Double.valueOf(twoDForm.format(d));
      }catch (Exception ex){
         return d;
      }

   }

   public static File from(Context context, Uri uri) throws IOException {
      InputStream inputStream = context.getContentResolver().openInputStream(uri);
      String fileName = getFileName(context, uri);
      String[] splitName = splitFileName(fileName);
      File tempFile = File.createTempFile(splitName[0], splitName[1]);
      tempFile = rename(tempFile, fileName);
      tempFile.deleteOnExit();
      FileOutputStream out = null;
      try {
         out = new FileOutputStream(tempFile);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      if (inputStream != null) {
         copy(inputStream, out);
         inputStream.close();
      }

      if (out != null) {
         out.close();
      }
      return tempFile;
   }

   private static String[] splitFileName(String fileName) {
      String name = fileName;
      String extension = "";
      int i = fileName.lastIndexOf(".");
      if (i != -1) {
         name = fileName.substring(0, i);
         extension = fileName.substring(i);
      }

      return new String[]{name, extension};
   }

   private static String getFileName(Context context, Uri uri) {
      String result = null;
      if (uri.getScheme().equals("content")) {
         Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
         try {
            if (cursor != null && cursor.moveToFirst()) {
               result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            if (cursor != null) {
               cursor.close();
            }
         }
      }
      if (result == null) {
         result = uri.getPath();
         int cut = result.lastIndexOf(File.separator);
         if (cut != -1) {
            result = result.substring(cut + 1);
         }
      }
      return result;
   }

   private static File rename(File file, String newName) {
      File newFile = new File(file.getParent(), newName);
      if (!newFile.equals(file)) {
         if (newFile.exists() && newFile.delete()) {
         }
         if (file.renameTo(newFile)) {
         }
      }
      return newFile;
   }

   private static long copy(InputStream input, OutputStream output) throws IOException {
      long count = 0;
      int n;
      byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
      while (EOF != (n = input.read(buffer))) {
         output.write(buffer, 0, n);
         count += n;
      }
      return count;
   }

   public static ArrayList<String> getDownloadedFileList(Context context, String userid) {
      ArrayList<String> fileList = new ArrayList<>();
      if (ContextCompat.checkSelfPermission(context,
              Manifest.permission.READ_EXTERNAL_STORAGE)
              == PackageManager.PERMISSION_GRANTED) {
         try {

            String userId = userid;
            String path = FileUtils.getBaseStorage2(context).toString() + "/UMANG/Digilocker/" ;
            File f = new File(path);
            File file[] = f.listFiles();

            for (int i = 0; i < file.length; i++) {

               if(file[i].exists())
                  fileList.add(file[i].getName());
            }

         } catch (Exception e) {
         }


      }

      return fileList;

   }



   public static File reduceFileSize(File file){
      try {

         // BitmapFactory options to downsize the image
         BitmapFactory.Options o = new BitmapFactory.Options();
         o.inJustDecodeBounds = true;
         o.inSampleSize = 6;
         // factor of downsizing the image

         FileInputStream inputStream = new FileInputStream(file);
         //Bitmap selectedBitmap = null;
         BitmapFactory.decodeStream(inputStream, null, o);
         inputStream.close();

         // The new size we want to scale to
         final int REQUIRED_SIZE=75;

         // Find the correct scale value. It should be the power of 2.
         int scale = 1;
         while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                 o.outHeight / scale / 2 >= REQUIRED_SIZE) {
            scale *= 2;
         }

         BitmapFactory.Options o2 = new BitmapFactory.Options();
         o2.inSampleSize = scale;
         inputStream = new FileInputStream(file);

         Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
         inputStream.close();

         // here i override the original image file
         file.createNewFile();
         FileOutputStream outputStream = new FileOutputStream(file);

         selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

         return file;
      } catch (Exception e) {
         return null;
      }
   }

   public static File getBaseStorage(){

      File storageDir=null;

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
         storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
      }else{
         storageDir= Environment.getExternalStorageDirectory();
      }

      return storageDir;
   }

   public static File getBaseStorage2(Context context){

      File storageDir=null;

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
         storageDir=new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "");

      }else{
         storageDir= Environment.getExternalStorageDirectory();
      }

      return storageDir;
   }


   public static String copyFileToInternalStorage(Uri uri, String newDirName,Context mContext) {
      Uri returnUri = uri;

      Cursor returnCursor = mContext.getContentResolver().query(returnUri, new String[]{
              OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
      }, null, null, null);


      /*
       * Get the column indexes of the data in the Cursor,
       *     * move to the first row in the Cursor, get the data,
       *     * and display it.
       * */
      int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
      int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
      returnCursor.moveToFirst();
      String name = (returnCursor.getString(nameIndex));
      String size = (Long.toString(returnCursor.getLong(sizeIndex)));

      File output;
      if (!newDirName.equals("")) {
         File dir = new File(mContext.getFilesDir() + "/" + newDirName);
         if (!dir.exists()) {
            dir.mkdir();
         }
         output = new File(mContext.getFilesDir() + "/" + newDirName + "/" + name);
      } else {
         output = new File(mContext.getFilesDir() + "/" + name);
      }
      try {
         InputStream inputStream = mContext.getContentResolver().openInputStream(uri);
         FileOutputStream outputStream = new FileOutputStream(output);
         int read = 0;
         int bufferSize = 1024;
         final byte[] buffers = new byte[bufferSize];
         while ((read = inputStream.read(buffers)) != -1) {
            outputStream.write(buffers, 0, read);
         }

         inputStream.close();
         outputStream.close();

      } catch (Exception e) {

         Log.e("Exception", e.getMessage());
      }

      return output.getPath();
   }

}
