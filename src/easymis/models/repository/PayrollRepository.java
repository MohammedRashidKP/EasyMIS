package easymis.models.repository;

import easymis.models.entity.Payroll;
import easymis.models.entity.TransactionStatus;
import easymis.models.utils.QueryConstants;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class PayrollRepository extends AbstractRepository{
     private static PayrollRepository uniqueInstance = new PayrollRepository();

    private PayrollRepository() {
    }

    public static PayrollRepository getUniqueInstance() {
        return uniqueInstance;
    }

    public TransactionStatus create(Payroll payroll) {
        TransactionStatus status;
        try {
            return persist(payroll);
        } catch (Exception ex) {
            status = fillTransactionStatus(ex);
        }

        return status;
    }

    public TransactionStatus update(Payroll payroll) {

        TransactionStatus status;
        try {
            return merge(payroll);
        } catch (Exception ex) {
            status = fillTransactionStatus(ex);
        }
        return status;
    }
    
    public List<Payroll> fetchAllEmployees(){

        return retrieve(QueryConstants.FETCH_ALL_PAYROLL, null, Payroll.class);
    }
    
        public Payroll fetchPayrollById(String id){
        QueryParams param = new QueryParams();
        param.setParamName("id");
        param.setParamValue(id);
        List<Payroll> payrolls = retrieve(QueryConstants.FETCH_PAYROLL_FOR_ID, Collections.singletonList(param), Payroll.class);
        return payrolls != null && !payrolls.isEmpty() ? payrolls.get(0) : null;
    }
}
