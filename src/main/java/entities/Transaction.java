package entities;
import exceptions.MessageException;
import org.w3c.dom.Element;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.regex.Pattern;

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
        System.out.println(delimiter);
        Pattern pattern=Pattern.compile(Pattern.quote(delimiter));
        String[] messages=pattern.split(receivedMessage);
        if (messages.length != 5)
            throw new MessageException(receivedMessage+messages.length+"1error in received message structure");

        try {
            transaction.setTransactionID(Integer.parseInt(messages[1]));
            transaction.setOperationType(messages[2]);
            transaction.setAmount(new BigDecimal(messages[3]));
            transaction.setDepositID(messages[4]);
        }catch (Exception e){
            throw new MessageException(e.getMessage());
        }
        return transaction;
    }

    public String getDelimiterFromMsg(String message)
    {
        int delimiterLen = Integer.parseInt(message.substring(0, 1));
        int offset = delimiterLen + 1;

        return message.substring(1,offset);
    }

    public String convertTransObjectToTransMsg(Transaction transaction)
    {
        String message;
        message = "1";//set delimiter length
        message += "^";//set delimiter identity
        message += transaction.getTransactionID();
        message += "^";
        message += transaction.getOperationType();
        message += "^";
        message += transaction.getAmount().toString();
        message += "^";
        message += transaction.getDepositID();

        return message;
    }

    public Object getInstance(String className) throws Exception
    {
        try {
            Class classDefinition = Class.forName(className);
            return classDefinition.newInstance();

        } catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public Transaction getInitializedTransObjectByElementValues(Element element) throws Exception
    {
        Transaction transaction;
        try{
            transaction=(Transaction)getInstance("entities.Transaction");
            transaction.setTransactionID(XMLReader.getIntValueOfAttributeTag(element,"id"));
            transaction.setOperationType(XMLReader.getStringValueOfAttributeTag(element,"type"));
            transaction.setAmount(XMLReader.getBigDecimalValueOfAttributeTag(element,"amount"));
            transaction.setDepositID(XMLReader.getStringValueOfAttributeTag(element,"deposit"));
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }

        return transaction;
    }

}
