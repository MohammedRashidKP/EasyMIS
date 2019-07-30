package easymis.models.utils;
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
    
    public static final String FETCH_BLOCKED_EVENT_FOR_DATE = "SELECT e from Event e "
            + "where e.eventDate = :eventDate and e.bookingStatus = "
            + "easymis.models.entity.enumeration.BookingStatus.BLOCKED";
    
    public static final String FETCH_IS_MEHANDI_ON_DATE = "SELECT e from Event e "
            + "where e.eventDate = :eventDate and e.eventType = easymis.models.entity.enumeration.EventType.MEHANDI and e.bookingStatus = "
            + "easymis.models.entity.enumeration.BookingStatus.BOOKED";
    
    public static final String FETCH_SETTINGS_FOR_ATTRIBUTES = "SELECT s from Settings s "
            + "where s.attribute = :attributes";
    
    public static final String FETCH_MULTIPLE_SETTINGS_FOR_ATTRIBUTES = "SELECT s from Settings s "
            + "where s.attribute in :attributes";
    
    public static final String FETCH_ALL_SETTINGS = "SELECT s from Settings s";
    
    public static final String FETCH_COMPLETED_EVENTS = "SELECT e from Booking e "
            + "where e.eventDate <= :eventDate and e.bookingStatus = "
            + "easymis.models.entity.enumeration.BookingStatus.BOOKED";
    
    public static final String FETCH_UPCOMING_EVENTS = "SELECT e from Booking e "
            + "where e.eventDate >= :eventDate and e.bookingStatus = "
            + "easymis.models.entity.enumeration.BookingStatus.BOOKED";
    
    public static final String FETCH_EXPENSES_FOR_RECEIPT_NUMBER = "SELECT e from Expenses e where e.receiptNumber = :receiptNumber";
    
    public static final String FETCH_ALL_INVENTORY = "SELECT i from Inventory i";
    
    public static final String FETCH_ALL_EMPLOYEE = "SELECT e from Employee e";
    
    public static final String FETCH_EMPLOYEE_FOR_ID = "SELECT e from Employee e where e.employeeId = :employeeId";
    
    public static final String FETCH_ALL_PAYROLL = "SELECT p from Payroll p";
    
    public static final String FETCH_PAYROLL_FOR_ID = "SELECT p from Payroll p where p.id = :id";
        
    public static final String FETCH_PAYROLL_FOR_MONTH_YEAR = "SELECT p from Payroll p where p.month = :month and p.year = :year and p.employeeId = :employeeId";
    
    public static final String FETCH_BOOKINGS_BETWEEN_DATES = "SELECT b from Booking b where b.eventDate BETWEEN :startDate AND :endDate";
    
    public static final String FETCH_PAYROLL_BETWEEN_DATES = "SELECT p from Payroll p where p.year BETWEEN :fromYear AND :toYear";
}