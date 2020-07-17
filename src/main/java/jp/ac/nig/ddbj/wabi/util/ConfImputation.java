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

import java.util.ResourceBundle;

/**
 * Imputation 関連の設定です。
 */
public class ConfImputation {
	private static ResourceBundle bundle = ResourceBundle.getBundle("env_imputation");

	/**
	 * Imputationリクエスト の入力値検証で、各値が満たすべ正規表現パターンです。
	 */
	public static class RequestValidationPattern {

		/** format値 */
		public static final String format = bundle.getString("Conf.Imputation.RequestValidatorPattern.format");

		/** result値 */
		public static final String result = bundle.getString("Conf.Imputation.RequestValidatorPattern.result");

		/** address値 が満たすべき正規表現パターンです。 */
		public static final String address = bundle.getString("Conf.Imputation.RequestValidatorPattern.address");
		/** address値 のローカル部が満たすべき正規表現パターンです。 */
		public static final String address_localpart = bundle.getString("Conf.Imputation.RequestValidatorPattern.address.localpart");
		/** address値 のドメイン部が満たすべき正規表現パターンです。 */
		public static final String address_domainpart = bundle.getString("Conf.Imputation.RequestValidatorPattern.address.domainpart");

		/** requestId値 */
		public static final String requestId = bundle.getString("Conf.Imputation.RequestValidatorPattern.requestId");

		/** info値 */
		public static final String info = bundle.getString("Conf.Imputation.RequestValidatorPattern.info");

		/** getenvリクエスト時 の info値 */
		public static final String infoEnv = bundle.getString("Conf.Imputation.RequestValidatorPattern.infoEnv");

	}

	/** リクエスト で GET getenv の実行を許可されている 接続元IPアドレス が満たすべき正規表現パターンです。 */
	public static final String patternGetenvPermittedRemoteAddr = bundle.getString("Conf.Imputation.patternGetenvPermittedRemoteAddr");

	/** リクエスト で GET result_stdout, result_stderr の実行を許可されている 接続元IPアドレス が満たすべき正規表現パターンです。 */
	public static final String patternGetResultOfQsubPermittedRemoteAddr = bundle.getString("Conf.Imputation.patternGetResultOfQsubPermittedRemoteAddr");

	/** リクエスト で GET status で system-info の出力を許可されている 接続元IPアドレス が満たすべき正規表現パターンです。 */
	public static final String patternGetStatusOfQsubPermittedRemoteAddr = bundle.getString("Conf.Imputation.patternGetStatusOfQsubPermittedRemoteAddr");

	/** 脆弱性診断ページの GET を許可されている 接続元IPアドレス が満たすべき正規表現パターンです。 */
//	public static final String patternTestSecurityApplicationScanPagePermittedRemoteAddr = bundle.getString("Conf.Imputation.patternTestSecurityApplicationScanPagePermittedRemoteAddr");

	
	/** ImputationExecCommand path */
	/** qsubで実行するコマンドのパス。今のところ実行するコマンド名をPOSTで送っているのでこれは使っていない。*/
	final static public String execHLACommand = bundle.getString("Conf.Imputation.execHLACommand");
	final static public String execSNPCommand = bundle.getString("Conf.Imputation.execSNPCommand");
	final static public String downloadCommand = bundle.getString("Conf.Imputation.downloadCommand");
	final static public String uploadCommand = bundle.getString("Conf.Imputation.uploadCommand");
//	final static public String monitorCommand = bundle.getString("Conf.Imputation.monitorCommand");
	
	/** imputationのqsubで実行するblastコマンドを格納したSingularity imageのパス */
//	final static public String singularityImagePath = bundle.getString("Conf.Imputation.singularityImagePath");
	
	/** imputationのqsubで実行するblastコマンドが参照するBlastDBのパス */	
//	final static public String blastDbPath = bundle.getString("Conf.Imputation.blastDbPath");

	/**
	 * Help情報 に関する設定です。
	 */
	public static class Help {

	}
}
