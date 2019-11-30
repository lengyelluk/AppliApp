package com.lengyel.pages;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.lengyel.actions.AssertActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class Page {

    protected static final Logger logger = LogManager.getLogger(Page.class);

    private WebDriver driver;
    private WebDriverWait driverWait;
    private String url;
    private String title;
    protected AssertActions assertActions;

    public Page(String title, String url, WebDriver driver) {
        this.driver = driver;
        this.driverWait = new WebDriverWait(driver, 2);
        this.assertActions = new AssertActions();
        this.url = url;
        this.title = title;
    }

    protected boolean isDisplayed() {
        boolean result = false;

        try {
            result = driverWait.until(ExpectedConditions.urlContains(url));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    protected boolean isElementPresent(WebElement element) {
        boolean elementPresent = false;

        try {
            driverWait.until(ExpectedConditions.visibilityOf(element));
            elementPresent = true;
        } catch (Exception e) {

        }

        return elementPresent;
    }


    protected String url() {
        return url;
    }

    protected String title() {
        return title;
    }

    protected WebDriver driver() {
        return driver;
    }

    protected WebDriverWait driverWait() {
        return driverWait;
    }

    protected AssertActions assertActions() {
        return assertActions;
    }

    protected List<WebElement> getAllMatchingElements(By by) {
        return driver.findElements(by);
    }
}
