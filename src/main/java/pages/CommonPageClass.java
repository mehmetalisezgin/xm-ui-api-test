package pages;


import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;

public class CommonPageClass extends BasePageClass{


    public CommonPageClass(WebDriver driver) {
        super(driver);
    }

    public static String getPageTitle() {
        LoggerUtils.log.debug("getPageTitle()");
        return driver.getTitle(); // Fetches <title> from the browser
    }

    public boolean isExpectedTitleDisplayed(String expectedTitle) {
        LoggerUtils.log.debug("isExpectedTitleDisplayed()");
        return getPageTitle().equalsIgnoreCase(expectedTitle);
    }

}
