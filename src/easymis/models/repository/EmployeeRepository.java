package easymis.models.repository;

import easymis.models.entity.Employee;
import easymis.models.entity.TransactionStatus;
import easymis.models.utils.QueryConstants;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class EmployeeRepository extends AbstractRepository{
     private static EmployeeRepository uniqueInstance = new EmployeeRepository();

    private EmployeeRepository() {
    }

    public static EmployeeRepository getUniqueInstance() {
        return uniqueInstance;
    }

    public TransactionStatus create(Employee employee) {
        TransactionStatus status;
        try {
            return persist(employee);
        } catch (Exception ex) {
            status = fillTransactionStatus(ex);
        }

        return status;
    }

    public TransactionStatus update(Employee employee) {

        TransactionStatus status;
        try {
            return merge(employee);
        } catch (Exception ex) {
            status = fillTransactionStatus(ex);
        }
        return status;
    }
    
    public List<Employee> fetchAllEmployees(){

        return retrieve(QueryConstants.FETCH_ALL_EMPLOYEE, null, Employee.class);
    }
    
        public Employee fetchEmployeeById(int employeeId){
        QueryParams param = new QueryParams();
        param.setParamName("employeeId");
        param.setParamValue(employeeId);
        List<Employee> employees = retrieve(QueryConstants.FETCH_EMPLOYEE_FOR_ID, Collections.singletonList(param), Employee.class);
        return employees != null && !employees.isEmpty() ? employees.get(0) : null;
    }
}
