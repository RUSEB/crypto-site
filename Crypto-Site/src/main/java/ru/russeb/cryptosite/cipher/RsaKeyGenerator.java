package ru.russeb.cryptosite.cipher;

import ru.russeb.cryptosite.model.RsaKeys;

import java.security.*;
import java.util.Base64;


//Генератор ключей для шифра RSA при помощи втроенной библиотеки Java Cryptography Architecture
public class RsaKeyGenerator {
    public static RsaKeys get(int keysSize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keysSize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Получение открытого и закрытого ключей
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            return new RsaKeys(publicKeyString,privateKeyString,keysSize);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
