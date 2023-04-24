package com.myproject.core.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

    public static String encrypt(String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");

        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");

        cipher.init(Cipher.ENCRYPT_MODE, key);

        // byte[] encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        
        return Base64.getEncoder().encodeToString(cipher.doFinal());
    }
    
    public static String decrypt(String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");

        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");

        cipher.init(Cipher.DECRYPT_MODE, key);

        // byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encrypted));

        return new String(cipher.doFinal(), StandardCharsets.UTF_8);
    }
}
