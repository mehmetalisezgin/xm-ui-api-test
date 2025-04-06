# Automation Testing Framework

This project is designed as part of an interview task and demonstrates UI and API test automation using modern Java testing tools and frameworks.

## Tech Stack

- **Java 17**
- **TestNG**
- **Maven**
- **IntelliJ IDEA**
- **Selenium WebDriver**
- **Rest Assured**

## Project Structure

```text
xm-ui-api-automation-framework/
├── .idea/                         # IntelliJ project settings
├── logs/                          # Log files (if applicable)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── data/              # Test data, constants
│   │   │   ├── objects/           # POJOs for data models
│   │   │   ├── pages/             # Page Object Model classes
│   │   │   └── utils/             # Utility/helper classes
│   │   └── resources/
│   │       ├── javascript/
│   │       ├── timeouts/
│   │       ├── common.properties  # Environment configs (e.g. browser, URL)
│   │       └── log4j2.properties  # Logging configuration
│
│   └── test/
│       ├── java/
│       │   └── tests/
│       │       ├── api/           # API test classes
│       │       └── ui/            # UI test classes
│       └── resources/
│           └── people-schema.json # JSON schema for API response validation
│
├── target/                        # Compiled output (ignored by Git)
├── .gitignore
├── pom.xml                        # Maven project file
└── README.md                      # Project overview and instructions


## Test Configuration

In the BaseTestClass, the following resolution setup methods are available:

- setUpMaxResolution()
- setUp1024x768Resolution()
- setUp800x600Resolution()

> These methods can be used to dynamically adjust browser window size during test execution.

## JSON Schema Validation
JSON Schema file for response validation is created under:
src/test/resources/people_schema.json
This schema is used to validate the structure of API responses in related test cases.

##  Notes

Configuration such as browser type, base URL, and other common keys are defined in:  
src/main/java/resources/common.properties

Microsoft Edge could not be executed on the local machine due to company policy restrictions.
Tests are successfully running on Chrome and Firefox.

**Step 8 and 9** of the test scenario could not be tested due to limitations or page behavior.
  Details are commented and explained within the relevant test class.

## How to Run Tests
To execute the test class, simply run:

```bash
UI ==>  mvn clean test -Dtest=EconomicCalendarTests
API ==> mvn clean test -Dtest=FilmApiTest

