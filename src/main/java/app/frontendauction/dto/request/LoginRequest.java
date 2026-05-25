package app.frontendauction.dto.request;

/**
 * DTO gửi đi khi login.
 * Khớp với backend: POST /login → LoginRequest(username, password)
 */
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
