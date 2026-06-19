package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * PaymentPage - Page Object for the Payments screen (/payments).
 * Test Cases: TC_05_01 – TC_05_06
 */
public class PaymentPage extends BasePage {

    private final By pageTitle = By.cssSelector(".container h1");
    private final By totalAmountDisplay = By.cssSelector(".summary-card h2");
    private final By totalLabel = By.cssSelector(".summary-card p");
    private final By paymentTable = By.cssSelector("table");
    private final By paymentRows = By.cssSelector("table mat-row, table tbody tr");
    private final By completeButtons = By.xpath("//button[contains(.,'Complete')]");
    private final By emptyState = By.cssSelector(".empty-state");
    private final By loadingSpinner = By.tagName("mat-spinner");
    private final By statusChips = By.cssSelector("table .status-chip");
    private final By amountCells = By.cssSelector("table td:nth-child(3)");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    // --- Verifications ---

    public boolean isPageDisplayed() {
        waitForPageLoad();
        return isDisplayed(pageTitle);
    }

    public String getPageTitleText() { return getText(pageTitle); }

    public String getTotalAmount() {
        return getText(totalAmountDisplay);
    }

    public String getTotalLabel() {
        return getText(totalLabel);
    }

    public int getPaymentRowCount() {
        return getElementCount(paymentRows);
    }

    public boolean isEmptyStateDisplayed() {
        return isDisplayed(emptyState);
    }

    public boolean isPaymentTableDisplayed() {
        return isDisplayed(paymentTable);
    }

    public List<String> getPaymentStatuses() {
        return findElements(statusChips).stream()
                .map(e -> e.getText().trim())
                .toList();
    }

    // --- Actions ---

    public void completeFirstPayment() {
        List<WebElement> buttons = findElements(completeButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
            waitUtils.shortWait(1000);
        }
    }

    public int getCompleteButtonCount() {
        return getElementCount(completeButtons);
    }

    public boolean hasCompleteButton() {
        return getElementCount(completeButtons) > 0;
    }

    public String getSnackbarText() { return getSnackbarMessage(); }
}
