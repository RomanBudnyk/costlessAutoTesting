package costlessTest.helpTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

public class HelpClass {
    public static AppiumDriver<MobileElement> d;
    public static WebDriverWait wait;
    public static TouchAction action;
    private DesiredCapabilities capabilities;

    @BeforeClass
    public void setup(){
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Nexus 5X API 27");
        capabilities.setCapability("udid", "emulator-5554");
//        capabilities.setCapability("udid", "192.168.143.101:5555");
//        capabilities.setCapability("avd", "Nexus_5X_API_25"); //launch emulator

//        capabilities.setCapability("deviceName", "Xiaomi Redmi Note 4");
//        capabilities.setCapability("udid", "f1fb6aec0803");

//        capabilities.setCapability("deviceName", "Leeco");
//        capabilities.setCapability("udid", "7c4da21a");

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "7.1.1");
        capabilities.setCapability("skipUnlock", "true");
        capabilities.setCapability("appPackage", "com.sannacode.android.costless");
        capabilities.setCapability("appActivity", "com.sannacode.android.costless.screen.splash.SplashActivity");
        capabilities.setCapability("noReset", "false");
        capabilities.setCapability("unicodeKeyboard", "true"); //do not open keyboard
        capabilities.setCapability("autoGrantPermissions", "true"); //give all permissions in the app as
        capabilities.setCapability("automationName", "UiAutomator2");

    }

    @BeforeMethod
    public void start() throws MalformedURLException {
        d = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(d, 7, 100);
        action = new TouchAction(d);
    }

    @AfterMethod
    public void close() {
        d.quit();
    }
}
