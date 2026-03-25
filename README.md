# Playtech QA Automation Assignment

## Overview
This project contains an automated solution for the Playtech DevQA Engineer internship assignment.

The solution is implemented using:
- Java
- Selenium WebDriver
- JUnit 5
- Maven

The goal of the assignment is to automate interaction with the Playtech careers website and extract specific information.

---

## Project Setup

### Requirements
- Java 17+ (project uses Java 21)
- Maven
- Google Chrome browser

### How to run
1. Clone the repository:
   ```bash
   git clone https://github.com/KatharinaMat/playtech-qa-assignment.git
2. Open the project in IntelliJ IDEA
3. Run the test:
- Navigate to PlaytechCareerTest
- Run the test using JUnit  

## Assumptions and Decisions
Teams extraction
The website presents team information in two places:
1. The footer contains a shorter list of teams, including potentially overlapping labels such as:
"IT" and "Information Technology" and "Security" and "Safety & Security"
2. The "Select Team" dropdown on the main page contains a more extensive list of team categories.

The dropdown list was chosen for extracting and counting teams, as it appears to reflect the classification more precisely.

The filter option "All" is excluded from the final team count.

### Implemented Functionality

- Open Playtech careers website
- Handle cookie consent banner
- Scroll to team selection section
- Extract team names from "Select Team" dropdown
- Exclude "All" 
- Print extracted teams count and team names to console