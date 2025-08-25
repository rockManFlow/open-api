package com.open.api.util;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author rock
 * @detail
 * @date 2025/8/25 10:51
 */
public class RSACryptoUtils {
    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 4096;

    // 生成密钥对
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(KEY_SIZE);
        return keyGen.generateKeyPair();
    }

    // 私钥加密
    public static String encryptPrivateKey(String data, String base64PrivateKey) throws Exception {
        PrivateKey privateKey=getPrivateKey(base64PrivateKey);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 公钥解密
    public static String decryptPublicKey(String encryptedData, String base64PublicKey) throws Exception {
        PublicKey publicKey=getPublicKey(base64PublicKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    // 将Base64编码的公钥字符串转换为PublicKey对象
    private static PublicKey getPublicKey(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    // 将Base64编码的私钥字符串转换为PrivateKey对象
    private static PrivateKey getPrivateKey(String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) throws Exception {
        // 1. 生成密钥对
        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 2. 将密钥转换为Base64字符串（便于存储）
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println("公钥: " + publicKeyStr);
        System.out.println("私钥: " + privateKeyStr);

        // 3. 加密测试
        String originalText = "Hello RSA!";
        String encryptedText = encryptPrivateKey(originalText, privateKeyStr);
        System.out.println("加密后: " + encryptedText);

        // 4. 解密测试
        String decryptedText = decryptPublicKey(encryptedText, publicKeyStr);
        System.out.println("解密后: " + decryptedText);
    }
}
