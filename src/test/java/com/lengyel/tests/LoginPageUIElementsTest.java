package com.lengyel.tests;

import com.lengyel.actions.AssertActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.lengyel.pages.AppliLoginPage;


import java.util.concurrent.TimeUnit;

public class LoginPageUIElementsTest {


    WebDriver driver;
    AppliLoginPage appliLoginPage;

    @BeforeTest
    public void setup () {
        System.setProperty("webdriver.chrome.driver", "src/main/java/com/lengyel/vendor/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    //1 test
    @Test
    public void checkLoginPage () {
        appliLoginPage = new AppliLoginPage(driver);
        appliLoginPage.checkInitialLoginPage();
        AssertActions.checkForVerificationErrors();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
