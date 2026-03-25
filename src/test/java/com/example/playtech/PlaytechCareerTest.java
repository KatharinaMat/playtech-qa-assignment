package com.example.playtech;

import org.junit.jupiter.api.Test;

public class PlaytechCareerTest extends BaseTest {

    @Test
    void openPlaytechHomePage() {
        driver.get("https://www.playtechpeople.com");
    }
}