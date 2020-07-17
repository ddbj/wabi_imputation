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

import jp.ac.nig.ddbj.wabi.request.WabiRequest;

/**
 * POST リクエストされた CLUSTALW入力データ の妥当性を検証します.
 */
public abstract class WabiPostRequestValidator implements Validator {
	
	protected WabiRequest request = null;
	
	public boolean supports(Class<?> clazz) {
		return WabiRequest.class.isAssignableFrom(clazz);
	}

	/**
	 * POSTリクエストの入力値を検証します。
	 */
	public void validate(Object target, Errors errors) {
		if (!(target instanceof WabiRequest)) {
			return;
		}
		request = (WabiRequest)target;
//		WabiRequestValidationUtil.validateQuerySequence(request.getDataFile(), errors);
//		WabiRequestValidationUtil.validateFormat(request.getFormat(), errors);
//		WabiRequestValidationUtil.validateResult(request.getResult(), errors);
//		WabiRequestValidationUtil.validateAddress(request.getAddress(), request.getResult(), errors);
	}

	/**
	 * CLUSTALW入力データ の parameters値 として受け付け可能なオプションです。
	 * 例: blastall の場合は "ABCDEFGIJKLMPQSTUVWXYZabdefglmnqrstvwyz" 等。
	 */
	protected abstract String getAcceptOptions(WabiRequest request);
}
