/*
 * Created on 2005. 8. 9.
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
public class TaxReceiptVO extends Object implements Serializable {
 
	private String uuid = "";					//������ȣ
	
	private String biz_id = "";					//�����ü����ڹ�ȣ
 
	private String const_no = "";				//�����ȣ
	
	private String amt1 = "";					//�������

	private String amt2 = "";					//���Լ������
	
	private String amt3 = "";					//����Ժμ��� 20101004 �߰�

	private String receipt_no = "";				//������ȣ

	private String receipt_kind = "";			//����������

	private String cust_name = "";				//����

	private String address = "";				//�ּ�
		
	private String const_date = "";				//�ð�����
	
	private String prof_cons_no = "";			//�����������ȣ
	
	private String prof_comp_amt = "";			//�����������

	private String matrbill_comp_amt = "";		//��������
	
	private String comp_amt = "";				//���ް����
	
	
	public String getAmt3() {
		return amt3;
	}

	public void setAmt3(String amt3) {
		this.amt3 = amt3;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return Returns the link_id.
	 */
	public String getBiz_id() {
		return biz_id;
	}

	/**
	 * @param link_id
	 *            The link_id to set.
	 */
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}

	/**
	 * @return Returns the parent_id.
	 */
	public String getAmt1() {
		return amt1;
	}

	/**
	 * @param parent_id
	 *            The parent_id to set.
	 */
	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}

	/**
	 * @return Returns the comp_name.
	 */
	public String getAmt2() {
		return amt2;
	}

	/**
	 * @param comp_name
	 *            The comp_name to set.
	 */
	public void setAmt2(String amt2) {
		this.amt2 = amt2;
	}

	/**
	 * @return Returns the id.
	 */
	public String getReceipt_no() {
		return receipt_no;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	/**
	 * @return Returns the biz_num.
	 */
	public String getReceipt_kind() {
		return receipt_kind;
	}

	/**
	 * @param biz_num
	 *            The biz_num to set.
	 */
	public void setReceipt_kind(String receipt_kind) {
		this.receipt_kind = receipt_kind;
	}

	/**
	 * @return Returns the dept.
	 */
	public String getCust_name() {
		return cust_name;
	}

	/**
	 * @param dept
	 *            The dept to set.
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	/**
	 * @return Returns the email.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the name.
	 */
	public String getConst_date() {
		return const_date;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setConst_date(String const_date) {
		this.const_date = const_date;
	}

	/**
	 * @return Returns the name.
	 */
	public String getConst_no() {
		return const_no;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setConst_no(String const_no) {
		this.const_no = const_no;
	}

	public String getProf_cons_no() {
		return prof_cons_no;
	}

	public void setProf_cons_no(String prof_cons_no) {
		this.prof_cons_no = prof_cons_no;
	}

	public String getProf_comp_amt() {
		return prof_comp_amt;
	}

	public void setProf_comp_amt(String prof_comp_amt) {
		this.prof_comp_amt = prof_comp_amt;
	}

	public String getComp_amt() {
		return comp_amt;
	}

	public void setComp_amt(String comp_amt) {
		this.comp_amt = comp_amt;
	}

	public String getMatrbill_comp_amt() {
		return matrbill_comp_amt;
	}

	public void setMatrbill_comp_amt(String matrbill_comp_amt) {
		this.matrbill_comp_amt = matrbill_comp_amt;
	}
}
