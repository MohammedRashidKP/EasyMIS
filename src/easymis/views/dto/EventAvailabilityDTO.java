package easymis.views.dto;

import easymis.models.entity.enumeration.EventType;
import easymis.views.viewobjects.EventAvailability;
import java.util.Objects;

/**
 *
 * @author RashidKP
 */
public class EventAvailabilityDTO {
    
    private EventType eventType;
    
    private EventAvailability eventAvailability;
    
    private String unavailabilityReason;

    public EventAvailabilityDTO(EventType eventType, EventAvailability eventAvailability, String unavailabilityReason) {
        this.eventType = eventType;
        this.eventAvailability = eventAvailability;
        this.unavailabilityReason = unavailabilityReason;
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
