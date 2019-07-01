package easymis.controllers.assembler;

import easymis.models.entity.Booking;
import easymis.models.entity.enumeration.EventType;
import easymis.utils.DateHelper;
import easymis.views.viewobjects.EventDetailsViewObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class EventDetailsAssembler {

    public EventDetailsViewObject toEventDetailsViewObject(Booking eventDetails) {
        StringBuilder fullName = new StringBuilder();
        if (eventDetails.getFirstName() != null) {
            fullName.append(eventDetails.getFirstName());
        }
        if (eventDetails.getLastName() != null) {
            fullName.append(" ").append(eventDetails.getLastName());
        }
        String eventType = resolveEventType(eventDetails);
        EventDetailsViewObject eventDetailsViewObject = new EventDetailsViewObject(DateHelper.format(eventDetails.getEventDate()) , 
                fullName.toString(), 
                eventDetails.getBookingStatus(), 
                eventType, 
                DateHelper.format(eventDetails.getCreatedDate()), 
                eventDetails.getEventCategory(),
                eventDetails.getReceiptNumber());
        return eventDetailsViewObject;
    }

    private String resolveEventType(Booking eventDetails) {
        String eventType = "";
        List<EventType> eventTypes = getEventTypeEnums(eventDetails);
        StringBuilder eventTypeBuilder = new StringBuilder();
        if(!eventTypes.isEmpty()){
            for(int i=0; i<eventTypes.size(); i++){
                eventTypeBuilder.append(eventTypes.get(i));
                if(i != eventTypes.size()-1){
                    eventTypeBuilder.append(", ");
                }
            }
            eventType = eventTypeBuilder.toString();
        }
        return eventType;
        
    }

    public List<EventType> getEventTypeEnums(Booking booking) {
        List<EventType> eventTypes = new ArrayList<>();
        
        booking.getEvents().stream().forEach((event) -> {
            eventTypes.add(event.getEventType());
        });
        return eventTypes;
    }
   
}
