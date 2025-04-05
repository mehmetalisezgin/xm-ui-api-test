package pages;

import data.Time;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static data.PageUrlPaths.ECONOMIC_CALENDAR_PAGE;

public class EconomicCalendarPage extends CommonPageClass {

    public EconomicCalendarPage (WebDriver driver) {
        super(driver);
    }

    public EconomicCalendarPage verifyEconomicCalendarPage() {
        LoggerUtils.log.debug("verifyEconomicCalendarPage()");
        waitForUrlChange(ECONOMIC_CALENDAR_PAGE, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORTER);
        return this;
    }

    public EconomicCalendarPage dragSliderThumbByLabel(WebDriver driver, String label) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("iFrameResizer0"));

        WebElement sliderThumb = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".mdc-slider__thumb.mat-mdc-slider-visual-thumb")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", sliderThumb);

        int step = switch (label.toLowerCase()) {
            case "today"       -> 1;
            case "tomorrow"    -> 2;
            case "this week"   -> 3;
            case "next week"   -> 4;
            case "this month"  -> 5;
            case "next month"  -> 6;
            default -> throw new IllegalArgumentException("Invalid label: " + label);
        };
        int pixelOffset = 42 * step;

        Actions actions = new Actions(driver);
        actions.clickAndHold(sliderThumb)
                .moveByOffset(pixelOffset, 0)
                .pause(Duration.ofMillis(200))
                .release()
                .perform();

        driver.switchTo().defaultContent();
        return this;
    }

    public void verifyCalendarRangeFromSelectedDay(WebDriver driver, String label) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("iFrameResizer0"));

        List<WebElement> selectedDateButtons = driver.findElements(
                By.cssSelector("button.mat-calendar-body-cell[aria-pressed='true']")
        );

        if (selectedDateButtons.isEmpty()) {
            throw new AssertionError("No selected dates found in the calendar for label: " + label);
        }

        List<LocalDate> selectedDates = selectedDateButtons.stream()
                .map(el -> el.getAttribute("aria-label").trim())
                .map(labelStr -> LocalDate.parse(labelStr, DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH)))
                .sorted()
                .toList();

        LocalDate actualStart = selectedDates.get(0);
        LocalDate actualEnd = selectedDates.get(selectedDates.size() - 1);

        Map<String, LocalDate> expected = DateTimeUtils.getExpectedDateRange(label);
        LocalDate expectedStart = expected.get("start");
        LocalDate expectedEnd = expected.get("end");

        System.out.println("UI shows selected range: " + actualStart + " → " + actualEnd);
        System.out.println("Expected range: " + expectedStart + " → " + expectedEnd);

        Assert.assertEquals(actualStart, expectedStart, "Start date in selected range is incorrect for label: " + label);
        Assert.assertEquals(actualEnd, expectedEnd, "End date in selected range is incorrect for label: " + label);

        driver.switchTo().defaultContent();
    }


}
