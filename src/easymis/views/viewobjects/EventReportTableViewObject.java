package easymis.views.viewobjects;

/**
 *
 * @author RashidKP
 */
public class EventReportTableViewObject {
    
    private String receiptNumber;
    
    private String eventDate;
    
    private String fullName;
    
    private String bookingStatus;
    
    private String events;
    
    private String bookingDate;
    
    private String eventCategory;

    public EventReportTableViewObject(String receiptNumber, String eventDate, String fullName, String bookingStatus, String events, String bookingDate, String eventCategory) {
        this.receiptNumber = receiptNumber;
        this.eventDate = eventDate;
        this.fullName = fullName;
        this.bookingStatus = bookingStatus;
        this.events = events;
        this.bookingDate = bookingDate;
        this.eventCategory = eventCategory;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getEvents() {
        return events;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getEventCategory() {
        return eventCategory;
    }
    
    
}
