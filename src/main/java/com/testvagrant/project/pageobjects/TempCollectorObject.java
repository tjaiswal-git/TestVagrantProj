package com.testvagrant.project.pageobjects;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testvagrant.project.utils.TestBase;

public class TempCollectorObject extends TestBase
{

    public static final Logger logger = Logger.getLogger(TempCollectorObject.class
            .getName());
    private static final String ARGUMENTS_0_CLICK = "arguments[0].click()";

    public TempCollectorObject(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@placeholder='Search Location']")
    WebElement searchLocation;

    @FindBy(xpath = "//div[@class='banner-button policy-accept']")
    WebElement accpetedBtn;

    @FindBy(xpath = "//div[@class='content-module']//a[1]")
    WebElement stCityElement;

    @FindBy(xpath = "//div[@id='dismiss-button']")
    WebElement dismissBtn;

    @FindBy(xpath = "(//div[@class='temp'])[1]")
    WebElement tempElement;

    public String getTempFromUI()
    {
        String finalTemp = "";
        try
        {
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(accpetedBtn));
            accpetedBtn.click();
            searchLocation.clear();
            searchLocation.sendKeys("Bhilwara");
            searchLocation.sendKeys(Keys.ENTER);

            int attampt = 0;
            while (attampt<4) {
                try {
                    System.out.println("clicked "+attampt);
                    wait.until(ExpectedConditions.visibilityOf(stCityElement));
                    JavascriptExecutor jse = (JavascriptExecutor)driver;
                    jse.executeScript(ARGUMENTS_0_CLICK, stCityElement);
                    break;
                } catch (StaleElementReferenceException | NoSuchElementException e) {
                    logger.info("stale element reference exception came times " + attampt);
                }
                attampt++;
            }

            Thread.sleep(5000);
            driver.navigate().back();
            driver.navigate().forward();
            finalTemp = extractTempFromWebPage();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return finalTemp;
    }

    public boolean tempCompare(String tempAPI,String tempWeb) {
        boolean tempStatus = false;
        int specifiedTempRange = 50;
        int actualTemp = (int) (Double.parseDouble(tempAPI) - Double.parseDouble(tempWeb));
        logger.info("Difference between 2 sources temperature's range is "+actualTemp +" degree");
        if (( actualTemp <= specifiedTempRange))
        {
            tempStatus = true;
        }

        return tempStatus;

    }
    private String extractTempFromWebPage()
    {
        String temp = tempElement.getText();
        System.out.println(temp);

        char[] allchars = temp.toCharArray();
        String finalDigit = "";
        for(int i = 0; i < allchars.length; i++)
        {
            if(Character.isDigit(allchars[i]))
            {
                finalDigit += allchars[i];
            }
        }
        logger.info("temperature from web app is "+finalDigit +" Degree");
        return finalDigit;
    }

}
