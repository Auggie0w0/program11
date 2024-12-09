package com.finalapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.*;
import java.time.LocalDate;

public class HelloController {
    @FXML private TextField amountField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private DatePicker datePicker;
    @FXML private RadioButton incomeRadio;
    @FXML private RadioButton expenseRadio;
    @FXML private ListView<Transaction> transactionListView;
    @FXML private Label balanceLabel;
    
    private TransactionManager transactionManager;
    private FileHandler fileHandler;
    
    @FXML
    public void initialize() {
        // Initialize our helper classes
        transactionManager = new TransactionManager();
        fileHandler = new FileHandler("transactions.txt");
        
        // Setup category options
        categoryComboBox.getItems().addAll("Food", "Transportation", "Entertainment", "Bills", "Other");
        
        // Setup toggle group for radio buttons
        ToggleGroup typeGroup = new ToggleGroup();
        incomeRadio.setToggleGroup(typeGroup);
        expenseRadio.setToggleGroup(typeGroup);
        
        // Load saved transactions
        loadTransactions();
        updateBalance();
    }
    
    @FXML
    protected void onAddTransactionClick() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();
            String category = categoryComboBox.getValue();
            LocalDate date = datePicker.getValue();
            boolean isIncome = incomeRadio.isSelected();
            
            if (description.isEmpty() || category == null || date == null) {
                showAlert("Error", "Please fill all fields");
                return;
            }
            
            Transaction transaction = new Transaction(
                amount,
                description,
                category,
                date,
                isIncome
            );
            
            transactionManager.addTransaction(transaction);
            fileHandler.saveTransaction(transaction);
            updateTransactionList();
            updateBalance();
            clearFields();
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid amount");
        }
    }
    
    private void loadTransactions() {
        transactionManager.setTransactions(fileHandler.loadTransactions());
        updateTransactionList();
    }
    
    private void updateTransactionList() {
        transactionListView.getItems().clear();
        transactionListView.getItems().addAll(transactionManager.getTransactions());
    }
    
    private void updateBalance() {
        double balance = transactionManager.calculateBalance();
        balanceLabel.setText(String.format("Balance: $%.2f", balance));
    }
    
    private void clearFields() {
        amountField.clear();
        descriptionField.clear();
        categoryComboBox.setValue(null);
        datePicker.setValue(null);
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
}