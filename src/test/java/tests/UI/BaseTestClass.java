package tests.UI;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import utils.*;

public abstract class BaseTestClass {


    protected WebDriver setUpDriver() {
        WebDriver driver = WebDriverUtils.setUpDriver();
        driver.get(PropertiesUtils.getBaseUrl());
        return driver;
    }

    protected WebDriver setUpDriverWithResolution(String resolutionKey) {
        WebDriver driver = setUpDriver();
        String resolution = PropertiesUtils.getProperty(resolutionKey);
        int width = Integer.parseInt(resolution.split("x")[0]);
        int height = Integer.parseInt(resolution.split("x")[1]);
        WebDriverUtils.setBrowserWindowSize(driver, width, height);
        return driver;
    }

    protected WebDriver setUpMaxResolution() {
        return setUpDriverWithResolution("screen.resolution.max");
    }
    protected WebDriver setUp1024x768Resolution() {
        return setUpDriverWithResolution("screen.resolution.medium");
    }
    protected WebDriver setUp800x600Resolution() {
        return setUpDriverWithResolution("screen.resolution.small");
    }

    protected void quitDriver(WebDriver driver) {
        WebDriverUtils.quitDriver(driver);
    }

    protected void tearDown(WebDriver driver, ITestResult testResult) {
        String sTestName = testResult.getTestClass().getName();
        String sFileName = sTestName + "_" + DateTimeUtils.getDateTimeStamp();
        try {
            testResult.getTestContext().removeAttribute(sTestName + ".driver");
            if (testResult.getStatus() == ITestResult.FAILURE && PropertiesUtils.getTakeScreenshots()) {
                ScreenShotUtils.takeScreenShot(driver, sFileName);
            }
        } catch (AssertionError | Exception e) {
            LoggerUtils.log.error("Exception occurred in tearDown(" + sTestName + ")! Message: " + e.getMessage());
        }
        finally {
            quitDriver(driver);
        }
    }




}
