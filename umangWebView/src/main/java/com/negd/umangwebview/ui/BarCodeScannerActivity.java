package com.negd.umangwebview.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.negd.umangwebview.R;
import com.negd.umangwebview.databinding.ActivityBarCodeScannerBinding;

import java.util.List;


public class BarCodeScannerActivity extends AppCompatActivity {

    private ActivityBarCodeScannerBinding binding;

    //private BarcodeCapture barcodeCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityBarCodeScannerBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        //setup barcode capture


        //barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);

        //barcodeCapture.setRetrieval(this);
    }



    @Override
    protected void onResume() {
        super.onResume();

    }
}