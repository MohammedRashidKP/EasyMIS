package easymis.models.repository;

import easymis.models.entity.Booking;
import easymis.models.entity.Event;
import easymis.models.entity.TransactionStatus;
import easymis.models.entity.businesspolicy.EventBookingBusinessPolicy;
import easymis.models.entity.enumeration.EventType;
import easymis.models.entity.utils.QueryConstants;
import easymis.utils.DateHelper;
import easymis.utils.ValidationError;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RashidKP
 *
 */
public class EventRepository extends AbstractRepository {

    private static EventRepository uniqueInstance = new EventRepository();

    private EventRepository() {
    }

    public static EventRepository getUniqueInstance() {
        return uniqueInstance;
    }

    public TransactionStatus create(Booking eventDetails) {
        TransactionStatus status;
        EventBookingBusinessPolicy policy = new EventBookingBusinessPolicy();
        List<ValidationError> validationErrors = policy.validateBooking(eventDetails);
        if (validationErrors.isEmpty()) {
            try {
                return persist(eventDetails);
            } catch (Exception ex) {
                status = fillTransactionStatus(ex);
            }
        } else {
            status = new TransactionStatus();
            status.setSuccess(false);
            status.setValidationErrors(validationErrors);
            return status;
        }
        return status;
    }

    public List<Booking> fetchAllEvents() {
        return retrieve(QueryConstants.FETCH_ALL_BOOKINGS, null, Booking.class);
    }
    
    public List<Booking> fetchByEventDate(Date eventDate){
        QueryParams param = new QueryParams();
        param.setParamName("eventDate");
        param.setParamDateValue(eventDate);
        return retrieve(QueryConstants.FETCH_BOOKING_FOR_DATE, Collections.singletonList(param), Booking.class);
    }
    
    public List<Event> fetchEventByEventDate(Date eventDate){
        QueryParams param = new QueryParams();
        param.setParamName("eventDate");
        param.setParamDateValue(eventDate);
        return retrieve(QueryConstants.FETCH_EVENT_FOR_DATE, Collections.singletonList(param), Event.class);
    }

    public boolean fetchIfMehandiExistsOnDate(Date eventDate) {
        QueryParams param = new QueryParams();
        param.setParamName("eventDate");
        param.setParamDateValue(eventDate);
        List<Event> mehandiEvents = retrieve(QueryConstants.FETCH_IS_MEHANDI_ON_DATE, Collections.singletonList(param), Event.class);
        
        return mehandiEvents != null ? !mehandiEvents.isEmpty(): false;
    }
    
     public Booking fetchExistingReceptionEventOnPreviousDate(Date eventDate) {
        java.sql.Date nextDate = DateHelper.getPreviousDay(eventDate);
        QueryParams param = new QueryParams();
        param.setParamName("eventDate");
        param.setParamDateValue(nextDate);
        List<Booking> allBookings = retrieve(QueryConstants.FETCH_BOOKING_FOR_DATE, Collections.singletonList(param), Booking.class);
        if(allBookings != null && !allBookings.isEmpty()){
            for(Booking booking : allBookings){
                for(Event event: booking.getEvents()){
                    if(EventType.RECEPTION_3_PM.equals(event.getEventType())){
                        return booking;
                    }
                        
                }
            }
        }
        return null;
    }

    public Booking fetchByBookingId(String bookingId) {
        QueryParams param = new QueryParams();
        param.setParamName("bookingId");
        param.setParamValue(bookingId);
        List<Booking> events = retrieve(QueryConstants.FETCH_BOOKING_FOR_BOOKING_ID, Collections.singletonList(param), Booking.class);
        return events != null && !events.isEmpty() ? events.get(0) : null;
    }

    public TransactionStatus update(Booking bookingDetail, boolean isNewBooking) {
        
        TransactionStatus status;
        if(isNewBooking){
        EventBookingBusinessPolicy policy = new EventBookingBusinessPolicy();
        List<ValidationError> validationErrors = policy.validateBooking(bookingDetail);
        if (validationErrors.isEmpty()) {
                try {
                    return merge(bookingDetail);
                } catch (Exception ex) {
                    status = fillTransactionStatus(ex);
                }
        } else {
            status = new TransactionStatus();
            status.setSuccess(false);
            status.setValidationErrors(validationErrors);
            return status;
        }
        }else{
            try {
                return merge(bookingDetail);
            } catch (Exception ex) {
                status = fillTransactionStatus(ex);
            }
        }
        return status;
    }
    
    private TransactionStatus fillTransactionStatus(Exception exception) {
        TransactionStatus status = new TransactionStatus();
        if (exception != null) {
            status.setSuccess(false);
            status.setMessage(exception.getLocalizedMessage());
        } else {
            status.setSuccess(true);
        }
        return status;
    }

    public Booking fetchByReceiptNumber(String receiptNumber) {
        QueryParams param = new QueryParams();
        param.setParamName("receiptNumber");
        param.setParamValue(receiptNumber);
        List<Booking> events =retrieve(QueryConstants.FETCH_BOOKING_FOR_RECEIPT_NUMBER, Collections.singletonList(param), Booking.class);
        return events != null && !events.isEmpty() ? events.get(0) : null;
    }
}
