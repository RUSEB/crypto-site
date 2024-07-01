package ru.russeb.cryptosite.cipher;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//Шифрование AES при помощи втроенной библиотеки Java Cryptography Architecture
public class AesCipher {

    /**
     * Создает экземпляр IvParameterSpec из строки с инициализирующим вектором (IV).
     * @param strIv Строка с инициализирующим вектором.
     * @return Экземпляр IvParameterSpec с указанным вектором.
     */
    private static IvParameterSpec getIv(String strIv){
        return new IvParameterSpec(strIv.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Шифрует данные с использованием AES-шифрования. Если передан инициализирующий вектор, то используется шифрование в режиме CBC с PKCS5Padding.
     * @param plainText Исходный текст для шифрования.
     * @param secretKey Секретный ключ для шифрования.
     * @param strIv Строка с инициализирующим вектором (может быть пустой).
     * @return Зашифрованный текст в формате Base64.
     * @throws Exception Если возникла ошибка при шифровании.
     */
    public static String encrypt(String plainText, SecretKey secretKey, String strIv) throws Exception {
        if(strIv.isEmpty()){
            return encryptWithoutIV(plainText,secretKey);
        }else{
            return encryptWithIV(plainText,secretKey,strIv);
        }
    }

    /**
     * Расшифровывает данные, зашифрованные с использованием AES-шифрования. Если передан инициализирующий вектор, то используется расшифрование в режиме CBC с PKCS5Padding.
     * @param encryptedText Зашифрованный текст в формате Base64.
     * @param secretKey Секретный ключ для расшифрования.
     * @param strIv Строка с инициализирующим вектором (может быть пустой).
     * @return Расшифрованный текст.
     * @throws Exception Если возникла ошибка при расшифровании.
     */
    public static String decrypt(String encryptedText, SecretKey secretKey, String strIv) throws Exception {
        if(strIv.isEmpty()){
            return decryptWithoutIVIV(encryptedText,secretKey);
        }else{
            return decryptWithIV(encryptedText,secretKey,strIv);
        }
    }

    /**
     * Шифрует данные с использованием режима CBC с инициализирующим вектором.
     * @param plainText Исходный текст для шифрования.
     * @param secretKey Секретный ключ для шифрования.
     * @param strIv Строка с инициализирующим вектором.
     * @return Зашифрованный текст в формате Base64.
     * @throws Exception Если возникла ошибка при шифровании.
     */
    public static String encryptWithIV(String plainText, SecretKey secretKey, String strIv) throws Exception {
        byte[] clean = plainText.getBytes(StandardCharsets.UTF_8);

        IvParameterSpec iv = getIv(strIv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(clean);

        byte[] encryptedIVAndText = new byte[strIv.length() + encrypted.length];
        System.arraycopy(iv.getIV(), 0, encryptedIVAndText, 0, strIv.length());
        System.arraycopy(encrypted, 0, encryptedIVAndText, strIv.length(), encrypted.length);

        // Преобразование в Base64-строку
        return Base64.getEncoder().encodeToString(encryptedIVAndText);
    }

/**
 * Расшифровывает данные, зашифрованные с использованием режима CBC с инициализирующим вектором.
 * @param encryptedIvTextBytes Зашифрованный текст в формате Base64.
 * @param secretKey Секретный ключ для расшифрования.
 * @param strIv Строка с инициализирующим вектором.
 * @return Расшифрованный текст.

 * @throws Exception Если возникла ошибка при расшифровании.
 */
public static String decryptWithIV(String encryptedIvTextBytes, SecretKey secretKey,String strIv) throws Exception {
    // Декодирование Base64-строки в байтовый массив
    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedIvTextBytes);

    // Извлечение IV.
    byte[] iv = new byte[strIv.length()];
    System.arraycopy(encryptedBytes, 0, iv, 0, iv.length);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

    // Извлечение зашифрованной части.
    int encryptedSize = encryptedBytes.length - strIv.length();
    byte[] encryptedBytesPart = new byte[encryptedSize];
    System.arraycopy(encryptedBytes, strIv.length(), encryptedBytesPart, 0, encryptedSize);

    // Расшифровка.
    Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
    byte[] decrypted = cipherDecrypt.doFinal(encryptedBytesPart);

    return new String(decrypted, StandardCharsets.UTF_8);
}

    /**
     * Шифрует данные с использованием AES-шифрования без инициализирующего вектора.
     * @param plainText Исходный текст для шифрования.
     * @param secretKey Секретный ключ для шифрования.
     * @return Зашифрованный текст в формате Base64.
     * @throws Exception Если возникла ошибка при шифровании.
     */
    private static String encryptWithoutIV(String plainText, SecretKey secretKey) throws Exception {
        // Инициализация Cipher в режиме шифрования с нашим ключом
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMessage = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

    /**
     * Расшифровывает данные, зашифрованные с использованием AES-шифрования без инициализирующего вектора.
     * @param encryptedText Зашифрованный текст в формате Base64.
     * @param secretKey Секретный ключ для расшифрования.
     * @return Расшифрованный текст.
     * @throws Exception Если возникла ошибка при расшифровании.
     */
    private static String decryptWithoutIVIV(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}
