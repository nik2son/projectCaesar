package com.studying;

/*
Часть 4. Расшифровать текст методом статистического анализа
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.studying.Main.ALPHABET;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Statistics {

    public static void statistics() throws IOException {

        //System.out.println("Введите файл для статистики"); //образец, большой файл
        //String fileAddressStatisticAnalysis = CONSOLE.nextLine();
        //прописать доступ к файлу

        //System.out.println("Введите файл для анализа шифрования"); //зашифрованный файл
        //String fileAddressEncryptedText = CONSOLE.nextLine();
        //прописать доступ к файлу

        String srcStat = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/statisticAnalysisText.txt";
        String srcText = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/encryptedText.txt";

        String statisticAnalysis = Files.readString(Paths.get(srcStat), UTF_8);
        String encryptedText = Files.readString(Paths.get(srcText), UTF_8);

        HashMap<Character, Integer> exampleStatistics = createCharacterStatistics(statisticAnalysis); //мапа для хранения выборки по образцовому тексту
        HashMap<Character, Integer> encryptedTextStatistics = createCharacterStatistics(encryptedText); //мапа для хранения выборки по зашифрованному тексту
        HashMap<Character, Character> characterStatisticsResult = createCharacterStatistics(encryptedTextStatistics, exampleStatistics); //мапа для хранения сопоставления символа в зашифрованном тексте и образце текста
        StringBuilder resultDecrypted = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            char charDecrypted = characterStatisticsResult.get(encryptedText.charAt(i));
            resultDecrypted.append(charDecrypted);
        }
        System.out.println(resultDecrypted.toString());
        //далее результат расшифровки методом стат анализа записать в файл и предложить пользователю проверить качество
    }

    public static HashMap<Character, Character> createCharacterStatistics(HashMap<Character, Integer> encryptedTextStatistics ,
                                                                          HashMap<Character, Integer> exampleStatistics) {
        //сопоставляем самые близкие значения между собой в двух мапах
        HashMap<Character, Character> result = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            char c = ALPHABET.charAt(i); //берем например символ "а" из алфавита
            Integer characterStat = exampleStatistics.get(c); //в экземпляровой статистике "а" встречается 55процентов раз(значение)
            Character closestCharacterFromStatMap = getClosestCharacterFromStatMap(encryptedTextStatistics, characterStat);
            //значит для зашифрованного текста нужно применить значение в 55процентов в методе по определению ближайшего
            result.put(closestCharacterFromStatMap, c);
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

    //метод вычисляет какой символ получается по ближайшему совпадению процентов содержания символов (55-54, 1-1, 27-27  и тп)
    public static Character getClosestCharacterFromStatMap(HashMap<Character, Integer> statMap, Integer value) {
        Integer minDelta = Integer.MAX_VALUE;
        Character charValue = ' ';
        for (Map.Entry<Character, Integer> characterIntegerEntry : statMap.entrySet()) {
            int delta = Math.abs(characterIntegerEntry.getValue() - value); //какая разница по модулю ближе всего друг к другу (55-54, 1-1, 27-27  и тп)
            if (delta < minDelta) {
                minDelta = delta;
                charValue = characterIntegerEntry.getKey();
            }
        }
        return charValue;
    }

}
