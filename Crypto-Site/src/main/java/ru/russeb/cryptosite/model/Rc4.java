package ru.russeb.cryptosite.model;


import lombok.Data;

//Объект для шифра RC4
@Data
public class Rc4 extends Cipher{
    private String key;
}
