package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.LoggerUtils;

public class HomePage extends CommonPageClass{
    private final String HOME_PAGE_URL = getPageUrl(PageUrlPaths.HOME_PAGE);

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final By discoverMenu = By.xpath("(//span[normalize-space()='Discover'])[1]");
    private final By economicCalendarLink = By.xpath("(//div[normalize-space()='Economic Calendar'])[1]");
    private final By liveEducationLink = By.xpath("(//div[normalize-space()='Live Education'])[1]");
    private final By acceptCookies = By.xpath("//button[contains(., 'Accept All')]");


    public HomePage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + HOME_PAGE_URL + ")");
        openUrl(HOME_PAGE_URL);
        if (bVerify) {
            verifyHomePage();
        }
        return this;
    }

    public HomePage verifyHomePage() {
        LoggerUtils.log.debug("verifyHomePage()");
        waitForUrlChange(HOME_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORTER);
        return this;
    }

    public void clickAcceptCookies() {
        clickOnWebElement(getWebElement(acceptCookies));
    }

    public void clickDiscoverMenu() {
        clickOnWebElement(getWebElement(discoverMenu));
    }

    public void clickEconomicCalendar() {
        clickOnWebElement(getWebElement(economicCalendarLink));
    }

    public void clickLiveEducation() {
        clickOnWebElement(getWebElement(liveEducationLink));
    }

}
