package com.example.ratkovic8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage mainStage;

    public static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Application started");
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Ratkovic-7");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage() {
        return mainStage;
    }

}