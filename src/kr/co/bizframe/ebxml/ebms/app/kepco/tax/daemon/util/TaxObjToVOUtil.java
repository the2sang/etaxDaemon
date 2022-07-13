/*
 * Created on 2005. 8. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util;

import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.*;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLineVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMainVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMetaVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxObjToVOUtil {
    TaxInvoiceVO taxVO = null;
    
    
    public TaxObjToVOUtil(TaxInvoice tax) {
        this.taxVO = new TaxInvoiceVO();
        this.taxVO.setUuid(tax.getApplicationArea().getExtension().getDoc_Info().getDoc_ID());
        setApplicationArea(tax, taxVO.getMeta());
        setHeader(tax, taxVO.getMain(), taxVO.getMeta());
        setLineList(tax, taxVO.getLine());
        setSummary(tax, taxVO.getMain());
    }
    
    public TaxInvoiceVO getVO(){
        return this.taxVO;
    }
    
    private void setSummary(TaxInvoice tax, TaxMainVO vo) {
        Summary info = tax.getDataArea().getSummary();
        vo.setCharge_amt(info.getCharge_Amount().getContent().toString());
        vo.setTot_tax_amt(info.getTotalTax_Amount().getContent().toString());
        vo.setTot_quantity(info.getTotal_Quantity().getContent().toString());
        vo.setTot_amt(info.getTotal_Amount().getContent().toString());
        setPayment(info, vo);
    }
/**
 * 10  현금
 * 20  수표
 * 2AA 어음
 * 30  외상
*/
    private void setPayment(Summary info,  TaxMainVO vo) {
        for(int i=0; i<info.getPaymentMethodCount(); i++){
            PaymentMethod payment = info.getPaymentMethod(i);
            String code = payment.getPaymentMethod_Code().toString();
            System.out.println(code+":");
            System.out.println("cash:"+payment.getDomesticCurrency_Amount().getContent().toString());
            if(code.equals("10")){
                vo.setPayment_cash_dc_amt(payment.getDomesticCurrency_Amount().getContent().toString());
                vo.setPayment_cash_fc_amt(payment.getForeignCurrency_Amount().getContent().toString());
            }else if(code.equals("20")){
                vo.setPayment_check_dc_amt(payment.getDomesticCurrency_Amount().getContent().toString());
                vo.setPayment_check_fc_amt(payment.getForeignCurrency_Amount().getContent().toString());
            }else if(code.equals("2AA")){
                vo.setPayment_bill_dc_amt(payment.getDomesticCurrency_Amount().getContent().toString());
                vo.setPayment_bill_fc_amt(payment.getForeignCurrency_Amount().getContent().toString());
            }else if(code.equals("30")){
                vo.setPayment_credit_dc_amt(payment.getDomesticCurrency_Amount().getContent().toString());
                vo.setPayment_credit_fc_amt(payment.getForeignCurrency_Amount().getContent().toString());
            }
        }
        
    }


    private ArrayList setLineList(TaxInvoice tax, ArrayList list) {
        DataArea info = tax.getDataArea();
        for (int i = 0; i < info.getLineCount(); i++) {
            TaxLineVO vo = setLine(info.getLine(i), new TaxLineVO());
            list.add(i, vo);
        }
        return list;
    }

    private TaxLineVO setLine(Line line, TaxLineVO vo) {
        vo.setLine_num(line.getLine_Number().getContent().toString());
        if (line.getTaxItem().getItem_NameCount() == 1)
            vo.setName(line.getTaxItem().getItem_Name(0).getContent());
        if (line.getTaxItem().getDefinition_TextCount() == 1)
            vo.setDefine_txt(line.getTaxItem().getDefinition_Text(0).getContent());
        vo.setTrans_date(line.getTransaction_DateTime().getContent());
        vo.setQuantity(line.getItem_Quantity().getContent().toString());
        vo.setSub_tot_quantity(line.getSubTotalItem_Quantity().getContent().toString());
        vo.setAmt(line.getItem_Amount().getContent().toString());
        vo.setBasis_amt(line.getItem_Basis_Amount().getContent().toString());
        vo.setSub_tot_amt(line.getSubTotalItem_Amount().getContent().toString());
        vo.setTax_amt(line.getTax_Amount().getContent().toString());
        vo.setTax_sub_tot_amt(line.getSubTotalTax_Amount().getContent().toString());
        if (line.getDescription_TextCount() == 1)
            vo.setLine_desc(line.getDescription_Text(0).getContent());
        return vo;

    }
    
    private void setHeader(TaxInvoice tax, TaxMainVO vo, TaxMetaVO metaVO) {
        Header info = tax.getDataArea().getHeader();
        vo.setDoc_date(info.getDocument_DateTime().getContent());
        vo.setDoc_desc(info.getDescription_Text(0).getContent());
        vo.setVolum_id(info.getVolume_Identifier().getContent());
        vo.setIssue_id(info.getIssue_Identifier().getContent());
        vo.setSeq_id(info.getSequence_Identifier().getContent());
        vo.setManagement_id(info.getManagement_Identifier());
        vo.setBlank_num(info.getBlank_Number());
        vo.setDoc_type_code(info.getTaxInvoice_Type_Code().toString());
        vo.setBusiness_type_code(info.getBusiness_Type_Code().toString());
        vo.setDemand_type(info.getTax_Demand_Indicator().toString());
        vo.setTot_improt_cnt(info.getTotalImport_Quantity().getContent().toString());
        
// 2006.04.28 이제중
        vo.setRef_inv_doc_id(info.getDocumentReferences().getInvoiceDocumentReference().getDocument_Identifier().getContent());
        vo.setRef_inv_doc_date(info.getDocumentReferences().getInvoiceDocumentReference().getDocument_DateTime().getContent());
        vo.setRef_other_doc_id(info.getDocumentReferences().getOtherDocumentReference().getDocument_Identifier().getContent());
        vo.setRef_pur_ord_id(info.getDocumentReferences().getPurchaseOrderReference().getDocument_Identifier().getContent());                
        vo.setImport_reg_id(info.getImportDeclaration_Identifier().getContent());
        
        
        SupplierParty supplier_party = tax.getDataArea().getHeader().getParties().getSupplierParty();
        vo.setSupplier_id(supplier_party.getParty_Identifier().getContent());
        if (supplier_party.getParty_NameCount() == 1)
            vo.setSupplier_president_name(supplier_party.getParty_Name(0).getContent());
        if (supplier_party.getBusiness().getOrganization_IdentifierCount() == 1)
            vo.setSupplier_biz_id(supplier_party.getBusiness().getOrganization_Identifier(0).getContent());
        vo.setSupplier_name(supplier_party.getBusiness().getOrganization_Name().getContent());
        vo.setSupplier_biz_type(supplier_party.getBusiness().getBusinessType_Name().getContent());
        vo.setSupplier_biz_class(supplier_party.getBusiness().getBusinessClassification_Name().getContent());
        if (supplier_party.getAddressCount() == 1)
            vo.setSupplier_addr(supplier_party.getAddress(0).getAddress_Line1_Text().getContent());
       
        Contact supplier_contact = supplier_party.getContacts().getContact();
        if(supplier_contact.getDepartmentCount()==1)
            vo.setSupplier_contactor_dept(supplier_contact.getDepartment(0).getDepartment_Name(0).getContent());
        if(supplier_contact.getEmail_TextCount()==1)
            vo.setSupplier_contactor_email(supplier_contact.getEmail_Text(0).getContent());
        if(supplier_contact.getPersonCount()==1)
            vo.setSupplier_contactor_name(supplier_contact.getPerson(0).getPerson_Name().getContent());
        if(supplier_contact.getTelephone_TextCount()==1)
            vo.setSupplier_contactor_tel(supplier_contact.getTelephone_Text(0).getContent());
        
        
        BuyerParty buyer_party = tax.getDataArea().getHeader().getParties().getBuyerParty();
        vo.setBuyer_id(buyer_party.getParty_Identifier().getContent());
        if (buyer_party.getParty_NameCount() == 1)
            vo.setBuyer_president_name(buyer_party.getParty_Name(0).getContent());
        if (buyer_party.getBusiness().getOrganization_IdentifierCount() == 1)
            vo.setBuyer_biz_id(buyer_party.getBusiness().getOrganization_Identifier(0).getContent());
        vo.setBuyer_name(buyer_party.getBusiness().getOrganization_Name().getContent());
        vo.setBuyer_biz_type(buyer_party.getBusiness().getBusinessType_Name().getContent());
        vo.setBuyer_biz_class(buyer_party.getBusiness().getBusinessClassification_Name().getContent());
        if (buyer_party.getAddressCount() == 1)
            vo.setBuyer_addr(buyer_party.getAddress(0).getAddress_Line1_Text().getContent());
       
        Contact buyer_contact = buyer_party.getContacts().getContact();
        if(buyer_contact.getDepartmentCount()==1)
            vo.setBuyer_contactor_dept(buyer_contact.getDepartment(0).getDepartment_Name(0).getContent());
        if(buyer_contact.getEmail_TextCount()==1)
            vo.setBuyer_contactor_email(buyer_contact.getEmail_Text(0).getContent());
        if(buyer_contact.getPersonCount()==1)
            vo.setBuyer_contactor_name(buyer_contact.getPerson(0).getPerson_Name().getContent());
        if(buyer_contact.getTelephone_TextCount()==1)
            vo.setBuyer_contactor_tel(buyer_contact.getTelephone_Text(0).getContent());

//      ERP 연계 추가 항목 
        System.out.println("############################33");
        System.out.println("metaVO.getConstruct_no()::::::::::"+metaVO.getConstruct_no());
        Doc_Info docInfo = info.getExtension().getDoc_Info();
        if(info.getExtension()!=null && info.getExtension().getDoc_Info() != null){
	        metaVO.setInspector_id(docInfo.getExt_Inspector_Sabun());
	        metaVO.setContractor_id(docInfo.getExt_Contractor_Sabun());
	        metaVO.setContract_no(docInfo.getExt_Contract_No());
	        metaVO.setConstruct_no(docInfo.getExt_Construct_No());
	        metaVO.setKisung_chg_no(docInfo.getExt_Kisung_Degree_No());
	        metaVO.setReport_type(docInfo.getExt_Report_Type_Code());
	        metaVO.setExt_status_code(docInfo.getExt_Doc_Process_Status());
	        metaVO.setExt_doc_result_msg(docInfo.getExt_Doc_result_Msg());
	        metaVO.setExt_doc_reissue_msg(docInfo.getExt_Doc_reissue_Msg());
        }
    }

    private void setApplicationArea(TaxInvoice tax, TaxMetaVO vo) {
        ApplicationArea info = tax.getApplicationArea();
        vo.setCreate_time(info.getCreation_DateTime().getContent());
        vo.setService_id(info.getServiceID_Identifier().getContent());
        if(info.getSignatureCount()>0)
            vo.setSignature(new String(info.getSignature(0).getSignatureValue().getContent()));
        Doc_Info doc_info = info.getExtension().getDoc_Info();
        
        vo.setUuid(doc_info.getDoc_ID());
        vo.setReceiver_id(doc_info.getReceiver_ID());
        vo.setSender_id(doc_info.getSender_ID());
        vo.setReceiver_comp_id(doc_info.getReceiver_Comp_ID());
        vo.setSender_comp_id(doc_info.getSender_Comp_ID());
        vo.setExt_doc_result_status(doc_info.getExt_Doc_result_Status());

        vo.setExt_system_type(doc_info.getExt_System_Type());
        vo.setExt_voucher_type(doc_info.getExt_Voucher_Type());
        vo.setExt_purchase_type(doc_info.getExt_Purchase_Type());
        vo.setExt_decuction_detail(doc_info.getExt_Decuction_Detail());
        vo.setExt_decuction_status(doc_info.getExt_Decuction_Status());
        vo.setExt_buyer_sabun(doc_info.getExt_Buyer_Sabun());
        vo.setExt_valid_sdate(doc_info.getExt_Valid_Sdate());
        vo.setExt_valid_edate(doc_info.getExt_Valid_Edate());
        vo.setExt_voucher_buseo(doc_info.getExt_Voucher_Buseo());
        vo.setExt_voucher_yearMonth(doc_info.getExt_Voucher_YearMonth());
        vo.setExt_voucher_seq(doc_info.getExt_Voucher_Seq());
        vo.setExt_process_status_code(doc_info.getExt_Status_Code());
        vo.setExt_consign_construct_no(doc_info.getExt_Consign_Construct_No());
        vo.setExt_consign_construct_amount(doc_info.getExt_Consign_Construct_Amount());
        vo.setExt_revenue_construct_no(doc_info.getExt_Revenue_Construct_No());
        vo.setExt_revenue_construct_amount(doc_info.getExt_Revenue_Construct_Amount());
        
        info.getExtension().setDoc_Info(doc_info);
    }
    
    
}
