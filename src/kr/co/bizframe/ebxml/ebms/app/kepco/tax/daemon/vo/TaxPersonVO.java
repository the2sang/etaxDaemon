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
public class TaxPersonVO extends Object implements Serializable {

	private String id = "";

	private String biz_id = "";

	private String name = "";

	private String dept = "";

	private String tel = "";

	private String hp = "";

	private String email = "";

	private String comp_name = "";

	private String parent_id = "";

	private String link_id = "";

	private String comp_type = "";

	private String jumin_num = "";

	private String tax_smsYN = "";

	public String getTax_smsYN() {
		return tax_smsYN;
	}

	public void setTax_smsYN(String tax_smsYN) {
		this.tax_smsYN = tax_smsYN;
	}

	public String getJumin_num() {
		return jumin_num;
	}

	public void setJumin_num(String jumin_num) {
		this.jumin_num = jumin_num;
	}

	/**
	 * @return Returns the link_id.
	 */
	public String getLink_id() {
		return link_id;
	}

	/**
	 * @param link_id
	 *            The link_id to set.
	 */
	public void setLink_id(String link_id) {
		this.link_id = link_id;
	}

	/**
	 * @return Returns the parent_id.
	 */
	public String getParent_id() {
		return parent_id;
	}

	/**
	 * @param parent_id
	 *            The parent_id to set.
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * @return Returns the comp_name.
	 */
	public String getComp_name() {
		return comp_name;
	}

	/**
	 * @param comp_name
	 *            The comp_name to set.
	 */
	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the biz_num.
	 */
	public String getBiz_id() {
		return biz_id;
	}

	/**
	 * @param biz_num
	 *            The biz_num to set.
	 */
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}

	/**
	 * @return Returns the dept.
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept
	 *            The dept to set.
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the tel.
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            The tel to set.
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getComp_type() {
		return comp_type;
	}

	public void setComp_type(String comp_type) {
		this.comp_type = comp_type;
	}

	/**
	 * @return Returns the hp.
	 */
	public String getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            The hp to set.
	 */
	public void setHp(String hp) {
		this.hp = hp;
	}
}
