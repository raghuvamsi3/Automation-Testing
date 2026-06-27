package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * SignupPage - Page Object for the Registration screen (/signup).
 * Test Cases: TC_01_01, TC_01_02, TC_01_03
 */
public class SignupPage extends BasePage {

    // --- Locators ---
    private final By usernameInput = By.cssSelector("input[formControlName='name']");
    private final By emailInput = By.cssSelector("input[formControlName='email']");
    private final By passwordInput = By.cssSelector("input[formControlName='password']");
    private final By roleSelect = By.cssSelector("mat-select[formControlName='role']");
    private final By createAccountButton = By.cssSelector("button[type='submit']");
    private final By passwordToggleButton = By.cssSelector("button[matSuffix]");
    private final By signInLink = By.cssSelector("a[routerLink='/login']");
    private final By cardTitle = By.cssSelector("mat-card-title");
    private final By brandPill = By.cssSelector(".brand-pill");

    // Error locators
    private final By usernameRequiredError = By.xpath("//mat-error[contains(text(),'Username is required')]");
    private final By usernameMinLengthError = By.xpath("//mat-error[contains(text(),'at least 3')]");
    private final By emailRequiredError = By.xpath("//mat-error[contains(text(),'Email is required')]");
    private final By emailInvalidError = By.xpath("//mat-error[contains(text(),'Invalid email')]");
    private final By passwordRequiredError = By.xpath("//mat-error[contains(text(),'Password is required')]");
    private final By passwordPatternError = By.xpath("//mat-error[contains(text(),'must include')]");
    private final By roleRequiredError = By.xpath("//mat-error[contains(text(),'select a role')]");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    // --- Navigation ---

    /**
     * Navigates directly to the signup page.
     */
    public SignupPage navigateToSignup() {
        navigateTo(driver.getCurrentUrl().split("/")[0] + "//sponsorship-front.netlify.app/signup");
        waitForPageLoad();
        return this;
    }

    // --- Actions ---

    /**
     * Enters username.
     */
    public SignupPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    /**
     * Enters email.
     */
    public SignupPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    /**
     * Enters password.
     */
    public SignupPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    /**
     * Blurs the password field to trigger validation.
     */
    public SignupPage blurPasswordField() {
        click(cardTitle);
        waitUtils.shortWait(200);
        return this;
    }

    /**
     * Selects role from the dropdown (BRAND or INFLUENCER).
     */
    public SignupPage selectRole(String role) {
        selectMatOption(roleSelect, role);
        return this;
    }

    /**
     * Clicks the Create Account button.
     */
    public SignupPage clickCreateAccount() {
        click(createAccountButton);
        return this;
    }

    /**
     * Performs a complete registration flow.
     */
    public void register(String username, String email, String password, String role) {
        enterUsername(username);
        enterEmail(email);
        enterPassword(password);
        selectRole(role);
        clickCreateAccount();
    }

    /**
     * Clicks the Sign In link.
     */
    public void clickSignInLink() {
        click(signInLink);
    }

    /**
     * Toggles password visibility.
     */
    public SignupPage togglePasswordVisibility() {
        click(passwordToggleButton);
        return this;
    }

    // --- Verifications ---

    /**
     * Checks if the signup page is displayed.
     */
    public boolean isSignupPageDisplayed() {
        return isDisplayed(usernameInput) && isDisplayed(emailInput)
                && isDisplayed(passwordInput) && isDisplayed(roleSelect);
    }

    /**
     * Gets the card title text.
     */
    public String getCardTitle() {
        return getText(cardTitle);
    }

    /**
     * Checks if the Create Account button is enabled.
     */
    public boolean isCreateAccountButtonEnabled() {
        return isEnabled(createAccountButton);
    }

    /**
     * Gets the snackbar message text.
     */
    public String getSnackbarText() {
        return getSnackbarMessage();
    }

    /**
     * Checks if registration was successful.
     */
    public boolean isRegistrationSuccessful() {
        return isSnackbarDisplayedWithText("Registration successful");
    }

    /**
     * Waits for redirect to dashboard after successful registration.
     */
    public void waitForDashboardRedirect() {
        waitUtils.waitForUrlContains("/dashboard");
        waitForPageLoad();
    }

    // --- Error Message Getters ---

    public boolean isUsernameRequiredErrorDisplayed() {
        return isDisplayed(usernameRequiredError);
    }

    public boolean isUsernameMinLengthErrorDisplayed() {
        return isDisplayed(usernameMinLengthError);
    }

    public boolean isEmailRequiredErrorDisplayed() {
        return isDisplayed(emailRequiredError);
    }

    public boolean isEmailInvalidErrorDisplayed() {
        return isDisplayed(emailInvalidError);
    }

    public boolean isPasswordRequiredErrorDisplayed() {
        return isDisplayed(passwordRequiredError);
    }

    public boolean isPasswordPatternErrorDisplayed() {
        return isDisplayed(passwordPatternError);
    }

    public boolean isRoleRequiredErrorDisplayed() {
        return isDisplayed(roleRequiredError);
    }

    /**
     * Touches all fields to trigger validation errors.
     */
    public SignupPage touchAllFields() {
        click(usernameInput);
        click(emailInput);
        click(passwordInput);
        click(usernameInput); // Click back to trigger last field validation
        return this;
    }
}
