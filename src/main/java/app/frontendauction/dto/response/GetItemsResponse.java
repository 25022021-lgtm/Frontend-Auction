package app.frontendauction.dto.response;

import java.util.List;

/**
 * DTO nhận về khi lấy danh sách items (GET /items).
 * Khớp với backend: GetItemsResponse extends BaseResponse + items
 */
public class GetItemsResponse extends BaseResponse {
    private List<ItemData> items;

    public List<ItemData> getItems() { return items; }
    public void setItems(List<ItemData> items) { this.items = items; }
}
