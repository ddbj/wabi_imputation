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
package jp.ac.nig.ddbj.wabi.validator.imputation;

import org.springframework.validation.Errors;

import jp.ac.nig.ddbj.wabi.request.WabiRequest;
import jp.ac.nig.ddbj.wabi.request.imputation.ImputationRequest;
import jp.ac.nig.ddbj.wabi.util.ConfImputation;
import jp.ac.nig.ddbj.wabi.validator.WabiPostRequestValidator;

/**
 * POST リクエストされた CLUSTALW入力データ の妥当性を検証します.
 */
public class ImputationPostRequestValidator extends WabiPostRequestValidator {

	/**
	 * POST "/chipatlas" リクエストの入力値を検証します。
	 */
	@Override
	public void validate(Object target, Errors errors) {
		super.validate(target, errors);
		ImputationRequest request2 = (ImputationRequest) request;

		//パラメータのチェックを追加
//		ChipatlasRequestValidationUtil.validateParameters(request2.getParameters(), getAcceptOptions(request2), errors);
		
//		ChipatlasRequestValidationUtil.validateQsubOptions(request2.getQsubOptions(), getAcceptQsubOptions(request2), errors);
//		ChipatlasRequestValidationUtil.validateTypeA(request2.getTypeA(), errors);
	}

	/**
	 * Vecscreen入力データ の parameters値 として受け付け可能なオプションです。
	 * 例: blastall の場合は "ABCDEFGIJKLMPQSTUVWXYZabdefglmnqrstvwyz" 等。
	 */
	@Override
	protected String getAcceptOptions(WabiRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
