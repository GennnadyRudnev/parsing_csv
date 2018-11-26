package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс является точкой входа в программу
 */
public class App {
    public static void main(String[] args) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.csv"))) {
           if (args == null || args.length <= 0) {
               System.out.println("Вы не указали путь к файлу");
               return;
           }
           Path path = Paths.get(args[0]);
           if (!Files.exists(path)) {
               System.out.println("Файл не найден или не существует");
               return;
           }

            List<String> content = CSVUtils.readAll(args[0]);
            Map<Character, List<String>> values = CSVUtils.cellReplacementByValue(content);
            Map<Integer, List<String>> result = new HashMap<>();

            substitutionOfFormulaForValue(values);

            for (int line = 0; line < content.size(); line++) {
                char ch = 'A';
                for (int column = 0; column < values.size(); column++) {
                    if (result.get(line) == null) {
                        result.put(line, new ArrayList<>());
                    }

                    result.get(line).add(values.get(ch++).get(line));
                }
            }


            for (List<String> l : result.values()) {
                StringBuilder builder = new StringBuilder();
               for (int i = 0; i < l.size(); i++) {
                   if (i == l.size() - 1) {
                       builder.append(l.get(i));
                       continue;
                   }
                   builder.append(l.get(i) + ";");
               }
               writer.write(builder.toString() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении/записи файла.");
            System.out.println(e.getCause());
        }
    }

    /**
     * Метод считает и заменяет мат. выражения числовым результатом
     * @param values - значения для расчета
     */
    static void substitutionOfFormulaForValue(Map<Character, List<String>> values) {
        MathParser parser = new MathParser();
        double res = 0;

        for (Map.Entry<Character, List<String>> m : values.entrySet()) {
            List<String> l = m.getValue();

            for (int i = 0; i < l.size(); i++) {
                if (CSVUtils.isDigits(l.get(i))) {
                    continue;
                } else if (CSVUtils.isFormula(l.get(i))) {
                    try {
                        res = parser.Parse(l.get(i));
                        l.set(i, l.get(i).replace(l.get(i), String.valueOf(res)));
                    } catch (Exception e) {
                        l.set(i, l.get(i).replace(l.get(i), "Арифметическая ошибка"));
                    }
                } else {
                    l.set(i, l.get(i).replace(l.get(i), "Не является числом"));

                }
            }
        }
    }
}
