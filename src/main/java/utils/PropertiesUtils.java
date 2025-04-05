package utils;

import org.testng.Assert;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private static final String sPropertiesPath = "common.properties";
    private static final Properties properties = loadPropertiesFile();

    public static Properties loadPropertiesFile(String sFilePath){
        Properties properties = new Properties();
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            Assert.fail("Cannot load " + sFilePath + " file! Message: " + e.getMessage());
        }
        return properties;
    }

    public static Properties loadPropertiesFile(){
        return loadPropertiesFile(sPropertiesPath);
    }

    public static String getProperty(String sProperty) {
        String sResult = properties.getProperty(sProperty);
        Assert.assertNotNull(sResult, "Cannot find property '" + sProperty + "' in " + sPropertiesPath + " file!");
        return sResult;
    }
    public static String getBrowser() {
        String sBrowser = System.getProperty("browser");
        if(sBrowser == null) {
            sBrowser = getProperty("browser").toLowerCase();
        }
        return sBrowser.toLowerCase();
    }

    public static String getEnvironment() {
        String sEnvironment = System.getProperty("environment");
        if (sEnvironment == null) {
            sEnvironment = getProperty("environment");
        }
        return sEnvironment;
    }
    public static String getLocale() {
        String sLocale = System.getProperty("locale");
        if (sLocale == null) {
            sLocale = getProperty("locale");
        }
        return sLocale;
    }

    public static boolean getTakeScreenshots() {
        String sTakeScreenshots = getProperty("takeScreenshots");
        sTakeScreenshots = sTakeScreenshots.toLowerCase();
        if (!(sTakeScreenshots.equals("true") || sTakeScreenshots.equals("false"))) {
            Assert.fail("Cannot convert 'TakeScreenshots' property value '" + sTakeScreenshots + "' to boolean value!");
        }
        return Boolean.parseBoolean(sTakeScreenshots);
    }
    private static String getLocalBaseUrl() {
        return getProperty("localBaseUrl");
    }
    private static String getTestBaseUrl() {
        return getProperty("testBaseUrl");
    }
    private static String getStageBaseUrl() {
        return getProperty("stageBaseUrl");
    }
    private static String getProdBaseUrl() {
        return getProperty("prodBaseUrl");
    }

    public static String getBaseUrl(String sEnvironment) {
        String sBaseUrl = null;
        switch (sEnvironment) {
            case "local" : {
                sBaseUrl = getLocalBaseUrl();
                break;
            }
            case "test" : {
                sBaseUrl = getTestBaseUrl();
                break;
            }
            case "stage" : {
                sBaseUrl = getStageBaseUrl();
                break;
            }
            case "prod" : {
                sBaseUrl = getProdBaseUrl();
                break;
            }
            default : {
                Assert.fail("Cannot get BaseUrl! Environment '" + sEnvironment + "' is not recognized!");
            }
        }
        return sBaseUrl;
    }

    public static String getBaseUrl() {
        String sEnvironment = getEnvironment().toLowerCase();
        return getBaseUrl(sEnvironment);
    }

    public static boolean getRemote() {
        String sRemote = System.getProperty("remote");
        if (sRemote == null) {
            sRemote = getProperty("remote");
        }
        sRemote = sRemote.toLowerCase();
        if (!(sRemote.equals("true") || sRemote.equals("false"))) {
            Assert.fail("Cannot convert 'Remote' property value '" + sRemote + "' to boolean value!");
        }
        return Boolean.parseBoolean(sRemote);
    }

    public static boolean getHeadless() {
        String sHeadless = System.getProperty("headless");
        if (sHeadless == null) {
            sHeadless = getProperty("headless");
        }
        sHeadless = sHeadless.toLowerCase();
        if (!(sHeadless.equals("true") || sHeadless.equals("false"))) {
            Assert.fail("Cannot convert 'Headless' property value '" + sHeadless + "' to boolean value!");
        }
        return Boolean.parseBoolean(sHeadless);
    }

    public static String getHubUrl() {
        return getProperty("hubUrl");
    }

    public static String getScreenshotsFolder() {
        return getProperty("screenshotsFolder");
    }

    public static String getImagesFolder() {
        return getProperty("imagesFolder");
    }
}
