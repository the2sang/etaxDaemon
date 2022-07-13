package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

public class EBXMLAppException extends Exception {

    public EBXMLAppException(String class_name, String method_name, String exception_name, String err_msg) {
        super("[" + class_name + "][" + method_name + "][" + exception_name + "][" + err_msg + "]");

    }

    public EBXMLAppException(String err_msg) {
        super(err_msg);
    }
}
