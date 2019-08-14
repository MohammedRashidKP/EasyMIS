package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import easymis.models.entity.Booking;
import easymis.models.entity.Event;
import easymis.models.entity.enumeration.EventCategory;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.DashboardRepository;
import easymis.models.repository.EventRepository;
import easymis.models.service.EventAvailabilityService;
import easymis.utils.DateHelper;
import easymis.utils.TooltippedTableCell;
import easymis.views.dto.EventAvailabilityDTO;
import easymis.views.viewobjects.EventAvailability;
import easymis.views.viewobjects.UpcomingEventsViewObject;
import java.net.URL;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author RashidKP
 */
public class DashboardController implements Initializable {

    @FXML
    private Text totalCancellationThisYear;
    @FXML
    private JFXDatePicker eventDateField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private Text weddingAvailability;
    @FXML
    private Text mehandiAvailability;
    @FXML
    private Text nicaLoungeAvailability;
    @FXML
    private Text totalDiamonBookingThisYear;
    @FXML
    private Text totalBookingThisYear;
    @FXML
    private Text totalBlockingThisYear;
    @FXML
    private Text reception5availability;
    @FXML
    private Text ishaDayAvailability;
    @FXML
    private Text reception3Availability;
    @FXML
    private Text ishaEveAvailability;

    private final int MAX_COUNT = 7;

