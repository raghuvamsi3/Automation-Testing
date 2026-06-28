package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * CampaignListPage - Page Object for the Campaign List (/campaigns).
 * Test Cases: TC_03_01, TC_03_02
 */
public class CampaignListPage extends BasePage {

    private final By pageTitle = By.cssSelector(".page-header h1");
    private final By createCampaignButton = By.xpath("//button[contains(.,'Create Campaign')]");
    private final By searchInput = By.cssSelector(".filters input[matInput]");
    private final By platformFilter = By.xpath("//mat-label[text()='Platform']/ancestor::mat-form-field//mat-select");
    private final By statusFilter = By.xpath("//mat-label[text()='Status']/ancestor::mat-form-field//mat-select");
    private final By applicationFilter = By.xpath("//mat-label[text()='Application Status']/ancestor::mat-form-field//mat-select");
    private final By clearFiltersButton = By.xpath("//button[contains(.,'Clear Filters')]");
    private final By campaignCards = By.cssSelector(".campaign-card");
    private final By campaignNames = By.cssSelector(".campaign-card mat-card-title");
    private final By emptyState = By.cssSelector(".empty-state");
    private final By appliedBadges = By.cssSelector(".applied-badge");
    private final By loadingSpinner = By.tagName("mat-spinner");

    // Campaign card action buttons
    private final By editButtons = By.xpath("//button[contains(.,'Edit')]");
    private final By deleteButtons = By.xpath("//button[contains(.,'Delete')]");
    private final By applyNowButtons = By.xpath("//button[contains(.,'Apply Now')]");
    private final By alreadyAppliedButtons = By.xpath("//button[contains(.,'Already Applied')]");

    public CampaignListPage(WebDriver driver) {
        super(driver);
    }

    // --- Navigation ---

    public void navigateToCampaignList() {
        navigateTo(driver.getCurrentUrl().split("//")[0] + "//sponsorship-front.netlify.app/campaigns");
        waitForPageLoad();
    }

    // --- Search & Filter ---

    public void searchByName(String name) {
        type(searchInput, name);
        waitUtils.shortWait(500);
    }

    public void filterByPlatform(String platform) {
        selectMatOption(platformFilter, platform);
        waitUtils.shortWait(500);
    }

    public void filterByStatus(String status) {
        selectMatOption(statusFilter, status);
        waitUtils.shortWait(500);
    }

    public void filterByApplicationStatus(String status) {
        selectMatOption(applicationFilter, status);
        waitUtils.shortWait(500);
    }

    public void clearFilters() {
        click(clearFiltersButton);
        waitUtils.shortWait(500);
    }

    // --- Campaign Card Actions ---

    public int getCampaignCount() {
        return getElementCount(campaignCards);
    }

    public List<String> getCampaignNames() {
        return findElements(campaignNames).stream()
                .map(e -> e.getText().trim())
                .toList();
    }

    public void clickCampaignCard(int index) {
        List<WebElement> cards = findElements(campaignCards);
        if (index < cards.size()) {
            cards.get(index).click();
            waitForPageLoad();
        }
    }

    public void clickFirstUnappliedCampaign() {
        List<WebElement> applyBtns = findElements(applyNowButtons);
        if (!applyBtns.isEmpty()) {
            applyBtns.get(0).click();
            waitForPageLoad();
            return;
        }
        
        // If all are applied, just click the first card and let the test logic handle it
        List<WebElement> cards = findElements(campaignCards);
        if (!cards.isEmpty()) {
            cards.get(0).click();
            waitForPageLoad();
        }
    }

    public void clickCampaignByName(String name) {
        By card = By.xpath("//mat-card-title[contains(text(),'" + name + "')]/ancestor::mat-card");
        click(card);
        waitForPageLoad();
    }

    public void clickCreateCampaign() {
        click(createCampaignButton);
        waitForPageLoad();
    }

    public void clickEditCampaign(int index) {
        List<WebElement> buttons = findElements(editButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
            waitForPageLoad();
        }
    }

    public void clickDeleteCampaign(int index) {
        List<WebElement> buttons = findElements(deleteButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
            waitUtils.shortWait(500);
        }
    }

    // --- Verifications ---

    public boolean isEmptyStateDisplayed() {
        return isDisplayed(emptyState);
    }

    public boolean isCreateCampaignButtonDisplayed() {
        return isDisplayed(createCampaignButton);
    }

    public int getAppliedBadgeCount() {
        return getElementCount(appliedBadges);
    }

    public String getPageTitleText() {
        return getText(pageTitle);
    }
}
