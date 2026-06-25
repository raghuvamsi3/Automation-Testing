package com.sponsorship.pages;

import com.sponsorship.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage - Page Object for the Login screen (/login).
 * Test Cases: TC_01_04, TC_01_05
 */
public class LoginPage extends BasePage {

    // --- Locators ---
    private final By emailInput = By.cssSelector("input[formControlName='email']");
    private final By passwordInput = By.cssSelector("input[formControlName='password']");
    private final By signInButton = By.cssSelector("button[type='submit']");
    private final By passwordToggleButton = By.cssSelector("button[matSuffix]");
    private final By signUpLink = By.cssSelector("a[routerLink='/signup']");
    private final By emailError = By.xpath("//mat-error[contains(text(),'Email')]");
    private final By passwordError = By.xpath("//mat-error[contains(text(),'Password')]");
    private final By brandPill = By.cssSelector(".brand-pill");
    private final By cardTitle = By.cssSelector("mat-card-title");
    private final By loadingSpinner = By.cssSelector("mat-spinner");
    private final By snackbarContainer = By.xpath("//*[@id='mat-snack-bar-container-live-8']/div/simple-snack-bar/div[1]");
    private final By demoAccountsSection = By.cssSelector(".demo-accounts");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // --- Actions ---

    /**
     * Enters email address into the email field.
     */
    public LoginPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    /**
     * Enters password into the password field.
     */
    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    /**
     * Clicks the Sign In button.
     */
    public LoginPage clickSignIn() {
        click(signInButton);
        return this;
    }

    /**
     * Performs a complete login flow.
     */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    /**
     * Clicks the Sign Up link.
     */
    public void clickSignUpLink() {
        click(signUpLink);
    }

    /**
     * Toggles password visibility.
     */
    public LoginPage togglePasswordVisibility() {
        click(passwordToggleButton);
        return this;
    }

    // --- Verifications ---

    /**
     * Checks if the login page is displayed.
     */
    public boolean isLoginPageDisplayed() {
        return isDisplayed(emailInput) && isDisplayed(passwordInput) && isDisplayed(signInButton);
    }

    /**
     * Gets the card title text ("Welcome Back").
     */
    public String getCardTitle() {
        return getText(cardTitle);
    }

    /**
     * Gets the brand pill text ("Sponsorship Hub").
     */
    public String getBrandPillText() {
        return getText(brandPill);
    }

    /**
     * Checks if the Sign In button is enabled.
     */
    public boolean isSignInButtonEnabled() {
        return isEnabled(signInButton);
    }

    /**
     * Gets the email validation error message.
     */
    public String getEmailErrorMessage() {
        return getText(emailError);
    }

    /**
     * Gets the password validation error message.
     */
    public String getPasswordErrorMessage() {
        return getText(passwordError);
    }

    /**
     * Gets the snackbar message after login attempt.
     */
    public String getSnackbarText() {
        return getSnackbarMessage();
    }

    /**
     * Checks if login was successful by verifying snackbar text.
     */
    public boolean isLoginSuccessful() {
        return isSnackbarDisplayedWithText("Login successful");
    }

    /**
     * Waits for redirect to a dashboard after successful login.
     */
    public void waitForDashboardRedirect() {
        try {
            // Primary: wait for the canonical dashboard path
            waitUtils.waitForUrlContains("/dashboard");
        } catch (Exception e) {
            // Fallback: some deployments may not change URL on login — wait for the authenticated navbar
            try {
                waitUtils.waitForVisible(By.tagName("mat-toolbar"));
            } catch (Exception ignored) {
                // If both checks fail, continue so tests can assert based on page elements
            }
        }

        waitForPageLoad();
    }

    /**
     * Checks if the password field type is 'password' (hidden) or 'text' (visible).
     */
    public String getPasswordFieldType() {
        return waitUtils.waitForVisible(passwordInput).getAttribute("type");
    }

    /**
     * Checks if demo accounts section is displayed.
     */
    public boolean isDemoAccountsSectionDisplayed() {
        return isDisplayed(demoAccountsSection);
    }

    /**
     * Checks if email field is empty and touches it to trigger validation.
     */
    public LoginPage touchEmailField() {
        click(emailInput);
        click(passwordInput); // Click away to trigger validation
        return this;
    }

    /**
     * Checks if password field is empty and touches it to trigger validation.
     */
    public LoginPage touchPasswordField() {
        click(passwordInput);
        click(emailInput); // Click away to trigger validation
        return this;
    }
}
