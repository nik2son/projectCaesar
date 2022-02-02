package com.studying;

/*
Часть 1. Зашифровать текст
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.studying.Main.CONSOLE;

public class Encryption {

    public static void encrypt(char[] charAlphabet) throws IOException {
        System.out.println("Введите адрес файла, который требуется зашифровать");
        String sourceFile = CONSOLE.nextLine();
        String textForEncryption = Files.readString(Paths.get(sourceFile));
        char[] charInitialText = textForEncryption.toCharArray();
        char[] resultEncrypted = new char[textForEncryption.length()];
        System.out.println("Введите ключ шифрования");
        int key = Integer.parseInt(CONSOLE.nextLine());
        for (int i = 0; i < charInitialText.length; i++) {
            char charTempOuter = charInitialText[i];
            for (int j = 0; j < charAlphabet.length; j++) {
                char charTempInner = charAlphabet[j];
                if (charTempOuter == charTempInner) {
                        resultEncrypted[i] = charAlphabet[(j + key) % charAlphabet.length];
                }
            }
        }

        System.out.println("Укажите адрес для сохранения зашифрованного файла");
        String encryptedText = CONSOLE.nextLine();
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(encryptedText))) {
             bw.write(resultEncrypted); //записали в файл encryptedText зашифрованный текст в формате массива байт
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("Расшифрованный файл успешно сохранен по адресу:\n %s", encryptedText);
    }
}

