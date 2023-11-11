package com.wanhella.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    WebDriver driver;
    WebDriverWait wait;
    int timeoutSec = 10;

    public BasePage(String browser) {
        driver = WebDriverManager.getInstance(browser).create();
        updateWait();
    }

    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
        updateWait();
    }

    private void updateWait() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void visit(String url) {
        driver.get(url);
    }

    public WebElement find(By element) {
        return driver.findElement(element);
    }

    public List<WebElement> findMultiple(By locator) {
        return driver.findElements(locator);
    }

    public void click(By element) {
        click(find(element));
    }

    public void click(WebElement element) {
        element.click();
    }

    public void type(By element, String text) {
        isDisplayed(element);
        type(find(element), text);
    }

    public void type(WebElement element, String text) {
        element.sendKeys(text);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getInputText(By element) {
        return find(element).getAttribute("value");
    }

    public boolean isDisplayed(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            System.out.printf("Timeout of %s wait for %s%n", timeoutSec, locator);
            return false;
        }
        return true;
    }

    public boolean isDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            System.out.printf("Timeout of %s wait for %s%n", timeoutSec, element);
            return false;
        }
        return true;
    }
}
