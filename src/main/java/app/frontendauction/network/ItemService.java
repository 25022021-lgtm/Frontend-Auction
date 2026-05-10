package app.frontendauction.network;

import java.net.http.HttpResponse;

/**
 * Service gọi API liên quan tới Items và Bidding.
 */
public class ItemService {

    // ===== Public Item APIs =====

    /**
     * Lấy danh sách items phân trang (GET /items?page=&size=).
     */
    public static String getItems(int page, int size) {
        try {
            String path = String.format("/items?page=%d&size=%d", page, size);
            HttpResponse<String> response = ApiClient.get(path);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi kết nối: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Lấy chi tiết 1 item (GET /items/{itemId}).
     */
    public static String getItem(long itemId) {
        try {
            HttpResponse<String> response = ApiClient.get("/items/" + itemId);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Lấy trạng thái item (GET /item/status/{itemId}).
     */
    public static String getItemStatus(long itemId) {
        try {
            HttpResponse<String> response = ApiClient.get("/item/status/" + itemId);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }

    // ===== Authenticated Item APIs =====

    /**
     * Đăng item mới lên đấu giá (POST /items).
     */
    public static String publishItem(String title, String description, long endTime,
                                     double startingPrice, double buyItNowPrice, double bidIncrement) {
        try {
            String json = String.format(
                    "{\"title\":\"%s\",\"description\":\"%s\",\"endTime\":%d," +
                            "\"startingPrice\":%.2f,\"buyItNowPrice\":%.2f,\"bidIncrement\":%.2f}",
                    title, description, endTime, startingPrice, buyItNowPrice, bidIncrement);
            HttpResponse<String> response = ApiClient.postAuth("/items", json);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Hủy item (POST /items/cancel/{itemId}).
     */
    public static String cancelItem(long itemId) {
        try {
            HttpResponse<String> response = ApiClient.postAuthNoBody("/items/cancel/" + itemId);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Lấy danh sách items do user đăng (GET /items/listings/{username}).
     */
    public static String getMyListings(String username, int page, int size) {
        try {
            String path = String.format("/items/listings/%s?page=%d&size=%d", username, page, size);
            HttpResponse<String> response = ApiClient.getAuth(path);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }

    // ===== Bidding APIs =====

    /**
     * Đặt bid (POST /bid).
     */
    public static String placeBid(long itemId, double bidAmount) {
        try {
            String json = String.format("{\"itemId\":%d,\"bidAmount\":%.2f}", itemId, bidAmount);
            HttpResponse<String> response = ApiClient.postAuth("/bid", json);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Mua ngay (POST /buy-now/{itemId}).
     */
    public static String buyNow(long itemId) {
        try {
            HttpResponse<String> response = ApiClient.postAuthNoBody("/buy-now/" + itemId);
            return response.body();
        } catch (Exception e) {
            return "{\"status\":false,\"message\":\"Lỗi: " + e.getMessage() + "\"}";
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
