package com.studying;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static com.studying.BrutForcing.brutForce;
import static com.studying.Decryption.decrypt;
import static com.studying.Encryption.encrypt;
import static com.studying.Statistics.statistics;

public class Main {

    public static final Scanner CONSOLE = new Scanner(System.in);
    public static final String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.,-:()«»%1234567890 "; //алфавит
    public static char[] charAlphabet = ALPHABET.toCharArray();

    static String src = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/initialText.txt"; //текст для шифрования
    static String encryptedText = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/encryptedText.txt"; //создаем файл для хранения зашифрованного текста
    static String decryptedText = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/decryptedText.txt"; //создаем файл для хранения расшифрованного текста

    static String initialText;

    static {
        try {
            initialText = Files.readString(Paths.get(src));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static char[] charInitialText = initialText.toCharArray();

    static String encrypted;

    static {
        try {
            encrypted = Files.readString(Paths.get(encryptedText));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static char[] charEncrypted = encrypted.toCharArray();

    static char[] resultEncrypted = new char[initialText.length()]; //массив для записи зашифрованного текста в формате char
    static char[] resultDecrypted = new char[encrypted.length()]; //массив для записи расшифрованного текста в формате char

    public static void main(String[] args) throws IOException {

        run();

    }

    private static void run() throws IOException {
        System.out.println("Выберите действие, которое необходимо выполнить, и введите его номер:\n"
                + "1. Зашифровать текст из файла, применяя ключ.\n"
                + "2. Расшифровать текст из файла, применяя ключ.\n"
                + "3. Расшифровать текст из файла, применяя метод подбора ключа.\n"
                + "4. Расшифровать текст из файла, применяя метод статистического анализа.\n");

        System.out.println("Чтобы выйти из программы, введите exit.");

        while (true) {
            //добавить проверку введения пользователем иных символов
            String exit = CONSOLE.nextLine();
            switch (exit) {
                case "1":
                    encrypt(charInitialText, charAlphabet, resultEncrypted);
                    System.out.println(new String(resultEncrypted)); //проверка шифрования
                    break;
                case "2":
                    decrypt(charEncrypted, charAlphabet, resultDecrypted, 10);
                    System.out.println(new String(resultDecrypted)); //проверка дешифрования
                    break;
                case "3":
                    brutForce(charEncrypted, charAlphabet, resultDecrypted);
                    System.out.println(new String(resultDecrypted)); //проверка брутфорса
                    break;
                case "4":
                    statistics();
                    break;
                case "exit":
                    System.out.println("Программа успешно завершена");
                    return;
            }
        }
    }
}
