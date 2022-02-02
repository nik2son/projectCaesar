package com.studying;

import java.io.IOException;
import java.util.Scanner;

import static com.studying.BrutForcing.brutForce;
import static com.studying.Decryption.decrypt;
import static com.studying.Encryption.encrypt;
import static com.studying.Statistics.statistics;

public class Main {

    public static final Scanner CONSOLE = new Scanner(System.in);
    public static final String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.,-:()«»%1234567890 "; //алфавит
    public static char[] charAlphabet = ALPHABET.toCharArray();

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
            String exit = CONSOLE.nextLine();
            switch (exit) {
                case "1":
                    encrypt(charAlphabet);
                    break;
                case "2":
                    decrypt(charAlphabet);
                    break;
                case "3":
                    brutForce(charAlphabet);
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
