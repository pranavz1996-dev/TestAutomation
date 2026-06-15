package com.automation.pages;

import com.microsoft.playwright.Page;

public class BasePage {

    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected void click(String locator) {
        page.locator(locator).click();
    }

    protected void fill(String locator, String value) {
        page.locator(locator).fill(value);
    }

    protected String getText(String locator) {
        return page.locator(locator).innerText();
    }

    protected boolean isVisible(String locator) {
        return page.locator(locator).isVisible();
    }

    protected void waitForLocator(String locator) {
        page.locator(locator).waitFor();
    }
}
