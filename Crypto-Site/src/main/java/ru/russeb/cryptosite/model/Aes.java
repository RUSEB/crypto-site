package ru.russeb.cryptosite.model;


import lombok.Data;


//Объект для шифра AES
@Data
public class Aes extends Cipher{

    private int keySize = 128; // Инициализируем keySize со значением 128

    private String key;

    private String iv;
}
