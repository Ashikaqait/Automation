package Automation;

import Utilities.YamlReader;
import Utilities.spec_reader;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Keywords {
    WebDriver driver;
    WebDriverWait wait;
    public Keywords(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(this.driver, 60);
    }


    static spec_reader element = new spec_reader();
    static YamlReader yaml = new YamlReader();

    Float temp1, temp2;
    Boolean flag = null;
    Long variance;
    String msg;

    public Boolean compareTemp(String city, Long variance)
    {
        System.out.println("City:"+city);
        temp1 = GetCurrentTempOfCity(city);
        temp2 = GetCurrentTempOfCityAPI(city);

        if(Math.abs((temp1-temp2))<variance)
        {
            flag = true;
            System.out.println("Comparison of Temperature is a success!!"+flag);
        }
        else
        {
            flag = false;
            System.out.println("Comparison of Temperature is a failure!!"+flag);
        }
        return flag;
    }
    public ArrayList<String> ReadJsonFile() {
        ArrayList<String> cities = new ArrayList<String>();
        String city;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/test/resources/data.json"));

            JSONObject jsonObject = (JSONObject) obj;
            variance = (Long) jsonObject.get("Variance");

            JSONArray cityList = (JSONArray) jsonObject.get("City");

            Iterator<String> iterator = cityList.iterator();
            while (iterator.hasNext()) {
                city = iterator.next().toString();
                cities.add(city);
            }
            System.out.println(cities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }
    public Long ReadJsonFileVariance() {

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/test/resources/data.json"));

            JSONObject jsonObject = (JSONObject) obj;
            variance = (Long) jsonObject.get("Variance");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return variance;
    }
    public String compareTemp(Float temp1, Float temp2)
    {
        if(temp1==temp2)
            return msg="Temperatures are equal";
        else if(temp1>temp2)
            return msg="Temperature on Website is greater than from API";
        else
            return msg="Temperature on API is greater than from Website";

    }
    public Float GetCurrentTempOfCityAPI(String city)
    {
        int statusCode;
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5/";
        RequestSpecification request = RestAssured.given();

        Response response = request.queryParam("q",city)
                .queryParam("appid", yaml.getYamlValue("appid"))
                .queryParam("units","metric")
                .get("/weather");

        statusCode = response.getStatusCode();

        Assert.assertTrue(statusCode==200,"[Assert Failed]: Unable to fetch API response");
        System.out.println("[Assert Passed]: API response fetched");

        Float temp = response.jsonPath().get("main.temp");
        System.out.println("Current Temperature in "+city+": "+temp);

        return temp;
    }
    public Float GetCurrentTempOfCity(String city)
    {
        LaunchURL("url");

        System.out.println("User is entering city in search field :"+city);
        Wait(2);
        wait.until(ExpectedConditions.elementToBeClickable(element.locate(driver,"search_field","")));
        element.locate(driver,"search_field","").sendKeys(city);

        System.out.println("User is selecting city from dropdown");
        element.locate(driver,"dropdown_city",city).click();

        Assert.assertTrue(element.locate(driver,"weather_page",city).isDisplayed(),"[Assert Failed]: User is not redirected to City Weather page");
        System.out.println("[Assert Passed]: User is redirected to City Weather page");

        String temp = element.locate(driver,"current_temp","").getText();
        System.out.println("Current Temperature in "+city+" :"+temp);
        temp = temp.replace("°","");
        return Float.parseFloat(temp);
    }
    public void LaunchURL(String url)
    {
        System.out.println("User is launching URL: "+yaml.getYamlValue(url));
        driver.get(yaml.getYamlValue(url));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*")));
        Wait(1);
    }

    public void VerifyUserIsAbleToClickOnTwitterIcon() {

        System.out.println("User is clicking on Twitter icon");
        element.locate(driver,"twitter_icon","").click();
        System.out.println("User has clicked on Twitter icon");

        Assert.assertTrue(element.locate(driver,"submitbtn","").isDisplayed(),"[Assert Failed]: User is not able to click on Twitter icon");
        System.out.println("[Assert Passed]: User is able to click on Twitter icon");
    }

    public void VerifyUserIsAbleToLoginThroughTwitter() {
        System.out.println("User is entering User Name");
        element.locate(driver,"user","").sendKeys(yaml.getYamlValue("user"));
        System.out.println("User has entered User Name");

        System.out.println("User is entering Password");
        element.locate(driver,"password","").sendKeys(yaml.getYamlValue("password"));
        System.out.println("User has entered Password");

        System.out.println("User is clicking on Submit button");
        element.locate(driver,"submitbtn","").click();
        Reporter.log("User has clicked on Submit button");

        Wait(2);
        Assert.assertTrue(element.locate(driver,"confirmtxt","").isDisplayed(),"[Assert Failed]: User cannot login through Twitter account");
        System.out.println("[Assert Passed]: User can login through twitter account");
    }
       public void Wait(int sec)
       {
           try {
               Thread.sleep( 1000*sec);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
       }

    public void verifyApplicationIsInstalledSuccessfully() {
        Wait(1);
            Assert.assertTrue(element.locate(driver,"google_icon","").isDisplayed(),"[Assert Failed]: Application is not installed!");
            System.out.println("[Assert Passed]: Application is successfully installed, User is present on Login screen.");

    }

    public void verifyTwitterIconIsPresentOnThePage() {
        Wait(1);
        Assert.assertTrue(element.locate(driver,"twitter_icon","").isDisplayed()&&element.locate(driver,"twitter_icon","").isEnabled(),"[Assert Failed]: Twitter icon is not displayed");
        System.out.println("[Assert Passed]: Twitter icon is displayed");
    }
}
