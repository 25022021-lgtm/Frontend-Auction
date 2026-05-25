module app.frontendauction {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    // Cho phép JavaFX truy cập vào package chứa code App và controllers
    opens app.frontendauction to javafx.fxml;
    opens app.frontendauction.controller to javafx.fxml;

    // Cho phép Gson truy cập vào DTO packages để deserialize JSON
    opens app.frontendauction.dto.request to com.google.gson;
    opens app.frontendauction.dto.response to com.google.gson;

    // Xuất package để chạy được ứng dụng
    exports app.frontendauction;
}