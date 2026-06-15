package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static String getBaseUrl() {
        return props.getProperty("base.url", "https://example.com");
    }

    public static String getBrowser() {
        return props.getProperty("browser", "chromium");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(props.getProperty("headless", "false"));
    }
}
