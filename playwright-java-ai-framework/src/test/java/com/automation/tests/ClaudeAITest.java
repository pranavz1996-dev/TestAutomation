package com.automation.tests;

import com.automation.ai.TestDataGenerator;
import com.automation.ai.TestDataGenerator.UserData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClaudeAITest {

    private final TestDataGenerator generator = new TestDataGenerator();

    @Test
    void claudeShouldGenerateTestUsers() throws Exception {
        System.out.println("\n========================================");
        System.out.println("  CLAUDE AI - TEST DATA GENERATION");
        System.out.println("========================================");

        List<UserData> users = generator.generateUsers(3);

        assertNotNull(users, "Users list should not be null");
        assertEquals(3, users.size(), "Should generate exactly 3 users");

        System.out.println("\nGenerated Users:");
        System.out.println("----------------");
        for (int i = 0; i < users.size(); i++) {
            UserData u = users.get(i);
            System.out.printf("User %d: %s%n", i + 1, u);
            assertNotNull(u.name,     "Name should not be null");
            assertNotNull(u.email,    "Email should not be null");
            assertNotNull(u.password, "Password should not be null");
            assertNotNull(u.phone,    "Phone should not be null");
            assertNotNull(u.city,     "City should not be null");
            assertTrue(u.email.contains("@"), "Email should be valid");
            assertEquals(10, u.phone.length(), "Phone should be 10 digits");
        }

        System.out.println("\nAll assertions passed!");
        System.out.println("========================================\n");
    }

    @Test
    void claudeShouldGenerateBugReport() throws Exception {
        System.out.println("\n========================================");
        System.out.println("  CLAUDE AI - BUG REPORT GENERATION");
        System.out.println("========================================");

        String report = generator.generateBugReport(
            "Login_With_Valid_Credentials",
            "Click on Login button",
            "Element not found: #login-btn — timeout 30000ms exceeded"
        );

        assertNotNull(report, "Bug report should not be null");
        assertFalse(report.isBlank(), "Bug report should not be empty");

        System.out.println("\nAuto-Generated Bug Report:");
        System.out.println("---------------------------");
        System.out.println(report);
        System.out.println("========================================\n");
    }

    @Test
    void claudeShouldSuggestBrokenLocator() throws Exception {
        System.out.println("\n========================================");
        System.out.println("  CLAUDE AI - SELF-HEALING LOCATOR");
        System.out.println("========================================");

        String fakeHtml = """
            <div class="login-container">
                <input type="email" id="user-email" placeholder="Enter Email" class="form-input"/>
                <input type="password" id="user-pass" placeholder="Enter Password" class="form-input"/>
                <button type="submit" class="btn-login" data-testid="submit-login">Sign In</button>
            </div>
            """;

        String suggestion = generator.suggestLocator("#login-btn", fakeHtml);

        assertNotNull(suggestion, "Locator suggestion should not be null");
        assertFalse(suggestion.isBlank(), "Locator suggestion should not be empty");

        System.out.println("\nBroken Locator : #login-btn");
        System.out.println("Claude Suggests: " + suggestion);
        System.out.println("========================================\n");
    }
}
