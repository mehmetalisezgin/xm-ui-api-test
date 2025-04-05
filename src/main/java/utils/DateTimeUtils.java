package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateTimeUtils {

    public static void wait(int seconds) {
        LoggerUtils.log.trace("wait(" + seconds + ")");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            LoggerUtils.log.warn("InterruptedException in Thread.sleep(" + seconds + "). Message: " + e.getMessage());
        }
    }

    public static Date getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getDateTime(long milliSeconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliSeconds);
        return cal.getTime();
    }

    public static String getFormattedDateTime(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String getFormattedCurrentDateTime(String pattern) {
        Date date = getCurrentDateTime();
        return getFormattedDateTime(date, pattern);
    }

    public static String getDateTimeStamp() {
        return getFormattedCurrentDateTime("yyMMddHHmmss");
    }

    public static Date getParsedDateTime(String sDateTime, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
             date = dateFormat.parse(sDateTime);
        } catch (ParseException e) {
            Assert.fail("Cannot parse date '" + sDateTime + "' using pattern '" + pattern + "'! Message: " + e.getMessage());
        }
        return date;
    }

    public static boolean compareDateTime(Date date1, Date date2, int threshold) {
        long diff = (date2.getTime() - date1.getTime())/1000;
        LoggerUtils.log.debug("Comparing dates (Date 1: " + date1 + ", Date 2: " + date2 + "). Difference: " + diff + " seconds. Threshold: " + threshold + ".");
        return Math.abs(diff) <= threshold;
    }

    public static long getBrowserTimeZoneOffset(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long offset = (long) js.executeScript("var browserDateTime = new Date(); return browserDateTime.getTimezoneOffset();");
        return offset;
    }

    public static String getBrowserTimeZone(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String sBrowserDateTimeString = getBrowserDateTimeString(driver);
        LoggerUtils.log.info("Browser DateTime String: " + sBrowserDateTimeString);
        String sBrowserTimeZone = sBrowserDateTimeString.substring(sBrowserDateTimeString.lastIndexOf(" ") + 1);
        LoggerUtils.log.info("Browser TimeZone String: " + sBrowserTimeZone);
        return sBrowserTimeZone;
    }

    private static String getBrowserDateTimeString(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String sBrowserDateTime = (String) js.executeScript("var browserDateTime = new Date().getTime(); return Intl.DateTimeFormat('en-GB', { dateStyle: 'full', timeStyle: 'long' }).format(browserDateTime);");
        sBrowserDateTime = sBrowserDateTime.replace(" at ", " ");
        return sBrowserDateTime;
    }

    public static Date getBrowserDateTime(WebDriver driver) {
        String sBrowserDateTime = getBrowserDateTimeString(driver);
        String sPattern = "EEEE d MMMM yyyy HH:mm:ss z";
        return getParsedDateTime(sBrowserDateTime, sPattern);
    }

    public static Map<String, LocalDate> getExpectedDateRange(String label) {
        LocalDate expectedStart;
        LocalDate expectedEnd;

        switch (label.toLowerCase()) {
            case "today" -> expectedStart = expectedEnd = LocalDate.now();
            case "tomorrow" -> expectedStart = expectedEnd = LocalDate.now().plusDays(1);
            case "this week" -> {
                expectedStart = LocalDate.now().with(DayOfWeek.MONDAY);
                expectedEnd = expectedStart.plusDays(6);
            }
            case "next week" -> {
                expectedStart = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(1);
                expectedEnd = expectedStart.plusDays(6);
            }
            case "this month" -> {
                LocalDate now = LocalDate.now();
                expectedStart = now.withDayOfMonth(1);
                expectedEnd = now.withDayOfMonth(now.lengthOfMonth());
            }
            case "next month" -> {
                LocalDate now = LocalDate.now().plusMonths(1);
                expectedStart = now.withDayOfMonth(1);
                expectedEnd = now.withDayOfMonth(now.lengthOfMonth());
            }
            default -> throw new IllegalArgumentException("Unsupported label: " + label);
        }

        Map<String, LocalDate> result = new HashMap<>();
        result.put("start", expectedStart);
        result.put("end", expectedEnd);
        return result;
    }


}
