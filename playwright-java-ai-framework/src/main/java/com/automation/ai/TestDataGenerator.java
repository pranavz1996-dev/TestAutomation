package com.automation.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {

    private final ClaudeClient claude = new ClaudeClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<UserData> generateUsers(int count) throws IOException {
        String prompt = """
            Generate %d realistic Indian user registration test data objects.
            Return ONLY a valid JSON array, no explanation, no markdown, no code blocks.
            Each object must have exactly these fields:
            {
              "name": "full name",
              "email": "valid email",
              "password": "strong password with letters and numbers",
              "phone": "10 digit Indian mobile number starting with 9, 8, 7 or 6",
              "city": "Indian city name"
            }
            """.formatted(count);

        String response = claude.ask(prompt);
        JsonNode array = mapper.readTree(response);

        List<UserData> users = new ArrayList<>();
        for (JsonNode node : array) {
            UserData user = new UserData();
            user.name     = node.get("name").asText();
            user.email    = node.get("email").asText();
            user.password = node.get("password").asText();
            user.phone    = node.get("phone").asText();
            user.city     = node.get("city").asText();
            users.add(user);
        }
        return users;
    }

    public String generateBugReport(String testName, String failedStep, String errorMessage) throws IOException {
        String prompt = """
            Write a professional bug report for a failed automation test.
            Test Name: %s
            Failed Step: %s
            Error Message: %s
            Format: Title, Environment, Steps to Reproduce, Expected Result, Actual Result, Severity.
            Keep it concise and professional.
            """.formatted(testName, failedStep, errorMessage);

        return claude.ask(prompt);
    }

    public String suggestLocator(String brokenLocator, String pageHtml) throws IOException {
        String trimmedHtml = pageHtml.length() > 3000 ? pageHtml.substring(0, 3000) : pageHtml;
        String prompt = """
            The CSS locator '%s' is broken in an automation test.
            Below is the relevant page HTML. Suggest the best replacement CSS selector or XPath.
            Return only the locator string, nothing else.
            HTML: %s
            """.formatted(brokenLocator, trimmedHtml);

        return claude.ask(prompt);
    }

    public static class UserData {
        public String name;
        public String email;
        public String password;
        public String phone;
        public String city;

        @Override
        public String toString() {
            return String.format("Name=%-25s Email=%-35s Phone=%s City=%s", name, email, phone, city);
        }
    }
}
