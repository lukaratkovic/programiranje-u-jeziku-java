package com.example.ratkovic8;

import database.Database;
import hr.java.production.enums.City;
import hr.java.production.model.Address;
import hr.java.production.model.Category;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SearchFactoriesController {
    List<Category> categories = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    List<Address> addresses = new ArrayList<>();
    List<Factory> factories = new ArrayList<>();

    @FXML
    TextField nameTextField;

    @FXML
    ComboBox<String> cityComboBox;

    @FXML
    TextField streetTextField;

    @FXML
    TextField houseNumberTextField;

    @FXML
    ComboBox<String> itemComboBox;

    @FXML
    TableView<Factory> factoryTableView;

    @FXML
    TableColumn<Factory, String> nameTableColumn;

    @FXML
    TableColumn<Factory, String> addressTableColumn;

    @FXML
    TableColumn<Factory, String> itemsTableColumn;

    public void initialize() {
        nameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        addressTableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress().toString()));

        itemsTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemsAsString()));

        try (Connection connection = Database.connectToDatabase()) {
            System.out.println("Connected to database.");
            categories = Database.fetchCategories(connection);
            items = Database.fetchItems(connection, categories);
            addresses = Database.fetchAddresses(connection);
            factories = Database.fetchFactories(connection, addresses, categories);
        } catch (SQLException | IOException ex) {
            System.out.println("Error connecting to database");
            ex.printStackTrace();
        }

        List<String> cityNames = Arrays.stream(City.values()).toList().stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
        cityNames.add(0, "All Cities");
        List<String> itemNames = items.stream()
                .map(i -> i.getName())
                .collect(Collectors.toList());
        itemNames.add(0, "All Items");

        cityComboBox.setItems(FXCollections.observableList(cityNames));
        cityComboBox.getSelectionModel().selectFirst();

        itemComboBox.setItems(FXCollections.observableList(itemNames));
        itemComboBox.getSelectionModel().selectFirst();

        factoryTableView.setItems(FXCollections.observableList(factories));
    }

    @FXML
    protected void onSearchButtonClick() {
        String enteredName = nameTextField.getText();
        Object enteredCity = cityComboBox.getValue();
        String enteredStreet = streetTextField.getText();
        String enteredHouseNumber = houseNumberTextField.getText();
        Object enteredItem = itemComboBox.getValue();

        List<Factory> filteredFactories = factories.stream()
                .filter(f -> f.getName().toUpperCase(Locale.ROOT).contains(enteredName.toUpperCase(Locale.ROOT)))
                .filter(f -> f.getAddress().getStreet().toUpperCase(Locale.ROOT).contains(enteredStreet.toUpperCase(Locale.ROOT)))
                .filter(f -> f.getAddress().getHouseNumber().toUpperCase(Locale.ROOT).contains(enteredHouseNumber.toUpperCase(Locale.ROOT)))
                .collect(Collectors.toList());

        if (!enteredCity.equals("All Cities")) {
            filteredFactories = filteredFactories.stream()
                    .filter(f -> f.getAddress().getCity().getName().equals(enteredCity))
                    .collect(Collectors.toList());
        }

        if (!enteredItem.equals("All Items")) {
            Optional<Item> item = items.stream()
                    .filter(i -> i.getName().equals(enteredItem))
                    .findFirst();
            filteredFactories = filteredFactories.stream()
                    .filter(f -> f.getItems().contains(item.get()))
                    .collect(Collectors.toList());
        }

        factoryTableView.setItems(FXCollections.observableList(filteredFactories));
    }
}
