/*
 * Created on 2005. 8. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

import java.io.Serializable;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;

/**
 * @author shin sung uk
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TaxMetaVO extends Object implements Serializable {
	private String uuid = "";

	private String create_time = CommonUtil.getCurrentTimeWithSecond();

	private String complete_time = "";

	private String service_id = "1208200052";

	private String signature = "";

	private String user_dn = "";

	private String signcert = "";

	private String doc_state = "";

	private String sender_id = "";

	private String receiver_id = "";

	private String sender_comp_id = "";

	private String receiver_comp_id = "";

	private String ext_doc_ins_date = "";

	private String ext_doc_result_status = "N";

	private String ext_doc_result_msg = "";

	private String ext_doc_reissue_msg = "";

	// private String ext_system_type = "ET1";
	private String ext_system_type = "";

	private String ext_voucher_type = "121";

	private String ext_purchase_type = "213";

	private String ext_decuction_status = "Y";

	private String ext_decuction_detail = "";

	private String ext_buyer_sabun = "";

	private String ext_valid_sdate = "";

	private String ext_valid_edate = "29991231";

	private String ext_voucher_buseo = "";

	private String ext_voucher_yearMonth = "";

	private String ext_voucher_seq = "";

	private String ext_status_code = "";

	private String writer_type = "";

	private String tax_gubun = "";

	// 세금계산서 ERP 연계 추가 항목들
	// 계약자 사번
	private String contractor_id = "";

	// 계약번호
	private String contract_no = "";

	// 공사번호
	private String construct_no = "";

	// 기성차수
	private String kisung_chg_no = "";

	// 보고서 구분
	private String report_type = "";

	// 검수자 사번
	private String inspector_id = "";

	//
	private String ext_process_status_code = "";

	// 한전 , 발전사 코드
	private String comp_code = "";

	// 위탁공사번호
	private String ext_consign_construct_no = "";

	// 위탁공사금액
	private String ext_consign_construct_amount = "";

	// 수익적공사번호
	private String ext_revenue_construct_no = "";

	// 수익적 공사비
	private String ext_revenue_construct_amount = "";

	//2015.12.07 내선계기 메일보낸이를 계약자로 변경 CDH
	private String contractor_email = "";
	
	private String contractor_name = "";
	
	private String contractor_phone = "";
	
	public String getConstruct_no() {
		return construct_no;
	}

	public void setConstruct_no(String construct_no) {
		this.construct_no = construct_no;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public String getContractor_id() {
		return contractor_id;
	}

	public void setContractor_id(String contractor_id) {
		this.contractor_id = contractor_id;
	}

	public String getInspector_id() {
		return inspector_id;
	}

	public void setInspector_id(String inspector_id) {
		this.inspector_id = inspector_id;
	}

	public String getKisung_chg_no() {
		return kisung_chg_no;
	}

	public void setKisung_chg_no(String kisung_chg_no) {
		this.kisung_chg_no = kisung_chg_no;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	/**
	 * @return Returns the ext_status_code.
	 */
	public String getExt_status_code() {
		return ext_status_code;
	}

	/**
	 * @param ext_status_code
	 *            The ext_status_code to set.
	 */
	public void setExt_status_code(String ext_status_code) {
		this.ext_status_code = ext_status_code;
	}

	/**
	 * @return Returns the ext_voucher_buseo.
	 */
	public String getExt_voucher_buseo() {
		return ext_voucher_buseo;
	}

	/**
	 * @param ext_voucher_buseo
	 *            The ext_voucher_buseo to set.
	 */
	public void setExt_voucher_buseo(String ext_voucher_buseo) {
		this.ext_voucher_buseo = ext_voucher_buseo;
	}

	/**
	 * @return Returns the ext_voucher_seq.
	 */
	public String getExt_voucher_seq() {
		return ext_voucher_seq;
	}

	/**
	 * @param ext_voucher_seq
	 *            The ext_voucher_seq to set.
	 */
	public void setExt_voucher_seq(String ext_voucher_seq) {
		this.ext_voucher_seq = ext_voucher_seq;
	}

	/**
	 * @return Returns the ext_voucher_yearMonth.
	 */
	public String getExt_voucher_yearMonth() {
		return ext_voucher_yearMonth;
	}

	/**
	 * @param ext_voucher_yearMonth
	 *            The ext_voucher_yearMonth to set.
	 */
	public void setExt_voucher_yearMonth(String ext_voucher_yearMonth) {
		this.ext_voucher_yearMonth = ext_voucher_yearMonth;
	}

	/**
	 * @return Returns the writer_type.
	 */
	public String getWriter_type() {
		return writer_type;
	}

	/**
	 * @param writer_type
	 *            The writer_type to set.
	 */
	public void setWriter_type(String writer_type) {
		this.writer_type = writer_type;
	}

	/**
	 * @return Returns the complete_time.
	 */
	public String getComplete_time() {
		return complete_time;
	}

	/**
	 * @param complete_time
	 *            The complete_time to set.
	 */
	public void setComplete_time(String complete_time) {
		this.complete_time = complete_time;
	}

	/**
	 * @return Returns the create_time.
	 */
	public String getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time
	 *            The create_time to set.
	 */
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	/**
	 * @return Returns the doc_state.
	 */
	public String getDoc_state() {
		return doc_state;
	}

	/**
	 * @param doc_state
	 *            The doc_state to set.
	 */
	public void setDoc_state(String doc_state) {
		this.doc_state = doc_state;
	}

	/**
	 * @return Returns the receiver_id.
	 */
	public String getReceiver_id() {
		return receiver_id;
	}

	/**
	 * @param receiver_id
	 *            The receiver_id to set.
	 */
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}

	/**
	 * @return Returns the sender_id.
	 */
	public String getSender_id() {
		return sender_id;
	}

	/**
	 * @param sender_id
	 *            The sender_id to set.
	 */
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	/**
	 * @return Returns the service_id.
	 */
	public String getService_id() {
		return service_id;
	}

	/**
	 * @param service_id
	 *            The service_id to set.
	 */
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	/**
	 * @return Returns the signature.
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param signature
	 *            The signature to set.
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @return Returns the signcert.
	 */
	public String getSigncert() {
		return signcert;
	}

	/**
	 * @param signcert
	 *            The signcert to set.
	 */
	public void setSigncert(String signcert) {
		this.signcert = signcert;
	}

	/**
	 * @return Returns the user_dn.
	 */
	public String getUser_dn() {
		return user_dn;
	}

	/**
	 * @param user_dn
	 *            The user_dn to set.
	 */
	public void setUser_dn(String user_dn) {
		this.user_dn = user_dn;
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
	 * @return Returns the ext_doc_ins_date.
	 */
	public String getExt_doc_ins_date() {
		return ext_doc_ins_date;
	}

	/**
	 * @param ext_doc_ins_date
	 *            The ext_doc_ins_date to set.
	 */
	public void setExt_doc_ins_date(String ext_doc_ins_date) {
		this.ext_doc_ins_date = ext_doc_ins_date;
	}

	/**
	 * @return Returns the ext_doc_result_msg.
	 */
	public String getExt_doc_result_msg() {
		return ext_doc_result_msg;
	}

	/**
	 * @param ext_doc_result_msg
	 *            The ext_doc_result_msg to set.
	 */
	public void setExt_doc_result_msg(String ext_doc_result_msg) {
		this.ext_doc_result_msg = ext_doc_result_msg;
	}

	/**
	 * @return Returns the ext_doc_reissue_msg.
	 */
	public String getExt_doc_reissue_msg() {
		return ext_doc_reissue_msg;
	}

	/**
	 * @param ext_doc_reissue_msg
	 *            The ext_doc_reissue_msg to set.
	 */
	public void setExt_doc_reissue_msg(String ext_doc_reissue_msg) {
		this.ext_doc_reissue_msg = ext_doc_reissue_msg;
	}

	/**
	 * @return Returns the ext_doc_result_status.
	 */
	public String getExt_doc_result_status() {
		return ext_doc_result_status;
	}

	/**
	 * @param ext_doc_result_status
	 *            The ext_doc_result_status to set.
	 */
	public void setExt_doc_result_status(String ext_doc_result_status) {
		this.ext_doc_result_status = ext_doc_result_status;
	}

	/**
	 * @return Returns the ext_system_type.
	 */
	public String getExt_system_type() {
		return ext_system_type;
	}

	/**
	 * @param ext_system_type
	 *            The ext_system_type to set.
	 */
	public void setExt_system_type(String ext_system_type) {
		this.ext_system_type = ext_system_type;
	}

	/**
	 * @return Returns the receiver_comp_id.
	 */
	public String getReceiver_comp_id() {
		return receiver_comp_id;
	}

	/**
	 * @param receiver_comp_id
	 *            The receiver_comp_id to set.
	 */
	public void setReceiver_comp_id(String receiver_comp_id) {
		this.receiver_comp_id = receiver_comp_id;
	}

	/**
	 * @return Returns the sender_comp_id.
	 */
	public String getSender_comp_id() {
		return sender_comp_id;
	}

	/**
	 * @param sender_comp_id
	 *            The sender_comp_id to set.
	 */
	public void setSender_comp_id(String sender_comp_id) {
		this.sender_comp_id = sender_comp_id;
	}

	/**
	 * @return Returns the ext_buyer_sabun.
	 */
	public String getExt_buyer_sabun() {
		return ext_buyer_sabun;
	}

	/**
	 * @param ext_buyer_sabun
	 *            The ext_buyer_sabun to set.
	 */
	public void setExt_buyer_sabun(String ext_buyer_sabun) {
		this.ext_buyer_sabun = ext_buyer_sabun;
	}

	/**
	 * @return Returns the ext_valid_edate.
	 */
	public String getExt_valid_edate() {
		return ext_valid_edate;
	}

	/**
	 * @param ext_valid_edate
	 *            The ext_valid_edate to set.
	 */
	public void setExt_valid_edate(String ext_valid_edate) {
		this.ext_valid_edate = ext_valid_edate;
	}

	/**
	 * @return Returns the ext_valid_sdate.
	 */
	public String getExt_valid_sdate() {
		return ext_valid_sdate;
	}

	/**
	 * @param ext_valid_sdate
	 *            The ext_valid_sdate to set.
	 */
	public void setExt_valid_sdate(String ext_valid_sdate) {
		this.ext_valid_sdate = ext_valid_sdate;
	}

	/**
	 * @return Returns the ext_decuction_detail.
	 */
	public String getExt_decuction_detail() {
		return ext_decuction_detail;
	}

	/**
	 * @param ext_decuction_detail
	 *            The ext_decuction_detail to set.
	 */
	public void setExt_decuction_detail(String ext_decuction_detail) {
		this.ext_decuction_detail = ext_decuction_detail;
	}

	/**
	 * @return Returns the ext_decuction_status.
	 */
	public String getExt_decuction_status() {
		return ext_decuction_status;
	}

	/**
	 * @param ext_decuction_status
	 *            The ext_decuction_status to set.
	 */
	public void setExt_decuction_status(String ext_decuction_status) {
		this.ext_decuction_status = ext_decuction_status;
	}

	/**
	 * @return Returns the ext_purchase_type.
	 */
	public String getExt_purchase_type() {
		return ext_purchase_type;
	}

	/**
	 * @param ext_purchase_type
	 *            The ext_purchase_type to set.
	 */
	public void setExt_purchase_type(String ext_purchase_type) {
		this.ext_purchase_type = ext_purchase_type;
	}

	/**
	 * @return Returns the ext_voucher_type.
	 */
	public String getExt_voucher_type() {
		return ext_voucher_type;
	}

	/**
	 * @param ext_voucher_type
	 *            The ext_voucher_type to set.
	 */
	public void setExt_voucher_type(String ext_voucher_type) {
		this.ext_voucher_type = ext_voucher_type;
	}

	/**
	 * @return Returns the tax_gubun.
	 */
	public String getTax_gubun() {
		return tax_gubun;
	}

	/**
	 * @param ext_voucher_type
	 *            The tax_gubun to set.
	 */
	public void setTax_gubun(String tax_gubun) {
		this.tax_gubun = tax_gubun;
	}

	public String getExt_process_status_code() {
		return ext_process_status_code;
	}

	public void setExt_process_status_code(String ext_process_status_code) {
		this.ext_process_status_code = ext_process_status_code;
	}

	public String getComp_code() {
		return comp_code;
	}

	public void setComp_code(String comp_code) {
		this.comp_code = comp_code;
	}

	public String getExt_consign_construct_no() {
		return ext_consign_construct_no;
	}

	public void setExt_consign_construct_no(String ext_consign_construct_no) {
		this.ext_consign_construct_no = ext_consign_construct_no;
	}

	public String getExt_consign_construct_amount() {
		return ext_consign_construct_amount;
	}

	public void setExt_consign_construct_amount(
			String ext_consign_construct_amount) {
		this.ext_consign_construct_amount = ext_consign_construct_amount;
	}

	public String getExt_revenue_construct_amount() {
		return ext_revenue_construct_amount;
	}

	public void setExt_revenue_construct_amount(String ext_revenue_construct_amount) {
		this.ext_revenue_construct_amount = ext_revenue_construct_amount;
	}

	public String getExt_revenue_construct_no() {
		return ext_revenue_construct_no;
	}

	public void setExt_revenue_construct_no(String ext_revenue_construct_no) {
		this.ext_revenue_construct_no = ext_revenue_construct_no;
	}

	/**
	 * @return the contractor_email
	 */
	public String getContractor_email() {
		return contractor_email;
	}

	/**
	 * @param contractorEmail the contractor_email to set
	 */
	public void setContractor_email(String contractorEmail) {
		contractor_email = contractorEmail;
	}

	/**
	 * @return the contractor_name
	 */
	public String getContractor_name() {
		return contractor_name;
	}

	/**
	 * @param contractorName the contractor_name to set
	 */
	public void setContractor_name(String contractorName) {
		contractor_name = contractorName;
	}

	/**
	 * @return the contractor_phone
	 */
	public String getContractor_phone() {
		return contractor_phone;
	}

	/**
	 * @param contractorPhone the contractor_phone to set
	 */
	public void setContractor_phone(String contractorPhone) {
		contractor_phone = contractorPhone;
	}
	
}
