module com.friendsbook {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.friendsbook to javafx.fxml;
    exports com.friendsbook;
}