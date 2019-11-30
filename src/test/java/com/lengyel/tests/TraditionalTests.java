package com.lengyel.tests;

import com.lengyel.actions.AssertActions;
import com.lengyel.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
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
    @Test(priority = 1)
    public void checkLoginPage () {
        appliLoginPage = new AppliLoginPage(driver);
        appliLoginPage.checkInitialLoginPage();
        AssertActions.checkForVerificationErrors();
    }

    //2 data-driver test
    @Test(priority = 2)
    public void dataDrivenTest () {
        appliLoginPage = new AppliLoginPage(driver);

        appliLoginPage.loginInvalid("", "");
        appliLoginPage.refreshPage();

        appliLoginPage.loginInvalid("validUserName", "");
        appliLoginPage.refreshPage();

        appliLoginPage.loginInvalid("", "validPassword");
        appliLoginPage.refreshPage();

        appliLoginPage.loginValid("validUserName", "validPassword");
        AssertActions.checkForVerificationErrors();
    }

    //3 table sort test
    @Test(priority = 3)
    public void sortTest() {
        appliLoginPage = new AppliLoginPage(driver);
        appliAppPage = appliLoginPage.loginValid("validUserName", "validPassword");
        appliAppPage.checkSorting();
        AssertActions.checkForVerificationErrors();
    }

    //4 canvas chart test
    //silly test - did not have time to find out how to automate canvas
    @Test(priority = 4)
    public void canvasChartTest() {
        appliLoginPage = new AppliLoginPage(driver);
        appliAppPage = appliLoginPage.loginValid("validUserName", "validPassword");
        appliChartPage = appliAppPage.goToCompareExpenses();
        appliChartPage.checkChartData2017_2018();
        appliChartPage.goToChartData2019();
        appliChartPage.checkData2019();
        AssertActions.checkForVerificationErrors();
    }

    @Test(priority = 5)
    public void dynamicContentTest() {
        int numOfAddsToBeFound = 2;

        appliLoginWithAddsPage = new AppliLoginWithAddsPage(driver);
        appliAppWithAddsPage = appliLoginWithAddsPage.loginValid("validUserName", "validPassword");
        appliAppWithAddsPage.checkAdds(numOfAddsToBeFound);
        AssertActions.checkForVerificationErrors();
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }


}
