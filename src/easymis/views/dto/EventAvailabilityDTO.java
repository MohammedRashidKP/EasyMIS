package easymis.views.dto;

import easymis.models.entity.enumeration.EventType;
import easymis.views.viewobjects.EventAvailability;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author RashidKP
 */
public class EventAvailabilityDTO {
    
    private EventType eventType;
    
    private EventAvailability eventAvailability;
    
    private String unavailabilityReason;
    
    private Date eventDate;

//    public EventAvailabilityDTO(EventType eventType, EventAvailability eventAvailability, String unavailabilityReason) {
//        this.eventType = eventType;
//        this.eventAvailability = eventAvailability;
//        this.unavailabilityReason = unavailabilityReason;
//    }

    public EventAvailabilityDTO(EventType eventType, EventAvailability eventAvailability, String unavailabilityReason, Date eventDate) {
        this.eventType = eventType;
        this.eventAvailability = eventAvailability;
        this.unavailabilityReason = unavailabilityReason;
        this.eventDate = eventDate;
    }

    public EventAvailability getEventAvailability() {
        return eventAvailability;
    }

    public String getUnavailabilityReason() {
        return unavailabilityReason;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setEventAvailability(EventAvailability eventAvailability) {
        this.eventAvailability = eventAvailability;
    }

    public void setUnavailabilityReason(String unavailabilityReason) {
        this.unavailabilityReason = unavailabilityReason;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof EventAvailabilityDTO)) {
            return false;
        }
        EventAvailabilityDTO event = (EventAvailabilityDTO) o;
        return eventType == event.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType);
    }

}
