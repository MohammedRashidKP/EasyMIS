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
    

    public static double getTotalEventCost(List<Event> eventDetails) {
        double totalCost = 0;
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
                totalCost = settings.stream().mapToDouble(e -> {
                    if(EventType.ADDITIONAL_AC.toString().equals(e.getAttribute())){
                        return  additionalAC * new Double(e.getAttributeValue());
                    } 
                    return new Double(e.getAttributeValue());
                }).sum();
            }
        }
        return totalCost;
    }
}
