package easymis.models.businesspolicy;

import easymis.models.entity.Booking;
import easymis.models.entity.Event;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.EventRepository;
import easymis.models.service.EventAvailabilityService;
import easymis.utils.DateHelper;
import easymis.utils.ValidationError;
import easymis.views.dto.EventAvailabilityDTO;
import easymis.views.viewobjects.EventAvailability;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author RashidKP
 */
public class EventBookingBusinessPolicy {
    
    List<ValidationError> validationErrors = new ArrayList<>();
    
    public List<ValidationError> validateBooking(Booking eventDetails) {
        
        validateIfEventDateIsInPast(eventDetails.getEventDate());
        validateDateAvailability(eventDetails);
        validateSameBookingConstraints(eventDetails);
        if (eventDetails.getBookingId() == null) {
            validateReceiptNumber(eventDetails.getReceiptNumber());
        }
        return validationErrors;
    }
    
    private void validateIfEventDateIsInPast(Date eventDate) {
        Date today = DateHelper.getToday();
        if (!eventDate.after(today)) {
            validationErrors.add(new ValidationError("501", "Event Date should be after today"));
        }
    }
    
    private void validateDateAvailability(Booking booking) {
        Set<EventAvailabilityDTO> eventAvailability = EventAvailabilityService.
                checkEventAvailability(booking.getEventDate(), booking.getBookingId());
        if (!eventAvailability.isEmpty()) {
            for (EventAvailabilityDTO eventAvailabilityDTO : eventAvailability) {
                for (Event event : booking.getEvents()) {
                    if (eventAvailabilityDTO.getEventType() == event.getEventType()
                            && EventAvailability.NOT_AVAILABLE == eventAvailabilityDTO.getEventAvailability() 
                            && eventAvailabilityDTO.getEventDate().equals(event.getEventDate())) {
                        validationErrors.add(new ValidationError("502", eventAvailabilityDTO.getUnavailabilityReason()));
                    }
                }
            }
        }
    }
    
    private void validateReceiptNumber(String receiptNumber) {
        Booking booking = EventRepository.getUniqueInstance().fetchByReceiptNumber(receiptNumber);
        if (booking != null) {
            validationErrors.add(new ValidationError("100", "Receipt Number already exists"));
        }
    }

    private void validateSameBookingConstraints(Booking eventDetails) {
        
        List<EventType> events = eventDetails.getEvents().stream().map(e -> e.getEventType()).collect(Collectors.toList());
        if(events.contains(EventType.WEDDING) && events.contains(EventType.RECEPTION_3_PM)){
            validationErrors.add(new ValidationError("200", "3 PM Reception cannot be be booked with Wedding"));
        }
        if(events.contains(EventType.ISHA_HALL_AC_DAY) && events.contains(EventType.RECEPTION_3_PM)){
            validationErrors.add(new ValidationError("201", "3 PM Reception cannot be be booked with Isha Hall Day"));
        }
    }
}
