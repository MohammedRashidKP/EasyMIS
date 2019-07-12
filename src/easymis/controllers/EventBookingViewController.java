package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import easymis.controllers.assembler.EventDetailsAssembler;
import easymis.models.entity.Booking;
import easymis.models.entity.Event;
import easymis.models.entity.EventTypeDetail;
import easymis.models.entity.TransactionStatus;
import easymis.models.entity.enumeration.BookingStatus;
import easymis.models.entity.enumeration.EventCategory;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.EventRepository;
import easymis.models.service.EventBlockingService;
import easymis.models.service.EventCostService;
import easymis.models.utils.EventCategoryUtils;
import easymis.utils.AlertHelper;
import easymis.utils.AlertMessages;
import easymis.utils.DateHelper;
import easymis.utils.NumberFilter;
import easymis.views.viewobjects.ComboBoxViewObject;
import easymis.views.viewobjects.EventDetailsViewObject;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author RashidKP
 */
public class EventBookingViewController implements Initializable {

    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField addressLine1;
    @FXML
    private JFXTextField addressLine2;
    @FXML
    private JFXTextField addressLine3;
    @FXML
    private JFXTextField district;
    @FXML
    private JFXTextField state;
    @FXML
    private JFXTextField pinCode;
    @FXML
    private CheckBox wedding;
    @FXML
    private CheckBox mehandi;
    @FXML
    private CheckBox reception;
    @FXML
    private CheckBox acRequired;
    @FXML
    private CheckBox ishaHall;
    @FXML
    private CheckBox niceHall;
    @FXML
    private CheckBox additionalAC;
    @FXML
    private JFXDatePicker eventDate;
    @FXML
    private Label totalAmount;
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
    private Label lblEventCategory;
    @FXML
    private JFXTextField primaryMobileNumber;
    @FXML
    private JFXTextField alternateMobileNumber;
    @FXML
    private JFXButton btnBlock;
    @FXML
    private JFXButton btnBook;
    @FXML
    private JFXButton btnReset;
    @FXML
    private JFXTextField updFirstName;
    @FXML
    private JFXTextField updLastName;
    @FXML
    private JFXTextField updAddressLine1;
    @FXML
    private JFXTextField updAddressLine2;
    @FXML
    private JFXTextField updAddressLine3;
    @FXML
    private JFXTextField updDistrict;
    @FXML
    private JFXTextField updState;
    @FXML
    private JFXTextField updPinCode;
    @FXML
    private JFXTextField updPrimaryMobile;
    @FXML
    private JFXTextField updAlternateMobile;
    @FXML
    private CheckBox updWedding;
    @FXML
    private CheckBox updMehandi;
    @FXML
    private CheckBox updReception;
    @FXML
    private CheckBox updAcRequired;
    @FXML
    private CheckBox updIshaHall;
    @FXML
    private CheckBox updNiceHall;
    @FXML
    private CheckBox updAdditionalAC;
    @FXML
    private JFXDatePicker updEventDate;
    @FXML
    private JFXButton updBtnBook;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton updBtnReset;
    @FXML
    private Label lblEventCategory1;
    @FXML
    private JFXTextField updBookingId;
    @FXML
    private Label updBookingStatus;
    @FXML
    private JFXButton updCancelButton;
    @FXML
    private Tab panelTabAllEvents;
    @FXML
    private Tab panelTabAddEvent;
    @FXML
    private Tab panelTabUpdateEvent;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private TableColumn<EventDetailsViewObject, String> col_ReceiptNumber;
    @FXML
    private JFXTextField receiptNumber;
    @FXML
    private JFXTextField advanceAmount;
    @FXML
    private JFXTextField updReceiptNumber;
    @FXML
    private JFXTextField updAdvanceAmount;
    @FXML
    private ComboBox<String> additionalAcComboBox;
    @FXML
    private ComboBox<ComboBoxViewObject> receptionComboBox;
    @FXML
    private ComboBox<ComboBoxViewObject> ishaHallComboBox;
    @FXML
    private ComboBox<String> updAdditionalAcComboBox;
    @FXML
    private ComboBox<ComboBoxViewObject> updReceptionComboBox;
    @FXML
    private ComboBox<ComboBoxViewObject> updIshaHallComboBox;
    @FXML
    private Label updTotalAmount;
    @FXML
    private JFXTextField blockedBy;
    @FXML
    private JFXTextField updBlockedBy;
    @FXML
    private TextField searchText;
    @FXML
    private Button onSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEventTableColumnFields();
        initializePanelTab();
        updReceiptNumber.addEventFilter(KeyEvent.KEY_PRESSED, onReceiptNumberChange());
        launchAllEventsTab();
        setNumberFormatter();
        initComboBoxes();
    }

    @FXML
    private void blockEvent(ActionEvent event) {
        if (event != null && validateMandatory()) {
            showBookingConfirmation(BookingStatus.BLOCKED);
        }
    }

    @FXML
    private void bookEvent(ActionEvent event) {
        if (event != null && validateMandatory()) {
            showBookingConfirmation(BookingStatus.BOOKED);
        }
    }

    public void manageEventCreation(BookingStatus bookingStatus) {
        Booking bookingDetail = getBookingDetails(bookingStatus);
        TransactionStatus status = EventRepository.getUniqueInstance().create(bookingDetail);
        AlertHelper.showMessage(status);
        lblEventCategory.setText(bookingDetail.getEventCategory().toString());
        totalAmount.setText(String.valueOf(bookingDetail.getBookingCost()));
        if (status.isSuccess()) {
            makeFieldsEditable(false);
            blockedBy.setText(new EventBlockingService().getEventBlockedBy(bookingDetail.getEvents()));
        }

    }

    private Booking getBookingDetails(BookingStatus bookingStatus) {
        Booking booking = new Booking();
        java.sql.Date eventDateValue = java.sql.Date.valueOf(eventDate.getValue());
        booking.setEventDate(eventDateValue);
        booking.setReceiptNumber(receiptNumber.getText());
        if (advanceAmount.getText() != null && !"".equals(advanceAmount.getText())) {
            booking.setAdvanceAmount(Double.valueOf(advanceAmount.getText()));
        }
        booking.setFirstName(firstName.getText());
        booking.setLastName(lastName.getText());
        booking.setAddressLine1(addressLine1.getText());
        booking.setAddressLine2(addressLine2.getText());
        booking.setAddressLine3(addressLine3.getText());
        booking.setDistrict(district.getText());
        booking.setState(state.getText());
        booking.setPinCode(pinCode.getText());
        booking.setBookingStatus(bookingStatus);
        booking.setEventCategory(EventCategoryUtils.getEventCategory(buildEventCategoryDetail()));
        booking.setPrimaryMobile(primaryMobileNumber.getText());
        booking.setAlternateMobile(alternateMobileNumber.getText());
        booking.setCreatedDate(DateHelper.getToday());
        getEventDetails(booking);
        booking.setBookingCost(EventCostService.getTotalEventCost(booking.getEvents()));
        return booking;
    }

    private boolean validateMandatory() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Following fileds missing: ");
        if (eventDate.getValue() == null) {
            errorMessage.append(" Event Date,");
            isValid = false;
        }
        if (receiptNumber.getText() == null || "".equals(receiptNumber.getText())) {
            errorMessage.append(" Receipt Number,");
            isValid = false;
        }
        if (firstName.getText() == null || "".equals(firstName.getText())) {
            errorMessage.append(" First Name,");
            isValid = false;
        }
        if (addressLine1.getText() == null || "".equals(addressLine1.getText())) {
            errorMessage.append(" Address Line 1,");
            isValid = false;
        }
        if (primaryMobileNumber.getText() == null || "".equals(primaryMobileNumber.getText())) {
            errorMessage.append(" Mobile Number,");
            isValid = false;
        }
        if (!wedding.isSelected() && !reception.isSelected() && !niceHall.isSelected() && !ishaHall.isSelected()) {
            errorMessage.append(" Any Event");
            isValid = false;
        }
        if (!isValid) {
            String stringMessage = errorMessage.toString();
            AlertHelper.showErrorMessage(stringMessage);
        }
        return isValid;
    }

    private EventTypeDetail buildEventCategoryDetail() {
        EventTypeDetail eventCategoryDetail = new EventTypeDetail();
        eventCategoryDetail.setAcSelected(acRequired.isSelected());
        eventCategoryDetail.setAdditionalACSelected(additionalAC.isSelected());
        eventCategoryDetail.setIshaSelected(ishaHall.isSelected());
        eventCategoryDetail.setMehandiSelected(mehandi.isSelected());
        eventCategoryDetail.setNicaSelected(niceHall.isSelected());
        eventCategoryDetail.setReceptionSelected(reception.isSelected());
        eventCategoryDetail.setWeddingSelected(wedding.isSelected());
        return eventCategoryDetail;
    }

    @FXML
    private void resetEventDetails(ActionEvent event) {
        makeFieldsEditable(true);
        clearFields();
    }

    @FXML
    private void bookEventInUpdate(ActionEvent event) {
        if (validateMandatoryFieldsInUpdate()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("");
            alert.setTitle(getConfirmationDialogTitle(BookingStatus.BOOKED));
            alert.setContentText(getConfirmationDialogContent(BookingStatus.BOOKED));

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                Booking eventDetail = getBookingDetailsForUpdate(BookingStatus.BOOKED);
                manageEventUpdate(eventDetail, true);
            }
        }
    }

    @FXML
    private void updateEvent(ActionEvent event) {
        if (validateMandatoryFieldsInUpdate()) {
            BookingStatus currentBookingStatus = BookingStatus.fromValue(updBookingStatus.getText());
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("");
            alert.setTitle(getConfirmationDialogTitle(currentBookingStatus));
            alert.setContentText(getConfirmationDialogContent(currentBookingStatus));

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                Booking eventDetail = getBookingDetailsForUpdate(currentBookingStatus);
                manageEventUpdate(eventDetail, false);
            }
        }
    }

    private void manageEventUpdate(Booking eventDetail, boolean isNewBooking) {

        TransactionStatus status = EventRepository.getUniqueInstance().update(eventDetail, isNewBooking);
        AlertHelper.showMessage(status);
        lblEventCategory1.setText(eventDetail.getEventCategory().toString());
        updTotalAmount.setText(String.valueOf(eventDetail.getBookingCost()));
        if (status.isSuccess()) {
            makeFieldsEditableInUpdate(false);
            updBookingStatus.setText(eventDetail.getBookingStatus().toString());
        }

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
                        tabPane.getSelectionModel().select(panelTabUpdateEvent);
                        populateDetailsForUpdate(rowData.getReceiptNumber());
                    }
                }
            });
            return row;
        });
    }

    public EventHandler<KeyEvent> onReceiptNumberChange() {
        return (KeyEvent e) -> {
            if (e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER) {
                String bookingId = updReceiptNumber.getText();
                populateDetailsForUpdate(bookingId);
            }
        };
    }

    private void populateDetailsForUpdate(String receiptNumber) {
        if (receiptNumber != null && !"".equals(receiptNumber)) {
            Booking booking = EventRepository.getUniqueInstance().fetchByReceiptNumber(receiptNumber);
            if (booking != null) {
                clearAllFieldsInUpdate();
                makeFieldsEditableInUpdate(true);
                updReceiptNumber.setText(receiptNumber);
                updBookingId.setText(booking.getBookingId());
                updFirstName.setText(booking.getFirstName());
                updLastName.setText(booking.getLastName());
                updAddressLine1.setText(booking.getAddressLine1());
                updAddressLine2.setText(booking.getAddressLine2());
                updAddressLine3.setText(booking.getAddressLine3());
                updDistrict.setText(booking.getDistrict());
                updState.setText(booking.getState());
                updPinCode.setText(booking.getPinCode());
                updPrimaryMobile.setText(booking.getPrimaryMobile());
                updAlternateMobile.setText(booking.getAlternateMobile());
                updEventDate.setValue(booking.getEventDate().toLocalDate());
                lblEventCategory1.setText(booking.getEventCategory().toString());
                updBookingStatus.setText(booking.getBookingStatus().toString());
                updAdvanceAmount.setText(String.valueOf(booking.getAdvanceAmount()));
                updTotalAmount.setText(String.valueOf(booking.getBookingCost()));
                updEventDate.setDisable(true);
                updReceiptNumber.setEditable(false);
                updTotalAmount.setText(String.valueOf(booking.getBookingCost()));
                if (BookingStatus.BOOKED.equals(booking.getBookingStatus())) {
                    updBtnBook.setDisable(true);
                } else {
                    updBtnBook.setDisable(false);
                }
                if (BookingStatus.BLOCKING_CANCELLED.equals(booking.getBookingStatus()) || BookingStatus.BOOKING_CANCELLED.equals(booking.getBookingStatus())) {
                    updCancelButton.setDisable(true);
                } else {
                    updCancelButton.setDisable(false);
                }
                btnUpdate.setDisable(false);
                updBlockedBy.setText(new EventBlockingService().getEventBlockedBy(booking.getEvents()));
                if (booking.getEvents() != null && !booking.getEvents().isEmpty()) {
                    for (Event event : booking.getEvents()) {
                        if (null != event.getEventType()) {
                            switch (event.getEventType()) {
                                case WEDDING:
                                    updWedding.setSelected(true);
                                    updWedding.setUserData(event.getEventId());
                                    break;
                                case MEHANDI:
                                    updMehandi.setSelected(true);
                                    updMehandi.setUserData(event.getEventId());
                                    break;
                                case RECEPTION_3_PM:
                                    updReception.setSelected(true);
                                    updReceptionComboBox.setValue(new ComboBoxViewObject(EventType.RECEPTION_3_PM.toString(), EventType.RECEPTION_3_PM));
                                    updReception.setUserData(event.getEventId());
                                    break;
                                case RECEPTION_5_PM:
                                    updReception.setSelected(true);
                                    updReceptionComboBox.setValue(new ComboBoxViewObject(EventType.RECEPTION_5_PM.toString(), EventType.RECEPTION_5_PM));
                                    updReception.setUserData(event.getEventId());
                                    break;
                                case ISHA_HALL_AC_DAY:
                                    updIshaHall.setSelected(true);
                                    updIshaHallComboBox.setValue(new ComboBoxViewObject(EventType.ISHA_HALL_AC_DAY.toString(), EventType.ISHA_HALL_AC_DAY));
                                    updIshaHall.setUserData(event.getEventId());
                                    break;
                                case ISHA_HALL_AC_EVE:
                                    updIshaHall.setSelected(true);
                                    updIshaHallComboBox.setValue(new ComboBoxViewObject(EventType.ISHA_HALL_AC_EVE.toString(), EventType.ISHA_HALL_AC_EVE));
                                    updIshaHall.setUserData(event.getEventId());
                                    break;
                                case NICA_LONGUE_AC:
                                    updNiceHall.setSelected(true);
                                    updNiceHall.setUserData(event.getEventId());
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    updAcRequired.setSelected(booking.getEvents().get(0).isNormalAcRequired());
                    if (booking.getEvents().get(0).getAdditionalAcRange() > 0) {
                        updAdditionalAC.setSelected(true);
                        updAdditionalAcComboBox.setValue(String.valueOf(booking.getEvents().get(0).getAdditionalAcRange()));
                    }
                }
            }
        }
    }

    @FXML
    private void resetDetailsInUpdate(ActionEvent event) {
        makeFieldsEditableInUpdate(true);
        clearAllFieldsInUpdate();
    }

    private void makeFieldsEditable(boolean flag) {
        firstName.setEditable(flag);
        lastName.setEditable(flag);
        addressLine1.setEditable(flag);
        addressLine2.setEditable(flag);
        addressLine3.setEditable(flag);
        district.setEditable(flag);
        state.setEditable(flag);
        pinCode.setEditable(flag);
        wedding.setDisable(!flag);
        mehandi.setDisable(!flag);
        reception.setDisable(!flag);
        acRequired.setDisable(!flag);
        ishaHall.setDisable(!flag);
        niceHall.setDisable(!flag);
        additionalAC.setDisable(!flag);
        eventDate.setDisable(!flag);
        btnBlock.setDisable(!flag);
        btnBook.setDisable(!flag);
        receptionComboBox.setDisable(!flag);
        ishaHallComboBox.setDisable(!flag);
        additionalAcComboBox.setDisable(!flag);
        primaryMobileNumber.setEditable(flag);
        alternateMobileNumber.setEditable(flag);
        receiptNumber.setEditable(flag);
        advanceAmount.setEditable(flag);
        if (reception.isSelected()) {
            receptionComboBox.setDisable(flag);
        } else {
            receptionComboBox.setDisable(true);
        }
        if (ishaHall.isSelected()) {
            ishaHallComboBox.setDisable(flag);
        } else {
            ishaHallComboBox.setDisable(true);
        }
        if (additionalAC.isSelected()) {
            additionalAcComboBox.setDisable(flag);
        } else {
            additionalAcComboBox.setDisable(true);
        }

    }

    private void clearFields() {
        receiptNumber.clear();
        advanceAmount.clear();
        firstName.clear();
        lastName.clear();
        addressLine1.clear();
        addressLine2.clear();
        addressLine3.clear();
        district.clear();
        state.clear();
        pinCode.clear();
        wedding.setSelected(false);
        mehandi.setSelected(false);
        reception.setSelected(false);
        acRequired.setSelected(false);
        ishaHall.setSelected(false);
        niceHall.setSelected(false);
        additionalAC.setSelected(false);
        eventDate.setValue(null);
        primaryMobileNumber.clear();
        alternateMobileNumber.clear();
        totalAmount.setText("");
        lblEventCategory.setText("");
        receptionComboBox.setValue(null);
        ishaHallComboBox.setValue(null);
        additionalAcComboBox.setValue(null);
        blockedBy.setText("");
    }

    private void makeFieldsEditableInUpdate(boolean flag) {
        updFirstName.setEditable(flag);
        updLastName.setEditable(flag);
        updAddressLine1.setEditable(flag);
        updAddressLine2.setEditable(flag);
        updAddressLine3.setEditable(flag);
        updDistrict.setEditable(flag);
        updState.setEditable(flag);
        updPinCode.setEditable(flag);
        updPrimaryMobile.setEditable(flag);
        updAlternateMobile.setEditable(flag);
        updWedding.setDisable(!flag);
        updMehandi.setDisable(!flag);
        updReception.setDisable(!flag);
        updAcRequired.setDisable(!flag);
        updIshaHall.setDisable(!flag);
        updNiceHall.setDisable(!flag);
        updAdditionalAC.setDisable(!flag);
        updEventDate.setEditable(flag);
        updBtnBook.setDisable(!flag);
        updCancelButton.setDisable(!flag);
        btnUpdate.setDisable(!flag);
        updReceiptNumber.setEditable(flag);
        updAdvanceAmount.setEditable(flag);
        if (updReception.isSelected()) {
            updReceptionComboBox.setDisable(flag);
        } else {
            updReceptionComboBox.setDisable(true);
        }
        if (updIshaHall.isSelected()) {
            updIshaHallComboBox.setDisable(flag);
        } else {
            updIshaHallComboBox.setDisable(true);
        }
        if (updAdditionalAC.isSelected()) {
            updAdditionalAcComboBox.setDisable(flag);
        } else {
            updAdditionalAcComboBox.setDisable(true);
        }
    }

    private void clearAllFieldsInUpdate() {
        receiptNumber.clear();
        advanceAmount.clear();
        updFirstName.clear();
        updLastName.clear();
        updAddressLine1.clear();
        updAddressLine2.clear();
        updAddressLine3.clear();
        updDistrict.clear();
        updState.clear();
        updPinCode.clear();
        updPrimaryMobile.clear();
        updAlternateMobile.clear();
        updWedding.setSelected(false);
        updMehandi.setSelected(false);
        updReception.setSelected(false);
        updAcRequired.setSelected(false);
        updIshaHall.setSelected(false);
        updNiceHall.setSelected(false);
        updAdditionalAC.setSelected(false);
        updEventDate.setValue(null);
        lblEventCategory1.setText("");
        updBookingId.clear();
        updBookingStatus.setText("");
        updTotalAmount.setText("");
        lblEventCategory1.setText("");
        updReceptionComboBox.setValue(null);
        updIshaHallComboBox.setValue(null);
        updAdditionalAcComboBox.setValue(null);
        updBlockedBy.setText("");
    }

    @FXML
    private void onWeddingSelection(ActionEvent event) {
    }

    @FXML
    private void onMehandiSelection(ActionEvent event) {
    }

    @FXML
    private void onReceptionSelection(ActionEvent event) {
    }

    @FXML
    private void onAcRequiredSelection(ActionEvent event) {
    }

    @FXML
    private void onIshaHallSelection(ActionEvent event) {
    }

    @FXML
    private void onNicaHallSelection(ActionEvent event) {
    }

    @FXML
    private void onAdditionalAcSelection(ActionEvent event) {
    }

    private boolean validateMandatoryFieldsInUpdate() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Following fileds missing: ");
        if (updEventDate.getValue() == null) {
            errorMessage.append(" Event Date,");
            isValid = false;
        }
        if (updFirstName.getText() == null || "".equals(updFirstName.getText())) {
            errorMessage.append(" First Name,");
            isValid = false;
        }
        if (updAddressLine1.getText() == null || "".equals(updAddressLine1.getText())) {
            errorMessage.append(" Address Line 1,");
            isValid = false;
        }
        if (updPrimaryMobile.getText() == null || "".equals(updPrimaryMobile.getText())) {
            errorMessage.append(" Mobile Number,");
            isValid = false;
        }
        if (!updWedding.isSelected() && !updReception.isSelected() && !updNiceHall.isSelected() && !updIshaHall.isSelected()) {
            errorMessage.append(" Any Event");
            isValid = false;
        }
        if (!isValid) {
            String stringMessage = errorMessage.toString();
            AlertHelper.showErrorMessage(stringMessage);
        }
        return isValid;
    }

    private Booking getBookingDetailsForUpdate(BookingStatus bookingStatus) {
        Booking bookingDetails = new Booking();
        java.sql.Date eventDateValue = java.sql.Date.valueOf(updEventDate.getValue());
        bookingDetails.setBookingId(updBookingId.getText());
        bookingDetails.setReceiptNumber(updReceiptNumber.getText());
        if (updAdvanceAmount.getText() != null && !"".equals(updAdvanceAmount.getText())) {
            bookingDetails.setAdvanceAmount(Double.valueOf(updAdvanceAmount.getText()));
        }
        bookingDetails.setEventDate(eventDateValue);
        bookingDetails.setFirstName(updFirstName.getText());
        bookingDetails.setLastName(updLastName.getText());
        bookingDetails.setAddressLine1(updAddressLine1.getText());
        bookingDetails.setAddressLine2(updAddressLine2.getText());
        bookingDetails.setAddressLine3(updAddressLine3.getText());
        bookingDetails.setDistrict(updDistrict.getText());
        bookingDetails.setState(updState.getText());
        bookingDetails.setPinCode(updPinCode.getText());
        bookingDetails.setBookingStatus(bookingStatus);
        bookingDetails.setEventCategory(EventCategoryUtils.getEventCategory(buildEventCategoryDetailForUpdate()));
        bookingDetails.setPrimaryMobile(updPrimaryMobile.getText());
        bookingDetails.setAlternateMobile(updAlternateMobile.getText());
        bookingDetails.setCreatedDate(DateHelper.getToday());
        bookingDetails.setBookingCost(EventCostService.getTotalEventCost(getEventDetailsForUpdate(new Booking())));
        getEventDetailsForUpdate(bookingDetails);
        return bookingDetails;
    }

    private EventTypeDetail buildEventCategoryDetailForUpdate() {
        EventTypeDetail eventCategoryDetail = new EventTypeDetail();
        eventCategoryDetail.setAcSelected(updAcRequired.isSelected());
        eventCategoryDetail.setAdditionalACSelected(updAdditionalAC.isSelected());
        eventCategoryDetail.setIshaSelected(updIshaHall.isSelected());
        eventCategoryDetail.setMehandiSelected(updMehandi.isSelected());
        eventCategoryDetail.setNicaSelected(updNiceHall.isSelected());
        eventCategoryDetail.setReceptionSelected(updReception.isSelected());
        eventCategoryDetail.setWeddingSelected(updWedding.isSelected());
        return eventCategoryDetail;
    }

    @FXML
    private void cancelEvent(ActionEvent event) {

        if (validateMandatoryFieldsInUpdate()) {
            BookingStatus newBookngStatus = null;
            BookingStatus currentBookingStatus = BookingStatus.fromValue(updBookingStatus.getText());
            if (BookingStatus.BOOKED.equals(currentBookingStatus)) {
                newBookngStatus = BookingStatus.BOOKING_CANCELLED;
            } else if (BookingStatus.BLOCKED.equals(currentBookingStatus)) {
                newBookngStatus = BookingStatus.BLOCKING_CANCELLED;
            }
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("");
            alert.setTitle(getConfirmationDialogTitle(newBookngStatus));
            alert.setContentText(getConfirmationDialogContent(newBookngStatus));

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                Booking eventDetail = getBookingDetailsForUpdate(newBookngStatus);
                manageEventUpdate(eventDetail, false);
            }
        }
    }

    private void showBookingConfirmation(BookingStatus bookingStatus) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("");
        alert.setTitle(getConfirmationDialogTitle(bookingStatus));
        alert.setContentText(getConfirmationDialogContent(bookingStatus));

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        handleOkClickOnConfirmationPopup(option, bookingStatus);
    }

    private String getConfirmationDialogTitle(BookingStatus bookingStatus) {
        String title = "";
        if (null != bookingStatus) {
            switch (bookingStatus) {
                case BOOKED:
                    title = AlertMessages.BOOKING_CONFIRMATION_TITLE;
                    break;
                case BLOCKED:
                    title = AlertMessages.BLOCKING_CONFIRMATION_TITLE;
                    break;
                case BLOCKING_CANCELLED:
                case BOOKING_CANCELLED:
                    title = AlertMessages.CANCELLATION_CONFIRMATION_TITLE;
                    break;
                default:
                    break;
            }
        }
        return title;
    }

    private String getConfirmationDialogContent(BookingStatus bookingStatus) {
        String content = "";
        if (null != bookingStatus) {
            switch (bookingStatus) {
                case BOOKED:
                    content = AlertMessages.BOOKING_CONFIRMATION_CONTENT;
                    break;
                case BLOCKED:
                    content = AlertMessages.BLOCKING_CONFIRMATION_CONTENT;
                    break;
                case BLOCKING_CANCELLED:
                case BOOKING_CANCELLED:
                    content = AlertMessages.CANCELLATION_CONFIRMATION_CONTENT;
                    break;
                default:
                    break;
            }
        }
        return content;
    }

    private void handleOkClickOnConfirmationPopup(Optional<ButtonType> option, BookingStatus bookingStatus) {

        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            manageEventCreation(bookingStatus);
        }
    }

    private List<Event> getEventDetails(Booking bookingDetails) {
        List<Event> eventDetails = new ArrayList<>();
        if (wedding.isSelected()) {
            Event event = new Event();
            event.setEventType(EventType.WEDDING);
            event.setEventDate(bookingDetails.getEventDate());
            eventDetails.add(event);
        }
        if (mehandi.isSelected()) {
            Event event = new Event();
            event.setEventType(EventType.MEHANDI);
            if (bookingDetails.getEventDate() != null) {
                event.setEventDate(DateHelper.getPreviousDay(bookingDetails.getEventDate()));
            }
            eventDetails.add(event);
        }
        if (reception.isSelected()) {
            Event event = new Event();
            event.setEventType(receptionComboBox.getValue().getValue());
            event.setEventDate(bookingDetails.getEventDate());
            eventDetails.add(event);
        }
        if (ishaHall.isSelected()) {
            Event event = new Event();
            event.setEventType(ishaHallComboBox.getValue().getValue());
            event.setEventDate(bookingDetails.getEventDate());
            eventDetails.add(event);
        }
        if (niceHall.isSelected()) {
            Event event = new Event();
            event.setEventType(EventType.NICA_LONGUE_AC);
            event.setEventDate(bookingDetails.getEventDate());
            eventDetails.add(event);
        }
        for (Event event : eventDetails) {
            event.setNormalAcRequired(acRequired.isSelected());
            event.setBookingStatus(bookingDetails.getBookingStatus());
            if (additionalAC.isSelected()) {
                event.setAdditionalAcRange(Integer.valueOf(additionalAcComboBox.getValue()));
            }
            event.setBookingDetails(bookingDetails);
        }
        bookingDetails.setEvents(eventDetails);
        return eventDetails;
    }

    private List<Event> getEventDetailsForUpdate(Booking bookingDetails) {
        List<Event> eventDetails = new ArrayList<>();
        if (updWedding.isSelected()) {
            Event event = new Event();
            event.setEventType(EventType.WEDDING);
            event.setEventId((Integer) updWedding.getUserData());
            event.setEventDate(bookingDetails.getEventDate());
            eventDetails.add(event);
        }
        if (updMehandi.isSelected()) {
            Event event = new Event();
            event.setEventType(EventType.MEHANDI);
            event.setEventId((Integer) updMehandi.getUserData());
            if (bookingDetails.getEventDate() != null) {
                event.setEventDate(DateHelper.getPreviousDay(bookingDetails.getEventDate()));
            }
            eventDetails.add(event);
        }
        if (updReception.isSelected()) {
            Event event = new Event();
            event.setEventType(updReceptionComboBox.getValue().getValue());
            event.setEventId((Integer) updReception.getUserData());
            event.setEventDate(bookingDetails.getEventDate());
            eventDetails.add(event);
        }
        if (updIshaHall.isSelected()) {
            Event event = new Event();
            event.setEventType(updIshaHallComboBox.getValue().getValue());
            event.setEventId((Integer) updIshaHall.getUserData());
            event.setEventDate(bookingDetails.getEventDate());
            eventDetails.add(event);
        }
        if (updNiceHall.isSelected()) {
            Event event = new Event();
            event.setEventType(EventType.NICA_LONGUE_AC);
            event.setEventId((Integer) updNiceHall.getUserData());
            event.setEventDate(bookingDetails.getEventDate());
            eventDetails.add(event);
        }
        for (Event event : eventDetails) {
            event.setNormalAcRequired(updAcRequired.isSelected());
            event.setBookingStatus(bookingDetails.getBookingStatus());
            if (updAdditionalAC.isSelected()) {
                event.setAdditionalAcRange(Integer.valueOf(updAdditionalAcComboBox.getValue()));
            }
            event.setBookingDetails(bookingDetails);
        }
        bookingDetails.setEvents(eventDetails);
        return eventDetails;
    }

    private void initializeReceptionComboBox() {
        receptionComboBox.getItems().clear();
        receptionComboBox.getSelectionModel().clearSelection();
        ObservableList<ComboBoxViewObject> receptionCombo = FXCollections.observableArrayList();
        receptionCombo.addAll(new ComboBoxViewObject(EventType.RECEPTION_3_PM.toString(), EventType.RECEPTION_3_PM),
                new ComboBoxViewObject(EventType.RECEPTION_5_PM.toString(), EventType.RECEPTION_5_PM));
        receptionComboBox.setItems(receptionCombo);
        receptionComboBox.getSelectionModel().selectFirst();

    }

    private void initializeUpdateReceptionComboBox() {
        updReceptionComboBox.getItems().clear();
        updReceptionComboBox.getSelectionModel().clearSelection();
        ObservableList<ComboBoxViewObject> updReceptionCombo = FXCollections.observableArrayList();
        updReceptionCombo.addAll(new ComboBoxViewObject(EventType.RECEPTION_3_PM.toString(), EventType.RECEPTION_3_PM),
                new ComboBoxViewObject(EventType.RECEPTION_5_PM.toString(), EventType.RECEPTION_5_PM));
        updReceptionComboBox.setItems(updReceptionCombo);
        updReceptionComboBox.getSelectionModel().selectFirst();
    }

    private void initializeAdditionalAcComboBox() {
        additionalAcComboBox.getItems().clear();
        additionalAcComboBox.getSelectionModel().clearSelection();

        ObservableList<String> additionalACs = FXCollections.observableArrayList();
        for (int i = 1; i < 7; i++) {
            additionalACs.add(String.valueOf(i));
        }

        additionalAcComboBox.setItems(additionalACs);
        additionalAcComboBox.getSelectionModel().selectFirst();

    }

    private void initializeUpdateAdditionalAcComboBox() {
        updAdditionalAcComboBox.getItems().clear();
        updAdditionalAcComboBox.getSelectionModel().clearSelection();
        ObservableList<String> updAdditionalACs = FXCollections.observableArrayList();
        for (int i = 1; i < 7; i++) {
            updAdditionalACs.add(String.valueOf(i));
        }
        updAdditionalAcComboBox.setItems(updAdditionalACs);
        updAdditionalAcComboBox.getSelectionModel().selectFirst();
    }

    private void initializeIshHallComboBox() {
        ishaHallComboBox.getItems().clear();
        ishaHallComboBox.getSelectionModel().clearSelection();

        ObservableList<ComboBoxViewObject> ishaHallCombo = FXCollections.observableArrayList();
        ishaHallCombo.addAll(new ComboBoxViewObject(EventType.ISHA_HALL_AC_DAY.toString(), EventType.ISHA_HALL_AC_DAY),
                new ComboBoxViewObject(EventType.ISHA_HALL_AC_EVE.toString(), EventType.ISHA_HALL_AC_EVE));

        ishaHallComboBox.setItems(ishaHallCombo);
        ishaHallComboBox.getSelectionModel().selectFirst();

    }

    private void initializeUpdateIshHallComboBox() {
        updIshaHallComboBox.getItems().clear();
        updIshaHallComboBox.getSelectionModel().clearSelection();
        ObservableList<ComboBoxViewObject> updIshaHallCombo = FXCollections.observableArrayList();
        updIshaHallCombo.addAll(new ComboBoxViewObject(EventType.ISHA_HALL_AC_DAY.toString(), EventType.ISHA_HALL_AC_DAY),
                new ComboBoxViewObject(EventType.ISHA_HALL_AC_EVE.toString(), EventType.ISHA_HALL_AC_EVE));
        updIshaHallComboBox.setItems(updIshaHallCombo);
        updIshaHallComboBox.getSelectionModel().selectFirst();
    }

    private void initializePanelTab() {
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newTab) {

                if (null != newTab.getId()) {
                    switch (newTab.getId()) {
                        case "panelTabAllEvents":
                            launchAllEventsTab();
                            break;
                        case "panelTabAddEvent":
                            launchPrepareAddEventTab();
                            break;
                        case "panelTabUpdateEvent":
                            launchPrepareUpdateEventTab();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    private void launchAllEventsTab() {
        List<Booking> eventDetails = EventRepository.getUniqueInstance().fetchAllEvents();
        EventDetailsAssembler assembler = new EventDetailsAssembler();
        observableList.clear();
        eventDetails.stream().forEach((eventDetail) -> {
            observableList.add(assembler.toEventDetailsViewObject(eventDetail));
        });
        FilteredList<EventDetailsViewObject> filteredData = new FilteredList<>(observableList, p -> true);
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(booking -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (booking.getReceiptNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (booking.getEventDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }else if (booking.getFullName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }else if (booking.getBookingStatus().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }else if (booking.getEventType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }else if (booking.getBookingDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }else if (booking.getEventCategory().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                return false; 
            });
        });
        SortedList<EventDetailsViewObject> sortedData = new SortedList<>(filteredData);
        
        sortedData.comparatorProperty().bind(eventTable.comparatorProperty());
        
        eventTable.setItems(sortedData);
    }

    private void launchPrepareAddEventTab() {
        makeFieldsEditable(true);
        clearFields();
    }

    private void launchPrepareUpdateEventTab() {
        initializeUpdateReceptionComboBox();
        initializeUpdateIshHallComboBox();
        initializeUpdateAdditionalAcComboBox();
    }

    @FXML
    private void getCost(ActionEvent event) {
        double totalCost = EventCostService.getTotalEventCost(getEventDetails(new Booking()));
        totalAmount.setText(String.valueOf(totalCost));
    }

    @FXML
    private void getCostInUpdate(ActionEvent event) {
        double totalCost = EventCostService.getTotalEventCost(getEventDetailsForUpdate(new Booking()));
        updTotalAmount.setText(String.valueOf(totalCost));
    }

    private void setNumberFormatter() {
        primaryMobileNumber.setTextFormatter(new NumberFilter().filter());
        alternateMobileNumber.setTextFormatter(new NumberFilter().filter());
        pinCode.setTextFormatter(new NumberFilter().filter());
        advanceAmount.setTextFormatter(new NumberFilter().decimalFilter());
        updPrimaryMobile.setTextFormatter(new NumberFilter().filter());
        updAlternateMobile.setTextFormatter(new NumberFilter().filter());
        updPinCode.setTextFormatter(new NumberFilter().filter());
        updAdvanceAmount.setTextFormatter(new NumberFilter().decimalFilter());
    }

    private void initComboBoxes() {
        reception.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                receptionComboBox.getItems().clear();
                receptionComboBox.getSelectionModel().clearSelection();
                if (newValue) {
                    receptionComboBox.setDisable(false);
                    initializeReceptionComboBox();
                } else {
                    receptionComboBox.setDisable(true);
                }
            }
        });

        updReception.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                updReceptionComboBox.getItems().clear();
                updReceptionComboBox.getSelectionModel().clearSelection();
                if (newValue) {
                    updReceptionComboBox.setDisable(false);
                    initializeUpdateReceptionComboBox();
                } else {
                    updReceptionComboBox.setDisable(true);
                }
            }
        });

        ishaHall.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                ishaHallComboBox.getItems().clear();
                ishaHallComboBox.getSelectionModel().clearSelection();
                if (newValue) {
                    ishaHallComboBox.setDisable(false);
                    initializeIshHallComboBox();
                } else {
                    ishaHallComboBox.setDisable(true);
                }
            }
        });

        updIshaHall.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                updIshaHallComboBox.getItems().clear();
                updIshaHallComboBox.getSelectionModel().clearSelection();
                if (newValue) {
                    updIshaHallComboBox.setDisable(false);
                    initializeUpdateIshHallComboBox();
                } else {
                    updIshaHallComboBox.setDisable(true);
                }
            }
        });

        additionalAC.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                additionalAcComboBox.getItems().clear();
                additionalAcComboBox.getSelectionModel().clearSelection();
                if (newValue) {
                    additionalAcComboBox.setDisable(false);
                    initializeAdditionalAcComboBox();
                } else {
                    additionalAcComboBox.setDisable(true);
                }
            }
        });

        updAdditionalAC.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                updAdditionalAcComboBox.getItems().clear();
                updAdditionalAcComboBox.getSelectionModel().clearSelection();
                if (newValue) {
                    updAdditionalAcComboBox.setDisable(false);
                    initializeUpdateAdditionalAcComboBox();
                } else {
                    updAdditionalAcComboBox.setDisable(true);
                }
            }
        });
    }
}
