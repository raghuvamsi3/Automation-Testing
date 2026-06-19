package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * InfluencerDashboardPage - Page Object for the Influencer Dashboard (/dashboard/influencer).
 */
public class InfluencerDashboardPage extends BasePage {

    private final By pageTitle = By.xpath("//h1[contains(.,'Influencer Dashboard')]");
    private final By browseCampaignsButton = By.xpath("//button[contains(.,'Browse Campaigns')]");
    private final By totalApplicationsStat = By.xpath("//p[contains(text(),'Total Applications')]/preceding-sibling::h2");
    private final By pendingStat = By.xpath("//p[contains(text(),'Pending')]/preceding-sibling::h2");
    private final By acceptedStat = By.xpath("//p[contains(text(),'Accepted')]/preceding-sibling::h2");
    private final By totalEarningsStat = By.xpath("//p[contains(text(),'Total Earnings')]/preceding-sibling::h2");
    private final By averageRatingStat = By.xpath("//p[contains(text(),'Average Rating')]/preceding-sibling::h2");
    private final By activeCampaignCards = By.cssSelector(".campaigns-section .campaign-card");
    private final By myApplicationCards = By.cssSelector(".applications-section .application-card");

    public InfluencerDashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        waitForPageLoad();
        return isDisplayed(pageTitle);
    }

    public String getTotalApplications() { return getText(totalApplicationsStat); }
    public String getPendingApplications() { return getText(pendingStat); }
    public String getAcceptedApplications() { return getText(acceptedStat); }
    public String getTotalEarnings() { return getText(totalEarningsStat); }
    public String getAverageRating() { return getText(averageRatingStat); }

    public int getActiveCampaignCount() { return getElementCount(activeCampaignCards); }
    public int getMyApplicationCount() { return getElementCount(myApplicationCards); }

    public void clickBrowseCampaigns() {
        click(browseCampaignsButton);
        waitForPageLoad();
    }

    public boolean isBrowseCampaignsButtonDisplayed() {
        return isDisplayed(browseCampaignsButton);
    }
}
