package easymis.models.entity;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author RashidKP
 */
@Entity
@Table(name = "EMIS_INVENTORY_DETAILS_B")
public class Inventory extends DomainObject{
    @Id
    @GeneratedValue(generator = "sqlite")
    @TableGenerator(name = "inventory", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "ITEM_ID", initialValue = 5000, allocationSize = 1)
    @Column(name = "ITEM_ID")
    private int itemId;
    
    @Column(name = "ITEM_NAME")
    private String itemName;
    
    @Column(name = "QUANTITY")
    private int quantity;
    
    @Column(name = "REMARKS")
    private String remarks;
    
    @Column(name = "ADDED_DATE")
    private Date addedDate;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    
}
