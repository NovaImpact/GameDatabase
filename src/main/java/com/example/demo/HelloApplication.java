package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    HelloApplication.class.getResource("hello-view.fxml")
            );

            Scene scene = new Scene(fxmlLoader.load(), 1400, 800);

            stage.setTitle("Video Game Data Editor");
            stage.setScene(scene);
            stage.setMinWidth(1000);
            stage.setMinHeight(600);

            stage.show();

            System.out.println("Application Starts");

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void stop() {
        System.out.println("Application shutting down...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}