package easymis.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author RashidKP
 */
@Entity
@Table(name = "EMIS_SETTINGS_B")
public class Settings extends DomainObject{
    @Id
    @Column(name = "ATTRIBUTE")
    private String attribute;
    
    @Column(name = "ATTRIBUTE_VALUE")
    private String attributeValue;
    
    @Column(name = "ADDITIONAL_DETAILS")
    private String additionalDetails;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }
}
