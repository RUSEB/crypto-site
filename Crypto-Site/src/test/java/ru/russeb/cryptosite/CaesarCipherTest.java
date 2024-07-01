package ru.russeb.cryptosite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.russeb.cryptosite.cipher.CaesarCipher;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Проверка работоспособности шифрования Цезаря
@SpringBootTest
public class CaesarCipherTest {


    @Test
    void encryptAndDecrypt() {
        String plainText = "Привет, мир!";
        int shift = 3;
        String encryptedText = CaesarCipher.encryptText(plainText, shift);
        String decryptedText = CaesarCipher.decryptText(encryptedText, shift);
        assertEquals(plainText, decryptedText);
    }
}
