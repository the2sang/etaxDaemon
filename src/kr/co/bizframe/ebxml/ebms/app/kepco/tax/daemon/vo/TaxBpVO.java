package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

public class TaxBpVO {

	String process_id = "";

	String activity_id = "";

	String operation_id = "";

	String uuid = "";

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getOperation_id() {
		return operation_id;
	}

	public void setOperation_id(String operation_id) {
		this.operation_id = operation_id;
	}

	public String getProcess_id() {
		return process_id;
	}

	public void setProcess_id(String process_id) {
		this.process_id = process_id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
