package ru.russeb.cryptosite.cipher;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


//Шифрование RSA при помощи втроенной библиотеки Java Cryptography Architecture
public class RsaCipher {
    /**
     * Шифрует данные с помощью открытого ключа RSA.
     *
     * @param data             Текст для шифрования.
     * @param publicKeyString  Открытый ключ RSA в формате Base64.
     * @return Зашифрованный текст в формате Base64.
     */
    public static String encrypt(String data, String publicKeyString) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyString);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Расшифровывает данные с помощью закрытого ключа RSA.
     *
     * @param encryptedData     Зашифрованный текст в формате Base64.
     * @param privateKeyString  Закрытый ключ RSA в формате Base64.
     * @return Расшифрованный текст.
     * @throws Exception Если возникла ошибка при расшифровании.
     */
    public static String decrypt(String encryptedData, String privateKeyString) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyString);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Получает закрытый ключ RSA из его строкового представления в формате Base64.
     *
     * @param privateKeyString Закрытый ключ RSA в формате Base64.
     * @return Объект PrivateKey, представляющий закрытый ключ.
     */
    public static PrivateKey getPrivateKey(String privateKeyString) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения закрытого ключа", e);
        }
    }

    /**
     * Получает открытый ключ RSA из его строкового представления в формате Base64.
     *
     * @param publicKeyString Открытый ключ RSA в формате Base64.
     * @return Объект PublicKey, представляющий открытый ключ.
     */
    public static PublicKey getPublicKey(String publicKeyString) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения открытого ключа", e);
        }
    }
}
