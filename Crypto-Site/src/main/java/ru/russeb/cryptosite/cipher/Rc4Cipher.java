package ru.russeb.cryptosite.cipher;


//Реализация шифрования RC4
public class Rc4Cipher {
    /**
     * Шифрует данные с помощью алгоритма RC4.
     *
     * @param data Массив байтов, содержащий данные для шифрования.
     * @param key  Массив байтов, содержащий ключ для шифрования.
     * @return Массив байтов, содержащий зашифрованные данные.
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        int[] s = initState(key);
        byte[] output = new byte[data.length];

        int i = 0, j = 0;
        for (int n = 0; n < data.length; n++) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            swap(s, i, j);
            int rnd = s[(s[i] + s[j]) % 256];
            output[n] = (byte) (data[n] ^ rnd);
        }

        return output;
    }

    /**
     * Расшифровывает данные, зашифрованные с помощью алгоритма RC4.
     *
     * @param data Массив байтов, содержащий зашифрованные данные.
     * @param key  Массив байтов, содержащий ключ для расшифрования.
     * @return Массив байтов, содержащий расшифрованные данные.
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        return encrypt(data, key);
    }

    /**
     * Инициализирует состояние алгоритма RC4 на основе заданного ключа.
     *
     * @param key Массив байтов, содержащий ключ для инициализации.
     * @return Массив целых чисел, представляющий состояние алгоритма RC4.
     */
    private static int[] initState(byte[] key) {
        int[] s = new int[256];
        for (int i = 0; i < 256; i++) {
            s[i] = i;
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + s[i] + (key[i % key.length] & 0xFF)) % 256;
            swap(s, i, j);
        }

        return s;
    }

    /**
     * Меняет местами элементы с индексами i и j в массиве s.
     *
     * @param s Массив целых чисел.
     * @param i Индекс первого элемента.
     * @param j Индекс второго элемента.
     */
    private static void swap(int[] s, int i, int j) {
        int temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }

    /**
     * Преобразует массив байтов в строку, представляющую шестнадцатеричное представление байтов.
     *
     * @param bytes Массив байтов для преобразования.
     * @return Строка, представляющая шестнадцатеричное представление байтов.
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Преобразует строку, представляющую шестнадцатеричное представление байтов, в массив байтов.
     *
     * @param hex Строка, представляющая шестнадцатеричное представление байтов.
     * @return Массив байтов, соответствующий входной строке.
     */
    public static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
}
