package com.sponsorship.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtils - Captures and saves screenshots for test reporting.
 */
public class ScreenshotUtils {

    private ScreenshotUtils() {
        // Private constructor
    }

    /**
     * Captures a screenshot and saves it with the given test name.
     * Returns the absolute path to the saved screenshot file.
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        String screenshotDir = ConfigReader.getProperty("screenshot.path", "reports/screenshots/");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = sanitizeFileName(testName) + "_" + timestamp + ".png";

        try {
            // Ensure directory exists
            Path dirPath = Paths.get(screenshotDir);
            Files.createDirectories(dirPath);

            // Take screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destPath = dirPath.resolve(fileName);
            Files.copy(srcFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);

            return destPath.toAbsolutePath().toString();
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Captures a screenshot and returns it as a Base64 string (for embedding in reports).
     */
    public static String captureScreenshotAsBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Sanitizes file name by removing special characters.
     */
    private static String sanitizeFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9_\\-]", "_");
    }
}
