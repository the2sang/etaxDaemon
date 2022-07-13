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
public class TaxLineVO extends Object implements Serializable {

	private String uuid = "";

	private String line_num = "";

	private String trans_date = "";

	private String id = "";

	private String class_id = "";

	private String name = "";

	private String define_txt = "";

	private String line_desc = "";

	private String quantity = "";

	private String sub_tot_quantity = "";

	private String basis_amt = "";

	private String currency_code = "";

	private String amt = "";

	private String sub_tot_amt = "";

	private String tax_amt = "";

	private String tax_sub_tot_amt = "";

	private String forn_charge_amt = "";

	private String forn_charge_sub_tot_amt = "";

	private String exchange_currency_rate = "";

	/**
	 * @return Returns the amt.
	 */
	public String getAmt() {
		return amt;
	}

	/**
	 * @param amt
	 *            The amt to set.
	 */
	public void setAmt(String amt) {
		this.amt = amt;
	}

	/**
	 * @return Returns the basis_amt.
	 */
	public String getBasis_amt() {
		return basis_amt;
	}

	/**
	 * @param basis_amt
	 *            The basis_amt to set.
	 */
	public void setBasis_amt(String basis_amt) {
		this.basis_amt = basis_amt;
	}

	/**
	 * @return Returns the class_id.
	 */
	public String getClass_id() {
		return class_id;
	}

	/**
	 * @param class_id
	 *            The class_id to set.
	 */
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	/**
	 * @return Returns the currency_code.
	 */
	public String getCurrency_code() {
		return currency_code;
	}

	/**
	 * @param currency_code
	 *            The currency_code to set.
	 */
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	/**
	 * @return Returns the define_txt.
	 */
	public String getDefine_txt() {
		return define_txt;
	}

	/**
	 * @param define_txt
	 *            The define_txt to set.
	 */
	public void setDefine_txt(String define_txt) {
		this.define_txt = define_txt;
	}

	/**
	 * @return Returns the exchange_currency_rate.
	 */
	public String getExchange_currency_rate() {
		return exchange_currency_rate;
	}

	/**
	 * @param exchange_currency_rate
	 *            The exchange_currency_rate to set.
	 */
	public void setExchange_currency_rate(String exchange_currency_rate) {
		this.exchange_currency_rate = exchange_currency_rate;
	}

	/**
	 * @return Returns the forn_charge_amt.
	 */
	public String getForn_charge_amt() {
		return forn_charge_amt;
	}

	/**
	 * @param forn_charge_amt
	 *            The forn_charge_amt to set.
	 */
	public void setForn_charge_amt(String forn_charge_amt) {
		this.forn_charge_amt = forn_charge_amt;
	}

	/**
	 * @return Returns the forn_charge_sub_tot_amt.
	 */
	public String getForn_charge_sub_tot_amt() {
		return forn_charge_sub_tot_amt;
	}

	/**
	 * @param forn_charge_sub_tot_amt
	 *            The forn_charge_sub_tot_amt to set.
	 */
	public void setForn_charge_sub_tot_amt(String forn_charge_sub_tot_amt) {
		this.forn_charge_sub_tot_amt = forn_charge_sub_tot_amt;
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
	 * @return Returns the line_desc.
	 */
	public String getLine_desc() {
		return line_desc;
	}

	/**
	 * @param line_desc
	 *            The line_desc to set.
	 */
	public void setLine_desc(String line_desc) {
		this.line_desc = line_desc;
	}

	/**
	 * @return Returns the line_num.
	 */
	public String getLine_num() {
		return line_num;
	}

	/**
	 * @param line_num
	 *            The line_num to set.
	 */
	public void setLine_num(String line_num) {
		this.line_num = line_num;
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
	 * @return Returns the quantity.
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            The quantity to set.
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return Returns the sub_tot_amt.
	 */
	public String getSub_tot_amt() {
		return sub_tot_amt;
	}

	/**
	 * @param sub_tot_amt
	 *            The sub_tot_amt to set.
	 */
	public void setSub_tot_amt(String sub_tot_amt) {
		this.sub_tot_amt = sub_tot_amt;
	}

	/**
	 * @return Returns the sub_tot_quantity.
	 */
	public String getSub_tot_quantity() {
		return sub_tot_quantity;
	}

	/**
	 * @param sub_tot_quantity
	 *            The sub_tot_quantity to set.
	 */
	public void setSub_tot_quantity(String sub_tot_quantity) {
		this.sub_tot_quantity = sub_tot_quantity;
	}

	/**
	 * @return Returns the tax_amt.
	 */
	public String getTax_amt() {
		return tax_amt;
	}

	/**
	 * @param tax_amt
	 *            The tax_amt to set.
	 */
	public void setTax_amt(String tax_amt) {
		this.tax_amt = tax_amt;
	}

	/**
	 * @return Returns the tax_sub_tot_amt.
	 */
	public String getTax_sub_tot_amt() {
		return tax_sub_tot_amt;
	}

	/**
	 * @param tax_sub_tot_amt
	 *            The tax_sub_tot_amt to set.
	 */
	public void setTax_sub_tot_amt(String tax_sub_tot_amt) {
		this.tax_sub_tot_amt = tax_sub_tot_amt;
	}

	/**
	 * @return Returns the trans_date.
	 */
	public String getTrans_date() {
		return trans_date;
	}

	/**
	 * @param trans_date
	 *            The trans_date to set.
	 */
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
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
}
