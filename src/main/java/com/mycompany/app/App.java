package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Artem\\ST-7\\my-app\\chromedriver\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        try {
            Task2.execute(webDriver);
            Task3.execute(webDriver);
        } finally {
            webDriver.quit();
        }
    }
}