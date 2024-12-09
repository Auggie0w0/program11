module com.finalapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.finalapp to javafx.fxml;
    exports com.finalapp;
}