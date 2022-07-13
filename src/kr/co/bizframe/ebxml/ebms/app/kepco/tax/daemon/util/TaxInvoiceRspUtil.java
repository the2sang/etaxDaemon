//package kr.co.bizframe.ebxml.app.kepco.xmledi.server.xml.tr;
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.TaxInvoice;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.TaxInvoice;
//import kr.co.bizframe.ebxml.app.kepco.xmledi.server.exception.XmlediServerException;
//import kr.co.bizframe.ebxml.app.kepco.xmledi.server.log.Debug;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class TaxInvoiceRspUtil {
	
    private static Unmarshaller trUnmar = null;
    
	public TaxInvoiceRspUtil(){	
		if (trUnmar == null)
			trUnmar = new Unmarshaller(TaxInvoice.class);
	}

	// ////////////////////////////////////////////////////////////////
	// (As XML OutputStream)
	// ////////////////////////////////////////////////////////////////
	public ByteArrayOutputStream generateXML(TaxInvoice tr) throws Exception {
	
	    //Debug.print("Marshaller generate xml doc [generateXML(PurchaseOrderRequest pq)]");
	    ByteArrayOutputStream bao = new ByteArrayOutputStream();
	    try {
	    	//Debug.print("  [Generate PurchaseOrderRequest]  ");
	        //Mapping      mapping = new Mapping();
	        
	        PrintWriter writer = new PrintWriter(bao);
	        Marshaller marshaller = new Marshaller(writer);
	        marshaller.setEncoding("euc-kr");
            marshaller.setNamespaceMapping("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            marshaller.setNamespaceMapping("", "http://www.kiec.or.kr/kis");
            marshaller.setNamespaceMapping("ns1", "http://www.kepco.co.kr/e-vatvil/xmldsig#");
            marshaller.setNamespaceMapping("ns2", "http://www.kepco.co.kr/e-vatvil/extension#");
            marshaller.setSchemaLocation("http://www.kiec.or.kr/kis http://www.kiec.or.kr/kis/TaxInvoice.xsd http://www.kepco.co.kr/e-vatvil/xmldsig# http://edi.kepco.net/kepcobill/xsd/xmldsig/kepco-xmldsig-schema.xsd http://www.kepco.co.kr/e-vatvil/extension# http://edi.kepco.net/kepcobill/xsd/extension/kepco-extension-schema.xsd");	        
	        marshaller.marshal(tr);
	        byte[] nomalized_byte = nomalize(bao.toByteArray());
	        bao = new ByteArrayOutputStream();
	        bao.write(nomalized_byte, 0, nomalized_byte.length);
	        //return bao;
	        //Debug.print("  empUtil[generateXML(PurchaseOrderRequest pq)]  ends ...    ");
	    } catch (MarshalException e) {
	    	System.out.println(e.toString());
	        //throw new XmlediServerException(this,e);
	    } catch (ValidationException e) {
	    	System.out.println(e.toString());
	        //throw new XmlediServerException(this,e);
	    } catch (IOException e) {
	    	System.out.println(e.toString());
	        //throw new XmlediServerException(this,e);
	    }
	    return bao;
	}

	public byte[] nomalize(byte[] xml_byte) throws Exception {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
	    String message = "";
	    try {
	        builder = factory.newDocumentBuilder();
	        factory.setIgnoringElementContentWhitespace(true);
	        Document document = builder.parse(new InputSource(new ByteArrayInputStream(xml_byte)));
	        Element root = document.getDocumentElement();
	        OutputFormat format = new OutputFormat(document, "EUC-KR", true);
	        StringWriter stringOut = new StringWriter();
	        XMLSerializer serial = new XMLSerializer(stringOut, format);
	        serial.asDOMSerializer();
	        serial.serialize(document.getDocumentElement());
	        message = stringOut.toString();
	    } catch (ParserConfigurationException e) {
	    	System.out.println(e.toString());
	        //throw new XmlediServerException(this,e);
	    } catch (SAXException e) {
	    	System.out.println(e.toString());
	        //throw new XmlediServerException(this,e);
	    } catch (IOException e) {
	    	System.out.println(e.toString());
	        //throw new XmlediServerException(this,e);
	    }
	    return message.getBytes();
	}
	// xml file create util
    public boolean createXML(byte[] xml, String filename) throws Exception {
    	boolean makeFile = false;
        try {
            File fn = new File(filename);
            FileOutputStream fos = new FileOutputStream(fn);

            fos.write(xml);
            fos.close();
            makeFile= true;
        } catch (IOException e) {
        	System.out.println(e.toString());
	        //throw new XmlediServerException(this,e);
        }
        return makeFile; 
    }
    
    public TaxInvoice getBindingObject(InputStream xml_in) throws Exception {
    	TaxInvoice bindingObj = null;
        try {
        	//Debug.print("  PurchaseOrderRequest[getBindingObject(InputStream xml_in)]  ends ...    ");
        	bindingObj = (TaxInvoice) trUnmar.unmarshal(new InputSource(xml_in));
         } catch (MarshalException e) {
             System.out.println(e.toString());
             //throw new XmlediServerException(this,e);
         } catch (ValidationException e) {
        	 System.out.println(e.toString());
             //throw new XmlediServerException(this,e);
         }
        return bindingObj; 
    }

}
