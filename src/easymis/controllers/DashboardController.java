package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import easymis.controllers.assembler.EventDetailsAssembler;
import easymis.models.entity.Booking;
import easymis.models.entity.enumeration.EventCategory;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.DashboardRepository;
import easymis.utils.DateHelper;
import easymis.views.viewobjects.EventAvailability;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
    private Text ishHallAvailable;
    @FXML
    private Text nicaLoungeAvailability;
    @FXML
    private Text receptionAvailability;
    @FXML
    private Text totalDiamonBookingThisYear;
    @FXML
    private JFXTextArea diamondNotifications;
    @FXML
    private Text totalBookingThisYear;
    @FXML
    private Text totalBlockingThisYear;
    @FXML
    private Text averageBookingMonthly;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateDashboardData();
    }

    @FXML
    private void onClickOfSearch(ActionEvent actionEvent) {

        if(eventDateField.getValue() != null){
            java.sql.Date searchDate = java.sql.Date.valueOf(eventDateField.getValue());
            List<Booking> events = new ArrayList<>();
            //TODO: Check availability
            List<EventType> allEvents = new ArrayList<>();
            if(!events.isEmpty()){
                EventDetailsAssembler assembler = new EventDetailsAssembler();
                for(Booking event: events){
                    allEvents.addAll(assembler.getEventTypeEnums(event));
                }
            }
            populateDateAvailabilitySearchResult(allEvents);
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
            averageBookingMonthly.setText(calculateAverageMonthlyBooking(totalBooking));
        }
    }

    private static long daysBetween(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }

    private String calculateAverageMonthlyBooking(int totalBooking) {
        int averageMonthlyBooking = totalBooking/12;
        if(averageMonthlyBooking == 0)
            averageMonthlyBooking = 1;
        return String.valueOf(averageMonthlyBooking);
    }

    private void populateDateAvailabilitySearchResult(List<EventType> allEvents) {
        if(allEvents.contains(EventType.WEDDING)){
            weddingAvailability.setText(EventAvailability.NOT_AVAILABLE.toString());
        }else{
            weddingAvailability.setText(EventAvailability.AVAILABLE.toString());
        }
        if(allEvents.contains(EventType.MEHANDI)){
           mehandiAvailability.setText(EventAvailability.NOT_AVAILABLE.toString());
        }else{
            mehandiAvailability.setText(EventAvailability.AVAILABLE.toString());
        }
        if(allEvents.contains(EventType.RECEPTION_3_PM)){
           receptionAvailability.setText(EventAvailability.NOT_AVAILABLE.toString());
        }else{
            receptionAvailability.setText(EventAvailability.AVAILABLE.toString());
        }
        if(allEvents.contains(EventType.ISHA_HALL_AC)){
           ishHallAvailable.setText(EventAvailability.NOT_AVAILABLE.toString());
        }else{
            ishHallAvailable.setText(EventAvailability.AVAILABLE.toString());
        }
        if(allEvents.contains(EventType.NICA_LONGUE_AC)){
           nicaLoungeAvailability.setText(EventAvailability.NOT_AVAILABLE.toString());
        }else{
            nicaLoungeAvailability.setText(EventAvailability.AVAILABLE.toString());
        }
    }
}
