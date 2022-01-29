package com.example.ratkovic8;

import database.Database;
import hr.java.production.enums.City;
import hr.java.production.model.Address;
import hr.java.production.model.Category;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CreateFactoryController {
    List<Category> categories;
    List<Item> items;
    List<Address> addresses;
    List<Factory> factories;

    @FXML
    TextField factoryNameTextField;

    @FXML
    ComboBox factoryCityComboBox;

    @FXML
    TextField factoryStreetTextField;

    @FXML
    TextField factoryHouseNumberTextField;

    @FXML
    ListView<Item> availableItemsListView;

    @FXML
    ListView<Item> selectedItemsListView;

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
        factoryCityComboBox.setConverter(new StringConverter<City>() {
            @Override
            public String toString(City o) {
                if (o == null) return null;
                else return o.getName();
            }

            @Override
            public City fromString(String s) {
                return (City) factoryCityComboBox.getItems().stream().filter(ap ->
                        ((City) ap).getName().equals(s)).findFirst().orElse(null);
            }
        });

        try (Connection connection = Database.connectToDatabase()) {
            System.out.println("Connected to database.");
            categories = Database.fetchCategories(connection);
            items = Database.fetchItems(connection, categories);
            addresses = Database.fetchAddresses(connection);
        } catch (SQLException | IOException ex) {
            System.out.println("Error connecting to database");
            ex.printStackTrace();
        }

        availableItemsListView.setItems(FXCollections.observableList(items));
        selectedItemsListView.getItems().clear();
        factoryCityComboBox.setItems(FXCollections.observableList(Arrays.stream(City.values()).toList()));
        factoryCityComboBox.getSelectionModel().selectFirst();

        factoryNameTextField.clear();
        factoryStreetTextField.clear();
        factoryHouseNumberTextField.clear();
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
        StringBuilder errorMessage = new StringBuilder();

        String addressStreet = factoryStreetTextField.getText();
        if (addressStreet.isEmpty())
            errorMessage.append("Factory street should not be empty!\n");

        String addressHouseNumber = factoryHouseNumberTextField.getText();
        if (addressHouseNumber.isEmpty())
            errorMessage.append("Factory house number should not be empty!\n");

        City addressCity = (City) factoryCityComboBox.getValue();

        Address address = new Address(addressStreet, addressHouseNumber, addressCity);

        String factoryName = factoryNameTextField.getText();
        if (factoryName.isEmpty())
            errorMessage.append("Factory name should not be empty!\n");

        Set<Item> factoryItems = new HashSet<>();
        if (selectedItemsListView.getItems().isEmpty()) {
            errorMessage.append("Factory should contain at least one item!");
        } else {
            factoryItems = new HashSet<>(selectedItemsListView.getItems());
        }

        if (errorMessage.isEmpty()) {
            try (Connection connection = Database.connectToDatabase()) {
                System.out.println("Connected to database.");
                /*Save address and get its Id*/
                Long addressId = Database.insertAddress(connection, address);
                address.setId(addressId);
                Factory factory = new Factory(factoryName, address, factoryItems, 0L);
                Database.insertFactory(connection, factory);
            } catch (SQLException | IOException ex) {
                System.out.println("Error connecting to database");
                ex.printStackTrace();
            }

            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Factory could not be saved");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
        }
    }
}
