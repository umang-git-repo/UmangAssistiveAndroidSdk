package com.negd.umangwebview.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DatePickerFragmentDepartment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


   private final String TAG = "DatePickerNotifFilter";
   private String dateStr;
   private String minDateStr;
   private String setCurrentMax;
   private String maxDateStr;
   private Context context;
   private OnDatePicker mListner;

   public DatePickerFragmentDepartment() {
   }

   @Override
   public void onAttach(Context context) {
      super.onAttach(context);
      this.context = context;
   }

   public static DatePickerFragmentDepartment newInstance(String dateStr, String minDate, String setCurrentMax, String maxDate, OnDatePicker callback) {

      DatePickerFragmentDepartment fragment = new DatePickerFragmentDepartment();

      fragment.dateStr = dateStr;
      fragment.minDateStr = minDate;
      fragment.setCurrentMax = setCurrentMax;
      fragment.maxDateStr = maxDate;
      fragment.mListner = callback;
      return fragment;
   }


   String getStrDate = "";


//        @Override
//        public void onCreate(@Nullable Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setRetainInstance(true);
//        }

   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
      // Use the current date as the default date in the picker
      final Calendar c = Calendar.getInstance();
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH);
      int day = c.get(Calendar.DAY_OF_MONTH);

      try {
         String temp = dateStr;
         if (temp.length() != 0) {
            day = Integer.parseInt(temp.substring(0, temp.indexOf("-")));
            month = Integer.parseInt(temp.substring(temp.indexOf("-") + 1, temp.lastIndexOf("-"))) - 1;
            year = Integer.parseInt(temp.substring(temp.lastIndexOf("-") + 1));
         }
      } catch (Exception e1) {
         year = c.get(Calendar.YEAR);
         month = c.get(Calendar.MONTH);
         day = c.get(Calendar.DAY_OF_MONTH);
      }
      // Create a new instance of DatePickerDialog and return it

      DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), this, year, month, day);

      SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

      try {
         Date minDate = sdf.parse(minDateStr);
         dateDialog.getDatePicker().setMinDate(minDate.getTime());
      } catch (Exception e) {
      }

      try {
         if (setCurrentMax.equalsIgnoreCase("NO")) {
            Date maxDate = sdf.parse(maxDateStr);
            dateDialog.getDatePicker().setMaxDate(maxDate.getTime());
         } else {
            dateDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
         }

      } catch (Exception e) {
      }


      return dateDialog;
   }

   @Override
   public void onResume() {
      super.onResume();
   }

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      return super.onCreateView(inflater, container, savedInstanceState);
   }

   @Override
   public void onDateSet(DatePicker view, int year, int month, int day) {
      // Do something with the date chosen by the user

      ///Make some changes in logic

      Log.d(TAG, "onDateSet..................................");

      String Day = "";
      String Month = "";

      if (day < 10) {
         Day = "0" + String.valueOf(day);
      } else {
         Day = "" + day;
      }

      if (month < 9) {
         month = month + 1;
         Month = "0" + month;
      } else {
         month = month + 1;
         Month = "" + month;
      }


      getStrDate = Day + "-" + Month + "-" + String.valueOf(year);

      if(mListner !=null && getStrDate!=null && Day!=null) {
         mListner.onDatePick(getStrDate, Day);
      }


   }


   @Override
   public void onDestroy() {
      super.onDestroy();
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
   }

   @Override
   public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);

      dismiss();
      show(getActivity().getSupportFragmentManager(), "datePicker");

   }

}
