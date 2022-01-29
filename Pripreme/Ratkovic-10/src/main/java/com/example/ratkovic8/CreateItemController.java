package com.example.ratkovic8;

import database.Database;
import hr.java.production.model.Category;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreateItemController {
    List<Category> categories;
    List<Item> items;

    @FXML
    ComboBox<String> itemCategoryComboBox;

    @FXML
    TextField itemNameTextField;

    @FXML
    TextField itemWidthTextField;

    @FXML
    TextField itemHeightTextField;

    @FXML
    TextField itemLengthTextField;

    @FXML
    TextField itemProductionCostTextField;

    @FXML
    TextField itemSellingPriceTextField;

    @FXML
    public void initialize() {
        try (Connection connection = Database.connectToDatabase()) {
            System.out.println("Connected to database.");
            categories = Database.fetchCategories(connection);
            items = Database.fetchItems(connection, categories);
        } catch (SQLException | IOException ex) {
            System.out.println("Error connecting to database");
            ex.printStackTrace();
        }

        List<String> categoryNames = categories.stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
        itemCategoryComboBox.setItems(FXCollections.observableList(categoryNames));
        itemCategoryComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    protected void onSaveButtonClick() {
        StringBuilder errorMessage = new StringBuilder();

        String name = itemNameTextField.getText();
        if (name.isEmpty())
            errorMessage.append("Item name should not be empty!\n");

        Category category = categories.stream()
                .filter(c -> c.getName().equals(itemCategoryComboBox.getValue()))
                .findFirst()
                .get();

        Optional<BigDecimal> width = Optional.empty();
        if (itemWidthTextField.getText().isEmpty())
            errorMessage.append("Item width should not be empty!\n");
        else {
            try {
                width = Optional.of(new BigDecimal(itemWidthTextField.getText()));
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item width!\n");
            }
        }

        Optional<BigDecimal> height = Optional.empty();
        if (itemHeightTextField.getText().isEmpty())
            errorMessage.append("Item height should not be empty!\n");
        else {
            try {
                height = Optional.of(new BigDecimal(itemHeightTextField.getText()));
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item width!\n");
            }
        }

        Optional<BigDecimal> length = Optional.empty();
        if (itemLengthTextField.getText().isEmpty())
            errorMessage.append("Item length should not be empty!\n");
        else {
            try {
                length = Optional.of(new BigDecimal(itemLengthTextField.getText()));
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item length!\n");
            }
        }

        Optional<BigDecimal> productionCost = Optional.empty();
        if (itemProductionCostTextField.getText().isEmpty())
            errorMessage.append("Item production cost should not be empty!\n");
        else {
            try {
                productionCost = Optional.of(new BigDecimal(itemProductionCostTextField.getText()));
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item production cost!\n");
            }
        }

        Optional<BigDecimal> sellingPrice = Optional.empty();
        if (itemSellingPriceTextField.getText().isEmpty())
            errorMessage.append("Item selling price should not be empty!\n");
        else {
            try {
                sellingPrice = Optional.of(new BigDecimal(itemSellingPriceTextField.getText()));
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item selling price!\n");
            }
        }


        if (errorMessage.isEmpty()) {
            try (Connection connection = Database.connectToDatabase()) {
                System.out.println("Connected to database.");
                Database.insertItem(connection, new Item(name, 0L, category, width.get(), height.get(), length.get(), productionCost.get(), sellingPrice.get()));
            } catch (SQLException | IOException ex) {
                System.out.println("Error connecting to database");
                ex.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save action successful!");
            alert.setHeaderText("Item successfully saved");
            alert.setContentText(new StringBuilder().append("Item ").append(itemNameTextField.getText()).append(" successfully saved").toString());
            alert.showAndWait();
            itemNameTextField.setText("");
            itemWidthTextField.setText("");
            itemHeightTextField.setText("");
            itemLengthTextField.setText("");
            itemProductionCostTextField.setText("");
            itemSellingPriceTextField.setText("");
            itemCategoryComboBox.getSelectionModel().selectFirst();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Category could not be saved");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
        }

    }
}

