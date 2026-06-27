package com.sponsorship.tests.auth;

import com.sponsorship.base.BaseTest;
import com.sponsorship.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SignupTests - Automation tests for the Registration module. Test Cases:
 * TC_01_01, TC_01_02, TC_01_03, TC_01_06
 */
public class SignupTests extends BaseTest {

    /**
     * Generates a unique email for registration tests.
     */
    private String generateUniqueEmail() {
        return "testuser_" + System.currentTimeMillis() + "@gmail.com";
    }

    private String generateUniqueUsername() {
        return "TestUser" + System.currentTimeMillis();
    }

    // ============================================================
    // TC_01_01 - Verify registration with valid data
    // ============================================================
    @Test(description = "TC_01_01 - Register with valid data as BRAND role",
            groups = {"smoke", "regression", "auth"})
    public void TC_01_01_registerWithValidDataAsBrand() {
        createTest("TC_01_01", "Register with valid data as BRAND");
        getTest().info("Step 1: Navigate to signup page");

        loginPage.clickSignUpLink();
        waitUtils.waitForUrlContains("/signup");

        getTest().info("Step 2: Fill registration form with valid data");
        String username = generateUniqueUsername();
        String email = generateUniqueEmail();

        signupPage.enterUsername(username);
        signupPage.enterEmail(email);
        signupPage.enterPassword("test@123");
        signupPage.selectRole("Brand");

        getTest().info("Step 3: Click Create Account");
        signupPage.clickCreateAccount();

        getTest().info("Step 4: Verify successful registration");
        // After registration, user is auto-logged-in and redirected to dashboard
        signupPage.waitForDashboardRedirect();
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "User should be redirected to dashboard after registration");
        getTest().pass("Brand registration successful with email: " + email);
    }

    @Test(description = "TC_01_01 - Register with valid data as INFLUENCER role",
            groups = {"smoke", "regression", "auth"})
    public void TC_01_01_registerWithValidDataAsInfluencer() {
        createTest("TC_01_01", "Register with valid data as INFLUENCER");
        getTest().info("Step 1: Navigate to signup page");

        loginPage.clickSignUpLink();
        waitUtils.waitForUrlContains("/signup");

        getTest().info("Step 2: Fill registration form with valid data");
        String username = generateUniqueUsername();
        String email = generateUniqueEmail();

        signupPage.enterUsername(username);
        signupPage.enterEmail(email);
        signupPage.enterPassword("test@456");
        signupPage.selectRole("Influencer");

        getTest().info("Step 3: Click Create Account");
        signupPage.clickCreateAccount();

        getTest().info("Step 4: Verify successful registration");
        signupPage.waitForDashboardRedirect();
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "User should be redirected to dashboard after registration");
        getTest().pass("Influencer registration successful with email: " + email);
    }

    // ============================================================
    // TC_01_02 - Verify registration fails with duplicate email
    // ============================================================
    @Test(description = "TC_01_02 - Registration fails with already registered email",
            groups = {"regression", "auth"})
    public void TC_01_02_registerWithDuplicateEmail() {
        createTest("TC_01_02", "Registration with duplicate email");
        getTest().info("Step 1: Navigate to signup page");

        loginPage.clickSignUpLink();
        waitUtils.waitForUrlContains("/signup");

        getTest().info("Step 2: Enter existing email (brand@gmail.com)");
        signupPage.enterUsername(generateUniqueUsername());
        signupPage.enterEmail(ConfigReader.getBrandEmail()); // Already registered
        signupPage.enterPassword("test@123");
        signupPage.selectRole("Brand");

        getTest().info("Step 3: Click Create Account");
        signupPage.clickCreateAccount();

        getTest().info("Step 4: Verify error message about duplicate email");
        String snackbarText = signupPage.getSnackbarText();
        Assert.assertTrue(
                snackbarText.toLowerCase().contains("already")
                || snackbarText.toLowerCase().contains("exists")
                || snackbarText.toLowerCase().contains("registered"),
                "Error message should indicate email already exists. Got: " + snackbarText);

        Assert.assertTrue(driver.getCurrentUrl().contains("/signup"),
                "User should remain on signup page");
        getTest().pass("Duplicate email error displayed correctly: " + snackbarText);
    }

    // ============================================================
    // TC_01_03 - Verify password strength validation
    // ============================================================
    @Test(description = "TC_01_03 - Password validation - too short password",
            groups = {"regression", "auth"})
    public void TC_01_03_passwordValidationTooShort() {
        createTest("TC_01_03", "Password validation - too short");
        getTest().info("Step 1: Navigate to signup page");

        loginPage.clickSignUpLink();
        waitUtils.waitForUrlContains("/signup");

        getTest().info("Step 2: Enter a short password without required characters");
        signupPage.enterUsername(generateUniqueUsername());
        signupPage.enterEmail(generateUniqueEmail());
        signupPage.enterPassword("ab1"); // Too short, but missing @

        getTest().info("Step 3: Click outside to trigger validation");
        signupPage.blurPasswordField();

        getTest().info("Step 4: Verify password validation error");
        Assert.assertTrue(signupPage.isPasswordPatternErrorDisplayed(),
                "Password pattern error should be displayed");
        Assert.assertFalse(signupPage.isCreateAccountButtonEnabled(),
                "Create Account button should be disabled");
        getTest().pass("Password validation correctly shows pattern error for short password");
    }

    @Test(description = "TC_01_03 - Password validation - missing special character",
            groups = {"regression", "auth"})
    public void TC_01_03_passwordValidationMissingSpecialChar() {
        createTest("TC_01_03", "Password validation - missing @ character");
        getTest().info("Step 1: Navigate to signup page");

        loginPage.clickSignUpLink();
        waitUtils.waitForUrlContains("/signup");

        getTest().info("Step 2: Enter password without @ character");
        signupPage.enterUsername(generateUniqueUsername());
        signupPage.enterEmail(generateUniqueEmail());
        signupPage.enterPassword("password123"); // Missing @

        getTest().info("Step 3: Trigger validation");
        signupPage.enterUsername(""); // Trigger blur

        getTest().info("Step 4: Verify validation error");
        Assert.assertTrue(signupPage.isPasswordPatternErrorDisplayed(),
                "Password pattern error should be shown for missing @ character");
        getTest().pass("Password validation correctly requires @ character");
    }

    @Test(description = "TC_01_03 - Password validation - valid password accepted",
            groups = {"regression", "auth"})
    public void TC_01_03_validPasswordAccepted() {
        createTest("TC_01_03", "Valid password passes validation");
        getTest().info("Step 1: Navigate to signup page");

        loginPage.clickSignUpLink();
        waitUtils.waitForUrlContains("/signup");

        getTest().info("Step 2: Enter valid password (letters + numbers + @)");
        signupPage.enterUsername(generateUniqueUsername());
        signupPage.enterEmail(generateUniqueEmail());
        signupPage.enterPassword("valid@123"); // Valid: letter + number + @, 6+ chars

        getTest().info("Step 3: Verify no password error");
        signupPage.selectRole("Brand");
        Assert.assertFalse(signupPage.isPasswordPatternErrorDisplayed(),
                "No password error should be shown for valid password");
        getTest().pass("Valid password accepted without errors");
    }

    // ============================================================
    // TC_01_06 - Verify user profile update (BLOCKED - Not Implemented)
    // ============================================================
    @Test(description = "TC_01_06 - BLOCKED: Profile update - Feature not implemented in current build",
            groups = {"regression", "auth"}, enabled = true)
    public void TC_01_06_profileUpdateNotImplemented() {
        createTest("TC_01_06", "BLOCKED: Profile update not implemented");
        getTest().warning("Feature NOT IMPLEMENTED: No profile edit page exists in the application.");
        getTest().info("The application has no /profile route or profile editing UI.");
        getTest().info("This test case is documented in the RTM but the feature is not built.");
        getTest().skip("BLOCKED - Feature Not Implemented: Profile update with image upload");

        // Intentionally skip
        throw new org.testng.SkipException(
                "BLOCKED - TC_01_06: Profile update feature is not implemented in the current build");
    }

    // ============================================================
    // Signup Page UI Verification
    // ============================================================
    @Test(description = "TC_01_01 - Verify signup page UI elements",
            groups = {"sanity", "regression", "auth"})
    public void TC_01_01_verifySignupPageUI() {
        createTest("TC_01_01", "Verify Signup page UI elements");

        getTest().info("Navigate to signup page");
        loginPage.clickSignUpLink();
        waitUtils.waitForUrlContains("/signup");

        getTest().info("Verify all form fields are present");
        Assert.assertTrue(signupPage.isSignupPageDisplayed(),
                "Signup page should display username, email, password, role fields");
        Assert.assertEquals(signupPage.getCardTitle(), "Create Your Account",
                "Card title should be 'Create Your Account'");

        getTest().pass("All signup page UI elements verified");
    }

    // waitUtils and setUp are handled by BaseTest
}
