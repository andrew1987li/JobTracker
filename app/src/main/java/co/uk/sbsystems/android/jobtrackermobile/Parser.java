package co.uk.sbsystems.android.jobtrackermobile;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.sax.Element;

public class Parser {

	//public C_JTJob objJTJob;
	
    private Boolean docLoaded = false;
	Document mydoc = null;
	
    public void ResetDocLoaded() {
    	docLoaded = false;
    }
    
	public String ExtractTagValue(String sDataIn,String sTag) {
		String sRet= "";
		
		
		// Not sure why but this is not coping with encoded &amp;
		// The HTML is formatted correctly but is being truncated at the &amp;
		// Removed following lines as it was an attempt to fix but made no difference.
		try {
			sDataIn = sDataIn.replaceAll("&amp;", "and");
		} catch (Exception e) {
			
		}
		
		try
		{
			if (docLoaded == false) {
				mydoc = XMLfromString(sDataIn);
				docLoaded = true;
			}
			
			// Document doc = XMLfromString(sDataIn);
			
			NodeList listAllNodes = mydoc.getElementsByTagName(sTag);			// Get list of all STATUS nodes 
		
			try {
				if (listAllNodes.getLength() > 0) {
					Node singleNode = listAllNodes.item(0);										// Get first NODE as our XML always ever has one node.
					String propertyValue = singleNode.getFirstChild().getNodeValue().toString();			// Return the value of the first child node. 
					sRet = propertyValue;
				}
			} catch (Exception e)
			{
				sRet = "";
			}
		}
		catch (Exception e)
		{
			System.out.println("ExtractTagValue: " + e.getMessage());
		}
		
		return sRet;
	}
	
	
	public Document XMLfromString(String xml){
		Document doc = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure:" + e.getMessage());
			return null;
		} catch(IOException e) {
			System.out.println("I/O Exception: " + e.getMessage());
		}
		
		return doc;
	}
	
	public static int numResults(Document doc){		
		Node results = doc.getDocumentElement();
		int res = -1;
		
		try{
			res = Integer.valueOf(results.getAttributes().getNamedItem("count").getNodeValue());
		}catch(Exception e ){
			res = -1;
		}
		
		return res;
	}
	
	public static String getCommand(Document doc) {
		Node results = doc.getDocumentElement();
		String Cmd = "";
		try {
			Cmd = results.getAttributes().getNamedItem("JOBNO").getNodeValue();
		} catch(Exception e) {
			Cmd= "";
		}
		
		return Cmd;
	}
	
	/** Returns element value
	  * @param elem element (it is XML tag)
	  * @return Element value otherwise empty String
	  */
	 public final static String getElementValue( Node elem ) {
		 
		 try {
		     Node kid;
		     if( elem != null){
		         if (elem.hasChildNodes()){
		             for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
		                 if( kid.getNodeType() == Node.TEXT_NODE  ){
		                     return kid.getNodeValue();
		                 }
		             }
		         }
		     }
		 } catch(Exception e)
		 {
			 
		 }
		 
	     return "";
	     
	 }
	

	public static String getValue(Element item, String str) {		
		try {
			NodeList n = ((Document) item).getElementsByTagName(str);
			return getElementValue(n.item(0));
		} catch (Exception e)
		{
			return "";
		}
	}
}
	
	
	
