package entities;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {

    public List<Transaction> loadTransactionsFromXML(String fileName) throws Exception{

        XMLReader xmlReader = new XMLReader();
        List<Transaction> transactions=new ArrayList<Transaction>();
        Transaction transaction = new Transaction();

        try {
            Element element = xmlReader.getDocumentElement(xmlReader.parseXmlFile(fileName));

            NodeList transactionNode = xmlReader.getElementsByTagName(element, "transactions");

            for (int i = 0; i < transactionNode.getLength(); i++) {

                try {
                    Element transactionElement = (Element) transactionNode.item(i);

                    transaction = transaction.getInitializedTransObjectByElementValues(transactionElement);

                    transactions.add(transaction);

                } catch (Exception ex) {
                    LogBuilder.createLog(Server.LOG_FILE_NAME, ex.getMessage());
                }

            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return transactions;

    }

    public void runTransactions(List<Transaction> transactions)
    {
        for (Transaction transaction:transactions)
        {

        }
    }
}