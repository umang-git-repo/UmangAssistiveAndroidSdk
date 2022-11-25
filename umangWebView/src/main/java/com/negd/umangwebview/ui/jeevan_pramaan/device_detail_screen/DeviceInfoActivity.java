package com.negd.umangwebview.ui.jeevan_pramaan.device_detail_screen;

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

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;

import com.negd.umangwebview.BaseActivity;
import com.negd.umangwebview.R;
import com.negd.umangwebview.data.model.jp.AppData;
import com.negd.umangwebview.data.model.jp.DeviceData;
import com.negd.umangwebview.databinding.ActivityDeviceInfoBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;


public class DeviceInfoActivity extends BaseActivity<ActivityDeviceInfoBinding,DeviceInfoViewModel> {

    @Inject
    DeviceInfoViewModel viewModel;

    private ActivityDeviceInfoBinding binding;
    private AppCompatTextView appInstallTxtGlobal;
    private AppCompatImageView appInstallImageGlobal;
    private LinearLayoutCompat appInstallLayGlobal;
    private String  installedRdServiceVersion;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_device_info;
    }

    @Override
    public DeviceInfoViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=getViewDataBinding();
        binding.setViewModel(viewModel);

        //set device data to binding
        binding.setDevice(getIntent().getParcelableExtra("bioDevice"));

        //set view model device
        viewModel.setDeviceData(getIntent().getParcelableExtra("bioDevice"),this);

        binding.actionbar.headerTxt.setText("");

        //back
        binding.actionbar.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void addLayout(){
        DeviceData deviceData=getIntent().getParcelableExtra("bioDevice");
        binding.appLayout.removeAllViews();
        List<AppData> appDataList=new ArrayList<>();
        appDataList = deviceData.getAppList();

        for (int i = 0; i < appDataList.size(); i++) {
            AppData  appData=appDataList.get(i);
            ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.custom_bio_app_lay, binding.appLayout, false);

            final LinearLayoutCompat appInstallLay = (LinearLayoutCompat) newView.findViewById(R.id.appInstallLay);
            AppCompatImageView appInstallImg = (AppCompatImageView) newView.findViewById(R.id.appInstallImg);
            AppCompatTextView appNameTxt = (AppCompatTextView) newView.findViewById(R.id.appNameTxt);
            AppCompatTextView appInstallTxt = (AppCompatTextView) newView.findViewById(R.id.appInstallTxt);
            appInstallTxtGlobal=appInstallTxt;
            appInstallImageGlobal=appInstallImg;
            appInstallLayGlobal=appInstallLay;

            appNameTxt.setText(appData.getAppName());
            appInstallLay.setTag(appData.getPkgName());

            if (viewModel.appInstalledOrNot(appData.getPkgName())) {
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
     * @param packageName
     * @return
     */
    private void checkForLatestRdService(String packageName){
        this.installedRdServiceVersion=getCurrentVersion(packageName);
        GetVersionCode getVersionCode=new GetVersionCode();
        getVersionCode.execute(packageName);
    }

    /**
     * Method to get the current version
     */
    private String getCurrentVersion(String pcgName){
        String lastestVersion=null;
        try {
            String currentVersion= getPackageManager().getPackageInfo(pcgName,0).versionName;
            return currentVersion;
        } catch (PackageManager.NameNotFoundException e) {
            hideLoading();
            e.printStackTrace();
            return lastestVersion;
        }
    }

    private boolean isVersionCheckFail=false;

    @Override
    public void onOkClick(String type) {

    }

    @Override
    public void onCancelClick(String type) {

    }

    private class GetVersionCode extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            String newVersion = null;
            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id="+args[0]+"&hl=en")
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
                isVersionCheckFail=true;
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            final boolean isLatest=checkVersionIsLatest(onlineVersion,installedRdServiceVersion);

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
        while(m.find()) {
            matches.add(m.group(1));
        }
        return matches;
    }

    private boolean checkVersionIsLatest(String onlineVersion,String installedVersion){
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

        }catch (Exception ex){
            return false;
        }
    }

    private void setupUi(Boolean isLatest){

        if(isVersionCheckFail){
            appInstallImageGlobal.setImageResource(R.drawable.installed);
            appInstallTxtGlobal.setText(getResources().getString(R.string.package_installed));
            appInstallTxtGlobal.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            appInstallLayGlobal.setEnabled(false);
            binding.nextTxt.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            binding.nextTxt.setClickable(true);
            isVersionCheckFail=false;
            viewModel.setInfoMessage("Please make sure you have latest version of the above application");

        }
        else if(isLatest){
            appInstallImageGlobal.setImageResource(R.drawable.installed);
            appInstallTxtGlobal.setText(getResources().getString(R.string.package_installed));
            appInstallTxtGlobal.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            appInstallLayGlobal.setEnabled(false);
            binding.nextTxt.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            binding.nextTxt.setClickable(true);
        }else{
            appInstallImageGlobal.setImageResource(R.drawable.not_installed);
            appInstallTxtGlobal.setText(getResources().getString(R.string.update));
            appInstallTxtGlobal.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            appInstallLayGlobal.setEnabled(true);
            binding.nextTxt.setBackgroundColor(Color.parseColor("#42000000"));
            binding.nextTxt.setClickable(false);
        }

        hideLoading();
    }


}
