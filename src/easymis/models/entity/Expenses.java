package easymis.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author RashidKP
 */
@Entity
@Table(name = "EMIS_EXPENSE_DETAILS_B")
public class Expenses extends DomainObject{
    
    @Id
    @Column(name = "RECEIPT_NUMBER")
    private String receiptNumber;
    
    @Column(name = "ELECTRICITY")
    private double electricity;
    
    @Column(name = "CLEANING")
    private double cleaing;
    
    @Column(name = "DAILY_WAGES")
    private double dailyWages;
    
    @Column(name = "SECURITY")
    private double security;
    
    @Column(name = "DIESEL")
    private double diesel;
    
    @Column(name = "TAX")
    private double tax;
    
    @Column(name = "WEDDING_GIFT")
    private double weddingGift;
    
    @Column(name = "OTHER_EXPENSES")
    private double otherExpenses;
    
    @Column(name = "STAGE_SERVICE_CHARGE")
    private double stageServiceCharge;
    
    @Column(name = "VIDEOGRAPHY_CHARGE")
    private double videographyCharge;
    
    @Column(name = "CATERING_SERVICE_CHARGE")
    private double cateringServiceCharge;
    
    @Column(name = "DISCOUNTS")
    private double discounts;
    
    @Column(name = "TOTAL_REVENUE")
    private double totalRevenue;
    
    @Column(name = "TOTAL_EXPENSE")
    private double totalExpense;
    
    @Column(name = "BALANCE")
    private double balance;
    
    @Column(name = "OTHER_REVENUE")
    private double otherRevenue;
    
    @Column(name = "CATERING_SERVICE_PROVIDER")
    private String cateringServiceProvider;

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getCleaing() {
        return cleaing;
    }

    public void setCleaing(double cleaing) {
        this.cleaing = cleaing;
    }

    public double getDailyWages() {
        return dailyWages;
    }

    public void setDailyWages(double dailyWages) {
        this.dailyWages = dailyWages;
    }

    public double getSecurity() {
        return security;
    }

    public void setSecurity(double security) {
        this.security = security;
    }

    public double getDiesel() {
        return diesel;
    }

    public void setDiesel(double diesel) {
        this.diesel = diesel;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getWeddingGift() {
        return weddingGift;
    }

    public void setWeddingGift(double weddingGift) {
        this.weddingGift = weddingGift;
    }

    public double getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(double otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    public double getStageServiceCharge() {
        return stageServiceCharge;
    }

    public void setStageServiceCharge(double stageServiceCharge) {
        this.stageServiceCharge = stageServiceCharge;
    }

    public double getVideographyCharge() {
        return videographyCharge;
    }

    public void setVideographyCharge(double videographyCharge) {
        this.videographyCharge = videographyCharge;
    }

    public double getCateringServiceCharge() {
        return cateringServiceCharge;
    }

    public void setCateringServiceCharge(double cateringServiceCharge) {
        this.cateringServiceCharge = cateringServiceCharge;
    }

    public double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(double discounts) {
        this.discounts = discounts;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getOtherRevenue() {
        return otherRevenue;
    }

    public void setOtherRevenue(double otherRevenue) {
        this.otherRevenue = otherRevenue;
    }

    public String getCateringServiceProvider() {
        return cateringServiceProvider;
    }

    public void setCateringServiceProvider(String cateringServiceProvider) {
        this.cateringServiceProvider = cateringServiceProvider;
    }
    
}
