package com.example.ratkovic8;

import database.Database;
import hr.java.production.model.Category;
import hr.java.production.model.Item;
import hr.java.production.model.Store;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateStoreController {
    List<Category> categories;
    List<Item> items;
    List<Store> stores;

    @FXML
    TextField storeNameTextField;

    @FXML
    TextField storeWebAddressTextField;

    @FXML
    ListView<Item> availableItemsListView;

    @FXML
    ListView<Item> selectedItemsListView;

    @FXML
    public void initialize() {
        availableItemsListView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        selectedItemsListView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        try (Connection connection = Database.connectToDatabase()) {
            categories = Database.fetchCategories(connection);
            items = Database.fetchItems(connection, categories);
        } catch (SQLException | IOException ex) {
            System.out.println("Error connecting to database");
            ex.printStackTrace();
        }

        availableItemsListView.setItems(FXCollections.observableList(items));

        selectedItemsListView.getItems().clear();
        storeNameTextField.clear();
        storeWebAddressTextField.clear();
    }

    public void onAddItemClick() {
        Item selectedItem = availableItemsListView.getSelectionModel().getSelectedItem();
        availableItemsListView.getItems().remove(selectedItem);
        selectedItemsListView.getItems().add(selectedItem);
    }

    public void onRemoveItemClick() {
        Item selectedItem = selectedItemsListView.getSelectionModel().getSelectedItem();
        selectedItemsListView.getItems().remove(selectedItem);
        availableItemsListView.getItems().add(selectedItem);
    }

    public void onSaveButtonClick() {
        StringBuilder recordValue = new StringBuilder();
        StringBuilder errorMessage = new StringBuilder();

        String storeName = storeNameTextField.getText();
        if (storeName.isEmpty())
            errorMessage.append("Store name should not be empty!\n");

        String storeWebAddress = storeWebAddressTextField.getText();
        if (storeWebAddress.isEmpty())
            errorMessage.append("Store web address should not be empty!\n");


        Set<Item> storeItems = new HashSet<>();
        if (selectedItemsListView.getItems().isEmpty()) {
            errorMessage.append("Store should contain at least one item!\n");
        } else {
            storeItems = new HashSet<>(selectedItemsListView.getItems());
        }

        if (errorMessage.isEmpty()) {
            try (Connection connection = Database.connectToDatabase()) {
                Store store = new Store(storeName, storeWebAddress, storeItems, 0L);
                Database.insertStore(connection, store);
            } catch (SQLException | IOException ex) {
                System.out.println("Error connecting to database");
                ex.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save action successful!");
            alert.setHeaderText("Store successfully saved");
            alert.setContentText(new StringBuilder().append("Category ").append(storeNameTextField.getText()).append(" successfully saved").toString());
            alert.showAndWait();
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Store could not be saved");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
        }
    }
}
