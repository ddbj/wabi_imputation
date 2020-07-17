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
package jp.ac.nig.ddbj.wabi.request;

import net.arnx.jsonic.JSON;

/**
 * GETメソッド で WABIで実行するプログラムの Help情報 を参照する時に用いられる入力データを表わします.
 */
public class WabiGetHelpRequest {
	String helpCommand;
	String format = "text";
	String program;

	public WabiGetHelpRequest() {
	}

	public WabiGetHelpRequest(String format) {
		this.format = format;
	}

	public String toJsonStr() {
		return JSON.encode(this, true);
	}


	public String getHelpCommand() {
		return helpCommand;
	}
	public void setHelpCommand(String helpCommand) {
		this.helpCommand = helpCommand;
	}

	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
}
