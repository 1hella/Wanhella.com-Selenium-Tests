package com.wanhella;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class ContactTest {

    static final Logger log = getLogger(lookup().lookupClass());
    public static final String NAME_FIELD_CSS_SELECTOR = "input[aria-label='Your name']";
    public static final String EMAIL_FIELD_CSS_SELECTOR = "input[aria-label='Your email address']";
    public static final String MESSAGE_FIELD_CSS_SELECTOR = "textarea[aria-label='Your message']";

    private WebDriver driver;

    private final String URL = "https://wanhella.com/Contact_Me";


    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testTitle() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String title = "Contact Me \u2022 Stephen Wanhella";
        wait.until(ExpectedConditions.titleIs(title));
        assertThat(driver.getTitle()).isEqualTo(title);
    }

    @Test
    void testHeading() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertThat(heading.getText()).isEqualTo("Contact Me");
    }

    @Test
    void testLinks() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement linkedIn = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("LinkedIn")));

        assertThat(linkedIn.getDomAttribute("href")).isEqualTo("https://linkedin.com/in/swanhella");

        WebElement gitHub = driver.findElement(By.linkText("GitHub"));

        assertThat(gitHub.getAttribute("href")).isEqualTo("https://github.com/1hella");
    }

    @Test
    void testFormValidation() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[text()='Email Me']")));
        submitButton.click();

        WebElement nameAlert = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[text()='Please enter your name']")));

        assertThat(nameAlert.isDisplayed()).isTrue();

        WebElement emailAlert = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[text()='Please enter your email address']")));

        assertThat(emailAlert.isDisplayed()).isTrue();

        WebElement messageAlert = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[text()='Please enter your message']")));

        assertThat(messageAlert.isDisplayed()).isTrue();
    }

    @Test
    void testFormSubmits() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // fill out form and click submit
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(NAME_FIELD_CSS_SELECTOR)));
        nameField.sendKeys("Stephen Wanhella");

        WebElement emailField = driver.findElement(By.cssSelector(EMAIL_FIELD_CSS_SELECTOR));
        emailField.sendKeys("test@wanhella.com");

        WebElement messageField = driver.findElement(By.cssSelector(MESSAGE_FIELD_CSS_SELECTOR));
        messageField.sendKeys("This is a test message from Selenium. Please ignore.");

        assertThat(nameField.getAttribute("value")).isEqualTo("Stephen Wanhella");
        assertThat(emailField.getAttribute("value")).isEqualTo("test@wanhella.com");
        assertThat(messageField.getAttribute("value")).isEqualTo("This is a test message from Selenium. Please ignore.");

        WebElement submitButton = driver.findElement(By.xpath("//p[text()='Email Me']"));
        submitButton.click();

        // wait for success message
        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[text()='Email sent']")));

        assertThat(successMessage.isDisplayed()).isTrue();

        // find fresh WebElements after page load
        nameField = driver.findElement(By.cssSelector(NAME_FIELD_CSS_SELECTOR));
        emailField = driver.findElement(By.cssSelector(EMAIL_FIELD_CSS_SELECTOR));
        messageField = driver.findElement(By.cssSelector(MESSAGE_FIELD_CSS_SELECTOR));

        // assert that fields have been cleared
        assertThat(nameField.getAttribute("value")).isEmpty();
        assertThat(emailField.getAttribute("value")).isEmpty();
        assertThat(messageField.getAttribute("value")).isEmpty();
    }
}
