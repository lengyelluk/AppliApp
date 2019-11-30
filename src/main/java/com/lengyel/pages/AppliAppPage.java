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

public class AppliAppPage extends Page {

    protected static final Logger logger = LogManager.getLogger(AppliAppPage.class);

    @FindBy(css="th#amount")
    WebElement amountHeader;

    @FindBy(css="a#showExpensesChart")
    WebElement compareExpenses;


    By flashSaleAdds = By.xpath("//img[contains(@src, '.gif')]");
    By statusValues = By.xpath("//td[@class='nowrap']//span[2]");
    By statusIcons = By.xpath("//td[@class='nowrap']//span[1]");
    By dateValues = By.xpath("//td[not(@class)]//span[1]");
    By timeValues = By.xpath("//td[not(@class)]//span[contains(@class, 'smaller')]");
    By companyLogos = By.xpath("//td[@class='cell-with-media']//img");
    By companyValues = By.xpath("//td[@class='cell-with-media']//span");
    By categoryValues = By.xpath("//td[@class='text-center']//a");
    By amountValues = By.xpath("//tr/td[contains(@class, 'text-right')]/span");

    public AppliAppPage(WebDriver driver) {
        super("AppliAppPage", "https://demo.applitools.com/hackathonAppV2.html", driver);

        PageFactory.initElements(driver, this);
        driver().get(url());

        if(isDisplayed() == false) {
            throw new RuntimeException("AppliAppPage is not displayed!");
        }
    }


    public void checkPageLoaded() {

        driverWait().until(ExpectedConditions.visibilityOf(amountHeader));
    }

    public void changeOrderingByAmount() {

        amountHeader.click();
    }

    public AppliChartPage goToCompareExpenses() {
        compareExpenses.click();
        return new AppliChartPage(driver());
    }

    //only valid for App page with Adds
    public void checkAdds(int numOfAddsToBeFound) {
        List<String> addsImagesFound = findAddsImages();
        int numOfAddsFound = addsImagesFound.size();

        //bassed on V2 - confirm num of displayed adds is correct => add should be a gif???
        assertActions().verifyTrue(numOfAddsFound == numOfAddsToBeFound, "Number of adds is the same as expected");
        //based on V2 - check found images are adds => containg flashSale in the name???
        addsImagesFound.forEach(add -> assertActions().verifyTrue(add.contains("flashSaling"), "Adds contained different images than flashSale"));
    }

    private List<String> findAddsImages() {
        List<WebElement> addsE = getAllMatchingElements(flashSaleAdds);
        List<String> addsImgs = new ArrayList<>();

        addsE.forEach( addE -> addsImgs.add(addE.getAttribute("src")));
        return addsImgs;
    }

    public void checkSorting() {
        Map<Double, List<String>> unsortedTable = getRows();

        TreeMap<Double, List<String>> checkTable = new TreeMap<>();
        checkTable.putAll(unsortedTable);

        changeOrderingByAmount();
        try {
            sleep(1000);
        } catch(Exception e) {

        }
        Map<Double, List<String>> sortedTable = getRows();

        //compare keys only -> custom sorting
        List<Double> checkTableKey = new ArrayList<>(checkTable.keySet());
        List<Double> sortedTableKey = getAmountColumn();

        //column sorting
        assertActions().verifyTrue(sortedTableKey.equals(checkTableKey), "The order of amounts is ascending");
        //integrity
        assertActions().verifyTrue(sortedTable.equals(checkTable), "Ordering by amount did not cause data integrity issues");
    }

    public List<Double> getAmountColumn() {
        List<WebElement> amountsE = getAllMatchingElements(amountValues);
        List<String> amounts = new ArrayList<>();

        for (WebElement e : amountsE) {
            amounts.add(e.getText().trim());
        }

        List<Double> nums = new ArrayList<>();

        for (String v : amounts) {
            StringBuilder sb = new StringBuilder();
            String[] dirtyNum = v.split(" ");
            if (dirtyNum[0].equals("-")) {
                sb.append("-");
            }

            sb.append(dirtyNum[1]);
            String cleanNum = sb.toString().replaceAll(",", "");
            Double i = Double.parseDouble(cleanNum);
            nums.add(i);
        }
        return nums;
    }


    public void sortAmounts() {
        List<WebElement> amounts = driver().findElements(amountValues);
        List<String> values = new ArrayList<>();

        for(WebElement e : amounts) {
            values.add(e.getText().trim());
        }

        List<Double> nums = new ArrayList<>();
        List<Double> numsSorted = new ArrayList<>();

        for(String v : values) {
            StringBuilder sb = new StringBuilder();
            String[] dirtyNum = v.split(" ");
            if(dirtyNum[0].equals("-")) {
                sb.append("-");
            }

            sb.append(dirtyNum[1]);
            String cleanNum = sb.toString().replaceAll(",", "");
            Double i = Double.parseDouble(cleanNum);

            nums.add(i);
            numsSorted.add(i);
        }


        numsSorted.sort(Comparator.naturalOrder());
        numsSorted.sort(Comparator.reverseOrder());
        System.out.println(nums);
        System.out.println(numsSorted);

        amountHeader.click();
    }

    public Map<Double, List<String>> getRows() {
        List<WebElement> statusesIconsE = getAllMatchingElements(statusIcons);
        List<WebElement> statusesValuesE = getAllMatchingElements(statusValues);
        List<WebElement> dateValuesE = getAllMatchingElements(dateValues);
        List<WebElement> timeValuesE = getAllMatchingElements(timeValues);
        List<WebElement> compLogosE = getAllMatchingElements(companyLogos);
        List<WebElement> compValuesE = getAllMatchingElements(companyValues);
        List<WebElement> catValuesE = getAllMatchingElements(categoryValues);
        List<WebElement> amounts = getAllMatchingElements(amountValues);

        Map<Double, List<String>> rows = new HashMap<>();

        for(int i=0; i < statusesIconsE.size(); i++) {

            String statusIcon = statusesIconsE.get(i).getAttribute("class");
            String status = statusesValuesE.get(i).getText().trim();
            String date = dateValuesE.get(i).getText().trim();
            String time = timeValuesE.get(i).getText().trim();
            String compLogo = compLogosE.get(i).getAttribute("src");
            String comp = compValuesE.get(i).getText();
            String cat = catValuesE.get(i).getText().trim();
            String catBadge = catValuesE.get(i).getAttribute("class");
            String amountDirty = amounts.get(i).getText().trim();
            String[] amountSplits = amountDirty.split(" ");
            StringBuilder amountSb = new StringBuilder();
            if(amountSplits[0].equals("-")) {
                amountSb.append("-");
            }

            amountSb.append(amountSplits[1]);
            String amount = amountSb.toString().replaceAll(",", "");
            Double amountDouble = Double.parseDouble(amount);

            List<String> tempList = Arrays.asList(statusIcon, status, date, time, compLogo, comp, cat, catBadge);
            rows.put(amountDouble, tempList);
        }

        return rows;

    }


}
