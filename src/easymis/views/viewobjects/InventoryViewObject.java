package easymis.views.viewobjects;

import java.sql.Date;

/**
 *
 * @author RashidKP
 */
public class InventoryViewObject {
    
    private int itemId;
    
    private String itemName;
    
    private int quantity;
    
    private String addedDateString;
    
    private Date addedDateValue;
    
    private String updatedDate;
    
    private String remarks;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddedDateString() {
        return addedDateString;
    }

    public void setAddedDateString(String addedDateString) {
        this.addedDateString = addedDateString;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getAddedDateValue() {
        return addedDateValue;
    }

    public void setAddedDateValue(Date addedDateValue) {
        this.addedDateValue = addedDateValue;
    }
    
}
