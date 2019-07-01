package easymis.views.viewobjects;

import easymis.models.entity.enumeration.EventType;

/**
 *
 * @author RashidKP
 */
public class ComboBoxViewObject {
    private String displayText;
    
    private EventType value;

    public ComboBoxViewObject(String displayText, EventType value) {
        this.displayText = displayText;
        this.value = value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public EventType getValue() {
        return value;
    }
    
    @Override
    public String toString(){
        return displayText;
    }
    
}
