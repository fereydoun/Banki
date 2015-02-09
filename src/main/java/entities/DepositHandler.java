package entities;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Method;
import java.math.BigDecimal;


public class DepositHandler {

    public void loadDepositsFromJSONFile(String fileName) throws Exception {
        JSONReader jsonReader = new JSONReader();
        Deposit deposit;

        try {


            JSONObject jsonObject = jsonReader.readJSONFile(fileName);
            JSONArray jsonDeposits = (JSONArray) jsonObject.get("deposits");

            for (int i = 0; i < jsonDeposits.toArray().length; i++) {
                deposit = new Deposit();
                try {

                    JSONObject jsonDeposit = (JSONObject) jsonDeposits.toArray()[i];
                    deposit.setDepositNumber(jsonDeposit.get("id").toString());
                    deposit.setCustomerNumber(jsonDeposit.get("customer").toString());
                    deposit.setBalance(new BigDecimal(jsonDeposit.get("initialBalance").toString()));
                    deposit.setInitialBalance(new BigDecimal(jsonDeposit.get("initialBalance").toString()));
                    deposit.setUpperBound(new BigDecimal(jsonDeposit.get("upperBound").toString()));

                    Deposit.deposits.put(deposit.getDepositNumber().trim(), deposit);

                } catch (Exception ex) {
                    System.out.println();
                }


            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }

    public void executeClientRequest(Transaction transaction) throws  Exception
    {
        Class[] paramTransaction = new Class[1];
        paramTransaction[0] = Transaction.class;

        try
        {
            Class cls=Class.forName("entities.Deposit");
            Object deposit= cls.newInstance();

            //call related method base on request
            Method method= cls.getDeclaredMethod(transaction.getOperationType(),paramTransaction);
            method.invoke(deposit,transaction);

        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }



}
