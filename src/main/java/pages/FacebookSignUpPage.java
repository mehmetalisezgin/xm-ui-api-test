package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.LoggerUtils;

import java.util.List;

public class FacebookSignUpPage extends CommonPageClass {

    public FacebookSignUpPage(WebDriver driver) {
        super(driver);
    }

    private final By firstNameInput = By.name("firstname");
    private final By lastNameInput = By.name("lastname");
    private final By emailInput = By.name("reg_email__");
    private final By emailConfirmationInput = By.name("reg_email_confirmation__");
    private final By passwordInput = By.name("reg_passwd__");
    private final By daySelect = By.id("day");
    private final By monthSelect = By.id("month");
    private final By yearSelect = By.id("year");
    private final By genderRadioButtons = By.name("sex");
    private final By signUpButton = By.name("websubmit");

    public void enterFirstName(String firstName) {
        LoggerUtils.log.debug("enterFirstName(" + firstName + ")");
        WebElement element = getWebElement(firstNameInput, Time.TIME_SHORTER);
        clearAndTypeTextToWebElement(element, firstName);
    }

    public void enterLastName(String lastName) {
        LoggerUtils.log.debug("enterLastName(" + lastName + ")");
        WebElement element = getWebElement(lastNameInput, Time.TIME_SHORTER);
        clearAndTypeTextToWebElement(element, lastName);
    }

    public void enterEmail(String email) {
        LoggerUtils.log.debug("enterEmail(" + email + ")");
        WebElement element = getWebElement(emailInput, Time.TIME_SHORTER);
        clearAndTypeTextToWebElement(element, email);
    }

    public void confirmEmail(String email) {
        LoggerUtils.log.debug("confirmEmail(" + email + ")");
        WebElement element = getWebElement(emailConfirmationInput, Time.TIME_SHORTER);
        clearAndTypeTextToWebElement(element, email);
    }

    public void enterPassword(String password) {
        LoggerUtils.log.debug("enterPassword(***)");
        WebElement element = getWebElement(passwordInput, Time.TIME_SHORTER);
        clearAndTypeTextToWebElement(element, password);
    }

    public void selectBirthDate(String day, String month, String year) {
        LoggerUtils.log.debug("selectBirthDate(" + day + "," + month + "," + year + ")");
        selectDropDownListOptionByText(getWebElement(daySelect, Time.TIME_SHORTER), day);
        selectDropDownListOptionByText(getWebElement(monthSelect, Time.TIME_SHORTER), month);
        selectDropDownListOptionByText(getWebElement(yearSelect, Time.TIME_SHORTER), year);
    }

    public void selectGender(String genderText) {
        LoggerUtils.log.debug("selectGender(" + genderText + ")");
        List<WebElement> radios = getWebElements(genderRadioButtons);
        for (WebElement radio : radios) {
            String label = radio.findElement(By.xpath("..//label")) != null ? radio.findElement(By.xpath("..//label")).getText() : "";
            if (label.equalsIgnoreCase(genderText)) {
                clickOnWebElement(radio);
                break;
            }
        }
    }

    public void submitForm() {
        LoggerUtils.log.debug("submitForm()");
        WebElement button = getWebElement(signUpButton, Time.TIME_SHORTER);
        clickOnWebElement(button);
    }
}
