package entities;

import exceptions.LowerBoundException;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Dotin school 5 on 2/7/2015.
 */
public class Deposit{

    private String depositNumber;
    private String customerNumber;
    private BigDecimal balance;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;
    public static Map deposits=new Hashtable();

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

    public synchronized   void deposit(Transaction transaction) throws Exception
    {
       Deposit deposit =(Deposit) Deposit.deposits.get(transaction.getDepositID().trim());

       deposit.setBalance(deposit.balance.add(transaction.getAmount()));//add to deposit

       if (deposit.getBalance().compareTo(deposit.getUpperBound()) == 1)
            throw new LowerBoundException("account balance is greater than the allowable amount");

      Deposit.deposits.replace(deposit.getDepositNumber(),deposit);

    }

    public synchronized void withdraw(Transaction transaction) throws Exception
    {
        Deposit deposit =(Deposit) Deposit.deposits.get(transaction.getDepositID().trim());

        deposit.setBalance(deposit.balance.subtract(transaction.getAmount()));//subtract from deposit

        if (deposit.getBalance().compareTo(BigDecimal.ZERO) == -1)
            throw new LowerBoundException("account balance is not enough");

        Deposit.deposits.replace(deposit.getDepositNumber(),deposit);
    }
}
