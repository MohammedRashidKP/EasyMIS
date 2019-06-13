package easymis.models.entity.enumeration;

/**
 *
 * @author RashidKP
 */
public enum BookingStatus {
    BOOKED("Booked"),
    BOOKING_CANCELLED("Booking Cancelled"), 
    BLOCKED("Blocked"),
    BLOCKING_CANCELLED("Blocking Cancelled");
    
    private String value;
    BookingStatus(final String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
    
    @Override
    public String toString(){
        return this.getValue();
    }
    
      public static BookingStatus fromValue(final String bookingStatusString) {

        for (BookingStatus status : BookingStatus.values()) {
            if (bookingStatusString.equals(status.toString())) {
                return status;
            }
        }
        return null;
    }
}
