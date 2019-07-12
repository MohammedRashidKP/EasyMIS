package easymis.views.viewobjects;

import easymis.models.entity.enumeration.EventType;
import easymis.utils.DateHelper;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class UpcomingEventNotifier {
    
    private String receiptNumber;
    
    private String mobileNumber;
    
    private String eventCategory;
    
    private Date eventDate;
    
    private List<EventType> events;

    public UpcomingEventNotifier(String receiptNumber, String mobileNumber, String eventCategory, Date eventDate, List<EventType> events) {
        this.receiptNumber = receiptNumber;
        this.mobileNumber = mobileNumber;
        this.eventCategory = eventCategory;
        this.eventDate = eventDate;
        this.events = events;
    }
    
    public String prettify(){
        String notification = getEventDateString()+" -- "+eventCategory+" -- "+getEvents()+" -- "+receiptNumber+" -- "+mobileNumber;
        return notification;
    }

    private String getEventDateString() {
        if(eventDate.equals(DateHelper.getToday())){
            return "Today";
        }else if(eventDate.equals(DateHelper.getNextDay(DateHelper.getToday()))){
            return "Tomorrow";
        }else{
            return DateHelper.format(eventDate);
        }
    }
    
    private String getEvents(){
        
        
        if(!events.isEmpty()){
            StringBuilder builder = new StringBuilder();
            events.stream().forEach(e -> builder.append(e.toString()).append(", "));
            String eventString = builder.toString();
            return eventString.substring(0, eventString.length()-2);
        }
        
        return "";
    }
}
