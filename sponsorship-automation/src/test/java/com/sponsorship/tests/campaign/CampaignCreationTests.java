package com.sponsorship.tests.campaign;

import com.sponsorship.base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * CampaignCreationTests - Automation tests for the Campaign Creation module.
 * Test Cases: TC_02_01, TC_02_02, TC_02_03, TC_02_06
 */
public class CampaignCreationTests extends BaseTest {

    private String generateUniqueCampaignName() {
        return "Summer Promo " + System.currentTimeMillis();
    }

    private String getFutureDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    private String getPastDate(int daysToSubtract) {
        return LocalDate.now().minusDays(daysToSubtract).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    // ============================================================
    // TC_02_01 - Verify brand can create campaign with valid data
    // ============================================================

    @Test(description = "TC_02_01 - Create campaign with valid budget, timeline, and platform",
            groups = {"smoke", "regression", "campaign"})
    public void TC_02_01_createCampaignWithValidData() {
        createTest("TC_02_01", "Create campaign with valid data");
        
        getTest().info("Step 1: Login as Brand");
        loginAsBrand();

        getTest().info("Step 2: Navigate to Create Campaign");
        brandDashboard.clickCreateCampaign();
        Assert.assertTrue(campaignForm.getFormTitle().contains("Create"),
                "Should be on Create Campaign page");

        getTest().info("Step 3: Fill form with valid data");
        String campaignName = generateUniqueCampaignName();
        campaignForm.createCampaign(
                campaignName,
                "This is a valid campaign description longer than 50 characters for the Summer Promo. Looking for tech influencers.",
                "Instagram",
                "1500",
                getFutureDate(1),
                getFutureDate(30),
                "Must have 10k+ followers"
        );

        getTest().info("Step 4: Verify successful creation snackbar");
        String snackbarText = campaignForm.getSnackbarText();
        Assert.assertTrue(snackbarText.toLowerCase().contains("success"),
                "Success message should be displayed. Got: " + snackbarText);

        getTest().info("Step 5: Verify redirect to Campaign List and campaign is present");
        Assert.assertTrue(driver.getCurrentUrl().contains("/campaigns") || 
                          driver.getCurrentUrl().contains("/dashboard"),
                "Should redirect away from creation form");

        getTest().pass("Campaign created successfully: " + campaignName);
    }

    // ============================================================
    // TC_02_02 - Verify campaign creation fails if name length is invalid
    // ============================================================

    @Test(description = "TC_02_02 - Campaign creation fails if name is less than 3 characters",
            groups = {"regression", "campaign"})
    public void TC_02_02_createCampaignNameTooShort() {
        createTest("TC_02_02", "Campaign creation validation - Name too short");
        loginAsBrand();
        brandDashboard.clickCreateCampaign();

        getTest().info("Step 1: Fill form with name < 3 characters");
        campaignForm.enterName("Ab"); // 2 characters
        
        getTest().info("Step 2: Trigger validation");
        campaignForm.enterDescription(""); // Trigger blur

        getTest().info("Step 3: Verify validation error");
        Assert.assertTrue(campaignForm.isNameMinLengthErrorDisplayed(),
                "Validation error for short name should be displayed");
        Assert.assertFalse(campaignForm.isSubmitButtonEnabled(),
                "Submit button should be disabled");

        getTest().pass("Validation correctly blocks short campaign names");
    }

    // ============================================================
    // TC_02_03 - Verify campaign creation fails if start date is in past (BLOCKED)
    // ============================================================

    @Test(description = "TC_02_03 - BLOCKED: Start date in past validation not implemented",
            groups = {"regression", "campaign"}, enabled = true)
    public void TC_02_03_createCampaignPastStartDate() {
        createTest("TC_02_03", "BLOCKED: Past start date validation");
        getTest().warning("Feature NOT IMPLEMENTED: The frontend form does not restrict past dates.");
        getTest().info("A user can select yesterday from the date picker and no error is shown.");
        getTest().skip("BLOCKED - Feature Not Implemented: Past date validation");

        throw new SkipException(
                "BLOCKED - TC_02_03: Start date past validation is not implemented in the current build");
    }

    // ============================================================
    // TC_02_06 - Verify field length constraint on Description
    // ============================================================

    @Test(description = "TC_02_06 - Campaign creation fails if description is too short",
            groups = {"regression", "campaign"})
    public void TC_02_06_campaignDescriptionTooShort() {
        createTest("TC_02_06", "Campaign creation validation - Description too short");
        loginAsBrand();
        brandDashboard.clickCreateCampaign();

        getTest().info("Step 1: Enter description < 10 characters (UI enforces 10 currently, RTM says 50)");
        campaignForm.enterDescription("Short");
        
        getTest().info("Step 2: Trigger validation");
        campaignForm.enterName(""); // Trigger blur

        getTest().info("Step 3: Verify validation error");
        Assert.assertTrue(campaignForm.isDescMinLengthErrorDisplayed(),
                "Validation error for short description should be displayed");
        Assert.assertFalse(campaignForm.isSubmitButtonEnabled(),
                "Submit button should be disabled");

        getTest().pass("Validation correctly blocks short campaign descriptions");
    }
}
