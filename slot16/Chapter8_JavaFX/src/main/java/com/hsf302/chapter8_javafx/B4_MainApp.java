package com.hsf302.chapter8_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class B4_MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load giao diện từ file FXML
        FXMLLoader fxmlLoader = new FXMLLoader(B4_MainApp.class.getResource("b4_login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 250);

        stage.setTitle("B4 - FXML Login Form");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}