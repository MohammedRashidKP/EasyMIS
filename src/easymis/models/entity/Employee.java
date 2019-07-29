package easymis.models.entity;

import easymis.models.entity.enumeration.EmployeeStatus;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.ObjectTypeConverter;

/**
 *
 * @author RashidKP
 */
@Entity
@Table(name = "EMIS_EMPLOYEE_DETAILS_B")
public class Employee extends DomainObject{
    @Id
    @GeneratedValue(generator = "sqlite")
    @TableGenerator(name = "employee", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "EMPLOYEE_ID", initialValue = 6000, allocationSize = 1)
    @Column(name = "EMPLOYEE_ID")
    private int employeeId; 
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    
    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;
    
    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;
    
    @Column(name = "ADDRESS_LINE_3")
    private String addressLine3;
    
    @Column(name = "PIN_CODE")
    private int pinCode;
    
    @Column(name = "DISTRICT")
    private String district;
    
    @Column(name = "STATE")
    private String states;
    
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    
    @Column(name = "ALTERNATE_NUMBER")
    private String alternateNumber;
    
    @Column(name = "JOINING_DATE")
    private Date joiningDate;
    
    @Column(name = "RELIEVING_DATE")
    private Date relievingDate;
    
    @Column(name = "SALARY_AMOUNT")
    private double salary;
    
    @Column(name = "PREVIOUS_EXPERIENCE")
    private String previousExperience;
    
    @ObjectTypeConverter(
            name = "employeeStatusConverter", objectType = EmployeeStatus.class, dataType = String.class, conversionValues = {
                @ConversionValue(objectValue = "ACTIVE", dataValue = "ACTIVE"),
                @ConversionValue(objectValue = "RESIGNED", dataValue = "RESIGNED")}
    )
    @Convert("employeeStatusConverter")
    @Column(name = "EMPLOYEE_STATUS")
    private EmployeeStatus employeeStatus;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Date getRelievingDate() {
        return relievingDate;
    }

    public void setRelievingDate(Date relievingDate) {
        this.relievingDate = relievingDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPreviousExperience() {
        return previousExperience;
    }

    public void setPreviousExperience(String previousExperience) {
        this.previousExperience = previousExperience;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
    
    
}
