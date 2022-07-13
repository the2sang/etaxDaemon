/*
 * Created on 2005. 11. 7.
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
public class TaxFinanceContectVO extends Object implements Serializable {
	private String uuid = "";

	private String id = "";

	private String name = "";

	private String tel = "";

	private String email = "";
	
	private String asp_issue_id ="";
	
	
	

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
 *  
 * @return
 */
	public String getAsp_issue_id() {
		return asp_issue_id;
	}

	public void setAsp_issue_id(String asp_issue_id) {
		this.asp_issue_id = asp_issue_id;
	}
	
	
	
	
}
