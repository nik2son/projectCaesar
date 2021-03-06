package com.studying;

/*
Часть 4. Расшифровать текст методом статистического анализа
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.studying.Main.ALPHABET;
import static com.studying.Main.CONSOLE;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Statistics {

    public static void statistics() throws IOException {

        System.out.println("Введите адрес файла для статистики"); //образец, большой файл для сбора статистических данных
        System.out.println("Введите адрес файла для анализа шифрования"); //зашифрованный файл для попытки расшифровки

        String srcStat = CONSOLE.nextLine();
        String srcText = CONSOLE.nextLine();
        String statisticAnalysis = Files.readString(Paths.get(srcStat), UTF_8);
        String encryptedText = Files.readString(Paths.get(srcText), UTF_8);

        HashMap<Character, Integer> exampleStatistics = createCharacterStatistics(statisticAnalysis); //мапа для хранения выборки по образцовому тексту
        HashMap<Character, Integer> encryptedTextStatistics = createCharacterStatistics(encryptedText); //мапа для хранения выборки по зашифрованному тексту
        HashMap<Character, Character> characterStatisticsResult = createCharacterStatistics(encryptedTextStatistics, exampleStatistics); //мапа для хранения сопоставления символа в зашифрованном тексте и образце текста
        createResultDecrypted(encryptedText, characterStatisticsResult);

    }

    private static void createResultDecrypted(String encryptedText, HashMap<Character, Character> characterStatisticsResult) {
        StringBuilder resultDecrypted = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            if (characterStatisticsResult.get(encryptedText.charAt(i)) == null) {
                continue;
            } else {
                char charDecrypted = characterStatisticsResult.get(encryptedText.charAt(i));
                resultDecrypted.append(charDecrypted);
            }
        }
        System.out.println("Укажите адрес для сохранения расшифрованного файла");
        String srcResult = CONSOLE.nextLine();
        writeResultDecryptedToFile(Paths.get(srcResult), resultDecrypted);
        System.out.printf("Расшифрованный файл успешно сохранен по адресу:\n %s", srcResult);
    }

    public static void writeResultDecryptedToFile(Path path, StringBuilder sb) {
        try {
            Files.writeString(path, sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Character, Character> createCharacterStatistics(HashMap<Character, Integer> encryptedTextStatistics ,
                                                                          HashMap<Character, Integer> exampleStatistics) {
        //сопоставляем самые близкие значения между собой в двух мапах
        HashMap<Character, Character> result = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            char c = ALPHABET.charAt(i); //берем например символ "а" из алфавита
            if (exampleStatistics.get(c) == null) {
                continue;
            } else {
                Integer characterStat = exampleStatistics.get(c); //в экземпляровой статистике "а" встречается 55процентов раз(значение)
                Character closestCharacterFromStatMap = getClosestCharacterFromStatMap(encryptedTextStatistics, characterStat);
                //значит для зашифрованного текста нужно применить значение в 55процентов в методе по определению ближайшего
                result.put(closestCharacterFromStatMap, c);
            }
        }
        return result;
    }

    public static HashMap<Character, Integer> createCharacterStatistics(String content) {
        HashMap<Character, Integer> resultAbsolute = new HashMap<>(); // мапа содержит абсолютное вхождение символов
        HashMap<Character, Integer> result = new HashMap<>();

        for (int i = 0; i <content.length(); i++) {
            char charValue = content.charAt(i); //достаем из текста по символу в цикле
            Integer integerValue = resultAbsolute.get(charValue); //проверяем сколько уже таких символов есть в мапе
            if (integerValue == null) { //проверяем встречался ли уже в этот символ
                resultAbsolute.put(charValue, 1); //если нет добавляем в первый раз
            } else {
                integerValue++; //
                resultAbsolute.put(charValue, integerValue);
            }
        }

        for (Character character : resultAbsolute.keySet()) {
            Integer integer = resultAbsolute.get(character);
            int i = integer * 10_000/content.length();
            result.put(character, i);
        }
        return result;
    }

    public static Character getClosestCharacterFromStatMap(HashMap<Character, Integer> statMap, Integer value) {
        int minDelta = Integer.MAX_VALUE;
        Character charValue = ' ';
        for (Map.Entry<Character, Integer> characterIntegerEntry : statMap.entrySet()) {
            int delta = Math.abs(characterIntegerEntry.getValue() - value);
            if (delta < minDelta) {
                minDelta = delta;
                charValue = characterIntegerEntry.getKey();
            }
        }
        return charValue;
    }

}
