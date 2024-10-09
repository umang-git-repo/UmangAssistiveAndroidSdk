package com.negd.umangwebview.utils;

import android.content.Context;
import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.Map;


public class WebViewUtils {
    private WebViewUtils() {
    }

    public static void clearWebViewCookies(){
        CookieManager cookieManager = CookieManager.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                // a callback which is executed when the cookies have been removed
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                    AppLogger.d("WebViewUtils","Cookie removed: " + aBoolean);
                }
            });
        } else cookieManager.removeAllCookie();
    }

    /**
     * Global Method to handle webview callbacks
     * @param webView
     * @param url
     * @param context
     * @return
     */
    public static boolean handleWebViewCallbacks(WebView webView, String url, Context context, Map<String,String> headers){

        if (url.contains("https://www.google.com/maps/")) {
            Uri IntentUri = Uri.parse(url);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, IntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            } else {
                Toast.makeText(context, "Map app not found", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        else if (url.startsWith("http:") || url.startsWith("https:")) {
             webView.loadUrl(url);
            return true;
        }else if (url.startsWith("tel:")) {
            Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            context.startActivity(tel);
            return true;
        }else if (url.startsWith("mailto:")) {
            Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse(url));
            MailTo mt = MailTo.parse(url);
//            Intent mail = new Intent(Intent.ACTION_SENDTO);
//            mail.setType("application/octet-stream");
//            mail.putExtra(Intent.EXTRA_EMAIL, new String[]{mt.getTo()});
            intent.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
            intent.putExtra(Intent.EXTRA_TEXT, mt.getBody());
            context.startActivity(intent);
            return true;
        }else if (url.startsWith("geo:")) {
            Uri gmmIntentUri = Uri.parse(url);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            try {
                context.startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(context, "Map app not found", Toast.LENGTH_SHORT).show();
                AppLogger.e("Map exception",e);
            }
            return true;
        }
        return true;
    }
}
