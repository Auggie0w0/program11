package com.friendsbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HelloController {
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ListView<Friend> friendsListView;
    @FXML private Label detailsLabel;
    @FXML private VBox friendDetailsPane;

    private ObservableList<Friend> friendsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        friendsListView.setItems(friendsList);
        friendsListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showFriendDetails(newValue));
    }

    //requires: nothing
    //modifies: friendsList
    //effects: adds a new friend if input is valid
    @FXML
    protected void onAddFriendClick() {
        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                showError("All fields must be filled out");
                return;
            }

            if (age <= 0 || age > 150) {
                showError("Please enter a valid age");
                return;
            }

            Friend friend = new Friend(name, age, email, phone);
            friendsList.add(friend);
            clearFields();
        } catch (NumberFormatException e) {
            showError("Please enter a valid number for age");
        }
    }

    //requires: nothing
    //modifies: friendsList
    //effects: removes the selected friend from the list
    @FXML
    protected void onDeleteFriendClick() {
        Friend selectedFriend = friendsListView.getSelectionModel().getSelectedItem();
        if (selectedFriend != null) {
            friendsList.remove(selectedFriend);
            detailsLabel.setText("");
        }
    }

    //requires: nothing
    //modifies: UI fields
    //effects: clears all input fields
    private void clearFields() {
        nameField.clear();
        ageField.clear();
        emailField.clear();
        phoneField.clear();
    }

    //requires: nothing
    //modifies: detailsLabel
    //effects: displays the details of the selected friend
    private void showFriendDetails(Friend friend) {
        if (friend != null) {
            detailsLabel.setText(String.format(
                "Name: %s\nAge: %d\nEmail: %s\nPhone: %s",
                friend.getName(), friend.getAge(), 
                friend.getEmail(), friend.getPhoneNumber()
            ));
        }
    }

    //requires: message != null
    //modifies: nothing
    //effects: shows an error alert with the given message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}