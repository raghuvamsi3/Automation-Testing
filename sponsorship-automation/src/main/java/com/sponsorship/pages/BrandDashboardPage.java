package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * BrandDashboardPage - Page Object for the Brand Dashboard (/dashboard/brand).
 */
public class BrandDashboardPage extends BasePage {

    // --- Locators ---
    private final By pageTitle = By.xpath("//h1[contains(.,'Brand Dashboard')]");
    private final By createCampaignButton = By.xpath("//button[contains(.,'Create Campaign')]");
    private final By totalCampaignsStat = By.xpath("//p[contains(text(),'Total Campaigns')]/preceding-sibling::h2");
    private final By activeStat = By.xpath("//p[contains(text(),'Active')]/preceding-sibling::h2");
    private final By completedStat = By.xpath("//p[contains(text(),'Completed')]/preceding-sibling::h2");
    private final By pendingRequestsStat = By.xpath("//p[contains(text(),'Pending Requests')]/preceding-sibling::h2");
    private final By totalSpendingStat = By.xpath("//p[contains(text(),'Total Spending')]/preceding-sibling::h2");
    private final By campaignCards = By.cssSelector(".campaign-card");
    private final By statusFilter = By.cssSelector(".status-filter mat-select");
    private final By loadingSpinner = By.tagName("mat-spinner");
    private final By errorMessage = By.cssSelector(".error-message");
    private final By recentApplications = By.cssSelector(".application-card");

    public BrandDashboardPage(WebDriver driver) {
        super(driver);
    }

    // --- Verifications ---

    public boolean isDashboardDisplayed() {
        waitForPageLoad();
        return isDisplayed(pageTitle);
    }

    public String getTotalCampaigns() {
        return getText(totalCampaignsStat);
    }

    public String getActiveCampaigns() {
        return getText(activeStat);
    }

    public String getCompletedCampaigns() {
        return getText(completedStat);
    }

    public String getPendingRequests() {
        return getText(pendingRequestsStat);
    }

    public String getTotalSpending() {
        return getText(totalSpendingStat);
    }

    public int getCampaignCardCount() {
        return getElementCount(campaignCards);
    }

    public int getRecentApplicationCount() {
        return getElementCount(recentApplications);
    }

    // --- Actions ---

    public void clickCreateCampaign() {
        click(createCampaignButton);
        try {
            waitUtils.waitForUrlContains("/campaigns/new");
        } catch (org.openqa.selenium.TimeoutException e) {
            // Fallback to javascript click if standard click was intercepted/ineffective
            WebElement btn = waitUtils.waitForPresence(createCampaignButton);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            waitUtils.waitForUrlContains("/campaigns/new");
        }
        waitForPageLoad();
        waitUtils.shortWait(1000); // Give Angular Reactive Forms time to initialize and stabilize
    }

    public void clickCampaignCard(int index) {
        List<WebElement> cards = findElements(campaignCards);
        if (index < cards.size()) {
            cards.get(index).click();
            waitForPageLoad();
        }
    }

    public void filterByStatus(String status) {
        selectMatOption(statusFilter, status);
        waitUtils.shortWait(500);
    }

    public boolean isCreateCampaignButtonDisplayed() {
        return isDisplayed(createCampaignButton);
    }
}
