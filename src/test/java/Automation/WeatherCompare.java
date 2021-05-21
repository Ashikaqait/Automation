package Automation;

import Utilities.YamlReader;
import Utilities.config_reader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class WeatherCompare {

    private static WebDriver driver;
    static YamlReader yaml = new YamlReader();
    config_reader y2 = new config_reader();
    Keywords pages;
    String driverpath = "/Users/ashikasrivastava/Desktop/drivers/chromedriver";
    String city;
    Long variance = null;
    Boolean flag;

    @BeforeTest
    public void session_start() throws IOException {
        System.out.println("<---------------Before Test-------------->");
        String browser = y2.getPropValues("browserName");
        if (browser.equals("chrome")) {

            System.setProperty("webdriver.chrome.driver", driverpath);
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }
        pages = new Keywords(driver);
    }
    @Test(priority = 0)
    public void VerifyWeatherSiteIsAccessible()
    {
        pages.LaunchURL("url");

        Assert.assertTrue(driver.getTitle().equalsIgnoreCase(yaml.getYamlValue("title")),"[Assert Failed]: The site is not accessible");
        System.out.println("[Assert Passed]: The site is accessible");
    }
    @Test(priority = 1)
    public void VerifySearchCityOrPostcodeFeatureIsWorkingFine()
    {
        pages.GetCurrentTempOfCity(yaml.getYamlValue("city"));
    }
    @Test(priority = 2)
    public void VerifyWeatherAPIisWorkingFine()
    {
        pages.GetCurrentTempOfCityAPI(yaml.getYamlValue("city"));
    }
    @Test(priority = 3)
    public void CompareTemperatureFromWebAndAPI()
    {
        Float temp1, temp2;
        temp1 = pages.GetCurrentTempOfCity(yaml.getYamlValue("city"));
        temp2 = pages.GetCurrentTempOfCityAPI(yaml.getYamlValue("city"));

        System.out.println(pages.compareTemp(temp1,temp2));
    }
    @Test(priority=4)
    public void CompareTemperatureFromWebAndAPIWithVariance()
    {
        variance = pages.ReadJsonFileVariance();
        ArrayList<String> cities = pages.ReadJsonFile();
        Iterator<String> iterator = cities.iterator();
        while (iterator.hasNext()) {
            city = iterator.next().toString();
            flag = pages.compareTemp(city,variance);
        }
    }
    @AfterTest
    public void session_close() {
        System.out.println("<---------------After Test-------------->");
        driver.quit();

    }

}