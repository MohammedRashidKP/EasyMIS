package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import easymis.controllers.assembler.EventDetailsAssembler;
import easymis.models.entity.Booking;
import easymis.models.entity.Expenses;
import easymis.models.entity.enumeration.BookingStatus;
import easymis.models.entity.enumeration.EventCategory;
import easymis.models.repository.EventRepository;
import easymis.models.repository.ExpensesRepository;
import easymis.utils.DateHelper;
import easymis.utils.NumberFilter;
import easymis.utils.StringUtils;
import easymis.views.viewobjects.EventDetailsViewObject;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Rashid
 */
public class ExpensesController implements Initializable {

    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab panelTabCompletedEvents;
    @FXML
    private TableColumn<EventDetailsViewObject, String> col_EventDate;
    @FXML
    private TableColumn<EventDetailsViewObject, String> col_fullName;
    @FXML
    private TableColumn<EventDetailsViewObject, BookingStatus> col_BookingStatus;
    @FXML
    private TableColumn<EventDetailsViewObject, String> col_eventType;
    @FXML
    private TableColumn<EventDetailsViewObject, Date> col_BookingDate;
    @FXML
    private TableView<EventDetailsViewObject> eventTable;

    ObservableList<EventDetailsViewObject> observableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<EventDetailsViewObject, EventCategory> col_EventCategory;
    @FXML
    private Tab panelTabExpenses;
    @FXML
    private TableColumn<EventDetailsViewObject, String> col_ReceiptNumber;
    @FXML
    private JFXTextField electricity;
    @FXML
    private JFXTextField cleaning;
    @FXML
    private JFXTextField dailyWages;
    @FXML
    private JFXTextField security;
    @FXML
    private JFXTextField diesel;
    @FXML
    private JFXTextField tax;
    @FXML
    private JFXTextField weddingGift;
    @FXML
    private JFXTextField otherExpenses;
    @FXML
    private JFXTextField bonus;
    @FXML
    private JFXTextField purchaseKitchenAndStationary;
    @FXML
    private JFXTextField maintenance;
    @FXML
    private JFXTextField discounts;
    @FXML
    private JFXTextField eventDate;
    @FXML
    private JFXTextField advancePaid;
    @FXML
    private JFXTextField eventType;
    @FXML
    private JFXTextField totalExpense;
    @FXML
    private JFXTextField balance;
    @FXML
    private JFXTextField otherRevenue;
    @FXML
    private JFXButton calculateButton;
    @FXML
    private JFXTextField receiptNumber;
    @FXML
    private JFXTextField totalBookingRevenue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEventTableColumnFields();
        initializePanelTab();
        launchCompletedEventsTab();
        setNumberFormatter();
    }

    @FXML
    private void onCalculateButtonClick(ActionEvent event) {
        
        Expenses expenses =  getExpenses();
        if(expenses != null && StringUtils.isNotNullCheckSpace(expenses.getReceiptNumber())){
            double totalCalculatedExpense = expenses.getAuditoriumMaintenance()
                    +expenses.getBonusPaid()
                    +expenses.getCleaing()
                    +expenses.getDailyWages()
                    +expenses.getDiesel()
                    +expenses.getDiscounts()
                    +expenses.getElectricity()
                    +expenses.getPurchase()
                    +expenses.getSecurity()
                    +expenses.getTax()
                    +expenses.getWeddingGift()
                    +expenses.getOtherExpenses();
            this.totalExpense.setText(String.valueOf(totalCalculatedExpense));
            double totalCalculatedRevenue = Double.valueOf(totalBookingRevenue.getText());
            if(StringUtils.isNotNullCheckSpace(otherRevenue.getText()))
                totalCalculatedRevenue = totalCalculatedRevenue + Double.valueOf(otherRevenue.getText());
            totalBookingRevenue.setText(String.valueOf(totalCalculatedRevenue));
            double balanceAmount = totalCalculatedRevenue - totalCalculatedExpense;
            balance.setText(String.valueOf(balanceAmount));
            ExpensesRepository.getUniqueInstance().update(getExpenses());
        }
    }

    @FXML
    private void onEditButtonClick(ActionEvent event) {
        setFieldsEditable(true);
    }

    private void initializeEventTableColumnFields() {
        col_EventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        col_fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_BookingStatus.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
        col_eventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        col_BookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        col_EventCategory.setCellValueFactory(new PropertyValueFactory<>("eventCategory"));
        col_ReceiptNumber.setCellValueFactory(new PropertyValueFactory<>("receiptNumber"));
        eventTable.setRowFactory(tv -> {
            TableRow<EventDetailsViewObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    EventDetailsViewObject rowData = row.getItem();
                    if (rowData != null) {
                        tabPane.getSelectionModel().select(panelTabExpenses);
                        populateDetailsForExpense(rowData.getReceiptNumber());
                    }
                }
            });
            return row;
        });
    }

    private void initializePanelTab() {
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newTab) {

                if (null != newTab.getId()) {
                    switch (newTab.getId()) {
                        case "panelTabCompletedEvents":
                            launchCompletedEventsTab();
                            break;
                        case "panelTabExpenses":
                            launchExpenseTab();
                            break;
                        default:
                            break;
                    }
                }
            }

        });
    }

    private void launchCompletedEventsTab() {
        List<Booking> eventDetails = EventRepository.getUniqueInstance().fetchCompletedEvents();
        EventDetailsAssembler assembler = new EventDetailsAssembler();
        observableList.clear();
        eventDetails.stream().forEach((eventDetail) -> {
            observableList.add(assembler.toEventDetailsViewObject(eventDetail));
        });
        eventTable.setItems(observableList);
    }

    private void launchExpenseTab() {
        setFieldsEditable(false);
    }

    private void populateDetailsForExpense(String receiptNumber) {
        if (receiptNumber != null && !"".equals(receiptNumber)) {
            this.receiptNumber.setText(receiptNumber);
            Booking booking = EventRepository.getUniqueInstance().fetchByReceiptNumber(receiptNumber);
            if (booking != null) {
                eventDate.setText(DateHelper.format(booking.getEventDate()));
                advancePaid.setText(String.valueOf(booking.getAdvanceAmount()));
                totalBookingRevenue.setText(String.valueOf(booking.getBookingCost()));
            }
            Expenses expenses = ExpensesRepository.getUniqueInstance().fetchExpensesForReceiptNumber(receiptNumber);
            if (expenses != null) {
                electricity.setText(String.valueOf(expenses.getElectricity()));
                cleaning.setText(String.valueOf(expenses.getCleaing()));
                dailyWages.setText(String.valueOf(expenses.getDailyWages()));
                security.setText(String.valueOf(expenses.getSecurity()));
                diesel.setText(String.valueOf(expenses.getDiesel()));
                tax.setText(String.valueOf(expenses.getTax()));
                weddingGift.setText(String.valueOf(expenses.getWeddingGift()));
                otherExpenses.setText(String.valueOf(expenses.getOtherExpenses()));
                bonus.setText(String.valueOf(expenses.getBonusPaid()));
                purchaseKitchenAndStationary.setText(String.valueOf(expenses.getPurchase()));
                maintenance.setText(String.valueOf(expenses.getAuditoriumMaintenance()));
                discounts.setText(String.valueOf(expenses.getDiscounts()));
                totalBookingRevenue.setText(String.valueOf(expenses.getTotalRevenue()));
                totalExpense.setText(String.valueOf(expenses.getTotalExpense()));
                otherRevenue.setText(String.valueOf(expenses.getOtherRevenue()));
                balance.setText(String.valueOf(expenses.getBalance()));
            }
        }
    }

    private Expenses getExpenses() {
        Expenses expenses = new Expenses();
        expenses.setReceiptNumber(receiptNumber.getText());
        if (StringUtils.isNotNullCheckSpace(electricity.getText())) {
            expenses.setElectricity(Integer.valueOf(electricity.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(cleaning.getText())) {
            expenses.setCleaing(Integer.valueOf(cleaning.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(dailyWages.getText())) {
            expenses.setDailyWages(Integer.valueOf(dailyWages.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(security.getText())) {
            expenses.setSecurity(Integer.valueOf(security.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(diesel.getText())) {
            expenses.setDiesel(Integer.valueOf(diesel.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(tax.getText())) {
            expenses.setTax(Integer.valueOf(tax.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(weddingGift.getText())) {
            expenses.setWeddingGift(Integer.valueOf(weddingGift.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(otherExpenses.getText())) {
            expenses.setOtherExpenses(Integer.valueOf(otherExpenses.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(bonus.getText())) {
            expenses.setBonusPaid(Integer.valueOf(bonus.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(purchaseKitchenAndStationary.getText())) {
            expenses.setPurchase(Integer.valueOf(purchaseKitchenAndStationary.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(maintenance.getText())) {
            expenses.setAuditoriumMaintenance(Integer.valueOf(maintenance.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(discounts.getText())) {
            expenses.setDiscounts(Integer.valueOf(discounts.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(totalBookingRevenue.getText())) {
            expenses.setTotalRevenue(Integer.valueOf(totalBookingRevenue.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(totalExpense.getText())) {
            expenses.setTotalExpense(Integer.valueOf(totalExpense.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(balance.getText())) {
            expenses.setBalance(Integer.valueOf(balance.getText()));
        }
        if (StringUtils.isNotNullCheckSpace(otherRevenue.getText())) {
            expenses.setOtherRevenue(Integer.valueOf(otherRevenue.getText()));
        }

        return expenses;
    }

    private void setNumberFormatter() {
        electricity.setTextFormatter(new NumberFilter().decimalFilter());
        cleaning.setTextFormatter(new NumberFilter().decimalFilter());
        dailyWages.setTextFormatter(new NumberFilter().decimalFilter());
        security.setTextFormatter(new NumberFilter().decimalFilter());
        diesel.setTextFormatter(new NumberFilter().decimalFilter());
        tax.setTextFormatter(new NumberFilter().decimalFilter());
        weddingGift.setTextFormatter(new NumberFilter().decimalFilter());
        otherExpenses.setTextFormatter(new NumberFilter().decimalFilter());
        bonus.setTextFormatter(new NumberFilter().decimalFilter());
        purchaseKitchenAndStationary.setTextFormatter(new NumberFilter().decimalFilter());
        maintenance.setTextFormatter(new NumberFilter().decimalFilter());
        discounts.setTextFormatter(new NumberFilter().decimalFilter());
        otherRevenue.setTextFormatter(new NumberFilter().decimalFilter());
    }
    
    private void setFieldsEditable(boolean flag){
        electricity.setEditable(flag);
        cleaning.setEditable(flag);
        dailyWages.setEditable(flag);
        security.setEditable(flag);
        diesel.setEditable(flag);
        tax.setEditable(flag);
        weddingGift.setEditable(flag);
        otherExpenses.setEditable(flag);
        bonus.setEditable(flag);
        purchaseKitchenAndStationary.setEditable(flag);
        maintenance.setEditable(flag);
        discounts.setEditable(flag);
        otherRevenue.setEditable(flag);
        calculateButton.setDisable(!flag);
    }
}
