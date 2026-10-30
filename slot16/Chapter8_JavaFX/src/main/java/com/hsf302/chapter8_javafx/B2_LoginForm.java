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

public class B2_LoginForm extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("B2 - Hệ thống Đăng nhập");

        // Sử dụng GridPane để bố trí form thành các hàng/cột
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));

        // Tên đăng nhập
        Label lblUsername = new Label("Tên đăng nhập:");
        grid.add(lblUsername, 0, 0);
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Nhập username...");
        grid.add(txtUsername, 1, 0);

        // Mật khẩu
        Label lblPassword = new Label("Mật khẩu:");
        grid.add(lblPassword, 0, 1);
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Nhập password...");
        grid.add(txtPassword, 1, 1);

        // Nút Đăng nhập đưa vào HBox để căn phải
        Button btnLogin = new Button("Đăng nhập");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnLogin);
        grid.add(hbBtn, 1, 2);

        // Xử lý sự kiện click
        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                AlertUtil.showAlert("Cảnh báo", "Vui lòng nhập đầy đủ tài khoản và mật khẩu!", Alert.AlertType.WARNING);
            } else if ("admin".equals(username) && "123456".equals(password)) {
                AlertUtil.showAlert("Thành công", "Đăng nhập hệ thống thành công!", Alert.AlertType.INFORMATION);
            } else {
                AlertUtil.showAlert("Lỗi truy cập", "Sai tên đăng nhập hoặc mật khẩu!", Alert.AlertType.ERROR);
            }
        });

        Scene scene = new Scene(grid, 350, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
