package app.frontendauction.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

import javafx.application.Platform;

/**
 * Service SSE (Server-Sent Events) để nhận giá item real-time.
 * Backend endpoint: GET /items/stream/{itemId} (produces text/event-stream)
 */
public class PriceStreamService {

    private volatile boolean running = false;
    private Thread streamThread;

    /**
     * Bắt đầu subscribe vào price stream của 1 item.
     *
     * @param itemId      ID của item cần theo dõi
     * @param onPriceUpdate callback được gọi trên JavaFX thread khi có giá mới
     * @param onError       callback được gọi khi có lỗi
     */
    public void startStreaming(long itemId, Consumer<Double> onPriceUpdate, Consumer<String> onError) {
        stop(); // Dừng stream cũ nếu có

        running = true;
        streamThread = new Thread(() -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(ApiClient.getBaseUrl() + "/items/stream/" + itemId))
                        .header("Accept", "text/event-stream")
                        .GET()
                        .build();

                HttpResponse<java.util.stream.Stream<String>> response =
                        client.send(request, HttpResponse.BodyHandlers.ofLines());

                response.body().forEach(line -> {
                    if (!running) return;

                    // SSE format: "data:123.45"
                    if (line.startsWith("data:")) {
                        try {
                            String priceStr = line.substring(5).trim();
                            double price = Double.parseDouble(priceStr);
                            Platform.runLater(() -> onPriceUpdate.accept(price));
                        } catch (NumberFormatException e) {
                            // Bỏ qua dòng không phải số
                        }
                    }
                });
            } catch (Exception e) {
                if (running) {
                    Platform.runLater(() -> onError.accept("Stream error: " + e.getMessage()));
                }
            }
        }, "PriceStream-" + itemId);

        streamThread.setDaemon(true); // Không chặn app thoát
        streamThread.start();
    }

    /**
     * Dừng stream hiện tại.
     */
    public void stop() {
        running = false;
        if (streamThread != null) {
            streamThread.interrupt();
            streamThread = null;
        }
    }

    public boolean isRunning() {
        return running;
    }
}
