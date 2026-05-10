package app.frontendauction.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

/**
 * Singleton HTTP client cho mọi request tới backend.
 * Quản lý base URL, JWT tokens, và các phương thức tiện ích.
 */
public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    // JWT tokens - được set sau khi login thành công
    private static String accessToken = null;
    private static String refreshToken = null;

    // ===== Token Management =====

    public static void setTokens(String access, String refresh) {
        accessToken = access;
        refreshToken = refresh;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static boolean isLoggedIn() {
        return accessToken != null && !accessToken.isEmpty();
    }

    public static void clearTokens() {
        accessToken = null;
        refreshToken = null;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static HttpClient getClient() {
        return client;
    }

    // ===== Public Requests (không cần auth) =====

    /**
     * Gửi GET request public.
     */
    public static HttpResponse<String> get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return client.send(request, BodyHandlers.ofString());
    }

    /**
     * Gửi POST request public với JSON body.
     */
    public static HttpResponse<String> post(String path, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, BodyHandlers.ofString());
    }

    // ===== Authenticated Requests (cần JWT) =====

    /**
     * Gửi GET request với Bearer token.
     */
    public static HttpResponse<String> getAuth(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
        return client.send(request, BodyHandlers.ofString());
    }

    /**
     * Gửi POST request với Bearer token và JSON body.
     */
    public static HttpResponse<String> postAuth(String path, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .POST(BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, BodyHandlers.ofString());
    }

    /**
     * Gửi POST request auth không có body (e.g. cancel item, buy now).
     */
    public static HttpResponse<String> postAuthNoBody(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .POST(BodyPublishers.noBody())
                .build();
        return client.send(request, BodyHandlers.ofString());
    }

    // ===== JSON Parsing Tiện ích (đơn giản, không cần thư viện) =====

    /**
     * Trích xuất giá trị string từ JSON.
     * Ví dụ: extractString(json, "accessToken") → "eyJhbG..."
     */
    public static String extractString(String json, String field) {
        String key = "\"" + field + "\":\"";
        int start = json.indexOf(key);
        if (start == -1) return null;
        start += key.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return null;
        return json.substring(start, end);
    }

    /**
     * Trích xuất giá trị boolean từ JSON.
     * Ví dụ: extractBoolean(json, "status") → true
     */
    public static boolean extractBoolean(String json, String field) {
        String key = "\"" + field + "\":";
        int start = json.indexOf(key);
        if (start == -1) return false;
        start += key.length();
        return json.substring(start).trim().startsWith("true");
    }

    /**
     * Trích xuất giá trị number từ JSON.
     * Ví dụ: extractDouble(json, "balance") → 1000.0
     */
    public static double extractDouble(String json, String field) {
        String key = "\"" + field + "\":";
        int start = json.indexOf(key);
        if (start == -1) return 0.0;
        start += key.length();
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (Character.isDigit(c) || c == '.' || c == '-') {
                sb.append(c);
            } else if (!sb.isEmpty()) {
                break;
            }
        }
        try {
            return Double.parseDouble(sb.toString());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Trích xuất message từ BaseResponse JSON.
     */
    public static String extractMessage(String json) {
        String msg = extractString(json, "message");
        return msg != null ? msg : json;
    }
}
