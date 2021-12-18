package com.example.ratkovic7;

import hr.java.production.model.Address;
import hr.java.production.model.Category;
import hr.java.production.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

import static hr.java.production.main.Main.*;

public class MenuController {
    public void showItemSearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("item-search.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Items");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showCategorySearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("category-search.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Categories");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showFactorySearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("factory-search.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Factories");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showStoreSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("store-search.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Stores");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }
}