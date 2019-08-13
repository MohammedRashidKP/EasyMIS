package easymis.views.viewobjects;

/**
 *
 * @author RashidKP
 */
public class PayrollViewObject {
    
    private String employeeId;
    
    private String firstName;
    
    private String lastName;
    
    private String month;
    
    private String year;
    
    private String netPay;
    
    private String bonus;
    
    private String advance;
    
    private String advanceHistory;

    public PayrollViewObject(String payrollId, 
            String firstName, 
            String lastName, 
            String month, 
            String year, 
            String netPay, 
            String bonus, 
            String advance,
            String advanceHistory) {
        this.employeeId = payrollId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.month = month;
        this.year = year;
        this.netPay = netPay;
        this.bonus = bonus;
        this.advance = advance;
        this.advanceHistory = advanceHistory;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNetPay() {
        return netPay;
    }

    public void setNetPay(String netPay) {
        this.netPay = netPay;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getAdvanceHistory() {
        return advanceHistory;
    }

    public void setAdvanceHistory(String advanceHistory) {
        this.advanceHistory = advanceHistory;
    }
    
    
}
