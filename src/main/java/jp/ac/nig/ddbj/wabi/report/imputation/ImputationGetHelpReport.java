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

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import jp.ac.nig.ddbj.wabi.report.WabiGetHelpReport;
import jp.ac.nig.ddbj.wabi.request.WabiGetHelpRequest;
import jp.ac.nig.ddbj.wabi.util.ConfImputation;

public class ImputationGetHelpReport extends WabiGetHelpReport {
	public ImputationGetHelpReport(WabiGetHelpRequest request, boolean withResultOfQsub, boolean withGetenv) {
		try {
			if (null==request.getHelpCommand() || request.getHelpCommand().isEmpty()) {
				usage();
			} else if ("list_format".equals(request.getHelpCommand())) {
				setListFormat();
			} else if ("list_result".equals(request.getHelpCommand())) {
				setListResult();
			} else if ("list_info".equals(request.getHelpCommand())) {
				setListInfo(withResultOfQsub, withGetenv);
			} else {
				usage();
			}
		} catch (Exception e) {
			usage();
		}
	}

	protected static List<String> helpCommands = Arrays.asList(
			 "list_format",
			 "list_result",
			 "list_info"
	);

	@Override
	protected void usage() {
		this.put("help_commands", helpCommands);
		this.put("format", helpFormats);
	}

	@Override
	protected void setListParameters(String program) {
		// TODO Auto-generated method stub
		
	}

}
