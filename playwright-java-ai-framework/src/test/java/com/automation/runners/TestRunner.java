package com.automation.runners;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm, html:reports/cucumber-report.html")
@ConfigurationParameter(key = "cucumber.glue", value = "com.automation.hooks, com.automation.stepdefs")
@ConfigurationParameter(key = "cucumber.publish.quiet", value = "true")
public class TestRunner {
}
