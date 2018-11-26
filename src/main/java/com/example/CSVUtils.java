package com.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс предназначен для работы с файлами формата csv
 * Состоит из статических методов
 */
public final class CSVUtils {
    private CSVUtils() {
    }

    private static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

    private static final Pattern REGEX = Pattern.compile("[A-Z]\\d");

    private static final String OPERATIONS = "[+-/*]";

    private static Pattern pattern = Pattern.compile("\\d*[A-Z]{0,1}\\d+[+\\-\\/*]{1}\\d*[A-Z]{0,1}\\d+");

    /**
     * Читает все содержимое файла в список.
     * @param pathFile - путь к файлу
     * @return - возвращает список строк
     * @throws IOException
     */
    public static List<String> readAll(String pathFile) throws IOException {
        return Files.readAllLines(Paths.get(pathFile), StandardCharsets.UTF_8);
    }


    public static boolean isDigits(String str) {
        return str.matches("\\d*");
    }

    public static boolean isFormula(String str) {

        return pattern.matcher(str).find();
    }

    /**
     * Заменяет название ячейки на число, выражение
     * @param list - список строк для изменения
     * @return - возвращает Map с заменнеными значениями
     */
    public static Map<Character, List<String>> cellReplacementByValue(List<String> list) {
        Map<Character, List<String>> values = new LinkedHashMap<>();
        boolean repeat = true;

        while (repeat) {
            repeat = false;
            for (int i = 0; i < list.size(); i++) {
                String[] strArr = list.get(i).split(";");
                for (int j = 0; j < strArr.length; j++) {

                    if (values.get(alphabet[j]) == null) {
                        values.put(alphabet[j], new ArrayList<>());
                    }
                    String[] strLocal = strArr[j].split(OPERATIONS);
                    for (String s1 : strLocal) {
                        Matcher matcher = REGEX.matcher(strArr[j]);
                        if (matcher.find()) {
                            MatchResult result = matcher.toMatchResult();
                            char c = result.group().charAt(0);
                            int val = Integer.valueOf(result.group().substring(1));
                            try {
                                if (values.get(c) != null && values.get(c).get(val - 1) != null) {
                                    strArr[j] = strArr[j].replace(s1, values.get(c).get(val - 1));
                                }
                            } catch (Exception e) {
                                repeat = true;
                            }
                        }
                    }
                    if (values.get(alphabet[j]).size() > i && values.get(alphabet[j]).get(i) != null) {
                        values.get(alphabet[j]).set(i, strArr[j]);
                    } else {
                        values.get(alphabet[j]).add(strArr[j]);
                    }

                }
            }
        }

        return values;
    }



}
