package app.frontendauction.dto.response;

/**
 * DTO nhận về sau khi đặt bid (POST /bid).
 * Khớp với backend: BidPostResponse extends BaseResponse + bid
 */
public class BidPostResponse extends BaseResponse {
    private BidData bid;

    public BidData getBid() { return bid; }
    public void setBid(BidData bid) { this.bid = bid; }
}
