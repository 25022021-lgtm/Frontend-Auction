package app.frontendauction.controller;

import javafx.fxml.FXML;

/**
 * Controller cho Landing Page.
 * Xử lý nút Login và Sign Up để chuyển sang màn hình tương ứng.
 */
public class LandingPageController {

    @FXML
    private void handleLogin() {
        SceneManager.switchScene("fxml/login.fxml");
    }

    @FXML
    private void handleSignUp() {
        SceneManager.switchScene("fxml/register.fxml");
    }
}
