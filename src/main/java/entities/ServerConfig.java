package entities;

import exceptions.XMLException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by Dotin school 5 on 2/8/2015.
 */
public class ServerConfig {
    public static String serverIP;
    public static int serverPort;

    public static void loadServerConfigFromXML(String fileName) throws Exception {

        XMLReader xmlReader = new XMLReader();
        try {


            Element domElement = xmlReader.getDocumentElement(xmlReader.parseXmlFile(fileName));
            NodeList serverNode = xmlReader.getElementsByTagName(domElement,"server");

            Element element=(Element)serverNode.item(0);

            serverIP=XMLReader.getTextValue(element,"ip");
            serverPort=XMLReader.getIntValue(element,"port");


        } catch (Exception ex) {
            throw new XMLException("server config can not load");
        }
    }
}