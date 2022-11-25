package com.negd.umangwebview.ui.jeevan_pramaan.device_select_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.negd.umangwebview.BR;
import com.negd.umangwebview.BaseActivity;
import com.negd.umangwebview.R;
import com.negd.umangwebview.databinding.ActivityJPDeviceSelectBinding;
import com.negd.umangwebview.utils.AppLogger;

import javax.inject.Inject;



public class JPDeviceSelectActivity extends BaseActivity<ActivityJPDeviceSelectBinding,JPDeviceSelectViewModel> implements IJPDeviceSelectNavigator{

    private static final String TAG=JPDeviceSelectActivity.class.getSimpleName();

    @Inject
    JPDeviceSelectViewModel viewModel;

    @Inject
    JPDeviceSelectAdapter adapter;

    private ActivityJPDeviceSelectBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_j_p_device_select;
    }

    @Override
    public JPDeviceSelectViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=getViewDataBinding();
        viewModel.setNavigator(this);
        binding.setViewModel(viewModel);

//        binding.actionbar.headerTxt.setText(getResources().getString(R.string.select_your_biometric_device));
//
//        binding.actionbar.imgLogo.setOnClickListener(view -> {
//            finish();
//        });

        //setup adapter and RV
        setup();

        //subscribe data
        subscribeToLiveData();

        //set context
        adapter.setContext(this);

        //get devices
        if(isNetworkConnected()){
            showLoading();
            viewModel.getDeviceList(this);
        }else{
            showToast(getString(R.string.please_check_network_and_try_again));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    Intent i = new Intent();
                    i.putExtra("successCallback", data.getStringExtra("successCallback"));
                    i.putExtra("failureCallback", data.getStringExtra("failureCallback"));
                    setResult(RESULT_OK, i);
                    this.finish();
                }
            } catch (Exception e) {
                AppLogger.e(TAG,"Error in onActivityResult",e);
            }

        }
    }

    private void setup(){
        binding.rvDevices.setItemAnimator(new DefaultItemAnimator());
        binding.rvDevices.setAdapter(adapter);
//        binding.actionbar.imgLogo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    private void subscribeToLiveData() {
        viewModel.getListMutableLiveData().observe(this, items -> viewModel.addData(items));
    }

    @Override
    public void onHideLoader() {
        hideLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        CommonUtils.sendScreenNameAnalytics(this, "JP Device Selection Screen");
    }

    @Override
    public void onOkClick(String type) {

    }

    @Override
    public void onCancelClick(String type) {

    }
}

