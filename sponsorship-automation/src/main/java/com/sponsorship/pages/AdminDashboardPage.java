package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * AdminDashboardPage - Page Object for the Admin Dashboard (/dashboard/admin).
 * Test Cases: TC_08_01 – TC_08_06
 */
public class AdminDashboardPage extends BasePage {

    private final By pageTitle = By.xpath("//h1[contains(.,'Admin Dashboard')]");
    private final By totalUsersStat = By.xpath("//p[contains(text(),'Total Users')]/preceding-sibling::h2");
    private final By brandsStat = By.xpath("//p[contains(text(),'Brands')]/preceding-sibling::h2");
    private final By influencersStat = By.xpath("//p[contains(text(),'Influencers')]/preceding-sibling::h2");
    private final By totalTransactionsStat = By.xpath("//p[contains(text(),'Total Transactions')]/preceding-sibling::h2");
    private final By activeCampaignsStat = By.xpath("//p[contains(text(),'Active Campaigns')]/preceding-sibling::h2");
    private final By roleFilter = By.cssSelector(".filter-dropdown mat-select");
    private final By userTableRows = By.cssSelector(".users-section table tbody tr, .users-section mat-row");
    private final By campaignTableRows = By.cssSelector(".campaigns-section table tbody tr, .campaigns-section mat-row");
    private final By deleteButtons = By.cssSelector("button[color='warn'] mat-icon");
    private final By protectedBadges = By.cssSelector(".admin-badge");
    private final By loadingSpinner = By.tagName("mat-spinner");

    public AdminDashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        waitForPageLoad();
        return isDisplayed(pageTitle);
    }

    public String getTotalUsers() { return getText(totalUsersStat); }
    public String getTotalBrands() { return getText(brandsStat); }
    public String getTotalInfluencers() { return getText(influencersStat); }
    public String getTotalTransactions() { return getText(totalTransactionsStat); }
    public String getActiveCampaigns() { return getText(activeCampaignsStat); }

    /**
     * Filters users by role.
     */
    public void filterUsersByRole(String role) {
        selectMatOption(roleFilter, role);
        waitUtils.shortWait(500);
    }

    /**
     * Gets the count of visible user rows.
     */
    public int getUserRowCount() {
        return getElementCount(userTableRows);
    }

    /**
     * Gets the count of campaign rows.
     */
    public int getCampaignRowCount() {
        return getElementCount(campaignTableRows);
    }

    /**
     * Clicks delete button for a specific user row.
     */
    public void deleteUser(int rowIndex) {
        List<WebElement> deleteIcons = findElements(deleteButtons);
        if (rowIndex < deleteIcons.size()) {
            deleteIcons.get(rowIndex).click();
            waitUtils.shortWait(500);
        }
    }

    /**
     * Checks if any protected badges (for Admin users) are displayed.
     */
    public boolean hasProtectedBadges() {
        return getElementCount(protectedBadges) > 0;
    }

    /**
     * Gets number of delete buttons visible (non-admin users).
     */
    public int getDeleteButtonCount() {
        return getElementCount(deleteButtons);
    }
}
