package easymis.models.repository;

import easymis.models.entity.Expenses;
import easymis.models.entity.TransactionStatus;
import easymis.models.utils.QueryConstants;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class ExpensesRepository extends AbstractRepository {

    private static ExpensesRepository uniqueInstance = new ExpensesRepository();

    private ExpensesRepository() {
    }

    public static ExpensesRepository getUniqueInstance() {
        return uniqueInstance;
    }

    public TransactionStatus create(Expenses expenses) {
        TransactionStatus status;
        try {
            return persist(expenses);
        } catch (Exception ex) {
            status = fillTransactionStatus(ex);
        }

        return status;
    }

    public TransactionStatus update(Expenses expenses) {

        TransactionStatus status;
        try {
            return merge(expenses);
        } catch (Exception ex) {
            status = fillTransactionStatus(ex);
        }
        return status;
    }
    
    public Expenses fetchExpensesForReceiptNumber(String receiptNumber){
        QueryParams param = new QueryParams();
        param.setParamName("receiptNumber");
        param.setParamValue(receiptNumber);
        List<Expenses> events =retrieve(QueryConstants.FETCH_EXPENSES_FOR_RECEIPT_NUMBER, Collections.singletonList(param), Expenses.class);
        return events != null && !events.isEmpty() ? events.get(0) : null;
    }
}
