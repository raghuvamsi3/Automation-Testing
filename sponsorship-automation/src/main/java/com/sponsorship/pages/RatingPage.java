package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * RatingPage - Page Object for the Ratings screen (/ratings).
 * Test Cases: TC_06_01 – TC_06_06
 */
public class RatingPage extends BasePage {

    private final By pageTitle = By.cssSelector(".page-header h1");
    private final By averageRatingDisplay = By.cssSelector(".rating-number");
    private final By reviewCount = By.cssSelector(".review-count");
    private final By ratingCards = By.cssSelector(".rating-card");
    private final By filledStars = By.cssSelector(".rating-stars .filled");
    private final By emptyState = By.cssSelector(".empty-state");
    private final By loadingSpinner = By.tagName("mat-spinner");
    private final By summaryCard = By.cssSelector(".summary-card");
    private final By sectionTitle = By.cssSelector(".section-title");
    private final By feedbackTexts = By.cssSelector(".feedback");

    public RatingPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageDisplayed() {
        waitForPageLoad();
        return isDisplayed(pageTitle);
    }

    public String getAverageRating() {
        try { return getText(averageRatingDisplay); } catch (Exception e) { return "0"; }
    }

    public String getReviewCount() {
        try { return getText(reviewCount); } catch (Exception e) { return "0"; }
    }

    public int getRatingCardCount() { return getElementCount(ratingCards); }

    public boolean isEmptyStateDisplayed() { return isDisplayed(emptyState); }

    public boolean isSummaryCardDisplayed() { return isDisplayed(summaryCard); }
}
