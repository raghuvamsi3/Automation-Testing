package com.sponsorship.pages;

import com.sponsorship.utils.ConfigReader;
import com.sponsorship.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * BasePage - Parent class for all Page Objects.
 * Provides common interaction methods with Angular Material awareness.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WaitUtils waitUtils;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // --- Core Interaction Methods ---

    /**
     * Clicks on an element after waiting for it to be clickable.
     */
    protected void click(By locator) {
        waitUtils.waitForClickable(locator).click();
    }

    /**
     * Types text into an input field after clearing it.
     */
    protected void type(By locator, String text) {
        WebElement element = waitUtils.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Types text into an input using JS (for Angular Material inputs that resist clear).
     */
    protected void typeWithJs(By locator, String text) {
        WebElement element = waitUtils.waitForVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '';", element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets the visible text of an element.
     */
    protected String getText(By locator) {
        return waitUtils.waitForVisible(locator).getText().trim();
    }

    /**
     * Gets the value attribute of an input element.
     */
    protected String getValue(By locator) {
        return waitUtils.waitForVisible(locator).getAttribute("value");
    }

    /**
     * Checks if an element is displayed on the page.
     */
    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Checks if an element is present in DOM (not necessarily visible).
     */
    protected boolean isPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if a button/input is enabled.
     */
    protected boolean isEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Gets all elements matching a locator.
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Gets element count for a locator.
     */
    protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }

    // --- Angular Material Specific Methods ---

    /**
     * Selects an option from a mat-select dropdown.
     */
    protected void selectMatOption(By selectLocator, String optionText) {
        try {
            click(selectLocator);
            waitUtils.waitForMatSelectPanel();
        } catch (org.openqa.selenium.TimeoutException e) {
            jsClick(selectLocator);
            waitUtils.waitForMatSelectPanel();
        }
        By optionLocator = By.xpath("//mat-option[contains(normalize-space(.), '" + optionText + "')]");
        try {
            click(optionLocator);
        } catch (Exception e) {
            jsClick(optionLocator);
        }
        // Wait for overlay to close
        waitUtils.shortWait(500);
    }

    /**
     * Gets the currently selected value from a mat-select.
     */
    protected String getMatSelectValue(By selectLocator) {
        return getText(By.cssSelector(selectLocator.toString().replace("By.cssSelector: ", "") + " .mat-mdc-select-value-text"));
    }

    /**
     * Gets the text of a mat-error message within a form field.
     */
    protected String getMatError(By parentFieldLocator) {
        try {
            WebElement field = driver.findElement(parentFieldLocator);
            WebElement error = field.findElement(By.tagName("mat-error"));
            return error.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /**
     * Checks if a mat-error is displayed.
     */
    protected boolean hasMatError() {
        return !driver.findElements(By.tagName("mat-error")).isEmpty();
    }

    /**
     * Gets all visible mat-error messages.
     */
    protected List<String> getAllMatErrors() {
        List<WebElement> errors = driver.findElements(By.tagName("mat-error"));
        return errors.stream()
                .filter(WebElement::isDisplayed)
                .map(e -> e.getText().trim())
                .toList();
    }

    /**
     * Waits for snackbar message and returns its text.
     */
    protected String getSnackbarMessage() {
        return waitUtils.waitForSnackbar();
    }

    /**
     * Checks if snackbar contains expected text.
     */
    protected boolean isSnackbarDisplayedWithText(String text) {
        return waitUtils.waitForSnackbarWithText(text);
    }

    // --- Navigation Methods ---

    /**
     * Gets the current URL.
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Gets the page title.
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Navigates to a specific URL.
     */
    protected void navigateTo(String url) {
        driver.get(url);
        waitUtils.waitForAngularLoad();
    }

    /**
     * Scrolls to an element.
     */
    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        waitUtils.shortWait(300);
    }

    /**
     * Clicks via JavaScript (for elements obscured by overlays).
     */
    protected void jsClick(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Waits for page to fully load (Angular + DOM).
     */
    protected void waitForPageLoad() {
        waitUtils.waitForAngularLoad();
        waitUtils.waitForSpinnerToDisappear();
    }
}
