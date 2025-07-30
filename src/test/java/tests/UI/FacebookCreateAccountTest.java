package tests.UI;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.FacebookHomePage;
import pages.FacebookSignUpPage;
import utils.DateTimeUtils;
import utils.LoggerUtils;

public class FacebookCreateAccountTest extends BaseTestClass {

    private WebDriver driver;
    private final String sTestName = this.getClass().getName();
    private FacebookHomePage homePage;
    private FacebookSignUpPage signUpPage;

    @BeforeMethod
    public void setUp(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpMaxResolution();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});
        homePage = new FacebookHomePage(driver);
        signUpPage = new FacebookSignUpPage(driver);

        String baseUrl = "https://www.facebook.com/";
        homePage.open(baseUrl);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        homePage.openCreateAccountForm();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(driver, result);
    }

    @Test
    public void testCreateAccountFlow() {
        // Test data
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@example.com";
        String password = "Test@1234";
        String birthDay = "10";
        String birthMonth = "Jun";
        String birthYear = "1990";
        String gender = "Male";

        signUpPage.enterFirstName(firstName);
        signUpPage.enterLastName(lastName);
        signUpPage.enterEmail(email);
        signUpPage.confirmEmail(email);
        signUpPage.enterPassword(password);
        signUpPage.selectBirthDate(birthDay, birthMonth, birthYear);
        signUpPage.selectGender(gender);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        signUpPage.submitForm();
    }
}
