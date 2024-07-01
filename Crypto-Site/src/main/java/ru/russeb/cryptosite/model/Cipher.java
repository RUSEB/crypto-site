package ru.russeb.cryptosite.model;


import lombok.Data;


//Родительский объект шифров
@Data
public class Cipher {

    private String textToCipher;

    private String operation;

    public boolean isEncrypt(){
        return operation.equals("Зашифровать");
    }
    public boolean isDecrypt(){
        return operation.equals("Расшифровать");
    }
}
