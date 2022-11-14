import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        String fileOutput = "data.json";
        List<Employee> employees = parseCSV(columnMapping, fileName);

        String json = listToJson(employees);
        writeString(json, fileOutput);

    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            List<Employee> employees = csv.parse();
            return employees;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String listToJson(List<Employee> list) {
        String json = "";
        for (Object obj : list) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            //System.out.println(gson.toJson(employee));
            json = gson.toJson(list);
        }
        return json;
    }

//    private static String listToJson(List<T> list) {
//        Type listType = new TypeToken<List<T>>() {}.getType();
//        String json = "";
//
//        for (T obj : list) {
//            GsonBuilder builder = new GsonBuilder();
//            Gson gson = builder.create();
//            //System.out.println(gson.toJson(employee));
//            json = gson.toJson(list, listType);
//        }
//        return json;
//    }

    private static void writeString(String str, String filename) {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(str);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
