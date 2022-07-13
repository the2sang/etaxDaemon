/*
 * Created on 2005. 5. 10.
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
public class TaxContentsVO extends Object implements Serializable {
	private String uuid = "";

	private byte[] contents = null;

	/**
	 * @return Returns the contents.
	 */
	public byte[] getContents() {
		return contents;
	}

	/**
	 * @param contents
	 *            The contents to set.
	 */
	public void setContents(byte[] contents) {
		this.contents = contents;
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
