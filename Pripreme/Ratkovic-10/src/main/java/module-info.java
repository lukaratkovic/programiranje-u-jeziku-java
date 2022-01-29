module com.example.ratkovic7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    opens com.example.ratkovic8 to javafx.fxml;
    exports com.example.ratkovic8;
}