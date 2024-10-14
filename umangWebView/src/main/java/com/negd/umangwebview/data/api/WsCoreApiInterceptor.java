package com.negd.umangwebview.data.api;

import com.negd.umangwebview.ui.jeevan_pramaan.EncryptionDecryptionHelper;
import com.negd.umangwebview.utils.CommonUtils;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class WsCoreApiInterceptor implements Interceptor {
    /**
     * Retrofit Interceptor that modify/add headers for outgoing requests and logs the request and response.
     *
     * @param chain Request chain
     * @return Modified header request
     * @throws IOException Throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = addRequestHeaders(chain.request());
        return chain.proceed(request);
    }

    /**
     * Add public headers.
     *
     * @param request instance of request to add header
     * @return instance of Request
     */
    private Request addRequestHeaders(Request request) {
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type", "text/plain");
        builder.addHeader("Authorization", "Bearer 02f6dd09-7573-3c7d-a9e7-7f0f429f9d50");
        builder.addHeader("X-REQUEST-CONTROL", new EncryptionDecryptionHelper().getHashedToken());
        builder.addHeader("X-REQUEST-TSTAMP", CommonUtils.getTimeStamp());
        builder.addHeader("X-REQUEST-UV", CommonUtils.getUniqueNumber());
        builder.addHeader("User-Agent", System.getProperty("http.agent"));
        builder.addHeader("x-api-key", "VKE9PnbY5k1ZYapR5PyYQ33I26sXTX569Ed7eqyg");

        return builder.build();
    }
}