package com.wanhella.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;


public class HomePage extends BasePage {
    public static final String HEADER_TEXT = "Stephen Wanhella";
    public static final String CONTACT_ME_TEXT = "Contact Me";
    public static final CharSequence CONTACT_ME_HREF = "/Contact_Me";
    private static final String URL = "https://wanhella.com";
    public static final String TITLE = "Home \u2022 Stephen Wanhella";

    By header = By.id("stephen-wanhella");

    By intro = By.xpath("//p[contains(text(), \"Hi, I'm Stephen!\")]");
    By contactMe = By.xpath("//a[text()='Contact Me']");
    By projectsHeadline = By.xpath("//p[contains(text(), \"projects\")]");
    By projectTitles = By.tagName("h2");

    public HomePage(String browser, int timeoutSec) {
        this(browser);
        setTimeoutSec(timeoutSec);
    }

    public HomePage(String browser) {
        super(browser);
        visit(URL);
    }

    public String getPageTitle() {
        wait.until(ExpectedConditions.titleIs(TITLE));
        return getTitle();
    }

    public String getPageHeader() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(header)).getText();
    }

    public boolean introIsDisplayed() {
        return isDisplayed(intro);
    }

    public List<String> getProjectTitles() {
        return driver.findElements(projectTitles).stream().map(WebElement::getText).toList();
    }

    public boolean projectsHeadlineIsDisplayed() {
        return isDisplayed(projectsHeadline);
    }

    private WebElement getProject(String title) {
        return driver.findElement(By.xpath("//h2//span[text()='" + title + "']"));
    }

    public String getContactMeText() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(contactMe)).getText();
    }

    public String getContactMeHref() {
        WebElement contact = wait.until(ExpectedConditions.presenceOfElementLocated(contactMe));
        return contact.getAttribute("href");
    }

    public boolean projectTitleIsDisplayed(String title) {
        WebElement projectTitle = getProject(title);
        return isDisplayed(projectTitle);
    }

    public boolean projectImageIsDisplayed(String title) {
        WebElement projectTitle = getProject(title);
        RelativeLocator.RelativeBy imgRelativeBy = RelativeLocator.with(By.tagName("img"));
        WebElement img = driver.findElement(imgRelativeBy.below(projectTitle));

        return isDisplayed(img);
    }

    public boolean projectDescriptionIsDisplayed(String title) {
        WebElement projectTitle = getProject(title);
        RelativeLocator.RelativeBy imgRelativeBy = RelativeLocator.with(By.tagName("img"));
        WebElement img = driver.findElement(imgRelativeBy.below(projectTitle));

        RelativeLocator.RelativeBy descriptionRelativeBy = RelativeLocator.with(By.tagName("p"));
        WebElement description = driver.findElement(descriptionRelativeBy.above(img));

        return isDisplayed(description);
    }

    public boolean projectLinkTextIsAccurate(String title) {
        WebElement projectTitle = getProject(title);
        RelativeLocator.RelativeBy imgRelativeBy = RelativeLocator.with(By.tagName("img"));
        WebElement img = driver.findElement(imgRelativeBy.below(projectTitle));

        RelativeLocator.RelativeBy linkRelativeBy = RelativeLocator.with(By.tagName("a"));
        WebElement link = driver.findElement(linkRelativeBy.below(img));

        Set<String> linkOptions = Set.of("Source Code", "Link");
        return linkOptions.contains(link.getText());
    }
}
