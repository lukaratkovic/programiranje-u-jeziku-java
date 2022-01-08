package com.example.ratkovic8;

import hr.java.production.model.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static hr.java.production.main.Main.loadCategories;

public class SearchCategoriesController {
    List<Category> categories = new ArrayList<>();

    @FXML
    TextField nameTextField;

    @FXML
    TableView<Category> categoriesTableView;

    @FXML
    TableColumn<Category, String> nameTableColumn;

    @FXML
    TableColumn<Category, String> descriptionTableColumn;

    public void initialize() {
        nameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        descriptionTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        categories = loadCategories();

        categoriesTableView.setItems(FXCollections.observableList(categories));
    }

    @FXML
    protected void onSearchButtonClick() {
        String enteredName = nameTextField.getText().toUpperCase(Locale.ROOT);

        List<Category> filteredCategories = categories.stream()
                .filter(c -> c.getName().toUpperCase(Locale.ROOT).contains(enteredName))
                .collect(Collectors.toList());

        categoriesTableView.setItems(FXCollections.observableList(filteredCategories));
    }
}
