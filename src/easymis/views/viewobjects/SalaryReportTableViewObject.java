package easymis.views.viewobjects;

/**
 *
 * @author RashidKP
 */
public class SalaryReportTableViewObject {
    
    private String employeeId;
    
    private String firstName;
    
    private String lastName;
    
    private String employeeStatus;
    
    private String salary;
    
    private String salaryDate;
    
    private String bonus;

    public SalaryReportTableViewObject(String employeeId, String firstName, String lastName, String employeeStatus, String salary, String salaryDate, String bonus) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeStatus = employeeStatus;
        this.salary = salary;
        this.salaryDate = salaryDate;
        this.bonus = bonus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public String getSalary() {
        return salary;
    }

    public String getSalaryDate() {
        return salaryDate;
    }

    public String getBonus() {
        return bonus;
    }
    
    
}
