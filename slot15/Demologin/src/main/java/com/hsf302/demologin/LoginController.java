package com.hsf302.demologin; // Đã sửa từ com.fu.demologin

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Hyperlink registerLink;

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean remember = rememberCheckBox.isSelected();

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Remember me: " + remember);

        // Xử lý logic đăng nhập tại đây
        // Kiểm tra thông tin đăng nhập
        if ("admin".equals(username) && "1234567".equals(password)) {
            showSuccessModal(username, rememberCheckBox.isSelected());
        } else {
            showErrorAlert();
        }
    }

    // Hiển thị Modal khi đăng nhập thành công
    private void showSuccessModal(String username, boolean remember) {
        Alert infoModal = new Alert(Alert.AlertType.INFORMATION);
        infoModal.setTitle("Thông báo");
        infoModal.setHeaderText("Đăng nhập thành công!");
        infoModal.setContentText("Tài khoản: " + username + "\n" +
                "Trạng thái ghi nhớ: " + (remember ? "Có" : "Không"));
        infoModal.showAndWait();
    }

    // Hiển thị Alert khi đăng nhập thất bại
    private void showErrorAlert() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Lỗi");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Đăng nhập không hợp lệ.");
        errorAlert.showAndWait();
    }

    @FXML
    void handleForgotPassword(ActionEvent event) {
        System.out.println("Chuyển tới màn hình Quên mật khẩu");
    }

    @FXML
    void handleRegister(ActionEvent event) {
        System.out.println("Chuyển tới màn hình Đăng ký");
    }
}