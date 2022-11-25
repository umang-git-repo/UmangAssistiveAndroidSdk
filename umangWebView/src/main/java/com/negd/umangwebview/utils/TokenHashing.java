package com.negd.umangwebview.utils;

import java.security.MessageDigest;

/**
 * Created by CH-E00812 on 10/26/2016.
 */

public class TokenHashing {

    private static final String TAG=TokenHashing.class.getName();

    private byte byteHash[] = null;
    private StringBuffer sb = null;
    private StringBuffer buffer = null;
    private MessageDigest md = null;

    public String getHashWithSalt(String input) {


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

            AppLogger.e(TAG,e.getMessage());
        } finally {
            byteHash = null;
        }

        return buffer.toString();

    }


}
