package easymis.views.viewobjects;

/**
 *
 * @author RashidKP
 */
public class UpcomingEventsViewObject {
    
    private String eventDate;
    
    private String category;
    
    private String events;
    
    private String receiptNumber;
    
    private String mobileNumber;
    
    private String remarks;

    public UpcomingEventsViewObject(String eventDate, 
            String category, 
            String events, 
            String receiptNumber, 
            String mobileNumber, 
            String remarks) {
        this.eventDate = eventDate;
        this.category = category;
        this.events = events;
        this.receiptNumber = receiptNumber;
        this.mobileNumber = mobileNumber;
        this.remarks = remarks;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    
}
