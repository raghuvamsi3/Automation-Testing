package com.sponsorship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * NavbarComponent - Page Object for the shared navigation bar.
 * Present on all authenticated pages.
 */
public class NavbarComponent extends BasePage {

    // --- Locators ---
    private final By navbar = By.tagName("mat-toolbar");
    private final By logoText = By.cssSelector(".logo-text");
    private final By campaignsButton = By.cssSelector("button[routerLink='/campaigns']");
    private final By requestsButton = By.cssSelector("button[routerLink='/sponsorship-requests']");
    private final By paymentsButton = By.cssSelector("button[routerLink='/payments']");
    private final By ratingsButton = By.cssSelector("button[routerLink='/ratings']");
    private final By notificationsButton = By.cssSelector("button[routerLink='/notifications']");
    private final By notificationBadge = By.cssSelector(".notification-btn .mat-badge-content");
    private final By userMenuButton = By.cssSelector(".user-menu-btn");
    private final By userName = By.cssSelector(".user-name");
    private final By dashboardMenuItem = By.xpath("//button[contains(.,'Dashboard')]");
    private final By changePasswordMenuItem = By.xpath("//button[contains(.,'Change Password')]");
    private final By logoutMenuItem = By.xpath("//button[contains(.,'Logout')]");
    private final By userMenu = By.cssSelector("div.mat-mdc-menu-panel");

    public NavbarComponent(WebDriver driver) {
        super(driver);
    }

    // --- Navigation Actions ---

    /**
     * Clicks on the Campaigns nav button.
     */
    public void goToCampaigns() {
        click(campaignsButton);
        waitForPageLoad();
    }

    /**
     * Clicks on the Requests nav button.
     */
    public void goToRequests() {
        click(requestsButton);
        waitForPageLoad();
    }

    /**
     * Clicks on the Payments nav button.
     */
    public void goToPayments() {
        click(paymentsButton);
        waitForPageLoad();
    }

    /**
     * Clicks on the Ratings nav button.
     */
    public void goToRatings() {
        click(ratingsButton);
        waitForPageLoad();
    }

    /**
     * Clicks on the Notifications button.
     */
    public void goToNotifications() {
        click(notificationsButton);
        waitForPageLoad();
    }

    /**
     * Clicks the logo to go to dashboard.
     */
    public void goToDashboard() {
        click(By.cssSelector(".logo"));
        waitForPageLoad();
    }

    // --- User Menu Actions ---

    /**
     * Opens the user dropdown menu.
     */
    public void openUserMenu() {
        click(userMenuButton);
        waitUtils.shortWait(300);
    }

    /**
     * Clicks Dashboard in the user menu.
     */
    public void clickDashboardFromMenu() {
        openUserMenu();
        click(dashboardMenuItem);
        waitForPageLoad();
    }

    /**
     * Clicks Change Password in the user menu.
     */
    public void clickChangePassword() {
        openUserMenu();
        click(changePasswordMenuItem);
        waitUtils.shortWait(500);
    }

    /**
     * Logs out via the user menu.
     */
    public void logout() {
        openUserMenu();
        click(logoutMenuItem);
        waitUtils.waitForUrlContains("/login");
    }

    // --- Verifications ---

    /**
     * Checks if the navbar is displayed.
     */
    public boolean isNavbarDisplayed() {
        return isDisplayed(navbar);
    }

    /**
     * Gets the displayed user name from the navbar.
     */
    public String getUserName() {
        return getText(userName);
    }

    /**
     * Gets the unread notification count.
     */
    public int getNotificationCount() {
        try {
            if (isDisplayed(notificationBadge)) {
                String count = getText(notificationBadge);
                return Integer.parseInt(count);
            }
        } catch (Exception e) {
            // Badge not displayed or no unread notifications
        }
        return 0;
    }

    /**
     * Checks if the Campaigns button is visible (hidden for Admin).
     */
    public boolean isCampaignsButtonVisible() {
        return isDisplayed(campaignsButton);
    }

    /**
     * Checks if the Requests button is visible (hidden for Admin).
     */
    public boolean isRequestsButtonVisible() {
        return isDisplayed(requestsButton);
    }

    /**
     * Checks if the Payments button is visible (hidden for Admin).
     */
    public boolean isPaymentsButtonVisible() {
        return isDisplayed(paymentsButton);
    }
}
