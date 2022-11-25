package com.negd.umangwebview.ui.jeevan_pramaan.other_device_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.library.baseAdapters.BR;

import com.negd.umangwebview.BaseActivity;
import com.negd.umangwebview.R;
import com.negd.umangwebview.databinding.ActivityOtherDeviceBinding;

import javax.inject.Inject;


public class OtherDeviceActivity extends BaseActivity<ActivityOtherDeviceBinding,OtherDeviceViewModel> implements IOtherDeviceNavigator {

    private ActivityOtherDeviceBinding binding;

    @Inject
    OtherDeviceViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_other_device;
    }

    @Override
    public OtherDeviceViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=getViewDataBinding();
        viewModel.setNavigator(this);

        binding.actionbar.imgLogo.setOnClickListener(view -> {
            finish();
        });

        binding.actionbar.headerTxt.setText(getString(R.string.other));

        binding.nextTxt.setOnClickListener(view -> {
            Intent i = new Intent();
            i.putExtra("successCallback", getIntent().getStringExtra("successCallback"));
            i.putExtra("failureCallback", getIntent().getStringExtra("failureCallback"));
            setResult(RESULT_OK, i);
            this.finish();
        });
    }


    @Override
    public void onNextClick() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        CommonUtils.sendScreenNameAnalytics(this, "JP Other Device Screen");

    }

    @Override
    public void onOkClick(String type) {

    }

    @Override
    public void onCancelClick(String type) {

    }
}
