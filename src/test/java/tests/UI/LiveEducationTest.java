package tests.UI;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CommonPageClass;
import pages.HomePage;
import pages.LiveEducationPage;
import utils.DateTimeUtils;
import utils.JavaScriptUtils;
import utils.LoggerUtils;

public class LiveEducationTest extends BaseTestClass {

    private WebDriver driver;
    private final String sTestName = this.getClass().getName();
    private boolean bCreated = false;
    HomePage homePage = new HomePage(driver);
    LiveEducationPage liveEducationPage = new LiveEducationPage(driver);
    JavaScriptUtils javaScriptUtils = new JavaScriptUtils();

    @BeforeMethod
    public void setUp(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpMaxResolution();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});
        CommonPageClass commonPageClass = new CommonPageClass(driver);
        commonPageClass.isExpectedTitleDisplayed("Access Global Financial Markets and Start Trading | XM");
        homePage.verifyHomePage();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        homePage.clickAcceptCookies();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        homePage.clickDiscoverMenu();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        homePage.clickLiveEducation();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        liveEducationPage.verifyLiveEducationPage();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(driver, result);
    }


    @Test
    public void test01() {
        // On the Live Education Page
        // No visible play button.
        // No time indicator.
        // No standard video controls (like play/pause or timeline).
        // UI suggests it's scheduled for a future date (April 7 at 08:00 AM)
        // Below it says: "Every weekday at 8:00 AM" → Could mean live-only event.

       /*
       {
    "id": 19664174,
    "metering": {
        "seconds_remaining": 43200,
        "seconds_max": 43200
    },
    "ingest": {
        "rtmps_url": "rtmps:\/\/rtmp-global.cloud.vimeo.com:443\/live",
        "height": null,
        "width": null,
        "status": 0,
        "session_id": null,
        "stream_ended_reason": null,
        "start_time": null,
        "scheduled_start_time": "2025-04-07T06:00:00+00:00"
    },
    "archive": {
        "status": 0
    }
}
        */

    }


}
