package com.lengyel.tests;

import com.lengyel.actions.AssertActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.lengyel.pages.AppliAppWithAddsPage;
import com.lengyel.pages.AppliLoginWithAddsPage;

import java.util.concurrent.TimeUnit;

public class DynamicContentTest {

    WebDriver driver;
    AppliLoginWithAddsPage appliLoginWithAddsPage;
    AppliAppWithAddsPage appliAppWithAddsPage;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/java/com/lengyel/vendor/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    //3 test
    @Test
    public void dynamicContentTest() {
        int numOfAddsToBeFound = 2;

        AppliLoginWithAddsPage appliLoginWithAddsPage = new AppliLoginWithAddsPage(driver);
        appliAppWithAddsPage = appliLoginWithAddsPage.loginValid("validUserName", "validPassword");
        appliAppWithAddsPage.checkAdds(numOfAddsToBeFound);
        AssertActions.checkForVerificationErrors();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}

