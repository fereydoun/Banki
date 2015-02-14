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
            System.out.println();
        }

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {

                ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
                DepositHandler depositHandler1 = new DepositHandler();
                Transaction transaction1 = new Transaction();
                transaction1.setTransactionID(1);
                transaction1.setOperationType("deposit");
                transaction1.setAmount(new BigDecimal(50));
                transaction1.setDepositID("33227781");
                transactionList.add(transaction1);

                for (Transaction transaction : transactionList) {
                    try {
                        depositHandler1.executeClientRequest(transaction);
                        System.out.println(transaction.getResult());
//                        for (Deposit deposit : Deposit.deposits) {
//                            System.out.println("Thread1 " + deposit.getDepositNumber() + " # " + deposit.getBalance());
//                        }
                    } catch (Exception ex) {
                        System.out.println();
                    }
                }
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {

                ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
                DepositHandler depositHandler2 = new DepositHandler();
                Transaction transaction1 = new Transaction();
                transaction1.setTransactionID(2);
                transaction1.setOperationType("deposit");
                transaction1.setAmount(new BigDecimal(70));
                transaction1.setDepositID("33227781");
                transactionList.add(transaction1);

                for (Transaction transaction : transactionList) {
                    try {
                        depositHandler2.executeClientRequest(transaction);
                        System.out.println(transaction.getResult());
//                        Thread.sleep(5000);
//                        for (Deposit deposit : Deposit.deposits) {
//                            System.out.println("Thread2 " + deposit.getDepositNumber() + " # " + deposit.getBalance());
//                        }
                    } catch (Exception ex) {
                        System.out.println();
                    }
                }
            }
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }
}
