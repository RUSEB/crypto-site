package ru.russeb.cryptosite.model;

import lombok.Data;


//Модель ключа для шифрования RSA(состоит из открытого и закрытого ключей)
@Data
public class RsaKeys {
    private String privateKey;
    private String publicKey;
    private int keysSize;

    public RsaKeys(String publicKey,String privateKey, int keysSize) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.keysSize = keysSize;
    }
}
