package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

import java.io.Serializable;

public class TaxHeaderVO extends Object implements Serializable {
	private String biz_no = "";				//공사업체사업자번호

	private String cons_no = "";			//공사번호

	private String req_no = "";				//역발행요청일시

	private String prof_cons_no = "";		//수익적공사번호

	private String status = "";				//승인상태

	private String cons_nm = "";			//공사명

	private String comp_no = "";			//공사업체코드

	private String pub_ymd = "";			//계산서발행일자

	private String buyer_biz_id = "";		//사업소 사업자번호

	private String inspector_id = "";		//검수자 이름

	private String contractor_id = "";		//계약자 이름

	//추가 200912 KHS
	private String bef_nts_issue_id 	= ""; // 국세청수정승인번호 // 사용안함
	private String modify_code			= ""; // 수정사유코드	// 사용안함

	private String supplier_biz_cd		= ""; // 공급자종사업장번호 // 필수아님
	private String buyer_biz_cd			= ""; // 공급받는자종사업장번호 // 세금계산서 생성시 필수
	private String doc_type_detail		= ""; // 세금계산서종류(3.0) // 필수받아야함
	
	private String rel_system_id		= ""; // 연계시스템 ID
	
	
	private String sms_yn  = "";  //SMS발신여부
	
	private String sms_sender_name = ""; //SMS 발신자 이름
	
	private String sms_sender_tel = "";  //SMS 발신자 전화번호
	
	

	/**
	 * @return the sms_yn
	 */
	public String getSms_yn() {
		return sms_yn;
	}

	/**
	 * @param smsYn the sms_yn to set
	 */
	public void setSms_yn(String smsYn) {
		sms_yn = smsYn;
	}

	/**
	 * @return the sms_sender_name
	 */
	public String getSms_sender_name() {
		return sms_sender_name;
	}

	/**
	 * @param smsSenderName the sms_sender_name to set
	 */
	public void setSms_sender_name(String smsSenderName) {
		sms_sender_name = smsSenderName;
	}

	/**
	 * @return the sms_sender_tel
	 */
	public String getSms_sender_tel() {
		return sms_sender_tel;
	}

	/**
	 * @param smsSenderTel the sms_sender_tel to set
	 */
	public void setSms_sender_tel(String smsSenderTel) {
		sms_sender_tel = smsSenderTel;
	}

	public String getRel_system_id() {
		return rel_system_id;
	}

	public void setRel_system_id(String relSystemId) {
		rel_system_id = relSystemId;
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

	public String getModify_code() {
		return modify_code;
	}

	public void setModify_code(String modify_code) {
		this.modify_code = modify_code;
	}

	public String getSupplier_biz_cd() {
		return supplier_biz_cd;
	}

	public void setSupplier_biz_cd(String supplier_biz_cd) {
		this.supplier_biz_cd = supplier_biz_cd;
	}

	public String getBiz_no() {
		return biz_no;
	}

	public void setBiz_no(String biz_no) {
		this.biz_no = biz_no;
	}

	public String getCons_no() {
		return cons_no;
	}

	public void setCons_no(String cons_no) {
		this.cons_no = cons_no;
	}

	public String getReq_no() {
		return req_no;
	}

	public void setReq_no(String req_no) {
		this.req_no = req_no;
	}

	public String getProf_cons_no() {
		return prof_cons_no;
	}

	public void setProf_cons_no(String prof_cons_no) {
		this.prof_cons_no = prof_cons_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPub_ymd() {
		return pub_ymd;
	}

	public void setPub_ymd(String pub_ymd) {
		this.pub_ymd = pub_ymd;
	}

	public String getBuyer_biz_id() {
		return buyer_biz_id;
	}

	public void setBuyer_biz_id(String buyer_biz_id) {
		this.buyer_biz_id = buyer_biz_id;
	}

	public String getInspector_id() {
		return inspector_id;
	}

	public void setInspector_id(String inspector_id) {
		this.inspector_id = inspector_id;
	}

	public String getContractor_id() {
		return contractor_id;
	}

	public void setContractor_id(String contractor_id) {
		this.contractor_id = contractor_id;
	}

	public String getCons_nm() {
		return cons_nm;
	}

	public void setCons_nm(String cons_nm) {
		this.cons_nm = cons_nm;
	}

	public String getComp_no() {
		return comp_no;
	}

	public void setComp_no(String comp_no) {
		this.comp_no = comp_no;
	}

}
