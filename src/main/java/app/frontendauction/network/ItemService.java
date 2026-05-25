package app.frontendauction.network;

import java.net.http.HttpResponse;

import app.frontendauction.dto.request.BidPostRequest;
import app.frontendauction.dto.request.PublishItemRequest;
import app.frontendauction.dto.response.BaseItemResponse;
import app.frontendauction.dto.response.BaseResponse;
import app.frontendauction.dto.response.BidPostResponse;
import app.frontendauction.dto.response.GetItemsResponse;
import app.frontendauction.dto.response.ItemStatusResponse;

/**
 * Service gọi API liên quan tới Items và Bidding.
 * Sử dụng DTO để serialize/deserialize JSON.
 */
public class ItemService {

    // ===== Public Item APIs =====

    /**
     * Lấy danh sách items phân trang (GET /items?page=&size=).
     */
    public static GetItemsResponse getItems(int page, int size) {
        try {
            String path = String.format("/items?page=%d&size=%d", page, size);
            HttpResponse<String> response = ApiClient.get(path);
            return ApiClient.fromJson(response.body(), GetItemsResponse.class);
        } catch (Exception e) {
            GetItemsResponse error = new GetItemsResponse();
            error.setStatus(false);
            error.setMessage("Lỗi kết nối: " + e.getMessage());
            return error;
        }
    }

    /**
     * Lấy chi tiết 1 item (GET /items/{itemId}).
     */
    public static BaseItemResponse getItem(long itemId) {
        try {
            HttpResponse<String> response = ApiClient.get("/items/" + itemId);
            return ApiClient.fromJson(response.body(), BaseItemResponse.class);
        } catch (Exception e) {
            BaseItemResponse error = new BaseItemResponse();
            error.setStatus(false);
            error.setMessage("Lỗi: " + e.getMessage());
            return error;
        }
    }

    /**
     * Lấy trạng thái item (GET /item/status/{itemId}).
     */
    public static ItemStatusResponse getItemStatus(long itemId) {
        try {
            HttpResponse<String> response = ApiClient.get("/item/status/" + itemId);
            return ApiClient.fromJson(response.body(), ItemStatusResponse.class);
        } catch (Exception e) {
            ItemStatusResponse error = new ItemStatusResponse();
            error.setStatus(false);
            error.setMessage("Lỗi: " + e.getMessage());
            return error;
        }
    }

    // ===== Authenticated Item APIs =====

    /**
     * Đăng item mới lên đấu giá (POST /items).
     */
    public static BaseResponse publishItem(String title, String description, long endTime,
                                           double startingPrice, double buyItNowPrice, double bidIncrement) {
        try {
            PublishItemRequest request = new PublishItemRequest(
                    title, description, endTime, startingPrice, buyItNowPrice, bidIncrement);
            String json = ApiClient.toJson(request);
            HttpResponse<String> response = ApiClient.postAuth("/items", json);
            return ApiClient.fromJson(response.body(), BaseResponse.class);
        } catch (Exception e) {
            BaseResponse error = new BaseResponse();
            error.setStatus(false);
            error.setMessage("Lỗi: " + e.getMessage());
            return error;
        }
    }

    /**
     * Hủy item (POST /items/cancel/{itemId}).
     */
    public static BaseResponse cancelItem(long itemId) {
        try {
            HttpResponse<String> response = ApiClient.postAuthNoBody("/items/cancel/" + itemId);
            return ApiClient.fromJson(response.body(), BaseResponse.class);
        } catch (Exception e) {
            BaseResponse error = new BaseResponse();
            error.setStatus(false);
            error.setMessage("Lỗi: " + e.getMessage());
            return error;
        }
    }

    /**
     * Lấy danh sách items do user đăng (GET /items/listings/{username}).
     */
    public static GetItemsResponse getMyListings(String username, int page, int size) {
        try {
            String path = String.format("/items/listings/%s?page=%d&size=%d", username, page, size);
            HttpResponse<String> response = ApiClient.getAuth(path);
            return ApiClient.fromJson(response.body(), GetItemsResponse.class);
        } catch (Exception e) {
            GetItemsResponse error = new GetItemsResponse();
            error.setStatus(false);
            error.setMessage("Lỗi: " + e.getMessage());
            return error;
        }
    }

    // ===== Bidding APIs =====

    /**
     * Đặt bid (POST /bid).
     */
    public static BidPostResponse placeBid(long itemId, double bidAmount) {
        try {
            BidPostRequest request = new BidPostRequest(itemId, bidAmount);
            String json = ApiClient.toJson(request);
            HttpResponse<String> response = ApiClient.postAuth("/bid", json);
            return ApiClient.fromJson(response.body(), BidPostResponse.class);
        } catch (Exception e) {
            BidPostResponse error = new BidPostResponse();
            error.setStatus(false);
            error.setMessage("Lỗi: " + e.getMessage());
            return error;
        }
    }

    /**
     * Mua ngay (POST /buy-now/{itemId}).
     */
    public static BaseResponse buyNow(long itemId) {
        try {
            HttpResponse<String> response = ApiClient.postAuthNoBody("/buy-now/" + itemId);
            return ApiClient.fromJson(response.body(), BaseResponse.class);
        } catch (Exception e) {
            BaseResponse error = new BaseResponse();
            error.setStatus(false);
            error.setMessage("Lỗi: " + e.getMessage());
            return error;
        }
    }

    /**
     * Lấy lịch sử bid của tôi (GET /me/bids).
     */
    public static String getMyBids(int page, int size) {
        try {
            String path = String.format("/me/bids?page=%d&size=%d", page, size);
            HttpResponse<String> response = ApiClient.getAuth(path);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Lấy các items đã thắng (GET /me/wins).
     */
    public static String getMyWins() {
        try {
            HttpResponse<String> response = ApiClient.getAuth("/me/wins");
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Lấy các bid trên 1 item (GET /bids/{itemId}/bids).
     */
    public static String getBidsOnItem(long itemId, int page, int size) {
        try {
            String path = String.format("/bids/%d/bids?page=%d&size=%d", itemId, page, size);
            HttpResponse<String> response = ApiClient.getAuth(path);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }
}
