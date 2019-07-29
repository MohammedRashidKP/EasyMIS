package easymis.models.entity;

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
@Table(name = "EMIS_EMPLOYEE_PAYROLL_B")
public class Payroll extends DomainObject{
    @Id
    @GeneratedValue(generator = "sqlite")
    @TableGenerator(name = "payroll", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "ID", initialValue = 7000, allocationSize = 1)
    @Column(name = "ID")
    private int id;
    
    @Column(name = "EMPLOYEE_ID")
    private int employeeId;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Column(name = "SALARY_MONTH")
    private String month;
    
    @Column(name = "SALARY_YEAR")
    private int year;
    
    @Column(name = "SALARY")
    private double salary;
    
    @Column(name = "ADVANCE")
    private double advance;
    
    @Column(name = "BONUS")
    private double bonus;
    
    @Column(name = "NET_PAY")
    private double netPay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getAdvance() {
        return advance;
    }

    public void setAdvance(double advance) {
        this.advance = advance;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
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
}
