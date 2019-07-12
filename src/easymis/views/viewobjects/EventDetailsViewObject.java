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
      
    private String bookingDate;
    
    private EventCategory eventCategory;
    
    private String receiptNumber;
    
    private String settlementStatus;

    public EventDetailsViewObject(String eventDate, 
            String fullName, 
            BookingStatus bookingStatus, 
            String eventType, 
            String bookingDate, 
            EventCategory eventCategory, 
            String receiptNumber) {
        this.eventDate = eventDate;
        this.fullName = fullName;
        this.bookingStatus = bookingStatus;
        this.eventType = eventType;
        this.bookingDate = bookingDate;
        this.eventCategory = eventCategory;
        this.receiptNumber = receiptNumber;
    }

    public EventDetailsViewObject(String eventDate, String fullName, BookingStatus bookingStatus, String eventType, String bookingDate, EventCategory eventCategory, String receiptNumber, String settlementStatus) {
        this.eventDate = eventDate;
        this.fullName = fullName;
        this.bookingStatus = bookingStatus;
        this.eventType = eventType;
        this.bookingDate = bookingDate;
        this.eventCategory = eventCategory;
        this.receiptNumber = receiptNumber;
        this.settlementStatus = settlementStatus;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }
}
