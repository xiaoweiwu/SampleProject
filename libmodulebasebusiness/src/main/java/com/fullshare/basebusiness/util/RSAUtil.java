package com.fullshare.basebusiness.util;

import com.common.basecomponent.encrypt.rsa.Base64;
import com.common.basecomponent.encrypt.rsa.RSAEncrypt;
import com.fullshare.basebusiness.constants.AppConfig;

/**
 * author: wuxiaowei
 * date : 2017/3/23
 */

public class RSAUtil {
    /**
     * 公钥加密过程
     *
     * @param data
     * @return
     */
    public static String rsaEncrypt(String data, String key) {
        byte[] cipherData = new byte[0];
        try {
            cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(key), data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cipher = Base64.encode(cipherData);
        return cipher;
    }

    /**
     * 公钥加密过程
     * @param data
     * @return
     */
    public static String encrypt(String data){
        byte[] cipherData = new byte[0];
        try {
            cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(AppConfig.APP_ENCRYPT_PUBLIC_KEY),data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cipher= Base64.encode(cipherData);
        return cipher;
    }
}
