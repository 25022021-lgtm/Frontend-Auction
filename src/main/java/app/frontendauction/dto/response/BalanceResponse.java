package app.frontendauction.dto.response;

/**
 * DTO nhận về khi lấy số dư (GET /users/me/balance).
 * Khớp với backend: BalanceResponse extends BaseResponse + balance
 */
public class BalanceResponse extends BaseResponse {
    private Double balance;

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}
