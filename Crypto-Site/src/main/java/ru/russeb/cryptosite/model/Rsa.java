package ru.russeb.cryptosite.model;

import lombok.Data;

//Объект для шифра RSA
@Data
public class Rsa extends Cipher{
    private String publicKey;
    private String privateKey;
    private int keysSize = 512;
}
