package app.frontendauction.dto.request;

/**
 * DTO gửi đi khi đăng item mới.
 * Khớp với backend: POST /items → PublishItemRequest(title, description, endTime, startingPrice, buyItNowPrice, bidIncrement)
 */
public class PublishItemRequest {
    private String title;
    private String description;
    private Long endTime;
    private Double startingPrice;
    private Double buyItNowPrice;
    private Double bidIncrement;

    public PublishItemRequest(String title, String description, Long endTime,
                              Double startingPrice, Double buyItNowPrice, Double bidIncrement) {
        this.title = title;
        this.description = description;
        this.endTime = endTime;
        this.startingPrice = startingPrice;
        this.buyItNowPrice = buyItNowPrice;
        this.bidIncrement = bidIncrement;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Long getEndTime() { return endTime; }
    public Double getStartingPrice() { return startingPrice; }
    public Double getBuyItNowPrice() { return buyItNowPrice; }
    public Double getBidIncrement() { return bidIncrement; }
}
