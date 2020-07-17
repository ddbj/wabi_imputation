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
import java.util.ArrayList;
import java.util.LinkedHashMap;

import jp.ac.nig.ddbj.wabi.job.JobIdNotInitializedException;
import jp.ac.nig.ddbj.wabi.job.imputation.ImputationJobInfo;
import jp.ac.nig.ddbj.wabi.util.CalendarUtil;

/** WABI blastをGETメソッド、info="status"で呼んだときに返される情報を定義するクラス
 * 
 * ジョブの現在の状況は、以下のいずれかになる。 
 * <ul>
 * <li>"waiting"</li>
 * <li>"running"</li>
 * <li>"finished"</li>
 * <li>"not-found"</li>
 * </ul>
 * 
 * @author oogasawa
 *
 */
public class ImputationGetReportOfStatus extends LinkedHashMap<String,String> {
	
	public ImputationGetReportOfStatus(String requestId) throws IOException, JobIdNotInitializedException {
		
		ImputationJobInfo jobInfo = new ImputationJobInfo(requestId);

		
//		ArrayList<String> status = jobInfo.getStatus();
		ArrayList<String> status = jobInfo.getSlurmStatus();
		if (status == null) {
			this.put("error-message", "Unexpected error (status == null)");
			this.putAll(jobInfo.getInfo(false));			
		}
		else if (status.size() < 2) {
			this.put("error-message", "Unexpected error (status.size() == " + status.size() + ")");
			this.putAll(jobInfo.getInfo(false));
		}
		else {
			this.put("request-ID", requestId);
//			this.put("status", status.get(0));
			this.put("current-time", CalendarUtil.getTime());
//			this.put("system-info",status.get(1));
			this.put("system-info", String.join("", status));
		}
	}


}
