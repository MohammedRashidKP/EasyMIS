package easymis.controllers;

import com.jfoenix.controls.JFXTabPane;
import easymis.models.entity.Employee;
import easymis.models.entity.Payroll;
import easymis.models.entity.TransactionStatus;
import easymis.models.entity.enumeration.EmployeeStatus;
import easymis.models.repository.EmployeeRepository;
import easymis.models.repository.PayrollRepository;
import easymis.utils.AlertHelper;
import easymis.utils.DateHelper;
import easymis.utils.NumberFilter;
import easymis.utils.StringUtils;
import easymis.utils.TooltippedTableCell;
import easymis.views.viewobjects.EmployeeViewObject;
import easymis.views.viewobjects.PayrollViewObject;
import java.net.URL;
import java.sql.Date;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DefaultStringConverter;

/**
 * FXML Controller class
 *
 * @author RashidKP
 */
public class EmployeeController implements Initializable {

    @FXML
    private JFXTabPane tabPane;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private Button reset;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField addressLine1;
    @FXML
    private TextField addressLine2;
    @FXML
    private TextField addressLine3;
    @FXML
    private TextField pinCode;
    @FXML
    private TextField district;
    @FXML
    private TextField state;
    @FXML
    private TextField mobileNumber;
    @FXML
    private TextField alternateNumber;
    @FXML
    private TextField employeeId;
    @FXML
    private DatePicker joininDate;
    @FXML
    private DatePicker releavingDate;
    @FXML
    private ComboBox<String> employeeStatus;
    @FXML
    private TextField salary;
    @FXML
    private TextField previousExperience;
    @FXML
    private TableView<EmployeeViewObject> employeeTable;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_EmpId;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_FirstName;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_LastName;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_Salary;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_DOB;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_PrevExp;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_JoiningDate;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_ReleavingDate;
    @FXML
    private TableColumn<EmployeeViewObject, String> col_EmpStatus;
    @FXML
    private TextField payrollFirstName;
    @FXML
    private TextField payrollLastName;
    @FXML
    private TextField payrollEmployeeId;
    @FXML
    private DatePicker payrollJoiningDate;
    @FXML
    private TextField payrollSalary;
    @FXML
    private TextField payrollBonus;
    @FXML
    private TextField payrollAdvance;
    @FXML
    private TextField payrollNetPay;
    @FXML
    private ComboBox<String> payrollMonth;
    @FXML
    private ComboBox<String> payrollYear;
    @FXML
    private TableColumn<PayrollViewObject, String> col_PayrollEmployeeId;
    @FXML
    private TableColumn<PayrollViewObject, String> col_payroll_firstName;
    @FXML
    private TableColumn<PayrollViewObject, String> col_payrollLastName;
    @FXML
    private TableColumn<PayrollViewObject, String> col_payrollNetPay;
    @FXML
    private TableColumn<PayrollViewObject, String> col_PayrollMonth;
    @FXML
    private TableColumn<PayrollViewObject, String> col_payrollYear;
    @FXML
    private TableColumn<PayrollViewObject, String> col_payrollBonus;
    @FXML
    private TableColumn<PayrollViewObject, String> col_payrollAdvance;
    @FXML
    private TableColumn<PayrollViewObject, String> col_payrollId;
    @FXML
    private TableView<PayrollViewObject> payrollTable;
    @FXML
    private Button editButton;
    @FXML
    private Button btnEmployeeSubmit;

    ObservableList<EmployeeViewObject> employeeObservableList = FXCollections.observableArrayList();

    ObservableList<PayrollViewObject> payrollObservableList = FXCollections.observableArrayList();

