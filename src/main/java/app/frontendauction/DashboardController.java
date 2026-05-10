package app.frontendauction;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import app.frontendauction.network.ApiClient;
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
            String profile = AuthService.getMyProfile();
            if (profile != null) {
                String username = ApiClient.extractString(profile, "username");
                String displayName = ApiClient.extractString(profile, "displayName");
                Platform.runLater(() -> {
                    if (usernameLabel != null) {
                        usernameLabel.setText(displayName != null ? displayName : username);
                    }
                });
            }
        }).start();
    }

    private void loadBalance() {
        new Thread(() -> {
            String balanceJson = AuthService.getBalance();
            if (balanceJson != null) {
                double balance = ApiClient.extractDouble(balanceJson, "balance");
                Platform.runLater(() -> {
                    if (balanceLabel != null) {
                        balanceLabel.setText(String.format("$%.2f", balance));
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
            String items = ItemService.getItems(0, 10);
            Platform.runLater(() -> {
                if (contentLabel != null) {
                    contentLabel.setText("Items: " + items);
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
