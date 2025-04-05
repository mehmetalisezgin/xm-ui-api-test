package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JavaScriptUtils {
    private static final String sDragAndDropJavaScriptFilePath = "javascript/drag-and-drop_simulator.js";
    private static final String sDragAndDropJavaScriptFilePath2 = "javascript/drag-and-drop_helper.js";


    private static String loadJavaScriptFile(String sFilePath) {
        String sJavaScript;
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
        Assert.assertNotNull(inputStream, "Cannot read JavaScript file '" + sFilePath + "'");
        StringBuilder sJavaScriptStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                sJavaScriptStringBuilder.append(line).append(" ");
            }
        } catch (IOException e) {
            Assert.fail("Cannot read JavaScript file '" + sFilePath + "'. Message: " + e.getMessage());
        }
        sJavaScript = sJavaScriptStringBuilder.toString();

        return sJavaScript;
    }

    public static void simulateDragAndDrop(WebDriver driver, String sourceLocator, String destinationLocator) {
        LoggerUtils.log.trace("simulateDragAndDrop()");
        String sJavaScript = loadJavaScriptFile(sDragAndDropJavaScriptFilePath);
        sJavaScript = sJavaScript + "DndSimulator.simulate('" + sourceLocator + "', '" + destinationLocator + "')";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(sJavaScript);
    }

    public static void simulateDragAndDrop2(WebDriver driver, String sourceLocator, String destinationLocator) {
        LoggerUtils.log.trace("simulateDragAndDrop2()");
        String sJavaScript = loadJavaScriptFile(sDragAndDropJavaScriptFilePath2);
        sJavaScript = sJavaScript + "$('" + sourceLocator + "').simulateDragDrop({ dropTarget: '" + destinationLocator + "'});";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(sJavaScript);
    }

    public static void scrollDown(WebDriver driver, int pixels) {
        LoggerUtils.log.trace("scrollDown() with native JS");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixels + ");");
    }

    public static void scrollToPageCenter(WebDriver driver) {
        LoggerUtils.log.trace("scrollToPageCenter()");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo({ top: document.body.scrollHeight / 2, behavior: 'smooth' });");
    }

    public static void scrollDownByPercentage(WebDriver driver) {
        LoggerUtils.log.trace("scrollDownByPercentage(" + 30 + ")");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, document.body.scrollHeight * arguments[0]);", 50);
    }
}
