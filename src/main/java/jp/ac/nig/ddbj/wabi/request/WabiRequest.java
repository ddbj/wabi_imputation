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
package jp.ac.nig.ddbj.wabi.request;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import jp.ac.nig.ddbj.wabi.request.WabiRequest;

import org.hibernate.validator.constraints.NotEmpty;

import net.arnx.jsonic.JSON;

import org.springframework.web.multipart.MultipartFile;

/** POSTメソッドでWABIを呼び出す時に用いられる入力データを表す.
 */
public class WabiRequest {

	// data fileの内容。ファイルに出力されるので内容のチェックは不要。
//	@NotEmpty
//	String dataFile;
	MultipartFile dataFile;
//	String parameters     = null;

	@Pattern(regexp = "^(text|json|xml|bigfile|requestfile)$", message = "format: text, json, xml, bigfile or requestfile")
	String format         = null;

	@Pattern(regexp = "^www$")
	String result         = null;

	//	String address        = null;
//	String database       = null;
	
	public String toJsonStr() {
		return JSON.encode(this, true);
	}
//	public String getDataFile() {
//		return dataFile;
//	}
//	public void setDataFile(String dataFile) {
//		this.dataFile = dataFile;
//	}
	public MultipartFile getDataFile() {
		return dataFile;
	}
	public void setDataFile(MultipartFile dataFile) {
		this.dataFile = dataFile;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	/*
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	*/
}
