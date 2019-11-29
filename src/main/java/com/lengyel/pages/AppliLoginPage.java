package com.lengyel.pages;


import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AppliLoginPage extends Page {

    protected static final Logger logger = LogManager.getLogger(AppliLoginPage.class);

    @FindBy(how = How.CSS, using="button#log-in")
    private WebElement loginButton;

    @FindBy(how = How.CSS, using="a#log-in")
    private WebElement signInButton;

    @FindBy(how = How.CSS, using="input#username")
    WebElement usernameTextField;

    @FindBy(how = How.CSS, using="input#password")
    WebElement passwordTextField;

    @FindBy(how = How.CSS, using="h4.auth-header")
    WebElement loginFormHeader;

    @FindBy(how = How.XPATH, using="//input[@id='username']/preceding-sibling::label")
    private WebElement usernameLabel;

    @FindBy(how = How.XPATH, using="//input[@id='password']/preceding-sibling::label")
    WebElement passwordLabel;

    @FindBy(how = How.XPATH, using="//div[@class='buttons-w']//img[contains(@src, 'twitter')]")
    WebElement twitterIcon;

    @FindBy(how = How.XPATH, using="//div[@class='buttons-w']//img[contains(@src, 'facebook')]")
    WebElement facebookIcon;

    @FindBy(how = How.XPATH, using="//div[@class='buttons-w']//img[contains(@src, 'linkedin')]")
    WebElement linkedinIcon;

    @FindBy(how = How.CSS, using="label.form-check-label")
    WebElement rememberMeLabel;

    @FindBy(how = How.CSS, using="input.form-check-input")
    WebElement rememberMeCheckbox;

    @FindBy(how = How.CSS, using="div.alert-warning")
    WebElement loginFailedMsg;

    @FindBy(how = How.XPATH, using="//input[@id='username']//following-sibling::div[contains(@class, 'os-icon-user-male-circle')]")
    WebElement maleIcon;

    @FindBy(how = How.XPATH, using= "//input[@id='password']//following-sibling::div[contains(@class, 'os-icon-fingerprint')]")
    WebElement fingerprintIcon;

    public AppliLoginPage(WebDriver driver) {
        super("AppliLoginPage", "https://demo.applitools.com/hackathonV2.html", driver);
        PageFactory.initElements(driver, this);
        driver().get(url());

        if(isDisplayed() == false) {
            throw new RuntimeException("AppliLoginPage is not displayed!");
        }
    }


    public void checkInitialLoginPage() {
        checkHeader();
        checkLabels();
        checkIconsPresent();
        checkTextFieldsPlaceholders();
        checkLoginButton();
        checkRememberMe();
        checkSocialIconsPresent();
    }


    public AppliAppPage loginValid(String username, String password) {
        usernameTextField.sendKeys(username);
        passwordTextField.sendKeys(password);

        driverWait().until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
        return new AppliAppPage(driver());
    }

    public void loginInvalid(String username, String password) {
        usernameTextField.sendKeys(username);
        passwordTextField.sendKeys(password);

        driverWait().until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();



        boolean loginFailedMsgDisplayed = isElementPresent(loginFailedMsg);
        assertActions().verifyTrue(loginFailedMsgDisplayed, "Login failed msg not displayed");

        if(loginFailedMsgDisplayed) {
            final String LOGIN_FAILED_MSG = "Both Username and Password must be present";
            final String PASSWORD_MISSING_MSG = "Password must be present";
            final String USERNAME_MISSING_MSG = "Username must be present";

            String msgText = loginFailedMsg.getText().trim();

            if (username.isBlank() && password.isBlank())
                assertActions().verifyTrue(LOGIN_FAILED_MSG.equals(msgText), "Error message text is incorrect");
            else if (username.isBlank())
                assertActions().verifyTrue(USERNAME_MISSING_MSG.equals(msgText), "Error message when username is missing is incorrect");
            else
                assertActions().verifyTrue(PASSWORD_MISSING_MSG.equals(msgText), "Error message when password is missing is incorrect");

            //based on V2 => invalid style of error msg
            final String ERROR_MSG_STYLE = "display: block;";
            String errorMsgStyleCaptured = loginFailedMsg.getAttribute("style");
            assertActions() .verifyTrue(errorMsgStyleCaptured.equals(ERROR_MSG_STYLE), "The style of error message is incorrect");
        }

        driver().navigate().refresh();
    }

    public void checkHeader() {
        final String HEADER_TEXT = "Login Form";

        String headerTextCaptured = loginFormHeader.getText().trim();

        assertActions().verifyTrue(HEADER_TEXT.equals(headerTextCaptured), "Login form header text incorrect");
    }

    public void checkLabels() {
        final String USERNAME_LABEL_TEXT = "Username";
        final String PASSWORD_LABEL_TEXT = "Password";

        String usernameLabelCaptured = usernameLabel.getText();
        String passwordLabelCaptured = passwordLabel.getText();

        assertActions().verifyTrue(USERNAME_LABEL_TEXT.equals(usernameLabelCaptured), "Username label incorrect");
        assertActions().verifyTrue(PASSWORD_LABEL_TEXT.equals(passwordLabelCaptured), "Password label incorrect");

    }

    public void checkTextFieldsPlaceholders() {
        //based on V2
        final String USERNAME_PLACEHOLDER = "John Smith";
        final String PASSWORD_PLACEHOLDER = "ABC$*1@";

        String usernamePlaceholderCaptured = usernameTextField.getAttribute("placeholder");
        String passwordPlaceholderCaptured = passwordTextField.getAttribute("placeholder");

        assertActions().verifyTrue(USERNAME_PLACEHOLDER.equals(usernamePlaceholderCaptured), "Username placeholder incorrect");
        assertActions().verifyTrue(PASSWORD_PLACEHOLDER.equals(passwordPlaceholderCaptured), "Password placeholder incorrect");
    }

    public void checkLoginButton() {
        final String LOGIN_BTN_TEXT = "Log In";

        String btnTextCaptured = loginButton.getText().trim();

        assertActions().verifyTrue(LOGIN_BTN_TEXT.equals(btnTextCaptured), "Login button text incorrect");
    }

    public void checkSignInButton() {
        final String SIGN_IN_BTN_TEXT = "Sign in";

        String btnTextCaptured = signInButton.getText().trim();

        assertActions().verifyTrue(SIGN_IN_BTN_TEXT.equals(btnTextCaptured), "Sign in button text incorrect");
    }

    public void checkRememberMe() {
        final String REMEMBER_ME_TEXT = "Remember Me";

        String labelTextCaptured = rememberMeLabel.getText().trim();
        String labelTextStyle = rememberMeCheckbox.getAttribute("style");

        assertActions().verifyTrue(REMEMBER_ME_TEXT.equals(labelTextCaptured), "Remember me label text incorrect");
        //based on V2 to check the offset of Remember me text form the checkbox
        assertActions().verifyTrue(StringUtils.isBlank(labelTextStyle), "The style attribute of Remember me checkbox should be empty");
    }

    public void checkSocialIconsPresent() {
        assertActions().verifyTrue(isElementPresent(facebookIcon), "Facebook icon is not present");
        assertActions().verifyTrue(isElementPresent(twitterIcon), "Twitter icon is not present");
        assertActions().verifyTrue(isElementPresent(linkedinIcon), "LinkedIn icon is not present");
    }

    public void checkIconsPresent() {

        assertActions().verifyTrue(isElementPresent(maleIcon), "Male icon is not present");
        assertActions().verifyTrue(isElementPresent(fingerprintIcon), "Fingerprint icon is not present");
    }

}
