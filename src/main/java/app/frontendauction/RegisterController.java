package app.frontendauction;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import app.frontendauction.network.ApiClient;
import app.frontendauction.network.AuthService;

/**
 * Controller cho màn hình Register.
 * Gọi backend POST /register trên background thread.
 */
public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private TextField displayNameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;
    @FXML private Button registerButton;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String displayName = displayNameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validation phía client
        if (username.isEmpty() || displayName.isEmpty() || password.isEmpty()) {
            showStatus("Vui lòng điền đầy đủ thông tin!", true);
            return;
        }

        if (username.contains(" ")) {
            showStatus("Username không được chứa khoảng trắng!", true);
            return;
        }

        if (password.contains(" ")) {
            showStatus("Password không được chứa khoảng trắng!", true);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showStatus("Mật khẩu xác nhận không khớp!", true);
            return;
        }

        registerButton.setDisable(true);
        showStatus("Đang đăng ký...", false);

        new Thread(() -> {
            String response = AuthService.register(username, displayName, password);
            boolean success = ApiClient.extractBoolean(response, "status");
            String message = ApiClient.extractMessage(response);

            Platform.runLater(() -> {
                registerButton.setDisable(false);
                if (success) {
                    showStatus("Đăng ký thành công! Chuyển tới Login...", false);
                    // Chờ 1.5 giây rồi chuyển sang Login
                    new Thread(() -> {
                        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                        Platform.runLater(() -> SceneManager.switchScene("fxml/login.fxml"));
                    }).start();
                } else {
                    showStatus(message, true);
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
            statusLabel.setStyle("-fx-text-fill: #ff4444; -fx-font-size: 12;");
        } else {
            statusLabel.setStyle("-fx-text-fill: #39ff14; -fx-font-size: 12;");
        }
    }
}
