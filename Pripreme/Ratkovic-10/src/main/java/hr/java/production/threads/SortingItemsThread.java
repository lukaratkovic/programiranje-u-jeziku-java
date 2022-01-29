package hr.java.production.threads;

import com.example.ratkovic8.HelloApplication;
import hr.java.production.model.Item;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingItemsThread implements Runnable {
    @FXML
    TableView<Item> itemsTableView;

    public SortingItemsThread(TableView<Item> itemsTableView) {
        this.itemsTableView = itemsTableView;
    }

    @Override
    public void run() {
        List<Item> sortedItems = itemsTableView.getItems().stream()
                .sorted(Comparator.comparing(Item::getSellingPrice).reversed())
                .collect(Collectors.toList());
        itemsTableView.setItems(FXCollections.observableList(sortedItems));
    }
}
