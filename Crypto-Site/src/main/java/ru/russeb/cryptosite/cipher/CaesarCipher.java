package ru.russeb.cryptosite.cipher;

import java.util.Arrays;

//Реализация шифрования Цезаря
public class CaesarCipher {
    // Массивы, содержащие русский алфавит в верхнем и нижнем регистре
    static char[] alphabetUpper = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
    static char[] alphabetLower = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};

    /**
     * Шифрует текст с помощью алгоритма Цезаря.
     *
     * @param plainText Исходный текст для шифрования.
     * @param shift     Сдвиг для шифрования (число от 1 до 32).
     * @return Зашифрованный текст.
     */
    public static String encryptText(String plainText, int shift) {
        StringBuilder cipherText = new StringBuilder();
        for(char c : plainText.toCharArray()) {
            if(isRussianLetter(c)) {
                if(Character.isUpperCase(c)) {
                    int index = Arrays.binarySearch(alphabetUpper, c);
                    cipherText.append(alphabetUpper[(index + shift + alphabetUpper.length) % alphabetUpper.length]);
                }else{
                    int index = Arrays.binarySearch(alphabetLower, c);
                    cipherText.append(alphabetLower[(index + shift + alphabetLower.length) % alphabetLower.length]);
                }
            }else {
                cipherText.append(c);
            }
        }
        return cipherText.toString();
    }

    /**
     * Расшифровывает текст, зашифрованный с помощью алгоритма Цезаря.
     *
     * @param encryptedText Зашифрованный текст.
     * @param shift         Сдвиг для расшифрования (число от 1 до 32).
     * @return Расшифрованный текст.
     */
    public static String decryptText(String encryptedText, int shift) {
        return encryptText(encryptedText, -shift);
    }

    /**
     * Проверяет, является ли заданный символ русской буквой.
     *
     * @param c Символ для проверки.
     * @return true, если символ является русской буквой, false в противном случае.
     */
    private static boolean isRussianLetter(char c){
        return Arrays.binarySearch(alphabetUpper, c) >= 0||Arrays.binarySearch(alphabetLower, c) >= 0;
    }
}
