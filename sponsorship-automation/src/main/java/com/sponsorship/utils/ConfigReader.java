package com.sponsorship.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader - Reads configuration from config.properties file.
 * Provides typed accessors for common settings.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private ConfigReader() {
        // Private constructor
    }

    /**
     * Gets a property value by key.
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    /**
     * Gets a property value with a default fallback.
     */
    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? value.trim() : defaultValue;
    }

    // --- Convenience Methods ---

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getAdminEmail() {
        return getProperty("admin.email");
    }

    public static String getAdminPassword() {
        return getProperty("admin.password");
    }

    public static String getBrandEmail() {
        return getProperty("brand.email");
    }

    public static String getBrandPassword() {
        return getProperty("brand.password");
    }

    public static String getInfluencerEmail() {
        return getProperty("influencer.email");
    }

    public static String getInfluencerPassword() {
        return getProperty("influencer.password");
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "15"));
    }
}
