package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import easymis.utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author RashidKP
 */
public class SideMenuViewController implements Initializable {

    @FXML
    private JFXButton btnDashboard;
    @FXML
    private JFXButton btnEventBooking;
    @FXML
    private JFXButton btnExpenses;
    @FXML
    private JFXButton btnReports;
    @FXML
    private JFXButton btnEmployees;
    @FXML
    private JFXButton btnInventory;
    @FXML
    private JFXButton btnSettings;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openDashboard(ActionEvent event) {
        switchPane(Constants.DASHBOARD_VIEW);
    }

    @FXML
    private void openEventBooking(ActionEvent event) {
        switchPane(Constants.EVENT_BOOKING_VIEW);
    }

    @FXML
    private void openExpenses(ActionEvent event) {
        switchPane(Constants.EXPENSES_VIEW);
    }

    @FXML
    private void openReports(ActionEvent event) {
        switchPane(Constants.REPORT_VIEW);
    }

    @FXML
    private void openEmployees(ActionEvent event) {
        switchPane(Constants.EMPLOYEE_VIEW);
    }

    @FXML
    private void openInventory(ActionEvent event) {
        switchPane(Constants.INVENTORY_VIEW);
    }

    @FXML
    private void openSettings(ActionEvent event) {
        switchPane(Constants.SETTINGS_VIEW);
    }
    
    private void switchPane(String pane){
        
        try {
            MainViewController.temporatyPane.getChildren().clear();    
            StackPane stackPane = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = stackPane.getChildren();
            MainViewController.temporatyPane.getChildren().setAll(elements);
        } catch (IOException ex) {
            Logger.getLogger(SideMenuViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
