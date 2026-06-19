package com.sponsorship.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager - Initializes and manages Extent Reports.
 * Generates HTML reports with system info and test details.
 */
public class ExtentReportManager {

    private static ExtentReports extentReports;

    private ExtentReportManager() {
        // Private constructor
    }

    /**
     * Initializes and returns the singleton ExtentReports instance.
     */
    public static synchronized ExtentReports getReporter() {
        if (extentReports == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = ConfigReader.getProperty("report.path", "reports/")
                    + "ExtentReport_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Report configuration
            sparkReporter.config().setDocumentTitle("Sponsorship Hub - Automation Test Report");
            sparkReporter.config().setReportName("QA Automation Execution Report");
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
            sparkReporter.config().setEncoding("UTF-8");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);

            // System information
            extentReports.setSystemInfo("Application", "Sponsorship Hub");
            extentReports.setSystemInfo("Base URL", ConfigReader.getBaseUrl());
            extentReports.setSystemInfo("Browser", ConfigReader.getProperty("browser", "chrome"));
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("Tester", "QA Automation Engineer");
            extentReports.setSystemInfo("Framework", "Selenium 4 + TestNG + POM");
        }
        return extentReports;
    }

    /**
     * Flushes the report (writes all data to file).
     * Must be called at end of test suite.
     */
    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    /**
     * Resets the reporter (for fresh runs).
     */
    public static void reset() {
        extentReports = null;
    }
}
