package com.example.ratkovic7;

import hr.java.production.model.Address;
import hr.java.production.model.Category;
import hr.java.production.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

import static hr.java.production.main.Main.*;

public class MainViewController {
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize(){
        List<Category> categories = loadCategories();
        List<Item> items = loadItems(categories);
        List<Address> addresses = loadAddresses();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}