# Rules applied to apps that consume this SDK and enable R8/ProGuard.

# Public API used by integrators
-keep class com.negd.umangwebview.UmangAssistiveAndroidSdk { *; }
-keep class com.negd.umangwebview.UmangAssistiveAndroidSdk$* { *; }
-keep interface com.negd.umangwebview.IUmangAssistiveListener { *; }

# Methods called from the web pages through addJavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keep class com.negd.umangwebview.callbacks.** { *; }

# Gson / Retrofit models and API definitions (serialized by field name)
-keep class com.negd.umangwebview.data.model.** { *; }
-keep interface com.negd.umangwebview.data.api.APIInterface { *; }
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod

-keep class com.google.android.gms.location.** { *; }
