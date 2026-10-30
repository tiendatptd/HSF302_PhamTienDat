package com.hsf302.chapter8_javafx;

import com.hsf302.chapter8_javafx.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class B4_LoginController {

    // Ánh xạ các UI control từ file FXML thông qua fx:id
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    // Hàm được gọi khi bấm nút Đăng nhập (tương ứng với onAction="#handleLogin" trong FXML)
    @FXML
    void handleLogin(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showAlert("Cảnh báo", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING);
        } else if ("admin".equals(username) && "123456".equals(password)) {
            AlertUtil.showAlert("Thành công", "Đăng nhập FXML thành công!", Alert.AlertType.INFORMATION);
        } else {
            AlertUtil.showAlert("Lỗi", "Sai tài khoản hoặc mật khẩu!", Alert.AlertType.ERROR);
        }
    }
}
