package easymis.models.entity;

import easymis.models.entity.enumeration.BookingStatus;
import easymis.models.entity.enumeration.EventType;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.ObjectTypeConverter;

/**
 *
 * @author RashidKP
 */
@Entity
@Table(name = "EMIS_EVENT_DETAILS_B")
public class Event extends DomainObject {

    @Id
    @GeneratedValue(generator = "events")
    @TableGenerator(name = "events", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "EVENT_ID", initialValue = 1000, allocationSize = 1)
    @Column(name = "EVENT_ID")
    private Integer eventId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BOOKING_ID")
    private Booking bookingDetails;
    
    @Column(name = "EVENT_DATE")
    private Date eventDate;

    @ObjectTypeConverter(
            name = "eventTypeConverter", objectType = EventType.class, dataType = String.class, conversionValues = {
                @ConversionValue(objectValue = "WEDDING", dataValue = "WEDDING"),
                @ConversionValue(objectValue = "MEHANDI", dataValue = "MEHANDI"),
                @ConversionValue(objectValue = "RECEPTION_3_PM", dataValue = "RECEPTION_3_PM"),
                @ConversionValue(objectValue = "RECEPTION_5_PM", dataValue = "RECEPTION_5_PM"),
                @ConversionValue(objectValue = "ISHA_HALL_AC_DAY", dataValue = "ISHA_HALL_AC_DAY"),
                @ConversionValue(objectValue = "ISHA_HALL_AC_EVE", dataValue = "ISHA_HALL_AC_EVE"),
                @ConversionValue(objectValue = "NICA_LONGUE_AC", dataValue = "NICA_LONGUE_AC")}
    )
    @Convert("eventTypeConverter")
    @Column(name = "EVENT_TYPE")
    private EventType eventType;

    @Column(name = "NORMAL_AC")
    private boolean normalAcRequired;

    @Column(name = "ADDITIONAL_AC")
    private int additionalAcRange;

    @Column(name = "EVENT_ADDONS")
    private String eventAddOns;
    
     @ObjectTypeConverter(
            name = "bookingStatusConverter", objectType = BookingStatus.class, dataType = String.class, conversionValues = {
                @ConversionValue(objectValue = "BOOKED", dataValue = "BOOKED"),
                @ConversionValue(objectValue = "BLOCKED", dataValue = "BLOCKED"),
                @ConversionValue(objectValue = "BOOKING_CANCELLED", dataValue = "BOOKING_CANCELLED"),
                @ConversionValue(objectValue = "BLOCKING_CANCELLED", dataValue = "BLOCKING_CANCELLED")}
    )
    @Convert("bookingStatusConverter")
    @Column(name = "BOOKING_STATUS")
    private BookingStatus bookingStatus;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Booking getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(Booking bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public boolean isNormalAcRequired() {
        return normalAcRequired;
    }

    public void setNormalAcRequired(boolean normalAcRequired) {
        this.normalAcRequired = normalAcRequired;
    }

    public int getAdditionalAcRange() {
        return additionalAcRange;
    }

    public void setAdditionalAcRange(int additionalAcRange) {
        this.additionalAcRange = additionalAcRange;
    }

    public String getEventAddOns() {
        return eventAddOns;
    }

    public void setEventAddOns(String eventAddOns) {
        this.eventAddOns = eventAddOns;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
