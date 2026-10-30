module com.hsf302.demologin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.hsf302.demologin to javafx.fxml;
    exports com.hsf302.demologin;
}