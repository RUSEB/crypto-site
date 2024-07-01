package ru.russeb.cryptosite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.russeb.cryptosite.cipher.Rc4Cipher;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Проверка работоспособности шифрования RC4
@SpringBootTest
public class Rc4CipherTest {
    @Test
    void testRc4Cipher() {
        byte[] plainText = "Text to test!".getBytes();
        byte[] key = "secret key".getBytes();
        byte[] cipherText = Rc4Cipher.encrypt(plainText, key);
        String cipherTextHex = Rc4Cipher.byteArrayToHexString(cipherText);

        byte[] decryptedText = Rc4Cipher.decrypt(Rc4Cipher.hexStringToByteArray(cipherTextHex), key);
        assertEquals(new String(plainText),new String(decryptedText));
    }
}
