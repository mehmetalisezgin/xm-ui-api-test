package tests.UI;

import data.Time;
import org.openqa.selenium.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.CommonPageClass;
import pages.EconomicCalendarPage;
import pages.HomePage;
import utils.DateTimeUtils;
import utils.JavaScriptUtils;
import utils.LoggerUtils;


public class EconomicCalendarTests extends BaseTestClass {

    private WebDriver driver;
    private final String sTestName = this.getClass().getName();
    HomePage homePage = new HomePage(driver);
    EconomicCalendarPage economicCalendarPage = new EconomicCalendarPage(driver);
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
        homePage.clickEconomicCalendar();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        economicCalendarPage.verifyEconomicCalendarPage();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(driver,result);
    }


    @Test(priority = 1)
    public void testCalendarDateMatchesTodaySelection() {
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        economicCalendarPage.dragSliderThumbByLabel(driver, "today");
        javaScriptUtils.scrollDown(driver, 300);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        economicCalendarPage.verifyCalendarRangeFromSelectedDay(driver,"today");
    }

    @Test(priority = 2)
    public void testCalendarDateMatchesTomorrowSelection() {
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        economicCalendarPage.dragSliderThumbByLabel(driver, "tomorrow");
        javaScriptUtils.scrollDown(driver, 300);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        economicCalendarPage.verifyCalendarRangeFromSelectedDay(driver, "tomorrow");
    }

    @Test(priority = 3)
    public void testCalendarDateMatchesNextWeekSelection() {
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        economicCalendarPage.dragSliderThumbByLabel(driver, "next week");
        javaScriptUtils.scrollDown(driver, 300);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        economicCalendarPage.verifyCalendarRangeFromSelectedDay(driver, "next week");
    }
}
