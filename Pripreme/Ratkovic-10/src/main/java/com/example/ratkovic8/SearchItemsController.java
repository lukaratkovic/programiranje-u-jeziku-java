package com.example.ratkovic8;

import database.Database;
import hr.java.production.model.Category;
import hr.java.production.model.Item;
import hr.java.production.threads.SortingItemsThread;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class SearchItemsController {
    List<Category> categories = new ArrayList<>();
    List<Item> items = new ArrayList<>();

    @FXML
    private TextField itemNameTextField;

    @FXML
    private ComboBox itemCategoryComboBox;

    @FXML
    private TableView<Item> itemsTableView;

    @FXML
    private TableColumn<Item, String> nameTableColumn;

    @FXML
    private TableColumn<Item, String> categoryTableColumn;

    @FXML
    private TableColumn<Item, String> widthTableColumn;

    @FXML
    private TableColumn<Item, String> heightTableColumn;

    @FXML
    private TableColumn<Item, String> lengthTableColumn;

    @FXML
    private TableColumn<Item, String> productionCostTableColumn;

    @FXML
    private TableColumn<Item, String> sellingPriceTableColumn;

    @FXML
    public void initialize() {
        nameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));
        widthTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWidth().toString()));
        heightTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHeight().toString()));
        lengthTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLength().toString()));
        productionCostTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductionCost().toString()));
        sellingPriceTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSellingPrice().toString()));

        try (Connection connection = Database.connectToDatabase()) {
            System.out.println("Connected to database.");
            categories = Database.fetchCategories(connection);
            items = Database.fetchItems(connection, categories);
        } catch (SQLException | IOException ex) {
            System.out.println("Error connecting to database");
            ex.printStackTrace();
        }

        itemsTableView.setItems(FXCollections.observableList(items));

        SortingItemsThread sortingItemsThread = new SortingItemsThread(itemsTableView);
        Platform.runLater(sortingItemsThread);

        Timeline sort = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Item sortedItems = itemsTableView.getItems().stream()
                        .sorted(Comparator.comparing(Item::getSellingPrice).reversed())
                        .findFirst().get();
                HelloApplication.getStage().setTitle(
                        new StringBuilder().append("Item with the highest price: ").append(sortedItems.getName())
                                .toString());
            }
        }), new KeyFrame(Duration.seconds(1)));
        sort.setCycleCount(Animation.INDEFINITE);
        sort.play();


        List<String> categoryNames = categories.stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
        categoryNames.add(0, "All Categories");
        itemCategoryComboBox.setItems(FXCollections.observableList(categoryNames));
        itemCategoryComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    protected void onSearchButtonClick() {
        SortingItemsThread sortingItemsThread = new SortingItemsThread(itemsTableView);
        Platform.runLater(sortingItemsThread);

        String enteredName = itemNameTextField.getText();
        Object enteredCategory = itemCategoryComboBox.getValue();

        List<Item> filteredItems = items.stream()
                .filter(i -> i.getName().toUpperCase(Locale.ROOT).contains(enteredName.toUpperCase(Locale.ROOT)))
                .collect(Collectors.toList());

        if (!enteredCategory.equals("All Categories")) {
            filteredItems = filteredItems.stream()
                    .filter(i -> i.getCategory().getName().contains(enteredCategory.toString()))
                    .collect(Collectors.toList());
        }

        itemsTableView.setItems(FXCollections.observableList(filteredItems));
    }
}
