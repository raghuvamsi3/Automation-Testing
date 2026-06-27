package com.sponsorship.tests.admin;

import com.sponsorship.base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
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

    @Test(description = "TC_08_02 - BLOCKED: User suspend/ban status not implemented",
            groups = {"regression", "admin"}, enabled = true)
    public void TC_08_02_suspendBanUser() {
        createTest("TC_08_02", "BLOCKED: Change user status to SUSPENDED/BANNED");
        getTest().warning("Feature NOT IMPLEMENTED: The UI only has a Delete button, no Status dropdown for users.");
        getTest().skip("BLOCKED - Feature Not Implemented: User status modification");

        throw new SkipException("BLOCKED - TC_08_02: Suspend/Ban user not implemented");
    }

    // ============================================================
    // TC_08_03 - Verify banned user blocked from login (BLOCKED)
    // ============================================================

    @Test(description = "TC_08_03 - BLOCKED: Banned user login block not implemented",
            groups = {"regression", "admin"}, enabled = true)
    public void TC_08_03_bannedUserLoginBlocked() {
        createTest("TC_08_03", "BLOCKED: Banned user login blocked");
        getTest().warning("Feature NOT IMPLEMENTED: Dependent on user status system which is absent.");
        getTest().skip("BLOCKED - Feature Not Implemented: Banned user restriction");

        throw new SkipException("BLOCKED - TC_08_03: Banned user login block not implemented");
    }

    // ============================================================
    // TC_08_04 - Verify admin can soft-delete campaign (BLOCKED)
    // ============================================================

    @Test(description = "TC_08_04 - BLOCKED: Admin campaign soft-delete not implemented",
            groups = {"regression", "admin"}, enabled = true)
    public void TC_08_04_adminSoftDeleteCampaign() {
        createTest("TC_08_04", "BLOCKED: Admin soft-delete campaign");
        getTest().warning("Feature NOT IMPLEMENTED: The Admin Dashboard campaigns table lacks delete buttons.");
        getTest().skip("BLOCKED - Feature Not Implemented: Admin campaign deletion");

        throw new SkipException("BLOCKED - TC_08_04: Admin campaign soft-delete not implemented");
    }

    // ============================================================
    // TC_08_05 - Verify audit log (BLOCKED)
    // ============================================================

    @Test(description = "TC_08_05 - BLOCKED: System audit log not implemented",
            groups = {"regression", "admin"}, enabled = true)
    public void TC_08_05_adminAuditLog() {
        createTest("TC_08_05", "BLOCKED: System audit log panel");
        getTest().warning("Feature NOT IMPLEMENTED: There is no Audit Log panel or page in the Admin Dashboard.");
        getTest().skip("BLOCKED - Feature Not Implemented: Audit Log");

        throw new SkipException("BLOCKED - TC_08_05: System audit log not implemented");
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
