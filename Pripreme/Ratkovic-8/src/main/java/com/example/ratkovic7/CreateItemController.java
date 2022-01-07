package com.example.ratkovic7;

import hr.java.production.model.Category;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hr.java.production.main.Main.*;

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
        categories = loadCategories();
        items = loadItems(categories);
        List<String> categoryNames = categories.stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
        itemCategoryComboBox.setItems(FXCollections.observableList(categoryNames));
        itemCategoryComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    protected void onSaveButtonClick() {
        StringBuilder recordValue = new StringBuilder();
        StringBuilder errorMessage = new StringBuilder();

        Long id;
        if (items.size() < 1) id = 1L;
        else id = items.get(items.size() - 1).getId() + 1;
        recordValue.append(id.toString());
        recordValue.append("\n");

        if (itemNameTextField.getText().isEmpty()) {
            errorMessage.append("Item name should not be empty!\n");
        } else {
            recordValue.append(itemNameTextField.getText());
            recordValue.append("\n");
        }

        Long categoryID = categories.stream()
                .filter(c -> c.getName().equals(itemCategoryComboBox.getValue()))
                .map(c -> c.getId())
                .findFirst()
                .get();
        recordValue.append(categoryID);
        recordValue.append("\n");
        recordValue.append("Basic\n");

        if (itemWidthTextField.getText().isEmpty()) {
            errorMessage.append("Item width should not be empty!\n");
        } else {
            try {
                BigDecimal enteredWidth = new BigDecimal(itemWidthTextField.getText());
                recordValue.append(enteredWidth);
                recordValue.append("\n");
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item width!\n");
            }
        }

        if (itemHeightTextField.getText().isEmpty()) {
            errorMessage.append("Item height should not be empty!\n");
        } else {
            try {
                BigDecimal enteredHeight = new BigDecimal(itemHeightTextField.getText());
                recordValue.append(enteredHeight);
                recordValue.append("\n");
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item height!\n");
            }
        }

        if (itemLengthTextField.getText().isEmpty()) {
            errorMessage.append("Item length should not be empty!\n");
        } else {
            try {
                BigDecimal enteredLength = new BigDecimal(itemLengthTextField.getText());
                recordValue.append(enteredLength);
                recordValue.append("\n");
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item length!\n");
            }
        }

        recordValue.append("0\n");

        if (itemProductionCostTextField.getText().isEmpty()) {
            errorMessage.append("Item production cost should not be empty!\n");
        } else {
            try {
                BigDecimal enteredProductionCost = new BigDecimal(itemProductionCostTextField.getText());
                recordValue.append(enteredProductionCost);
                recordValue.append("\n");
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item production cost!\n");
            }
        }

        if (itemSellingPriceTextField.getText().isEmpty()) {
            errorMessage.append("Item selling price should not be empty!\n");
        } else {
            try {
                BigDecimal enteredSellingPrice = new BigDecimal(itemSellingPriceTextField.getText());
                recordValue.append(enteredSellingPrice);
                recordValue.append("\n");
            } catch (NumberFormatException ex) {
                errorMessage.append("Incorrect format for item selling price!\n");
            }
        }

        recordValue.append("0\n0\n");


        if (errorMessage.isEmpty()) {
            try (FileWriter itemFileWriter = new FileWriter(ITEM_FILE, true)) {
                itemFileWriter.write(recordValue.toString());
            } catch (IOException e) {
                e.printStackTrace();
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
            categories = loadCategories();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Category could not be saved");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
        }

    }
}

