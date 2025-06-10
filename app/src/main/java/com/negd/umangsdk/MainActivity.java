package com.negd.umangsdk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.negd.umangsdk.databinding.ActivityMainBinding;
import com.negd.umangwebview.UmangAssistiveAndroidSdk;
import com.negd.umangwebview.IUmangAssistiveListener;


public class MainActivity extends AppCompatActivity implements IUmangAssistiveListener {

    private UmangAssistiveAndroidSdk umangAssistiveAndroidSdk;
    private ActivityMainBinding binding;
    private String baseUrl = "BASE_URL";
    private String tenantId = "TENANT_ID";
    private String domain ="DOMAIN_NAME";
    private String token = "TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btStartSpiceMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String PARTNER_URL = "https://web.umang.gov.in/assistive?tenantId=spicemoney.com&domain=spicemoney.com&token=dg96341a3738jjk64y53dbdb8815ty689c271642fde03hdd4sm10f";
                String PARTNER_URL = "https://web.umang.gov.in/assistive_stg?tenantId=spicemoney.com&domain=spicemoney.com&token=dg96341a3738jjk64y53dbdb8815ty689c271642fde03hdd4sm10f";
                umangAssistiveAndroidSdk = UmangAssistiveAndroidSdk.Builder.newInstance()
                        .setDeptUrl(PARTNER_URL)
                        .setAssistiveListener(MainActivity.this)
                        .enableDeptHeader(false)
                        .setHeaderTextColor("#FF018786")
                        .setBackButtonColor("#FF018786")
                        .setCustomFooterLayoutId(R.layout.layout_header_view)
                        .setCustomFooterClickViewId(R.id.bt_click)
                        .closeSdkOnCustomFooterClick(false)
                        .build();
                umangAssistiveAndroidSdk.startUmangWebview(MainActivity.this);
            }
        });
        binding.btStartRnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PARTNER_URL= "https://web.umang.gov.in/assistive?tenantId=rnfiservices.com&domain=rnfiservices.com&token=qa96341217382y6aifnrsbyty689c23a5c50cefde03r3n1f9i4";
                umangAssistiveAndroidSdk = UmangAssistiveAndroidSdk.Builder.newInstance()
                        .setDeptUrl(PARTNER_URL)
                        .setAssistiveListener(MainActivity.this)
                        .enableDeptHeader(false)
                        .setHeaderTextColor("#FF018786")
                        .setBackButtonColor("#FF018786")
                        .build();
                umangAssistiveAndroidSdk.startUmangWebview(MainActivity.this);
            }
        });
        binding.btStartNsso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PARTNER_URL = "https://web.umang.gov.in/assistive_stg?tenantId=digilocker.gov.in&domain=digilocker.gov.in&token=18482b912a60126bb232240a0e47ff6eefaca1c97e6d2e0f29c13128a8dc58f8";
