package easymis.controllers.assembler;

import easymis.models.entity.Booking;
import easymis.models.entity.Expenses;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.ExpensesRepository;
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
    
    public EventDetailsViewObject toEventExpenseDetailsViewObject(Booking booking){
        StringBuilder fullName = new StringBuilder();
        if (booking.getFirstName() != null) {
            fullName.append(booking.getFirstName());
        }
        if (booking.getLastName() != null) {
            fullName.append(" ").append(booking.getLastName());
        }
        String eventType = resolveEventType(booking);
        EventDetailsViewObject eventDetailsViewObject = new EventDetailsViewObject(DateHelper.format(booking.getEventDate()) , 
                fullName.toString(), 
                booking.getBookingStatus(), 
                eventType, 
                DateHelper.format(booking.getCreatedDate()), 
                booking.getEventCategory(),
                booking.getReceiptNumber(),
                getSettlementStatus(booking.getReceiptNumber()));
        return eventDetailsViewObject;
    }

    private String resolveEventType(Booking eventDetails) {
        String eventType = "";
        List<EventType> eventTypes = getEventTypeEnums(eventDetails);
        if(eventDetails.getEvents().get(0).isNormalAcRequired()){
            eventTypes.add(EventType.NORMAL_AC);
        }
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

    private String getSettlementStatus(String receiptNumber) {
        Expenses expenses = ExpensesRepository.getUniqueInstance().
                fetchExpensesForReceiptNumber(receiptNumber);
        return expenses != null ? "CLOSED": "OPEN";
    }
   
}
