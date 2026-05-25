package app.frontendauction.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import app.frontendauction.dto.response.BalanceResponse;
import app.frontendauction.dto.response.GetItemsResponse;
import app.frontendauction.dto.response.ItemData;
import app.frontendauction.dto.response.UserResponse;
import app.frontendauction.network.AuthService;
import app.frontendauction.network.ItemService;

/**
 * Controller cho Dashboard chính (framework.fxml).
 * Load thông tin user và hiển thị sau khi login thành công.
 */
public class DashboardController {

    @FXML private Label usernameLabel;
    @FXML private Label balanceLabel;
    @FXML private Label contentLabel;

    @FXML
    public void initialize() {
        // Load user profile khi dashboard mở
        loadUserProfile();
        loadBalance();
    }

    private void loadUserProfile() {
        new Thread(() -> {
            UserResponse profile = AuthService.getMyProfile();
            if (profile != null) {
                Platform.runLater(() -> {
                    if (usernameLabel != null) {
                        String display = profile.getDisplayName() != null
                                ? profile.getDisplayName()
                                : profile.getUsername();
                        usernameLabel.setText(display);
                    }
                });
            }
        }).start();
    }

    private void loadBalance() {
        new Thread(() -> {
            BalanceResponse balanceResponse = AuthService.getBalance();
            if (balanceResponse != null && balanceResponse.getBalance() != null) {
                Platform.runLater(() -> {
                    if (balanceLabel != null) {
                        balanceLabel.setText(String.format("$%.2f", balanceResponse.getBalance()));
                    }
                });
            }
        }).start();
    }

    @FXML
    private void handleDashboard() {
        if (contentLabel != null) {
            contentLabel.setText("Dashboard - Tổng quan");
        }
        loadBalance();
    }

    @FXML
    private void handleExplore() {
        if (contentLabel != null) {
            contentLabel.setText("Đang tải danh sách items...");
        }
        new Thread(() -> {
            GetItemsResponse itemsResponse = ItemService.getItems(0, 10);
            Platform.runLater(() -> {
                if (contentLabel != null) {
                    if (itemsResponse.getStatus() && itemsResponse.getItems() != null) {
                        StringBuilder sb = new StringBuilder("Items:\n");
                        for (ItemData item : itemsResponse.getItems()) {
                            sb.append(String.format("• [%d] %s\n", item.getItemId(), item.getTitle()));
                        }
                        contentLabel.setText(sb.toString());
                    } else {
                        contentLabel.setText("Lỗi: " + itemsResponse.getMessage());
                    }
                }
            });
        }).start();
    }

    @FXML
    private void handleMyListing() {
        if (contentLabel != null) {
            contentLabel.setText("Đang tải listings...");
        }
        // TODO: Cần username, sẽ implement sau khi có profile
    }

    @FXML
    private void handleWatchlist() {
        if (contentLabel != null) {
            contentLabel.setText("Watchlist - Coming soon");
        }
    }

    @FXML
    private void handleLogout() {
        AuthService.logout();
        SceneManager.switchScene("fxml/landingPage.fxml");
    }
}
