package app.frontendauction.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import com.google.gson.Gson;

/**
 * Singleton HTTP client cho mọi request tới backend.
 * Quản lý base URL, JWT tokens, Gson instance và các phương thức tiện ích.
 */
public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final Gson gson = new Gson();

    // JWT tokens - được set sau khi login thành công
    private static String accessToken = null;
    private static String refreshToken = null;

    // ===== Gson =====

    /**
     * Lấy Gson instance dùng chung.
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * Chuyển object → JSON string.
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Chuyển JSON string → object.
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

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
}
