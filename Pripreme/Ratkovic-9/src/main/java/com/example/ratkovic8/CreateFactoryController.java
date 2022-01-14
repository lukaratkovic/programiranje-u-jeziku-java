package com.example.ratkovic8;

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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static hr.java.production.main.Main.*;

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

        categories = loadCategories();
        items = loadItems(categories);
        addresses = loadAddresses();
        factories = loadFactories(items, addresses);

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
        StringBuilder addressRecordValue = new StringBuilder();
        StringBuilder errorMessage = new StringBuilder();

        Long addressId = 1L;
        if (addresses.size() > 0) {
            addressId = addresses.get(addresses.size() - 1).getId() + 1;
        }
        addressRecordValue.append(addressId).append("\n");

        if (factoryStreetTextField.getText().isEmpty()) {
            errorMessage.append("Factory street should not be empty!\n");
        } else {
            addressRecordValue.append(factoryStreetTextField.getText()).append("\n");
        }

        if (factoryHouseNumberTextField.getText().isEmpty()) {
            errorMessage.append("Factory house number should not be empty!\n");
        } else {
            addressRecordValue.append(factoryHouseNumberTextField.getText()).append("\n");
        }

        addressRecordValue.append(factoryCityComboBox.getValue()).append("\n");

        StringBuilder factoryRecordValue = new StringBuilder();

        Long factoryId = 1L;
        if (factories.size() > 0) {
            factoryId = factories.get(factories.size() - 1).getId() + 1;
        }
        factoryRecordValue.append(factoryId).append("\n");

        if (factoryNameTextField.getText().isEmpty()) {
            errorMessage.append("Factory name should not be empty!\n");
        } else {
            factoryRecordValue.append(factoryNameTextField.getText()).append("\n");
        }

        factoryRecordValue.append(addressId).append("\n");

        if (selectedItemsListView.getItems().isEmpty()) {
            errorMessage.append("Factory should contain at least one item!");
        } else {
            String selectedItemsIds = selectedItemsListView.getItems().stream()
                    .map(i -> i.getId())
                    .collect(Collectors.toList())
                    .toString();
            for (char c : "[] ".toCharArray()) selectedItemsIds = selectedItemsIds.replace("" + c, "");
            factoryRecordValue.append(selectedItemsIds).append("\n");
        }

        if (errorMessage.isEmpty()) {
            try (FileWriter categoryFileWriter = new FileWriter(ADDRESS_FILE, true)) {
                categoryFileWriter.write(addressRecordValue.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileWriter categoryFileWriter = new FileWriter(FACTORIES_FILE, true)) {
                categoryFileWriter.write(factoryRecordValue.toString());
            } catch (IOException e) {
                e.printStackTrace();
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
