# UMANG Assistive Android SDK

**UMANG Assistive Android SDK** provides seamless integration of UMANG (Unified Mobile Application for New-age Governance) services into partner Android applications. Developed by National e-Governance Division (NeGD), this SDK enables third-party and government partner applications to embed UMANG web views, biometric services, document submission, and state-of-the-art government utilities inside their apps.

---

## 🌟 Key Features

- **Embedded Government Services**: Seamlessly load and interact with UMANG services using a high-performance web container.
- **Customizable UI**: Customize header color, header text color, back button color, loader color, and swipe-to-refresh loader color.
- **Custom Header & Footer Layouts**: Supply custom XML header and footer layouts with custom view click handlers and SDK exit behaviors.
- **Biometric & RD Device Support**: Built-in support for Jeevan Pramaan and RD Services (Mantra, Morpho, SecuGen, Precision, Tatvik, UIDAI FaceRD).
- **QR & Barcode Scanner**: Built-in camera-based barcode/QR code scanner.
- **Rich Media & File Support**: Camera integration, file picker, image cropping (`vanniktech image cropper`), and audio recording.
- **User Session & Authentication**:
  - Auto pre-fill user mobile number.
  - NSSO Payload and JWT Token handling for authenticated flows.
  - Built-in session cleanup and `logoutUser()` support.
- **Flexible Service Views**: Filter services between `TOP` and `ALL` view types.
- **Callback Listeners**: Handle user actions for header click, footer click, and SDK initialization errors.

---

## 📋 System Requirements

| Parameter | Minimum Requirement |
|---|---|
| **Minimum SDK Version** | API 29 (Android 10) |
| **Target SDK Version** | API 36 |
| **Java Compatibility** | Java 17 |
| **Build System** | Gradle 8.x or higher |
| **AndroidX** | Required |

---

## 🚀 Installation & Setup

### 1. Add JitPack Repository

Add the JitPack repository to your `settings.gradle` file:

```groovy
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```

### 2. Add Dependency

Add the SDK dependency to your module's `build.gradle` file:

```groovy
dependencies {
    // UMANG Assistive Android SDK
    implementation 'com.github.umang-git-repo:UmangAssistiveAndroidSdk:1.3.4'
}
```

If integrating via local module inside the project:

```groovy
dependencies {
    implementation project(':umangWebView')
}
```

### 3. Java 17 & View Binding Setup

Ensure Java 17 compatibility and enable DataBinding / ViewBinding in your app's `build.gradle`:

```groovy
android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
}
```

From 1.3.3 the SDK uses plain View Binding internally, so the host app no longer needs `dataBinding true`. On 1.3.2 and older, enable `dataBinding true` in the app or the SDK crashes with `NoClassDefFoundError: androidx.databinding.DataBinderMapperImpl`.

From 1.3.4 the SDK activities ship their own AppCompat theme (`UmangSdk.Theme`), so the host app can use any theme (Material3, Compose, etc.).

---

## 💻 Usage & Integration

### 1. Implement `IUmangAssistiveListener`

Implement `IUmangAssistiveListener` in your `Activity` or `Fragment` to handle SDK events and callbacks:

#### **Java Example**:
```java
import com.negd.umangwebview.IUmangAssistiveListener;
import android.content.Context;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IUmangAssistiveListener {

    @Override
    public void onHeaderClickAction(Context context) {
        Toast.makeText(context, "Header Action Triggered", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFooterClickAction(Context context) {
        Toast.makeText(context, "Footer Action Triggered", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSdkInitializationError(String error) {
        Toast.makeText(this, "SDK Initialization Error: " + error, Toast.LENGTH_SHORT).show();
    }
}
```

#### **Kotlin Example**:
```kotlin
import com.negd.umangwebview.IUmangAssistiveListener
import android.content.Context
import android.widget.Toast

class MainActivity : AppCompatActivity(), IUmangAssistiveListener {

    override void onHeaderClickAction(context: Context) {
        Toast.makeText(context, "Header Action Triggered", Toast.LENGTH_SHORT).show()
    }

    override void onFooterClickAction(context: Context) {
        Toast.makeText(context, "Footer Action Triggered", Toast.LENGTH_SHORT).show()
    }

    override void onSdkInitializationError(error: String) {
        Toast.makeText(this, "SDK Initialization Error: $error", Toast.LENGTH_SHORT).show()
    }
}
```

---

### 2. Launching the SDK

Use `UmangAssistiveAndroidSdk.Builder` to configure and launch the UMANG Web View:

#### **Basic Integration (Java)**:
```java
String PARTNER_URL = "https://web.umang.gov.in/assistive?tenantId=YOUR_TENANT_ID&domain=YOUR_DOMAIN&token=YOUR_TOKEN";

UmangAssistiveAndroidSdk umangSdk = UmangAssistiveAndroidSdk.Builder.newInstance()
        .setDeptUrl(PARTNER_URL)
        .setAssistiveListener(this)
        .enableDeptHeader(false)
        .setHeaderTextColor("#FF018786")
        .setBackButtonColor("#FF018786")
        .build();

umangSdk.startUmangWebview(MainActivity.this);
```

