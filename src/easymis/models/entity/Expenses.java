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
    private int electricity;
    
    @Column(name = "CLEANING")
    private int cleaing;
    
    @Column(name = "DAILY_WAGES")
    private int dailyWages;
    
    @Column(name = "SECURITY")
    private int security;
    
    @Column(name = "DIESEL")
    private int diesel;
    
    @Column(name = "TAX")
    private int tax;
    
    @Column(name = "WEDDING_GIFT")
    private int weddingGift;
    
    @Column(name = "OTHER_EXPENSES")
    private int otherExpenses;
    
    @Column(name = "BONUS_PAID")
    private int bonusPaid;
    
    @Column(name = "PURCHASE")
    private int purchase;
    
    @Column(name = "MAINTENANCE")
    private int auditoriumMaintenance;
    
    @Column(name = "DISCOUNTS")
    private int discounts;
    
    @Column(name = "TOTAL_REVENUE")
    private int totalRevenue;
    
    @Column(name = "TOTAL_EXPENSE")
    private int totalExpense;
    
    @Column(name = "BALANCE")
    private int balance;
    
    @Column(name = "OTHER_REVENUE")
    private int otherRevenue;

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public int getElectricity() {
        return electricity;
    }

    public void setElectricity(int electricity) {
        this.electricity = electricity;
    }

    public int getCleaing() {
        return cleaing;
    }

    public void setCleaing(int cleaing) {
        this.cleaing = cleaing;
    }

    public int getDailyWages() {
        return dailyWages;
    }

    public void setDailyWages(int dailyWages) {
        this.dailyWages = dailyWages;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public int getDiesel() {
        return diesel;
    }

    public void setDiesel(int diesel) {
        this.diesel = diesel;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getWeddingGift() {
        return weddingGift;
    }

    public void setWeddingGift(int weddingGift) {
        this.weddingGift = weddingGift;
    }

    public int getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(int otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    public int getBonusPaid() {
        return bonusPaid;
    }

    public void setBonusPaid(int bonusPaid) {
        this.bonusPaid = bonusPaid;
    }

    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }

    public int getAuditoriumMaintenance() {
        return auditoriumMaintenance;
    }

    public void setAuditoriumMaintenance(int auditoriumMaintenance) {
        this.auditoriumMaintenance = auditoriumMaintenance;
    }

    public int getDiscounts() {
        return discounts;
    }

    public void setDiscounts(int discounts) {
        this.discounts = discounts;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(int totalExpense) {
        this.totalExpense = totalExpense;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getOtherRevenue() {
        return otherRevenue;
    }

    public void setOtherRevenue(int otherRevenue) {
        this.otherRevenue = otherRevenue;
    }
    
}
