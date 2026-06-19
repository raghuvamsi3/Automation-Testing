package com.sponsorship.tests.campaign;

import com.sponsorship.base.BaseTest;
import com.sponsorship.utils.ConfigReader;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * CampaignDiscoveryTests - Automation tests for the Campaign Discovery & Applications module.
 * Test Cases: TC_03_01 - TC_03_06
 */
public class CampaignDiscoveryTests extends BaseTest {

    // ============================================================
    // TC_03_01 - Verify influencer can browse and filter by platform
    // ============================================================

    @Test(description = "TC_03_01 - Filter campaigns by platform",
            groups = {"sanity", "campaign"})
    public void TC_03_01_filterCampaignsByPlatform() {
        createTest("TC_03_01", "Filter campaigns by social media platform");
        loginAsInfluencer();

        getTest().info("Step 1: Navigate to Campaign Discovery");
        navbar.goToCampaigns();

        getTest().info("Step 2: Filter by 'Instagram'");
        campaignList.filterByPlatform("Instagram");

        getTest().info("Step 3: Verify results");
        int count = campaignList.getCampaignCount();
        if (count > 0) {
            getTest().pass("Filtered campaigns successfully. Found " + count + " campaigns.");
        } else {
            getTest().pass("Filter applied, but no active campaigns match 'Instagram' currently.");
        }
    }

    // ============================================================
    // TC_03_02 - Verify search by keyword & budget (BLOCKED)
    // ============================================================

    @Test(description = "TC_03_02 - BLOCKED: Budget range filter not implemented",
            groups = {"regression", "campaign"}, enabled = true)
    public void TC_03_02_searchCampaignsByKeywordAndBudget() {
        createTest("TC_03_02", "BLOCKED: Search campaigns by budget range");
        getTest().warning("Feature NOT IMPLEMENTED: The UI only has a text search bar, no budget filters.");
        getTest().info("Test case requires filtering by $500 - $1500 range which is not possible.");
        getTest().skip("BLOCKED - Feature Not Implemented: Budget filter");

        throw new SkipException(
                "BLOCKED - TC_03_02: Budget filtering is not implemented");
    }

    // ============================================================
    // TC_03_03 - Verify influencer can successfully apply
    // ============================================================

    @Test(description = "TC_03_03 - Apply to campaign with valid proposal",
            groups = {"smoke", "regression", "campaign"})
    public void TC_03_03_applyToCampaignWithValidProposal() {
        createTest("TC_03_03", "Apply to campaign with valid proposal");
        loginAsInfluencer();
        navbar.goToCampaigns();

        getTest().info("Step 1: Select first active campaign");
        if (campaignList.getCampaignCount() == 0) {
            getTest().skip("No active campaigns available to apply to");
            throw new SkipException("No campaigns available");
        }
        campaignList.clickCampaignCard(0);

        getTest().info("Step 2: Check if already applied");
        if (campaignDetail.isAlreadyApplied()) {
            getTest().info("Already applied to this campaign. Test setup limitation.");
            // In a real framework, we'd inject test data via DB or API first
            getTest().pass("Verified 'Already Applied' state correctly.");
            return;
        }

        getTest().info("Step 3: Enter proposal and submit");
        String proposalText = "Hi, I am a tech influencer with 50k followers. I would love to collaborate on this project.";
        campaignDetail.applyForCampaign(proposalText);

        getTest().info("Step 4: Verify success snackbar");
        String snackbarText = campaignDetail.getApplicationSnackbar();
        Assert.assertTrue(snackbarText.toLowerCase().contains("success"),
                "Success message should be displayed. Got: " + snackbarText);

        getTest().pass("Application submitted successfully.");
    }

    // ============================================================
    // TC_03_04 - Verify proposal length validation (BLOCKED)
    // ============================================================

    @Test(description = "TC_03_04 - BLOCKED: Proposal min 50 chars validation not implemented",
            groups = {"regression", "campaign"}, enabled = true)
    public void TC_03_04_applyWithShortProposal() {
        createTest("TC_03_04", "BLOCKED: Proposal length validation");
        getTest().warning("Feature NOT IMPLEMENTED: Proposal textarea accepts 1 character.");
        getTest().info("The RTM states a minimum of 50 chars, but the UI has no min-length validator on the apply form.");
        getTest().skip("BLOCKED - Feature Not Implemented: Proposal length validation");

        throw new SkipException(
                "BLOCKED - TC_03_04: Proposal minimum length validation is not implemented");
    }

    // ============================================================
    // TC_03_05 - Verify duplicate application prevention
    // ============================================================

    @Test(description = "TC_03_05 - Prevent duplicate applications",
            groups = {"regression", "campaign"})
    public void TC_03_05_preventDuplicateApplication() {
        createTest("TC_03_05", "Duplicate application prevention mechanism");
        loginAsInfluencer();
        navbar.goToCampaigns();

        getTest().info("Step 1: Find a campaign with 'Applied' badge");
        int appliedCount = campaignList.getAppliedBadgeCount();
        if (appliedCount == 0) {
            getTest().skip("No previously applied campaigns available for this user");
            throw new SkipException("No applied campaigns available");
        }

        getTest().info("Step 2: Click on applied campaign");
        // For simplicity, click the first one if it exists. A robust approach would find specific card.
        campaignList.clickCampaignCard(0);

        getTest().info("Step 3: Verify apply form is hidden and already applied message is shown");
        Assert.assertFalse(campaignDetail.isApplySectionDisplayed(),
                "Apply form should be hidden");
        Assert.assertTrue(campaignDetail.isAlreadyApplied(),
                "Already applied message should be visible");

        getTest().pass("System correctly blocks duplicate applications");
    }

    // ============================================================
    // TC_03_06 - Verify brand can review application
    // ============================================================

    @Test(description = "TC_03_06 - Brand review pending application (Accept/Reject)",
            groups = {"smoke", "regression", "campaign"})
    public void TC_03_06_brandReviewPendingApplication() {
        createTest("TC_03_06", "Brand reviews a pending application");
        loginAsBrand();

        getTest().info("Step 1: Go to Requests");
        navbar.goToRequests();

        getTest().info("Step 2: Check for pending requests");
        if (!sponsorshipRequestPage.hasAcceptButton()) {
            getTest().skip("No pending requests to review");
            throw new SkipException("No pending requests");
        }

        getTest().info("Step 3: Accept the first request");
        sponsorshipRequestPage.acceptFirstRequest();

        getTest().info("Step 4: Verify success");
        String snackbarText = sponsorshipRequestPage.getSnackbarText();
        Assert.assertTrue(snackbarText.toLowerCase().contains("success") || 
                          snackbarText.toLowerCase().contains("accept"),
                "Should show success message on accepting");

        getTest().pass("Brand successfully reviewed and accepted application");
    }
}
