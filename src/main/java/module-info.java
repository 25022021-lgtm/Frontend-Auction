module app.frontendauction {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    // Cho phép JavaFX truy cập vào package chứa code App
    opens app.frontendauction to javafx.fxml;

    // Xuất package để chạy được ứng dụng
    exports app.frontendauction;
}