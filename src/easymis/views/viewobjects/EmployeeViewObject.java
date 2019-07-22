package easymis.views.viewobjects;

/**
 *
 * @author RashidKP
 */
public class EmployeeViewObject {
    
    private String employeeId;
    
    private String firstName;
    
    private String lastName;
    
    private String salary;
    
    private String dateOfBirth;
    
    private String previousExperience;
    
    private String joiningDate;
    
    private String releavingDate;
    
    private String employeeStatus;

    public EmployeeViewObject(String employeeId, String firstName, String lastName, String salary, String dateOfBirth, String previousExperience, String joiningDate, String releavingDate, String employeeStatus) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.previousExperience = previousExperience;
        this.joiningDate = joiningDate;
        this.releavingDate = releavingDate;
        this.employeeStatus = employeeStatus;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPreviousExperience() {
        return previousExperience;
    }

    public void setPreviousExperience(String previousExperience) {
        this.previousExperience = previousExperience;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getReleavingDate() {
        return releavingDate;
    }

    public void setReleavingDate(String releavingDate) {
        this.releavingDate = releavingDate;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
}
