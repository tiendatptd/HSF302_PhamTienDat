module com.hsf302.chapter8_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.hsf302.chapter8_javafx to javafx.fxml;

    exports com.hsf302.chapter8_javafx;
    exports com.hsf302.chapter8_javafx.utils;
}