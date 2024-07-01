package ru.russeb.cryptosite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.russeb.cryptosite.cipher.AesCipher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Проверка работоспособности шифрования AES
@SpringBootTest
class AesCipherTest {

    private static final String PLAINTEXT = "This is a secret message";
    private static final String ENCRYPTION_KEY = "0123456789abcdef";
    private static final String INITIALIZATION_VECTOR = "abcdef0123456789";

    @Test
    void testEncryptDecrypt() throws Exception {
        // Создаем секретный ключ
        byte[] keyBytes = ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // Шифруем текст
        String encryptedText = AesCipher.encrypt(PLAINTEXT, secretKey, INITIALIZATION_VECTOR);
        System.out.println("Encrypted text: " + encryptedText);

        // Расшифровываем текст
        String decryptedText = AesCipher.decrypt(encryptedText, secretKey, INITIALIZATION_VECTOR);
        System.out.println("Decrypted text: " + decryptedText);

        // Проверяем, что расшифрованный текст совпадает с исходным
        assertEquals(PLAINTEXT, decryptedText);
    }

    @Test
    void testEncryptDecryptWithoutIV() throws Exception {
        // Создаем секретный ключ
        byte[] keyBytes = ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // Шифруем текст без инициализирующего вектора
        String encryptedText = AesCipher.encrypt(PLAINTEXT, secretKey, "");
        System.out.println("Encrypted text: " + encryptedText);

        // Расшифровываем текст без инициализирующего вектора
        String decryptedText = AesCipher.decrypt(encryptedText, secretKey, "");
        System.out.println("Decrypted text: " + decryptedText);

        // Проверяем, что расшифрованный текст совпадает с исходным
        assertEquals(PLAINTEXT, decryptedText);
    }
}
