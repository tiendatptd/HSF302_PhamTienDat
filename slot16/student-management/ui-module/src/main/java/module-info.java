module ui.module {
    requires javafx.controls;
    requires javafx.fxml;

    // Yêu cầu data.module để gọi được Student và StudentRepository
    requires data.module;

    opens com.hsf302.ui to javafx.fxml;
    exports com.hsf302.ui;
}