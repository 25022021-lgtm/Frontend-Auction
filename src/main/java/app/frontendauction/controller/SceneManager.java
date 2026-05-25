package app.frontendauction.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Quản lý chuyển cảnh giữa các màn hình.
 * Dùng singleton pattern - lưu reference tới Stage chính.
 */
public class SceneManager {
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getStage() {
        return primaryStage;
    }

    /**
     * Chuyển sang màn hình mới bằng FXML file.
     *
     * @param fxmlPath đường dẫn tương đối từ resources, ví dụ "fxml/login.fxml"
     */
    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/app/frontendauction/" + fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể load FXML: " + fxmlPath);
        }
    }

    /**
     * Chuyển sang màn hình mới với kích thước tùy chỉnh.
     */
    public static void switchScene(String fxmlPath, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/app/frontendauction/" + fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể load FXML: " + fxmlPath);
        }
    }

    /**
     * Chuyển sang màn hình và trả về controller để truyền data.
     */
    @SuppressWarnings("unchecked")
    public static <T> T switchSceneAndGetController(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/app/frontendauction/" + fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            return (T) loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
