package easymis.controllers;

import com.jfoenix.controls.JFXTabPane;
import easymis.models.entity.Inventory;
import easymis.models.entity.TransactionStatus;
import easymis.models.repository.InventoryRepository;
import easymis.utils.AlertHelper;
import easymis.utils.DateHelper;
import easymis.utils.NumberFilter;
import easymis.utils.StringUtils;
import easymis.views.viewobjects.InventoryViewObject;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Rashid
 */
public class InventoryController implements Initializable {

    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab panelTabAlddInventory;
    @FXML
    private TableView<InventoryViewObject> table;
    @FXML
    private TableColumn<InventoryViewObject, Integer> col_ItemId;
    @FXML
    private TableColumn<InventoryViewObject, String> col_ItemName;
    @FXML
    private TableColumn<InventoryViewObject, Integer> col_Quantity;
    @FXML
    private TableColumn<InventoryViewObject, Date> col_AddedDate;
    @FXML
    private TableColumn<InventoryViewObject, Date> col_LastUpdatedDate;
    @FXML
    private TableColumn<InventoryViewObject, String> col_Remarks;
    
    ObservableList<InventoryViewObject> observableList = FXCollections.observableArrayList();

    @FXML
    private TextField itemName;
    @FXML
    private TextField quantity;
    @FXML
    private Button okButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextField itemId;
    @FXML
    private TextField remarks;
    @FXML
    private DatePicker createdDateField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        quantity.setTextFormatter(new NumberFilter().filter());
        initializeTable();
        populateInventoryTable();
    }

    @FXML
    private void onAllEventsTabSelection(Event event) {
    }

    @FXML
    private void onOkClick(ActionEvent event) {
        Inventory inventory = getInventoryDetails();
        if (inventory != null) {
            TransactionStatus status;
            if (inventory.getItemId() == 0) {
                status = InventoryRepository.getUniqueInstance().create(inventory);
            } else {
                status = InventoryRepository.getUniqueInstance().update(inventory);
            }
            if (status != null) {
                AlertHelper.showMessage(status);
                if (status.isSuccess()) {
                    itemName.setEditable(false);
                    quantity.setEditable(false);
                    remarks.setEditable(false);
                    populateInventoryTable();
                }
            }

        }
    }

    @FXML
    private void onClearClick(ActionEvent event) {

        itemId.setText("");
        itemName.setText("");
        quantity.setText("");
        remarks.setText("");
        createdDateField.setValue(null);
        itemName.setEditable(true);
        quantity.setEditable(true);
        remarks.setEditable(true);
    }

    private Inventory getInventoryDetails() {
        Inventory inventory = null;
        String id = this.itemId.getText();
        String name = this.itemName.getText();
        String quant = this.quantity.getText();
        if (StringUtils.isNotNullCheckSpace(name) && StringUtils.isNotNullCheckSpace(quant)) {
            inventory = new Inventory();
            inventory.setItemName(name);
            inventory.setQuantity(Integer.valueOf(quant));
            inventory.setRemarks(remarks.getText());
            inventory.setUpdatedDate(DateHelper.getToday());
            if (createdDateField.getValue() != null) {
                inventory.setAddedDate(java.sql.Date.valueOf(createdDateField.getValue()));
            } else {
                inventory.setAddedDate(DateHelper.getToday());
            }
            if (StringUtils.isNotNullCheckSpace(id)) {
                inventory.setItemId(Integer.valueOf(id));
            }
        }
        return inventory;
    }

    private void initializeTable() {
        col_ItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        col_ItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_AddedDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        col_LastUpdatedDate.setCellValueFactory(new PropertyValueFactory<>("updatedDate"));
        col_Remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        table.setRowFactory(tv -> {
            TableRow<InventoryViewObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    InventoryViewObject rowData = row.getItem();
                    if (rowData != null) {
                        populateDetailsForUpdate(rowData);
                    }
                }
            });
            return row;
        });
    }

    private void populateDetailsForUpdate(InventoryViewObject rowData) {
        
        itemId.setText(String.valueOf(rowData.getItemId()));
        itemName.setText(rowData.getItemName());
        quantity.setText(String.valueOf(rowData.getQuantity()));
        createdDateField.setValue(rowData.getAddedDate().toLocalDate());
        remarks.setText(rowData.getRemarks());
    }
    
    private void populateInventoryTable() {
        List<Inventory> inventoryList = InventoryRepository.getUniqueInstance().fetchAllInventory();
        if(inventoryList != null && !inventoryList.isEmpty()){
        observableList.clear();
        inventoryList.stream().forEach((inventory) -> {
            observableList.add(buildInventoryViewObject(inventory));
        });
        table.setItems(observableList);
        }
    }

    private InventoryViewObject buildInventoryViewObject(Inventory inventory){
        InventoryViewObject inventoryViewObject = new InventoryViewObject();
        inventoryViewObject.setItemId(inventory.getItemId());
        inventoryViewObject.setItemName(inventory.getItemName());
        inventoryViewObject.setQuantity(inventory.getQuantity());
        inventoryViewObject.setRemarks(inventory.getRemarks());
        inventoryViewObject.setAddedDate(inventory.getAddedDate());
        inventoryViewObject.setUpdatedDate(inventory.getUpdatedDate());
        return inventoryViewObject;
    }
}
