package com.negd.umangwebview.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.negd.umangwebview.R;

public class Utils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }


    public static void openPermissionSettingsDialog(final Context mContext, String msg) {
        final Dialog d = new Dialog(mContext);
        d.requestWindowFeature(d.getWindow().FEATURE_NO_TITLE);
        d.setContentView(R.layout.open_permission_settings_dialog);
        d.setCancelable(true);

        TextView cancelTxt = (TextView) d.findViewById(R.id.cancelTxt);
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        TextView settingsTxt = (TextView) d.findViewById(R.id.settingsTxt);
        settingsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                intent.setData(uri);
                mContext.startActivity(intent);
            }
        });

        TextView msgTxt = (TextView) d.findViewById(R.id.msgTxt);
        msgTxt.setText(msg);

        d.show();
    }


    public static void showInfoDialog(Context context, String msg) {

        try{
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_info);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            AppCompatTextView dialogTxt = dialog.findViewById(R.id.dialogTxt);
            dialogTxt.setText(msg);
            AppCompatButton btnOK = dialog.findViewById(R.id.btnOk);
            btnOK.setOnClickListener(view -> {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    dialog.cancel();
                }
            });
        }catch (Exception ex){

        }

    }


    public static ProgressDialog showLoadingDialog(Context context) {
        try{
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.show();
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            return progressDialog;
        }catch (Exception ex){
            return null;
        }

    }

    public static void showInfoDialogHtml(Context context, String msg) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        AppCompatTextView dialogTxt = dialog.findViewById(R.id.dialogTxt);
        dialogTxt.setText(Html.fromHtml(msg));
        AppCompatButton btnOK = dialog.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(view -> {
            dialog.cancel();
        });
    }
}
