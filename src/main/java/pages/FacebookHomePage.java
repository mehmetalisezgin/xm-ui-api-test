package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;

public class FacebookHomePage extends CommonPageClass {

    public FacebookHomePage(WebDriver driver) {
        super(driver);
    }

    private final By createAccountButton = By.cssSelector("a[data-testid='open-registration-form-button']");

    /**
     * Opens the given url and waits for the page to be fully loaded.
     * @param url URL of the facebook home page
     * @return FacebookHomePage
     */
    public FacebookHomePage open(String url) {
        LoggerUtils.log.debug("open(" + url + ")");
        openUrl(url);
        waitUntilPageIsReady(Time.TIME_SHORTER);
        return this;
    }

    /**
     * Clicks the "Create new account" button on the home page.
     */
    public void openCreateAccountForm() {
        LoggerUtils.log.debug("openCreateAccountForm()");
        click(createAccountButton, Time.TIME_SHORTER);
    }
}
