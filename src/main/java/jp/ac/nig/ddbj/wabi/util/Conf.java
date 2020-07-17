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

public class Conf {
	/*
	 * Note: pom.xml 設定に応じて環境毎にリソースを切り替える。
	 * (pom.xml の project/profiles/profile/properties/resources.directory 要素を参照。)
	 * 例: 本番用は src/main/resources/env.properties
	 * 例: 統合テスト用は src/integration/resources/env.properties
	 * 例: 開発用は src/development/resources/env.properties
	 */
	private static ResourceBundle bundle = ResourceBundle.getBundle("env");
	
	/** このディレクトリの下にユーザーの計算結果などが置かれる。 */
	final static public String workingDirBase = bundle.getString("Conf.workingDirBase");
	// 例: workingDirBase = "/home/w3wabi/wabi/data/wabi-user-data/"
	
	/** qsub コマンドのオプション。 */
//	final static public String qsubOptions = bundle.getString("Conf.qsubOptions");
	// 例: qsubOptions = "-l s_vmem=64G -l mem_req=64G -pe def_slot 1 -cwd -N"
	
	
	/** UGEの設定シェルスクリプト */
//	final static public String ugeSetting = bundle.getString("Conf.ugeSetting");

}
