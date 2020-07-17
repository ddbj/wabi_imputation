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

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import jp.ac.nig.ddbj.wabi.job.imputation.ImputationJobInfo;
import jp.ac.nig.ddbj.wabi.util.ConfImputation;
import jp.ac.nig.ddbj.wabi.util.ConfWabi;
import jp.ac.nig.ddbj.wabi.validator.WabiRequestValidationUtil;

import org.springframework.validation.Errors;

/**
 * リクエストされた 入力データ の妥当性を検証します.
 */
public class ImputationRequestValidationUtil extends WabiRequestValidationUtil {
	/*
	 * Note: patternParameters等 は、入力値が
	 * セキュリティ上の問題を起こす可能性があるか判定するための
	 * ホワイトリストです。
	 * 問題を起こし得る入力値の例として、
	 * 「foo; bar | baz」のような「;」、「|」等の文字を含むものの他に、
	 * 「%」や「\」などの HTTP上 のエンコーディングに関係する文字などが
	 * 考えられます。
	 */
	

	/**
	 * parameters値 を検証して、不備があった場合は errors に追加します。
	 *
	 * @param parameters parameters値
	 * @param acceptOptions Vecscreen入力データ の parameters値 として受け付け可能なオプションです。
	 *     -f [0-3]のみ。
	 * @param errors 検証で不備を見つけた場合に追加します。
	 */
	static void validateParameters(String parameters, String acceptOptions, Errors errors) {
		if (null == parameters || parameters.isEmpty()) {
			return;
		} else {
			errors.rejectValue("parameters", "error.illegal_arguments1");
		}
	}

	/**
	 * validateParameters(String parameters, String acceptOptions, Errors errors)のErrorsを使わないバージョン
	 * @param parameters
	 * @param acceptOptions
	 */
	static String validateParameters(String parameters, String acceptOptions) {
		if (null == parameters || parameters.isEmpty()) {
			return "no error";
		} else {
			return "no parameter is needed.";
		}
	}
	
/*	static void validateQsubParameters(String parameters, String acceptOptions, Errors errors) {
		if (null == parameters || parameters.isEmpty()) {
			return;
		} else {
			//チェック処理
		}
	}
*/
	static void validateRequestId(String requestId, Errors errors) {
		if (null==requestId || requestId.isEmpty()) {
			errors.rejectValue("requestId", "error.illegal_arguments");
		} else if (0<requestId.indexOf(File.pathSeparator) || 0<requestId.indexOf(File.separator)) {
			// Note: パス区切り文字 ":" 、名前区切り文字 "/" 等は、意図しないディレクトリを参照する恐れがある。
			errors.rejectValue("requestId", "error.illegal_arguments");
		} else if (!patternRequestId.matcher(requestId).matches()) {
			errors.rejectValue("requestId", "error.illegal_arguments");
		} else {
			try {
				ImputationJobInfo jobInfo = new ImputationJobInfo(requestId);
				if (!jobInfo.existsWorkingDir()) {
					// Note: requestId 発行時に workingDir も作成済みだが、一定日数超過後は削除される。
					errors.rejectValue("requestId", "error.not_found");
				}
			} catch (IOException e) {
				errors.rejectValue("requestId", "error.illegal_arguments");
			}
		}
	}

}
