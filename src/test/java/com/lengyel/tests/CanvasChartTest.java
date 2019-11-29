package com.lengyel.tests;

import com.lengyel.actions.AssertActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.lengyel.pages.AppliAppPage;
import com.lengyel.pages.AppliChartPage;
import com.lengyel.pages.AppliLoginPage;
import java.util.concurrent.TimeUnit;

public class CanvasChartTest {

    WebDriver driver;
    AppliLoginPage appliLoginPage;
    AppliAppPage appliAppPage;
    AppliChartPage appliChartPage;

    @BeforeTest
    public void setup () {
        System.setProperty("webdriver.chrome.driver", "src/main/java/com/lengyel/vendor/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    //3 test
    @Test
    public void canvasChartTest() {
        appliLoginPage = new AppliLoginPage(driver);
        appliAppPage = appliLoginPage.loginValid("validUserName", "validPassword");
        appliChartPage = appliAppPage.goToCompareExpenses();
        appliChartPage.checkChartData2017_2018();
        appliChartPage.goToChartData2019();
        AssertActions.checkForVerificationErrors();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
