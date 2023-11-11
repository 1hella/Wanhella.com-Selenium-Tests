package com.wanhella;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class HomeTest {

    static final Logger log = getLogger(lookup().lookupClass());
    private HomePage homePage;

    @BeforeEach
    void setup() {
        homePage = new HomePage("chrome");
    }

    @AfterEach
    void teardown() {
        homePage.quit();
    }

    @Test
    void testTitle() {
        assertThat(homePage.getPageTitle()).isEqualTo(HomePage.TITLE);
    }

    @Test
    void testHeader() {
        assertThat(homePage.getPageHeader()).isEqualTo(HomePage.HEADER_TEXT);
    }

    @Test
    void testIntroduction() {
        assertThat(homePage.introIsDisplayed()).isTrue();
    }

    @Test
    void testProjects() {
        assertThat(homePage.projectsHeadlineIsDisplayed()).isTrue();

        List<String> projectTitles = homePage.getProjectTitles();
        assertThat(projectTitles.size()).isGreaterThan(0);

        for (String projectTitle : projectTitles) {
            assertThat(homePage.projectTitleIsDisplayed(projectTitle)).isTrue();
            assertThat(homePage.projectImageIsDisplayed(projectTitle)).isTrue();
            assertThat(homePage.projectDescriptionIsDisplayed(projectTitle)).isTrue();
            assertThat(homePage.projectLinkTextIsAccurate(projectTitle)).isTrue();
        }
    }

    @Test
    void testContactMe() {
        assertThat(homePage.getContactMeText()).isEqualTo("Contact Me");
        assertThat(homePage.getContactMeHref()).contains("/Contact_Me");
    }
}
