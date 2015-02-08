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
    public static String terminalIP;
    public static int terminalPort;
    public static String LOG_FILE_NAME="";



    public void main()
    {
        try{
             ServerConfig.loadServerConfigFromXML("src/main/resources/terminal.xml");
             loadTerminalConfigFromXML("src/main/resources/terminal.xml");
        }catch (Exception ex)
        {
            return;
        }

        TransactionHandler transactionHandler=new TransactionHandler();

        try
        {
           transactionHandler.runTransactions(transactionHandler.loadTransactionsFromXML("src/main/resources/terminal.xml"));

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void loadTerminalConfigFromXML(String fileName) throws Exception {

        XMLReader xmlReader = new XMLReader();
        try {


            Element domElement = xmlReader.getDocumentElement(xmlReader.parseXmlFile(fileName));

            Node rootNode = xmlReader.getFirstChild(xmlReader.parseXmlFile(fileName));

            //get root attribute
            Element element=(Element)rootNode;
            terminalID = XMLReader.getStringValueOfAttributeTag(element,"id");
            terminalType = XMLReader.getStringValueOfAttributeTag(element,"type");


            NodeList terminalNode = xmlReader.getElementsByTagName(domElement,"outLog");
            element=(Element)terminalNode.item(0);
            LOG_FILE_NAME = XMLReader.getStringValueOfAttributeTag(element,"path");

        } catch (Exception ex) {
            throw new XMLException("server config can not load");
        }
    }

    public String sendRequestToServer(String requestString)
    {
        String responseMessage ="";
        int bufferSize=32;
        int timeOut = 5000;
        byte[] byteBuffer=new byte[bufferSize];
        Socket socket;

        try {
            socket = new Socket(ServerConfig.serverIP,ServerConfig.serverPort);
            socket.setSoTimeout(timeOut);
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();

            outputStream.write(requestString.getBytes());//send message to server

            while ((inputStream.read(byteBuffer)) != -1)
            {
                String s = new String(byteBuffer);
                responseMessage.concat(s);
            }

        }catch (Exception ex)
        {
            LogBuilder.createLog(Terminal.LOG_FILE_NAME,ex.getMessage());
        }

        return responseMessage;
    }







}
