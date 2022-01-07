package com.example.ratkovic7;

import hr.java.production.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static hr.java.production.main.Main.CATEGORY_FILE;
import static hr.java.production.main.Main.loadCategories;

public class CreateCategoryController {
    List<Category> categories;

    @FXML
    TextField categoryNameTextField;

    @FXML
    TextField categoryDescriptionTextField;

    @FXML
    public void initialize() {
        categories = loadCategories();
    }

    @FXML
    protected void onSaveButtonClick() {
        StringBuilder recordValue = new StringBuilder();
        StringBuilder errorMessage = new StringBuilder();

        Long id;
        if (categories.size() < 1) id = 1L;
        else id = categories.get(categories.size() - 1).getId() + 1;
        recordValue.append(id.toString());
        recordValue.append("\n");

        if (categoryNameTextField.getText().isEmpty()) {
            errorMessage.append("Category name should not be empty!\n");
        } else {
            recordValue.append(categoryNameTextField.getText());
            recordValue.append("\n");
        }

        if (categoryDescriptionTextField.getText().isEmpty()) {
            errorMessage.append("Category description should not be empty!\n");
        } else {
            recordValue.append(categoryDescriptionTextField.getText());
            recordValue.append("\n");
        }
        if (errorMessage.isEmpty()) {
            try (FileWriter categoryFileWriter = new FileWriter(CATEGORY_FILE, true)) {
                categoryFileWriter.write(recordValue.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save action successful!");
            alert.setHeaderText("Category successfully saved");
            alert.setContentText(new StringBuilder().append("Category ").append(categoryNameTextField.getText()).append(" successfully saved").toString());
            alert.showAndWait();
            categoryNameTextField.setText("");
            categoryDescriptionTextField.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Category could not be saved");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
        }
    }
}
