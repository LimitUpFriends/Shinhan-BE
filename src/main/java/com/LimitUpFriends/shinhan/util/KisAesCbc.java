package com.LimitUpFriends.shinhan.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class KisAesCbc {

    public static String decryptBase64(String base64Cipher, String keyStr, String ivStr) throws Exception {
        byte[] key = keyStr.getBytes(StandardCharsets.UTF_8);
        byte[] iv = ivStr.getBytes(StandardCharsets.UTF_8);

        if (key.length != 32) throw new IllegalArgumentException("AES-256 키는 32바이트여야 합니다.");
        if (iv.length != 16) throw new IllegalArgumentException("IV는 16바이트여야 합니다.");

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        byte[] plain = c.doFinal(Base64.getDecoder().decode(base64Cipher));
        return new String(plain, StandardCharsets.UTF_8);
    }

}