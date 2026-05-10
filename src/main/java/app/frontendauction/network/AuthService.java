package app.frontendauction.network;

import java.net.http.HttpResponse;

/**
 * Xử lý authentication: login, register, refresh token.
 */
public class AuthService {

    /**
     * Đăng nhập và lưu JWT tokens.
     *
     * @return true nếu login thành công (status 200)
     */
    public static boolean login(String username, String password) {
        try {
            String json = String.format(
                    "{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

            HttpResponse<String> response = ApiClient.post("/login", json);

            if (response.statusCode() == 200) {
                String body = response.body();
                String accessToken = ApiClient.extractString(body, "accessToken");
                String refreshToken = ApiClient.extractString(body, "refreshToken");

                if (accessToken != null && refreshToken != null) {
                    ApiClient.setTokens(accessToken, refreshToken);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Đăng ký user mới.
     *
     * @return response body từ server (JSON)
     */
    public static String register(String username, String displayName, String password) {
        try {
            String json = String.format(
                    "{\"username\":\"%s\",\"displayName\":\"%s\",\"password\":\"%s\"}",
                    username, displayName, password);

            HttpResponse<String> response = ApiClient.post("/register", json);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Không thể kết nối tới server: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Refresh access token bằng refresh token hiện tại.
     *
     * @return true nếu refresh thành công
     */
    public static boolean refreshAccessToken() {
        try {
            String currentRefresh = ApiClient.getRefreshToken();
            if (currentRefresh == null) return false;

            String json = String.format("{\"refreshToken\":\"%s\"}", currentRefresh);
            HttpResponse<String> response = ApiClient.post("/refresh", json);

            if (response.statusCode() == 200) {
                String body = response.body();
                String newAccess = ApiClient.extractString(body, "accessToken");
                String newRefresh = ApiClient.extractString(body, "refreshToken");

                if (newAccess != null && newRefresh != null) {
                    ApiClient.setTokens(newAccess, newRefresh);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Đăng xuất - xóa tokens.
     */
    public static void logout() {
        ApiClient.clearTokens();
    }

    /**
     * Lấy thông tin user hiện tại (GET /users/me).
     *
     * @return JSON string chứa thông tin user
     */
    public static String getMyProfile() {
        try {
            HttpResponse<String> response = ApiClient.getAuth("/users/me");
            if (response.statusCode() == 200) {
                return response.body();
            } else if (response.statusCode() == 401) {
                // Token expired → thử refresh
                if (refreshAccessToken()) {
                    response = ApiClient.getAuth("/users/me");
                    return response.body();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy số dư tài khoản (GET /users/me/balance).
     */
    public static String getBalance() {
        try {
            HttpResponse<String> response = ApiClient.getAuth("/users/me/balance");
            if (response.statusCode() == 200) {
                return response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Nạp tiền vào tài khoản.
     */
    public static String deposit(double amount) {
        try {
            String json = String.format("{\"amount\":%.2f}", amount);
            HttpResponse<String> response = ApiClient.postAuth("/users/me/deposit", json);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }
}
