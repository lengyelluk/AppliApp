package com.lengyel.tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.lengyel.pages.AppliAppPage;
import com.lengyel.pages.AppliLoginPage;
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

    @BeforeClass
    public static void setBatchInfo() {
        batchInfo = new BatchInfo("Hackathon Tests");
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

    @Test
    public void hackathonTests() {
        eyes.open(driver, "Applitools App", "Hackathon", new RectangleSize(1080, 900));
        appliLoginPage = new AppliLoginPage(driver);
        eyes.checkWindow("Login Window");

        appliLoginPage.loginInvalid("", "");
        eyes.checkWindow("Empty Input - Invalid Login");

        appliLoginPage.loginInvalid("validUserName", "");
        eyes.checkWindow("Invalid Password - Invalid Login");

        appliLoginPage.loginInvalid("", "validPassword");
        eyes.checkWindow("Invalid username - Invalid Login");

        appliAppPage = appliLoginPage.loginValid("validUserName", "validLogin");
        eyes.checkWindow("Valid Login");

        eyes.closeAsync();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
