package com.testvagrant.project.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.testvagrant.project.endpoints.APIConstants;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;

/**
 * This is parent class of all framework setup operation
 * it contains properties file,excel reader,waiting statement,screenShoot etc.
 *
 * @author tjaiswal
 */

public class TestBase
{
    public static WebDriver driver;
    String browser = "chrome";

    public static final Logger logger = Logger.getLogger(TestBase.class.getName());

    @BeforeMethod
    protected void setUpConfiguration()
    {
        RestAssured.baseURI = APIConstants.baseUrl;
    }

    public void init(String URL)
    {
        selectBrowser(browser);

        PropertyConfigurator.configure(System.getProperty("user.dir") + "//Configuration//loggingstatus.properties");
        getURL(URL);
    }


    public Properties getProp()
    {
        //String path1 = System.getProperty("");
        File file = new File(System.getProperty("user.dir") + "//Configuration//projectconfig.properties");
        FileInputStream fileInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(file);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try
        {
            properties.load(fileInputStream);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return properties;
    }

    @Parameters("browser")
    public void selectBrowser(String browser)
    {
        if(browser.equalsIgnoreCase("firefox"))
        {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            logger.info("Firefox has launching...");

        }
        else if(browser.equalsIgnoreCase("chrome"))
        {
            //Map<String, Object> prefs = new HashMap<String, Object>();

            //add key and value to map as follow to switch off browser notification
            //Pass the argument 1 to allow and 2 to block
            //prefs.put("profile.default_content_setting_values.notifications", 2);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);


            // set ExperimentalOption - prefs
            //options.setExperimentalOption("prefs", prefs);
            logger.info("chrome browser has launching..");

        }

    }

    public void getURL(String URL)
    {
        driver.get(URL);
        logger.info("url has lauching..");
        driver.manage().window().maximize();
        logger.info("Now Browser is maximize...");
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        logger.info("Page loading.....");
    }

    /**
     * This method is used for waitforElement has to load in page
     *
     * @param timeOutInSeconds
     * @param element
     */

    public void waitforElement(int timeOutInSeconds, WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    /**
     * This method is used for wait to process some time
     *
     * @param sleepTimeOutInSeconds
     */

    public static void sleepTime(int sleepTimeOutInSeconds)
    {
        try
        {
            Thread.sleep(sleepTimeOutInSeconds*1000);
        }
        catch(InterruptedException e)
        {

            e.printStackTrace();
        }
    }

    public Timeouts ownImplicitWait(long time)
    {
        Timeouts implicitWait = driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        return implicitWait;
    }


    /**
     * This method is used for take a screen shot through webdriver with format of current time instance
     *
     * @param name
     */

    public void quitDriver()
    {
        driver.quit();
    }
}