    @FXML
    private Tab panelTabEmployee;
    @FXML
    private Tab panelTabPayroll;
    @FXML
    private Label payrollEmployeeStatus;
    @FXML
    private Label payrollId;
    @FXML
    private Button btnPayrollReset;
    @FXML
    private Button btnPayrollEdit;
    @FXML
    private Button btnSalaryPay;
    @FXML
    private Button btnAdvancePay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEmployeeStatusComboBox();
        initializePanelTab();
        initializeNumberFormatter();
        initializeEmployeeTable();
        loadEmployeeTableDetails();
    }

    @FXML
    private void onEmployeeResetClick(ActionEvent event) {
        resetEmployeeDetails();
    }

    private void initializeEmployeeStatusComboBox() {
        ObservableList<String> employeeCombo = FXCollections.observableArrayList();
        employeeCombo.addAll(Arrays.asList(EmployeeStatus.ACTIVE.toString(), EmployeeStatus.RESIGNED.toString()));
        employeeStatus.setItems(employeeCombo);
        employeeStatus.getSelectionModel().selectFirst();
    }

    private boolean validateMandatory() {
        return StringUtils.isNotNullCheckSpace(firstName.getText());
    }

    private Employee getEmployeeDetails() {
        Employee employee = new Employee();

        if (StringUtils.isNotNullCheckSpace(employeeId.getText())) {
            employee.setEmployeeId(Integer.valueOf(employeeId.getText()));
        }

        employee.setFirstName(firstName.getText());
        employee.setLastName(lastName.getText());

        if (dateOfBirth.getValue() != null) {
            employee.setDateOfBirth(Date.valueOf(dateOfBirth.getValue()));
        }

        employee.setAddressLine1(addressLine1.getText());
        employee.setAddressLine2(addressLine2.getText());
        employee.setAddressLine3(addressLine3.getText());

        if (StringUtils.isNotNullCheckSpace(pinCode.getText())) {
            employee.setPinCode(Integer.valueOf(pinCode.getText()));
        }

        employee.setDistrict(district.getText());
        employee.setStates(state.getText());

        if (StringUtils.isNotNullCheckSpace(mobileNumber.getText())) {
            employee.setMobileNumber(mobileNumber.getText());
        }

        if (StringUtils.isNotNullCheckSpace(alternateNumber.getText())) {
            employee.setAlternateNumber(alternateNumber.getText());
        }

        if (joininDate.getValue() != null) {
            employee.setJoiningDate(Date.valueOf(joininDate.getValue()));
        }

        if (releavingDate.getValue() != null) {
            employee.setRelievingDate(Date.valueOf(releavingDate.getValue()));
        }

        employee.setEmployeeStatus(EmployeeStatus.valueOf(employeeStatus.getValue()));

        if (StringUtils.isNotNullCheckSpace(salary.getText())) {
            employee.setSalary(Double.valueOf(salary.getText()));
        }
        employee.setPreviousExperience(previousExperience.getText());

        return employee;
    }

    private void initializeNumberFormatter() {
        pinCode.setTextFormatter(new NumberFilter().filter());
        mobileNumber.setTextFormatter(new NumberFilter().filter());
        alternateNumber.setTextFormatter(new NumberFilter().filter());
        previousExperience.setTextFormatter(new NumberFilter().filter());
        payrollEmployeeId.setTextFormatter(new NumberFilter().filter());
        salary.setTextFormatter(new NumberFilter().decimalFilter());

    }

    @FXML
    private void onEmployeeEditClick(ActionEvent event) {
        makeEmployeeFieldsEditable(true);
    }

    private void makeEmployeeFieldsEditable(boolean flag) {
        firstName.setEditable(flag);
        lastName.setEditable(flag);
        addressLine1.setEditable(flag);
        addressLine2.setEditable(flag);
        addressLine3.setEditable(flag);
        pinCode.setEditable(flag);
        district.setEditable(flag);
        state.setEditable(flag);
        mobileNumber.setEditable(flag);
        alternateNumber.setEditable(flag);
        salary.setEditable(flag);
        previousExperience.setEditable(flag);
        employeeStatus.setDisable(!flag);
        joininDate.setDisable(!flag);
        releavingDate.setDisable(!flag);
        dateOfBirth.setDisable(!flag);
        btnEmployeeSubmit.setDisable(!flag);
        editButton.setDisable(!flag);

    }

    @FXML
    private void onEmployeeSubmitClick(ActionEvent event) {
        if (validateMandatory()) {
            TransactionStatus status;
            if (StringUtils.isNotNullCheckSpace(employeeId.getText())) {
                status = EmployeeRepository.getUniqueInstance().update(getEmployeeDetails());
            } else {
                status = EmployeeRepository.getUniqueInstance().create(getEmployeeDetails());
            }
            if (status.isSuccess()) {
                makeEmployeeFieldsEditable(false);
                loadEmployeeTableDetails();
            }
            AlertHelper.showMessage(status);
        }
    }

    private void loadEmployeeTableDetails() {
        List<Employee> employees = EmployeeRepository.getUniqueInstance().fetchAllEmployees();
        if (employees != null && !employees.isEmpty()) {
            employeeObservableList.clear();
            employees.stream().forEach((employee) -> {
                employeeObservableList.add(buildEmployeeViewObject(employee));
            });
            employeeTable.setItems(employeeObservableList);
        }
    }

    private EmployeeViewObject buildEmployeeViewObject(Employee employee) {

        EmployeeViewObject employeeViewObject = new EmployeeViewObject(
                String.valueOf(employee.getEmployeeId()),
                employee.getFirstName(),
                employee.getLastName(),
                String.valueOf(employee.getSalary()),
                employee.getDateOfBirth() != null ? DateHelper.format(employee.getDateOfBirth()) : null,
                employee.getPreviousExperience(),
                employee.getJoiningDate() != null ? DateHelper.format(employee.getJoiningDate()) : null,
                employee.getRelievingDate() != null ? DateHelper.format(employee.getRelievingDate()) : null,
                employee.getEmployeeStatus() != null ? employee.getEmployeeStatus().toString() : null);
        return employeeViewObject;
    }

    private void initializeEmployeeTable() {
        col_EmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        col_FirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_LastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_Salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        col_DOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        col_PrevExp.setCellValueFactory(new PropertyValueFactory<>("previousExperience"));
        col_JoiningDate.setCellValueFactory(new PropertyValueFactory<>("joiningDate"));
        col_ReleavingDate.setCellValueFactory(new PropertyValueFactory<>("releavingDate"));
        col_EmpStatus.setCellValueFactory(new PropertyValueFactory<>("employeeStatus"));
        employeeTable.setRowFactory(tv -> {
            TableRow<EmployeeViewObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    EmployeeViewObject rowData = row.getItem();
                    if (rowData != null) {
                        populateDetailsForUpdate(rowData);
                    }
                }
            });
            return row;
        });
    }

    private void populateDetailsForUpdate(EmployeeViewObject rowData) {
        if (rowData != null) {
            Employee employee = EmployeeRepository.getUniqueInstance().fetchEmployeeById(Integer.valueOf(rowData.getEmployeeId()));
            if (employee != null) {
                resetEmployeeDetails();
                employeeId.setText(String.valueOf(employee.getEmployeeId()));
                firstName.setText(employee.getFirstName());
                lastName.setText(employee.getLastName());
                addressLine1.setText(employee.getAddressLine1());
                addressLine2.setText(employee.getAddressLine2());
                addressLine3.setText(employee.getAddressLine3());
                pinCode.setText(employee.getPinCode() > 0 ? String.valueOf(employee.getPinCode()) : "");
                district.setText(employee.getDistrict());
                state.setText(employee.getStates());
                mobileNumber.setText(employee.getMobileNumber());
                alternateNumber.setText(employee.getAlternateNumber());
                salary.setText(employee.getSalary() > 0 ? String.valueOf(employee.getSalary()) : "");
                previousExperience.setText(employee.getPreviousExperience());
                employeeStatus.setValue(employee.getEmployeeStatus() != null ? employee.getEmployeeStatus().toString() : "");
                joininDate.setValue(employee.getJoiningDate() != null ? employee.getJoiningDate().toLocalDate() : null);
                releavingDate.setValue(employee.getRelievingDate() != null ? employee.getRelievingDate().toLocalDate() : null);
                dateOfBirth.setValue(employee.getDateOfBirth() != null ? employee.getDateOfBirth().toLocalDate() : null);
                makeEmployeeFieldsEditable(false);
                btnEmployeeSubmit.setDisable(true);
                editButton.setDisable(false);
            }
        }
    }

    private void resetEmployeeDetails() {
        employeeId.clear();
        firstName.clear();
        lastName.clear();
        addressLine1.clear();
        addressLine2.clear();
        addressLine3.clear();
        pinCode.clear();
        district.clear();
        state.clear();
        mobileNumber.clear();
        alternateNumber.clear();
        salary.clear();
        previousExperience.clear();
        initializeEmployeeStatusComboBox();
        joininDate.setValue(null);
        releavingDate.setValue(null);
        dateOfBirth.setValue(null);
        btnEmployeeSubmit.setDisable(false);
        editButton.setDisable(false);
    }

    private void initializePanelTab() {
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newTab) {

                if (null != newTab.getId()) {
                    switch (newTab.getId()) {
                        case "panelTabEmployee":
                            loadEmployeeTableDetails();
                            resetEmployeeDetails();
                            makeEmployeeFieldsEditable(true);
                            break;
                        case "panelTabPayroll":
                            initializePayrollTab();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    private void initializePayrollTab() {

        clearAllPayrollFields();
        initializePayrollTable();
        initializeMonthYearComboBoxes();
        payrollEmployeeId.setTextFormatter(new NumberFilter().filter());
        payrollEmployeeId.addEventFilter(KeyEvent.KEY_PRESSED, onPayrollEmployeeIdChangeChange());
        payrollBonus.addEventFilter(KeyEvent.KEY_PRESSED, onPayrollBonusChange());
        payrollAdvance.addEventFilter(KeyEvent.KEY_PRESSED, onPayrollAdvanceChange());
        payrollAdvance.setTextFormatter(new NumberFilter().decimalFilter());
        payrollBonus.setTextFormatter(new NumberFilter().decimalFilter());
        payrollSalary.setTextFormatter(new NumberFilter().decimalFilter());
        payrollNetPay.setTextFormatter(new NumberFilter().decimalFilter());
    }

    public EventHandler<KeyEvent> onPayrollEmployeeIdChangeChange() {
        return (KeyEvent e) -> {
            if (e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER) {
                String employeeIdValue = payrollEmployeeId.getText();
                if (StringUtils.isNotNullCheckSpace(employeeIdValue)) {
                    populateEmployeeDetailsForPayroll(Integer.valueOf(employeeIdValue));
                }
            }
        };
    }

    public EventHandler<KeyEvent> onPayrollAdvanceChange() {
        return (KeyEvent e) -> {
            if (e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER) {
                if (StringUtils.isNotNullCheckSpace(payrollAdvance.getText())) {
                    refreshNetPayAmount();
                }
            }
        };
    }

    public EventHandler<KeyEvent> onPayrollBonusChange() {
        return (KeyEvent e) -> {
            if (e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER) {
                if (StringUtils.isNotNullCheckSpace(payrollBonus.getText())) {
                    refreshNetPayAmount();
                }

            }
        };
    }

    private void refreshNetPayAmount() throws NumberFormatException {
        double bonus = Double.valueOf("00.0");
        if (StringUtils.isNotNullCheckSpace(payrollBonus.getText())) {
            bonus = Double.valueOf(payrollBonus.getText());
        }
        double advance = Double.valueOf("00.0");
        if (StringUtils.isNotNullCheckSpace(payrollAdvance.getText())) {
            advance = Double.valueOf(payrollAdvance.getText());
        }
        double salaryValue = Double.valueOf("00.0");
        if (StringUtils.isNotNullCheckSpace(payrollSalary.getText())) {
            salaryValue = Double.valueOf(payrollSalary.getText());
        }
        payrollNetPay.setText(String.valueOf((salaryValue + bonus) - advance));
    }

    private void initializePayrollTable() {
        col_PayrollEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        col_PayrollMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        col_payrollYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_payroll_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_payrollLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_payrollAdvance.setCellValueFactory(new PropertyValueFactory<>("advance"));
        col_payrollBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        col_payrollNetPay.setCellValueFactory(new PropertyValueFactory<>("netPay"));
        col_payrollAdvance.setCellFactory(TooltippedTableCell.forTableColumn());
        col_payrollAdvance.setCellFactory((TableColumn<PayrollViewObject, String> param) -> {
            TextFieldTableCell<PayrollViewObject, String> myCell = new TextFieldTableCell<PayrollViewObject, String>(new DefaultStringConverter()) {
                @Override
                public void updateItem(final String value, final boolean empty) {
                    super.updateItem(value, empty);
                    TableRow row = this.getTableRow();
                    PayrollViewObject item = (PayrollViewObject) row.getItem();
                    if (item != null) {
                        String history = item.getAdvanceHistory();
                        this.setText(item.getAdvance());

                        //Add text as tooltip so that user can read text without editing it.
                        Tooltip tooltip = new Tooltip(history);
                        tooltip.setWrapText(true);
                        tooltip.prefWidthProperty().bind(this.widthProperty());
                        this.setTooltip(tooltip);
                    }
                }
            };
            return myCell;
        });

        populatePayrollTable();
    }

    private void initializeMonthYearComboBoxes() {
        ObservableList<String> monthCombo = FXCollections.observableArrayList();
        String currentMonth = DateHelper.getToday().toLocalDate().getMonth().name();
        monthCombo.addAll(Stream.of(Month.values()).map(e -> e.name()).collect(Collectors.toList()));
        payrollMonth.setItems(monthCombo);
        payrollMonth.getSelectionModel().select(currentMonth);

        ObservableList<String> yearCombo = FXCollections.observableArrayList();
        int currentYear = DateHelper.getToday().toLocalDate().getYear();
        for (int i = 2019; i < (currentYear + 50); i++) {
            yearCombo.add(String.valueOf(i));
        }
        payrollYear.setItems(yearCombo);
        payrollYear.getSelectionModel().select(String.valueOf(currentYear));
    }

    private void populateEmployeeDetailsForPayroll(int employeeId) {
        Employee employee = EmployeeRepository.getUniqueInstance().fetchEmployeeById(employeeId);
        if (employee != null) {
            payrollFirstName.setText(employee.getFirstName());
            payrollLastName.setText(employee.getLastName());
            payrollJoiningDate.setValue(employee.getJoiningDate() != null ? employee.getJoiningDate().toLocalDate() : null);
            payrollEmployeeStatus.setText(employee.getEmployeeStatus().toString());
            payrollSalary.setText(String.valueOf(employee.getSalary()));
            payrollBonus.setText("00.0");
            payrollAdvance.setText("00.0");
            Payroll payroll = PayrollRepository.getUniqueInstance().fetchPayrollByMonthYear(
                    employee.getEmployeeId(),
                    payrollMonth.getValue(),
                    Integer.valueOf(payrollYear.getValue()));
            if (payroll != null) {
                payrollBonus.setText(String.valueOf(payroll.getBonus()));
                payrollId.setText(String.valueOf(payroll.getId()));
                double salaryValue = payroll.getSalary();
                if (payroll.getSalary() == 0) {
                    salaryValue = employee.getSalary();
                } else if (payroll.getSalary() > 0) {
                    makePayrollFieldsEditable(false);
                }
                double netPay = (salaryValue + payroll.getBonus()) - payroll.getAdvance();
                payrollNetPay.setText(String.valueOf(netPay));
                payrollAdvance.setUserData(payroll.getAdvanceHistory());
            } else {
                payrollNetPay.setText(String.valueOf(employee.getSalary()));
            }
            payrollEmployeeId.setEditable(false);
            payrollMonth.setDisable(true);
            payrollYear.setDisable(true);
            if (EmployeeStatus.RESIGNED == employee.getEmployeeStatus()) {
                makePayrollFieldsEditable(false);
            }
        } else {
            payrollEmployeeId.clear();
        }
    }

    @FXML
    private void onPayrollResetClick(ActionEvent event) {

        clearAllPayrollFields();
    }

    @FXML
    private void onPayrollEditClick(ActionEvent event) {
        makePayrollFieldsEditable(true);
    }

    @FXML
    private void onPaySalaryButtonClick(ActionEvent event) {
        if (StringUtils.isNotNullCheckSpace(payrollNetPay.getText())
                && StringUtils.isNotNullCheckSpace(payrollEmployeeId.getText())) {
            Payroll payroll = new Payroll();
            payroll.setEmployeeId(Integer.valueOf(payrollEmployeeId.getText()));
            payroll.setFirstName(payrollFirstName.getText());
            payroll.setLastName(payrollLastName.getText());
            payroll.setMonth(payrollMonth.getValue());
            payroll.setYear(Integer.valueOf(payrollYear.getValue()));
            if (StringUtils.isNotNullCheckSpace(payrollSalary.getText())) {
                payroll.setSalary(Double.valueOf(payrollSalary.getText()));
            }
            if (StringUtils.isNotNullCheckSpace(payrollNetPay.getText())) {
                payroll.setNetPay(Double.valueOf(payrollNetPay.getText()));
            }
            if (StringUtils.isNotNullCheckSpace(payrollBonus.getText())) {
                payroll.setBonus(Double.valueOf(payrollBonus.getText()));
            }

            updateTotalAdvance(payroll);
            payroll.setAdvanceHistory(updateAdvanceHistory(false));
            payroll.setNetPay((payroll.getSalary() + payroll.getBonus()) - payroll.getAdvance());
            payrollNetPay.setText(String.valueOf(payroll.getNetPay()));
            TransactionStatus status;
            if (StringUtils.isNotNullCheckSpace(payrollId.getText())) {
                payroll.setId(Integer.valueOf(payrollId.getText()));
                status = PayrollRepository.getUniqueInstance().update(payroll);
            } else {
                status = PayrollRepository.getUniqueInstance().create(payroll);
            }
            AlertHelper.showMessage(status);
            if (status.isSuccess()) {
                makePayrollFieldsEditable(false);
                populatePayrollTable();

            }
        }
    }

    @FXML
    private void onPayAdvanceButtonClick(ActionEvent event) {

        if (StringUtils.isNotNullCheckSpace(payrollAdvance.getText())
                && StringUtils.isNotNullCheckSpace(payrollEmployeeId.getText())
                && Double.valueOf(payrollAdvance.getText()) > 0) {
            Payroll payroll = new Payroll();
            payroll.setEmployeeId(Integer.valueOf(payrollEmployeeId.getText()));
            payroll.setFirstName(payrollFirstName.getText());
            payroll.setLastName(payrollLastName.getText());
            payroll.setMonth(payrollMonth.getValue());
            payroll.setYear(Integer.valueOf(payrollYear.getValue()));
            payroll.setAdvance(Double.valueOf(payrollAdvance.getText()));
            payroll.setAdvanceHistory(updateAdvanceHistory(true));
            TransactionStatus status;
            if (StringUtils.isNotNullCheckSpace(payrollId.getText())) {
                payroll.setId(Integer.valueOf(payrollId.getText()));
                updateTotalAdvance(payroll);
                status = PayrollRepository.getUniqueInstance().update(payroll);
            } else {
                status = PayrollRepository.getUniqueInstance().create(payroll);
            }
            AlertHelper.showMessage(status);
            if (status.isSuccess()) {
                makePayrollFieldsEditable(false);
                populatePayrollTable();
            }
        }
    }

    private void makePayrollFieldsEditable(boolean flag) {

        payrollAdvance.setEditable(flag);
        payrollBonus.setEditable(flag);
        payrollMonth.setDisable(!flag);
        payrollYear.setDisable(!flag);
        btnAdvancePay.setDisable(!flag);
        btnSalaryPay.setDisable(!flag);
        btnPayrollEdit.setDisable(!flag);
    }

    private void clearAllPayrollFields() {
        payrollEmployeeId.clear();
        payrollAdvance.clear();
        payrollBonus.clear();
        payrollNetPay.clear();
        payrollSalary.clear();
        initializeMonthYearComboBoxes();
        btnAdvancePay.setDisable(false);
        btnSalaryPay.setDisable(false);
        btnPayrollEdit.setDisable(false);
        payrollId.setText("");
        payrollFirstName.clear();
        payrollLastName.clear();
        payrollJoiningDate.setValue(null);
        payrollEmployeeStatus.setText("");
        makePayrollFieldsEditable(true);
        payrollEmployeeId.setEditable(true);
        payrollAdvance.setUserData(null);
    }

    private void populatePayrollTable() {
        payrollObservableList.clear();
        List<Payroll> payrolls = PayrollRepository.getUniqueInstance().fetchAllPayrollRecords();
        if (payrolls != null && !payrolls.isEmpty()) {

            payrolls.stream().forEach((payroll) -> {
                payrollObservableList.add(buildPayrollViewObject(payroll));
            });
            payrollTable.setItems(payrollObservableList);

        }
    }

    private PayrollViewObject buildPayrollViewObject(Payroll payroll) {

        PayrollViewObject payrollViewObject = new PayrollViewObject(
                String.valueOf(payroll.getEmployeeId()),
                payroll.getFirstName(),
                payroll.getLastName(),
                payroll.getMonth(),
                String.valueOf(payroll.getYear()),
                String.valueOf(payroll.getNetPay()),
                String.valueOf(payroll.getBonus()),
                String.valueOf(payroll.getAdvance()),
                prettifyAdvanceHistory(payroll.getAdvanceHistory()));
        return payrollViewObject;
    }

    private String updateAdvanceHistory(boolean isPayAdvance) {

        StringBuilder advanceHistory = new StringBuilder();
        String currentHistory = "";
        if (payrollAdvance.getUserData() != null) {
            currentHistory = (String) payrollAdvance.getUserData();
            if(StringUtils.isNotNullCheckSpace(currentHistory)){
                advanceHistory.append(currentHistory);
            }
        }
        if (isPayAdvance) {
            String newEntry = payrollAdvance.getText() + " : " + DateHelper.format(DateHelper.getToday());
            if(advanceHistory.length() > 0){
                advanceHistory.append(",");
            }
            advanceHistory.append(newEntry);
        }

        return advanceHistory.toString();
    }

    private void updateTotalAdvance(Payroll payroll) {

        double existingAdvance = new Double("0.0");
        if (payrollAdvance.getUserData() != null) {
            String[] payrollHistory = payrollAdvance.getUserData().toString().split(",");
            if (payrollHistory != null && payrollHistory.length > 0) {
                for (String current : payrollHistory) {
                    String[] advance = current.split(":");
                    if (advance != null && advance.length > 0) {

                        double eachAdvance = new Double(advance[0]);
                        if (!Double.isNaN(eachAdvance)) {
                            existingAdvance += eachAdvance;
                        }
                    }
                }
            }
        }
        payroll.setAdvance(payroll.getAdvance() + existingAdvance);
    }

    private String prettifyAdvanceHistory(String advanceHistory) {

        return StringUtils.isNotNullCheckSpace(advanceHistory) ? advanceHistory.replaceAll(",", "\n") : "";
    }
}
