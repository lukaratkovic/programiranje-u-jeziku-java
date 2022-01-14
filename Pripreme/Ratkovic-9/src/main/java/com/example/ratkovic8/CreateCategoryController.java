package com.example.ratkovic8;

import database.Database;
import hr.java.production.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static hr.java.production.main.Main.CATEGORY_FILE;
import static hr.java.production.main.Main.loadCategories;

public class CreateCategoryController {

    @FXML
    TextField categoryNameTextField;

    @FXML
    TextField categoryDescriptionTextField;

    @FXML
    protected void onSaveButtonClick() {
        StringBuilder errorMessage = new StringBuilder();

        String name = categoryNameTextField.getText();
        if (name.isEmpty())
            errorMessage.append("Category name should not be empty!\n");

        String description = categoryDescriptionTextField.getText();
        if (description.isEmpty())
            errorMessage.append("Category description should not be empty!\n");

        if (errorMessage.isEmpty()) {
            try (Connection connection = Database.connectToDatabase()) {
                System.out.println("Connected to database.");
                Database.insertCategory(connection, new Category(name, description, 0L));
            } catch (SQLException | IOException ex) {
                System.out.println("Error connecting to database");
                ex.printStackTrace();
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
