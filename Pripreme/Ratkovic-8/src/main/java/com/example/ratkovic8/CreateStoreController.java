package com.example.ratkovic8;

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
import java.util.List;
import java.util.stream.Collectors;

import static hr.java.production.main.Main.*;

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

        categories = loadCategories();
        items = loadItems(categories);
        stores = loadStores(items);
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

        Long id = 1L;
        if (stores.size() > 0) {
            id = stores.get(stores.size() - 1).getId() + 1;
        }
        recordValue.append(id).append("\n");

        if (storeNameTextField.getText().isEmpty()) {
            errorMessage.append("Store name should not be empty!\n");
        } else {
            recordValue.append(storeNameTextField.getText()).append("\n");
        }

        if (storeWebAddressTextField.getText().isEmpty()) {
            errorMessage.append("Store web address should not be empty!\n");
        } else {
            recordValue.append(storeWebAddressTextField.getText()).append("\n");
        }

        if (selectedItemsListView.getItems().isEmpty()) {
            errorMessage.append("Store should contain at least one item!\n");
        } else {
            String selectedItemsIds = selectedItemsListView.getItems().stream()
                    .map(i -> i.getId())
                    .collect(Collectors.toList())
                    .toString();
            for (char c : "[] ".toCharArray()) selectedItemsIds = selectedItemsIds.replace("" + c, "");
            recordValue.append(selectedItemsIds).append("\n");
        }

        if (errorMessage.isEmpty()) {
            try (FileWriter categoryFileWriter = new FileWriter(STORE_FILE, true)) {
                categoryFileWriter.write(recordValue.toString());
            } catch (IOException e) {
                e.printStackTrace();
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
