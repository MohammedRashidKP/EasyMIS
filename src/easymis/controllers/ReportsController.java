package easymis.controllers;

import com.jfoenix.controls.JFXTabPane;
import easymis.models.entity.Booking;
import easymis.models.entity.Employee;
import easymis.models.entity.Expenses;
import easymis.models.entity.Payroll;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.EmployeeRepository;
import easymis.models.repository.EventRepository;
import easymis.models.repository.ExpensesRepository;
import easymis.models.repository.PayrollRepository;
import easymis.utils.DateHelper;
import easymis.views.viewobjects.EventReportTableViewObject;
import easymis.views.viewobjects.RevenueReportTableViewObject;
import easymis.views.viewobjects.SalaryReportTableViewObject;
import java.net.URL;
import java.sql.Date;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author RashidKP
 */
public class ReportsController implements Initializable {

    @FXML
    private JFXTabPane tabPane;
    @FXML
    private TableView<EventReportTableViewObject> eventTable;
    @FXML
    private Tab revenueReportTab;
    @FXML
    private TableView<RevenueReportTableViewObject> revenueTable;
    @FXML
    private TableColumn<RevenueReportTableViewObject, String> revenue_receiptNumber;
    @FXML
    private TableColumn<RevenueReportTableViewObject, String> revenue_eventDate;
    @FXML
    private TableColumn<RevenueReportTableViewObject, String> revenue_settlementStatus;
    @FXML
    private TableColumn<RevenueReportTableViewObject, String> revenue_eventType;
    @FXML
    private TableColumn<RevenueReportTableViewObject, String> revenue_totalRevenue;
    @FXML
    private TableColumn<RevenueReportTableViewObject, String> revenue_totalExpense;
    @FXML
    private TableColumn<RevenueReportTableViewObject, String> revenue_profit;
    @FXML
    private DatePicker revenueFromDate;
    @FXML
    private DatePicker revenueToDate;
    @FXML
    private TextField totalRevenue;
    @FXML
    private TextField totalExpense;
    @FXML
    private TextField profit;
    @FXML
    private Tab eventReportTab;
    @FXML
    private TableColumn<EventReportTableViewObject, String> event_receiptNumber;
    @FXML
    private TableColumn<EventReportTableViewObject, String> event_eventDate;
    @FXML
    private TableColumn<EventReportTableViewObject, String> event_fullName;
    @FXML
    private TableColumn<EventReportTableViewObject, String> event_bookingStatus;
    @FXML
    private TableColumn<EventReportTableViewObject, String> event_eventType;
    @FXML
    private TableColumn<EventReportTableViewObject, String> event_bookingDate;
    @FXML
    private TableColumn<EventReportTableViewObject, String> event_eventCategory;
    @FXML
    private DatePicker eventFromDate;
    @FXML
    private DatePicker eventToDate;
    @FXML
    private TextField totalEvents;
    @FXML
    private TextField totalBooked;
    @FXML
    private TextField totalBlocked;
    @FXML
    private TextField totalCancelled;
    @FXML
    private Tab salaryReportTab;
    @FXML
    private TableView<SalaryReportTableViewObject> salaryTable;
    @FXML
    private TableColumn<SalaryReportTableViewObject, String> salary_employeeId;
    @FXML
    private TableColumn<SalaryReportTableViewObject, String> salary_firstName;
    @FXML
    private TableColumn<SalaryReportTableViewObject, String> salary_lastName;
    @FXML
    private TableColumn<SalaryReportTableViewObject, String> salary_employeeStatus;
    @FXML
    private TableColumn<SalaryReportTableViewObject, String> salary_salary;
    @FXML
    private TableColumn<SalaryReportTableViewObject, String> salary_salaryDate;
    @FXML
    private TableColumn<SalaryReportTableViewObject, String> salary_bonus;
    @FXML
    private TextField totalSalary;
    @FXML
    private TextField totalBonus;

