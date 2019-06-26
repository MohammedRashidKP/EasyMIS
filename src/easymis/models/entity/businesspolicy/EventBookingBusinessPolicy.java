package easymis.models.entity.businesspolicy;

import easymis.models.entity.Booking;
import easymis.utils.DateHelper;
import easymis.utils.ValidationError;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class EventBookingBusinessPolicy {

    List<ValidationError> validationErrors = new ArrayList<>();

    public List<ValidationError> validateBooking(Booking eventDetails) {

        validateIfEventDateIsInPast(eventDetails.getEventDate());
        validateDateAvailability(eventDetails);

        return validationErrors;
    }

    private void validateIfEventDateIsInPast(Date eventDate) {
        Date today = DateHelper.getToday();
        if (!eventDate.after(today)) {
            validationErrors.add(new ValidationError("501", "Event Date should be after today"));
        }
    }

    private void validateDateAvailability(Booking booking) {
        
    }
}
