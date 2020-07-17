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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ac.nig.ddbj.wabi.util.ConfWabi;

import org.springframework.validation.Errors;

/**
 * リクエストされた BLAST入力データ の妥当性を検証します.
 */
public abstract class WabiRequestValidationUtil {
	/*
	 * Note: patternParameters等 は、入力値が
	 * セキュリティ上の問題を起こす可能性があるか判定するための
	 * ホワイトリストです。
	 * 問題を起こし得る入力値の例として、
	 * 「foo; bar | baz」のような「;」、「|」等の文字を含むものの他に、
	 * 「%」や「\」などの HTTP上 のエンコーディングに関係する文字などが
	 * 考えられます。
	 */
	protected static Pattern patternFormat = Pattern.compile(ConfWabi.RequestValidationPattern.format);
	protected static Pattern patternResult = Pattern.compile(ConfWabi.RequestValidationPattern.result);
	protected static Pattern patternAddress = Pattern.compile(ConfWabi.RequestValidationPattern.address);
	protected static Pattern patternAddress_localpart = Pattern.compile(ConfWabi.RequestValidationPattern.address_localpart);
	protected static Pattern patternAddress_domainpart = Pattern.compile(ConfWabi.RequestValidationPattern.address_domainpart);
	protected static Pattern patternRequestId = Pattern.compile(ConfWabi.RequestValidationPattern.requestId);
	protected static Pattern patternInfo = Pattern.compile(ConfWabi.RequestValidationPattern.info);
	protected static Pattern patternInfoEnv = Pattern.compile(ConfWabi.RequestValidationPattern.infoEnv);

	/*
	 * Note: 無限ループ抑止のため、字句数に上限あり。
	 */
	protected static final int RECURSIVE_LIMIT = 1000;


	static void validateQuerySequence(String data, Errors errors) {
		/*
		 * Note: 実装なし。
		 * この querySequence値 はファイルに保存されて、
		 * そのファイル名を BLASTコマンド に与えているだけなので、
		 * OSコマンド・インジェクションのようなセキュリティ問題には
		 * 関係してこない。
		 * 値に不備がある場合は BLAST側 でエラーにする。
		 */
	}


	public static void validateFormat(String format, Errors errors) {
		if (null==format || format.isEmpty()) {
			errors.rejectValue("format", "error.illegal_arguments");
		} else if (!patternFormat.matcher(format).matches()) {
			errors.rejectValue("format", "error.illegal_arguments");
		}
	}

	static void validateResult(String result, Errors errors) {
		if (null==result || result.isEmpty()) {
			errors.rejectValue("result", "error.illegal_arguments");
		} else if (!patternResult.matcher(result).matches()) {
			errors.rejectValue("result", "error.illegal_arguments");
		}
	}

	/*
	 * address値 を検証して、不備があった場合は errors に追加します。
	 * result値 が "mail" だった場合は必須です。
	 *
	 * @param address address値
	 * @param result result値
	 * @param errors 検証で不備を見つけた場合に追加します。
	 */
	static void validateAddress(String address, String result, Errors errors) {
		if (null==address || address.isEmpty()) {
			if ("mail".equals(result)) {
				errors.rejectValue("address", "error.illegal_arguments");
			}
		} else {
			Matcher matcher = patternAddress.matcher(address);
			if (!matcher.matches()) {
				errors.rejectValue("address", "error.illegal_arguments");
			} else {
				String localpart = matcher.group(1);
				String domainpart = matcher.group(2);
				if (!patternAddress_localpart.matcher(localpart).matches()) {
					errors.rejectValue("address", "error.illegal_arguments");
				} else if (!patternAddress_domainpart.matcher(domainpart).matches()) {
					errors.rejectValue("address", "error.illegal_arguments");
				}
			}
		}
	}

	static void validateInfo(String info, boolean isRequired, Errors errors) {
		if (null==info || info.isEmpty()) {
			if (isRequired) {
				errors.rejectValue("info", "error.illegal_arguments");
			} else {
				; // Note: imageId値 が指定されている場合には、 info値 は必須ではない。
			}
		} else if (!patternInfo.matcher(info).matches()) {
			errors.rejectValue("info", "error.illegal_arguments");
		}
	}


	public static void validateInfoEnv(String info, Errors errors) {
		if (null==info || info.isEmpty()) {
			errors.rejectValue("info", "error.illegal_arguments");
		} else if (!patternInfoEnv.matcher(info).matches()) {
			errors.rejectValue("info", "error.illegal_arguments");
		}
	}
	
}
