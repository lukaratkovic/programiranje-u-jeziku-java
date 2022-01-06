package com.example.ratkovic7;

import hr.java.production.model.Category;
import javafx.fxml.FXML;
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
    public void initialize(){
        categories = loadCategories();
    }

    @FXML
    protected void onSaveButtonClick(){
        //TODO: Checks for validity of fields before saving
        String enteredName = categoryNameTextField.getText();
        String enteredDescription = categoryDescriptionTextField.getText();
        Long id = categories.get(categories.size()-1).getId() + 1;

        try(FileWriter categoryFileWriter = new FileWriter(CATEGORY_FILE, true)){
            categoryFileWriter.write(id+"\n"+enteredName+"\n"+enteredDescription+"\n");
        }catch (IOException e){
            e.printStackTrace();
        }

        categoryNameTextField.setText("");
        categoryDescriptionTextField.setText("");
    }
}
