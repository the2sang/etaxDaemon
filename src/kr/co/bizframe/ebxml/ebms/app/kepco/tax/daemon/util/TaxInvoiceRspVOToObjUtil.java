/*
 * Created on 2005. 5. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//package kr.co.bizframe.ebxml.app.kepco.xmledi.server.xml.tr;
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util;

/*
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.ApplicationArea;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Creation_DateTime;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.DataArea;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.DocumentReferences;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Document_DateTime;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Document_Identifier;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Extension;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Header;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.InvoiceDocumentReference;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Master_Info;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.OtherDocumentReference;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Parties;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Party_Identifier;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Response_Info;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.ServiceID_Identifier;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.Signature;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.SupplierParty;
import kr.co.bizframe.ebxml.app.kepco.xmledi.binding.tx.TaxInvoice;
*/
import java.util.ArrayList;
import java.util.Date;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.*;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.Master_Info;
//import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.Master_Info_Type;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.Response_Info;
//import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.Response_Info_Type;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLineVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMainVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMetaVO;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvRspVO;

/**
 * @author shin sung uk
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TaxInvoiceRspVOToObjUtil {
	TaxInvoice taxRsp = null;

	TaxInvRspVO trVO = null;

    public TaxInvoiceRspVOToObjUtil(TaxInvRspVO trVO) {
    	taxRsp = new TaxInvoice();
    	
        this.trVO = trVO;
        setDataArea(taxRsp, trVO);
    }

    public TaxInvoice getTaxInvoiceResponse() {
        return taxRsp;
    }

    private void setDataArea(TaxInvoice taxRsp, TaxInvRspVO trVO) {
    	taxRsp.setRevision("2.0");
    	DataArea dArea = new DataArea();
    	Header head = new Header();
    	Extension extentsion = new Extension();
    	
    	Master_Info mstInfo = new Master_Info();
    	Document_DateTime docDate = new Document_DateTime();

    	head.setManagement_Identifier(trVO.getManagement_id());
    	mstInfo.setTaxInvoice_Revision(trVO.getSeqno());
    	    	
    	extentsion.setMaster_Info(mstInfo);
    	//docDate.setContent(trVO.getBldat());
    	
    	docDate.setContent(trVO.getIssue_day());
  
    	head.setExtension(extentsion);
    	head.setDocument_DateTime(docDate);
    	dArea.setHeader(head);
    	
    	ApplicationArea appArea = new ApplicationArea(); 	
    	String nowDate = CommonUtil.getCurrentTimeFormat("yyyyMMdd");
    	Creation_DateTime cd = new Creation_DateTime();
    	cd.setContent(nowDate);
    	
    	appArea.setCreation_DateTime(cd);
    	appArea.addSignature(new Signature());
    	ServiceID_Identifier si = new ServiceID_Identifier();
    	si.setContent("1208200052");
    	appArea.setServiceID_Identifier(si);
    	
/*/		승인번호    	
 */	    
	    if (trVO.getIssue_id()!=null && !trVO.getIssue_id().equals("")) {
    		DocumentReferences documentRef =  new DocumentReferences();
	    	InvoiceDocumentReference invoiceDocRef = new InvoiceDocumentReference();	    	
	    	
	    	Document_Identifier invoice_Id = new Document_Identifier();	    	
	    	invoice_Id.setContent(trVO.getIssue_id());
	    	invoiceDocRef.setDocument_Identifier(invoice_Id);	    	
	    	
	    	//세금계산서 국세청신고완료일시 2013.04.22 장지호
	    	Document_DateTime Esero_Finish_Ts = new Document_DateTime();
	    	Esero_Finish_Ts.setContent(trVO.getEsero_finish_ts());
	    	invoiceDocRef.setDocument_DateTime(Esero_Finish_Ts);
	    	
	    	documentRef.setInvoiceDocumentReference(invoiceDocRef);
	    	head.setDocumentReferences(documentRef);
	    	
	    	
    	}
	    
	    Response_Info respInfo = new Response_Info();   
    	respInfo.setStatus(trVO.getStatus());
    	respInfo.setStatus_Desc(trVO.getStatus_txt());
    	respInfo.setJob_Define(trVO.getExt_system_type());
    	respInfo.setIs_Delete(trVO.getDel_flag());
    	respInfo.setDelay_Desc(trVO.getDelay_reason());
    	respInfo.setDelay_Days(trVO.getDelay_days());
    	respInfo.setDelay_Penalty_Rate(trVO.getDelay_ratio());
    	respInfo.setDelay_Penalty_Amount(trVO.getDelay_penalty());
    	respInfo.setPredict_Return_Payment(trVO.getReturned_payment());
    	respInfo.setPredict_Return_DateTime(trVO.getReturned_datetime());
    	    	
    	extentsion.setResponse_Info(respInfo);
    	head.setExtension(extentsion);
    	
    	Parties parties = new Parties();
    	parties.setSupplierParty(new SupplierParty());
    	parties.getSupplierParty().setParty_Identifier(new Party_Identifier());
    	parties.getSupplierParty().getParty_Identifier().setContent(trVO.getSup_code());
    	
    	head.setParties(parties);
    	taxRsp.setDataArea(dArea);
    	taxRsp.setApplicationArea(appArea);    	
    }
}
