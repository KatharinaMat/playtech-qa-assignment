package com.example.playtech;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlaytechCareerTest extends BaseTest {

    @Test
    void openTeamDropdown() {
        driver.get("https://www.playtechpeople.com/");
        handleCookieBanner();

        WebElement teamDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(), 'Select Team')]")
                )
        );

        scrollIntoView(teamDropdown);

        teamDropdown.click();
        List<WebElement> options = wait.until (
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//div[contains(@class,'teams-column__item')]//span")
                )
        );

        List<String> teams = new ArrayList<>();

        for (WebElement option : options) {
            String text = option.getText().trim();

            if (!text.isEmpty() && !text.equalsIgnoreCase("All")) {
                teams.add(text);
            }
        }

        System.out.println("Teams count: " + teams.size());

        for (String team : teams) {
            System.out.println(team);
        }
        assertFalse(teams.isEmpty(), "No teams were extracted.");
    }

    @Test
    void extractResearchAreas() {
        driver.get("https://www.playtechpeople.com/life-at-playtech/");
        handleCookieBanner();

        WebElement researchButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("button.accordion-button[data-bs-target='#collapse-6-4-6']")
                )
        );

        scrollIntoView(researchButton);

        WebElement researchContent = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("collapse-6-4-6")
                )
        );

        String contentClasses = researchContent.getAttribute("class");

        if (contentClasses == null || !contentClasses.contains("show")){
            clickWithJs(researchButton);
        }

        researchContent = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("collapse-6-4-6")
                )
        );

        List<WebElement> areaItems = researchContent.findElements(
                By.xpath(".//ul/li/ul/li")
        );

        List<String> researchAreas = new ArrayList<>();

        for (WebElement item : areaItems) {
            String text = item.getText().trim();

            if (!text.isEmpty()) {
                researchAreas.add(text);
            }
        }

        System.out.println("Research areas:");

        for (String area : researchAreas) {
            System.out.println("- " + area);
        }

        assertFalse(researchAreas.isEmpty(), "No research areas were extracted");
    }

    @Test
    void extractLinkWithTallinnAndTartuLocation() {
        driver.get("https://www.playtechpeople.com/jobs-our/?activeLocation=Estonia");
        handleCookieBanner();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".jobs-wrap .job-item[data-location='estonia']")
        ));

        List<WebElement> jobs = driver.findElements(
                By.cssSelector(".jobs-wrap .job-item[data-location='estonia']")
        );

        List<String> links = new ArrayList<>();

        for (WebElement job : jobs) {
            links.add(job.getAttribute("href"));
        }

        String foundLink = null;

        for (String link : links) {
            driver.get(link);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            String pageText = driver.getPageSource();

            if (pageText.contains("Tartu") && pageText.contains("Tallinn")) {
                foundLink = link;
                break;
            }
        }
        if (foundLink != null) {
            System.out.println("Link: " + foundLink);
        }

        assertFalse(foundLink == null, "No matching job found.");
    }
}