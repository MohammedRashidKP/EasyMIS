package easymis.models.entity;

import easymis.models.entity.enumeration.BookingStatus;
import easymis.models.entity.enumeration.EventCategory;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "EMIS_BOOKING_DETAILS_B")
public class Booking extends DomainObject {

    @Id
    @GeneratedValue(generator = "sqlite")
    @TableGenerator(name = "sqlite", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "BOOKING_ID", initialValue = 1000, allocationSize = 1)
    @Column(name = "BOOKING_ID")
    private String bookingId;
    
    @Column(name = "RECEIPT_NUMBER")
    private String receiptNumber;
    
    @Column(name = "ADVANCE_AMOUNT")
    private Integer advanceAmount;

    @OneToMany(cascade=ALL, mappedBy = "bookingDetails")
    private List<Event> events;

    @Column(name = "EVENT_DATE")
    private Date eventDate;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;

    @Column(name = "ADDRESS_LINE_3")
    private String addressLine3;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "STATE")
    private String state;

    @Column(name = "PIN")
    private String pinCode;

    @ObjectTypeConverter(
            name = "eventCategoryConverter", objectType = EventCategory.class, dataType = String.class, conversionValues = {
                @ConversionValue(objectValue = "DIAMOND", dataValue = "DIAMOND"),
                @ConversionValue(objectValue = "GOLD", dataValue = "GOLD"),
                @ConversionValue(objectValue = "SILVER", dataValue = "SILVER")}
    )
    @Convert("eventCategoryConverter")
    @Column(name = "EVENT_CATEGORY")
    private EventCategory eventCategory;

    @Column(name = "PRIMARY_MOBILE")
    private String primaryMobile;

    @Column(name = "ALTERNATE_MOBILE")
    private String alternateMobile;

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

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date lastUpdatedDate;
    
    public Booking(){
        super();
        this.events = new ArrayList<>();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getPrimaryMobile() {
        return primaryMobile;
    }

    public void setPrimaryMobile(String primaryMobile) {
        this.primaryMobile = primaryMobile;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Integer getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(Integer advanceAmount) {
        this.advanceAmount = advanceAmount;
    }
}
