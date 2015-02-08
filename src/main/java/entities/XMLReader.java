package entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;

public class XMLReader {

    //get DoumentBuilder from loaded xml file
    public Document parseXmlFile(String fileName) throws Exception
    {
        Document dom ;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

        try{

            DocumentBuilder docBuilder=docBuilderFactory.newDocumentBuilder();
            dom = docBuilder.parse(fileName);

        }
        catch (Exception ex)   {
            throw new Exception(ex.getMessage());
        }

        return dom;
    }


    public Node getFirstChild(Document dom) throws Exception
    {
        try {

            return dom.getFirstChild();
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }


    //get elements from  Document of xml file
    public Element getDocumentElement(Document dom) throws Exception
    {

        try {

            //get the root element
            return dom.getDocumentElement();
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }

    }


    //get node list of Element by tag name
    public NodeList getElementsByTagName(Element docElement,String tagName) throws Exception
    {
        try
        {
            //get the nodelist of elements
            return  docElement.getElementsByTagName(tagName);

        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }

    }


    //return String tag types
    public static String  getTextValue(Element element,String tagName) throws Exception
    {
        try {

            NodeList nodeList = element.getElementsByTagName(tagName);
            if (nodeList != null && nodeList.getLength() > 0)
                return nodeList.item(0).getFirstChild().getNodeValue();
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }


        return "";
    }


    //return int tag types
    public static int getIntValue(Element element,String tagName) throws Exception {
        try {

            return Integer.parseInt(getTextValue(element, tagName));

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }


    //return BigDecimal tag types
    public static BigDecimal getBigDecimalValue(Element element,String tagName) throws Exception
    {
        try
        {
            return new BigDecimal(getTextValue(element,tagName));

        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }

    }

    public static int getIntValueOfAttributeTag(Element element,String attributeName) throws Exception
    {
        try {
            return Integer.parseInt(getStringValueOfAttributeTag(element,attributeName));
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }

    public static BigDecimal getBigDecimalValueOfAttributeTag(Element element,String attributeName) throws Exception
    {
        try {
            return new BigDecimal(getStringValueOfAttributeTag(element,attributeName));
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }

    public static String getStringValueOfAttributeTag(Element element,String attributeName) throws Exception
    {
        try {
            return element.getAttributes().getNamedItem(attributeName).getNodeValue();
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }
}
