package com.lengyel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

import static java.lang.Thread.sleep;

public class AppliAppWithAddsPage extends Page {

    protected static final Logger logger = LogManager.getLogger(AppliAppWithAddsPage.class);

    @FindBy(css="th#amount")
    WebElement amountHeader;

    By flashSaleAdds = By.xpath("//img[contains(@src, '.gif')]");


    public AppliAppWithAddsPage(WebDriver driver) {
        super("AppliAppWithAddsPage", "https://demo.applitools.com/hackathonAppV2.html?showAd=true", driver);

        PageFactory.initElements(driver, this);
        driver().get(url());

        if(isDisplayed() == false) {
            throw new RuntimeException("AppliAppWithAddsPage is not displayed!");
        }
    }


    public void checkPageLoaded() {

        driverWait().until(ExpectedConditions.visibilityOf(amountHeader));
    }

    //only valid for App page with Adds
    public void checkAdds(int numOfAddsToBeFound) {
        logger.info("***TEST Dynamic content test ***");

        List<String> addsImagesFound = findAddsImages();
        int numOfAddsFound = addsImagesFound.size();

        //bassed on V2 - confirm num of displayed adds is correct => add should be a gif???
        assertActions().verifyEquals(numOfAddsFound, numOfAddsToBeFound, "Number of adds is the same as expected");
        //based on V2 - check found images are adds => containg flashSale in the name???
        for(String add : addsImagesFound) {
            if(!add.contains("flashSale")) {
                logger.info("New add added to the page: " + add);
            }
        }
    }

    private List<String> findAddsImages() {
        checkPageLoaded();
        List<WebElement> addsE = getAllMatchingElements(flashSaleAdds);
        List<String> addsImgs = new ArrayList<>();

        addsE.forEach( addE -> addsImgs.add(addE.getAttribute("src")));
        return addsImgs;
    }

}
