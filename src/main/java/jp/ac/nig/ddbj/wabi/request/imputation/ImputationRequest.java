/**
 * This file is part of WABI : DDBJ WebAPIs for Biology.
 *
 * WABI : DDBJ WebAPIs for Biology is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WABI : DDBJ WebAPIs for Biology is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with WABI : DDBJ WebAPIs for Biology.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.ac.nig.ddbj.wabi.request.imputation;

//import javax.validation.constraints.DecimalMax;
//import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;

import jp.ac.nig.ddbj.wabi.request.WabiRequest;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonProperty;


/** POSTメソッドでWabi WebAPIを呼び出す時に用いられる入力データを表す.
 * 
 * @author oogasawa
 *
 */
public class ImputationRequest extends WabiRequest {

	@NotNull
	int user_id;

	@NotNull
	int analysis_id;

	@NotNull
	@Range(min = 1, max = 2, message = "analysis_type range error.")
	int analysis_type;
	
	@NotNull
	String download_path;
	
	@NotNull
	String upload_path;
	
//	@NotEmpty
//	@Pattern(regexp = "^(blastn|blastp|blastx|tblastn|tblastx)$",
//		message = "blastn, blastp, blastx, tblastn and tblastx are permitted as program.")
//	String program;
	

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getAnalysis_id() {
		return analysis_id;
	}
	public void setAnalysis_id(int analysis_id) {
		this.analysis_id = analysis_id;
	}
	public int getAnalysis_type() {
		return analysis_type;
	}
	public void setAnalysis_type(int analysis_type) {
		this.analysis_type = analysis_type;
	}
	public String getDownload_path() {
		return download_path;
	}
	public void setDownload_path(String download_path) {
		this.download_path = download_path;
	}
	public String getUpload_path() {
		return upload_path;
	}
	public void setUpload_path(String upload_path) {
		this.upload_path = upload_path;
	}
	
}
