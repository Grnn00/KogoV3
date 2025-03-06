module com.example.clone {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.clone to javafx.fxml;
    exports com.example.clone;
}