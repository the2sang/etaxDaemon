/*
 * Created on 2005. 11. 4.
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
public class TaxCountVO extends Object implements Serializable {
	private String date = "";

	private String cnt = "";

	private String totCnt = "";

	/**
	 * @return Returns the cnt.
	 */
	public String getCnt() {
		return cnt;
	}

	/**
	 * @param cnt
	 *            The cnt to set.
	 */
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return Returns the totCnt.
	 */
	public String getTotCnt() {
		return totCnt;
	}

	/**
	 * @param totCnt
	 *            The totCnt to set.
	 */
	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}
}
