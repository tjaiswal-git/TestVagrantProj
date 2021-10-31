package com.testvagrant.project.pageobjects;

import com.testvagrant.project.endpoints.APIConstants;
import com.testvagrant.project.utils.TestBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

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
            waitforElement(60,accpetedBtn);
            accpetedBtn.click();
            searchLocation.clear();
            searchLocation.sendKeys("Bhilwara");
            searchLocation.sendKeys(Keys.ENTER);

            getCityTemp();

            Thread.sleep(5000);
            driver.navigate().back();
            driver.navigate().forward();
            int attempt = 1;
            while (attempt < 4)
            {
                try
                {
                    waitforElement(60,tempElement);
                    finalTemp = tempElement.getText();
                    finalTemp = tempRegex(finalTemp);
                    attempt++;
                    break;
                }
               catch (Exception e)
               {
                   getCityTemp();
                   e.printStackTrace();
               }
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return finalTemp;
    }

    private void getCityTemp() {
        int attampt = 1;
        while (attampt<4) {
            try
            {
                waitforElement(60,stCityElement);
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript(ARGUMENTS_0_CLICK, stCityElement);
                break;
            }
            catch (StaleElementReferenceException | NoSuchElementException e)
            {
                logger.info("stale element reference exception came times " + attampt);
            }
            attampt++;
        }
    }

    public boolean tempCompare(String tempAPI,String tempWeb) {
        boolean tempStatus = false;
        double actualTemp = Math.abs(Double.parseDouble(tempAPI)) - Math.abs(Double.parseDouble(tempWeb));
        logger.info("Difference between 2 sources temperature's range is "+actualTemp +" Degree Celsius");
        if (( actualTemp <= Math.abs(Double.parseDouble(APIConstants.TEMP_VARIANCE))))
        {
            tempStatus = true;
        }

        return tempStatus;

    }
    private String tempRegex(String temp)
    {
        System.out.println(temp);
        if(temp == null || temp.equals(""))
        {
            getCityTemp();
        }
        char[] allchars = temp.toCharArray();
        String finalDigit = "";
        for(int chindex = 0; chindex < allchars.length; chindex++)
        {
            if(Character.isDigit(allchars[chindex]))
            {
                finalDigit += allchars[chindex];
            }
        }
        return finalDigit;
    }

}
