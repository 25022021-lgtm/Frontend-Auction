package app.frontendauction.dto.response;

/**
 * DTO đại diện cho trạng thái item (phía client).
 * Khớp với backend ItemStatus entity serialize ra JSON.
 */
public class ItemStatusData {
    private Long id;
    private Double currentPrice;
    private String highestBidUser;
    private Long startTime;
    private Long endTime;
    private Long maxEndTime;
    private Double startingPrice;
    private Double buyItNowPrice;
    private Double bidIncrement;
    private String itemStatus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }

    public String getHighestBidUser() { return highestBidUser; }
    public void setHighestBidUser(String highestBidUser) { this.highestBidUser = highestBidUser; }

    public Long getStartTime() { return startTime; }
    public void setStartTime(Long startTime) { this.startTime = startTime; }

    public Long getEndTime() { return endTime; }
    public void setEndTime(Long endTime) { this.endTime = endTime; }

    public Long getMaxEndTime() { return maxEndTime; }
    public void setMaxEndTime(Long maxEndTime) { this.maxEndTime = maxEndTime; }

    public Double getStartingPrice() { return startingPrice; }
    public void setStartingPrice(Double startingPrice) { this.startingPrice = startingPrice; }

    public Double getBuyItNowPrice() { return buyItNowPrice; }
    public void setBuyItNowPrice(Double buyItNowPrice) { this.buyItNowPrice = buyItNowPrice; }

    public Double getBidIncrement() { return bidIncrement; }
    public void setBidIncrement(Double bidIncrement) { this.bidIncrement = bidIncrement; }

    public String getItemStatus() { return itemStatus; }
    public void setItemStatus(String itemStatus) { this.itemStatus = itemStatus; }
}
