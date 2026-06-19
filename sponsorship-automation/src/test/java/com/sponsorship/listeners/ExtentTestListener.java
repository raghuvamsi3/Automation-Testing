package com.sponsorship.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.sponsorship.utils.DriverFactory;
import com.sponsorship.utils.ExtentReportManager;
import com.sponsorship.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * ExtentTestListener - TestNG ITestListener for Extent Reports integration.
 * Automatically logs test start/pass/fail/skip and captures screenshots on failure.
 */
public class ExtentTestListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("========================================");
        System.out.println("  Test Suite Started: " + context.getName());
        System.out.println("========================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();

        // Use description if available, otherwise use method name
        String displayName = (description != null && !description.isEmpty())
                ? description : testName;

        ExtentTest test = ExtentReportManager.getReporter().createTest(displayName);
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());

        // Add test groups as categories
        String[] groups = result.getMethod().getGroups();
        if (groups.length > 0) {
            test.assignCategory(groups);
        }

        testThread.set(test);
        System.out.println("  ▶ Running: " + displayName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = testThread.get();
        if (test != null) {
            test.log(Status.PASS,
                    MarkupHelper.createLabel("TEST PASSED", ExtentColor.GREEN));
        }
        System.out.println("  ✓ PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testThread.get();
        if (test != null) {
            test.log(Status.FAIL,
                    MarkupHelper.createLabel("TEST FAILED", ExtentColor.RED));
            test.fail(result.getThrowable());

            // Capture screenshot
            WebDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                String base64Screenshot = ScreenshotUtils.captureScreenshotAsBase64(driver);
                if (base64Screenshot != null) {
                    test.addScreenCaptureFromBase64String(base64Screenshot, "Failure Screenshot");
                }
                // Also save to file
                String filePath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
                if (filePath != null) {
                    test.info("Screenshot saved: " + filePath);
                }
            }
        }
        System.out.println("  ✗ FAILED: " + result.getMethod().getMethodName()
                + " | Reason: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = testThread.get();
        if (test != null) {
            test.log(Status.SKIP,
                    MarkupHelper.createLabel("TEST SKIPPED", ExtentColor.ORANGE));
            if (result.getThrowable() != null) {
                test.skip(result.getThrowable());
            }
        }
        System.out.println("  ⊘ SKIPPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
        System.out.println("========================================");
        System.out.println("  Test Suite Finished: " + context.getName());
        System.out.println("  Total: " + (context.getPassedTests().size()
                + context.getFailedTests().size() + context.getSkippedTests().size()));
        System.out.println("  Passed: " + context.getPassedTests().size());
        System.out.println("  Failed: " + context.getFailedTests().size());
        System.out.println("  Skipped: " + context.getSkippedTests().size());
        System.out.println("========================================");
    }

    /**
     * Gets the current thread's ExtentTest instance.
     */
    public static ExtentTest getTest() {
        return testThread.get();
    }
}
