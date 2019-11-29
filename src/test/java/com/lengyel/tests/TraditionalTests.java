package com.lengyel.tests;

import com.lengyel.actions.AssertActions;
import com.lengyel.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TraditionalTests {

    WebDriver driver;
    AppliLoginPage appliLoginPage;
    AppliAppPage appliAppPage;
    AppliChartPage appliChartPage;
    AppliLoginWithAddsPage appliLoginWithAddsPage;
    AppliAppWithAddsPage appliAppWithAddsPage;

    @BeforeTest
    public void setup () {
        System.setProperty("webdriver.chrome.driver", "src/main/java/com/lengyel/vendor/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    //1 test Login Page UI Elements
    @Test
    public void checkLoginPage () {
        appliLoginPage = new AppliLoginPage(driver);
        appliLoginPage.checkInitialLoginPage();
        AssertActions.checkForVerificationErrors();
    }

    //2 data-driver test
    @Test
    public void dataDrivenTest () {
        appliLoginPage = new AppliLoginPage(driver);
        appliLoginPage.loginInvalid("", "");
        appliLoginPage.loginInvalid("validUserName", "");
        appliLoginPage.loginInvalid("", "validPassword");
        appliLoginPage.loginValid("validUserName", "validPassword");
        AssertActions.checkForVerificationErrors();
    }

    //3 table sort test
    public void sortTest() {
        appliLoginPage = new AppliLoginPage(driver);
        appliAppPage = appliLoginPage.loginValid("validUserName", "validPassword");
        appliAppPage.checkSorting();
        AssertActions.checkForVerificationErrors();
    }

    //4 canvas chart test
    @Test
    public void canvasChartTest() {
        appliLoginPage = new AppliLoginPage(driver);
        appliAppPage = appliLoginPage.loginValid("validUserName", "validPassword");
        appliChartPage = appliAppPage.goToCompareExpenses();
        appliChartPage.checkChartData2017_2018();
        appliChartPage.goToChartData2019();
        AssertActions.checkForVerificationErrors();
    }

    @Test
    public void dynamicContentTest() {
        int numOfAddsToBeFound = 2;

        appliLoginWithAddsPage = new AppliLoginWithAddsPage(driver);
        appliAppWithAddsPage = appliLoginWithAddsPage.loginValid("validUserName", "validPassword");
        appliAppWithAddsPage.checkAdds(numOfAddsToBeFound);
        AssertActions.checkForVerificationErrors();
    }


    @AfterTest
    public void tearDown() {
        driver.close();
    }
}
