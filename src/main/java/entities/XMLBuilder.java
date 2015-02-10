package entities;

import exceptions.XMLException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLBuilder {

    private Document document;
    private String fileName;

    public XMLBuilder(String fileName) throws Exception
    {
        if (fileName == "")
            throw new XMLException("File name is empty");
        this.fileName = fileName;
        createDocument(fileName);
    }

    public void createDocument(String fileName) throws Exception
    {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            if ((new File(fileName)).exists())
                document = documentBuilder.parse(fileName);
            else
                document=documentBuilder.newDocument();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public Element addTag(String tagName){

        Element tag=this.document.createElement(tagName);
        this.document.appendChild(tag);
        return tag;
    }
    public Element addTag(String tagName,String tagValue)
    {
        Element tag=this.document.createElement(tagName);
        tag.appendChild(this.document.createTextNode(tagValue));
        this.document.appendChild(tag);
        return tag;
    }

    public Element addTag(Element tag,String childName,String childValue){

        Element childTag=this.document.createElement(childName);
        childTag.appendChild(this.document.createTextNode(childValue));
        tag.appendChild(childTag);
        return  childTag;
    }

    public Attr createAttribute(Element tag,String attrName,String attrValue){

        Attr attr=this.document.createAttribute(attrName);
        attr.setValue(attrValue);
        tag.setAttributeNode(attr);
        return attr;
    }

    public void writeToXMLFile() throws Exception{
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource=new DOMSource(this.document);
            StreamResult streamResult=new StreamResult();
            transformer.transform(domSource,streamResult);

        } catch (Exception ex) {
            throw new XMLException("Error in write to file");
        }
    }


}
