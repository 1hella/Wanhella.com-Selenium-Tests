package com.wanhella;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import static org.assertj.core.api.Assertions.assertThat;

public class HomeTest {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;

    private final String URL = "https://wanhella.com";


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
        String title = "Home \u2022 Stephen Wanhella";
        wait.until(ExpectedConditions.titleIs(title));
        assertThat(driver.getTitle()).isEqualTo(title);
    }

    @Test
    void testElementsAppearOnPage() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("stephen-wanhella")));
        assertThat(header.getText()).isEqualTo("Stephen Wanhella");

        WebElement intro = driver.findElement(By.xpath("//p[contains(text(), \"Hi, I'm Stephen!\")]"));
        assertThat(intro.isDisplayed()).isTrue();

        WebElement projectHeadline = driver.findElement(By.xpath("//p[contains(text(), \"projects\")]"));
        assertThat(projectHeadline.isDisplayed()).isTrue();

        List<WebElement> projectTitles = driver.findElements(By.tagName("h2"));
        assertThat(projectTitles.size()).isGreaterThan(0);

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        for (WebElement projectTitle : projectTitles) {
            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", projectTitle);

            assertThat(projectTitle.isDisplayed()).isTrue();

            // Search for image first then the description above and the link below.
            // Otherwise, the test gets the link instead of the description for some reason.
            RelativeLocator.RelativeBy imgRelativeBy = RelativeLocator.with(By.tagName("img"));
            WebElement img = driver.findElement(imgRelativeBy.below(projectTitle));

            assertThat(img.isDisplayed()).isTrue();

            RelativeLocator.RelativeBy descriptionRelativeBy = RelativeLocator.with(By.tagName("p"));
            WebElement description = driver.findElement(descriptionRelativeBy.above(img));

            assertThat(description.isDisplayed()).isTrue();

            RelativeLocator.RelativeBy linkRelativeBy = RelativeLocator.with(By.tagName("a"));
            WebElement link = driver.findElement(linkRelativeBy.below(img));

            assertThat(link.isDisplayed()).isTrue();
            List<String> linkOptions = Arrays.asList("Source Code", "Link");
            assertThat(link.getText()).isIn(linkOptions);
        }

        WebElement contactMe = driver.findElement(By.xpath("//a[text()='Contact Me']"));
        assertThat(contactMe.getText()).isEqualTo("Contact Me");
        assertThat(contactMe.getAttribute("href")).contains("/Contact_Me");
    }
}
