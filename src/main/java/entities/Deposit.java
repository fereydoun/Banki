package entities;

import java.math.BigDecimal;

/**
 * Created by Dotin school 5 on 2/7/2015.
 */
public class Deposit{

    private String depositNumber;
    private String customerNumber;
    private BigDecimal balance;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;

    public String getDepositNumber() {
        return  depositNumber;
    }

    public void setDepositNumber(String accountNumber) {
        this. depositNumber = accountNumber;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    public  void deposit(BigDecimal amount)
    {

    }

    public void withdraw(BigDecimal amount)
    {

    }
}
