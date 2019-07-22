package easymis.models.entity.enumeration;

/**
 *
 * @author RashidKP
 */
public enum ActionType {
    
    BOOK("BOOK EVENT"),
    BLOCK("BLOCK EVENT"),
    CANCEL_BOOKING("CANCEL BOOKING"),
    CANCEL_BLOCKING("CANCEL BLOCKING"),
    UPDATE("UPDATE EVENT");
    
     private String value;

    ActionType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
    
     public static ActionType fromValue(final String eventTypeString) {

        for (ActionType status : ActionType.values()) {
            if (eventTypeString.equals(status.toString())) {
                return status;
            }
        }
        return null;
    }
}
