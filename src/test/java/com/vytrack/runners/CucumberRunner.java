package com.vytrack.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/Activities",
                     "src/test/resources/features/fleet"

        },
        glue = "com/vytrack/step_definitions",
        dryRun = false,
        tags = "",
        plugin = {""
        }
)
public class CucumberRunner {
}
