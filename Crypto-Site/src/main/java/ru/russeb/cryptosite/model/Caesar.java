package ru.russeb.cryptosite.model;


import lombok.Getter;
import lombok.Setter;

//Объект для шифра Цезаря
public class Caesar extends Cipher {

    @Getter
    @Setter
    private int shift;
}
