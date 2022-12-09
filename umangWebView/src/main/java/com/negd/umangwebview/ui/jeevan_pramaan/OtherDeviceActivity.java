package com.negd.umangwebview.ui.jeevan_pramaan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.negd.umangwebview.R;
import com.negd.umangwebview.databinding.ActivityDeviceInfoBinding;

public class OtherDeviceActivity extends AppCompatActivity {

    private ActivityDeviceInfoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_device);
        binding = ActivityDeviceInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.actionbar.imgLogo.setOnClickListener(view1 -> finish());

        binding.actionbar.headerTxt.setText(getString(R.string.other));

        binding.nextTxt.setOnClickListener(view2 -> {
            Intent i = new Intent();
            i.putExtra("successCallback", getIntent().getStringExtra("successCallback"));
            i.putExtra("failureCallback", getIntent().getStringExtra("failureCallback"));
            setResult(RESULT_OK, i);
            this.finish();
        });
    }
}
