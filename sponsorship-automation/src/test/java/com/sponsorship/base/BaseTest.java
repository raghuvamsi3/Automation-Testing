package com.sponsorship.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.sponsorship.utils.ConfigReader;
import com.sponsorship.utils.DriverFactory;
import com.sponsorship.utils.ExtentReportManager;
import com.sponsorship.utils.ScreenshotUtils;
import com.sponsorship.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * BaseTest - Parent class for all test classes. Manages WebDriver lifecycle,
 * Extent Reports, and common test setup.
 */
public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extentReports;
    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Page Objects - initialized in setUp
    protected LoginPage loginPage;
    protected SignupPage signupPage;
    protected NavbarComponent navbar;
    protected BrandDashboardPage brandDashboard;
    protected InfluencerDashboardPage influencerDashboard;
    protected AdminDashboardPage adminDashboard;
    protected CampaignListPage campaignList;
    protected CampaignFormPage campaignForm;
    protected CampaignDetailPage campaignDetail;
    protected SponsorshipRequestPage sponsorshipRequestPage;
    protected PaymentPage paymentPage;
    protected RatingPage ratingPage;
    protected NotificationPage notificationPage;
    protected com.sponsorship.utils.WaitUtils waitUtils;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        extentReports = ExtentReportManager.getReporter();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.initDriver();
        driver.get(ConfigReader.getBaseUrl());
        waitUtils = new com.sponsorship.utils.WaitUtils(driver);
        initializePageObjects();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot on failure
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
            ExtentTest test = extentTest.get();
            if (test != null && screenshotPath != null) {
                test.fail("Test Failed: " + result.getThrowable().getMessage());
                try {
                    test.addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e) {
                    test.fail("Screenshot capture failed: " + e.getMessage());
                }
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentTest test = extentTest.get();
            if (test != null) {
                test.skip("Test Skipped: " + (result.getThrowable() != null
                        ? result.getThrowable().getMessage() : "Skipped by configuration"));
            }
        }

        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown() {
        ExtentReportManager.flushReport();
    }

    /**
     * Initializes all page objects with the current driver instance.
     */
    private void initializePageObjects() {
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        navbar = new NavbarComponent(driver);
    }

    /**
     * Initializes page objects that require authentication. Call this after
     * logging in.
     */
    protected void initializeAuthenticatedPages() {
        brandDashboard = new BrandDashboardPage(driver);
        influencerDashboard = new InfluencerDashboardPage(driver);
        adminDashboard = new AdminDashboardPage(driver);
        campaignList = new CampaignListPage(driver);
        campaignForm = new CampaignFormPage(driver);
        campaignDetail = new CampaignDetailPage(driver);
        sponsorshipRequestPage = new SponsorshipRequestPage(driver);
        paymentPage = new PaymentPage(driver);
        ratingPage = new RatingPage(driver);
        notificationPage = new NotificationPage(driver);
    }

    // --- Helper Methods for Tests ---
    /**
     * Logs into the application as a Brand user.
     */
    protected void loginAsBrand() {
        loginPage.login(ConfigReader.getBrandEmail(), ConfigReader.getBrandPassword());
        loginPage.waitForDashboardRedirect();
        initializeAuthenticatedPages();
    }

    /**
     * Logs into the application as an Influencer user.
     */
    protected void loginAsInfluencer() {
        loginPage.login(ConfigReader.getInfluencerEmail(), ConfigReader.getInfluencerPassword());
        loginPage.waitForDashboardRedirect();
        initializeAuthenticatedPages();
    }

    /**
     * Logs into the application as an Admin user.
     */
    protected void loginAsAdmin() {
        loginPage.login(ConfigReader.getAdminEmail(), ConfigReader.getAdminPassword());
        loginPage.waitForDashboardRedirect();
        initializeAuthenticatedPages();
    }

    /**
     * Creates an ExtentTest entry for the current test.
     */
    protected ExtentTest createTest(String testCaseId, String description) {
        ExtentTest test = extentReports.createTest(testCaseId + " - " + description);
        extentTest.set(test);
        return test;
    }

    /**
     * Gets the current ExtentTest instance.
     */
    protected ExtentTest getTest() {
        return extentTest.get();
    }
}
