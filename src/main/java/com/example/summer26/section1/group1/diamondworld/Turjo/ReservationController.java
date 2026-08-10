package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class ReservationController extends BaseController {

    @FXML private TextField itemTagField;
    @FXML private TextField customerIdField;

    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, String> idCol;
    @FXML private TableColumn<Reservation, String> tagCol;
    @FXML private TableColumn<Reservation, String> custCol;
    @FXML private TableColumn<Reservation, String> expiryCol;
    @FXML private TableColumn<Reservation, String> statusCol;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<Reservation> resList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (reservationTable != null) {
            reservationTable.setEditable(true);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            tagCol.setCellValueFactory(new PropertyValueFactory<>("itemTag"));
            custCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            expiryCol.setCellValueFactory(new PropertyValueFactory<>("expiryTimestamp"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            idCol.setCellFactory(TextFieldTableCell.forTableColumn());
            idCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setId(e.getNewValue()); });

            tagCol.setCellFactory(TextFieldTableCell.forTableColumn());
            tagCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setItemTag(e.getNewValue()); });

            custCol.setCellFactory(TextFieldTableCell.forTableColumn());
            custCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setCustomerId(e.getNewValue()); });

            expiryCol.setCellFactory(TextFieldTableCell.forTableColumn());
            expiryCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setExpiryTimestamp(e.getNewValue()); });

            statusCol.setCellFactory(TextFieldTableCell.forTableColumn());
            statusCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setStatus(e.getNewValue()); });

            loadReservations();

            reservationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (itemTagField != null) itemTagField.setText(newSel.getItemTag());
                    if (customerIdField != null) customerIdField.setText(newSel.getCustomerId());
                }
            });
        }
    }

    private void loadReservations() {
        var existing = store.getAllReservations();
        if (existing != null && !existing.isEmpty()) {
            resList = FXCollections.observableArrayList(existing);
        } else {
            Reservation sample = new Reservation();
            sample.setId("RES-301");
            sample.setItemTag("DW-RING-001");
            sample.setCustomerId("C001");
            sample.setExpiryTimestamp("2026-08-12 18:00");
            sample.setStatus("ACTIVE");
            resList.add(sample);
        }
        reservationTable.setItems(resList);
    }

    @FXML
    private void onReserve() {
        if (itemTagField.getText().isBlank() || customerIdField.getText().isBlank()) {
            setStatus("Enter item tag and customer ID. (UID)");
            return;
        }
        try {
            Reservation r = store.createReservation(itemTagField.getText(), customerIdField.getText());
            if (r == null) {
                setStatus("Item unavailable or already sold/reserved. (VR)");
                return;
            }
            resList.add(r);
            reservationTable.getSelectionModel().select(r);
            setStatus(String.format("Reserved! ID: %s | Expires: %s (48hr auto-release) (DP, OP)",
                    r.getId(), r.getExpiryTimestamp()));
        } catch (Exception e) {
            setStatus("Reservation failed: " + e.getMessage());
        }
    }

    @FXML
    private void onAddRow() {
        onReserve();
    }

    @FXML
    private void onUpdateRow() {
        Reservation sel = reservationTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a reservation row in the table to update.");
            return;
        }
        if (itemTagField != null && !itemTagField.getText().isBlank()) sel.setItemTag(itemTagField.getText().trim());
        if (customerIdField != null && !customerIdField.getText().isBlank()) sel.setCustomerId(customerIdField.getText().trim());
        reservationTable.refresh();
        setStatus("Reservation row updated.");
    }

    @FXML
    private void onDeleteRow() {
        Reservation sel = reservationTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a reservation row in the table to delete.");
            return;
        }
        resList.remove(sel);
        setStatus("Reservation row deleted.");
    }
}
