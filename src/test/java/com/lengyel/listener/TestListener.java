package com.lengyel.listener;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.internal.InvokedMethod;
import org.testng.internal.TestResult;

import java.lang.reflect.Field;

public class TestListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            if (!testResult.isSuccess()) {
                //String logPath = getTechnicalProperty(SGTConstants.CURRENT_LOGPATH_PROPERTY);
                String testDataName = null;
                if (testResult.getParameters() != null && testResult.getParameters().length > 0
                        && testResult.getParameters()[0] != null) {
                    testDataName = (String) testResult.getParameters()[0];
                } else {
                    testDataName = method.getTestMethod().getRealClass().getSimpleName();
                }

                Field privateTestCaseField;
                try {
                    privateTestCaseField = InvokedMethod.class.getDeclaredField("m_instance");
                    privateTestCaseField.setAccessible(true);
                    Object testCase = privateTestCaseField.get(method);
                   // WebDriver driver = testCase.getTestContext().getDriver();
              //      if (driver.getClass().equals(RemoteWebDriver.class)) {
             //           driver = new Augmenter().augment(driver);
              //      }


            /*        String screenshotAddition = getProperty(Constants.CUSTOM_ADDITIONAL_SCREENSHOT_PROPERTY);
                    if (screenshotAddition == null) {
                        screenshotAddition = "";
                    } else {
                        if (screenshotAddition.length() > 0) {
                            testDataName += "_";
                        }
                    }
                    testDataName += screenshotAddition;
                    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(screenshot, new File(logPath + "\\" + testDataName + ".png"));
                    */
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Field cause = TestResult.class.getDeclaredField("m_throwable");
                    cause.setAccessible(true);
                    Throwable throwa = (Throwable) cause.get(testResult);
                    if (throwa != null) {
                        System.err.println(throwa.getMessage());
                        throwa.printStackTrace(System.out);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
