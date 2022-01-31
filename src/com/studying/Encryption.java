package com.studying;

/*
Часть 1. Зашифровать текст
 */

import java.io.IOException;

import static com.studying.Main.CONSOLE;

public class Encryption {

    public static void encrypt(char[] charInitialText, char[] charAlphabet, char[] resultEncrypted) throws IOException { //продумать введение кода шифрования пользователем
        //System.out.println("Выберите файл, который требуется зашифровать");
        //String fileAddress = CONSOLE.nextLine();
        //String fileForEncryption = Files.readString(Paths.get(fileAddress));
        //charInitialText = fileAddress.toCharArray();
        System.out.println("Введите ключ шифрования");
        int key = CONSOLE.nextInt();
        for (int i = 0; i < charInitialText.length; i++) { //работа с одним конкретным файлом, не универсалный доступ к библиотеке для шифрования
            char charTempOuter = charInitialText[i];
            for (int j = 0; j < charAlphabet.length; j++) {
                char charTempInner = charAlphabet[j];
                if (charTempOuter == charTempInner) {
                    resultEncrypted[i] = charAlphabet[(j + key) % charAlphabet.length];
                }
            }
        }
    }
}
