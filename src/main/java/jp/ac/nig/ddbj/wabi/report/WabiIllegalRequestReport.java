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
package jp.ac.nig.ddbj.wabi.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jp.ac.nig.ddbj.wabi.request.WabiGetRequest;
import jp.ac.nig.ddbj.wabi.request.WabiRequest;
import jp.ac.nig.ddbj.wabi.util.CalendarUtil;

public class WabiIllegalRequestReport extends LinkedHashMap<String, Object> {
	public WabiIllegalRequestReport(WabiRequest req) {
		this.put("status",       "illegal-arguments");
		this.put("message",      "Illegal arguments.");
		this.put("format",       req.getFormat());
//		this.put("parameters",   req.getParameters());
		this.put("data",         req.getDataFile());
		this.put("result",       req.getResult());
//		this.put("address",      req.getAddress());
		this.put("current-time", CalendarUtil.getTime());
	}

	public WabiIllegalRequestReport(WabiGetRequest request) {
		this.put("status",       "illegal-arguments");
		this.put("message",      "Illegal arguments.");
		this.put("request-ID",   request.getRequestId());
		this.put("format",       request.getFormat());
		this.put("info",         request.getInfo());
		this.put("current-time", CalendarUtil.getTime());
	}

	public void addErrorMessage(String message) {
		final String key = "error-messages";
		if (!this.containsKey(key)) {
			this.put(key, new ArrayList<String>());
		}
		((List<String>)this.get(key)).add(message);
	}

	public void addErrorCode(List<String> code) {
		final String key = "error-codes";
		if (!this.containsKey(key)) {
			this.put(key, new ArrayList<List<String>>());
		}
		((List<List<String>>)this.get(key)).add(code);
	}
}
