
**Currency API**
This API is used to fetch the current currency values.

**Language used:** Java

**library used :** Cucumber, Rest Assured ,JUnit,Jdk 22

**Build tool:** Maven

**Setup the project:**

1.git clone https://github.com/Brijendra9322/currencyAPI.git
branch :main

2. **install cucumber,gherkin plugins in Intellij**
   
**To run the test:**

**Run the runner file:**
src-> test/java/runner/TestRunner.java (right click)

**Run the test using Maven:**

mvn clean verify

**report is under target folder:**

Cucumber-reports.html (open in chrome or any browser)

**Maven genrated report:**

target/cucumber-reports/cucumber-html-reports/overview-reports.html

![image](https://github.com/Brijendra9322/currencyAPI/assets/13295851/69d21c5f-6d68-4b47-a5fc-e4c5c290368d)


**Project Structure:**

src/test/java/runner
src/test/java/stepDfinition
src/test/java/utils
src/test/resources/features/currency.feature

**API Reference:**
https://open.er-api.com
endpoint : /v6/latest/USD
