package jp.ac.nig.ddbj.wabi.request.imputation;

import javax.validation.constraints.Pattern;

import jp.ac.nig.ddbj.wabi.request.WabiGetRequest;

public class ImputationGetRequest extends WabiGetRequest {

	@Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{2}-\\d{3}-\\d{6}$")
	String requestId;

	@Pattern(regexp = "^(status|result|request)$")
	String info = "status";

	@Pattern(regexp = "^(text|json|xml|bigfile|imagefile|requestfile)$")
	String format = "text";
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}
