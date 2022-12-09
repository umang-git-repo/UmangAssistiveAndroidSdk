package com.negd.umangwebview.ui.jeevan_pramaan;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.negd.umangwebview.R;
import com.negd.umangwebview.data.model.biomodel.AppData;
import com.negd.umangwebview.data.model.biomodel.DeviceData;
import com.negd.umangwebview.databinding.ActivityDeviceInfoBinding;
import com.negd.umangwebview.utils.AppLogger;
import com.negd.umangwebview.utils.CommonUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DeviceInfoActivity extends AppCompatActivity {


    private ActivityDeviceInfoBinding binding;
    private AppCompatTextView appInstallTxtGlobal;
    private AppCompatImageView appInstallImageGlobal;
    private LinearLayoutCompat appInstallLayGlobal;
    private String installedRdServiceVersion;
    private ProgressDialog mProgressDialog;
    private ArrayList<AppData> myList;
    private String image = "", name = "", make = "", appHeading = "", appDes = "", note = "",
            regdevHeading = "", regDeviceDes = "", helpHeading = "",
            helpDes = "", email = "", phone = "", userManual = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        binding = ActivityDeviceInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mProgressDialog = new ProgressDialog(DeviceInfoActivity.this);

        Intent intent = getIntent();
        if (intent != null) {
            image = intent.getStringExtra("Imang");
            name = intent.getStringExtra("name");
            appHeading = intent.getStringExtra("appHeading");
            appDes = intent.getStringExtra("appDes");
            note = intent.getStringExtra("note");
            regdevHeading = intent.getStringExtra("regdevHeading");
            regDeviceDes = intent.getStringExtra("regDeviceDes");
            helpHeading = intent.getStringExtra("helpHeading");
            helpDes = intent.getStringExtra("helpDes");
            email = intent.getStringExtra("email");
            phone = intent.getStringExtra("phone");
            userManual = intent.getStringExtra("userManual");
        }
        Glide.with(this).load(image).into(binding.bioDeviceImg);
        binding.bioDeviceTxt.setText(name);
        binding.appHeadingTxt.setText(appHeading);
        binding.appDescTxt.setText(appDes);
        binding.noteTxt.setText(note);
        binding.regDevHeadingTxt.setText(regdevHeading);
        binding.regDevDescTxt.setText(regDeviceDes);
        binding.helpHeadingTxt.setText(helpHeading);
        binding.helpDescTxt.setText(helpDes);
        binding.emailTxt.setText(email);
        binding.callTxt.setText(phone);


        binding.actionbar.headerTxt.setText("");
        //back
        binding.actionbar.imgLogo.setOnClickListener(view1 -> finish());
        if (userManual.equals("")) {
            binding.userManualLay.setVisibility(View.GONE);
        } else {
            binding.userManualLay.setVisibility(View.VISIBLE);
        }
        binding.userManualLay.setOnClickListener(view12 -> {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(userManual));
                startActivity(i);
            } catch (Exception e) {
                AppLogger.e("TAG", "Error in clickUserManual", e);
            }
        });
        if (email.equals("")) {
            binding.emailLay.setVisibility(View.GONE);
        } else {
            binding.emailLay.setVisibility(View.VISIBLE);
        }

        binding.emailLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, email);
                    startActivity(intent);
                } catch (Exception ex) {

                }
            }
        });
        if (phone.equals("")) {
            binding.phoneLay.setVisibility(View.GONE);
        } else {
            binding.phoneLay.setVisibility(View.VISIBLE);
        }

        binding.phoneLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        binding.nextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < myList.size(); i++) {
                    if (!appInstalledOrNot(myList.get(i).getPkgName())) {
                        Toast.makeText(DeviceInfoActivity.this, getString(R.string.above_apps_needs_to_be_installed), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Intent i = new Intent();
                i.putExtra("successCallback", getIntent().getStringExtra("successCallback"));
                i.putExtra("failureCallback", getIntent().getStringExtra("failureCallback"));
                setResult(RESULT_OK, i);
                finish();
            }
        });
        //add layout
        addLayout();
    }
    @Override
    protected void onResume() {
        super.onResume();
//        CommonUtils.sendScreenNameAnalytics(this, "JP Device Info Screen");
        addLayout();
    }

    private void addLayout() {
//        DeviceData deviceData=getIntent().getParcelableExtra("bioDevice");
        myList = (ArrayList<AppData>) getIntent().getSerializableExtra("bioDevice");
        binding.appLayout.removeAllViews();
//        List<AppData> appDataList=new ArrayList<>();
////        appDataList = deviceData.getAppList();
//        appDataList = myList;
        for (int i = 0; i < myList.size(); i++) {
            AppData appData = myList.get(i);
            ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.custom_bio_app_lay, binding.appLayout, false);
            final LinearLayoutCompat appInstallLay = (LinearLayoutCompat) newView.findViewById(R.id.appInstallLay);
            AppCompatImageView appInstallImg = (AppCompatImageView) newView.findViewById(R.id.appInstallImg);
            AppCompatTextView appNameTxt = (AppCompatTextView) newView.findViewById(R.id.appNameTxt);
            AppCompatTextView appInstallTxt = (AppCompatTextView) newView.findViewById(R.id.appInstallTxt);
            appInstallTxtGlobal = appInstallTxt;
            appInstallImageGlobal = appInstallImg;
            appInstallLayGlobal = appInstallLay;

            appNameTxt.setText(appData.getAppName());
            appInstallLay.setTag(appData.getPkgName());

            if (appInstalledOrNot(appData.getPkgName())) {
                showLoading();
                checkForLatestRdService(appData.getPkgName());

            } else {
                appInstallImg.setImageResource(R.drawable.not_installed);
                appInstallTxt.setText(getResources().getString(R.string.package_install));
                appInstallTxt.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                appInstallLay.setEnabled(true);
            }


            appInstallLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
//                    CommonUtils.sendClickEventAnalytics(
//                            DeviceInfoActivity.this,
//                            null,
//                            "Install Button",
//                            "clicked",
//                            "JP Device Info Screen"
//                    );

                    String pkgName = (String) appInstallLay.getTag();

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkgName)));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pkgName)));
                    }

                }
            });
            binding.appLayout.addView(newView);
        }
    }

    /**
     * Check if latest version is installed or not
     *
     * @param packageName
     * @return
     */
    private void checkForLatestRdService(String packageName) {
        this.installedRdServiceVersion = getCurrentVersion(packageName);
        GetVersionCode getVersionCode = new GetVersionCode();
        getVersionCode.execute(packageName);
    }

    /**
     * Method to get the current version
     */
    private String getCurrentVersion(String pcgName) {
        String lastestVersion = null;
        try {
            String currentVersion = getPackageManager().getPackageInfo(pcgName, 0).versionName;
            return currentVersion;
        } catch (PackageManager.NameNotFoundException e) {
            hideLoading();
            e.printStackTrace();
            return lastestVersion;
        }
    }

    private boolean isVersionCheckFail = false;

    private class GetVersionCode extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            String newVersion = null;
            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + args[0] + "&hl=en")
                        .timeout(30000)
                        .get();

                if (document != null) {
                    Elements ele = document.getElementsByTag("script");
                    Element versionElement = null;
                    for (Element e : ele) {
                        if (e.html() != null && e.html().contains("/store/apps/developer")) {
                            versionElement = e;
                        }
                    }
                    if (versionElement != null) {
                        List<String> matches = getAllMatches(versionElement.html(), "\\[\\[\\[\"\\d+.*\"\\]\\],");
                        if (matches.size() == 1) {
                            String match = matches.get(0);
                            int idx = match.indexOf('\"');
                            int lIdx = -1;
                            if (idx != -1 && idx < match.length()) {
                                lIdx = match.indexOf('\"', idx + 1);
                            }
                            if (idx != -1 && lIdx != -1 && lIdx < match.length()) {
                                newVersion = match.substring(idx + 1, lIdx);
                            }
                        }
                    }
                }

                return newVersion;
            } catch (Exception e) {
                isVersionCheckFail = true;
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            final boolean isLatest = checkVersionIsLatest(onlineVersion, installedRdServiceVersion);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupUi(isLatest);
                }
            });
        }
    }

    public List<String> getAllMatches(String text, String regex) {
        List<String> matches = new ArrayList<>();
        Matcher m = Pattern.compile("(?=(" + regex + "))").matcher(text);
        while (m.find()) {
            matches.add(m.group(1));
        }
        return matches;
    }


    private boolean checkVersionIsLatest(String onlineVersion, String installedVersion) {

        try {
            //check if any version key is null then return true
            if (onlineVersion == null || installedVersion == null) {
                hideLoading();
                return false;
            }

            String[] v1 = onlineVersion.split("\\.");
            String[] v2 = installedVersion.split("\\.");

            boolean isLatestVersion = true;

            if (v1.length != v2.length) {
                hideLoading();
                return false;
            }


            for (int pos = 0; pos < v1.length; pos++) {
                // compare v1[pos] with v2[pos] as necessary
                if (Integer.parseInt(v1[pos]) > Integer.parseInt(v2[pos])) {
                    isLatestVersion = false;
                } else if (Integer.parseInt(v1[pos]) < Integer.parseInt(v2[pos])) {
                    isLatestVersion = true;
                }
            }
            return isLatestVersion;

        } catch (Exception ex) {
            return false;
        }
    }

    private void setupUi(Boolean isLatest) {

        if (isVersionCheckFail) {
            appInstallImageGlobal.setImageResource(R.drawable.installed);
            appInstallTxtGlobal.setText(getResources().getString(R.string.package_installed));
            appInstallTxtGlobal.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            appInstallLayGlobal.setEnabled(false);
            binding.nextTxt.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            binding.nextTxt.setClickable(true);
            isVersionCheckFail = false;
            setInfoMessage("Please make sure you have latest version of the above application");

        } else if (isLatest) {
            appInstallImageGlobal.setImageResource(R.drawable.installed);
            appInstallTxtGlobal.setText(getResources().getString(R.string.package_installed));
            appInstallTxtGlobal.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            appInstallLayGlobal.setEnabled(false);
            binding.nextTxt.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            binding.nextTxt.setClickable(true);
        } else {
            appInstallImageGlobal.setImageResource(R.drawable.not_installed);
            appInstallTxtGlobal.setText(getResources().getString(R.string.update));
            appInstallTxtGlobal.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            appInstallLayGlobal.setEnabled(true);
            binding.nextTxt.setBackgroundColor(Color.parseColor("#42000000"));
            binding.nextTxt.setClickable(false);
        }

        hideLoading();
    }

    public boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public void setInfoMessage(String msg) {
        //note.set(msg);

    }

    public void hideLoading() {
        if (!isFinishing()) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
        }
    }

    public void showLoading() {
        try {
            hideLoading();
            if (!isFinishing())
                mProgressDialog = CommonUtils.showLoadingDialog(this);
        } catch (Exception ex) {

        }
    }
}
