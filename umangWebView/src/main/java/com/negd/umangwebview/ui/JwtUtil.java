package com.negd.umangwebview.ui;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.negd.umangwebview.utils.AppLogger;

import org.json.JSONObject;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import kotlin.text.Charsets;

class JwtUtil {

    @RequiresApi(api = Build.VERSION_CODES.O)
    private PublicKey loadPublicKey() throws Exception {
        // Extract the Base64-encoded public key from the PEM file
        String NSSO_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy/s+Q23FcTtpdCm/sN3x\n" +
                "9RiaYyUX9xatDZQBAOEiTkxmUe6EVDj55q4EbwSsb+Y4TGhA2ZLTAGBXy+DGp9Kz\n" +
                "oQ4iYqrlum70/3P9rCrSUc0/K6UVIZNyDMSxcusMS7/beQoaCDvwX2ZQVe1D31LH\n" +
                "GmwEL9iUb4JkmC+CFwsnHwHtnwN/2jHu9MBLq6+BkNlW8ovl5uCKWHzOpKeK8NnE\n" +
                "EPBf9UVZatRgIXqrF1pyGKp0VNU3Ij3gY0fMtt4HV446NOq7j23prTFZSyvmYu8S\n" +
                "oIhREBm2kdb4feUjVwXowKRNUFoLscMYhxsL5GSK8SLzDDbKTvpye9FULD4R9C41\n" +
                "SwIDAQAB";
        String publicKeyPEM = NSSO_PUBLIC_KEY
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean customVerifyJwtWithPublicKey(String jwtToken) {
        try {
            // Load the public key from the PEM file
            PublicKey publicKey = loadPublicKey();

            // Split the JWT and base64 decode
            Pair<String, String> headerAndPayloadAndSignature = customSplitJwtToken(jwtToken);

            // Verify the signature using the public key
            boolean isSignatureValid = customVerifySignature(publicKey, headerAndPayloadAndSignature.first, headerAndPayloadAndSignature.second);

            if (!isSignatureValid) {
//                Log.i("JWTUtils", "JWT signature is invalid");
                return false;
            }

            // Signature is valid, now check other claims (e.g., expiration time) if necessary
//            Log.i("JWTUtils", "JWT signature is valid");
            return true;

        } catch (Exception e) {
//            Log.i("JWTUtils","JWT verification failed: " + e.getMessage());
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Pair<String, String> customSplitJwtToken(String jwtToken) {
        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) throw new IllegalArgumentException("Invalid JWT token");

        String headerAndPayload = parts[0] + "." + parts[1];
        String signature = parts[2];

        return new Pair<>(headerAndPayload, signature);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean customVerifySignature(PublicKey publicKey, String headerAndPayload, String signature) throws Exception {
        byte[] signedData = headerAndPayload.getBytes(Charsets.UTF_8);
        byte[] signatureBytes = base64UrlDecode(signature);

        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(signedData);

        return sig.verify(signatureBytes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] base64UrlDecode(String input) {
        String decoded = input.replace('-', '+').replace('_', '/');
        return Base64.getDecoder().decode(decoded);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String customExtractPayload(String jwtToken) throws Exception {
        // Split the JWT into its parts (header, payload, and signature)
        String[] parts = jwtToken.split("\\.");

        // Check if the token has the correct structure
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        // Extract
        String payload = parts[1];

        // JWT uses URL-safe Base64 encoding, so convert '-' to '+' and '_' to '/'
        String adjustedInput = payload.replace('-', '+').replace('_', '/');

        // Add padding if necessary (Base64 URL-safe encoding omits padding)
        String paddedInput;
        int mod = adjustedInput.length() % 4;

        if (mod == 2) {
            paddedInput = adjustedInput + "==";
        } else if (mod == 3) {
            paddedInput = adjustedInput + "=";
        } else {
            paddedInput = adjustedInput;
        }

        // Decode the Base64 URL-encoded string
        byte[] byteArray = Base64.getDecoder().decode(paddedInput);

        // decode the payload (second part of the JWT)
        return new String(byteArray, Charsets.UTF_8);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getDataFromPayload(String payload) throws Exception {
        JSONObject jsonObject = new JSONObject(payload);
        return jsonObject.getString("data");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    Pair<Boolean, String> verifyJWTAndGetPayload(String jwtToken) throws Exception {
        if (customVerifyJwtWithPublicKey(jwtToken)) {
            String payload = customExtractPayload(jwtToken);
            return new Pair<>(true, getDataFromPayload(payload));
        } else {
            return new Pair<>(false, "");
        }
    }
}

class Pair<T, U> {
    public final T first;
    public final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}
