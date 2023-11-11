package com.wanhella;

import com.wanhella.pages.ContactPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactTest {

    private ContactPage contactPage;

    @BeforeEach
    void setup() {
        contactPage = new ContactPage("chrome");
    }

    @AfterEach
    void teardown() {
        contactPage.quit();
    }

    @Test
    void testTitle() {
        assertThat(contactPage.getTitle()).isEqualTo(ContactPage.TITLE);
    }

    @Test
    void testHeading() {
        assertThat(contactPage.getPageHeader()).isEqualTo(ContactPage.PAGE_HEADER);
    }

    @Test
    void testLinks() {
        assertThat(contactPage.getLinkedInHref()).isEqualTo(ContactPage.LINKEDIN_URL);
        assertThat(contactPage.getGithubHref()).isEqualTo(ContactPage.GITHUB_URL);
    }

    @Test
    void testBadForm() {
        contactPage.contact("", "", "");
        assertThat(contactPage.isNameErrorDisplayed()).isTrue();
        assertThat(contactPage.isEmailErrorDisplayed()).isTrue();
        assertThat(contactPage.isMessageErrorDisplayed()).isTrue();
        // timeout seconds is set to 1 before testing that an element is not displayed to avoid waiting x seconds
        // for every assertion
        contactPage.setTimeoutSec(1);
        assertThat(contactPage.isSuccessMessageDisplayed()).isFalse();
    }

    @Test
    void testSuccessfulForm() {
        contactPage.contact("Stephen Wanhella", "test@wanhella.com", "This is a test message from Selenium. Please ignore.");
        assertThat(contactPage.isSuccessMessageDisplayed()).isTrue();
        contactPage.setTimeoutSec(1);
        assertThat(contactPage.isNameErrorDisplayed()).isFalse();
        assertThat(contactPage.isEmailErrorDisplayed()).isFalse();
        assertThat(contactPage.isMessageErrorDisplayed()).isFalse();

        // assert that fields have been cleared
        assertThat(contactPage.getNameFieldText()).isEmpty();
        assertThat(contactPage.getEmailFieldText()).isEmpty();
        assertThat(contactPage.getMessageFieldText()).isEmpty();
    }

    @Test
    void testBadName() {
        contactPage.contact("", "test@wanhella.com", "This is a test message from Selenium. Please ignore.");
        assertThat(contactPage.isNameErrorDisplayed()).isTrue();
        contactPage.setTimeoutSec(1);
        assertThat(contactPage.isSuccessMessageDisplayed()).isFalse();
        assertThat(contactPage.isEmailErrorDisplayed()).isFalse();
        assertThat(contactPage.isMessageErrorDisplayed()).isFalse();
    }

    @Test
    void testBadEmail() {
        contactPage.contact("Stephen Wanhella", "", "This is a test message from Selenium. Please ignore.");
        assertThat(contactPage.isEmailErrorDisplayed()).isTrue();
        contactPage.setTimeoutSec(1);
        assertThat(contactPage.isSuccessMessageDisplayed()).isFalse();
        assertThat(contactPage.isNameErrorDisplayed()).isFalse();
        assertThat(contactPage.isMessageErrorDisplayed()).isFalse();
    }

    @Test
    void testBadMessage() {
        contactPage.contact("Stephen Wanhella", "test@wanhella.com", "");
        assertThat(contactPage.isMessageErrorDisplayed()).isTrue();
        contactPage.setTimeoutSec(1);
        assertThat(contactPage.isSuccessMessageDisplayed()).isFalse();
        assertThat(contactPage.isNameErrorDisplayed()).isFalse();
        assertThat(contactPage.isEmailErrorDisplayed()).isFalse();
    }
}
