package com.studying;

/*
Часть 1. Зашифровать текст
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.studying.Main.CONSOLE;
import static com.studying.Main.encryptedText;

public class Encryption {
                               //char[] charInitialText, char[] charAlphabet, char[] resultEncrypted
    public static void encrypt(char[] charAlphabet) throws IOException { //продумать введение кода шифрования пользователем
        System.out.println("Введите адрес файла, который требуется зашифровать");
        String sourceFile = CONSOLE.nextLine();
        String textForEncryption = Files.readString(Paths.get(sourceFile)); //initialText = Files.readString(Paths.get(src));
        char[] charInitialText = textForEncryption.toCharArray();                  //static char[] charInitialText = initialText.toCharArray();
        char[] resultEncrypted = new char[textForEncryption.length()];
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

        try(BufferedWriter bwSrc = Files.newBufferedWriter(Paths.get(encryptedText))) {
            bwSrc.write(resultEncrypted); //записали в файл encryptedText зашифрованный текст в формате массива байт
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

