package com.friendsbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HelloController {
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ListView<Friend> friendsListView;
    @FXML private Label detailsLabel;
    @FXML private VBox friendDetailsPane;
    private static final String FRIEND_DELIMITER = "|||";
    
    @FXML
    private Button saveButton;
    @FXML
    private Button loadButton;

    private ObservableList<Friend> friendsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        friendsListView.setItems(friendsList);
        friendsListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showFriendDetails(newValue));
        
        // Add styling
        friendDetailsPane.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");
        friendsListView.setStyle("-fx-background-color: #ffffff;");
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

    @FXML
    protected void onSaveClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Friends List");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            saveFriendsToFile(file);
        }
    }
    
    @FXML
    protected void onLoadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Friends List");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            loadFriendsFromFile(file);
        }
    }
    
    //requires: file != null
    //modifies: file system
    //effects: saves all friends to the specified file
    private void saveFriendsToFile(File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Friend friend : friendsList) {
                writer.println(String.format("%s%s%d%s%s%s%s",
                    friend.getName(), FRIEND_DELIMITER,
                    friend.getAge(), FRIEND_DELIMITER,
                    friend.getEmail(), FRIEND_DELIMITER,
                    friend.getPhoneNumber()));
            }
        } catch (IOException e) {
            showError("Error saving file: " + e.getMessage());
        }
    }
    
    //requires: file != null
    //modifies: friendsList
    //effects: loads friends from the specified file
    private void loadFriendsFromFile(File file) {
        friendsList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\" + FRIEND_DELIMITER);
                if (parts.length == 4) {
                    Friend friend = new Friend(
                        parts[0],
                        Integer.parseInt(parts[1]),
                        parts[2],
                        parts[3]
                    );
                    friendsList.add(friend);
                }
            }
        } catch (IOException e) {
            showError("Error loading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Error parsing age from file");
        }
    }
}