package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import easymis.controllers.assembler.EventDetailsAssembler;
import easymis.models.entity.EventDetails;
import easymis.models.entity.EventTypeDetail;
import easymis.models.entity.TransactionStatus;
import easymis.models.entity.enumeration.BookingStatus;
import easymis.models.entity.enumeration.BookingType;
import easymis.models.entity.enumeration.EventCategory;
import easymis.models.entity.utils.EventCategoryUtils;
import easymis.models.repository.EventRepository;
import easymis.utils.AlertHelper;
import easymis.utils.AlertMessages;
import easymis.utils.DateHelper;
import easymis.views.viewobjects.EventDetailsViewObject;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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
    private TableColumn<EventDetailsViewObject, BookingType> col_bookingId;
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
    private Label totalAmount1;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEventTableColumnFields();
        updBookingId.addEventFilter(KeyEvent.KEY_PRESSED, onBookingIdChange());
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
        EventDetails eventDetail = getEventDetails(bookingStatus);
        TransactionStatus status = EventRepository.getUniqueInstance().create(eventDetail);
        AlertHelper.showMessage(status);
        lblEventCategory.setText(eventDetail.getEventCategory().toString());
        if (status.isSuccess()) {
            disableAllFields(true);
        }

    }

    private EventDetails getEventDetails(BookingStatus bookingStatus) {
        EventDetails eventDetails = new EventDetails();
        java.sql.Date eventDateValue = java.sql.Date.valueOf(eventDate.getValue());
        eventDetails.setEventDate(eventDateValue);
        eventDetails.setFirstName(firstName.getText());
        eventDetails.setLastName(lastName.getText());
        eventDetails.setAddressLine1(addressLine1.getText());
        eventDetails.setAddressLine2(addressLine2.getText());
        eventDetails.setAddressLine3(addressLine3.getText());
        eventDetails.setDistrict(district.getText());
        eventDetails.setState(state.getText());
        eventDetails.setPinCode(pinCode.getText());
        eventDetails.setWeddingSelected(wedding.isSelected());
        eventDetails.setMehandiSelected(mehandi.isSelected());
        eventDetails.setReceptionSelected(reception.isSelected());
        eventDetails.setIshaSelected(ishaHall.isSelected());
        eventDetails.setNicaSelected(niceHall.isSelected());
        eventDetails.setAcSelected(acRequired.isSelected());
        eventDetails.setAdditionalACSelected(additionalAC.isSelected());
        eventDetails.setBookingStatus(bookingStatus);
        eventDetails.setEventCategory(EventCategoryUtils.getEventCategory(buildEventCategoryDetail()));
        eventDetails.setPrimaryMobile(primaryMobileNumber.getText());
        eventDetails.setAlternateMobile(alternateMobileNumber.getText());
        eventDetails.setCreatedDate(DateHelper.getToday());
        return eventDetails;
    }

    private boolean validateMandatory() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Following fileds missing: ");
        if (eventDate.getValue() == null) {
            errorMessage.append(" Event Date,");
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
        disableAllFields(false);
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
                EventDetails eventDetail = getEventDetailsForUpdate(BookingStatus.BOOKED);
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
                EventDetails eventDetail = getEventDetailsForUpdate(currentBookingStatus);
                manageEventUpdate(eventDetail, false);
            }

        }
    }

    private void manageEventUpdate(EventDetails eventDetail, boolean isNewBooking) {

        TransactionStatus status = EventRepository.getUniqueInstance().update(eventDetail, isNewBooking);
        AlertHelper.showMessage(status);
        lblEventCategory1.setText(eventDetail.getEventCategory().toString());
        if (status.isSuccess()) {
            disableAllFieldsInUpdate(true);
            updBookingStatus.setText(eventDetail.getBookingStatus().toString());
        }

    }

    private void initializeEventTableColumnFields() {
        col_EventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        col_fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_BookingStatus.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
        col_eventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        col_bookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        col_BookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        col_EventCategory.setCellValueFactory(new PropertyValueFactory<>("eventCategory"));
        eventTable.setRowFactory(tv -> {
            TableRow<EventDetailsViewObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    EventDetailsViewObject rowData = row.getItem();
                    if (rowData != null) {
                        populateDetailsForUpdate(rowData.getBookingId());
                        tabPane.getSelectionModel().select(panelTabUpdateEvent);
                    }
                }
            });
            return row;
        });
    }

    public EventHandler<KeyEvent> onBookingIdChange() {
        return (KeyEvent e) -> {
            if (e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER) {
                String bookingId = updBookingId.getText();
                populateDetailsForUpdate(bookingId);
            }
        };
    }

    private void populateDetailsForUpdate(String bookingId) {
        if (bookingId != null && !"".equals(bookingId)) {
            EventDetails event = EventRepository.getUniqueInstance().fetchByBookingId(bookingId);
            if (event != null) {
                clearAllFieldsInUpdate();
                updBookingId.setText(bookingId);
                updFirstName.setText(event.getFirstName());
                updLastName.setText(event.getLastName());
                updAddressLine1.setText(event.getAddressLine1());
                updAddressLine2.setText(event.getAddressLine2());
                updAddressLine3.setText(event.getAddressLine3());
                updDistrict.setText(event.getDistrict());
                updState.setText(event.getState());
                updPinCode.setText(event.getPinCode());
                updPrimaryMobile.setText(event.getPrimaryMobile());
                updAlternateMobile.setText(event.getAlternateMobile());
                updWedding.setSelected(event.isWeddingSelected());
                updMehandi.setSelected(event.isMehandiSelected());
                updReception.setSelected(event.isReceptionSelected());
                updAcRequired.setSelected(event.isAcSelected());
                updIshaHall.setSelected(event.isIshaSelected());
                updNiceHall.setSelected(event.isNicaSelected());
                updAdditionalAC.setSelected(event.isAdditionalACSelected());
                updEventDate.setValue(event.getEventDate().toLocalDate());
                lblEventCategory1.setText(event.getEventCategory().toString());
                updBookingStatus.setText(event.getBookingStatus().toString());
                updBookingId.setDisable(true);
                updEventDate.setDisable(true);
                if (BookingStatus.BOOKED.equals(event.getBookingStatus())) {
                    updBtnBook.setDisable(true);
                } else {
                    updBtnBook.setDisable(false);
                }
                if (BookingStatus.BLOCKING_CANCELLED.equals(event.getBookingStatus()) || BookingStatus.BOOKING_CANCELLED.equals(event.getBookingStatus())) {
                    updCancelButton.setDisable(true);
                } else {
                    updCancelButton.setDisable(false);
                }
                btnUpdate.setDisable(false);
            }
        }
    }

    @FXML
    private void resetDetailsInUpdate(ActionEvent event) {
        disableAllFieldsInUpdate(false);
        clearAllFieldsInUpdate();
    }

    private void disableAllFields(boolean flag) {
        firstName.setDisable(flag);
        lastName.setDisable(flag);
        addressLine1.setDisable(flag);
        addressLine2.setDisable(flag);
        addressLine3.setDisable(flag);
        district.setDisable(flag);
        state.setDisable(flag);
        pinCode.setDisable(flag);
        wedding.setDisable(flag);
        mehandi.setDisable(flag);
        reception.setDisable(flag);
        acRequired.setDisable(flag);
        ishaHall.setDisable(flag);
        niceHall.setDisable(flag);
        additionalAC.setDisable(flag);
        eventDate.setDisable(flag);
        btnBlock.setDisable(flag);
        btnBook.setDisable(flag);
        primaryMobileNumber.setDisable(flag);
        alternateMobileNumber.setDisable(flag);
    }

    private void clearFields() {
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
    }

    private void disableAllFieldsInUpdate(boolean flag) {
        updFirstName.setDisable(flag);
        updLastName.setDisable(flag);
        updAddressLine1.setDisable(flag);
        updAddressLine2.setDisable(flag);
        updAddressLine3.setDisable(flag);
        updDistrict.setDisable(flag);
        updState.setDisable(flag);
        updPinCode.setDisable(flag);
        updPrimaryMobile.setDisable(flag);
        updAlternateMobile.setDisable(flag);
        updWedding.setDisable(flag);
        updMehandi.setDisable(flag);
        updReception.setDisable(flag);
        updAcRequired.setDisable(flag);
        updIshaHall.setDisable(flag);
        updNiceHall.setDisable(flag);
        updAdditionalAC.setDisable(flag);
        updEventDate.setDisable(flag);
        lblEventCategory1.setDisable(flag);
        updBookingId.setDisable(flag);
        updBtnBook.setDisable(true);
        updCancelButton.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void clearAllFieldsInUpdate() {
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

    private EventDetails getEventDetailsForUpdate(BookingStatus bookingStatus) {
        EventDetails eventDetails = new EventDetails();
        java.sql.Date eventDateValue = java.sql.Date.valueOf(updEventDate.getValue());
        eventDetails.setBookingId(updBookingId.getText());
        eventDetails.setEventDate(eventDateValue);
        eventDetails.setFirstName(updFirstName.getText());
        eventDetails.setLastName(updLastName.getText());
        eventDetails.setAddressLine1(updAddressLine1.getText());
        eventDetails.setAddressLine2(updAddressLine2.getText());
        eventDetails.setAddressLine3(updAddressLine3.getText());
        eventDetails.setDistrict(updDistrict.getText());
        eventDetails.setState(updState.getText());
        eventDetails.setPinCode(updPinCode.getText());
        eventDetails.setWeddingSelected(updWedding.isSelected());
        eventDetails.setMehandiSelected(updMehandi.isSelected());
        eventDetails.setReceptionSelected(updReception.isSelected());
        eventDetails.setIshaSelected(updIshaHall.isSelected());
        eventDetails.setNicaSelected(updNiceHall.isSelected());
        eventDetails.setAcSelected(updAcRequired.isSelected());
        eventDetails.setAdditionalACSelected(updAdditionalAC.isSelected());
        eventDetails.setBookingStatus(bookingStatus);
        eventDetails.setEventCategory(EventCategoryUtils.getEventCategory(buildEventCategoryDetailForUpdate()));
        eventDetails.setPrimaryMobile(updPrimaryMobile.getText());
        eventDetails.setAlternateMobile(updAlternateMobile.getText());
        eventDetails.setCreatedDate(DateHelper.getToday());
        return eventDetails;
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
                EventDetails eventDetail = getEventDetailsForUpdate(newBookngStatus);
                manageEventUpdate(eventDetail, false);
            }
        }
    }

    @FXML
    private void onAllEventsTabSelection(Event event) {
        if (event != null) {
            List<EventDetails> eventDetails = EventRepository.getUniqueInstance().fetchAllEvents();
            EventDetailsAssembler assembler = new EventDetailsAssembler();
            observableList.clear();
            eventDetails.stream().forEach((eventDetail) -> {
                observableList.add(assembler.toEventDetailsViewObject(eventDetail));
            });
            eventTable.setItems(observableList);
        }
    }

    @FXML
    private void onAddEventTabSelection(Event event) {

    }

    @FXML
    private void onUpdateEventTabSelection(Event event) {

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
}
