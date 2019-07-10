package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import easymis.models.entity.Booking;
import easymis.models.entity.enumeration.EventCategory;
import easymis.models.repository.DashboardRepository;
import easymis.models.service.EventAvailabilityService;
import easymis.utils.DateHelper;
import easymis.views.dto.EventAvailabilityDTO;
import easymis.views.viewobjects.EventAvailability;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private JFXTextArea diamondNotifications;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateDashboardData();
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
}
