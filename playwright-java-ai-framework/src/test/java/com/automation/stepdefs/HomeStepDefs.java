package com.automation.stepdefs;

import com.automation.hooks.Hooks;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeStepDefs {

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        // Navigation is handled in Hooks @Before
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedTitle) {
        String actualTitle = Hooks.page.title();
        assertTrue(actualTitle.contains(expectedTitle),
            "Expected title to contain: " + expectedTitle + " but got: " + actualTitle);
    }
}
