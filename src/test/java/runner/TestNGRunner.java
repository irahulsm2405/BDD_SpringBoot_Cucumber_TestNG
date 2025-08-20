package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features", 
				 glue = "stepDefinitions.controller", 
				 monochrome = true, 
				 tags = "not @Ignore" 
			 //, plugin = {"pretty", "html:target/cucumber.html", "json:target/cucumber.json" }
			 //, dryRun = true
)
public class TestNGRunner extends AbstractTestNGCucumberTests {

}
