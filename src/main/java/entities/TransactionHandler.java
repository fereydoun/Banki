package entities;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;

public class TransactionHandler{

    public List<Transaction> loadTransactionsFromXML(String fileName) throws Exception{

        XMLReader xmlReader = new XMLReader();
        List<Transaction> transactions=new ArrayList<Transaction>();
        Transaction transaction = new Transaction();
        try {
            Element element = xmlReader.getDocumentElement(xmlReader.parseXmlFile(fileName));
            NodeList transactionNode = xmlReader.getElementsByTagName(element, "transaction");

            for (int i = 0; i < transactionNode.getLength(); i++){

                try {
                    Element transactionElement = (Element) transactionNode.item(i);
                    transaction = transaction.getInitializedTransObjectByElementValues(transactionElement);
                    transactions.add(transaction);
                }catch (Exception ex){
                    LogBuilder logBuilder=new LogBuilder(Terminal.LOG_FILE_NAME);
                    logBuilder.writeToLog(ex.getMessage());
                    logBuilder.closeFile();
                }
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
        return transactions;
    }

    public void runTransactions(List<Transaction> transactions){
        Terminal terminal = new Terminal();
        String transactionMessage;
        String responseMessage;
        if(terminal.connect() != 1){
            LogBuilder logBuilder=new LogBuilder(Terminal.LOG_FILE_NAME);
            logBuilder.writeToLog("cannot connect to server");
            logBuilder.closeFile();
            return;
        }

        for (Transaction transaction : transactions) {
            transactionMessage = transaction.convertTransObjectToTransMsg(transaction);
            responseMessage = terminal.sendRequestToServer(transactionMessage);
            transaction.setResult(responseMessage.substring(0,responseMessage.length()-Terminal.END_OF_MSG_LEN));//cut end of message #EXIT#
            terminal.writeReponseToXML(transaction);

            LogBuilder logBuilder=new LogBuilder(Terminal.LOG_FILE_NAME);
            logBuilder.writeToLog(transaction.getResult());
            logBuilder.closeFile();
        }
    }
}
