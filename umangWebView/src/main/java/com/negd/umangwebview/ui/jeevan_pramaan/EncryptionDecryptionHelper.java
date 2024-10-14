package com.negd.umangwebview.ui.jeevan_pramaan;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.negd.umangwebview.utils.AppLogger;
import com.negd.umangwebview.utils.CommonUtils;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryptionHelper {
    private static final int GCM_TAG_LENGTH = 16; // Assuming GCM tag length of 16 bytes
    private static final String ENCK = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArpgmSqIv/3zYAxoNK/6eLqjeBEHFsiGJCia5wd\n" +
            "QuhCw54ceg6EyKc5mPrkEnK7CYgbJxSQO37HbnWIROMN6kRQqxa1kZFFS+xQPZ9z4Gs+njypX8HNKcse2/\n" +
            "kbwbIX4y8kcCENVVOV8URK8+znEsuN/UCzJXv2Pg0KII5ofb8wAvYNXkZ44DhcWnyxO6JohbuMvpt096NBkdq8lWtRrappL3HqpTG4Fd5H4v9b7fD8rAhBB8cAbiM2nyBz51VDovS/SZheIemKjwGLGMDiMeNcQGryOT\n" +
            " ZASX+jxe69NoFR9bQ8+5jN/88x5k53UzV8en+HRKbYjgUJzZOVfu3fL/WQIDAQAB";
    private static final String DECK = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCumCZKoi//fNgDGg0r/p4uqN4EQcWyIYkKJrnB1C6ELDnhx6DoTIpzmY+uQScrsJiBsnFJA7fsdudYhE4w3qRFCrFrWRkUVL7FA9n3Pgaz6ePKlfwc0pyx7b+RvBshfjLyRwIQ1VU5XxRErz7OcSy439QLMle/Y+DQogjmh9vzAC9g1eRnjgOFxafLE7omiFu4y+m3T3o0GR2ryVa1GtqmkvceqlMbgV3kfi/1vt8PysCEEHxwBuIzafIHPnVUOi9L9JmF4h6YqPAYsYwOIx41xAavI5NkBJf6PF7r02gVH1tDz7mM3/zzHmTndTNXx6f4dEptiOBQnNk5V+7d8v9ZAgMBAAECggEActY8iWZ4L5GL+y5Nb5x/qq0DqsUgJXQNURH7qFPJbMIyKCFH4sNFZZehe7n666+x/8zA2oeJmAz1SbFsRJSMc6T+4V6vMkIzYB6SZR71Ba1XWM6iDsswqY95K4AQUE1TcSvnXe8TqTKygCLMKrkh80+1hs/MC2TEYDXTqN2/e+qOBq6/RQ4A7E+Qd1R6/mFEHZJfmvMVl0lBmowHk060/JAgeSxE5VPnP2pQXad21w/nBtSoQDOKpAzbcSIgn4vLavtUlS87XYQ8bq3Mc4sPQe9RZ9o89aQrau0SmdgNeGYVi0tWhP2lm3b8C3XcgDQi6LwnZpzwCo9m5fEcJM4IyQKBgQD//t26TkBC2h4trvbOID2SN2hPiBySxjb3Eoaiy58ryF/d4Im56Xf61ksuy0od52vgNzW03As6vPjTJpqy/EeJrOZHLdZSOiFZzqcgXUcir2O6gM/WIU/0x/Ge7nnp0DfwTYgTNzpJA+csR9aDFhOu9e+xogJ0lV4eCEBjjC+Q+wKBgQCumOxDZkaH9uuXSbrGTsrEMO0439oLM+Er6L+Pdpy8qY0Q5ZYp2C1ZZBz8DwH6NwwKY/JcvrVVS5D90mmqsm39ubeIrmv7C04TWBQqYRp9dgRkUkVo9BGM6EqA2q/D2JT/sr/72h5FEsMblHSVEXbCo/QLYvCglxSs7jhx5tDIuwKBgQC/W2epJWc50cvvQDNzP3xm+Q37LXaWbJ6Xr/x+YpFX7A9lTrwFAbVTBq7qisGbeusTjpGR4U5vmOSzCc9n7dcX3evA102254cYl7YsJi3PiqWUu0cg/IPFKVS/BeqR0biO45XNL2JdRBKg8g4yrOUHywVilgUZ2rGg53AiOZ8w0wKBgQCpby/AjI0fvwiLrXo6nhX55H0hd2LTAkqe4OSdJX8fOu7xmctq2iXQHO5f0XSazDa8EpgNVukEWCvhlgMDKtrAoiyw0ItreWIQNaaEJe2eGRxT+t7u5gPuGTLL7u0pApI9vcq/bsF3SKjcp+mnC+aTJqZbMm3Pei4PT7KpHlQ4pwKBgDeLXCwO6cCa+4nV4t5ktCEzQPiu/yEMAL0hf3RwPr7WuckwyHm3cUt7DFCEf6+bJj+hpxVXGcdgVxDZjyh4s8YE8AAd+2iv8I61gF475KAOOMbkt21TzTI0y2R2BN3JvB1iTmf6CkV6C82b4UELlKjEOX342756ZWQbhB8Yld1T";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public <T> T getDecryptedObject(String response, Class<T> type) throws Exception {
        Gson gson=new Gson();
        String decryptResponse = decryptAes(response);
        return gson.fromJson(decryptResponse, type);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encryptAes(String plaintext) throws Exception {
        // Generate a random AES key and IV
        PublicKey publicKey = getPublicKey();
        SecretKey aesKey = generateAesKey();
        byte[] iv = generateIv();

        // Encrypt the plaintext with AES-GCM using the generated key and IV
        byte[] ciphertext = encryptPlainTextWithIvSpec(plaintext.getBytes(), aesKey, iv);

        // Encrypt the AES key with the recipient's public key (RSA)
        byte[] encryptedAesKeyBytes = encryptRSA(aesKey.getEncoded(), publicKey);
        byte[] ivBytes = encryptRSA(iv, publicKey);

        // Combine the encrypted AES key and ciphertext
        return (Base64.getEncoder().encodeToString(encryptedAesKeyBytes) + ":"
                + Base64.getEncoder().encodeToString(ivBytes) + ":"
                + Base64.getEncoder().encodeToString(ciphertext));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decryptAes(String encryptedPayload) throws Exception {
        PrivateKey privateKey = getPrivateKey();

        String[] parts = encryptedPayload.split(":");
        String aesKey = parts[0];
        byte[] encryptedAesKey = Base64.getDecoder().decode(aesKey);
        String ivKey = parts[1];
        byte[] decIVKey = Base64.getDecoder().decode(ivKey);
        String ciphertext = parts[2];

        // Decrypt the AES key using the private key (RSA)
        byte[] decryptedAESKey = decryptRSA(encryptedAesKey, privateKey);
        SecretKey aesKeySpec = new SecretKeySpec(decryptedAESKey, 0, decryptedAESKey.length, "AES");

        // Decrypt the IV using the private key (RSA)
        byte[] decryptedIVKey = decryptRSA(decIVKey, privateKey);

        // Decrypt the ciphertext with AES-GCM using the decrypted AES key
        return decryptPlaintextWithIVSpec(Base64.getDecoder().decode(ciphertext), aesKeySpec, decryptedIVKey);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private PublicKey getPublicKey() throws Exception {
        // Remove any whitespace and newline characters
        String publicKeyPEM = ENCK.replaceAll("\\s+", "");

        // Decode the Base64-encoded public key
        byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);

        // Convert the key to a PublicKey object
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private SecretKey generateAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] generateIv() {
        byte[] iv = new byte[GCM_TAG_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] encryptPlainTextWithGcmSpec(byte[] plaintextByteArray, SecretKey key, byte[] IV) throws Exception {
        // Get Cipher Instance for selected algorithm
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec for key
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec for key
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

        // Initialize Cipher for ENCRYPT_MODE for encrypt plaintext
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Encryption
        return cipher.doFinal(plaintextByteArray);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] encryptPlainTextWithIvSpec(byte[] plaintextByteArray, SecretKey key, byte[] IV) throws Exception {
        // Get Cipher Instance for selected algorithm
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Create SecretKeySpec for key
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Use IvParameterSpec for CBC mode
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

        // Initialize Cipher for ENCRYPT_MODE for encrypt plaintext
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

        // Perform Encryption
        return cipher.doFinal(plaintextByteArray);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] encryptRSA(byte[] plaintextByteArray, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plaintextByteArray);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private PrivateKey getPrivateKey() throws Exception {
        // Remove any whitespace and newline characters
        String privateKeyPEM = DECK.replaceAll("\\s+", "");

        // Decode the Base64-encoded private key
        byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);

        // Convert the key to a PrivateKey object
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] decryptRSA(byte[] cipherTextByteArray, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherTextByteArray);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String decryptPlaintextWithIVSpec(byte[] cipherTextByteArray, SecretKey key, byte[] ivByteArray) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByteArray); // Use IvParameterSpec for CBC mode
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
        byte[] decryptedText = cipher.doFinal(cipherTextByteArray);
        return new String(decryptedText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String decryptPlaintextWithGcmSpec(byte[] cipherTextByteArray, SecretKey key, byte[] ivByteArray) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, ivByteArray);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        byte[] decryptedText = cipher.doFinal(cipherTextByteArray);
        return new String(decryptedText);
    }

    /**
     * Method to get MD5 value from json
     * @param json - json value
     * @return- MD5 value
     */
    public String getMD5( String json) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((json + "|" + "R%d&Wst676#(Na").getBytes());
            byte[] mdbytes = md.digest();
            //convert the byte to hex format
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            md5 = sb.toString();
        } catch (Exception e) {
            md5 = "";
        }
        return md5;
    }

    public String getHashedToken(){
        String xReqHeader = getHashWithSalt("|" + CommonUtils.getTimeStamp() + "|" + "$f%GY#JX^9H@" + "|");
        return xReqHeader;
    }

    // Token Hashing
    private String getHashWithSalt(String input) {
        byte byteHash[] = null;
        StringBuffer sb = null;
        StringBuffer buffer = null;
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byteHash = md.digest();
            sb = new StringBuffer();
            for (int i = 0; i < byteHash.length; i++) {
                sb.append(Integer.toString((byteHash[i] & 0xff) + 0x100, 16).substring(1));
            }

            buffer = new StringBuffer();
            for (int i = 0; i < byteHash.length; i++) {
                String hex = Integer.toHexString(0xff & byteHash[i]);
                if (hex.length() == 1) buffer.append('0');
                buffer.append(hex);
            }

        } catch (Exception e) {
        } finally {
            byteHash = null;
        }

        return buffer.toString();

    }
}
