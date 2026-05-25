package app.frontendauction.dto.response;

/**
 * DTO nhận về khi lấy chi tiết 1 item (GET /items/{id}).
 * Khớp với backend: BaseItemResponse extends BaseResponse + item
 */
public class BaseItemResponse extends BaseResponse {
    private ItemData item;

    public ItemData getItem() { return item; }
    public void setItem(ItemData item) { this.item = item; }
}
