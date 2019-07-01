package easymis.models.entity.enumeration;

/**
 *
 * @author RashidKP
 */
public enum EventType {
    WEDDING("Wedding"),
    MEHANDI("Mehandi"),
    RECEPTION_3_PM("3 PM Reception"),
    RECEPTION_5_PM("5:30 PM Reception"),
    NORMAL_AC("AC"),
    ADDITIONAL_AC("Additional AC"),
    ISHA_HALL_AC_DAY("Isha Hall Day"),
    ISHA_HALL_AC_EVE("Isha Hall Evening"),
    NICA_LONGUE_AC("Nica Longue"),
    WEDDING_AND_MEHANDI("Wedding And Mehandi");

    private String value;

    EventType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
    
     public static EventType fromValue(final String eventTypeString) {

        for (EventType status : EventType.values()) {
            if (eventTypeString.equals(status.toString())) {
                return status;
            }
        }
        return null;
    }
}
