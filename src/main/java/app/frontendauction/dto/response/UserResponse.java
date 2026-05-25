package app.frontendauction.dto.response;

/**
 * DTO nhận về khi lấy thông tin user (GET /users/me).
 * Khớp với backend: UserResponse(username, displayName, balance)
 */
public class UserResponse {
    private String username;
    private String displayName;
    private Double balance;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}
