package easymis.models.entity.enumeration;

/**
 *
 * @author RashidKP
 */
public enum EventType {
   WEDDING("Wedding"),
   MEHANDI("Mehandi"),
   RECEPTION_3_PM("3 PM Reception"),
   RECEPTION_5_PM("5 PM Reception"),
   NORMAL_AC("AC"),
   ADDITIONAL_AC("Additional AC"),
   ISHA_HALL_AC("Isha Hall"),
   NICA_LONGUE_AC("Nica Longue");
   
   private String value;
    EventType(final String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
    
    @Override
    public String toString(){
        return this.getValue();
    }
}
