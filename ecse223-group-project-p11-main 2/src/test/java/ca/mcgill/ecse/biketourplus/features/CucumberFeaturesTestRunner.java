package ca.mcgill.ecse.biketourplus.features;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/resources/",
        glue = "ca.mcgill.ecse.biketourplus.features")
public class CucumberFeaturesTestRunner {
}