#### **Advanced Integration with Custom Footers & Pre-filled Data**:
```java
UmangAssistiveAndroidSdk umangSdk = UmangAssistiveAndroidSdk.Builder.newInstance()
        .setDeptUrl(PARTNER_URL)
        .setLoggedInMobileNumber("9876543210")
        .setServiceViewType(UmangAssistiveAndroidSdk.SdkServicesViewType.ALL) // or SdkServicesViewType.TOP
        .setNssoPayload(payloadString)
        .setNssoJwtToken(jwtTokenString)
        .setAssistiveListener(this)
        .enableDeptHeader(false)
        .setHeaderColor("#000000")
        .setHeaderTextColor("#FFFFFF")
        .setBackButtonColor("#FFFFFF")
        .setLoaderColor("#FF018786")
        .setCustomFooterLayoutId(R.layout.layout_custom_footer)
        .setCustomFooterClickViewId(R.id.btn_footer_click)
        .closeSdkOnCustomFooterClick(false)
        .build();

umangSdk.startUmangWebview(this);
```

---

### 3. User Logout & Session Cleanup

To log out the current user, clear web storage, and invalidate the session token on the UMANG server:

```java
if (umangSdk != null) {
    umangSdk.logoutUser(context);
}
```

---

## 🛠️ API Reference (`UmangAssistiveAndroidSdk.Builder`)

| Method | Type | Description |
|---|---|---|
| `setDeptUrl(String deptUrl)` | `Builder` | Sets the partner department URL (mandatory). |
| `setDeptHeader(String deptHeader)` | `Builder` | Sets the department title on the header bar. |
| `enableDeptHeader(boolean enable)` | `Builder` | Enables or disables the department header view. |
| `setDeptId(String deptId)` | `Builder` | Sets the department ID. |
| `setServiceId(String serviceId)` | `Builder` | Sets the service ID. |
| `setHeaderColor(String hexColor)` | `Builder` | Sets the header bar background color (HEX format). |
| `setHeaderTextColor(String hexColor)`| `Builder` | Sets the title text color (HEX format). |
| `setBackButtonColor(String hexColor)`| `Builder` | Sets the back button tint color (HEX format). |
| `setLoaderColor(String hexColor)`    | `Builder` | Sets progress loader color (HEX format). |
| `setSwipeLoaderColor(String color)`  | `Builder` | Sets swipe-to-refresh loader color. |
| `setLoggedInMobileNumber(String mobile)` | `Builder` | Passes user's pre-authenticated mobile number. |
| `setServiceViewType(SdkServicesViewType type)` | `Builder` | Defines view mode (`SdkServicesViewType.TOP` or `SdkServicesViewType.ALL`). |
| `setNssoPayload(String payload)`     | `Builder` | Sets NSSO encrypted payload for deep-linking. |
| `setNssoJwtToken(String token)`      | `Builder` | Sets NSSO JWT token. |
| `setCustomHeaderLayoutId(@LayoutRes int layoutRes)` | `Builder` | Sets a custom XML header layout. |
| `setCustomHeaderClickViewId(@IdRes int viewId)` | `Builder` | Sets click listener ID for the custom header layout. |
| `closeSdkOnCustomHeaderClick(boolean close)` | `Builder` | Auto-close SDK on custom header click. |
| `setCustomFooterLayoutId(@LayoutRes int layoutRes)` | `Builder` | Sets a custom XML footer layout. |
| `setCustomFooterClickViewId(@IdRes int viewId)` | `Builder` | Sets click listener ID for the custom footer layout. |
| `closeSdkOnCustomFooterClick(boolean close)` | `Builder` | Auto-close SDK on custom footer click. |
| `setAssistiveListener(IUmangAssistiveListener listener)` | `Builder` | Attaches listener for SDK events. |
| `build()` | `UmangAssistiveAndroidSdk` | Builds and returns the configured SDK instance. |

---

## 🔒 Permissions & Manifest Configuration

The SDK automatically includes required permissions and package queries for biometric/RD services in its manifest:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

---

## 📁 Repository Structure

```
UmangAssistiveAndroidSdk/
├── app/                  # Sample Demo Application showing SDK integration
│   ├── src/main/java/com/negd/umangsdk/MainActivity.java
│   └── build.gradle
├── umangWebView/         # Core SDK Library Module
│   ├── src/main/java/com/negd/umangwebview/
│   │   ├── UmangAssistiveAndroidSdk.java
│   │   ├── IUmangAssistiveListener.java
│   │   ├── ui/           # Web activity & Jeevan Pramaan biometric screens
│   │   ├── data/         # API clients and data models
│   │   └── utils/        # Camera, Audio, GPS & File utilities
│   └── build.gradle
├── build.gradle          # Top-level Gradle configuration
├── settings.gradle       # Subproject declarations
├── jitpack.yml           # JitPack deployment configuration
└── README.md             # SDK Documentation
```

---

## 📄 License & Maintainer

Maintained by **National e-Governance Division (NeGD)** / **UMANG Team**.  
All rights reserved.
