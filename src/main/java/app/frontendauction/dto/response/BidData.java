package app.frontendauction.dto.response;

/**
 * DTO đại diện cho 1 bid (phía client).
 * Khớp với backend Bid entity serialize ra JSON.
 */
public class BidData {
    private Long bidId;
    private Double bidAmount;
    private Long time;

    public Long getBidId() { return bidId; }
    public void setBidId(Long bidId) { this.bidId = bidId; }

    public Double getBidAmount() { return bidAmount; }
    public void setBidAmount(Double bidAmount) { this.bidAmount = bidAmount; }

    public Long getTime() { return time; }
    public void setTime(Long time) { this.time = time; }
}
