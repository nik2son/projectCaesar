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
import static java.nio.charset.StandardCharsets.UTF_8;

public class BrutForcing {

    public static void brutForce(char[] charEncrypted, char[] charAlphabet, char[] resultDecrypted) throws IOException {
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
            int resultLongWord = isCountLongWord();
            if ((resultComaSpace > 5) && (resultPointSpace > 5) && (resultInterestSpace > 1) && (resultLongWord < 5)) {
                System.out.println(stringKey + key);
                //записать результат расшифровки в файл
                System.out.println("Расшифрованный файл записан");
                break;
            }
        }

        try(BufferedWriter bwSrc = Files.newBufferedWriter(Paths.get(decryptedTextByBrutForce))) {
            bwSrc.write(resultDecrypted); //записали в файл encryptedText зашифрованный текст в формате массива байт
        } catch(Exception e) {
            e.printStackTrace();
        }
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

    public static int isCountLongWord() throws IOException {
        //переписать код, чтобы программа обращалась к переменной resultDecrypted вместо decryptedText.txt
        //программа должна считывать текст из файла, текст должен быть расшифрован
        String lines = Files.readString(Paths.get(decryptedText), UTF_8);
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
