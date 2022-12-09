package com.negd.umangsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.negd.umangwebview.UmangAssistiveAndroidSdk;


public class MainActivity extends AppCompatActivity {

    private UmangAssistiveAndroidSdk umangAssistiveAndroidSdk;
    private String baseUrl = "BASE_URL";
    private String tenantId = "TENANT_ID";
    private String domain ="DOMAIN_NAME";
    private String token = "TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button= (Button) findViewById(R.id.startBtn);

        button.setOnClickListener(view -> {

            String PARTNER_URL= "https://web.umang.gov.in/assistive?tenantId=rnfiservices.com&domain=rnfiservices.com&token=qa96341217382y6aifnrsbyty689c23a5c50cefde03r3n1f9i4";

            umangAssistiveAndroidSdk = UmangAssistiveAndroidSdk.Builder.newInstance()
                    .setDeptUrl(PARTNER_URL)
                    .build();
            umangAssistiveAndroidSdk.startUmangWebview(this);

        });
    }
}