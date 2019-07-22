package easymis.views.viewobjects;

/**
 *
 * @author RashidKP
 */
public class RevenueReportTableViewObject {
    
    private String receiptNumber;
    
    private String eventDate;
    
    private String settlementStatus;
    
    private String eventCategory;
    
    private String totalRevenue;
    
    private String totalExpense;
    
    private String profit;

    public RevenueReportTableViewObject(String receiptNumber, String eventDate, String settlementStatus, String eventCategory, String totalRevenue, String totalExpense, String profit) {
        this.receiptNumber = receiptNumber;
        this.eventDate = eventDate;
        this.settlementStatus = settlementStatus;
        this.eventCategory = eventCategory;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
        this.profit = profit;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public String getTotalExpense() {
        return totalExpense;
    }

    public String getProfit() {
        return profit;
    }
    
    
    
}
