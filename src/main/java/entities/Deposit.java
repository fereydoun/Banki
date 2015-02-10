package entities;
import exceptions.LowerBoundException;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;

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


    public   void deposit(Transaction transaction) throws Exception{

        synchronized (Deposit.deposits.get(transaction.getDepositID().trim())){

            Deposit deposit = (Deposit) Deposit.deposits.get(transaction.getDepositID().trim());
            deposit.setBalance(deposit.balance.add(transaction.getAmount()));//add to deposit
            if (deposit.getBalance().compareTo(deposit.getUpperBound()) == 1){
                transaction.setResult("account balance is greater than the allowable amount");
                throw new LowerBoundException(transaction.getResult(),Server.LOG_FILE_NAME);
            }
            transaction.setResult("Deposit Success");
        }
    }

    public  void withdraw(Transaction transaction) throws Exception{

        synchronized (Deposit.deposits.get(transaction.getDepositID().trim())){

            Deposit deposit = (Deposit) Deposit.deposits.get(transaction.getDepositID().trim());
            deposit.setBalance(deposit.balance.subtract(transaction.getAmount()));//subtract from deposit
            if (deposit.getBalance().compareTo(BigDecimal.ZERO) == -1){
                transaction.setResult("account balance is not enough");
                throw new LowerBoundException(transaction.getResult(),Server.LOG_FILE_NAME);
            }
            transaction.setResult("Withdraw Success");
        }
    }
}
