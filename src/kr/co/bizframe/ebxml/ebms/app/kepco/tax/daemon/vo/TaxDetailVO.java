package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

import java.io.Serializable;

public class TaxDetailVO extends Object implements Serializable {
	
	private String biz_no = "";				//공사업체사업자번호

	private String cons_no = "";			//공사번호

	private String req_no = "";				//역발행요청일시

	private String acptno = "";				//접수번호

	private String cons_knd_cd = "";		//공사종류코드
	
//	private String meter_comp_amt = "";		//계기공사비
	
//	private String inb_comp_amt = "";		//인입선공사비
	
	private String matrbill_comp_amt = "";	//도급재료비
	
	private String comp_amt = "";			//도급공사비

	private String acpt_knd_nm = "";		//접수종류명

	private String custnm = "";				//고객명

	private String address = "";			//주소

	private String oper_ymd = "";			//시공일자

	private String prof_comp_amt = "";		//수익적공사비
//2015.08.20 vat_amt 추가(ppa시스템 세액)
	private String vat_amt = "";

	public String getAcpt_knd_nm() {
		return acpt_knd_nm;
	}

	public void setAcpt_knd_nm(String acpt_knd_nm) {
		this.acpt_knd_nm = acpt_knd_nm;
	}

	public String getAcptno() {
		return acptno;
	}

	public void setAcptno(String acptno) {
		this.acptno = acptno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBiz_no() {
		return biz_no;
	}

	public void setBiz_no(String biz_no) {
		this.biz_no = biz_no;
	}

	public String getCons_knd_cd() {
		return cons_knd_cd;
	}

	public void setCons_knd_cd(String cons_knd_cd) {
		this.cons_knd_cd = cons_knd_cd;
	}

	public String getCons_no() {
		return cons_no;
	}

	public void setCons_no(String cons_no) {
		this.cons_no = cons_no;
	}

	public String getCustnm() {
		return custnm;
	}

	public void setCustnm(String custnm) {
		this.custnm = custnm;
	}

	public String getOper_ymd() {
		return oper_ymd;
	}

	public void setOper_ymd(String oper_ymd) {
		this.oper_ymd = oper_ymd;
	}


	public String getReq_no() {
		return req_no;
	}

	public void setReq_no(String req_no) {
		this.req_no = req_no;
	}

	public String getComp_amt() {
		return comp_amt;
	}

	public void setComp_amt(String comp_amt) {
		this.comp_amt = comp_amt;
	}

	public String getMatrbill_comp_amt() {
		return matrbill_comp_amt;
	}

	public void setMatrbill_comp_amt(String matrbill_comp_amt) {
		this.matrbill_comp_amt = matrbill_comp_amt;
	}

	public String getProf_comp_amt() {
		return prof_comp_amt;
	}

	public void setProf_comp_amt(String prof_comp_amt) {
		this.prof_comp_amt = prof_comp_amt;
	}
//2015.08.20 vat_amt 추가(ppa시스템 세액)
	/**
	 * @return the vat_amt
	 */
	public String getVat_amt() {
		return vat_amt;
	}

	/**
	 * @param vatAmt the vat_amt to set
	 */
	public void setVat_amt(String vatAmt) {
		vat_amt = vatAmt;
	}

}
