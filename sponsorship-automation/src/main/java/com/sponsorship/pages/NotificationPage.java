package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * NotificationPage - Page Object for the Notifications screen (/notifications).
 * Test Cases: TC_07_01 – TC_07_06
 */
public class NotificationPage extends BasePage {

    private final By pageTitle = By.cssSelector(".page-header h1");
    private final By markAllAsReadButton = By.xpath("//button[contains(.,'Mark All as Read')]");
    private final By notificationItems = By.tagName("mat-list-item");
    private final By unreadNotifications = By.cssSelector("mat-list-item.unread");
    private final By emptyState = By.cssSelector(".empty-state");
    private final By loadingSpinner = By.tagName("mat-spinner");
    private final By notificationTitles = By.cssSelector("mat-list-item [matListItemTitle]");
    private final By notificationMessages = By.cssSelector("mat-list-item [matListItemLine]");

    public NotificationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageDisplayed() {
        waitForPageLoad();
        return isDisplayed(pageTitle);
    }

    public int getTotalNotificationCount() { return getElementCount(notificationItems); }

    public int getUnreadNotificationCount() { return getElementCount(unreadNotifications); }

    public boolean isEmptyStateDisplayed() { return isDisplayed(emptyState); }

    public boolean isMarkAllAsReadDisplayed() { return isDisplayed(markAllAsReadButton); }

    /**
     * Clicks Mark All as Read button.
     */
    public void markAllAsRead() {
        click(markAllAsReadButton);
        waitUtils.shortWait(1000);
    }

    /**
     * Clicks on the first unread notification to mark it as read.
     */
    public void clickFirstNotification() {
        List<WebElement> items = findElements(notificationItems);
        if (!items.isEmpty()) {
            items.get(0).click();
            waitUtils.shortWait(500);
        }
    }

    /**
     * Clicks on the first unread notification.
     */
    public void clickFirstUnreadNotification() {
        List<WebElement> items = findElements(unreadNotifications);
        if (!items.isEmpty()) {
            items.get(0).click();
            waitUtils.shortWait(500);
        }
    }

    /**
     * Gets all notification titles.
     */
    public List<String> getNotificationTitles() {
        return findElements(notificationTitles).stream()
                .map(e -> e.getText().trim())
                .toList();
    }
}
