package com.studying;

/*
Часть 2. Расшифровать текст
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.studying.Main.*;

public class Decryption {

    public static void decrypt(char[] charAlphabet) throws IOException {
        System.out.println("Введите адрес файла, который требуется расшифровать");
        String sourceFile = CONSOLE.nextLine();
        String textForDecryption = Files.readString(Paths.get(sourceFile));
        char[] charEncrypted = textForDecryption.toCharArray();
        char[] resultDecrypted = new char[textForDecryption.length()];
        System.out.println("Введите ключ шифрования");
        int key = CONSOLE.nextInt();
        for (int i = 0; i < charEncrypted.length; i++) {
            char charTempOuter = charEncrypted[i];
            for (int j = 0; j < charAlphabet.length; j++) {
                char charTempInner = charAlphabet[j];
                if (charTempOuter == charTempInner) {
                    resultDecrypted[i] = charAlphabet[(j + (charAlphabet.length - key)) % charAlphabet.length];
                }
            }
        }

        try (BufferedWriter bwSrc = Files.newBufferedWriter(Paths.get(decryptedText))) {
            bwSrc.write(resultDecrypted); //записали в файл decryptedText расшифрованный текст в формате массива байт
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
