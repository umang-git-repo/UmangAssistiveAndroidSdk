package com.negd.umangwebview.utils;

import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.negd.umangwebview.R;
import com.negd.umangwebview.ui.UmangWebActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AudioRecord {
   private String TAG = "AudioRecord";
   private boolean isRecording;
   private static String mFileName = null;
   private MediaRecorder mRecorder = null;
   private MediaPlayer mPlayer = null;
   private Activity act;
   private Dialog promptView;
   private boolean isPlaying;
   private CountDownTimer mTimer;
   private SeekBar otp_seekbar;
   private final boolean isBotActivity;

   public AudioRecord(Activity act, boolean isBotActivity) {
      this.act = act;
      this.isBotActivity = isBotActivity;
      mFileName = act.getExternalCacheDir().getAbsolutePath();
      mFileName += "/mp4agriaudio.mp3";
      File audioFile = new File(mFileName);

      audioFile.delete();


   }
   private boolean progressOnPause = false;
   private int progressValue = 0;
   private ImageView btnPlay, btnStop,btnStart;
   public void onAudioClick() {


      promptView = new Dialog(act);
      promptView.requestWindowFeature(promptView.getWindow().FEATURE_NO_TITLE);
      promptView.setContentView(R.layout.record_audio_dialog);
      promptView.setCancelable(false);
      btnPlay = (ImageView) promptView.findViewById(R.id.btn_play);
      btnStop = (ImageView) promptView.findViewById(R.id.btn_stop);
      btnStart = (ImageView) promptView.findViewById(R.id.btn_recording);


      btnPlay.setVisibility(View.GONE);
      btnStop.setVisibility(View.VISIBLE);
      TextView yes_txt = (TextView)promptView.findViewById(R.id.yes_txt);
      TextView no_txt = (TextView)promptView.findViewById(R.id.no_txt);
      final TextView otp_timer_txt = (TextView)promptView.findViewById(R.id.otp_timer_txt);
      otp_seekbar = (SeekBar)promptView.findViewById(R.id.otp_seekbar);

      otp_seekbar.setClickable(false);
      otp_seekbar.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
         }
      });
      final int max = 120;
      otp_seekbar.setMax(max);


      mTimer = new CountDownTimer(max * 1000, 1000) {


         public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 1000);
            int tempProgress, tempMax;

            if(progressValue == 0){
               progressValue = progress;
            }
            else{
               progressValue = progressValue - 1;
            }


            int progressIncrease = max - progressValue;
            otp_seekbar.setProgress(progressIncrease);

            otp_timer_txt.setText(String.format("%02d:%02d", progressIncrease / 60, progressIncrease % 60));
         }

         public void onFinish() {
            isRecording = false;
            otp_timer_txt.setText("");
            otp_seekbar.setProgress(120);
            stopRecording();
            btnStart.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.GONE);
            btnStop.setOnClickListener(playBtnClickListner);


         }
      };

      setAudioRecorder();
      btnPlay.setOnClickListener(playBtnClickListner);
      //btnStop.setOnClickListener(playBtnClickListner);
      btnStop.setOnClickListener(stopRecordClickListener);
      btnStart.setOnClickListener(recordClickListner);
      //btnAttach.setOnClickListener(attachBtnClickListner);
      yes_txt.setOnClickListener(attachBtnClickListner);
      no_txt.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Log.d(TAG,"ISRECORDONG === "+isRecording);
            if (isRecording)
               stopRecording();

            if (isPlaying)
               stopPlaying();


            promptView.dismiss();
         }
      });

      promptView.show();
   }

   private void onRecord(boolean start) {
      if (start) {
         startRecording();
      } else {
         stopRecording();
      }
   }

   private void onPlay(boolean start) {
      if (start) {
         startPlaying();
      } else {
         stopPlaying();
      }
   }

   private void startPlaying() {

      File audioFile = new File(mFileName);

      if(!audioFile.exists()){
         Toast.makeText(act,"No Recording Found",Toast.LENGTH_LONG).show();
      }
      else{

         mPlayer = new MediaPlayer();
         try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
               @Override
               public void onCompletion(MediaPlayer mediaPlayer) {
                  if (isPlaying) {
                     isPlaying = false;
                     stopPlaying();
                     // ((Button) promptView.findViewById(R.id.btn_play)).setText("Play");

                  }
               }
            });

            btnPlay.setImageResource(R.drawable.play_pause_audio);

         } catch (IOException e) {
         }
      }

   }

   private void stopPlaying() {
      if(mPlayer != null){
         mPlayer.release();
         mPlayer = null;
         btnPlay.setImageResource(R.drawable.icon_play_grey);
      }
      //((Button) promptView.findViewById(R.id.btn_play)).setText("Start");
   }


   private void pausePlaying(){
      if(mPlayer != null) {
         mPlayer.pause();
         btnPlay.setImageResource(R.drawable.icon_play_grey);
      }
   }

   private AudioRecorder recorder;
   private void setAudioRecorder(){
      AudioRecorder.MediaRecorderConfig mConfig = new AudioRecorder.MediaRecorderConfig( 64 * 1024,2,MediaRecorder.AudioSource.MIC,MediaRecorder.AudioEncoder.AAC,120000);
      recorder = AudioRecorderBuilder.with(act)
              .fileName(mFileName)
              .config(mConfig)
              .loggable()
              .build();
   }

   private void startRecording() {


      mTimer.start();
      ((ImageView) promptView.findViewById(R.id.btn_recording)).setImageResource(R.drawable.recording_pause);
      btnPlay.setVisibility(View.GONE);
      recorder.start(new AudioRecorder.OnStartListener() {
         @Override
         public void onStarted() {
            // started
            Log.d(TAG,"Recording Started");


            //btnStop.setVisibility(View.GONE);
         }

         @Override
         public void onException(Exception e) {
            // error
         }
      });

      recorder.onComplete(new AudioRecorder.OnCompleteListener(){
         @Override
         public void onComplete(String activeRecordFileName) {
            Log.d(TAG,"Recording Complete");
            onCompleteRecord();

         }

         @Override
         public void onException(Exception e) {

         }
      });
   }

   public void stopRecordingInBackground(){
      if(recorder.isRecording())
      {
         stopRecording();
      }
   }


   private void stopRecording() {
      isPlaying = false;

      btnStart.setImageResource(R.drawable.icon_recoding);
      btnPlay.setVisibility(View.GONE);
      btnStop.setVisibility(View.VISIBLE);
      recorder.pause(new AudioRecorder.OnPauseListener(){
         @Override
         public void onException(Exception e) {

         }

         @Override
         public void onPaused(String activeRecordFileName) {

            progressOnPause = true;


         }
      });

      mTimer.cancel();
   }

   private void onCompleteRecord(){

   }

   private View.OnClickListener recordClickListner = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         if (!isPlaying) {
            if (!isRecording) {
               isRecording = true;
               startRecording();


            } else {
               isRecording = false;
               stopRecording();


            }
         }
      }
   };


   private View.OnClickListener playBtnClickListner = new View.OnClickListener() {
      @Override
      public void onClick(View v) {


         if (isPlaying) {
            isPlaying = false;
            //stopPlaying();
            pausePlaying();
            // ((Button) promptView.findViewById(R.id.btn_play)).setText("Play");

         } else {
            isPlaying = true;
            startPlaying();

         }
      }
   };

   private View.OnClickListener stopRecordClickListener = new View.OnClickListener(){

      @Override
      public void onClick(View view) {
         if(isRecording){
            stopRecording();
            isRecording = false;

         }
         btnStart.setVisibility(View.GONE);
         btnPlay.setVisibility(View.VISIBLE);
         btnStop.setVisibility(View.GONE);
         btnStop.setOnClickListener(playBtnClickListner);

      }
   };

   private View.OnClickListener attachBtnClickListner = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         if ((!isRecording) && (!isPlaying)) {
            File audioFile = new File(mFileName);
            if (!audioFile.exists()) {
               Toast.makeText(act, R.string.please_try_again, Toast.LENGTH_LONG).show();
            } else {
               fileToBase64(audioFile);
            }
         }
      }
   };

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

            } else {
               ((UmangWebActivity)act).sendAudioSuccessToWeb(encodedString+"#"+size);
            }

            promptView.dismiss();
         }
      });
   }
   private class UICallback implements Handler.Callback{
      public static final int DISPLAY_UI_TOAST = 0;
      public static final int ON_START = 1;
      public static final int ON_PAUSE = 2;
      public static final int ON_COMPLETE = 3;
      public static final int ON_STOP = 4;



      @Override
      public boolean handleMessage(Message msg) {
         switch (msg.what) {
            case UICallback.DISPLAY_UI_TOAST: {

               return true;
            }
            case UICallback.ON_START:{





               return true;
            }
            case UICallback.ON_PAUSE:{

               return true;
            }
            case UICallback.ON_COMPLETE:{

               return true;
            }
            case UICallback.ON_STOP:{

               return true;
            }
            default:
               return false;
         }
      }
   }
}

