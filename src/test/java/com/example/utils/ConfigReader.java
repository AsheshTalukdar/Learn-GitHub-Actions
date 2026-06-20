package com.example.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Reads configuration from settings.json (src/test/resources).
 * Values can be overridden at run time with JVM system properties,
 * e.g. -Dbrowser=firefox -Dheadless=false. This is how the GitHub
 * Actions workflow passes in "Run workflow" input parameters,
 * similar to how Jenkins build parameters work.
 */
public class ConfigReader {

    private static JSONObject config;

    private static JSONObject getConfig() {
        if (config == null) {
            try (InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("settings.json")) {
                if (is == null) {
                    throw new RuntimeException("settings.json not found on classpath (src/test/resources)");
                }
                String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                config = new JSONObject(content);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read settings.json", e);
            }
        }
        return config;
    }

    public static String getBrowser() {
        String sysVal = System.getProperty("browser");
        if (sysVal != null && !sysVal.isBlank()) {
            return sysVal;
        }
        return getConfig().optString("browser", "chrome");
    }

    public static boolean isHeadless() {
        String sysVal = System.getProperty("headless");
        if (sysVal != null && !sysVal.isBlank()) {
            return Boolean.parseBoolean(sysVal);
        }
        return getConfig().optBoolean("headless", true);
    }

    public static String getBaseUrl() {
        return getConfig().optString("baseUrl");
    }

    public static int getImplicitWaitSeconds() {
        return getConfig().optInt("implicitWaitSeconds", 10);
    }

    public static int getPageLoadTimeoutSeconds() {
        return getConfig().optInt("pageLoadTimeoutSeconds", 30);
    }

    public static String getUsername() {
        return getConfig().optString("username");
    }

    public static String getPassword() {
        return getConfig().optString("password");
    }
}
