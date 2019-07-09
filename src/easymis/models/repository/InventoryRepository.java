package easymis.models.repository;

import easymis.models.entity.Inventory;
import easymis.models.entity.TransactionStatus;
import easymis.models.utils.QueryConstants;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class InventoryRepository extends AbstractRepository{
    private static InventoryRepository uniqueInstance = new InventoryRepository();

    private InventoryRepository() {
    }

    public static InventoryRepository getUniqueInstance() {
        return uniqueInstance;
    }

    public TransactionStatus create(Inventory inventory) {
        TransactionStatus status;
        try {
            return persist(inventory);
        } catch (Exception ex) {
            status = fillTransactionStatus(ex);
        }

        return status;
    }

    public TransactionStatus update(Inventory inventory) {

        TransactionStatus status;
        try {
            return merge(inventory);
        } catch (Exception ex) {
            status = fillTransactionStatus(ex);
        }
        return status;
    }
    
    public List<Inventory> fetchAllInventory(){

        return retrieve(QueryConstants.FETCH_ALL_INVENTORY, null, Inventory.class);
    }
}
