package entities;
import exceptions.XMLException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Terminal {

    public static String terminalID;
    public static String terminalType;
    public static String LOG_FILE_NAME="src/main/resources/log/";
    public static String RESPONSE_FILE_NAME = "src/main/resources/response/response.xml";
    public static final String END_OF_MSG = "#EXIT#";
    public static final int END_OF_MSG_LEN = 6;

    public void main(){
        try{
           ServerConfig.loadServerConfigFromXML("src/main/resources/terminal.xml");
           loadTerminalConfigFromXML("src/main/resources/terminal.xml");
           TransactionHandler transactionHandler=new TransactionHandler();
           transactionHandler.runTransactions(transactionHandler.loadTransactionsFromXML("src/main/resources/terminal.xml"));
        }catch (Exception ex){
            LogBuilder logBuilder=new LogBuilder(Terminal.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
        }
    }

    public static void loadTerminalConfigFromXML(String fileName) throws Exception{

        XMLReader xmlReader = new XMLReader();
        try {
            Element domElement = xmlReader.getDocumentElement(xmlReader.parseXmlFile(fileName));
            Node rootNode = xmlReader.getFirstChild(xmlReader.parseXmlFile(fileName));
            Element element=(Element)rootNode;//get root attribute
            terminalID = XMLReader.getStringValueOfAttributeTag(element,"id");
            terminalType = XMLReader.getStringValueOfAttributeTag(element,"type");
            NodeList terminalNode = xmlReader.getElementsByTagName(domElement,"outLog");
            element=(Element)terminalNode.item(0);
            LOG_FILE_NAME += XMLReader.getStringValueOfAttributeTag(element,"path");

        } catch (Exception ex) {
            throw new XMLException("server config can not load",Terminal.LOG_FILE_NAME);
        }
    }

    public String sendRequestToServer(String requestString){
        String responseMessage ="";
        boolean completed = false;
        int bufferSize=64;
        byte[] byteBuffer=new byte[bufferSize];
        Socket socket;
        try {
            socket = new Socket(ServerConfig.serverIP,ServerConfig.serverPort);
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();
            outputStream.write(requestString.getBytes());//send message to server

            while ((!completed) && (inputStream.read(byteBuffer)) != -1)
            {
                String s = new String(byteBuffer);
                responseMessage += s.trim();
                String patternExit = responseMessage.substring(responseMessage.length() - END_OF_MSG_LEN);
                if(patternExit.trim().compareTo(END_OF_MSG.trim()) == 0)
                    completed=true;
            }
        }catch (Exception ex){
            LogBuilder logBuilder=new LogBuilder(Terminal.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
        }
        return responseMessage;
    }

    public void writeReponseToXML(Transaction transaction) {

        Element tag ;
        try {
            XMLBuilder xmlBuilder = new XMLBuilder(Terminal.RESPONSE_FILE_NAME);
            if ((tag = xmlBuilder.getRootTag()) == null) {
                tag = xmlBuilder.addTag("response");
                xmlBuilder.createAttribute(tag, "terminalID", Terminal.terminalID);
                xmlBuilder.createAttribute(tag, "terminalType", Terminal.terminalType);
            }
            tag = xmlBuilder.addTag(tag, transaction.getOperationType(), transaction.getResult());
            xmlBuilder.createAttribute(tag, "transactionId", String.valueOf(transaction.getTransactionID()));
            xmlBuilder.createAttribute(tag, "depositNumber", String.valueOf(transaction.getDepositID()));

            xmlBuilder.writeToXMLFile();
        } catch (Exception ex) {
            LogBuilder logBuilder = new LogBuilder(Terminal.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
        }
    }
}
