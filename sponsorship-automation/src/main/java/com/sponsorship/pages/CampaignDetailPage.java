package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * CampaignDetailPage - Page Object for Campaign Detail View (/campaigns/:id).
 * Test Cases: TC_03_03, TC_03_05, TC_03_06
 */
public class CampaignDetailPage extends BasePage {

    private final By backButton = By.cssSelector(".back-btn");
    private final By campaignName = By.cssSelector(".campaign-detail-card mat-card-title");
    private final By campaignBrand = By.cssSelector(".campaign-detail-card mat-card-subtitle span:first-child");
    private final By campaignPlatform = By.cssSelector(".campaign-detail-card mat-card-subtitle span:nth-child(2)");
    private final By campaignStatus = By.cssSelector(".campaign-detail-card .status-chip");
    private final By budgetValue = By.xpath("//strong[text()='Budget']/following-sibling::p");
    private final By durationValue = By.xpath("//strong[text()='Duration']/following-sibling::p");
    private final By eligibilityValue = By.xpath("//strong[text()='Eligibility']/following-sibling::p");
    private final By descriptionText = By.cssSelector(".description");

    // Influencer apply section
    private final By proposalTextarea = By.cssSelector(".apply-section textarea");
    private final By submitApplicationButton = By.xpath("//button[contains(.,'Submit Application')]");
    private final By alreadyAppliedMessage = By.cssSelector(".applied-message");

    // Brand applications table
    private final By applicationsCard = By.cssSelector(".applications-card");
    private final By applicationRows = By.cssSelector(".applications-card mat-row, .applications-card tr");
    private final By acceptButtons = By.cssSelector("button[matTooltip='Accept']");
    private final By rejectButtons = By.cssSelector("button[matTooltip='Reject']");

    private final By loadingSpinner = By.tagName("mat-spinner");

    public CampaignDetailPage(WebDriver driver) {
        super(driver);
    }

    // --- Verifications ---

    public String getCampaignName() { return getText(campaignName); }
    public String getCampaignStatus() { return getText(campaignStatus); }
    public String getDescription() { return getText(descriptionText); }

    public boolean isAlreadyApplied() {
        return isDisplayed(alreadyAppliedMessage);
    }

    public boolean isApplySectionDisplayed() {
        return isDisplayed(proposalTextarea);
    }

    public boolean isApplicationsTableDisplayed() {
        return isDisplayed(applicationsCard);
    }

    public int getApplicationCount() {
        return getElementCount(applicationRows);
    }

    // --- Influencer Actions ---

    public CampaignDetailPage enterProposal(String proposal) {
        type(proposalTextarea, proposal);
        return this;
    }

    public void submitApplication() {
        click(submitApplicationButton);
        waitUtils.shortWait(1000);
    }

    public void applyForCampaign(String proposal) {
        enterProposal(proposal);
        submitApplication();
    }

    public String getApplicationSnackbar() {
        return getSnackbarMessage();
    }

    // --- Brand Actions ---

    public void acceptApplication(int index) {
        List<WebElement> buttons = findElements(acceptButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
            waitUtils.shortWait(500);
        }
    }

    public void rejectApplication(int index) {
        List<WebElement> buttons = findElements(rejectButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
            waitUtils.shortWait(500);
        }
    }

    // --- Navigation ---

    public void goBack() {
        click(backButton);
        waitForPageLoad();
    }
}
