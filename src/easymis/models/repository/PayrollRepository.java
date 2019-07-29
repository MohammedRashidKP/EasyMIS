package easymis.models.repository;

import easymis.models.entity.Payroll;
import easymis.models.entity.TransactionStatus;
import easymis.models.utils.QueryConstants;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class PayrollRepository extends AbstractRepository {

    private static PayrollRepository uniqueInstance = new PayrollRepository();

    private PayrollRepository() {
    }

    public static PayrollRepository getUniqueInstance() {
        return uniqueInstance;
    }

    public TransactionStatus create(Payroll payroll) {
        TransactionStatus status;
        if (validateSameMonthPayroll(payroll.getMonth(), payroll.getYear())) {
            try {
                return persist(payroll);
            } catch (Exception ex) {
                status = fillTransactionStatus(ex);
            }
        } else {
            status = new TransactionStatus();
            status.setSuccess(false);
            status.setMessage("Payment record already exists for the given Month/Year");
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

    public List<Payroll> fetchAllEmployees() {

        return retrieve(QueryConstants.FETCH_ALL_PAYROLL, null, Payroll.class);
    }

    public Payroll fetchPayrollById(String id) {
        QueryParams param = new QueryParams();
        param.setParamName("id");
        param.setParamValue(id);
        List<Payroll> payrolls = retrieve(QueryConstants.FETCH_PAYROLL_FOR_ID, Collections.singletonList(param), Payroll.class);
        return payrolls != null && !payrolls.isEmpty() ? payrolls.get(0) : null;
    }
    
    public Payroll fetchPayrollByMonthYear(String month, int year) {
        QueryParams param1 = new QueryParams();
        param1.setParamName("month");
        param1.setParamValue(month);
        QueryParams param2 = new QueryParams();
        param2.setParamName("year");
        param2.setParamValue(year);
        List<Payroll> payrolls = retrieve(QueryConstants.FETCH_PAYROLL_FOR_MONTH_YEAR, Arrays.asList(param1, param2), Payroll.class);
        return payrolls != null && !payrolls.isEmpty() ? payrolls.get(0) : null;
    }

    private boolean validateSameMonthPayroll(String month, int year) {
        
        Payroll payroll = fetchPayrollByMonthYear(month, year);
        return payroll == null;
    }

    public List<Payroll> fetchAllPayrollRecords() {

        return retrieve(QueryConstants.FETCH_ALL_PAYROLL, null, Payroll.class);
    } 
    

   
}
