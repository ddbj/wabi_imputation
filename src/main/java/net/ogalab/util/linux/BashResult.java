package net.ogalab.util.linux;

import java.util.ArrayList;

import net.ogalab.util.fundamental.StringUtil;

public class BashResult {

	String stdout;
	String stderr;
	int    retCode;
	
	
	public ArrayList<String> getStdoutLines() {
		return StringUtil.splitByNewLine(stdout.trim());
	}

	public ArrayList<String> getStderrLines() {
		return StringUtil.splitByNewLine(stderr.trim());
	}
	
	public String getStdout() {
		return stdout;
	}
	public void setStdout(String stdout) {
		this.stdout = stdout;
	}
	public String getStderr() {
		return stderr;
	}
	public void setStderr(String stderr) {
		this.stderr = stderr;
	}
	public int getRetCode() {
		return retCode;
	}
	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

}
