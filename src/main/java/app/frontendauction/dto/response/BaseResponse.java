package app.frontendauction.dto.response;

/**
 * DTO cơ bản cho mọi response từ backend.
 * Khớp với backend: BaseResponse(status, message)
 */
public class BaseResponse {
    private boolean status;
    private String message;

    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
