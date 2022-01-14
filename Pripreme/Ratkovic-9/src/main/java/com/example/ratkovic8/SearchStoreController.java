package com.example.ratkovic8;

import database.Database;
import hr.java.production.model.Category;
import hr.java.production.model.Item;
import hr.java.production.model.Store;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static hr.java.production.main.Main.*;

public class SearchStoreController {
    List<Category> categories = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    List<Store> stores = new ArrayList<>();

    @FXML
    TextField nameTextField;

    @FXML
    TextField webAddressTextField;

    @FXML
    ComboBox<String> itemComboBox;

    @FXML
    TableView<Store> storeTableView;

    @FXML
    TableColumn<Store, String> nameTableColumn;

    @FXML
    TableColumn<Store, String> webAddressTableColumn;

    @FXML
    TableColumn<Store, String> itemsTableColumn;

    public void initialize() {
        nameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        webAddressTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWebAddress()));

        itemsTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemsAsString()));

        try (Connection connection = Database.connectToDatabase()) {
            System.out.println("Connected to database.");
            categories = Database.fetchCategories(connection);
            items = Database.fetchItems(connection, categories);
            stores = Database.fetchStores(connection, categories);
            System.out.println(Database.fetchStoreById(connection, 1L, categories));
        } catch (SQLException | IOException ex) {
            System.out.println("Error connecting to database");
            ex.printStackTrace();
        }

        List<String> itemNames = items.stream()
                .map(i -> i.getName())
                .collect(Collectors.toList());
        itemNames.add(0, "All Items");

        itemComboBox.setItems(FXCollections.observableList(itemNames));
        itemComboBox.getSelectionModel().selectFirst();

        storeTableView.setItems(FXCollections.observableList(stores));
    }

    @FXML
    protected void onSearchButtonClick() {
        String enteredName = nameTextField.getText();
        String enteredWebAddress = webAddressTextField.getText();
        Object enteredItem = itemComboBox.getValue();

        List<Store> filteredStores = stores.stream()
                .filter(s -> s.getName().toUpperCase(Locale.ROOT).contains(enteredName.toUpperCase(Locale.ROOT)))
                .filter(s -> s.getWebAddress().toUpperCase(Locale.ROOT).contains(enteredWebAddress.toUpperCase(Locale.ROOT)))
                .collect(Collectors.toList());

        if (enteredItem.toString() != "All Items") {
            Optional<Item> item = items.stream()
                    .filter(i -> i.getName().equals(enteredItem))
                    .findFirst();
            filteredStores = filteredStores.stream()
                    .filter(f -> f.getItems().contains(item.get()))
                    .collect(Collectors.toList());
        }

        storeTableView.setItems(FXCollections.observableList(filteredStores));
    }
}
