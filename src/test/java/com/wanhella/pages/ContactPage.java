package com.wanhella.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ContactPage extends BasePage {
    private static final String URL = "https://wanhella.com/Contact_Me";
    public static final String LINKEDIN_URL = "https://linkedin.com/in/swanhella";
    public static final String GITHUB_URL = "https://github.com/1hella";
    public static final String TITLE = "Contact Me \u2022 Stephen Wanhella";
    public static final String PAGE_HEADER = "Contact Me";

    private final By header = By.tagName("h1");
    private final By linkedIn = By.linkText("LinkedIn");
    private final By github = By.linkText("GitHub");

    private final By nameField = By.cssSelector("input[aria-label='Your name']");
    private final By emailField = By.cssSelector("input[aria-label='Your email address']");
    private final By messageField = By.cssSelector("textarea[aria-label='Your message']");
    private final By submitButton = By.xpath("//p[text()='Email Me']");
    private final By successMessage = By.xpath("//p[text()='Email sent']");

    private final By nameError = By.xpath("//p[text()='Please enter your name']");
    private final By emailError = By.xpath("//p[text()='Please enter your email address']");
    private final By messageError = By.xpath("//p[text()='Please enter your message']");

    public ContactPage(String browser, int timeoutSec) {
        this(browser);
        setTimeoutSec(timeoutSec);
    }

    public ContactPage(String browser) {
        super(browser);
        visit(URL);
    }

    public String getTitle() {
        wait.until(ExpectedConditions.titleIs(TITLE));
        return super.getTitle();
    }

    public String getPageHeader() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(header)).getText();
    }

    public String getLinkedInHref() {
        WebElement linkedIn = wait.until(ExpectedConditions.presenceOfElementLocated(this.linkedIn));
        return linkedIn.getAttribute("href");
    }

    public String getGithubHref() {
        WebElement github = wait.until(ExpectedConditions.presenceOfElementLocated(this.github));
        return github.getAttribute("href");
    }

    public void contact(String name, String email, String message) {
        wait.until(ExpectedConditions.presenceOfElementLocated(nameField));
        type(nameField, name);
        type(emailField, email);
        type(messageField, message);
        click(submitButton);
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage);
    }

    public boolean isNameErrorDisplayed() {
        return isDisplayed(nameError);
    }

    public boolean isEmailErrorDisplayed() {
        return isDisplayed(emailError);
    }

    public boolean isMessageErrorDisplayed() {
        return isDisplayed(messageError);
    }

    public String getNameFieldText() {
        wait.until(ExpectedConditions.presenceOfElementLocated(nameField));
        return getInputText(nameField);
    }

    public String getEmailFieldText() {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailField));
        return getInputText(emailField);
    }

    public String getMessageFieldText() {
        wait.until(ExpectedConditions.presenceOfElementLocated(messageField));
        return getInputText(messageField);
    }
}
