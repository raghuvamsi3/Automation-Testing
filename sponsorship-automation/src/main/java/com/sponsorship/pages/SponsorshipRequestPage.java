package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * SponsorshipRequestPage - Page Object for Sponsorship Requests (/sponsorship-requests).
 * Test Cases: TC_04_01 – TC_04_06
 */
public class SponsorshipRequestPage extends BasePage {

    private final By pageTitle = By.cssSelector(".page-header h1");
    private final By totalStat = By.xpath("//span[@class='stat-label' and text()='Total']/preceding-sibling::span");
    private final By pendingStat = By.xpath("//span[@class='stat-label' and text()='Pending']/preceding-sibling::span");
    private final By acceptedStat = By.xpath("//span[@class='stat-label' and text()='Accepted']/preceding-sibling::span");
    private final By completedStat = By.xpath("//span[@class='stat-label' and text()='Completed']/preceding-sibling::span");
    private final By requestCards = By.cssSelector(".request-card");
    private final By emptyState = By.cssSelector(".empty-state");

    // Brand action buttons
    private final By acceptButtons = By.xpath("//button[contains(.,'Accept')]");
    private final By rejectButtons = By.xpath("//button[contains(.,'Reject')]");
    private final By approveWorkButtons = By.xpath("//button[contains(.,'Approve Work')]");
    private final By makePaymentButtons = By.xpath("//button[contains(.,'Make Payment')]");
    private final By rateInfluencerButtons = By.xpath("//button[contains(.,'Rate Influencer')]");

    // Influencer action buttons
    private final By submitWorkButtons = By.xpath("//button[contains(.,'Submit Work')]");
    private final By rateBrandButtons = By.xpath("//button[contains(.,'Rate Brand')]");
    private final By waitingForResponseText = By.cssSelector(".waiting-text");

    // Status chips on cards
    private final By statusChips = By.cssSelector(".request-card .status-chip");

    private final By loadingSpinner = By.tagName("mat-spinner");

    public SponsorshipRequestPage(WebDriver driver) {
        super(driver);
    }

    // --- Verifications ---

    public boolean isPageDisplayed() {
        waitForPageLoad();
        return isDisplayed(pageTitle);
    }

    public String getPageTitleText() { return getText(pageTitle); }
    public int getRequestCardCount() { return getElementCount(requestCards); }
    public boolean isEmptyStateDisplayed() { return isDisplayed(emptyState); }

    public String getTotalCount() {
        try { return getText(totalStat); } catch (Exception e) { return "0"; }
    }
    public String getPendingCount() {
        try { return getText(pendingStat); } catch (Exception e) { return "0"; }
    }
    public String getAcceptedCount() {
        try { return getText(acceptedStat); } catch (Exception e) { return "0"; }
    }
    public String getCompletedCount() {
        try { return getText(completedStat); } catch (Exception e) { return "0"; }
    }

    // --- Brand Actions ---

    public void acceptFirstRequest() {
        List<WebElement> buttons = findElements(acceptButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
            waitUtils.shortWait(1000);
        }
    }

    public void rejectFirstRequest() {
        List<WebElement> buttons = findElements(rejectButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
            waitUtils.shortWait(1000);
        }
    }

    public void approveFirstWork() {
        List<WebElement> buttons = findElements(approveWorkButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
            waitUtils.shortWait(1000);
        }
    }

    public void makeFirstPayment() {
        List<WebElement> buttons = findElements(makePaymentButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
            waitUtils.shortWait(1000);
        }
    }

    // --- Influencer Actions ---

    public void submitFirstWork() {
        List<WebElement> buttons = findElements(submitWorkButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
            waitUtils.shortWait(1000);
        }
    }

    public boolean isWaitingForResponseDisplayed() {
        return isDisplayed(waitingForResponseText);
    }

    // --- Common ---

    public List<String> getRequestStatuses() {
        return findElements(statusChips).stream()
                .map(e -> e.getText().trim())
                .toList();
    }

    public boolean hasAcceptButton() { return getElementCount(acceptButtons) > 0; }
    public boolean hasRejectButton() { return getElementCount(rejectButtons) > 0; }
    public boolean hasSubmitWorkButton() { return getElementCount(submitWorkButtons) > 0; }
    public boolean hasApproveWorkButton() { return getElementCount(approveWorkButtons) > 0; }
    public boolean hasMakePaymentButton() { return getElementCount(makePaymentButtons) > 0; }

    public String getSnackbarText() { return getSnackbarMessage(); }
}
