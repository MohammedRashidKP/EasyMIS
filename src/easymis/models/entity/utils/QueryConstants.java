package easymis.models.entity.utils;
/**
 *
 * @author RashidKP
 */
public class QueryConstants {
    public static final String FETCH_ALL_BOOKINGS = "SELECT e FROM Booking e"; 
    
    public static final String FETCH_BOOKING_FOR_DATE = "SELECT e from Booking e "
            + "where e.eventDate = :eventDate and e.bookingStatus = "
            + "easymis.models.entity.enumeration.BookingStatus.BOOKED";

    public static final String FETCH_BOOKING_FOR_BOOKING_ID = "SELECT e from Booking e "
            + "where e.bookingId = :bookingId";
    
    public static final String FETCH_BOOKING_FOR_RECEIPT_NUMBER = "SELECT e from Booking e "
            + "where e.receiptNumber = :receiptNumber";
    
    public static final String FETCH_ALL_BOOKINGS_OF_CURRENT_YEAR = "SELECT e from Booking e "
            + "where e.eventDate >= :fistDateOfYear";
    
    public static final String FETCH_EVENT_FOR_DATE = "SELECT e from Event e "
            + "where e.eventDate = :eventDate and e.bookingStatus = "
            + "easymis.models.entity.enumeration.BookingStatus.BOOKED";
    
    public static final String FETCH_IS_MEHANDI_ON_DATE = "SELECT e from Event e "
            + "where e.eventDate = :eventDate and e.eventType = easymis.models.entity.enumeration.EventType.MEHANDI and e.bookingStatus = "
            + "easymis.models.entity.enumeration.BookingStatus.BOOKED";
    
    public static final String FETCH_SETTINGS_FOR_ATTRIBUTES = "SELECT s from Settings s "
            + "where s.attribute = :attributes";
    
    public static final String FETCH_MULTIPLE_SETTINGS_FOR_ATTRIBUTES = "SELECT s from Settings s "
            + "where s.attribute in :attributes";
    
    public static final String FETCH_ALL_SETTINGS = "SELECT s from Settings s";
}