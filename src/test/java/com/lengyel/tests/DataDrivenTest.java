package com.lengyel.tests;

import com.lengyel.actions.AssertActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.lengyel.pages.AppliLoginPage;

import java.util.concurrent.TimeUnit;


public class DataDrivenTest  {

    WebDriver driver;
    AppliLoginPage appliLoginPage;

    @BeforeTest
    public void setup () {
        System.setProperty("webdriver.chrome.driver", "src/main/java/com/lengyel/vendor/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void dataDrivenTest () {
        appliLoginPage = new AppliLoginPage(driver);
        appliLoginPage.loginInvalid("", "");
        appliLoginPage.loginInvalid("validUserName", "");
        appliLoginPage.loginInvalid("", "validPassword");
        appliLoginPage.loginValid("validUserName", "validPassword");
        AssertActions.checkForVerificationErrors();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
