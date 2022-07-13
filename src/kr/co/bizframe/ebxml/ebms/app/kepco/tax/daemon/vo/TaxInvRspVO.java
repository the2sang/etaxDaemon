//package kr.co.bizframe.ebxml.app.kepco.xmledi.server.vo.tr;
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

import java.io.Serializable;

//import kr.co.bizframe.ebxml.app.kepco.xmledi.server.vo.common.BinDatVO;

public class TaxInvRspVO implements Serializable{
	
	private String management_id   ;
	private String seqno           ;
	private String bldat           ;
	private String status          ;
	private String status_txt      ;
	private String ext_system_type ;
	private String del_flag        ;
	private String delay_reason    ;
	private String delay_days      ;
	private String delay_ratio     ;
	private String delay_penalty   ;
	private String returned_payment;
	private String contract_no     ;
	private String sup_code        ;
	private String stats           ;
	private String msgtxt          ;
	private String doc_id          ;
	private int doc_seq            ;
	private String confirm_flag	   ;
	
	private String returned_datetime;		// 지급예정일
	
	private String issue_id;
	
	//
	private String issue_day;
	//public BinDatVO binVO;
	
	//세금계산서 국세청신고완료일시 2013.04.22 장지호
	private String esero_finish_ts;
	
	public TaxInvRspVO(){
		management_id   ="";
		seqno           ="";
		bldat           ="";
		status          ="";
		status_txt      ="";
		ext_system_type ="";
		del_flag        ="";
		delay_reason    ="";
		delay_days      ="";
		delay_ratio     ="";
		delay_penalty   ="";
		returned_payment="";
		contract_no     ="";
		sup_code        ="";
		stats           ="";
		msgtxt          ="";
		doc_id          ="";
		doc_seq         =0;
		confirm_flag    ="";
		returned_datetime = "";		
		
		issue_day = "";
		//binVO = new BinDatVO();
		
		//세금계산서 국세청신고완료일시 2013.04.22 장지호
		esero_finish_ts ="";
	}


	public String getContract_no() {
		return contract_no;
	}


	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}


	public String getSup_code() {
		return sup_code;
	}


	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}


	public String getBldat() {
		return bldat;
	}


	public void setBldat(String bldat) {
		this.bldat = bldat;
	}


	public String getDel_flag() {
		return del_flag;
	}


	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}


	public String getDelay_days() {
		return delay_days;
	}


	public void setDelay_days(String delay_days) {
		this.delay_days = delay_days;
	}


	public String getDelay_penalty() {
		return delay_penalty;
	}


	public void setDelay_penalty(String delay_penalty) {
		this.delay_penalty = delay_penalty;
	}


	public String getDelay_ratio() {
		return delay_ratio;
	}


	public void setDelay_ratio(String delay_ratio) {
		this.delay_ratio = delay_ratio;
	}


	public String getDelay_reason() {
		return delay_reason;
	}


	public void setDelay_reason(String delay_reason) {
		this.delay_reason = delay_reason;
	}


	public String getDoc_id() {
		return doc_id;
	}


	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}


	public int getDoc_seq() {
		return doc_seq;
	}


	public void setDoc_seq(int doc_seq) {
		this.doc_seq = doc_seq;
	}


	public String getExt_system_type() {
		return ext_system_type;
	}


	public void setExt_system_type(String ext_system_type) {
		this.ext_system_type = ext_system_type;
	}


	public String getManagement_id() {
		return management_id;
	}


	public void setManagement_id(String management_id) {
		this.management_id = management_id;
	}


	public String getMsgtxt() {
		return msgtxt;
	}


	public void setMsgtxt(String msgtxt) {
		this.msgtxt = msgtxt;
	}


	public String getReturned_payment() {
		return returned_payment;
	}


	public void setReturned_payment(String returned_payment) {
		this.returned_payment = returned_payment;
	}


	public String getSeqno() {
		return seqno;
	}


	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}


	public String getStats() {
		return stats;
	}


	public void setStats(String stats) {
		this.stats = stats;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatus_txt() {
		return status_txt;
	}


	public void setStatus_txt(String status_txt) {
		this.status_txt = status_txt;
	}

/*
	public BinDatVO getBinVO() {
		return binVO;
	}


	public void setBinVO(BinDatVO binVO) {
		this.binVO = binVO;
	}
*/

	public String getConfirm_flag() {
		return confirm_flag;
	}


	public void setConfirm_flag(String confirm_flag) {
		this.confirm_flag = confirm_flag;
	}


	public String getReturned_datetime() {
		return returned_datetime;
	}


	public void setReturned_datetime(String returned_datetime) {
		this.returned_datetime = returned_datetime;
	}


	public String getIssue_id() {
		return issue_id;
	}


	public void setIssue_id(String issue_id) {
		this.issue_id = issue_id;
	}
	
	public String getIssue_day() {
		return issue_day;
	}


	public void setIssue_day(String issueDay) {
		issue_day = issueDay;
	}


	// 세금계산서 국세청신고완료일시 2013.04.22 장지호
	public String getEsero_finish_ts() {
		return esero_finish_ts;
	}


	public void setEsero_finish_ts(String eseroFinishTs) {
		esero_finish_ts = eseroFinishTs;
	}

}
