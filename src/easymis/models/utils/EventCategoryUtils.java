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
        if(ec.isWeddingSelected() && ec.isMehandiSelected() && ec.isAcSelected() && ec.isIshaSelected()
                && ec.isNicaSelected()){
            eventCategory = EventCategory.DIAMOND;
        }else if(ec.isWeddingSelected() && ec.isMehandiSelected() && ec.isAcSelected() && ec.isIshaSelected()){
            eventCategory = EventCategory.DIAMOND;
        }else if(ec.isWeddingSelected() && ec.isAcSelected() && ec.isIshaSelected() && ec.isNicaSelected()){
            eventCategory = EventCategory.DIAMOND;
        }else if(ec.isWeddingSelected() && ec.isAcSelected() && ec.isIshaSelected()){
            eventCategory = EventCategory.DIAMOND;
        }else if(ec.isIshaSelected() && ec.isNicaSelected()){
            eventCategory = EventCategory.GOLD;
        }else if(ec.isReceptionSelected() && ec.isNicaSelected()){
            eventCategory = EventCategory.GOLD;
        }else if(ec.isWeddingSelected() && ec.isAcSelected()){
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
