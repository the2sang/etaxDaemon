package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.TaxInvoice;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.EBXMLAppException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TaxinvUtil { 

    private static Unmarshaller taxinvUnmar = null;

    public TaxinvUtil() {
        if (taxinvUnmar == null)
            taxinvUnmar = new Unmarshaller(TaxInvoice.class);
    }

    // ////////////////////////////////////////////////////////////////
    // 주문응답서 생성(As XML OutputStream)
    // ////////////////////////////////////////////////////////////////
    public ByteArrayOutputStream generateXML(TaxInvoice taxinv) throws EBXMLAppException {

        System.out.println("Marshaller generate xml doc [generateXML(TaxInvoice taxinv)]");
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        
        try {
            System.out.println("  [Generate TAXINV (DOCID: taxinv.getDocid() )]  ");
          
            Mapping     mapping = new Mapping();
            PrintWriter writer = new PrintWriter(bao);
            Marshaller marshaller = new Marshaller(writer);
            
            Properties props = new Properties();
            //System.out.println("MAPPING.URL:"+CommonUtil.getProperty("MAPPING.URL"));
            //URL url = new URL(CommonUtil.getProperty("MAPPING.URL"));
            //mapping.loadMapping(url);

            String MAPPING_PATH = CommonUtil.getProperty("MAPPING.PATH");
            System.out.println("MAPPING.PATH:"+MAPPING_PATH);
            mapping.loadMapping(MAPPING_PATH);
            
            marshaller.setMapping(mapping);
            marshaller.setNamespaceMapping("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            marshaller.setNamespaceMapping("", "http://www.kiec.or.kr/kis");
            marshaller.setNamespaceMapping("ns1", "http://www.kepco.co.kr/e-vatvil/xmldsig#");
            marshaller.setNamespaceMapping("ns2", "http://www.kepco.co.kr/e-vatvil/extension#");
            marshaller.setSchemaLocation("http://www.kiec.or.kr/kis http://www.kiec.or.kr/kis/TaxInvoice.xsd http://www.kepco.co.kr/e-vatvil/xmldsig# http://edi.kepco.net/kepcobill/xsd/xmldsig/kepco-xmldsig-schema.xsd http://www.kepco.co.kr/e-vatvil/extension# http://edi.kepco.net/kepcobill/xsd/extension/kepco-extension-schema.xsd");
            
            marshaller.setEncoding("euc-kr");
            marshaller.marshal(taxinv);
            
            byte[] nomalized_byte = nomalize(bao.toByteArray());
            bao = new ByteArrayOutputStream();
            bao.write(nomalized_byte, 0, nomalized_byte.length);
                         
            System.out.println("  TaxinvUtil[generateTaxinv(TaxInvoice taxinv)]  ends ...    ");
            return bao;
        } catch (MarshalException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "generateXML(TaxInvoice taxinv)", "MarshalException", e.getMessage());
        } catch (ValidationException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "generateXML(TaxInvoice taxinv)", "ValidationException", e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "generateXML(TaxInvoice taxinv)", "IOException", e.getMessage());
        } catch (MappingException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "generateXML(TaxInvoice taxinv)", "MappingException", e.getMessage());
        }
      
    }

    // ////////////////////////////////////////////////////////////////
    // TaxInvoice Object(Castor) <== 언마샬링 (XML InputStream)
    // ////////////////////////////////////////////////////////////////
    public TaxInvoice getTaxinv(InputStream xml_in) throws EBXMLAppException {
        System.out.println("  TaxinvUtil[getTaxinv(InputStream xml_in)]  starts ...    ");
        try {
            // Unmarshaller taxinvUnmar = new Unmarshaller(TaxInvoice.class);
            TaxInvoice taxinvoice = (TaxInvoice) taxinvUnmar.unmarshal(new InputSource(xml_in));
            System.out.println("  TaxinvUtil[getTaxinv(InputStream xml_in)]  ends ...    ");
            return taxinvoice;
        } catch (MarshalException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "getTaxinv(InputStream in)", "MarshalException", e.getMessage());
        } catch (ValidationException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "getTaxinv(InputStream in)", "ValidationException", e.getMessage());
        }
    }

    public TaxInvoice getTaxrsp(InputStream xml_in) throws EBXMLAppException {
        System.out.println("  TaxinvUtil[getTaxrsp(InputStream xml_in)]  starts ...    ");
        try {
            TaxInvoice taxinvoice = (TaxInvoice) taxinvUnmar.unmarshal(new InputSource(xml_in));
            System.out.println("  TaxinvUtil[getTaxrsp(InputStream xml_in)]  ends ...    ");
            return taxinvoice;
        } catch (MarshalException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "getTaxrsp(InputStream in)", "MarshalException", e.getMessage());
        } catch (ValidationException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "getTaxrsp(InputStream in)", "ValidationException", e.getMessage());
        }
    }

    private String toKor(String str) {
        try {
            if (str == null)
                return str;
            // In case of DBMS is Oracle, Do not following statement
            // else str = new String(str.trim().getBytes("8859_1"),"KSC5601");
        } catch (/* java.io.UnsupportedEncodingException */Exception uee) {
        }
        return str;
    }

    private String toUni(String str) {
        try {
            if (str == null)
                return str;
            // In case of DBMS is Oracle, Do not following statement
            // else str = new String(str.trim().getBytes("KSC5601"),"8859_1");
        } catch (/* java.io.UnsupportedEncodingException */Exception uee) {
        }
        return str;
    }

    public String readStream(InputStream in) throws EBXMLAppException {

        StringBuffer sbuf = new StringBuffer();
        try {
            byte[] buf = new byte[8 * 1024];
            int result;
            do {
                result = in.read(buf, 0, buf.length); // this.readLine() does
                                                        // +=
                if (result != -1) {
                    sbuf.append(new String(buf, 0, result)); // ,
                    // "ISO-8859-1"));
                }
            } while (result != -1); // loop only if the buffer was filled

            System.out.println(sbuf.toString());
            return sbuf.toString();

        } catch (IOException e) {
            System.out.println(e);
            throw new EBXMLAppException("TaxinvUtil", "readStream(InputStream in)", "IOException", e.getMessage());
        }

    }

    public byte[] nomalize(byte[] xml_byte) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        String message = "";
        try {
            builder = factory.newDocumentBuilder();
            factory.setIgnoringElementContentWhitespace(true);
            Document document = builder.parse(new InputSource(new ByteArrayInputStream(xml_byte)));
            Element root = document.getDocumentElement();
            root.setAttribute("revision", "2.0");
            OutputFormat format = new OutputFormat(document, "EUC-KR", true);
            StringWriter stringOut = new StringWriter();
            XMLSerializer serial = new XMLSerializer(stringOut, format);
            serial.asDOMSerializer();
            serial.serialize(document.getDocumentElement());
            message = stringOut.toString();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message.getBytes();
    }

}
