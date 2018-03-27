package com.common.basecomponent.util;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.common.basecomponent.encrypt.rsa.RSAEncrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author chenweibin
 * @time 2017/4/7.
 */

public class EncryptUtil {

    public static String HMacSha1(String key, String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(key.getBytes(), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(value.getBytes());
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    sb.append(0);
                }
                sb.append(shaHex);
            }
            return sb.toString();
        } catch (Exception e) {
            android.util.Log.e("hash_hmac", "Exception [" + e.getMessage() + "]", e);
        }
        return "";
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

//            // Create Hex String
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : messageDigest)
//                hexString.append(Integer.toHexString(0xFF & b));
            return bytesToHex(messageDigest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    public static String createSHA1(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");

            byte[] buffer = new byte[1024 * 10];
            int len;
            FileInputStream in = new FileInputStream(file);
            while ((len = in.read(buffer)) > 0) {
                digest.update(buffer, 0, len);
            }
            in.close();

            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decryptAES(String value, String key) throws Exception {
        byte[] decode = Base64.decode(value, Base64.DEFAULT);
        byte[] decrypt = decryptAES(decode, key);

        byte[] d = new byte[]{decrypt[0], decrypt[decrypt.length - 1]};
        String first = new String(d);
        if (first.equalsIgnoreCase("ok")) {
            return new String(decrypt, 1, decrypt.length - 2);
        }
        return null;
    }

    public static byte[] decryptAES(byte[] encrypted, String key) throws Exception {
        Cipher cipher = getCipher(key);

        byte[] decrypt = cipher.doFinal(encrypted);

        int length = decrypt.length;
        for (int i = decrypt.length - 1; i > 0; i--) {
            if (decrypt[i] != 0)
                break;
            length--;
        }

        byte[] decryptCopy = new byte[length];
        System.arraycopy(decrypt, 0, decryptCopy, 0, length);
        return decryptCopy;
    }

    @NonNull
    public static Cipher getCipher(String key) throws Exception {
        byte[] rawKey = key.getBytes("ASCII");
        SecretKeySpec sKeySpec = new SecretKeySpec(rawKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

        String ivParameter = "0000000000000000";
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
        return cipher;
    }

    public static InputStream decryptAES(String key, InputStream encryptedInput) throws Exception {
        Cipher cipher = getCipher(key);
        return new CipherInputStream(encryptedInput, cipher);
    }

    public static String rsaEncrypt(String data, String key) {
        byte[] cipherData = new byte[0];
        try {
            cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(key), data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cipher = com.common.basecomponent.encrypt.rsa.Base64.encode(cipherData);
        return cipher;
    }
}
