package com.myproject.core.common.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.util.Base64Util;
import org.hibernate.tool.schema.internal.StandardTableExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

    public static String encrypt(String secretKey, String target) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");

        System.out.println("secret key is " + secretKey);
        System.out.println("secret to byte is " + secretKey.getBytes(StandardCharsets.UTF_8));

        String restoredSecretKey = secretKey; // new String(secretKey, StandardCharsets.UTF_8);
        
        SecretKeySpec key = new SecretKeySpec(restoredSecretKey.getBytes(StandardCharsets.UTF_8), "AES");

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(target.getBytes(StandardCharsets.UTF_8));
        
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public static String decrypt(String secretKey, String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");

        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");

        System.out.println("secret key is " + secretKey);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)));

        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
