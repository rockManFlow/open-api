package com.open.api.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author rock
 * @detail
 * @date 2025/8/25 11:04
 */
public class AESCryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;

    // 生成随机密钥
    private static String generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE, new SecureRandom());
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // 加密方法
    public static String encrypt(String data, String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        // 生成随机IV
        byte[] ivBytes = new byte[16];
        new SecureRandom().nextBytes(ivBytes);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));

        // 合并IV和密文
        byte[] combined = new byte[ivBytes.length + encrypted.length];
        System.arraycopy(ivBytes, 0, combined, 0, ivBytes.length);
        System.arraycopy(encrypted, 0, combined, ivBytes.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    // 解密方法
    public static String decrypt(String encryptedData, String base64Key) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        // 分离IV和密文
        byte[] ivBytes = new byte[16];
        byte[] encryptedBytes = new byte[combined.length - ivBytes.length];
        System.arraycopy(combined, 0, ivBytes, 0, ivBytes.length);
        System.arraycopy(combined, ivBytes.length, encryptedBytes, 0, encryptedBytes.length);

        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        return new String(cipher.doFinal(encryptedBytes), "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        // 1. 生成密钥
        String secretKey = generateKey();
        System.out.println("生成的密钥(Base64): " + secretKey);

        // 2. 加密测试
        String originalText = "Hello AES Encryption!";
        String encryptedText = encrypt(originalText, secretKey);
        System.out.println("加密结果: " + encryptedText);

        // 3. 解密测试
        String decryptedText = decrypt(encryptedText, "secretKey");
        System.out.println("解密结果: " + decryptedText);
    }
}
