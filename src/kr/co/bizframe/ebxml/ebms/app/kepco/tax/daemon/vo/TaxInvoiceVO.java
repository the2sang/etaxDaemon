/*
 * Created on 2005. 8. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author shin sung uk
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TaxInvoiceVO extends Object implements Serializable {

	private TaxMetaVO meta = null;

	private TaxMainVO main = null;

	private ArrayList line = null;

	private String uuid = "";

	private TaxContentsVO contents = null;
	
	private String sms_yn = "";
	
	private String sms_sender_name = "";
	
	private String sms_sender_tel = "";
	
	
	
	
	
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

	public TaxInvoiceVO() {
		this.meta = new TaxMetaVO();
		this.main = new TaxMainVO();
		this.line = new ArrayList();
		this.contents = new TaxContentsVO();

	}

	/**
	 * @return Returns the contents.
	 */
	public TaxContentsVO getContents() {
		return contents;
	}

	/**
	 * @param contents
	 *            The contents to set.
	 */
	public void setContents(TaxContentsVO contents) {
		this.contents = contents;
	}
	

	/**
	 * @return Returns the line.
	 */
	public ArrayList getLine() {
		return line;
	}

	/**
	 * @param line
	 *            The line to set.
	 */
	public void setLine(ArrayList line) {
		this.line = line;
	}

	/**
	 * @return Returns the main.
	 */
	public TaxMainVO getMain() {
		return main;
	}

	/**
	 * @param main
	 *            The main to set.
	 */
	public void setMain(TaxMainVO main) {
		this.main = main;
	}

	/**
	 * @return Returns the meta.
	 */
	public TaxMetaVO getMeta() {
		return meta;
	}

	/**
	 * @param meta
	 *            The meta to set.
	 */
	public void setMeta(TaxMetaVO meta) {
		this.meta = meta;
	}
}