    ObservableList<SalaryReportTableViewObject> salaryObservableList = FXCollections.observableArrayList();
    ObservableList<EventReportTableViewObject> eventsObservableList = FXCollections.observableArrayList();
    ObservableList<RevenueReportTableViewObject> revenueObservableList = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> fromMonth;
    @FXML
    private ComboBox<String> fromYear;
    @FXML
    private ComboBox<String> toMonth;
    @FXML
    private ComboBox<String> toYear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeRevenueTable();
        initializeEventTable();
        initializeSalaryTable();
        initializeMonthYearComboBoxes();
    }

    @FXML
    private void generateRevenueReport(ActionEvent event) {

        revenueObservableList.clear();
        double totalRevenueValue = new Double("0.00");
        double totalExpenseValue = new Double("0.00");
        double totalProfit = new Double("0.00");
        if (revenueFromDate.getValue() != null && revenueToDate.getValue() != null) {
            List<Booking> bookings = EventRepository.getUniqueInstance().getBookingsBetweenDays(
                    Date.valueOf(revenueFromDate.getValue()),
                    Date.valueOf(revenueToDate.getValue()));

            if (bookings != null && !bookings.isEmpty()) {
                revenueObservableList.clear();
                for (Booking booking : bookings) {
                    RevenueReportTableViewObject viewObj = buildRevenueTableViewObject(booking);
                    if (viewObj != null) {
                        revenueObservableList.add(viewObj);
                        totalRevenueValue += Double.valueOf(viewObj.getTotalRevenue());
                        totalExpenseValue += Double.valueOf(viewObj.getTotalExpense());
                        totalProfit += Double.valueOf(viewObj.getProfit());
                    }

                }
                revenueTable.setItems(revenueObservableList);
            }
        }
        totalRevenue.setText(String.valueOf(totalRevenueValue));
        totalExpense.setText(String.valueOf(totalExpenseValue));
        profit.setText(String.valueOf(totalProfit));
    }

    @FXML
    private void generateEventReport(ActionEvent event) {

        eventsObservableList.clear();
        int totalEventsCount = 0;
        int bookedCount = 0;
        int blockedCount = 0;
        int cancelledCount = 0;
        if (eventFromDate.getValue() != null && eventToDate.getValue() != null) {
            List<Booking> bookings = EventRepository.getUniqueInstance().getBookingsBetweenDays(
                    Date.valueOf(eventFromDate.getValue()),
                    Date.valueOf(eventToDate.getValue()));
            if (bookings != null && !bookings.isEmpty()) {
                for (Booking booking : bookings) {
                    EventReportTableViewObject viewObj = new EventReportTableViewObject(
                            booking.getReceiptNumber(),
                            DateHelper.format(booking.getEventDate()),
                            getFullName(booking.getFirstName(), booking.getLastName()),
                            booking.getBookingStatus().toString(),
                            resolveEventType(booking),
                            DateHelper.format(booking.getCreatedDate()),
                            booking.getEventCategory().toString());
                    eventsObservableList.add(viewObj);

                    if (null != booking.getBookingStatus()) {
                        switch (booking.getBookingStatus()) {
                            case BOOKED:
                                bookedCount++;
                                totalEventsCount++;
                                break;
                            case BLOCKED:
                                blockedCount++;
                                break;
                            case BOOKING_CANCELLED:
                                cancelledCount++;
                                totalEventsCount++;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            eventTable.setItems(eventsObservableList);
        }
        totalEvents.setText(String.valueOf(totalEventsCount));
        totalBooked.setText(String.valueOf(bookedCount));
        totalBlocked.setText(String.valueOf(blockedCount));
        totalCancelled.setText(String.valueOf(cancelledCount));
    }

    @FXML
    private void generateSalaryReport(ActionEvent event) {

        salaryObservableList.clear();
        Double totalSalaryPaid = new Double("0.0");
        Double bonusPaid = new Double("0.0");
        Month fromMonthValue = Month.valueOf(fromMonth.getValue());
        int fromYearValue = Integer.valueOf(fromYear.getValue());
        Month toMonthValue = Month.valueOf(toMonth.getValue());
        int toYearValue = Integer.valueOf(toYear.getValue());
        List<Payroll> payrolls = PayrollRepository.getUniqueInstance().fetchPayrollByInterval(fromMonthValue, fromYearValue, toMonthValue, toYearValue);
        if (payrolls != null && !payrolls.isEmpty()) {
            for (Payroll payroll : payrolls) {

                Employee employee = EmployeeRepository.getUniqueInstance().fetchEmployeeById(payroll.getEmployeeId());

                String salaryDate = payroll.getMonth() + "/" + payroll.getYear();
                SalaryReportTableViewObject viewObj = new SalaryReportTableViewObject(
                        String.valueOf(payroll.getEmployeeId()),
                        payroll.getFirstName(),
                        payroll.getLastName(),
                        employee.getEmployeeStatus() != null ? employee.getEmployeeStatus().name() : null,
                        String.valueOf(payroll.getSalary()),
                        salaryDate,
                        String.valueOf(payroll.getBonus()));
                salaryObservableList.add(viewObj);
                totalSalaryPaid += payroll.getSalary();
                bonusPaid += payroll.getBonus();
                totalSalary.setText(String.valueOf(totalSalaryPaid));
                totalBonus.setText(String.valueOf(bonusPaid));
            }
            salaryTable.setItems(salaryObservableList);
        }
    }

    private void initializeMonthYearComboBoxes() {
        ObservableList<String> monthCombo = FXCollections.observableArrayList();
        String currentMonth = DateHelper.getToday().toLocalDate().getMonth().name();
        monthCombo.addAll(Stream.of(Month.values()).map(e -> e.name()).collect(Collectors.toList()));
        fromMonth.setItems(monthCombo);
        fromMonth.getSelectionModel().select(currentMonth);
        toMonth.setItems(monthCombo);
        toMonth.getSelectionModel().select(currentMonth);
        ObservableList<String> yearCombo = FXCollections.observableArrayList();
        int currentYear = DateHelper.getToday().toLocalDate().getYear();
        for (int i = 2019; i < (currentYear + 50); i++) {
            yearCombo.add(String.valueOf(i));
        }
        fromYear.setItems(yearCombo);
        fromYear.getSelectionModel().select(String.valueOf(currentYear));
        toYear.setItems(yearCombo);
        toYear.getSelectionModel().select(String.valueOf(currentYear));
    }

    private RevenueReportTableViewObject buildRevenueTableViewObject(Booking booking) {

        RevenueReportTableViewObject revenueReportTableViewObject = null;
        Expenses expense = ExpensesRepository.getUniqueInstance().fetchExpensesForReceiptNumber(booking.getReceiptNumber());
        if (expense != null) {
            double totalRevenueValue = booking.getBookingCost();
            double totalExpenseValue = expense.getTotalExpense();

            double profitValue = totalRevenueValue - totalExpenseValue;
            revenueReportTableViewObject = new RevenueReportTableViewObject(
                    String.valueOf(booking.getReceiptNumber()),
                    DateHelper.format(booking.getEventDate()),
                    "CLOSED",
                    booking.getEventCategory().toString(),
                    String.valueOf(totalRevenueValue),
                    String.valueOf(totalExpenseValue),
                    String.valueOf(profitValue));
        }
        return revenueReportTableViewObject;
    }

    private String getFullName(String firstName, String lastName) {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) {
            fullName.append(firstName);
        }
        if (lastName != null) {
            fullName.append(" ").append(lastName);
        }
        return fullName.toString();
    }

    private String resolveEventType(Booking eventDetails) {
        String eventType = "";
        List<EventType> eventTypes = getEventTypeEnums(eventDetails);
        if (eventDetails.getEvents().get(0).isNormalAcRequired()) {
            eventTypes.add(EventType.NORMAL_AC);
        }
        StringBuilder eventTypeBuilder = new StringBuilder();
        if (!eventTypes.isEmpty()) {
            for (int i = 0; i < eventTypes.size(); i++) {
                eventTypeBuilder.append(eventTypes.get(i));
                if (i != eventTypes.size() - 1) {
                    eventTypeBuilder.append(", ");
                }
            }
            eventType = eventTypeBuilder.toString();
        }
        return eventType;

    }

    public List<EventType> getEventTypeEnums(Booking booking) {
        List<EventType> eventTypes = new ArrayList<>();

        booking.getEvents().stream().forEach((event) -> {
            eventTypes.add(event.getEventType());
        });
        return eventTypes;
    }

    private void initializeRevenueTable() {
        revenue_receiptNumber.setCellValueFactory(new PropertyValueFactory<>("receiptNumber"));
        revenue_eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        revenue_settlementStatus.setCellValueFactory(new PropertyValueFactory<>("settlementStatus"));
        revenue_eventType.setCellValueFactory(new PropertyValueFactory<>("eventCategory"));
        revenue_totalRevenue.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        revenue_totalExpense.setCellValueFactory(new PropertyValueFactory<>("totalExpense"));
        revenue_profit.setCellValueFactory(new PropertyValueFactory<>("profit"));
    }

    private void initializeEventTable() {
        event_receiptNumber.setCellValueFactory(new PropertyValueFactory<>("receiptNumber"));
        event_eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        event_fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        event_bookingStatus.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
        event_eventType.setCellValueFactory(new PropertyValueFactory<>("events"));
        event_bookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        event_eventCategory.setCellValueFactory(new PropertyValueFactory<>("eventCategory"));

    }

    private void initializeSalaryTable() {

        salary_employeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        salary_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        salary_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        salary_employeeStatus.setCellValueFactory(new PropertyValueFactory<>("employeeStatus"));
        salary_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salary_salaryDate.setCellValueFactory(new PropertyValueFactory<>("salaryDate"));
        salary_bonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));
    }
}
