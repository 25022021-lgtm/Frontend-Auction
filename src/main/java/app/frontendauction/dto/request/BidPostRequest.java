package app.frontendauction.dto.request;

/**
 * DTO gửi đi khi đặt bid.
 * Khớp với backend: POST /bid → BidPostRequest(itemId, bidAmount)
 */
public class BidPostRequest {
    private Long itemId;
    private Double bidAmount;

    public BidPostRequest(Long itemId, Double bidAmount) {
        this.itemId = itemId;
        this.bidAmount = bidAmount;
    }

    public Long getItemId() { return itemId; }
    public Double getBidAmount() { return bidAmount; }
}