    ObservableList<UpcomingEventsViewObject> eventsObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<UpcomingEventsViewObject> upcomingEventsTable;
    @FXML
    private TableColumn<UpcomingEventsViewObject, String> col_eventDate;
    @FXML
    private TableColumn<UpcomingEventsViewObject, String> col_category;
    @FXML
    private TableColumn<UpcomingEventsViewObject, String> col_events;
    @FXML
    private TableColumn<UpcomingEventsViewObject, String> col_receiptNumber;
    @FXML
    private TableColumn<UpcomingEventsViewObject, String> col_mobileNumber;
    @FXML
    private TableColumn<UpcomingEventsViewObject, String> col_remarks;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeUpcomingBookingsTable();
        populateDashboardData();
        populateUpcomingBookings();

    }

    @FXML
    private void onClickOfSearch(ActionEvent actionEvent) {

        if (eventDateField.getValue() != null) {
            java.sql.Date searchDate = java.sql.Date.valueOf(eventDateField.getValue());
            enrichEventAvailability();
            Set<EventAvailabilityDTO> events = EventAvailabilityService.checkEventAvailability(searchDate, null);
            populateDateAvailabilitySearchResult(events, searchDate);
        }
    }

    private void populateDashboardData() {

        List<Booking> allEvents = DashboardRepository.getUniqueInstance().fetchAllEventOfCurrentYear();
        if (allEvents != null && !allEvents.isEmpty()) {
            int totalBooking = 0;

            int totalBlocking = 0;

            int totalCancellation = 0;

            int totalDiamonBooking = 0;

            for (Booking event : allEvents) {
                if (null != event.getBookingStatus()) {
                    switch (event.getBookingStatus()) {
                        case BOOKED:
                            totalBooking++;
                            break;
                        case BLOCKED:
                            totalBlocking++;
                            break;
                        case BOOKING_CANCELLED:
                            totalCancellation++;
                            break;
                        default:
                            break;
                    }
                }
                if (EventCategory.DIAMOND.equals(event.getEventCategory())) {
                    totalDiamonBooking++;
                    if (daysBetween(event.getEventDate(), DateHelper.getToday()) <= 10) {

                    }
                }
            }
            totalBookingThisYear.setText(String.valueOf(totalBooking));
            totalBlockingThisYear.setText(String.valueOf(totalBlocking));
            totalCancellationThisYear.setText(String.valueOf(totalCancellation));
            totalDiamonBookingThisYear.setText(String.valueOf(totalDiamonBooking));
        }
    }

    private static long daysBetween(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }

    private void populateDateAvailabilitySearchResult(Set<EventAvailabilityDTO> eventAvailabilityDTOs, Date searchDate) {
        if (eventAvailabilityDTOs != null && !eventAvailabilityDTOs.isEmpty()) {
            for (EventAvailabilityDTO eventAvailabilityDTO : eventAvailabilityDTOs) {
                if (searchDate.equals(eventAvailabilityDTO.getEventDate())) {
                    switch (eventAvailabilityDTO.getEventType()) {
                        case WEDDING:
                            weddingAvailability.setText(eventAvailabilityDTO.getEventAvailability().toString());
                            break;
                        case MEHANDI:
                            mehandiAvailability.setText(eventAvailabilityDTO.getEventAvailability().toString());
                            break;
                        case RECEPTION_3_PM:
                            reception3Availability.setText(eventAvailabilityDTO.getEventAvailability().toString());
                            break;
                        case RECEPTION_5_PM:
                            reception5availability.setText(eventAvailabilityDTO.getEventAvailability().toString());
                            break;
                        case ISHA_HALL_AC_DAY:
                            ishaDayAvailability.setText(eventAvailabilityDTO.getEventAvailability().toString());
                            break;
                        case ISHA_HALL_AC_EVE:
                            ishaEveAvailability.setText(eventAvailabilityDTO.getEventAvailability().toString());
                            break;
                        case NICA_LONGUE_AC:
                            nicaLoungeAvailability.setText(eventAvailabilityDTO.getEventAvailability().toString());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void enrichEventAvailability() {
        weddingAvailability.setText(EventAvailability.AVAILABLE.toString());
        mehandiAvailability.setText(EventAvailability.AVAILABLE.toString());
        reception3Availability.setText(EventAvailability.AVAILABLE.toString());
        reception5availability.setText(EventAvailability.AVAILABLE.toString());
        ishaDayAvailability.setText(EventAvailability.AVAILABLE.toString());
        ishaEveAvailability.setText(EventAvailability.AVAILABLE.toString());
        nicaLoungeAvailability.setText(EventAvailability.AVAILABLE.toString());
    }

    private void populateUpcomingBookings() {

        List<Booking> bookings = EventRepository.getUniqueInstance().fetchUpcomingEvents();
        if (bookings != null && !bookings.isEmpty()) {
            Collections.sort(bookings, new Comparator<Booking>() {
                @Override
                public int compare(Booking o1, Booking o2) {
                    return o1.getEventDate().compareTo(o2.getEventDate());
                }
            });
            eventsObservableList.clear();
            bookings.stream().limit(MAX_COUNT).forEach((booking) -> {
                eventsObservableList.add(buildBookingViewObject(booking));
            });
            upcomingEventsTable.setItems(eventsObservableList);
        }
    }

    private UpcomingEventsViewObject buildBookingViewObject(Booking booking) {
        UpcomingEventsViewObject upcomingEventsViewObject = new UpcomingEventsViewObject(
                getEventDateString(booking.getEventDate()),
                booking.getEventCategory().toString(),
                getEvents(booking.getEvents()),
                booking.getReceiptNumber(),
                booking.getPrimaryMobile(), 
                booking.getRemarks());
        return upcomingEventsViewObject;
    }

    private String getEventDateString(Date eventDate) {
        if (eventDate.equals(DateHelper.getToday())) {
            return "Today";
        } else if (eventDate.equals(DateHelper.getNextDay(DateHelper.getToday()))) {
            return "Tomorrow";
        } else {
            return DateHelper.format(eventDate);
        }
    }

    private String getEvents(List<Event> events) {

        List<EventType> eventTypes = events.stream().map(e -> e.getEventType()).collect(Collectors.toList());
        if (!eventTypes.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            eventTypes.stream().forEach(e -> builder.append(e.toString()).append(", "));
            if (events.get(0).getAdditionalAcRange() > 0) {
                builder.append("A/C - ")
                        .append(events.get(0).getAdditionalAcRange())
                        .append(" Hrs");
            }
            String eventString = builder.toString();
            return eventString.substring(0, eventString.length());
        }
        return "";
    }

    private void initializeUpcomingBookingsTable() {
        col_eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_events.setCellValueFactory(new PropertyValueFactory<>("events"));   
        col_mobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        col_receiptNumber.setCellValueFactory(new PropertyValueFactory<>("receiptNumber"));
        col_remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        
        col_events.setCellFactory(TooltippedTableCell.forTableColumn());
        col_eventDate.setCellFactory(TooltippedTableCell.forTableColumn());
        col_category.setCellFactory(TooltippedTableCell.forTableColumn());
        col_events.setCellFactory(TooltippedTableCell.forTableColumn());
        col_mobileNumber.setCellFactory(TooltippedTableCell.forTableColumn());
        col_receiptNumber.setCellFactory(TooltippedTableCell.forTableColumn());
        col_remarks.setCellFactory(TooltippedTableCell.forTableColumn());
    }
}
