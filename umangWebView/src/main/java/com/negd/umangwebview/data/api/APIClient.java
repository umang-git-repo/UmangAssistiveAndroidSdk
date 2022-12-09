package com.negd.umangwebview.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
//    public static final String BASE_URL = "https://app.umang.gov.in/t/negd.gov.in/";
    public static final String BASE_URL = "https://apigw.umangapp.in/";
    private static Retrofit retrofit = null;


    public static final Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
