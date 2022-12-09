package com.negd.umangwebview.ui.jeevan_pramaan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.negd.umangwebview.R;
import com.negd.umangwebview.data.adapter.RecyclerViewAdapter;
import com.negd.umangwebview.data.api.APIClient;
import com.negd.umangwebview.data.api.APIInterface;
import com.negd.umangwebview.data.model.biomodel.DeviceData;
import com.negd.umangwebview.data.model.biomodel.DeviceListResponse;
import com.negd.umangwebview.data.model.biomodel.RdDeviceRequest;
import com.negd.umangwebview.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JPDeviceSelectActivity extends AppCompatActivity implements RecyclerViewAdapter.RDClickListener {


    private RecyclerView mRecyclerView;
    List<DeviceData> rdResponsesList = new ArrayList<>();
    private String image = "", name = "", make = "", appHeading = "", appDes = "", note = "",
            regdevHeading = "", regDeviceDes = "", helpHeading = "",
            helpDes = "", email = "", phone = "", userManual = "";
    private ImageView image_back;
    private TextView tv_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_j_p_device_select);
        mRecyclerView = findViewById(R.id.rv_devices);
        image_back = findViewById(R.id.img_logo);
        tv_header = findViewById(R.id.header_txt);



        RdDeviceRequest request = new RdDeviceRequest();
        request.setLang("en");
        request.setVer("140");
        request.setAcc("");
        request.setClid("");
        request.setPeml(DeviceUtils.getEmail(this));
        request.setDid(DeviceUtils.getDeviceId(this));
        request.setImei("");
        request.setLac("");
        request.setLat("");
        request.setLon("");
        request.setHmk(DeviceUtils.getDeviceMake());
        request.setMcc(DeviceUtils.getMCC(this));
        request.setMnc(DeviceUtils.getMNC(this));
        request.setHmd(DeviceUtils.getDeviceModel());
        request.setOs(DeviceUtils.getMobileOS());
        request.setRot("no");
        request.setMod("app");

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<DeviceListResponse> call = apiInterface.getDeviceList(request);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        call.enqueue(new Callback<DeviceListResponse>() {
            @Override
            public void onResponse(Call<DeviceListResponse> call, Response<DeviceListResponse> response) {
                rdResponsesList = response.body().getPd().getDeviceList();
                mRecyclerView.setAdapter(new RecyclerViewAdapter(JPDeviceSelectActivity.this, rdResponsesList, JPDeviceSelectActivity.this));
            }

            @Override
            public void onFailure(Call<DeviceListResponse> call, Throwable t) {
                Toast.makeText(JPDeviceSelectActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        tv_header.setText(getResources().getString(R.string.select_your_biometric_device));
        image_back.setOnClickListener(view -> {
            finish();
        });
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
                e.printStackTrace();
            }

        }
    }

    @Override
    public void getItem(int position) {
        image = rdResponsesList.get(position).getImg();
        name = rdResponsesList.get(position).getName();
        make = rdResponsesList.get(position).getMake();
        appHeading = rdResponsesList.get(position).getAppHeading();
        appDes = rdResponsesList.get(position).getAppDesc();
        note = rdResponsesList.get(position).getNote();
        regdevHeading = rdResponsesList.get(position).getRegDevHeading();
        regDeviceDes = rdResponsesList.get(position).getRegDevDesc();
        helpHeading = rdResponsesList.get(position).getHelpHeading();
        helpDes = rdResponsesList.get(position).getHelpDesc();
        email = rdResponsesList.get(position).getEmail();
        phone = rdResponsesList.get(position).getPhone();
        userManual = rdResponsesList.get(position).getUserManual();

        if (name.equalsIgnoreCase(getString(R.string.other_devices))) {
            Intent i = new Intent(this, OtherDeviceActivity.class);
            i.putExtra("successCallback", getIntent().getStringExtra("successCallback"));
            i.putExtra("failureCallback", getIntent().getStringExtra("failureCallback"));
            startActivityForResult(i, 100);
        } else {
            Intent intent = new Intent(this, DeviceInfoActivity.class);
            intent.putExtra("Imang", image);
            intent.putExtra("name", name);
            intent.putExtra("make", make);
            intent.putExtra("appHeading", appHeading);
            intent.putExtra("appDes", appDes);
            intent.putExtra("note", note);
            intent.putExtra("regdevHeading", regdevHeading);
            intent.putExtra("regDeviceDes", regDeviceDes);
            intent.putExtra("helpHeading", helpHeading);
            intent.putExtra("helpDes", helpDes);
            intent.putExtra("email", email);
            intent.putExtra("phone", phone);
            intent.putExtra("userManual", userManual);
            intent.putParcelableArrayListExtra("bioDevice", (ArrayList<? extends Parcelable>) rdResponsesList.get(position).getAppList());
            intent.putExtra("successCallback", getIntent().getStringExtra("successCallback"));
            intent.putExtra("failureCallback", getIntent().getStringExtra("failureCallback"));
            startActivityForResult(intent, 100);
        }
    }
  }
