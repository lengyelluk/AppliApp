package com.lengyel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AppliLoginWithAddsPage extends Page {

    protected static final Logger logger = LogManager.getLogger(AppliLoginWithAddsPage.class);

    @FindBy(how = How.CSS, using="button#log-in")
    private WebElement loginButton;

    @FindBy(how = How.CSS, using="input#username")
    WebElement usernameTextField;

    @FindBy(how = How.CSS, using="input#password")
    WebElement passwordTextField;


    public AppliLoginWithAddsPage(WebDriver driver) {
        super("AppliLoginWithAddsPage", "https://demo.applitools.com/hackathonV2.html?showAd=true", driver);
        PageFactory.initElements(driver, this);
        driver().get(url());

        if(isDisplayed() == false) {
            throw new RuntimeException("AppliLoginWithAddsPage is not displayed!");
        }
    }

    public AppliAppWithAddsPage loginValid(String username, String password) {
        usernameTextField.sendKeys(username);
        passwordTextField.sendKeys(password);

        driverWait().until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();
        return new AppliAppWithAddsPage(driver());
    }

}
