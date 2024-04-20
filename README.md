**Currency API**
This API is used to fetch the current currency values.

**Language used:** Java

**git clone:** https://github.com/Brijendra9322/currencyAPI.git
**branch :** master

**library used :** Cucumber, Rest Assured ,JUnit,Jdk 22

**Build tool:** Maven

**To run the test:**

**Run the runner file:**
src-> test/java/runner/TestRunner.java (right click)

**report is under target folder:**
Cucumber-reports.html (open in chrome or any browser)

**POM.xml**

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"> <modelVersion>4.0.0</modelVersion>

<groupId>org.example</groupId>
<artifactId>currency-api</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>jar</packaging>

<name>currency-api</name>
<url>http://maven.apache.org</url>

<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.release>17</maven.compiler.release>
</properties>

<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
        <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.json/json -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20240303</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/io.rest-assured/json-schema-validator -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>json-schema-validator</artifactId>
        <version>5.4.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>1.7.32</version> <!-- Use the appropriate version -->
    </dependency>
    <!-- https://mvnrepository.com/artifact/io.rest-assured/rest-assured -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.everit.json/org.everit.json.schema -->
    <!-- https://mvnrepository.com/artifact/org.glassfish/javax.json -->
    <!-- https://mvnrepository.com/artifact/com.github.java-json-tools/json-schema-validator -->

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.16.1</version> <!-- Or the latest version -->
    </dependency>
    <!-- https://mvnrepository.com/artifact/com.jayway.jsonpath/json-path -->
    <dependency>
        <groupId>com.jayway.jsonpath</groupId>
        <artifactId>json-path</artifactId>
        <version>2.9.0</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.16.1</version>
        <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.testng/testng -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.1</version>
        <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-junit -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-junit</artifactId>
        <version>7.16.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-compiler-plugin -->
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.13.0</version>
            <configuration>
                <source>1.8</source> <!-- or your Java version -->
                <target>1.8</target> <!-- or your Java version -->
            </configuration>
        </plugin>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.2.5</version>
            <configuration>
                <includes>
                    <!--                        <include>**/*TestRunner.java</include>-->
                    <include>**/runner/TestRunner.java</include>
                </includes>
                <testFailureIgnore>true</testFailureIgnore>
            </configuration>
        </plugin>
    </plugins>
</build>
</project>

**Project Structure:**

src/test/java/runner
src/test/java/stepDfinition
src/test/java/utils
src/test/resources/features/currency.feature

**API Reference:**
https://open.er-api.com
endpoint : /v6/latest/USD
