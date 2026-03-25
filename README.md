# Playtech QA Automation Assignment

## Overview
This project contains an automated solution for the Playtech DevQA Engineer internship assignment.

The solution is implemented using:
- Java
- Selenium WebDriver
- JUnit 5
- Maven
- File output (.txt export)

The goal of the assignment is to automate interaction with the Playtech careers website and extract specific information.

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
1. The footer contains a shorter list of teams (total count 11), including potentially overlapping labels such as:
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

### 2. Research areas extraction

The research areas are extracted from the "Research" accordion section under "Life at Playtech".

Since the section is initially collapsed, the test:
- scrolls to the element
- checks whether the accordion is expanded
- expands it if necessary

The research areas are located within a nested unordered list structure.  
A specific XPath was used to target only the inner list items containing the actual research areas.

Only the relevant list items are printed, excluding descriptive text.

### 3. Job link extraction (Tallinn & Tartu)

The goal of this test is to find a job listing in Estonia that is available in both Tallinn and Tartu, and print its link.

Approach:

- Navigate to the "All Jobs" page filtered by Estonia
- Extract job links from the underlying job list
- Iterate through each job link
- Open each job detail page
- Check whether the page contains both "Tallinn" and "Tartu"
- Stop execution once a matching job is found

Notes:

The visible job list differs from the underlying DOM structure, therefore filtering is applied using the data-location='estonia' attribute
Page content is validated using page source text to ensure robustness across different job layouts

## Additional Notes
- Explicit waits are used to handle dynamic content and ensure test stability
- Basic assertions are included to verify that expected data is successfully extracted
- SLF4J logging dependency is added to suppress default logging warnings during test execution

### 4. Results export (.txt)

As a bonus task, test results are exported into a `results.txt` file.

Implementation approach:
- Each test stores its extracted data (teams, research areas, job link)
- Test execution order is controlled using `@TestMethodOrder` and `@Order`
- After all tests complete, results are written to file using an `@AfterAll` method

An example `results.txt` file is included in the repository.