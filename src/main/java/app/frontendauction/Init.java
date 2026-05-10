package app.frontendauction;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Init extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Đăng ký stage cho SceneManager để chuyển cảnh
        SceneManager.setStage(stage);

        FXMLLoader fxmlLoader = new FXMLLoader(Init.class.getResource("fxml/landingPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Ponzi™ Auctions - Hệ thống đấu giá");
        stage.show();
    }
}

