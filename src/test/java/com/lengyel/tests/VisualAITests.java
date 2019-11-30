package com.lengyel.tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.lengyel.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class VisualAITests {

    private EyesRunner eyesRunner;
    private Eyes eyes;
    private static BatchInfo batchInfo;
    private WebDriver driver;
    AppliLoginPage appliLoginPage;
    AppliAppPage appliAppPage;
    AppliChartPage appliChartPage;
    AppliLoginWithAddsPage appliLoginWithAddsPage;
    AppliAppWithAddsPage appliAppWithAddsPage;

    @BeforeClass
    public static void setBatchInfo() {
        batchInfo = new BatchInfo("Hackathon");
    }

    @BeforeTest
    public void setUp() {
        eyesRunner = new ClassicRunner();
        eyes = new Eyes(eyesRunner);
        eyes.setApiKey("xI1AO102yxfcIUR4u698D9ov26BPHbbqb7PHdHss4NCFSY110");
        eyes.setBatch(batchInfo);

        System.setProperty("webdriver.chrome.driver", "src/main/java/com/lengyel/vendor/chromedriver");
        driver = new ChromeDriver();
    }

    @Test(priority = 1)
    public void hackathonTest1() {
        eyes.open(driver, "Applitools App", "Login Page UI Elements Test", new RectangleSize(1080, 900));
        appliLoginPage = new AppliLoginPage(driver);
        eyes.checkWindow("Login Window");

        eyes.closeAsync();
    }

    @Test(priority = 2)
    public void hackathonTest2() {
        eyes.open(driver, "Applitools App", "Data Driven Test", new RectangleSize(1080, 900));

        AppliLoginPage appliLoginPage = new AppliLoginPage(driver);
        appliLoginPage.loginInvalid("", "");
        eyes.checkWindow("Invalid Login1");
        appliLoginPage.refreshPage();

        appliLoginPage.loginInvalid("validUserName", "");
        eyes.checkWindow("Invalid Login2");
        appliLoginPage.refreshPage();

        appliLoginPage.loginInvalid("", "validPassword");
        eyes.checkWindow("Invalid Login3");
        appliLoginPage.refreshPage();

        appliAppPage = appliLoginPage.loginValid("validUserName", "validLogin");
        eyes.checkWindow("Valid Login");

        eyes.closeAsync();
    }

    @Test(priority = 3)
    public void hackathonTest3() {
        eyes.open(driver, "Applitools App", "Table Sort Test", new RectangleSize(1080, 900));
        eyes.setForceFullPageScreenshot(true);

        appliLoginPage = new AppliLoginPage(driver);
        appliAppPage = appliLoginPage.loginValid("validUserName", "validPassword");
        appliAppPage.checkSorting();
        eyes.checkWindow("Table sorting by price ascending");
        eyes.closeAsync();
    }

    @Test(priority = 4)
    public void hackathonTest4() {
        eyes.open(driver, "Applitools App", "Canvas Chart Test", new RectangleSize(1080, 900));

        appliLoginPage = new AppliLoginPage(driver);
        appliAppPage = appliLoginPage.loginValid("validUserName", "validPassword");
        appliChartPage = appliAppPage.goToCompareExpenses();
        eyes.checkWindow("Chart data 2017 & 2018");
        appliChartPage.goToChartData2019();
        eyes.checkWindow("Chart data 2017-2019");
        eyes.closeAsync();

    }

    @Test(priority = 5)
    public void hackathonTest5() {
        eyes.open(driver, "Applitools App", "Dynamic Content Test", new RectangleSize(1080, 900));

        appliLoginWithAddsPage = new AppliLoginWithAddsPage(driver);
        appliAppWithAddsPage = appliLoginWithAddsPage.loginValid("validUserName", "validPassword");
        eyes.checkWindow("Adds");


        eyes.closeAsync();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
