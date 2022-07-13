package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

import java.io.Serializable;

public class TaxHeaderVO extends Object implements Serializable {
	private String biz_no = "";				//�����ü����ڹ�ȣ

	private String cons_no = "";			//�����ȣ

	private String req_no = "";				//�������û�Ͻ�

	private String prof_cons_no = "";		//�����������ȣ

	private String status = "";				//���λ���

	private String cons_nm = "";			//�����

	private String comp_no = "";			//�����ü�ڵ�

	private String pub_ymd = "";			//��꼭��������

	private String buyer_biz_id = "";		//����� ����ڹ�ȣ

	private String inspector_id = "";		//�˼��� �̸�

	private String contractor_id = "";		//����� �̸�

	//�߰� 200912 KHS
	private String bef_nts_issue_id 	= ""; // ����û�������ι�ȣ // ������
	private String modify_code			= ""; // ���������ڵ�	// ������

	private String supplier_biz_cd		= ""; // ��������������ȣ // �ʼ��ƴ�
	private String buyer_biz_cd			= ""; // ���޹޴�����������ȣ // ���ݰ�꼭 ������ �ʼ�
	private String doc_type_detail		= ""; // ���ݰ�꼭����(3.0) // �ʼ��޾ƾ���
	
	private String rel_system_id		= ""; // ����ý��� ID
	
	
	private String sms_yn  = "";  //SMS�߽ſ���
	
	private String sms_sender_name = ""; //SMS �߽��� �̸�
	
	private String sms_sender_tel = "";  //SMS �߽��� ��ȭ��ȣ
	
	

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
