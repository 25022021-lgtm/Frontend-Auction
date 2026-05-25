package app.frontendauction.dto.request;

/**
 * DTO gửi đi khi đăng ký.
 * Khớp với backend: POST /register → RegisterRequest(username, displayName, password)
 */
public class RegisterRequest {
    private String username;
    private String displayName;
    private String password;

    public RegisterRequest(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }
    public String getPassword() { return password; }
}
