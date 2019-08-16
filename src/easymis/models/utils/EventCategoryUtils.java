package easymis.models.utils;

import easymis.models.entity.EventTypeDetail;
import easymis.models.entity.enumeration.EventCategory;

/**
 *
 * @author RashidKP
 */
public class EventCategoryUtils {
    
    public static EventCategory getEventCategory(EventTypeDetail ec){
        EventCategory eventCategory = EventCategory.SILVER;
        if(ec.isWeddingSelected() && ec.isMehandiSelected() && ec.isAdditionalACSelected()&& ec.isIshaSelected()
                && ec.isNicaSelected()){
            eventCategory = EventCategory.DIAMOND;
        }else if(ec.isWeddingSelected() && ec.isMehandiSelected() && ec.isAdditionalACSelected() && ec.isIshaSelected()){
            eventCategory = EventCategory.DIAMOND;
        }else if(ec.isWeddingSelected() && ec.isAdditionalACSelected() && ec.isIshaSelected() && ec.isNicaSelected()){
            eventCategory = EventCategory.DIAMOND;
        }else if(ec.isWeddingSelected() && ec.isAdditionalACSelected() && ec.isIshaSelected()){
            eventCategory = EventCategory.DIAMOND;
        }else if(ec.isIshaSelected() && ec.isNicaSelected()){
            eventCategory = EventCategory.GOLD;
        }else if(ec.isReceptionSelected() && ec.isNicaSelected()){
            eventCategory = EventCategory.GOLD;
        }else if(ec.isWeddingSelected() && ec.isAdditionalACSelected()){
            eventCategory = EventCategory.GOLD;
        }else if(ec.isWeddingSelected() && ec.isMehandiSelected() && ec.isNicaSelected()){
            eventCategory = EventCategory.GOLD;
        }else if(ec.isWeddingSelected() && ec.isNicaSelected()){
            eventCategory = EventCategory.GOLD;
        }else if(ec.isWeddingSelected() && ec.isMehandiSelected()){
            eventCategory = EventCategory.GOLD;
        }
        return eventCategory;
    }
    
}
