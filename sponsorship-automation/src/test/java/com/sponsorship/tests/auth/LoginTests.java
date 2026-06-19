package com.sponsorship.tests.auth;

import com.sponsorship.base.BaseTest;
import com.sponsorship.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LoginTests - Automation tests for the Login module.
 * Test Cases: TC_01_04, TC_01_05
 */
public class LoginTests extends BaseTest {

    // ============================================================
    // TC_01_04 - Verify successful login with valid credentials
    // ============================================================

    @Test(description = "TC_01_04 - Login with valid Brand credentials and verify dashboard redirect",
            groups = {"smoke", "regression", "auth"})
    public void TC_01_04_loginWithValidBrandCredentials() {
        createTest("TC_01_04", "Login with valid Brand credentials");
        getTest().info("Step 1: Enter valid brand email and password");

        loginPage.enterEmail(ConfigReader.getBrandEmail());
        loginPage.enterPassword(ConfigReader.getBrandPassword());

        getTest().info("Step 2: Click Sign In");
        loginPage.clickSignIn();

        getTest().info("Step 3: Verify redirect to Brand Dashboard");
        loginPage.waitForDashboardRedirect();
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard/brand"),
                "User should be redirected to Brand Dashboard");

        initializeAuthenticatedPages();
        Assert.assertTrue(brandDashboard.isDashboardDisplayed(),
                "Brand Dashboard should be displayed");
        getTest().pass("Brand login successful - redirected to Brand Dashboard");
    }

    @Test(description = "TC_01_04 - Login with valid Influencer credentials and verify dashboard redirect",
            groups = {"smoke", "regression", "auth"})
    public void TC_01_04_loginWithValidInfluencerCredentials() {
        createTest("TC_01_04", "Login with valid Influencer credentials");
        getTest().info("Step 1: Enter valid influencer email and password");

        loginPage.enterEmail(ConfigReader.getInfluencerEmail());
        loginPage.enterPassword(ConfigReader.getInfluencerPassword());

        getTest().info("Step 2: Click Sign In");
        loginPage.clickSignIn();

        getTest().info("Step 3: Verify redirect to Influencer Dashboard");
        loginPage.waitForDashboardRedirect();
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard/influencer"),
                "User should be redirected to Influencer Dashboard");

        initializeAuthenticatedPages();
        Assert.assertTrue(influencerDashboard.isDashboardDisplayed(),
                "Influencer Dashboard should be displayed");
        getTest().pass("Influencer login successful - redirected to Influencer Dashboard");
    }

    @Test(description = "TC_01_04 - Login with valid Admin credentials and verify dashboard redirect",
            groups = {"smoke", "regression", "auth"})
    public void TC_01_04_loginWithValidAdminCredentials() {
        createTest("TC_01_04", "Login with valid Admin credentials");
        getTest().info("Step 1: Enter valid admin email and password");

        loginPage.enterEmail(ConfigReader.getAdminEmail());
        loginPage.enterPassword(ConfigReader.getAdminPassword());

        getTest().info("Step 2: Click Sign In");
        loginPage.clickSignIn();

        getTest().info("Step 3: Verify redirect to Admin Dashboard");
        loginPage.waitForDashboardRedirect();
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard/admin"),
                "User should be redirected to Admin Dashboard");

        initializeAuthenticatedPages();
        Assert.assertTrue(adminDashboard.isDashboardDisplayed(),
                "Admin Dashboard should be displayed");
        getTest().pass("Admin login successful - redirected to Admin Dashboard");
    }

    // ============================================================
    // TC_01_05 - Verify login fails with incorrect password
    // ============================================================

    @Test(description = "TC_01_05 - Login with incorrect password shows error message",
            groups = {"regression", "auth"})
    public void TC_01_05_loginWithIncorrectPassword() {
        createTest("TC_01_05", "Login with incorrect password");
        getTest().info("Step 1: Enter valid email with wrong password");

        loginPage.enterEmail(ConfigReader.getBrandEmail());
        loginPage.enterPassword("wrongpassword123");

        getTest().info("Step 2: Click Sign In");
        loginPage.clickSignIn();

        getTest().info("Step 3: Verify error message is displayed");
        String snackbarText = loginPage.getSnackbarText();
        Assert.assertTrue(snackbarText.toLowerCase().contains("fail") ||
                        snackbarText.toLowerCase().contains("invalid") ||
                        snackbarText.toLowerCase().contains("error"),
                "Error message should be displayed for incorrect password. Got: " + snackbarText);

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "User should remain on login page");
        getTest().pass("Error message displayed correctly for incorrect password: " + snackbarText);
    }

    @Test(description = "TC_01_05 - Login with non-existent email shows error message",
            groups = {"regression", "auth"})
    public void TC_01_05_loginWithNonExistentEmail() {
        createTest("TC_01_05", "Login with non-existent email");
        getTest().info("Step 1: Enter non-existent email with any password");

        loginPage.enterEmail("nonexistent_user_xyz@gmail.com");
        loginPage.enterPassword("somePassword@123");

        getTest().info("Step 2: Click Sign In");
        loginPage.clickSignIn();

        getTest().info("Step 3: Verify error message is displayed");
        String snackbarText = loginPage.getSnackbarText();
        Assert.assertTrue(snackbarText.toLowerCase().contains("fail") ||
                        snackbarText.toLowerCase().contains("invalid") ||
                        snackbarText.toLowerCase().contains("error") ||
                        snackbarText.toLowerCase().contains("not found"),
                "Error message should be displayed for non-existent email. Got: " + snackbarText);

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "User should remain on login page");
        getTest().pass("Error message displayed correctly for non-existent email: " + snackbarText);
    }

    // ============================================================
    // Additional Login Validations
    // ============================================================

    @Test(description = "TC_01_04 - Verify login page UI elements are displayed correctly",
            groups = {"sanity", "regression", "auth"})
    public void TC_01_04_verifyLoginPageUI() {
        createTest("TC_01_04", "Verify Login page UI elements");

        getTest().info("Verifying login page elements");
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page should display email, password fields and sign-in button");
        Assert.assertEquals(loginPage.getCardTitle(), "Welcome Back",
                "Card title should be 'Welcome Back'");
        Assert.assertEquals(loginPage.getBrandPillText(), "Sponsorship Hub",
                "Brand pill should show 'Sponsorship Hub'");
        Assert.assertTrue(loginPage.isDemoAccountsSectionDisplayed(),
                "Demo accounts section should be visible");

        getTest().pass("All login page UI elements verified successfully");
    }

    @Test(description = "TC_01_04 - Verify Sign In button is disabled when form is empty",
            groups = {"regression", "auth"})
    public void TC_01_04_verifySignInDisabledForEmptyForm() {
        createTest("TC_01_04", "Verify Sign In button disabled for empty form");

        getTest().info("Checking Sign In button state with empty form");
        Assert.assertFalse(loginPage.isSignInButtonEnabled(),
                "Sign In button should be disabled when form is empty");

        getTest().pass("Sign In button is correctly disabled for empty form");
    }

    @Test(description = "TC_01_04 - Verify password visibility toggle works",
            groups = {"regression", "auth"})
    public void TC_01_04_verifyPasswordToggle() {
        createTest("TC_01_04", "Verify password visibility toggle");

        loginPage.enterPassword("testpassword");
        getTest().info("Step 1: Password field should be hidden by default");
        Assert.assertEquals(loginPage.getPasswordFieldType(), "password",
                "Password should be hidden by default");

        getTest().info("Step 2: Toggle visibility");
        loginPage.togglePasswordVisibility();
        Assert.assertEquals(loginPage.getPasswordFieldType(), "text",
                "Password should be visible after toggle");

        getTest().info("Step 3: Toggle back to hidden");
        loginPage.togglePasswordVisibility();
        Assert.assertEquals(loginPage.getPasswordFieldType(), "password",
                "Password should be hidden again");

        getTest().pass("Password visibility toggle works correctly");
    }
}
