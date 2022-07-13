/*
 * Created on 2005. 8. 8.
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
public class TaxCompanyVO extends Object implements Serializable {

	private String main_biz_id = "";

	private String biz_id = "";

	private String name = "";

	private String president_name = "";

	private String addr = "";

	private String tel = "";

	private String fax = "";

	private String biz_type = "";

	private String biz_class = "";

	private String comp_type = "";

	private String comp_code = "";

	private String person_id = ""; // 사번

    private String zbran_name	  = ""; //한전-지점명
    private String buy_regist_id  = ""; //한전 종사업자 번호

	public String getBuy_regist_id() {
		return buy_regist_id;
	}

	public void setBuy_regist_id(String buy_regist_id) {
		this.buy_regist_id = buy_regist_id;
	}

	public String getZbran_name() {
		return zbran_name;
	}

	public void setZbran_name(String zbran_name) {
		this.zbran_name = zbran_name;
	}

	/**
	 * @return Returns the comp_code.
	 */
	public String getComp_code() {
		return comp_code;
	}

	/**
	 * @param comp_code
	 *            The comp_code to set.
	 */
	public void setComp_code(String comp_code) {
		this.comp_code = comp_code;
	}

	/**
	 * @return Returns the type.
	 */
	public String getComp_type() {
		return comp_type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setComp_type(String type) {
		this.comp_type = type;
	}

	/**
	 * @return Returns the addr.
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr
	 *            The addr to set.
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return Returns the biz_class.
	 */
	public String getBiz_class() {
		return biz_class;
	}

	/**
	 * @param biz_class
	 *            The biz_class to set.
	 */
	public void setBiz_class(String biz_class) {
		this.biz_class = biz_class;
	}

	/**
	 * @return Returns the biz_id.
	 */
	public String getBiz_id() {
		return biz_id;
	}

	/**
	 * @param biz_id
	 *            The biz_id to set.
	 */
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}

	/**
	 * @return Returns the biz_type.
	 */
	public String getBiz_type() {
		return biz_type;
	}

	/**
	 * @param biz_type
	 *            The biz_type to set.
	 */
	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}

	/**
	 * @return Returns the fax.
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return Returns the main_biz_id.
	 */
	public String getMain_biz_id() {
		return main_biz_id;
	}

	/**
	 * @param main_biz_id
	 *            The main_biz_id to set.
	 */
	public void setMain_biz_id(String main_biz_id) {
		this.main_biz_id = main_biz_id;
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
	 * @return Returns the president_name.
	 */
	public String getPresident_name() {
		return president_name;
	}

	/**
	 * @param president_name
	 *            The president_name to set.
	 */
	public void setPresident_name(String president_name) {
		this.president_name = president_name;
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

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}
}
