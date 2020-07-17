package net.ogalab.util.linux;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class Bash extends DefaultExecutor {

	long              timeout   = -1;

	public static void main(String[] args) {
		Bash bash = new Bash();
                
                BashResult res = bash.system("head < /proc/cpuinfo");
                System.out.println(res.getStdout());
                
		res = bash.system("env");
		System.out.println(res.getStdout());
		
		res = bash.system("ping -n 5 -w 20 127.0.0.1");
		System.out.println(res.getStderr());
                
                
	}
	
	
	/** Methods for starting synchronous execution.
	 * 
	 * @param com
	 * @return A string array of (stdout, stderr, retcode).
	 * @throws IOException 
	 */
	public BashResult system(String com)  {
		BashResult result = new BashResult();
		
		// setting stream handlers.
		ByteArrayOutputStream  stdout = new ByteArrayOutputStream();
		ByteArrayOutputStream  stderr = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(stdout, stderr);
		setStreamHandler(streamHandler);

		// making command line object.
		CommandLine c = new CommandLine("bash");
		c.addArgument("-c");
		c.addArgument(com, false);

		// synchronous execution.
		if (timeout > 0) {
			ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
			setWatchdog(watchdog);
		}
		
		int retCode = -100; // initialized by a dummy value.
		
		try {
			//executor.setExitValue(0);    // 正常終了の場合に返される値
			// 実行
			retCode = execute(c);
		} catch (ExecuteException ex) {
			// nothing to do.
			//ex.printStackTrace();
		} catch (IOException ex) {
			// nothing to do.
			//ex.printStackTrace();
		}
		finally {
			// getting outputs as strings.
			result.setStdout(stdout.toString());
			result.setStderr(stderr.toString());
			result.setRetCode(retCode);
		}

		return result;
	}
	
	public long getTimeout() {
		return timeout;
	}


	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

}
