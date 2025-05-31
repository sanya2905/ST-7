package com.mycompany.app;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Task3 {
    public static void main(String[] args) {
        try {
            String urlStr = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Reader reader = new InputStreamReader(connection.getInputStream());
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(reader);
            JSONObject hourly = (JSONObject) json.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");
            File resultDir = new File("result");
            if (!resultDir.exists()) {
                resultDir.mkdir();
            }
            File file = new File("result/forecast.txt");
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            String header = String.format("%-5s %-20s %-15s %-15s%n", "№", "Дата/время", "Температура (°C)", "Осадки (мм)");
            String separator = "----------------------------------------------------------------------------";
            System.out.print(header);
            System.out.println(separator);
            writer.print(header);
            writer.println(separator);
            int count = times.size();
            for (int i = 0; i < count; i++) {
                String row = String.format("%-5d %-20s %-15s %-15s%n",
                        i + 1,
                        times.get(i),
                        temperatures.get(i),
                        rains.get(i));
                System.out.print(row);
                writer.print(row);
            }
            writer.close();
            System.out.println("\nТаблица сохранена в файл: result/forecast.txt");
        } catch (Exception e) {
            System.out.println("Ошибка:");
            System.out.println(e.toString());
        }
    }
}
