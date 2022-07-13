/*
 * Created on 2006. 1. 11.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.run;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.ChangeStatus;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxFinanceManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxReceiptManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxFinanceContectVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxReceiptVO;
/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PublishTaxinvoice {
 
	private static Logger logger = Logger.getLogger(PublishTaxinvoice.class);	
	
    public String publishData(Hashtable data) throws SQLException, TaxInvoiceException{
    	logger.debug("역발행 세금계산서 저장  publishData(ArrayList data)" );
         
    	String errMsg = "";
    	
    	try { 
    		TaxInvoiceVO vo = (TaxInvoiceVO)data.get("taxVO");
           logger.debug("uuid " + vo.getUuid());
           
           vo.getMeta().setDoc_state("WM");
//           vo.getMeta().setContractor_id(financeVO.getId());
           vo.getMeta().setInspector_id(vo.getMain().getBuyer_id());
           TaxManagementDao dao = new TaxManagementDao();
           vo = dao.saveTaxinvoice(vo);
           vo.getMeta().setDoc_state("CFR");
           TaxFinanceContectVO financeVO = (TaxFinanceContectVO)data.get("financeVO");
           TaxFinanceManagementDao fdao = new TaxFinanceManagementDao();
           financeVO.setUuid(vo.getUuid());
           fdao.save(financeVO);
           
           
           //20180321 유종일 한전 담당자 전화번호 추가 
           vo.setSms_yn((String)data.get("sms_yn"));
	   	   vo.setSms_sender_name((String)data.get("sms_name"));
	   	   vo.setSms_sender_tel((String)data.get("sms_tel"));
	   		
	   		
           ArrayList detailArry = (ArrayList)data.get("detailArry");
           TaxReceiptManagementDao rdao = new TaxReceiptManagementDao();
	   		for (int t = 0; t < detailArry.size(); t++) {
				TaxReceiptVO rvo = new TaxReceiptVO();
				rvo = (TaxReceiptVO)detailArry.get(t);
				rdao.insert(rvo);
	   		}	
	      
           ChangeStatus changeStatus = new ChangeStatus();
           changeStatus.sendPublishByBuyer(vo);
           
           
       
    	} catch (Exception e) {
    		errMsg = "세금계산서 저장 중 오류 발생";
            logger.debug("publishData Error.. " + e);
        }    
           
       logger.debug("역발행 세금계산서 저장  publishData(ArrayList data)");
       
       return errMsg; 
   }
}
