module ui.module {
    requires javafx.controls;
    requires javafx.fxml;
    // requires data.module; // Sẽ mở comment khi cấu hình xong data-module

    opens com.hsf302.ui to javafx.fxml;
    exports com.hsf302.ui;
}