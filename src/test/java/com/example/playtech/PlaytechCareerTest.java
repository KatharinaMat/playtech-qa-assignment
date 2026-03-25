package com.example.playtech;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlaytechCareerTest extends BaseTest {

    private static List<String> teamsResult;
    private static List<String> researchAreasResult;
    private static String jobLinkResult;

    private static final By TEAM_DROPDOWN =
            By.xpath("//*[contains(text(), 'Select Team')]");
    private static final By TEAM_OPTIONS =
            By.xpath("//div[contains(@class,'teams-column__item')]//span");
    private static final By RESEARCH_BUTTON =
            By.cssSelector("button.accordion-button[data-bs-target='#collapse-6-4-6']");
    private static final By RESEARCH_CONTENT =
            By.id("collapse-6-4-6");
    private static final By RESEARCH_AREA_ITEMS = By.xpath(".//ul/li/ul/li");
    private static final By JOB_ITEMS =
            By.cssSelector(".jobs-wrap .job-item[data-location='estonia']");

    @Test
    @Order(1)
    void openTeamDropdown() {
        driver.get("https://www.playtechpeople.com/");
        handleCookieBanner();

        WebElement teamDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(TEAM_DROPDOWN));

        scrollIntoView(teamDropdown);

        teamDropdown.click();
        List<WebElement> options = wait.until (
                ExpectedConditions.visibilityOfAllElementsLocatedBy(TEAM_OPTIONS));

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
        teamsResult = teams;
    }

    @Test
    @Order(2)
    void extractResearchAreas() {
        driver.get("https://www.playtechpeople.com/life-at-playtech/");
        handleCookieBanner();

        WebElement researchButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(RESEARCH_BUTTON));

        scrollIntoView(researchButton);

        WebElement researchContent = wait.until(
                ExpectedConditions.presenceOfElementLocated(RESEARCH_CONTENT));

        String contentClasses = researchContent.getAttribute("class");

        if (contentClasses == null || !contentClasses.contains("show")){
            clickWithJs(researchButton);
        }

        researchContent = wait.until(
                ExpectedConditions.visibilityOfElementLocated(RESEARCH_CONTENT));

        List<WebElement> areaItems = researchContent.findElements(RESEARCH_AREA_ITEMS);

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
        researchAreasResult = researchAreas;
    }

    @Test
    @Order(3)
    void extractLinkWithTallinnAndTartuLocation() {
        driver.get("https://www.playtechpeople.com/jobs-our/?activeLocation=Estonia");
        handleCookieBanner();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(JOB_ITEMS));

        List<WebElement> jobs = driver.findElements(JOB_ITEMS);

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
            System.out.println("Job Link (Tallinn & Tartu): " + foundLink);
        }
        jobLinkResult = foundLink;
        assertFalse(foundLink == null, "No matching job found.");

    }

    @AfterAll
    static void exportResults() {
          writeResultsToFile(
                teamsResult != null ? teamsResult : new ArrayList<>(),
                researchAreasResult != null ? researchAreasResult : new ArrayList<>(),
                jobLinkResult
        );
    }
}