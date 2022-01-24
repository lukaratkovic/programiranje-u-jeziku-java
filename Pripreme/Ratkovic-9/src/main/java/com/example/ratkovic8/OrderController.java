package com.example.ratkovic8;

import hr.java.production.model.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    @FXML
    ComboBox<String> slanjePutemComboBox;

    @FXML
    TextField napomenaTextField;

    @FXML
    TableView<Order> orderTableView;

    @FXML
    TableColumn<Order, String> oznakaTableColumn;

    @FXML
    TableColumn<Order, String> slanjeTableColumn;

    @FXML
    TableColumn<Order, String> napomenaTableColumn;

    @FXML
    TableColumn<Order, String> datumTableColumn;

    @FXML
    public void initialize() {
        oznakaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOznaka()));
        slanjeTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSlanje()));
        napomenaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNapomena()));
        datumTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatumKreiranja().toString()));

        List<String> slanjeOptions = new ArrayList<>();
        slanjeOptions.add("HP");
        slanjeOptions.add("Oversees Express");
        slanjePutemComboBox.setItems(FXCollections.observableList(slanjeOptions));
    }

    @FXML
    protected void createOrder() {

    }
}
