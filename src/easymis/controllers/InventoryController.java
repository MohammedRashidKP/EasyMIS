/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easymis.controllers;

import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author faisal
 */
public class InventoryController implements Initializable {
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab panelTabAllEvents;
    @FXML
    private TableView<?> eventTable;
    @FXML
    private TableColumn<?, ?> col_bookingId;
    @FXML
    private TableColumn<?, ?> col_EventDate;
    @FXML
    private TableColumn<?, ?> col_fullName;
    @FXML
    private TableColumn<?, ?> col_BookingStatus;
    @FXML
    private TableColumn<?, ?> col_eventType;
    @FXML
    private TableColumn<?, ?> col_BookingDate;
    @FXML
    private TableColumn<?, ?> col_EventCategory;
    @FXML
    private Tab panelTabUpdateEvent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onAllEventsTabSelection(Event event) {
    }

    @FXML
    private void onUpdateEventTabSelection(Event event) {
    }
    
}
