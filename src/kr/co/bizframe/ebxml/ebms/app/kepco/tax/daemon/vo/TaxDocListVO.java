/*
 * Created on 2005. 8. 25.
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
public class TaxDocListVO extends Object implements Serializable {
	private String from_comp_name = "";

	private String to_comp_name = "";

	private String other_comp_name = "";

	private String other_comp_id = "";

	private String other_person_name = "";

	private String state = "";

	private String create_date = "";

	private String complete_date = "";

	private String uuid = "";

	private String chargeAmt = "";

	private String publishType = "";

	private String status_code = "";

	// 2006.06.13 이제중 계산서 구분
	private String doc_type_code = "";

	// 역발행, 매출 구분위해
	private String write_type = "";

	private String construct_no = "";

	private String comp_code = "";

	/**
	 * @return Returns the status_code.
	 */
	public String getStatus_code() {
		return status_code;
	}

	/**
	 * @param status_code
	 *            The status_code to set.
	 */
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	/**
	 * @return Returns the publishType.
	 */
	public String getPublishType() {
		return publishType;
	}

	/**
	 * @param publishType
	 *            The publishType to set.
	 */
	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	/**
	 * @return Returns the from_comp_name.
	 */
	public String getFrom_comp_name() {
		return from_comp_name;
	}

	/**
	 * @param from_comp_name
	 *            The from_comp_name to set.
	 */
	public void setFrom_comp_name(String from_comp_name) {
		this.from_comp_name = from_comp_name;
	}

	/**
	 * @return Returns the to_comp_name.
	 */
	public String getTo_comp_name() {
		return to_comp_name;
	}

	/**
	 * @param to_comp_name
	 *            The to_comp_name to set.
	 */
	public void setTo_comp_name(String to_comp_name) {
		this.to_comp_name = to_comp_name;
	}

	/**
	 * @return Returns the chargeAmt.
	 */
	public String getChargeAmt() {
		return chargeAmt;
	}

	/**
	 * @param chargeAmt
	 *            The chargeAmt to set.
	 */
	public void setChargeAmt(String chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	/**
	 * @return Returns the complete_date.
	 */
	public String getComplete_date() {
		return complete_date;
	}

	/**
	 * @param complete_date
	 *            The complete_date to set.
	 */
	public void setComplete_date(String complete_date) {
		this.complete_date = complete_date;
	}

	/**
	 * @return Returns the create_date.
	 */
	public String getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date
	 *            The create_date to set.
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return Returns the other_comp_id.
	 */
	public String getOther_comp_id() {
		return other_comp_id;
	}

	/**
	 * @param other_comp_id
	 *            The other_comp_id to set.
	 */
	public void setOther_comp_id(String other_comp_id) {
		this.other_comp_id = other_comp_id;
	}

	/**
	 * @return Returns the other_comp_name.
	 */
	public String getOther_comp_name() {
		return other_comp_name;
	}

	/**
	 * @param other_comp_name
	 *            The other_comp_name to set.
	 */
	public void setOther_comp_name(String other_comp_name) {
		this.other_comp_name = other_comp_name;
	}

	/**
	 * @return Returns the other_person_name.
	 */
	public String getOther_person_name() {
		return other_person_name;
	}

	/**
	 * @param other_person_name
	 *            The other_person_name to set.
	 */
	public void setOther_person_name(String other_person_name) {
		this.other_person_name = other_person_name;
	}

	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            The state to set.
	 */
	public void setState(String state) {
		this.state = state;
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
	 * @return Returns the uuid.
	 */
	public String getDoc_type_code() {
		return doc_type_code;
	}

	/**
	 * @param uuid
	 *            The uuid to set.
	 */
	public void setDoc_type_code(String doc_type_code) {
		this.doc_type_code = doc_type_code;
	}

	/**
	 * @return Returns the uuid.
	 */
	public String getWrite_type() {
		return write_type;
	}

	/**
	 * @param uuid
	 *            The uuid to set.
	 */
	public void setWrite_type(String write_type) {
		this.write_type = write_type;
	}

	/**
	 * @return Returns the uuid.
	 */
	public String getConstruct_no() {
		return construct_no;
	}

	/**
	 * @param uuid
	 *            The uuid to set.
	 */
	public void setConstruct_no(String construct_no) {
		this.construct_no = construct_no;
	}

	/**
	 * @return Returns the uuid.
	 */
	public String getComp_code() {
		return comp_code;
	}

	/**
	 * @param uuid
	 *            The uuid to set.
	 */
	public void setComp_code(String comp_code) {
		this.comp_code = comp_code;
	}

}
