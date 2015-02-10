import entities.Deposit;
import entities.DepositHandler;
import entities.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

public class UnitTestRunner {

    public static void main(String[] args) {

        DepositHandler depositHandler = new DepositHandler();
        try {
            depositHandler.loadDepositsFromJSONFile("src/main/resources/core.json");
        } catch (Exception ex) {
        }


        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {

                ArrayList<Transaction> arrayList1 = new ArrayList<Transaction>();
                DepositHandler depositHandler1 = new DepositHandler();
                Transaction transaction1 = new Transaction();
                transaction1.setTransactionID(1);
                transaction1.setOperationType("deposit");
                transaction1.setAmount(new BigDecimal(50));
                transaction1.setDepositID("33227781");
                arrayList1.add(transaction1);

                Transaction transaction2 = new Transaction();
                transaction2.setTransactionID(2);
                transaction2.setOperationType("withdraw");
                transaction2.setAmount(new BigDecimal(105));
                transaction2.setDepositID("35527439");
                arrayList1.add(transaction2);

                for (Transaction transaction : arrayList1) {
                    try {
                        depositHandler1.executeClientRequest(transaction);
                    } catch (Exception ex) {

                    }
                }

                for (Deposit deposit : Deposit.deposits) {
                    System.out.println("Thread1" + deposit.getDepositNumber() + "#" + deposit.getBalance());
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {

                ArrayList<Transaction> arrayList1 = new ArrayList<Transaction>();
                DepositHandler depositHandler2 = new DepositHandler();
                Transaction transaction1 = new Transaction();
                transaction1.setTransactionID(1);
                transaction1.setOperationType("deposit");
                transaction1.setAmount(new BigDecimal(70));
                transaction1.setDepositID("33227781");
                arrayList1.add(transaction1);

                Transaction transaction2 = new Transaction();
                transaction2.setTransactionID(2);
                transaction2.setOperationType("withdraw");
                transaction2.setAmount(new BigDecimal(15));
                transaction2.setDepositID("35527439");
                arrayList1.add(transaction2);

                for (Transaction transaction : arrayList1) {
                    try {
                        depositHandler2.executeClientRequest(transaction);
                    } catch (Exception ex) {

                    }
                }

                for (Deposit deposit : Deposit.deposits) {
                    System.out.println("Thread2" + deposit.getDepositNumber() + "#" + deposit.getBalance());
                }
            }
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();
    }
}
