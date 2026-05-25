package app.frontendauction.dto.response;

/**
 * DTO nhận về khi lấy trạng thái item (GET /item/status/{id}).
 * Khớp với backend: ItemStatusGetResponse extends BaseResponse + itemStatus
 */
public class ItemStatusResponse extends BaseResponse {
    private ItemStatusData itemStatus;

    public ItemStatusData getItemStatus() { return itemStatus; }
    public void setItemStatus(ItemStatusData itemStatus) { this.itemStatus = itemStatus; }
}
