package com.automation.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ClaudeClient {

    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final String MODEL = "claude-sonnet-4-6";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiKey;

    public ClaudeClient() {
        this.apiKey = System.getenv("CLAUDE_API_KEY");
        if (this.apiKey == null || this.apiKey.isBlank()) {
            throw new IllegalStateException("CLAUDE_API_KEY environment variable is not set.");
        }
    }

    public String ask(String prompt) throws IOException {
        Map<String, Object> body = Map.of(
            "model", MODEL,
            "max_tokens", 1024,
            "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        String json = mapper.writeValueAsString(body);

        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("x-api-key", apiKey)
            .addHeader("anthropic-version", "2023-06-01")
            .addHeader("content-type", "application/json")
            .post(RequestBody.create(json, JSON))
            .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            Map<?, ?> parsed = mapper.readValue(responseBody, Map.class);

            // API returned an error — surface it clearly
            if (parsed.containsKey("error")) {
                Map<?, ?> error = (Map<?, ?>) parsed.get("error");
                throw new IOException("Claude API error: " + error.get("type") + " — " + error.get("message"));
            }

            List<?> content = (List<?>) parsed.get("content");
            if (content == null || content.isEmpty()) {
                throw new IOException("Claude API returned empty content. Full response: " + responseBody);
            }

            Map<?, ?> first = (Map<?, ?>) content.get(0);
            return (String) first.get("text");
        }
    }
}
