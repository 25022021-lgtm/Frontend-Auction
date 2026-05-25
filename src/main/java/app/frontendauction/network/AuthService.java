package app.frontendauction.network;

import java.net.http.HttpResponse;

import app.frontendauction.dto.request.DepositRequest;
import app.frontendauction.dto.request.LoginRequest;
import app.frontendauction.dto.request.RefreshTokenRequest;
import app.frontendauction.dto.request.RegisterRequest;
import app.frontendauction.dto.response.AuthResponse;
import app.frontendauction.dto.response.BalanceResponse;
import app.frontendauction.dto.response.BaseResponse;
import app.frontendauction.dto.response.UserResponse;

/**
 * Xử lý authentication: login, register, refresh token.
 * Sử dụng DTO để serialize/deserialize JSON thay vì build string thủ công.
 */
public class AuthService {

    /**
     * Đăng nhập và lưu JWT tokens.
     *
     * @return true nếu login thành công (status 200)
     */
    public static boolean login(String username, String password) {
        try {
            LoginRequest request = new LoginRequest(username, password);
            String json = ApiClient.toJson(request);

            HttpResponse<String> response = ApiClient.post("/login", json);

            if (response.statusCode() == 200) {
                AuthResponse authResponse = ApiClient.fromJson(response.body(), AuthResponse.class);

                if (authResponse.getAccessToken() != null && authResponse.getRefreshToken() != null) {
                    ApiClient.setTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
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
     * @return BaseResponse từ server
     */
    public static BaseResponse register(String username, String displayName, String password) {
        try {
            RegisterRequest request = new RegisterRequest(username, displayName, password);
            String json = ApiClient.toJson(request);

            HttpResponse<String> response = ApiClient.post("/register", json);
            return ApiClient.fromJson(response.body(), BaseResponse.class);
        } catch (Exception e) {
            BaseResponse error = new BaseResponse();
            error.setStatus(false);
            error.setMessage("Không thể kết nối tới server: " + e.getMessage());
            return error;
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

            RefreshTokenRequest request = new RefreshTokenRequest(currentRefresh);
            String json = ApiClient.toJson(request);
            HttpResponse<String> response = ApiClient.post("/refresh", json);

            if (response.statusCode() == 200) {
                AuthResponse authResponse = ApiClient.fromJson(response.body(), AuthResponse.class);

                if (authResponse.getAccessToken() != null && authResponse.getRefreshToken() != null) {
                    ApiClient.setTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
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
     * @return UserResponse chứa thông tin user, hoặc null nếu lỗi
     */
    public static UserResponse getMyProfile() {
        try {
            HttpResponse<String> response = ApiClient.getAuth("/users/me");
            if (response.statusCode() == 200) {
                return ApiClient.fromJson(response.body(), UserResponse.class);
            } else if (response.statusCode() == 401) {
                // Token expired → thử refresh
                if (refreshAccessToken()) {
                    response = ApiClient.getAuth("/users/me");
                    return ApiClient.fromJson(response.body(), UserResponse.class);
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
    public static BalanceResponse getBalance() {
        try {
            HttpResponse<String> response = ApiClient.getAuth("/users/me/balance");
            if (response.statusCode() == 200) {
                return ApiClient.fromJson(response.body(), BalanceResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Nạp tiền vào tài khoản.
     */
    public static BaseResponse deposit(double amount) {
        try {
            DepositRequest request = new DepositRequest(amount);
            String json = ApiClient.toJson(request);
            HttpResponse<String> response = ApiClient.postAuth("/users/me/deposit", json);
            return ApiClient.fromJson(response.body(), BaseResponse.class);
        } catch (Exception e) {
            BaseResponse error = new BaseResponse();
            error.setStatus(false);
            error.setMessage("Lỗi: " + e.getMessage());
            return error;
        }
    }
}
