package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class StockSearchController extends BaseController {

    @FXML private TextField keywordField;
    @FXML private TableView<Product> resultTable;
    @FXML private TableColumn<Product, String> nameCol;
    @FXML private TableColumn<Product, String> branchCol;
    @FXML private TableColumn<Product, String> locationCol;
    @FXML private TableColumn<Product, Integer> stockCol;

    @FXML private TextField nameInput;
    @FXML private TextField branchInput;
    @FXML private TextField locationInput;
    @FXML private TextField stockInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (resultTable != null) {
            resultTable.setEditable(true);

            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            branchCol.setCellValueFactory(new PropertyValueFactory<>("branch"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("displayLocation"));
            stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

            nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            nameCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setName(e.getNewValue()); });

            branchCol.setCellFactory(TextFieldTableCell.forTableColumn());
            branchCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setBranch(e.getNewValue()); });

            locationCol.setCellFactory(TextFieldTableCell.forTableColumn());
            locationCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setDisplayLocation(e.getNewValue()); });

            stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            stockCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setStock(e.getNewValue()); });

            var all = store.searchProducts("");
            if (all != null && !all.isEmpty()) {
                productList = FXCollections.observableArrayList(all);
                resultTable.setItems(productList);
            }

            resultTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (nameInput != null) nameInput.setText(newSel.getName());
                    if (branchInput != null) branchInput.setText(newSel.getBranch());
                    if (locationInput != null) locationInput.setText(newSel.getDisplayLocation());
                    if (stockInput != null) stockInput.setText(String.valueOf(newSel.getStock()));
                }
            });
        }
    }

    @FXML
    private void onSearch() {
        String keyword = keywordField.getText();
        if (!ValidationUtil.isValidSearchKeyword(keyword)) {
            setStatus("Invalid search keyword. (VL)");
            return;
        }
        var results = store.searchProducts(keyword);
        productList = FXCollections.observableArrayList(results);
        resultTable.setItems(productList);
        setStatus(String.format("Found %d items across branches. (UID, VR, DP, OP)", results.size()));
    }

    @FXML
    private void onAddRow() {
        String name = nameInput != null ? nameInput.getText().trim() : "";
        String branch = branchInput != null ? branchInput.getText().trim() : "";
        String loc = locationInput != null ? locationInput.getText().trim() : "";
        int stock = 1;
        try {
            if (stockInput != null && !stockInput.getText().isBlank()) stock = Integer.parseInt(stockInput.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Enter valid integer stock quantity.");
            return;
        }

        if (name.isEmpty()) {
            setStatus("Product Name is required.");
            return;
        }

        Product p = new Product();
        p.setName(name);
        p.setBranch(branch.isEmpty() ? "Gulshan Branch" : branch);
        p.setDisplayLocation(loc.isEmpty() ? "Vault Display" : loc);
        p.setStock(stock);

        productList.add(p);
        resultTable.getSelectionModel().select(p);
        setStatus("New stock item row added.");
    }

    @FXML
    private void onUpdateRow() {
        Product sel = resultTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (nameInput != null && !nameInput.getText().isBlank()) sel.setName(nameInput.getText().trim());
        if (branchInput != null && !branchInput.getText().isBlank()) sel.setBranch(branchInput.getText().trim());
        if (locationInput != null && !locationInput.getText().isBlank()) sel.setDisplayLocation(locationInput.getText().trim());
        if (stockInput != null && !stockInput.getText().isBlank()) {
            try { sel.setStock(Integer.parseInt(stockInput.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        resultTable.refresh();
        setStatus("Row updated.");
    }

    @FXML
    private void onDeleteRow() {
        Product sel = resultTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        productList.remove(sel);
        setStatus("Row deleted.");
    }
}
