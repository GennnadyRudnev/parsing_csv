package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        try {
//           if (args == null || args.length <= 0) {
//               System.out.println("Вы не указали путь к файлу");
//               return;
//           }
//           Path path = Paths.get(args[0]);
//           if (!Files.exists(path)) {
//               System.out.println("Файл не найден или не существует");
//               return;
//           }

            List<String> content = CSVUtils.readAll("/Users/demonicmc/IdeaProjects/parsing_csv/test.csv");
            Map<Character, List<String>> values = CSVUtils.cellReplacementByValue(content);
//            Map<Character, List<String>> result = new HashMap<>();
            System.out.println(values);

//            MathParser parser = new MathParser();
//            double res = 0;
//            int count = values.size();
//            int row = 0;
//
//            for (Map.Entry<Character, List<String>> m : values.entrySet()) {
//                List<String> l = m.getValue();
//                row = l.size();
//                for (int i = 0; i < l.size(); i++) {
//                    if (CSVUtils.isDigits(l.get(i))) {
//                        continue;
//                    } else if (CSVUtils.isFormula(l.get(i))) {
//                        try {
//                            res = parser.Parse(l.get(i));
//                            l.get(i).replace(l.get(i), String.valueOf(res));
//                        } catch (Exception e) {
//                            l.get(i).replace(l.get(i), "Арифметическая ошибка");
//                        }
//                    } else {
//                        l.get(i).replace(l.get(i), "Не является числом");
//                    }
//                }
////                result.put(m.getKey(), l);
//            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
