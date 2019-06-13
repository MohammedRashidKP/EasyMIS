package easymis.views.viewobjects;

import easymis.models.entity.enumeration.BookingStatus;
import easymis.models.entity.enumeration.EventCategory;

/**
 *
 * @author RashidKP
 */
public class EventDetailsViewObject {
    
    private String eventDate;
    
    private String fullName;
    
    private BookingStatus bookingStatus;
    
    private String eventType;
    
    private String bookingId;
    
    private String bookingDate;
    
    private EventCategory eventCategory;

    public EventDetailsViewObject(String eventDate, String fullName, BookingStatus bookingStatus, String eventType, String bookingId, String bookingDate, EventCategory eventCategory) {
        this.eventDate = eventDate;
        this.fullName = fullName;
        this.bookingStatus = bookingStatus;
        this.eventType = eventType;
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.eventCategory = eventCategory;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getFullName() {
        return fullName;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public String getEventType() {
        return eventType;
    }

    public String getBookingId() {
        return  bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }
    
    
}
