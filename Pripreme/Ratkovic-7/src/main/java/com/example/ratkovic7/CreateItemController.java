package com.example.ratkovic7;

import hr.java.production.model.Category;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hr.java.production.main.Main.loadCategories;

public class CreateItemController {
    List<Category> categories = new ArrayList<>();

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
    public void initialize(){
        categories = loadCategories();
        List<String> categoryNames = categories.stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
        itemCategoryComboBox.setItems(FXCollections.observableList(categoryNames));
        itemCategoryComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    protected void onSaveButtonClick(){
        String enteredName = itemNameTextField.getText();
        BigDecimal enteredWidth = new BigDecimal(itemWidthTextField.getText());
        BigDecimal enteredHeight = new BigDecimal(itemHeightTextField.getText());
        BigDecimal enteredLength = new BigDecimal(itemLengthTextField.getText());
        BigDecimal enteredProductionCost = new BigDecimal(itemProductionCostTextField.getText());
        BigDecimal enteredSellingPrice = new BigDecimal(itemSellingPriceTextField.getText());
        Category selectedCategory = categories.stream()
                .filter(c -> c.getName().equals(itemCategoryComboBox.getValue()))
                .findFirst()
                .get();
    }
}

