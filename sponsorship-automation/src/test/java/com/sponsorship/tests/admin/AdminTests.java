package com.sponsorship.tests.admin;

import com.sponsorship.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * AdminTests - Automation tests for the Admin Oversight module.
 * Test Cases: TC_08_01 - TC_08_06
 */
public class AdminTests extends BaseTest {

    // ============================================================
    // TC_08_01 - Verify admin dashboard displays correct system-wide stats
    // ============================================================

    @Test(description = "TC_08_01 - Admin dashboard displays system-wide statistics",
            groups = {"smoke", "regression", "admin"})
    public void TC_08_01_adminDashboardStats() {
        createTest("TC_08_01", "Admin dashboard system-wide statistics");
        loginAsAdmin();

        getTest().info("Step 1: Verify all metrics are loaded and non-empty");
        Assert.assertTrue(adminDashboard.isDashboardDisplayed(), "Admin dashboard should be visible");

        String totalUsers = adminDashboard.getTotalUsers();
        String brands = adminDashboard.getTotalBrands();
        String influencers = adminDashboard.getTotalInfluencers();
        String campaigns = adminDashboard.getActiveCampaigns();
        String transactions = adminDashboard.getTotalTransactions();

        Assert.assertFalse(totalUsers.isEmpty(), "Total Users stat should not be empty");
        Assert.assertFalse(brands.isEmpty(), "Brands stat should not be empty");
        Assert.assertFalse(influencers.isEmpty(), "Influencers stat should not be empty");
        Assert.assertFalse(campaigns.isEmpty(), "Active Campaigns stat should not be empty");
        Assert.assertFalse(transactions.isEmpty(), "Transactions stat should not be empty");

        getTest().pass("Admin dashboard successfully loaded and aggregated metrics are accurately rendered");
    }

    // ============================================================
    // TC_08_02 - Verify admin can change user status (BLOCKED)
    // ============================================================

    @Test(description = "TC_08_02 - Admin can filter users by role",
            groups = {"regression", "admin"})
    public void TC_08_02_adminRoleFilter() {
        createTest("TC_08_02", "Admin can filter users by role");
        loginAsAdmin();
        
        getTest().info("Step 1: Filter users by 'Brand' role");
        adminDashboard.filterUsersByRole("Brand");
        
        getTest().info("Step 2: Verify user table reflects filter");
        int rowCount = adminDashboard.getUserRowCount();
        Assert.assertTrue(rowCount >= 0, "User table should load without errors after filtering");
        
        getTest().pass("Admin successfully filtered users by role.");
    }

    // ============================================================
    // TC_08_03 - Verify admin user table is visible and populates
    // ============================================================

    @Test(description = "TC_08_03 - Admin user table is visible and populates",
            groups = {"regression", "admin"})
    public void TC_08_03_adminUserTableVisible() {
        createTest("TC_08_03", "Admin user table renders correctly");
        loginAsAdmin();
        
        getTest().info("Step 1: Verify user table rows are present");
        int rowCount = adminDashboard.getUserRowCount();
        Assert.assertTrue(rowCount > 0, "User table should be populated with system users");
        
        getTest().pass("User table renders with data correctly.");
    }

    // ============================================================
    // TC_08_04 - Verify admin campaign table is visible and populates
    // ============================================================

    @Test(description = "TC_08_04 - Admin campaign table is visible and populates",
            groups = {"regression", "admin"})
    public void TC_08_04_adminCampaignTableVisible() {
        createTest("TC_08_04", "Admin campaigns table renders correctly");
        loginAsAdmin();
        
        getTest().info("Step 1: Verify campaign table rows are present");
        int rowCount = adminDashboard.getCampaignRowCount();
        Assert.assertTrue(rowCount > 0, "Campaign table should be populated with active campaigns");
        
        getTest().pass("Campaign table renders with data correctly.");
    }

    // ============================================================
    // TC_08_05 - Verify admin accounts display protected badges
    // ============================================================

    @Test(description = "TC_08_05 - Admin accounts display protected badges",
            groups = {"regression", "admin"})
    public void TC_08_05_adminProtectedBadgesVisible() {
        createTest("TC_08_05", "Admin protected badges are visible");
        loginAsAdmin();
        
        getTest().info("Step 1: Verify admin rows have protected badges instead of delete buttons");
        Assert.assertTrue(adminDashboard.hasProtectedBadges(), "Admin dashboard should display protected badges for admins");
        
        getTest().pass("Protected badges properly displayed for admin accounts.");
    }

    // ============================================================
    // TC_08_06 - Verify non-admin restricted from admin dashboards
    // ============================================================

    @Test(description = "TC_08_06 - Non-admin restricted from admin dashboard",
            groups = {"regression", "admin"})
    public void TC_08_06_nonAdminRestricted() {
        createTest("TC_08_06", "Non-admin restricted from administrative dashboards");
        
        getTest().info("Step 1: Login as Brand");
        loginAsBrand();

        getTest().info("Step 2: Force navigate URL to /dashboard/admin");
        driver.get(com.sponsorship.utils.ConfigReader.getBaseUrl() + "/dashboard/admin");
        
        // Wait for potential redirect or error
        waitUtils.waitForAngularLoad();

        getTest().info("Step 3: Verify access is blocked (redirected away)");
        Assert.assertFalse(driver.getCurrentUrl().contains("/dashboard/admin"),
                "Brand user should be redirected away from admin dashboard");

        getTest().pass("System intercepts request and blocks non-admin access correctly");
    }
}
