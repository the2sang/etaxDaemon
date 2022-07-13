package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

public class ReturnValue {
    private String doctype;

    private String id;

    private String docid;

    private String chg_deg;

    private String err_msg;

    private boolean result;

    private String d_number;

    private String d_date;

    private String docid2;

    private String chg_deg2;

    public String getD_number() {
        return this.d_number;
    }

    public String getD_date() {
        return this.d_date;
    }

    public String getDocid2() {
        return this.docid2;
    }

    public String getChg_deg2() {
        return this.chg_deg2;
    }

    public void setD_number(String d_number) {
        this.d_number = d_number;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

    public void setDocid2(String docid2) {
        this.docid2 = docid2;
    }

    public void setChg_deg2(String chg_deg2) {
        this.chg_deg2 = chg_deg2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocid() {
        return this.docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getChg_deg() {
        return this.chg_deg;
    }

    public void setChg_deg(String chg_deg) {
        this.chg_deg = chg_deg;
    }

    public String getErr_msg() {
        return this.err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getDoctype() {
        return this.doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
