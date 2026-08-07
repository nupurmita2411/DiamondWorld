package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StockSearchController extends BaseController {

    @FXML private TextField keywordField;
    @FXML private TableView<Product> resultTable;
    @FXML private TableColumn<Product, String> nameCol;
    @FXML private TableColumn<Product, String> branchCol;
    @FXML private TableColumn<Product, String> locationCol;
    @FXML private TableColumn<Product, Number> stockCol;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        branchCol.setCellValueFactory(new PropertyValueFactory<>("branch"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("displayLocation"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    @FXML
    private void onSearch() {
        String keyword = keywordField.getText();
        if (!ValidationUtil.isValidSearchKeyword(keyword)) {
            setStatus("Invalid search keyword. (VL)");
            return;
        }
        var results = store.searchProducts(keyword);
        resultTable.setItems(FXCollections.observableArrayList(results));
        setStatus(String.format("Found %d items across branches. (UID, VR, DP, OP)", results.size()));
    }
}




