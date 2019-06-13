package easymis.controllers.assembler;

import easymis.models.entity.EventDetails;
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

    public EventDetailsViewObject toEventDetailsViewObject(EventDetails eventDetails) {
        StringBuilder fullName = new StringBuilder();
        if (eventDetails.getFirstName() != null) {
            fullName.append(eventDetails.getFirstName());
        }
        if (eventDetails.getLastName() != null) {
            fullName.append(" ").append(eventDetails.getLastName());
        }
        String eventType = resolveEventType(eventDetails);
        EventDetailsViewObject eventDetailsViewObject = new EventDetailsViewObject(DateHelper.format(eventDetails.getEventDate()) , fullName.toString(), eventDetails.getBookingStatus(), 
                eventType, eventDetails.getBookingId(), DateHelper.format(eventDetails.getCreatedDate()), eventDetails.getEventCategory());
        return eventDetailsViewObject;
    }

    private String resolveEventType(EventDetails eventDetails) {
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

    public List<EventType> getEventTypeEnums(EventDetails eventDetails) {
        List<EventType> eventTypes = new ArrayList<>();
        if(eventDetails.isWeddingSelected()){
            eventTypes.add(EventType.WEDDING);
        }
        if(eventDetails.isMehandiSelected()){
            eventTypes.add(EventType.MEHANDI);
        }
        if(eventDetails.isReceptionSelected()){
            eventTypes.add(EventType.RECEPTION);
        }
        if(eventDetails.isIshaSelected()){
            eventTypes.add(EventType.ISHA_HALL_AC);
        }
        if(eventDetails.isNicaSelected()){
            eventTypes.add(EventType.NICA_LONGUE_AC);
        }
        if(eventDetails.isAcSelected()){
            eventTypes.add(EventType.NORMAL_AC);
        }
        if(eventDetails.isAdditionalACSelected()){
            eventTypes.add(EventType.ADDITIONAL_AC);
        }
        return eventTypes;
    }
   
}
