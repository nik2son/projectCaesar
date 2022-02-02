package com.studying;

/*
Часть 3. Расшифровать текст методом перебора ключей брут-форс
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.studying.Main.*;

public class BrutForcing {

    public static void brutForce(char[] charAlphabet) throws IOException {
        System.out.println("Введите адрес файла, который требуется расшифровать");
        String sourceFile = CONSOLE.nextLine();
        String textForDecryption = Files.readString(Paths.get(sourceFile));
        char[] charEncrypted = textForDecryption.toCharArray();
        char[] resultDecrypted = new char[textForDecryption.length()];
        int key; String stringKey = "Ключ - ";
        for (key = 1; key <= charAlphabet.length; key++) {
            for (int i = 0; i < charEncrypted.length; i++) {
                char charTempOuter = charEncrypted[i];
                for (int j = 0; j < charAlphabet.length; j++) {
                    char charTempInner = charAlphabet[j];
                    if (charTempOuter == charTempInner) {
                        resultDecrypted[i] = charAlphabet[(j + (charAlphabet.length - key)) % charAlphabet.length];
                    }
                }
            }
            int resultComaSpace = isExistComaSpace(resultDecrypted);
            int resultPointSpace = isExistPointSpace(resultDecrypted);
            int resultInterestSpace = isExistInterestSpace(resultDecrypted);
            int resultLongWord = isCountLongWord(resultDecrypted);
            if ((resultComaSpace > 5) && (resultPointSpace > 5) && (resultInterestSpace > 1) && (resultLongWord < 5)) {
                System.out.println(stringKey + key);
                break;
            }
        }

        System.out.println("Укажите адрес для сохранения расшифрованного файла");
        String decryptedTextByBrutForce = CONSOLE.nextLine();
        try(BufferedWriter bwSrc = Files.newBufferedWriter(Paths.get(decryptedTextByBrutForce))) {
            bwSrc.write(resultDecrypted); //записали в файл decryptedTextByBrutForce зашифрованный текст методом брутфорс в формате массива байт
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.printf("Расшифрованный файл успешно сохранен по адресу:\n %s", decryptedTextByBrutForce);
    }

    public static int isExistComaSpace(char[] textForAnalysis) {
        int counterComaSpace = 0;
        for (int i = 0; i < textForAnalysis.length; i++) {
            if (textForAnalysis[i] == ',' && textForAnalysis[i + 1] == ' ') {
                counterComaSpace++;
            }
        } return counterComaSpace;
    }

    public static int isExistPointSpace(char[] textForAnalysis) {
        int counterPointSpace = 0;
        for (int i = 0; i < textForAnalysis.length; i++) {
            if (textForAnalysis[i] == '.' && textForAnalysis[i + 1] == ' ') {
                counterPointSpace++;
            }
        } return counterPointSpace;
    }

    public static int isExistInterestSpace(char[] textForAnalysis) {
        int counterInterest = 0;
        for (int i = 0; i < textForAnalysis.length; i++) {
            if (textForAnalysis[i] == '%' && textForAnalysis[i + 1] == ' ') {
                counterInterest++;
            }
        } return counterInterest;
    }

    public static int isCountLongWord(char[] textForAnalysis) {
        String lines = new String(textForAnalysis);
        String[] linesWithSpace = lines.split(" ");
        List<Integer> list = new ArrayList<>();

        for (String value : linesWithSpace) {
            list.add(value.length());
        }

        int counterLongWord = 0;
        for (Integer integer : list) {
            if (integer >= 20) {
                counterLongWord++;
            }
        }
        return counterLongWord;
    }

}
