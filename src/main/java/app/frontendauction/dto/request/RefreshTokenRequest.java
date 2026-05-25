package app.frontendauction.dto.request;

/**
 * DTO gửi đi khi refresh token.
 * Khớp với backend: POST /refresh → RefreshTokenRequest(refreshToken)
 */
public class RefreshTokenRequest {
    private String refreshToken;

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() { return refreshToken; }
}
