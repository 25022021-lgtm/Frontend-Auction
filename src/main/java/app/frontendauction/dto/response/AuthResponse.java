package app.frontendauction.dto.response;

/**
 * DTO nhận về sau khi login/refresh thành công.
 * Khớp với backend: AuthResponse extends BaseResponse + accessToken, refreshToken
 */
public class AuthResponse extends BaseResponse {
    private String accessToken;
    private String refreshToken;

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
