package easymis.models.entity.utils;
/**
 *
 * @author RashidKP
 */
public class QueryConstants {
    public static final String FETCH_ALL_EVENTS = "SELECT e FROM Booking e"; 
    
    public static final String FETCH_EVENTS_FOR_DATE = "SELECT e from Booking e "
            + "where e.eventDate = :eventDate and e.bookingStatus = "
            + "easymis.models.entity.enumeration.BookingStatus.BOOKED";

    public static final String FETCH_EVENT_FOR_BOOKING_ID = "SELECT e from Booking e "
            + "where e.bookingId = :bookingId";
    
    public static final String FETCH_ALL_EVENTS_OF_CURRENT_YEAR = "SELECT e from Booking e "
            + "where e.eventDate >= :fistDateOfYear";
}