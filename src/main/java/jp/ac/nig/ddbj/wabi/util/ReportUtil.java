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
package jp.ac.nig.ddbj.wabi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReportUtil {
	
	public static LinkedHashMap<String, String> makeSubset(Map<String, String> data, String[] keyList) {
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		for (String k : keyList) {
			result.put(k, data.get(k));
		}
		return result;
	}
	
}
