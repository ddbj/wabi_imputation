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
package jp.ac.nig.ddbj.wabi.report.imputation;

import java.io.IOException;

import jp.ac.nig.ddbj.wabi.report.WabiErrorReport;
import jp.ac.nig.ddbj.wabi.request.imputation.ImputationRequest;
import jp.ac.nig.ddbj.wabi.request.WabiRequest;

public class ImputationErrorReport extends WabiErrorReport {

	public ImputationErrorReport(WabiRequest req) throws IOException {
		super(req);
	}
	
	public ImputationErrorReport(ImputationRequest req) throws IOException {
		super(req);
		this.put("status", "400");
		this.put("result", "fail");
	}
	

}
