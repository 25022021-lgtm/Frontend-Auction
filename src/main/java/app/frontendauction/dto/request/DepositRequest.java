package app.frontendauction.dto.request;

/**
 * DTO gửi đi khi nạp tiền.
 * Khớp với backend: POST /users/me/deposit → DepositRequest(amount)
 */
public class DepositRequest {
    private Double amount;

    public DepositRequest(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() { return amount; }
}
