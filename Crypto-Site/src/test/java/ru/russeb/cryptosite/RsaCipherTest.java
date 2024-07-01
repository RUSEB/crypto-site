package ru.russeb.cryptosite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.russeb.cryptosite.cipher.RsaCipher;
import ru.russeb.cryptosite.cipher.RsaKeyGenerator;
import ru.russeb.cryptosite.model.RsaKeys;

import static org.junit.jupiter.api.Assertions.assertEquals;


//Проверка работоспособности шифрования RSA
@SpringBootTest
public class RsaCipherTest {
    @Test
    void testRsaCipher() throws Exception {
        String plainText = "Text to Test!";
        RsaKeys rsaKeys = RsaKeyGenerator.get(512);
        String encrypt = RsaCipher.encrypt(plainText,rsaKeys.getPublicKey());
        String decrypt = RsaCipher.decrypt(encrypt,rsaKeys.getPrivateKey());
        assertEquals(plainText,decrypt);
    }
}
