package com.hsf302.chapter8_javafx;

import com.hsf302.chapter8_javafx.utils.AlertUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class B3_RegisterForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("B3 - Hệ thống Đăng ký");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));

        // Tên đăng nhập
        Label lblUsername = new Label("Tên đăng nhập:");
        grid.add(lblUsername, 0, 0);
        TextField txtUsername = new TextField();
        grid.add(txtUsername, 1, 0);

        // Mật khẩu
        Label lblPassword = new Label("Mật khẩu:");
        grid.add(lblPassword, 0, 1);
        PasswordField txtPassword = new PasswordField();
        grid.add(txtPassword, 1, 1);

        // Xác nhận mật khẩu
        Label lblConfirm = new Label("Xác nhận mật khẩu:");
        grid.add(lblConfirm, 0, 2);
        PasswordField txtConfirm = new PasswordField();
        grid.add(txtConfirm, 1, 2);

        // Các nút bấm
        Button btnRegister = new Button("Đăng ký");
        Button btnLogin = new Button("Đăng nhập");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btnLogin, btnRegister);
        grid.add(hbBtn, 1, 3);

        // Xử lý sự kiện Đăng ký
        btnRegister.setOnAction(e -> {
            String user = txtUsername.getText().trim();
            String pass = txtPassword.getText().trim();
            String confirm = txtConfirm.getText().trim();

            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                AlertUtil.showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING);
            } else if (!pass.equals(confirm)) {
                AlertUtil.showAlert("Lỗi", "Mật khẩu xác nhận không khớp!", Alert.AlertType.ERROR);
            } else {
                AlertUtil.showAlert("Thành công", "Đăng ký thành công! Chuyển sang màn hình đăng nhập.", Alert.AlertType.INFORMATION);
                openLoginForm(primaryStage);
            }
        });

        // Xử lý sự kiện nút chuyển sang Đăng nhập
        btnLogin.setOnAction(e -> openLoginForm(primaryStage));

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Hàm hỗ trợ mở Form Đăng Nhập (B2)
    private void openLoginForm(Stage currentStage) {
        try {
            B2_LoginForm loginForm = new B2_LoginForm();
            Stage loginStage = new Stage();
            loginForm.start(loginStage);
            currentStage.close(); // Đóng form đăng ký hiện tại
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}