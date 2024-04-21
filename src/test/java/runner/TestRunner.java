package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"stepDefinition"},
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-reports.html"},
        monochrome = true,
        tags = "@valid or @invalid or @schema",
        dryRun = false
)

public class TestRunner {
}
