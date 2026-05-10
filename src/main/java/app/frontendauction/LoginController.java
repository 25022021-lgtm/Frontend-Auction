package app.frontendauction;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import app.frontendauction.network.AuthService;

/**
 * Controller cho màn hình Login.
 * Gọi backend POST /login trên background thread.
 */
public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button loginButton;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Vui lòng nhập username và password!", true);
            return;
        }

        // Disable nút để tránh click nhiều lần
        loginButton.setDisable(true);
        showStatus("Đang đăng nhập...", false);

        // Network call trên background thread - KHÔNG block UI
        new Thread(() -> {
            boolean success = AuthService.login(username, password);
            Platform.runLater(() -> {
                loginButton.setDisable(false);
                if (success) {
                    showStatus("Đăng nhập thành công! ✓", false);
                    // Chuyển sang Dashboard
                    SceneManager.switchScene("fxml/framework.fxml");
                } else {
                    showStatus("Sai username hoặc password!", true);
                }
            });
        }).start();
    }

    @FXML
    private void handleBack() {
        SceneManager.switchScene("fxml/landingPage.fxml");
    }

    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);
        if (isError) {
            statusLabel.setStyle("-fx-text-fill: #ff4444;");
        } else {
            statusLabel.setStyle("-fx-text-fill: #39ff14;");
        }
    }
}
