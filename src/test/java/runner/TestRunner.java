package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"stepDefinition"},
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-reports"},
        monochrome = true,
        tags = "@regression or @sanity or @smoke",
        dryRun = false
)

public class TestRunner {
}
