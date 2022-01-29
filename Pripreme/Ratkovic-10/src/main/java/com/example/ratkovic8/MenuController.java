package com.example.ratkovic8;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuController {
    public void showItemSearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("searchItem.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("searchCategory.fxml"));
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

    public void showFactorySearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("searchFactory.fxml"));
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

    public void showStoreSearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("searchStore.fxml"));
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

    public void showItemCreateScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createItem.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Create Item");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showCategoryCreateScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createCategory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Create Category");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showStoreCreateScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createStore.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Create Store");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showFactoryCreateScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createFactory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Create Factory");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }
}