package easymis.models.service;

import easymis.models.entity.Event;
import easymis.models.entity.Settings;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.SettingsRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author RashidKP
 */
public class EventCostService {
    

    public static int getTotalEventCost(List<Event> eventDetails) {
        int totalCost = 0;
        if(eventDetails != null && !eventDetails.isEmpty()){
            
            List<String> events = eventDetails
                    .stream()
                    .map(e -> e.getEventType().toString())
                    .collect(Collectors.toList());
            if(eventDetails.get(0).isNormalAcRequired()){
                events.add(EventType.NORMAL_AC.toString());
            }
            int additionalAC = eventDetails.get(0).getAdditionalAcRange();
            if(additionalAC > 0){
                events.add(EventType.ADDITIONAL_AC.toString());
            }
            List<Settings> settings = SettingsRepository.getUniqueInstance().getSettingsValues(events);
            if(settings != null && !settings.isEmpty()){
                totalCost = settings.stream().mapToInt(e -> {
                    if(EventType.ADDITIONAL_AC.toString().equals(e.getAttribute())){
                        return  additionalAC * new Integer(e.getAttributeValue());
                    } 
                    return new Integer(e.getAttributeValue());
                }).sum();
            }
        }
        return totalCost;
    }
}
