package com.studying;

/*
Часть 2. Расшифровать текст
 */

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.studying.Main.*;

public class Decryption {

    public static void decrypt(char[] charEncrypted, char[] charAlphabet, char[] resultDecrypted, int key) { //продумать введение кода дешифрования пользователем
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
            bwSrc.write(resultDecrypted); //записали в файл encryptedText зашифрованный текст в формате массива байт
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