//            String PARTNER_URL= "https://web.umang.gov.in/assistive?tenantId=digilocker.gov.in&domain=digilocker.gov.in&token=18482b912a60126bb232240a0e47ff6eefaca1c97e6d2e0f29c13128a8dc58f8";
//            String PARTNER_URL= "https://web.umang.gov.in/assistive_stg?tenantId=digilocker.gov.in&domain=digilocker.gov.in&token=18482b912a60126bb232240a0e47ff6eefaca1c97e6d2e0f29c13128a8dc58f8";

                umangAssistiveAndroidSdk = UmangAssistiveAndroidSdk.Builder.newInstance()
                        .setDeptUrl(PARTNER_URL)
                        .setNssoPayload(binding.etPayload.getText().toString().trim())
                        .setNssoJwtToken(binding.etJwt.getText().toString().trim())
                        .setAssistiveListener(MainActivity.this)
                        .enableDeptHeader(false)
                        .setHeaderTextColor("#FF018786")
                        .setBackButtonColor("#FF018786")
                        .setCustomFooterLayoutId(R.layout.layout_header_view)
                        .setCustomFooterClickViewId(R.id.bt_click)
                        .closeSdkOnCustomFooterClick(false)
                        .build();
                umangAssistiveAndroidSdk.startUmangWebview(MainActivity.this);
            }
        });
        binding.btStartBoiFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PARTNER_URL = "https://web.umang.gov.in/assistive?tenantId=bankofindia.co.in&domain=bankofindia.co.in&token=98339e543c8c16a58061d615d26aa46f5c43d1e9c9a8e2db509845de278a6f24";
                String STAGING_PARTNER_URL = "https://web.umang.gov.in/assistive_stg?tenantId=bankofindia.co.in&domain=bankofindia.co.in&token=98339e543c8c16a58061d615d26aa46f5c43d1e9c9a8e2db509845de278a6f24";
                umangAssistiveAndroidSdk = UmangAssistiveAndroidSdk.Builder.newInstance()
                        .setDeptUrl(STAGING_PARTNER_URL)
                        .setLoggedInMobileNumber(binding.etBoiMobile.getText().toString().trim())
                        .setServiceViewType(UmangAssistiveAndroidSdk.SdkServicesViewType.ALL)
                        .setAssistiveListener(MainActivity.this)
                        .enableDeptHeader(false)
                        .setHeaderTextColor("#FF018786")
                        .setBackButtonColor("#FF018786")
                        .setCustomFooterLayoutId(R.layout.layout_header_view)
                        .setCustomFooterClickViewId(R.id.bt_click)
                        .closeSdkOnCustomFooterClick(false)
                        .build();
                umangAssistiveAndroidSdk.startUmangWebview(MainActivity.this);
            }
        });
        binding.btStartBoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PARTNER_URL = "https://web.umang.gov.in/assistive?tenantId=bankofindia.co.in&domain=bankofindia.co.in&token=98339e543c8c16a58061d615d26aa46f5c43d1e9c9a8e2db509845de278a6f24";
                String STAGING_PARTNER_URL = "https://web.umang.gov.in/assistive_stg?tenantId=bankofindia.co.in&domain=bankofindia.co.in&token=98339e543c8c16a58061d615d26aa46f5c43d1e9c9a8e2db509845de278a6f24";
                umangAssistiveAndroidSdk = UmangAssistiveAndroidSdk.Builder.newInstance()
                        .setDeptUrl(STAGING_PARTNER_URL)
                        .setLoggedInMobileNumber(binding.etBoiMobile.getText().toString().trim()).
                        setServiceViewType(UmangAssistiveAndroidSdk.SdkServicesViewType.TOP)
                        .setAssistiveListener(MainActivity.this)
                        .enableDeptHeader(false)
                        .setHeaderTextColor("#FF018786")
                        .setBackButtonColor("#FF018786")
                        .setCustomFooterLayoutId(R.layout.layout_header_view)
                        .setCustomFooterClickViewId(R.id.bt_click)
                        .closeSdkOnCustomFooterClick(false)
                        .build();
                umangAssistiveAndroidSdk.startUmangWebview(MainActivity.this);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleNssoDeepLink(AppCompatEditText jwtEditText) throws Exception {
        if(getIntent()!=null) {
            String data = getIntent().getData().getQueryParameter("data");
            if (data != null && !data.isEmpty()) {
                jwtEditText.setText(data);
            }
        }
    }

    @Override
    public void onHeaderClickAction(Context context) {
        Toast.makeText(this, "onHeaderClickAction", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFooterClickAction(Context context) {
        String uriString = "umang://web.umang.gov.in/nsso?data=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoiNm9ZUzVUSWIzOUQyeDhEdkt1dnZtRGJVelliNzVNQUJocGU3QXVHMUVPRlN4Wno2VlVzNXdzUnlTVHVVVmVxWnZFVk9RclF5ZHh4ODVkNE9PVlY5ellzZjJvb2pmY0Naemp3V09WK0pUb2ZnbS81V1VjbDZGR3dPbDhXSWRhaWNjbEpKcG8xWlpvczQ4WVV3N3Z5MGZSQ0lNbjc0ZkV4Vy8rampkcjBwbWFPL3NoVzBYTVpuK0lpN2VvQy85OHpBV2dwdXFtaCszYVBjSHpDNTVsRmk4TTYxVDV1T0ROcDE5VEVUME9ZcllVdWE5RVI4cktGSVpIbXYxbWNCUmliWFpFalA3Q3cyazE5TkFGM251SFQxaUZwbDlnWFp0L3hkdDNFcFpUY1UzN0xtWUxyc0ZNcHQ3a3VseFlhZFFqbVpzOXJBT3dRaGVrTkFYVDFlL1RiOURRPT0ifQ.nusxeSnmvg1C1qIs2ztmk8_PbpmYh6ympW9w3wQp4vnMP-jBACELootF-b_4thpbAlhVHSFZe3qejwscw6iK7As8rhFQPlI36DaQfLOHdiqUmYZN_Lx7xm4ZJwMHmP0_8qKZ9JnI7RkK78OpLJmZGjmW0B6FwgplpWNtn8OvhE7LvCmagkatbWjluImjLx_oR9NRR1O-0Wr0TVtZO8dCuLI932YfUVDEB5T9WWNzgjIaYiyasbLdR9CnBYaU8kAb7oAsiDV-VecPNB8hDUc5cR4mccxBnFvmxmzln2yOkRha9lKLJPFIhx27gjlDE_NnpRLP2F6YrBPRdZqspa-KkA";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Check if the app that can handle the URI is installed
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "App not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSdkInitializationError(String error) {
        Toast.makeText(this, "Error :"+error, Toast.LENGTH_SHORT).show();
    }
}