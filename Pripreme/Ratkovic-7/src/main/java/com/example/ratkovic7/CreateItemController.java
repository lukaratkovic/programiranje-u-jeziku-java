package com.example.ratkovic7;

import hr.java.production.model.Category;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
    public void initialize(){
        categories = loadCategories();
        items = loadItems(categories);
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
        Long id = items.get(items.size()-1).getId();

        try(FileWriter itemFileWriter = new FileWriter(ITEM_FILE, true))
        {
            itemFileWriter.write(id.toString()+"\n");
            itemFileWriter.write(enteredName+"\n");
            itemFileWriter.write(selectedCategory.getId().toString()+"\n");
            itemFileWriter.write("Basic\n");
            itemFileWriter.write(enteredWidth.toString()+"\n");
            itemFileWriter.write(enteredHeight.toString()+"\n");
            itemFileWriter.write(enteredLength.toString()+"\n");
            itemFileWriter.write("0\n");
            itemFileWriter.write(enteredProductionCost.toString()+"\n");
            itemFileWriter.write(enteredSellingPrice.toString()+"\n");
            itemFileWriter.write("0\n");
            itemFileWriter.write("0\n");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        itemNameTextField.setText("");
        itemWidthTextField.setText("");
        itemHeightTextField.setText("");
        itemLengthTextField.setText("");
        itemProductionCostTextField.setText("");
        itemSellingPriceTextField.setText("");
        itemCategoryComboBox.getSelectionModel().selectFirst();
    }
}

