package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CampaignFormPage - Page Object for Campaign Creation/Edit (/campaigns/new, /campaigns/edit/:id).
 * Test Cases: TC_02_01, TC_02_02, TC_02_03, TC_02_06
 */
public class CampaignFormPage extends BasePage {

    private final By formTitle = By.cssSelector("mat-card-title");
    private final By nameInput = By.cssSelector("input[formControlName='name']");
    private final By descriptionInput = By.cssSelector("textarea[formControlName='description']");
    private final By platformSelect = By.cssSelector("mat-select[formControlName='platform']");
    private final By budgetInput = By.cssSelector("input[formControlName='budget']");
    private final By startDateInput = By.cssSelector("input[formControlName='startDate']");
    private final By endDateInput = By.cssSelector("input[formControlName='endDate']");
    private final By eligibilityInput = By.cssSelector("textarea[formControlName='eligibility']");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By cancelButton = By.xpath("//button[contains(.,'Cancel')]");

    // Validation errors
    private final By nameRequiredError = By.xpath("//mat-error[contains(text(),'Name is required')]");
    private final By nameMinLengthError = By.xpath("//mat-error[contains(text(),'at least 3')]");
    private final By descRequiredError = By.xpath("//mat-error[contains(text(),'Description is required')]");
    private final By descMinLengthError = By.xpath("//mat-error[contains(text(),'at least 10')]");
    private final By platformRequiredError = By.xpath("//mat-error[contains(text(),'Platform is required')]");
    private final By budgetRequiredError = By.xpath("//mat-error[contains(text(),'Budget is required')]");
    private final By budgetMinError = By.xpath("//mat-error[contains(text(),'positive')]");
    private final By startDateRequiredError = By.xpath("//mat-error[contains(text(),'Start date')]");
    private final By endDateRequiredError = By.xpath("//mat-error[contains(text(),'End date')]");

    public CampaignFormPage(WebDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public CampaignFormPage enterName(String name) {
        type(nameInput, name);
        driver.findElement(nameInput).sendKeys(org.openqa.selenium.Keys.TAB);
        return this;
    }

    public CampaignFormPage enterDescription(String description) {
        type(descriptionInput, description);
        driver.findElement(descriptionInput).sendKeys(org.openqa.selenium.Keys.TAB);
        return this;
    }

    public CampaignFormPage selectPlatform(String platform) {
        selectMatOption(platformSelect, platform);
        return this;
    }

    public CampaignFormPage enterBudget(String budget) {
        type(budgetInput, budget);
        driver.findElement(budgetInput).sendKeys(org.openqa.selenium.Keys.TAB);
        return this;
    }

    public CampaignFormPage enterStartDate(String date) {
        type(startDateInput, date);
        driver.findElement(startDateInput).sendKeys(org.openqa.selenium.Keys.TAB);
        return this;
    }

    public CampaignFormPage enterEndDate(String date) {
        type(endDateInput, date);
        driver.findElement(endDateInput).sendKeys(org.openqa.selenium.Keys.TAB);
        return this;
    }

    public CampaignFormPage enterEligibility(String eligibility) {
        type(eligibilityInput, eligibility);
        driver.findElement(eligibilityInput).sendKeys(org.openqa.selenium.Keys.TAB);
        return this;
    }

    /**
     * Blurs the form fields to trigger validation.
     */
    public CampaignFormPage blurForm() {
        jsClick(formTitle);
        waitUtils.shortWait(200);
        return this;
    }

    public void clickSubmit() {
        click(submitButton);
    }

    public void clickCancel() {
        click(cancelButton);
        waitForPageLoad();
    }

    /**
     * Fills the campaign form with all required fields and submits.
     */
    public void createCampaign(String name, String description, String platform,
                                String budget, String startDate, String endDate,
                                String eligibility) {
        enterName(name);
        enterDescription(description);
        selectPlatform(platform);
        enterBudget(budget);
        enterStartDate(startDate);
        enterEndDate(endDate);
        if (eligibility != null && !eligibility.isEmpty()) {
            enterEligibility(eligibility);
        }
        blurForm(); // Trigger validation checks
        clickSubmit();
    }

    // --- Verifications ---

    public String getFormTitle() {
        return getText(formTitle);
    }

    public boolean isEditMode() {
        return getFormTitle().contains("Edit");
    }

    public boolean isSubmitButtonEnabled() {
        return isEnabled(submitButton);
    }

    public boolean isNameRequiredErrorDisplayed() { return isDisplayed(nameRequiredError); }
    public boolean isNameMinLengthErrorDisplayed() { return isDisplayed(nameMinLengthError); }
    public boolean isDescRequiredErrorDisplayed() { return isDisplayed(descRequiredError); }
    public boolean isDescMinLengthErrorDisplayed() { return isDisplayed(descMinLengthError); }
    public boolean isPlatformRequiredErrorDisplayed() { return isDisplayed(platformRequiredError); }
    public boolean isBudgetRequiredErrorDisplayed() { return isDisplayed(budgetRequiredError); }

    /**
     * Touches all fields to trigger validation.
     */
    public CampaignFormPage touchAllFields() {
        click(nameInput);
        click(descriptionInput);
        click(budgetInput);
        click(nameInput);
        return this;
    }

    /**
     * Gets the snackbar message after form submission.
     */
    public String getSnackbarText() {
        return getSnackbarMessage();
    }
}
