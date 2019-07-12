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
import easymis.views.dto.EventAvailabilityDTO;
import easymis.views.viewobjects.EventAvailability;
import easymis.views.viewobjects.UpcomingEventNotifier;
import java.net.URL;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    private final int MAX_COUNT = 10;
    @FXML
    private Label row1;
    @FXML
    private Label row2;
    @FXML
    private Label row3;
    @FXML
    private Label row4;
    @FXML
    private Label row5;
    @FXML
    private Label row6;
    @FXML
    private Label row7;
    @FXML
    private Label row8;
    @FXML
    private Label row9;
    @FXML
    private Label row10;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        StringBuilder notificationBuilder = new StringBuilder();
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
            int i = 1;
            for (Booking booking : bookings) {
                String notification = new UpcomingEventNotifier(booking.getReceiptNumber(),
                        booking.getPrimaryMobile(),
                        booking.getEventCategory().toString(),
                        booking.getEventDate(),
                        getEvents(booking.getEvents())).prettify();
                setNotification(notification, i);
                i++;
            }
        }
    }

    private List<EventType> getEvents(List<Event> events) {
        return events.stream().map(e -> e.getEventType()).collect(Collectors.toList());
    }

    private void setNotification(String notification, int i) {
        switch (i) {
            case 1:
                row1.setText(notification);
                break;
            case 2:
                row2.setText(notification);
                break;
            case 3:
                row3.setText(notification);
                break;
            case 4:
                row4.setText(notification);
                break;
            case 5:
                row5.setText(notification);
                break;
            case 6:
                row6.setText(notification);
                break;
            case 7:
                row7.setText(notification);
                break;
            case 8:
                row8.setText(notification);
                break;
            case 9:
                row9.setText(notification);
                break;
            case 10:
                row10.setText(notification);
                break;
            default:
                break;
        }
    }
}
