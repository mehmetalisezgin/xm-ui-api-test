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

---

## 🧪 Test Configuration

In the BaseTestClass, the following resolution setup methods are available:

- setUpMaxResolution()
- setUp1024x768Resolution()
- setUp800x600Resolution()

> These methods can be used to dynamically adjust browser window size during test execution.

##  Notes

Configuration such as browser type, base URL, and other common keys are defined in:  
src/main/java/resources/common.properties

**Step 8 and 9** of the test scenario could not be tested due to limitations or page behavior.
  Details are commented and explained within the relevant test class.

## How to Run Tests
To execute the test class, simply run:

```bash
UI ==>  mvn clean test -Dtest=EconomicCalendarTests
API ==> mvn clean test -Dtest=FilmApiTest

