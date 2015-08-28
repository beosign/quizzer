package de.beosgin.quizzer.question;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateQuestionTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    private static String questionCode = "CQ-";
    private static String question = "Question-";
    private static String answerCorrect = "Correct Answer";
    private static String answerWrong = "Wrong Answer";
    private static String testId;
    private static int questionPoints = 4;

    @Before
    public void setUp() throws Exception {
        // Optional, if not specified, WebDriver will search your path for chromedriver.
        System.setProperty("webdriver.chrome.driver", "D:/Eigene Dateien/.java-4.4/Quizzer/lib/test/selenium/chrome/chromedriver.exe");

        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/Quizzer";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        Random r = new Random();
        testId = String.valueOf(r.nextInt(10000));
        questionCode += testId;
        question += testId;

    }

    @Test
    public void createQuestionTypeSingle() throws Exception {
        driver.get(baseUrl);

        // Click buttons "Questions", then "Add"
        driver.findElement(By.id("form:btn-questions")).click();
        driver.findElement(By.id("form:btn-add")).click();

        // Now on "Add question" page, set fields for question
        driver.findElement(By.id("form:code")).sendKeys(questionCode);
        driver.findElement(By.id("form:question")).sendKeys(question);
        driver.findElement(By.id("form:points")).sendKeys(String.valueOf(questionPoints));

        // Add two answers, one correct and one wrong answer
        driver.findElement(By.id("form:answer-table:btn-add-answer")).click();
        driver.findElement(By.className("ui-editable-column")).click(); // trigger edit mode
        driver.findElement(By.id("form:answer-table:0:inputTextEdit")).sendKeys(answerCorrect);
        driver.findElement(By.id("form")).click(); // click anywhere on form to leave edit mode

        // Click hidden checkbox ny using JavaScript
        WebElement checkbox = driver.findElement(By.id("form:answer-table:0:chk-answer-correct_input"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", checkbox);
        // Click somewhere on form to leave edit mode for checkbox
        driver.findElement(By.id("form")).click();

        // Add second answer
        driver.findElement(By.id("form:answer-table:btn-add-answer")).click();
        driver.findElements(By.className("ui-editable-column")).get(1).click();
        driver.findElement(By.id("form:answer-table:1:inputTextEdit")).sendKeys(answerWrong);
        driver.findElement(By.id("form")).click();

        // Add ok button to store question
        driver.findElement(By.id("form:btn-ok")).click();

        Assert.assertNotNull("Question header was not found, so we are not on the page we should be (questions.xhtml)",
                driver.findElement(By.id("question-heading")));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
