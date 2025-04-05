package data;

import org.testng.Assert;
import utils.PropertiesUtils;

import java.util.Properties;

public final class Time {

    private static final String sTimeoutsFile = "timeouts_" + PropertiesUtils.getEnvironment() + ".txt";
    private static final String sTimeoutsPath = "\\timeouts\\" + sTimeoutsFile;

    private static final Properties timeouts = PropertiesUtils.loadPropertiesFile(sTimeoutsPath);

    private static int getTimeout(String sTimeoutName) {
        String sText = timeouts.getProperty(sTimeoutName);
        Assert.assertNotNull(sText, "Timeout '" + sTimeoutName + "' doesn't exist in file " + sTimeoutsFile + "!");
        return Integer.parseInt(sText);
    }

    public static final int IMPLICIT_TIMEOUT = getTimeout("IMPLICIT_TIMEOUT");
    public static final int PAGE_LOAD_TIMEOUT = getTimeout("PAGE_LOAD_TIMEOUT");
    public static final int ASYNC_SCRIPT_TIMEOUT = getTimeout("ASYNC_SCRIPT_TIMEOUT");
    public static final int TIME_DEMONSTRATION = getTimeout("TIME_DEMONSTRATION");
    public static final int TIME_SHORTER = getTimeout("TIME_SHORTER");


}
