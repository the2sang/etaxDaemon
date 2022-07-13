/*
 * Created on 2006. 4. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.util.Date;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxInvoiceException extends Exception {
	
	
	public static final int RDB_EXCEPTION = 1000;
	public static final int BINDING_EXCEPTION = 2000;
	public static final int MSH_EXCEPTION = 3000;
	public static final int MSH_UNKNOWN_ERROR = 9999;//MSH_RETRIEVE_ERROR
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3257286924480558129L;

    int code = MSH_UNKNOWN_ERROR;
    public TaxInvoiceException(Object obj, Exception e) {
        super(
        "<br>\n"+
        "##################################################################################" +
        "<br>\n"+
        "  Time of the catched exception  >>>>> "+new Date(System.currentTimeMillis()) +
        "<br>\n"+
        "  Exception entry class is       >>>>> "+ obj.getClass().getName()   +
        "<br>\n"+
        " <br>\n"+
        "   [Error message]<br>\n"+
         e.getMessage()+"<br>\n"+ 
        "###################################################################################"
        );
        

    }
    
	public TaxInvoiceException(int code, String message)
	{
		super(message);
		this.code = code;		
	}
    

    public TaxInvoiceException(String err_msg) {
        super(err_msg);
    }
}
