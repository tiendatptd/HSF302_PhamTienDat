package com.hsf302.chapter8_javafx;


import com.hsf302.chapter8_javafx.utils.AlertUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class B1_OrderApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label lblTitle = new Label("Chọn đồ uống của bạn:");

        CheckBox cbCoffee = new CheckBox("Cà phê (20,000 VND)");
        CheckBox cbMilkTea = new CheckBox("Trà sữa (30,000 VND)");
        CheckBox cbJuice = new CheckBox("Nước ép (25,000 VND)");

        Button btnOrder = new Button("Đặt hàng");

        // Xử lý sự kiện khi bấm nút Đặt hàng
        btnOrder.setOnAction(e -> {
            StringBuilder order = new StringBuilder("Bạn đã đặt:\n");
            int total = 0;

            if (cbCoffee.isSelected()) { order.append("- Cà phê\n"); total += 20000; }
            if (cbMilkTea.isSelected()) { order.append("- Trà sữa\n"); total += 30000; }
            if (cbJuice.isSelected()) { order.append("- Nước ép\n"); total += 25000; }

            if (total == 0) {
                AlertUtil.showAlert("Lỗi", "Vui lòng chọn ít nhất một đồ uống!", Alert.AlertType.WARNING);
            } else {
                order.append("Tổng tiền: ").append(total).append(" VND");
                AlertUtil.showAlert("Thành công", order.toString(), Alert.AlertType.INFORMATION);
            }
        });

        // Sử dụng VBox để xếp các thành phần theo chiều dọc
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(lblTitle, cbCoffee, cbMilkTea, cbJuice, btnOrder);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("B1 - Đặt Đồ Uống");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
