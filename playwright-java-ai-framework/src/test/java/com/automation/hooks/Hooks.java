package com.automation.hooks;

import com.automation.config.ConfigReader;
import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private static Playwright playwright;
    private static Browser browser;
    public static Page page;

    @Before
    public void setUp() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
            .setHeadless(ConfigReader.isHeadless());

        String browserName = ConfigReader.getBrowser();
        browser = switch (browserName.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit"  -> playwright.webkit().launch(options);
            default        -> playwright.chromium().launch(options);
        };

        page = browser.newPage();
        page.navigate(ConfigReader.getBaseUrl());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
