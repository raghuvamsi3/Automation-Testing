package com.sponsorship.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * WaitUtils - Provides explicit wait helpers for Selenium operations.
 * Includes Angular-specific waits for Material components.
 */
public class WaitUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        int timeout = ConfigReader.getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public WaitUtils(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Wait for element to be visible on page.
     */
    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable.
     */
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be present in DOM (not necessarily visible).
     */
    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for element to disappear from page.
     */
    public boolean waitForInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for URL to contain a specific substring.
     */
    public boolean waitForUrlContains(String urlPart) {
        return wait.until(ExpectedConditions.urlContains(urlPart));
    }

    /**
     * Wait for URL to match exactly.
     */
    public boolean waitForUrlToBe(String url) {
        return wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Wait for text to be present in element.
     */
    public boolean waitForTextPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for all elements to be visible.
     */
    public List<WebElement> waitForAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for Angular Material spinner to disappear.
     */
    public void waitForSpinnerToDisappear() {
        try {
            List<WebElement> spinners = driver.findElements(By.tagName("mat-spinner"));
            if (!spinners.isEmpty()) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.tagName("mat-spinner")));
            }
        } catch (TimeoutException e) {
            // Spinner might have already disappeared
        }
    }

    /**
     * Wait for Angular Material snackbar to appear and return its text.
     */
    public String waitForSnackbar() {
        try {
            // Try standard Angular Material 15+ locator first
            WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("mat-snack-bar-container simple-snack-bar span, mat-snack-bar-container .mdc-snackbar__label")));
            return snackbar.getText();
        } catch (TimeoutException e) {
            // Fallback to simpler locator
            WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("mat-snack-bar-container")));
            return snackbar.getText();
        }
    }

    /**
     * Wait for snackbar to appear with specific text.
     */
    public boolean waitForSnackbarWithText(String expectedText) {
        try {
            // Using textContains expected condition directly on the container
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.cssSelector("mat-snack-bar-container"), expectedText));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Wait for snackbar to disappear.
     */
    public void waitForSnackbarToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("mat-snack-bar-container")));
        } catch (TimeoutException e) {
            // Already gone
        }
    }

    /**
     * Wait for Angular to finish all pending HTTP requests and rendering.
     * Uses Angular's testability API.
     */
    public void waitForAngularLoad() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Wait for document ready state
            wait.until((ExpectedCondition<Boolean>) d -> {
                Object readyState = js.executeScript("return document.readyState");
                return "complete".equals(readyState);
            });

            // Wait for Angular to stabilize (if available)
            wait.until((ExpectedCondition<Boolean>) d -> {
                try {
                    Boolean angularReady = (Boolean) js.executeScript(
                            "if (window.getAllAngularTestabilities) {" +
                            "  var testabilities = window.getAllAngularTestabilities();" +
                            "  if (testabilities && testabilities.length > 0) {" +
                            "    return testabilities[0].isStable();" +
                            "  }" +
                            "}" +
                            "return true;");
                    return angularReady != null && angularReady;
                } catch (Exception e) {
                    return true;
                }
            });
        } catch (Exception e) {
            // Fallback: Angular might not be loaded yet
        }
    }

    /**
     * Wait for Mat-Select overlay to appear (after clicking a mat-select).
     */
    public void waitForMatSelectPanel() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.cdk-overlay-container mat-option")));
    }

    /**
     * Short wait (used between rapid UI interactions).
     */
    public void shortWait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Wait for page to navigate to a new URL (different from current).
     */
    public void waitForNavigation(String currentUrl) {
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
    }

    /**
     * Wait for an element to become stale (removed from DOM), useful for route transitions.
     */
    public void waitForElementToBeStale(WebElement element) {
        wait.until(ExpectedConditions.stalenessOf(element));
    }
}
