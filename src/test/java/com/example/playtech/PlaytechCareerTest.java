package com.example.playtech;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        assertFalse(options.isEmpty(), "Team dropdown listing was not displayed.");

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
}