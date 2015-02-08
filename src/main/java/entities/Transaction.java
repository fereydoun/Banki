package entities;

import exceptions.MessageException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Dotin school 5 on 2/7/2015.
 */
public class Transaction implements Serializable {

    private int transactionID;
    private String depositID;
    private String operationType;
    private BigDecimal amount;
    private String result;

    private Terminal terminal;

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getDepositID() {
        return depositID;
    }

    public void setDepositID(String depositID) {
        this.depositID = depositID;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }


    public Transaction convertTransMsgToTransObject(String receivedMessage) throws Exception
    {
        Transaction transaction=new Transaction();

        String delimiter=getDelimiterFromMsg(receivedMessage);
        String[] messages = receivedMessage.split(delimiter);
        if (messages.length != 5)
            throw new MessageException("error in received message structure");

        try {
            transaction.setTransactionID(Integer.parseInt(messages[1]));
            transaction.setOperationType(messages[2]);
            transaction.setAmount(new BigDecimal(messages[3]));
            transaction.setDepositID(messages[4]);
        }catch (Exception e)
        {
            throw new MessageException(e.getMessage());
        }

        return transaction;
    }

    public String getDelimiterFromMsg(String message)
    {
        int delimiterLen = Integer.parseInt(message.substring(0, 1));


        return message.substring(1,delimiterLen);
    }

    public String convertTransObjectToTransMsg(Transaction transaction)
    {
        String message="";
        message.concat("1");//set delimiter length
        message.concat("^");//set delimiter identity
        message.concat(String.valueOf(transaction.getTransactionID()));
        message.concat("^");
        message.concat(transaction.getOperationType());
        message.concat("^");
        message.concat(transaction.getAmount().toString());
        message.concat("^");
        message.concat(transaction.getDepositID());

        return message;
    }

    public Object getInstance(String className) throws Exception
    {
        try {
            Class classDefinition = Class.forName(className);

            return classDefinition.newInstance();

        } catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }

    public Transaction getInitializedTransObjectByElementValues(Element element) throws Exception
    {
        Transaction transaction;
        try{
            transaction=(Transaction)getInstance("Transaction");
            transaction.setTransactionID(XMLReader.getIntValue(element,"id"));
            transaction.setOperationType(XMLReader.getTextValue(element,"type"));
            transaction.setAmount(XMLReader.getBigDecimalValue(element,"amount"));
            transaction.setDepositID(XMLReader.getTextValue(element,"deposit"));

        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }

        return transaction;

    }

}
