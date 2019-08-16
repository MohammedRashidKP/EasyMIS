package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import easymis.controllers.assembler.EventDetailsAssembler;
import easymis.models.entity.Booking;
import easymis.models.entity.Event;
import easymis.models.entity.EventTypeDetail;
import easymis.models.entity.TransactionStatus;
import easymis.models.entity.enumeration.ActionType;
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
import easymis.utils.StringUtils;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author RashidKP
 */
public class EventBookingViewController implements Initializable {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField addressLine1;
    @FXML
    private TextField addressLine2;
    @FXML
    private TextField addressLine3;
    @FXML
    private TextField district;
    @FXML
    private TextField state;
    @FXML
    private TextField pinCode;
    @FXML
    private CheckBox wedding;
    @FXML
    private CheckBox mehandi;
    @FXML
    private CheckBox reception;
    @FXML
    private CheckBox ishaHall;
    @FXML
    private CheckBox niceHall;
    @FXML
    private CheckBox additionalAC;
    @FXML
    private DatePicker eventDate;
    @FXML
    private TextField totalAmount;
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
    private TextField lblEventCategory;
    @FXML
    private TextField primaryMobileNumber;
    @FXML
    private TextField alternateMobileNumber;

    @FXML
    private JFXButton btnReset;

    @FXML
    private Tab panelTabAllEvents;
    @FXML
    private Tab panelTabAddEvent;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private TableColumn<EventDetailsViewObject, String> col_ReceiptNumber;
    @FXML
    private TextField receiptNumber;
    @FXML
    private TextField advanceAmount;
    @FXML
    private ComboBox<String> additionalAcComboBox;
    @FXML
    private ComboBox<ComboBoxViewObject> receptionComboBox;
    @FXML
    private ComboBox<ComboBoxViewObject> ishaHallComboBox;
    @FXML
    private TextField blockedBy;
    @FXML
    private TextField searchText;
    @FXML
    private ComboBox<String> actionComboBOx;
    @FXML
    private Button submitButton;
    @FXML
    private Text bookingStatus;
    @FXML
    private Text bookingId;
    @FXML
    private Pane diamondNote;
    @FXML
    private TextField remarks;
    @FXML
    private TableColumn<EventDetailsViewObject, String> col_remarks;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEventTableColumnFields();
        initializePanelTab();
        this.receiptNumber.addEventFilter(KeyEvent.KEY_PRESSED, onReceiptNumberChange());
        launchAllEventsTab();
        setNumberFormatter();
        initComboBoxes();
    }

    private void blockEvent(ActionEvent event) {
        if (event != null && validateMandatory()) {
            showBookingConfirmation(BookingStatus.BLOCKED);
        }
    }

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
            if(EventCategory.DIAMOND == bookingDetail.getEventCategory())
                diamondNote.setVisible(true);
        }

    }

    private Booking getBookingDetails(BookingStatus bookingStatus) {
        Booking booking = new Booking();
        java.sql.Date eventDateValue = java.sql.Date.valueOf(eventDate.getValue());
        if (StringUtils.isNotNullCheckSpace(bookingId.getText())) {
            booking.setBookingId(bookingId.getText());
        }
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
        booking.setRemarks(remarks.getText());
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
        initActionComboBox();
    }

    private void bookEventInUpdate() {
        if (validateMandatory()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("");
            alert.setTitle(getConfirmationDialogTitle(BookingStatus.BOOKED));
            alert.setContentText(getConfirmationDialogContent(BookingStatus.BOOKED));

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                Booking eventDetail = getBookingDetails(BookingStatus.BOOKED);
                manageEventUpdate(eventDetail, true);
            }
        }
    }

    private void updateEvent() {
        if (validateMandatory()) {
            BookingStatus currentBookingStatus = BookingStatus.fromValue(bookingStatus.getText());
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("");
            alert.setTitle(getConfirmationDialogTitle(currentBookingStatus));
            alert.setContentText(getConfirmationDialogContent(currentBookingStatus));

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                Booking eventDetail = getBookingDetails(currentBookingStatus);
                manageEventUpdate(eventDetail, true);
            }
        }
    }

    private void manageEventUpdate(Booking eventDetail, boolean isValidationRequired) {

        TransactionStatus status = EventRepository.getUniqueInstance().update(eventDetail, isValidationRequired);
        AlertHelper.showMessage(status);
        lblEventCategory.setText(eventDetail.getEventCategory().toString());
        totalAmount.setText(String.valueOf(eventDetail.getBookingCost()));
        if (status.isSuccess()) {
            makeFieldsEditable(false);
            bookingStatus.setText(eventDetail.getBookingStatus().toString());
            if(EventCategory.DIAMOND == eventDetail.getEventCategory())
                diamondNote.setVisible(true);
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
        col_remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        eventTable.setRowFactory(tv -> {
            TableRow<EventDetailsViewObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    EventDetailsViewObject rowData = row.getItem();
                    if (rowData != null) {
                        tabPane.getSelectionModel().select(panelTabAddEvent);
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
                String bookingIdValue = this.receiptNumber.getText();
                populateDetailsForUpdate(bookingIdValue);
            }
        };
    }

    private void populateDetailsForUpdate(String receiptNumber) {
        if (receiptNumber != null && !"".equals(receiptNumber)) {
            Booking booking = EventRepository.getUniqueInstance().fetchByReceiptNumber(receiptNumber);
            if (booking != null) {
                clearFields();
                makeFieldsEditable(true);
                initializeActionComboBoxForUpdate(booking.getBookingStatus());
                this.receiptNumber.setText(receiptNumber);
                bookingId.setText(booking.getBookingId());
                firstName.setText(booking.getFirstName());
                lastName.setText(booking.getLastName());
                addressLine1.setText(booking.getAddressLine1());
                addressLine2.setText(booking.getAddressLine2());
                addressLine3.setText(booking.getAddressLine3());
                district.setText(booking.getDistrict());
                state.setText(booking.getState());
                pinCode.setText(booking.getPinCode());
                primaryMobileNumber.setText(booking.getPrimaryMobile());
                alternateMobileNumber.setText(booking.getAlternateMobile());
                eventDate.setValue(booking.getEventDate().toLocalDate());
                lblEventCategory.setText(booking.getEventCategory().toString());
                bookingStatus.setText(booking.getBookingStatus().toString());
                advanceAmount.setText(String.valueOf(booking.getAdvanceAmount()));
                totalAmount.setText(String.valueOf(booking.getBookingCost()));
                eventDate.setDisable(true);
                eventDate.setStyle("-fx-opacity: 1");
                eventDate.getEditor().setStyle("-fx-opacity: 1");
                this.receiptNumber.setEditable(false);
                totalAmount.setText(String.valueOf(booking.getBookingCost()));
                if(EventCategory.DIAMOND == booking.getEventCategory())
                    diamondNote.setVisible(true);
                blockedBy.setText(new EventBlockingService().getEventBlockedBy(booking.getEvents()));
                remarks.setText(booking.getRemarks());
                if (booking.getEvents() != null && !booking.getEvents().isEmpty()) {
                    for (Event event : booking.getEvents()) {
                        if (null != event.getEventType()) {
                            switch (event.getEventType()) {
                                case WEDDING:
                                    wedding.setSelected(true);
                                    wedding.setUserData(event.getEventId());
                                    break;
                                case MEHANDI:
                                    mehandi.setSelected(true);
                                    mehandi.setUserData(event.getEventId());
                                    break;
                                case RECEPTION_3_PM:
                                    reception.setSelected(true);
                                    receptionComboBox.setValue(new ComboBoxViewObject(EventType.RECEPTION_3_PM.toString(), EventType.RECEPTION_3_PM));
                                    reception.setUserData(event.getEventId());
                                    break;
                                case RECEPTION_5_PM:
                                    reception.setSelected(true);
                                    receptionComboBox.setValue(new ComboBoxViewObject(EventType.RECEPTION_5_PM.toString(), EventType.RECEPTION_5_PM));
                                    reception.setUserData(event.getEventId());
                                    break;
                                case ISHA_HALL_AC_DAY:
                                    ishaHall.setSelected(true);
                                    ishaHallComboBox.setValue(new ComboBoxViewObject(EventType.ISHA_HALL_AC_DAY.toString(), EventType.ISHA_HALL_AC_DAY));
                                    ishaHall.setUserData(event.getEventId());
                                    break;
                                case ISHA_HALL_AC_EVE:
                                    ishaHall.setSelected(true);
                                    ishaHallComboBox.setValue(new ComboBoxViewObject(EventType.ISHA_HALL_AC_EVE.toString(), EventType.ISHA_HALL_AC_EVE));
                                    ishaHall.setUserData(event.getEventId());
                                    break;
                                case NICA_LONGUE_AC:
                                    niceHall.setSelected(true);
                                    niceHall.setUserData(event.getEventId());
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    if (booking.getEvents().get(0).getAdditionalAcRange() > 0) {
                        additionalAC.setSelected(true);
                        additionalAcComboBox.setValue(String.valueOf(booking.getEvents().get(0).getAdditionalAcRange()));
                    }
                }
            }
        }
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
        ishaHall.setDisable(!flag);
        niceHall.setDisable(!flag);
        additionalAC.setDisable(!flag);
        eventDate.setDisable(!flag);
        eventDate.setStyle("-fx-opacity: 1");
        eventDate.getEditor().setStyle("-fx-opacity: 1");
        receptionComboBox.setDisable(!flag);
        ishaHallComboBox.setDisable(!flag);
        additionalAcComboBox.setDisable(!flag);
        primaryMobileNumber.setEditable(flag);
        alternateMobileNumber.setEditable(flag);
        receiptNumber.setEditable(flag);
        advanceAmount.setEditable(flag);
        remarks.setEditable(flag);
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
        wedding.setUserData(null);
        mehandi.setSelected(false);
        mehandi.setUserData(null);
        reception.setSelected(false);
        reception.setUserData(null);
        ishaHall.setSelected(false);
        ishaHall.setUserData(null);
        niceHall.setSelected(false);
        niceHall.setUserData(null);
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
        bookingId.setText("");
        bookingStatus.setText("");
        diamondNote.setVisible(false);
        remarks.clear();
    }

    private void cancelEvent() {

        if (validateMandatory()) {
            BookingStatus newBookngStatus = null;
            BookingStatus currentBookingStatus = BookingStatus.fromValue(bookingStatus.getText());
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
                Booking eventDetail = getBookingDetails(newBookngStatus);
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
            if (wedding.getUserData() != null) {
                event.setEventId((Integer) wedding.getUserData());
            }
            eventDetails.add(event);
        }
        if (mehandi.isSelected()) {
            Event event = new Event();
            event.setEventType(EventType.MEHANDI);
            if (bookingDetails.getEventDate() != null) {
                event.setEventDate(DateHelper.getPreviousDay(bookingDetails.getEventDate()));
            }
            if (mehandi.getUserData() != null) {
                event.setEventId((Integer) mehandi.getUserData());
            }
            eventDetails.add(event);
        }
        if (reception.isSelected()) {
            Event event = new Event();
            event.setEventType(receptionComboBox.getValue().getValue());
            event.setEventDate(bookingDetails.getEventDate());
            if (reception.getUserData() != null) {
                event.setEventId((Integer) reception.getUserData());
            }
            eventDetails.add(event);
        }
        if (ishaHall.isSelected()) {
            Event event = new Event();
            event.setEventType(ishaHallComboBox.getValue().getValue());
            event.setEventDate(bookingDetails.getEventDate());
            if (ishaHall.getUserData() != null) {
                event.setEventId((Integer) ishaHall.getUserData());
            }
            eventDetails.add(event);
        }
        if (niceHall.isSelected()) {
            Event event = new Event();
            event.setEventType(EventType.NICA_LONGUE_AC);
            event.setEventDate(bookingDetails.getEventDate());
            if (niceHall.getUserData() != null) {
                event.setEventId((Integer) niceHall.getUserData());
            }
            eventDetails.add(event);
        }
        for (Event event : eventDetails) {
            event.setBookingStatus(bookingDetails.getBookingStatus());
            if (additionalAC.isSelected()) {
                event.setAdditionalAcRange(Integer.valueOf(additionalAcComboBox.getValue()));
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

    private void initializeIshHallComboBox() {
        ishaHallComboBox.getItems().clear();
        ishaHallComboBox.getSelectionModel().clearSelection();

        ObservableList<ComboBoxViewObject> ishaHallCombo = FXCollections.observableArrayList();
        ishaHallCombo.addAll(new ComboBoxViewObject(EventType.ISHA_HALL_AC_DAY.toString(), EventType.ISHA_HALL_AC_DAY),
                new ComboBoxViewObject(EventType.ISHA_HALL_AC_EVE.toString(), EventType.ISHA_HALL_AC_EVE));

        ishaHallComboBox.setItems(ishaHallCombo);
        ishaHallComboBox.getSelectionModel().selectFirst();

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
                } else if (booking.getFullName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (booking.getBookingStatus().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (booking.getEventType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (booking.getBookingDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (booking.getEventCategory().toString().toLowerCase().contains(lowerCaseFilter)) {
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
        initActionComboBox();
    }

    private void setNumberFormatter() {
        primaryMobileNumber.setTextFormatter(new NumberFilter().filter());
        alternateMobileNumber.setTextFormatter(new NumberFilter().filter());
        pinCode.setTextFormatter(new NumberFilter().filter());
        advanceAmount.setTextFormatter(new NumberFilter().decimalFilter());
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
    }

    @FXML
    private void getCostIconClick(MouseEvent event) {
        double totalCost = EventCostService.getTotalEventCost(getEventDetails(new Booking()));
        totalAmount.setText(String.valueOf(totalCost));
        lblEventCategory.setText(EventCategoryUtils.getEventCategory(buildEventCategoryDetail()).toString());
    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        if (event != null && validateMandatory()) {
            if (ActionType.BOOK.toString().equals(actionComboBOx.getValue())) {
                if (StringUtils.isNotNullCheckSpace(bookingId.getText())) {
                    bookEventInUpdate();
                } else {
                    showBookingConfirmation(BookingStatus.BOOKED);
                }
            } else if (ActionType.BLOCK.toString().equals(actionComboBOx.getValue())) {
                showBookingConfirmation(BookingStatus.BLOCKED);
            } else if (ActionType.UPDATE.toString().equals(actionComboBOx.getValue())) {
                updateEvent();
            } else if (ActionType.CANCEL_BOOKING.toString().equals(actionComboBOx.getValue())) {
                cancelEvent();
            } else if (ActionType.CANCEL_BLOCKING.toString().equals(actionComboBOx.getValue())) {
                cancelEvent();
            }
        }
    }

    private void initActionComboBox() {
        actionComboBOx.getItems().clear();
        actionComboBOx.getSelectionModel().clearSelection();
        ObservableList<String> actionCombo = FXCollections.observableArrayList();
        actionCombo.addAll(ActionType.BOOK.toString(), ActionType.BLOCK.toString());
        actionComboBOx.setItems(actionCombo);
        actionComboBOx.getSelectionModel().selectFirst();
    }

    private void initializeActionComboBoxForUpdate(BookingStatus bookingStatus) {
        actionComboBOx.getItems().clear();
        actionComboBOx.getSelectionModel().clearSelection();
        ObservableList<String> actionCombo = FXCollections.observableArrayList();
        actionCombo.add(ActionType.UPDATE.toString());
        if (BookingStatus.BOOKED == bookingStatus) {
            actionCombo.add(ActionType.CANCEL_BOOKING.toString());
        } else if (BookingStatus.BLOCKED == bookingStatus) {
            actionCombo.addAll(ActionType.BOOK.toString(), ActionType.CANCEL_BLOCKING.toString());
        }
        actionComboBOx.setItems(actionCombo);
        actionComboBOx.getSelectionModel().selectFirst();
    }
}
