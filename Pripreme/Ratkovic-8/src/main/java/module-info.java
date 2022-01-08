module com.example.ratkovic7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ratkovic8 to javafx.fxml;
    exports com.example.ratkovic8;
}