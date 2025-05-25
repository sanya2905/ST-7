package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;

public class Task3 {
    public static void execute(WebDriver webDriver) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
            webDriver.get(url);

            WebElement elem = webDriver.findElement(By.tagName("pre"));

            String jsonStr = elem.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            JSONObject hourly = (JSONObject) obj.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            try (FileWriter writer = new FileWriter("C:\\Users\\Artem\\ST-7\\result\\forecast.txt", false)) {
                writer.write(String.format("%-3s %-18s %-13s %-13s%n", "№", "Дата/время", "Температура", "Осадки (мм)"));

                for (int i = 0; i < times.size(); i++) {
                    String time = (String) times.get(i);
                    Double temp = ((Number) temperatures.get(i)).doubleValue();
                    Double rain = ((Number) rains.get(i)).doubleValue();
                    writer.append(String.format("%-4d %-18s %-13.1f %-11.2f%n", i+1, time, temp, rain));
                }
                writer.flush();
            }
            System.out.println("Weather forecast written to result/forecast.txt");
        } catch (Exception e) {
            System.out.println("Error in Task3");
            System.out.println(e.toString());
        }
    }
}