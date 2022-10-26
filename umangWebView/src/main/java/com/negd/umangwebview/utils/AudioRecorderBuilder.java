package com.negd.umangwebview.utils;

import android.content.Context;

import androidx.annotation.NonNull;

public final class AudioRecorderBuilder {

   private Context mContext;
   private String mFileName;
   private AudioRecorder.MediaRecorderConfig mConfig;
   private boolean mIsLoggable;

   private AudioRecorderBuilder() {
   }

   public static AudioRecorderBuilder with(@NonNull Context context) {
      final AudioRecorderBuilder audioRecorderBuilder = new AudioRecorderBuilder();
      audioRecorderBuilder.mContext = context;
      return audioRecorderBuilder;
   }

   public AudioRecorderBuilder fileName(@NonNull String targetFileName) {
      mFileName = targetFileName;
      return this;
   }

   public AudioRecorderBuilder config(@NonNull AudioRecorder.MediaRecorderConfig config) {
      mConfig = config;
      return this;
   }

   public AudioRecorderBuilder loggable() {
      mIsLoggable = true;
      return this;
   }

   /**
    * Returns a ready-to-use AudioRecorder.<p>
    * Uses {@link AudioRecorder.MediaRecorderConfig#DEFAULT} as
    * {@link android.media.MediaRecorder} config by default.<p>
    * Logs are turned off by default.
    *
    * @return Created {@link AudioRecorder instance}.
    */
   public AudioRecorder build() {
      if (mFileName == null) {
         throw new RuntimeException("Target filename is not set: use `#fileName` method");
      }
      return new AudioRecorder(mContext, mFileName, mConfig, mIsLoggable);
   }

}
