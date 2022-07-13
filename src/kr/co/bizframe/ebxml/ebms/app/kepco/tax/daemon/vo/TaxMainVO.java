/*
 * Created on 2005. 8. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

import java.io.Serializable;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TaxMainVO extends Object implements Serializable {

	private String uuid = "";

	private String management_id = "";

	private String doc_date = "";

	private String blank_num = "";

	private String doc_type_code = "";

	private String business_type_code = "";

	private String demand_type = "";

	private String volum_id = "";

	private String issue_id = "";

	private String seq_id = "";

	private String ref_pur_ord_id = "";

	private String ref_pur_ord_date = "";

	private String ref_inv_doc_id = "";

	private String ref_inv_doc_date = "";

	private String ref_other_doc_id = "";

	private String doc_desc = "";

	private String import_reg_id = "";

	private String batch_issue_sdate = "";

	private String batch_issue_edate = "";

	private String tot_improt_cnt = "";

	private String supplier_biz_id = "";

	private String supplier_id = "";

	private String supplier_name = "";

	private String supplier_president_name = "";

	private String supplier_addr = "";

	private String supplier_biz_type = "";

	private String supplier_biz_class = "";

	private String supplier_contactor_dept = "";

	private String supplier_contactor_name = "";

	private String supplier_contactor_tel = "";

	private String supplier_contactor_email = "";

	private String buyer_biz_id = "";

	private String buyer_id = "";

	private String buyer_name = "";

	private String buyer_president_name = "";

	private String buyer_addr = "";

	private String buyer_biz_type = "";

	private String buyer_biz_class = "";

	private String buyer_contactor_dept = "";

	private String buyer_contactor_name = "";

	private String buyer_contactor_tel = "";

	private String buyer_contactor_email = "";

	private String charge_amt = "";

	private String tot_tax_amt = "";

	private String tot_fore_amt = "";

	private String tot_quantity = "";

	private String tot_amt = "";

	private String payment_cash_dc_amt = "";

	private String payment_cash_fc_amt = "";

	private String payment_check_dc_amt = "";

	private String payment_check_fc_amt = "";

	private String payment_bill_dc_amt = "";

	private String payment_bill_fc_amt = "";

	private String payment_credit_dc_amt = "";

	private String payment_credit_fc_amt = "";
	//	 20091109 세금계산서 추가
    private String nts_issue_id 	= "";			// 국세청연계승인번호
    private String bef_nts_issue_id = "";			// 국세청수정승인번호
    private String modify_code 		= "";			// 수정사유코드
    private String supplier_biz_cd 	= "";			// 공급자종사업장번호
    private String buyer_biz_cd 	= "";			// 공급받는자종사업장번호
    private String doc_type_detail	= "";			// 세금계산서종류(3.0)
    // 200912
    private String issue_dt = ""; // 발행일시
    // 20100204
    private String zbran_name = ""; // 지점명
    
    private String asp_issue_id = "";  //외부발행 세금계산서
    
    /**
     * getZbran_name
     * @return
     */
	public String getZbran_name() {
		return zbran_name;
	}
	
	/**
	 * setZbran_name
	 * @param zbranName
	 */
	public void setZbran_name(String zbranName) {
		zbran_name = zbranName;
	}

	/**
	 * @return Returns the charge_amt.
	 */
	public String getCharge_amt() {
		return charge_amt;
	}

	/**
	 * @param charge_amt
	 *            The charge_amt to set.
	 */
	public void setCharge_amt(String charge_amt) {
		this.charge_amt = charge_amt;
	}

	/**
	 * @return Returns the tot_amt.
	 */
	public String getTot_amt() {
		return tot_amt;
	}

	/**
	 * @param tot_amt
	 *            The tot_amt to set.
	 */
	public void setTot_amt(String tot_amt) {
		this.tot_amt = tot_amt;
	}

	/**
	 * @return Returns the tot_fore_amt.
	 */
	public String getTot_fore_amt() {
		return tot_fore_amt;
	}

	/**
	 * @param tot_fore_amt
	 *            The tot_fore_amt to set.
	 */
	public void setTot_fore_amt(String tot_fore_amt) {
		this.tot_fore_amt = tot_fore_amt;
	}

	/**
	 * @return Returns the tot_quantity.
	 */
	public String getTot_quantity() {
		return tot_quantity;
	}

	/**
	 * @param tot_quantity
	 *            The tot_quantity to set.
	 */
	public void setTot_quantity(String tot_quantity) {
		this.tot_quantity = tot_quantity;
	}

	/**
	 * @return Returns the tot_tax_amt.
	 */
	public String getTot_tax_amt() {
		return tot_tax_amt;
	}

	/**
	 * @param tot_tax_amt
	 *            The tot_tax_amt to set.
	 */
	public void setTot_tax_amt(String tot_tax_amt) {
		this.tot_tax_amt = tot_tax_amt;
	}

	/**
	 * @return Returns the batch_issue_edate.
	 */
	public String getBatch_issue_edate() {
		return batch_issue_edate;
	}

	/**
	 * @param batch_issue_edate
	 *            The batch_issue_edate to set.
	 */
	public void setBatch_issue_edate(String batch_issue_edate) {
		this.batch_issue_edate = batch_issue_edate;
	}

	/**
	 * @return Returns the batch_issue_sdate.
	 */
	public String getBatch_issue_sdate() {
		return batch_issue_sdate;
	}

	/**
	 * @param batch_issue_sdate
	 *            The batch_issue_sdate to set.
	 */
	public void setBatch_issue_sdate(String batch_issue_sdate) {
		this.batch_issue_sdate = batch_issue_sdate;
	}

	/**
	 * @return Returns the blank_num.
	 */
	public String getBlank_num() {
		return blank_num;
	}

	/**
	 * @param blank_num
	 *            The blank_num to set.
	 */
	public void setBlank_num(String blank_num) {
		this.blank_num = blank_num;
	}

	/**
	 * @return Returns the buyer_addr.
	 */
	public String getBuyer_addr() {
		return buyer_addr;
	}

	/**
	 * @param buyer_addr
	 *            The buyer_addr to set.
	 */
	public void setBuyer_addr(String buyer_addr) {
		this.buyer_addr = buyer_addr;
	}

	/**
	 * @return Returns the buyer_biz_class.
	 */
	public String getBuyer_biz_class() {
		return buyer_biz_class;
	}

	/**
	 * @param buyer_biz_class
	 *            The buyer_biz_class to set.
	 */
	public void setBuyer_biz_class(String buyer_biz_class) {
		this.buyer_biz_class = buyer_biz_class;
	}

	/**
	 * @return Returns the buyer_biz_id.
	 */
	public String getBuyer_biz_id() {
		return buyer_biz_id;
	}

	/**
	 * @param buyer_biz_id
	 *            The buyer_biz_id to set.
	 */
	public void setBuyer_biz_id(String buyer_biz_id) {
		this.buyer_biz_id = buyer_biz_id;
	}

	/**
	 * @return Returns the buyer_biz_type.
	 */
	public String getBuyer_biz_type() {
		return buyer_biz_type;
	}

	/**
	 * @param buyer_biz_type
	 *            The buyer_biz_type to set.
	 */
	public void setBuyer_biz_type(String buyer_biz_type) {
		this.buyer_biz_type = buyer_biz_type;
	}

	/**
	 * @return Returns the buyer_contactor_dept.
	 */
	public String getBuyer_contactor_dept() {
		return buyer_contactor_dept;
	}

	/**
	 * @param buyer_contactor_dept
	 *            The buyer_contactor_dept to set.
	 */
	public void setBuyer_contactor_dept(String buyer_contactor_dept) {
		this.buyer_contactor_dept = buyer_contactor_dept;
	}

	/**
	 * @return Returns the buyer_contactor_email.
	 */
	public String getBuyer_contactor_email() {
		return buyer_contactor_email;
	}

	/**
	 * @param buyer_contactor_email
	 *            The buyer_contactor_email to set.
	 */
	public void setBuyer_contactor_email(String buyer_contactor_email) {
		this.buyer_contactor_email = buyer_contactor_email;
	}

	/**
	 * @return Returns the buyer_contactor_name.
	 */
	public String getBuyer_contactor_name() {
		return buyer_contactor_name;
	}

	/**
	 * @param buyer_contactor_name
	 *            The buyer_contactor_name to set.
	 */
	public void setBuyer_contactor_name(String buyer_contactor_name) {
		this.buyer_contactor_name = buyer_contactor_name;
	}

	/**
	 * @return Returns the buyer_contactor_tel.
	 */
	public String getBuyer_contactor_tel() {
		return buyer_contactor_tel;
	}

	/**
	 * @param buyer_contactor_tel
	 *            The buyer_contactor_tel to set.
	 */
	public void setBuyer_contactor_tel(String buyer_contactor_tel) {
		this.buyer_contactor_tel = buyer_contactor_tel;
	}

	/**
	 * @return Returns the buyer_id.
	 */
	public String getBuyer_id() {
		return buyer_id;
	}

	/**
	 * @param buyer_id
	 *            The buyer_id to set.
	 */
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	/**
	 * @return Returns the buyer_name.
	 */
	public String getBuyer_name() {
		return buyer_name;
	}

	/**
	 * @param buyer_name
	 *            The buyer_name to set.
	 */
	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}

	/**
	 * @return Returns the buyer_president_name.
	 */
	public String getBuyer_president_name() {
		return buyer_president_name;
	}

	/**
	 * @param buyer_president_name
	 *            The buyer_president_name to set.
	 */
	public void setBuyer_president_name(String buyer_president_name) {
		this.buyer_president_name = buyer_president_name;
	}

	/**
	 * @return Returns the demand_type.
	 */
	public String getDemand_type() {
		return demand_type;
	}

	/**
	 * @param demand_type
	 *            The demand_type to set.
	 */
	public void setDemand_type(String demand_type) {
		this.demand_type = demand_type;
	}

	/**
	 * @return Returns the doc_date.
	 */
	public String getDoc_date() {
		return doc_date;
	}

	/**
	 * @param doc_date
	 *            The doc_date to set.
	 */
	public void setDoc_date(String doc_date) {
		this.doc_date = doc_date;
	}

	/**
	 * @return Returns the doc_desc.
	 */
	public String getDoc_desc() {
		return doc_desc;
	}

	/**
	 * @param doc_desc
	 *            The doc_desc to set.
	 */
	public void setDoc_desc(String doc_desc) {
		this.doc_desc = doc_desc;
	}

	/**
	 * @return Returns the doc_type_code.
	 */
	public String getDoc_type_code() {
		return doc_type_code;
	}

	/**
	 * @param doc_type_code
	 *            The doc_type_code to set.
	 */
	public void setDoc_type_code(String doc_type_code) {
		this.doc_type_code = doc_type_code;
	}

	public String getBusiness_type_code() {
		return business_type_code;
	}

	public void setBusiness_type_code(String business_type_code) {
		this.business_type_code = business_type_code;
	}

	/**
	 * @return Returns the import_reg_id.
	 */
	public String getImport_reg_id() {
		return import_reg_id;
	}

	/**
	 * @param import_reg_id
	 *            The import_reg_id to set.
	 */
	public void setImport_reg_id(String import_reg_id) {
		this.import_reg_id = import_reg_id;
	}

	/**
	 * @return Returns the issue_id.
	 */
	public String getIssue_id() {
		return issue_id;
	}

	/**
	 * @param issue_id
	 *            The issue_id to set.
	 */
	public void setIssue_id(String issue_id) {
		this.issue_id = issue_id;
	}

	/**
	 * @return Returns the management_id.
	 */
	public String getManagement_id() {
		return management_id;
	}

	/**
	 * @param management_id
	 *            The management_id to set.
	 */
	public void setManagement_id(String management_id) {
		this.management_id = management_id;
	}

	/**
	 * @return Returns the ref_inv_doc_date.
	 */
	public String getRef_inv_doc_date() {
		return ref_inv_doc_date;
	}

	/**
	 * @param ref_inv_doc_date
	 *            The ref_inv_doc_date to set.
	 */
	public void setRef_inv_doc_date(String ref_inv_doc_date) {
		this.ref_inv_doc_date = ref_inv_doc_date;
	}

	/**
	 * @return Returns the ref_inv_doc_id.
	 */
	public String getRef_inv_doc_id() {
		return ref_inv_doc_id;
	}

	/**
	 * @param ref_inv_doc_id
	 *            The ref_inv_doc_id to set.
	 */
	public void setRef_inv_doc_id(String ref_inv_doc_id) {
		this.ref_inv_doc_id = ref_inv_doc_id;
	}

	/**
	 * @return Returns the ref_other_doc_id.
	 */
	public String getRef_other_doc_id() {
		return ref_other_doc_id;
	}

	/**
	 * @param ref_other_doc_id
	 *            The ref_other_doc_id to set.
	 */
	public void setRef_other_doc_id(String ref_other_doc_id) {
		this.ref_other_doc_id = ref_other_doc_id;
	}

	/**
	 * @return Returns the ref_pur_ord_date.
	 */
	public String getRef_pur_ord_date() {
		return ref_pur_ord_date;
	}

	/**
	 * @param ref_pur_ord_date
	 *            The ref_pur_ord_date to set.
	 */
	public void setRef_pur_ord_date(String ref_pur_ord_date) {
		this.ref_pur_ord_date = ref_pur_ord_date;
	}

	/**
	 * @return Returns the ref_pur_ord_id.
	 */
	public String getRef_pur_ord_id() {
		return ref_pur_ord_id;
	}

	/**
	 * @param ref_pur_ord_id
	 *            The ref_pur_ord_id to set.
	 */
	public void setRef_pur_ord_id(String ref_pur_ord_id) {
		this.ref_pur_ord_id = ref_pur_ord_id;
	}

	/**
	 * @return Returns the seq_id.
	 */
	public String getSeq_id() {
		return seq_id;
	}

	/**
	 * @param seq_id
	 *            The seq_id to set.
	 */
	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}

	/**
	 * @return Returns the supplier_addr.
	 */
	public String getSupplier_addr() {
		return supplier_addr;
	}

	/**
	 * @param supplier_addr
	 *            The supplier_addr to set.
	 */
	public void setSupplier_addr(String supplier_addr) {
		this.supplier_addr = supplier_addr;
	}

	/**
	 * @return Returns the supplier_biz_class.
	 */
	public String getSupplier_biz_class() {
		return supplier_biz_class;
	}

	/**
	 * @param supplier_biz_class
	 *            The supplier_biz_class to set.
	 */
	public void setSupplier_biz_class(String supplier_biz_class) {
		this.supplier_biz_class = supplier_biz_class;
	}

	/**
	 * @return Returns the supplier_biz_id.
	 */
	public String getSupplier_biz_id() {
		return supplier_biz_id;
	}

	/**
	 * @param supplier_biz_id
	 *            The supplier_biz_id to set.
	 */
	public void setSupplier_biz_id(String supplier_biz_id) {
		this.supplier_biz_id = supplier_biz_id;
	}

	/**
	 * @return Returns the supplier_biz_type.
	 */
	public String getSupplier_biz_type() {
		return supplier_biz_type;
	}

	/**
	 * @param supplier_biz_type
	 *            The supplier_biz_type to set.
	 */
	public void setSupplier_biz_type(String supplier_biz_type) {
		this.supplier_biz_type = supplier_biz_type;
	}

	/**
	 * @return Returns the supplier_contactor_dept.
	 */
	public String getSupplier_contactor_dept() {
		return supplier_contactor_dept;
	}

	/**
	 * @param supplier_contactor_dept
	 *            The supplier_contactor_dept to set.
	 */
	public void setSupplier_contactor_dept(String supplier_contactor_dept) {
		this.supplier_contactor_dept = supplier_contactor_dept;
	}

	/**
	 * @return Returns the supplier_contactor_email.
	 */
	public String getSupplier_contactor_email() {
		return supplier_contactor_email;
	}

	/**
	 * @param supplier_contactor_email
	 *            The supplier_contactor_email to set.
	 */
	public void setSupplier_contactor_email(String supplier_contactor_email) {
		this.supplier_contactor_email = supplier_contactor_email;
	}

	/**
	 * @return Returns the supplier_contactor_name.
	 */
	public String getSupplier_contactor_name() {
		return supplier_contactor_name;
	}

	/**
	 * @param supplier_contactor_name
	 *            The supplier_contactor_name to set.
	 */
	public void setSupplier_contactor_name(String supplier_contactor_name) {
		this.supplier_contactor_name = supplier_contactor_name;
	}

	/**
	 * @return Returns the supplier_contactor_tel.
	 */
	public String getSupplier_contactor_tel() {
		return supplier_contactor_tel;
	}

	/**
	 * @param supplier_contactor_tel
	 *            The supplier_contactor_tel to set.
	 */
	public void setSupplier_contactor_tel(String supplier_contactor_tel) {
		this.supplier_contactor_tel = supplier_contactor_tel;
	}

	/**
	 * @return Returns the supplier_id.
	 */
	public String getSupplier_id() {
		return supplier_id;
	}

	/**
	 * @param supplier_id
	 *            The supplier_id to set.
	 */
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	/**
	 * @return Returns the supplier_name.
	 */
	public String getSupplier_name() {
		return supplier_name;
	}

	/**
	 * @param supplier_name
	 *            The supplier_name to set.
	 */
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	/**
	 * @return Returns the supplier_president_name.
	 */
	public String getSupplier_president_name() {
		return supplier_president_name;
	}

	/**
	 * @param supplier_president_name
	 *            The supplier_president_name to set.
	 */
	public void setSupplier_president_name(String supplier_president_name) {
		this.supplier_president_name = supplier_president_name;
	}

	/**
	 * @return Returns the tot_improt_cnt.
	 */
	public String getTot_improt_cnt() {
		return tot_improt_cnt;
	}

	/**
	 * @param tot_improt_cnt
	 *            The tot_improt_cnt to set.
	 */
	public void setTot_improt_cnt(String tot_improt_cnt) {
		this.tot_improt_cnt = tot_improt_cnt;
	}

	/**
	 * @return Returns the uuid.
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            The uuid to set.
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return Returns the volum_id.
	 */
	public String getVolum_id() {
		return volum_id;
	}

	/**
	 * @param volum_id
	 *            The volum_id to set.
	 */
	public void setVolum_id(String volum_id) {
		this.volum_id = volum_id;
	}

	/**
	 * @return Returns the payment_bill_dc_amt.
	 */
	public String getPayment_bill_dc_amt() {
		return payment_bill_dc_amt;
	}

	/**
	 * @param payment_bill_dc_amt
	 *            The payment_bill_dc_amt to set.
	 */
	public void setPayment_bill_dc_amt(String payment_bill_dc_amt) {
		this.payment_bill_dc_amt = payment_bill_dc_amt;
	}

	/**
	 * @return Returns the payment_bill_fc_amt.
	 */
	public String getPayment_bill_fc_amt() {
		return payment_bill_fc_amt;
	}

	/**
	 * @param payment_bill_fc_amt
	 *            The payment_bill_fc_amt to set.
	 */
	public void setPayment_bill_fc_amt(String payment_bill_fc_amt) {
		this.payment_bill_fc_amt = payment_bill_fc_amt;
	}

	/**
	 * @return Returns the payment_cash_dc_amt.
	 */
	public String getPayment_cash_dc_amt() {
		return payment_cash_dc_amt;
	}

	/**
	 * @param payment_cash_dc_amt
	 *            The payment_cash_dc_amt to set.
	 */
	public void setPayment_cash_dc_amt(String payment_cash_dc_amt) {
		this.payment_cash_dc_amt = payment_cash_dc_amt;
	}

	/**
	 * @return Returns the payment_cash_fc_amt.
	 */
	public String getPayment_cash_fc_amt() {
		return payment_cash_fc_amt;
	}

	/**
	 * @param payment_cash_fc_amt
	 *            The payment_cash_fc_amt to set.
	 */
	public void setPayment_cash_fc_amt(String payment_cash_fc_amt) {
		this.payment_cash_fc_amt = payment_cash_fc_amt;
	}

	/**
	 * @return Returns the payment_check_dc_amt.
	 */
	public String getPayment_check_dc_amt() {
		return payment_check_dc_amt;
	}

	/**
	 * @param payment_check_dc_amt
	 *            The payment_check_dc_amt to set.
	 */
	public void setPayment_check_dc_amt(String payment_check_dc_amt) {
		this.payment_check_dc_amt = payment_check_dc_amt;
	}

	/**
	 * @return Returns the payment_check_fc_amt.
	 */
	public String getPayment_check_fc_amt() {
		return payment_check_fc_amt;
	}

	/**
	 * @param payment_check_fc_amt
	 *            The payment_check_fc_amt to set.
	 */
	public void setPayment_check_fc_amt(String payment_check_fc_amt) {
		this.payment_check_fc_amt = payment_check_fc_amt;
	}

	/**
	 * @return Returns the payment_credit_dc_amt.
	 */
	public String getPayment_credit_dc_amt() {
		return payment_credit_dc_amt;
	}

	/**
	 * @param payment_credit_dc_amt
	 *            The payment_credit_dc_amt to set.
	 */
	public void setPayment_credit_dc_amt(String payment_credit_dc_amt) {
		this.payment_credit_dc_amt = payment_credit_dc_amt;
	}

	/**
	 * @return Returns the payment_credit_fc_amt.
	 */
	public String getPayment_credit_fc_amt() {
		return payment_credit_fc_amt;
	}

	/**
	 * @param payment_credit_fc_amt
	 *            The payment_credit_fc_amt to set.
	 */
	public void setPayment_credit_fc_amt(String payment_credit_fc_amt) {
		this.payment_credit_fc_amt = payment_credit_fc_amt;
	}

	public String getBef_nts_issue_id() {
		return bef_nts_issue_id;
	}

	public void setBef_nts_issue_id(String bef_nts_issue_id) {
		this.bef_nts_issue_id = bef_nts_issue_id;
	}

	public String getBuyer_biz_cd() {
		return buyer_biz_cd;
	}

	public void setBuyer_biz_cd(String buyer_biz_cd) {
		this.buyer_biz_cd = buyer_biz_cd;
	}

	public String getDoc_type_detail() {
		return doc_type_detail;
	}

	public void setDoc_type_detail(String doc_type_detail) {
		this.doc_type_detail = doc_type_detail;
	}

	public String getIssue_dt() {
		return issue_dt;
	}

	public void setIssue_dt(String issue_dt) {
		this.issue_dt = issue_dt;
	}

	public String getModify_code() {
		return modify_code;
	}

	public void setModify_code(String modify_code) {
		this.modify_code = modify_code;
	}

	public String getNts_issue_id() {
		return nts_issue_id;
	}

	public void setNts_issue_id(String nts_issue_id) {
		this.nts_issue_id = nts_issue_id;
	}

	public String getSupplier_biz_cd() {
		return supplier_biz_cd;
	}

	public void setSupplier_biz_cd(String supplier_biz_cd) {
		this.supplier_biz_cd = supplier_biz_cd;
	}

	public String getAsp_issue_id() {
		return asp_issue_id;
	}

	public void setAsp_issue_id(String asp_issue_id) {
		this.asp_issue_id = asp_issue_id;
	}
	
	
	
	
}
