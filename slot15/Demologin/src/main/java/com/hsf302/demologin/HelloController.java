package com.hsf302.demologin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("admin".equals(username) && "123456".equals(password)) {
            welcomeText.setStyle("-fx-text-fill: green;");
            welcomeText.setText("Đăng nhập thành công!");
        } else {
            welcomeText.setStyle("-fx-text-fill: red;");
            welcomeText.setText("Sai tài khoản hoặc mật khẩu!");
        }
    }
}