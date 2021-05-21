package Automation;
import Utilities.config_reader;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;


public class Mobile {
    AndroidDriver driver;
    Keywords pages;
    config_reader config = new config_reader();
    @BeforeClass
    public void setUp() throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("BROWSER_NAME", "");
        capabilities.setCapability("VERSION", config.getPropValues("platformVersion"));
        capabilities.setCapability("deviceName",config.getPropValues("deviceName"));
        capabilities.setCapability("udid",config.getPropValues("udid"));
        capabilities.setCapability("platformName",config.getPropValues("platformName"));
        capabilities.setCapability("deviceOrientation", "portrait");
        capabilities.setCapability("app", config.getPropValues("appPath"));
        capabilities.setCapability("appPackage", "tv.game");
        capabilities.setCapability("appActivity",".MainActivity");
        capabilities.setCapability("automationName", "uiautomator2");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        pages = new Keywords(driver);
    }

    @Test(priority = 0)
    public void VerifyApplicationIsInstalled() {
        pages.verifyApplicationIsInstalledSuccessfully();
    }
    @Test(priority = 1)
    public void VerifyTwitterIconIsDisplayedAndClickable()
    {
        pages.verifyTwitterIconIsPresentOnThePage();
    }
    @Test(priority = 2)
    public void VerifyUserIsAbleToLoginThroughTwitterAccount()
    {
        pages.VerifyUserIsAbleToClickOnTwitterIcon();
        pages.VerifyUserIsAbleToLoginThroughTwitter(); // Login not verified due to confirmation code sent to Gmail account
    }
   // @Test(priority = 3)
    public void VerifyUserIsAbleToAccessLoginPage()
    {
        Reporter.log("Login not verified due to confirmation code sent to Gmail account");
    }
    @AfterClass
    public void teardown(){
        driver.quit();
    }
}