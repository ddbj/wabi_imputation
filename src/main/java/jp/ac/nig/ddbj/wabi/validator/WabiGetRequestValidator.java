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
package jp.ac.nig.ddbj.wabi.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.ac.nig.ddbj.wabi.request.WabiGetRequest;
import jp.ac.nig.ddbj.wabi.request.WabiRequest;

/**
 * GET リクエストされた BLAST入力データ の妥当性を検証します.
 */
public class WabiGetRequestValidator implements Validator {

	protected WabiGetRequest request = null;

	public boolean supports(Class clazz) {
		return WabiGetRequest.class.isAssignableFrom(clazz);
	}

	/**
	 * GET "/blast/{id}" リクエストの入力値を検証します。
	 */
	public void validate(Object target, Errors errors) {
		if (!(target instanceof WabiGetRequest)) {
			return;
		}
		request = (WabiGetRequest)target;
		WabiRequestValidationUtil.validateFormat(request.getFormat(), errors);
	}
}
