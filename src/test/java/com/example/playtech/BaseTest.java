package com.example.playtech;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");

        driver = new ChromeDriver(options);

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void handleCookieBanner() {
        try {
            WebElement acceptButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(., 'Allow all')]")
                    )
            );
            acceptButton.click();
        } catch (Exception e) {
            }
    }

    protected void scrollIntoView(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected void clickWithJs(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    protected static void writeResultsToFile(List<String> teams, List<String> researchAreas, String jobLink) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            writer.write("Playtech QA Assignment Results\n\n");

            writer.write("Teams count: " + teams.size() + "\n");
            for (String team : teams) {
                writer.write("- " + team + "\n");
            }

            writer.write("\nResearch areas:\n");
            for (String area : researchAreas) {
                writer.write("- " + area + "\n");
            }

            writer.write("\nJob Link (Tallinn & Tartu):\n");
            writer.write(jobLink != null ? jobLink : "Not found");

        } catch (IOException e) {
            throw new RuntimeException("Failed to write results to file", e);
        }
    }
}