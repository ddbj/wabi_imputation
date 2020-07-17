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
package jp.ac.nig.ddbj.wabi.job.imputation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import jp.ac.nig.ddbj.wabi.controller.WabiController;
import jp.ac.nig.ddbj.wabi.controller.imputation.ImputationController;
import jp.ac.nig.ddbj.wabi.job.JobIdNotInitializedException;
import jp.ac.nig.ddbj.wabi.job.WabiJobInfo;
import jp.ac.nig.ddbj.wabi.util.Conf;
import net.ogalab.util.container.ArrayUtil;
import net.ogalab.util.fundamental.Type;
import net.ogalab.util.linux.Bash;
import net.ogalab.util.linux.BashResult;
import net.ogalab.util.os.FileIO;
import net.ogalab.util.rand.RNG;
import cern.jet.random.Uniform;

public class ImputationJobInfo extends WabiJobInfo {
	
	Pattern pRequestId = Pattern.compile("(.+?)_([0-9]{4})-([0-9]{2})([0-9]{2})-([0-9]{2})([0-9]{2})-([0-9]{2})-([0-9]+)-([0-9]+)");
	
	Pattern p1 = Pattern.compile("^Following jobs do not exists", Pattern.MULTILINE);
	Pattern p2 = Pattern.compile("^usage\\s+", Pattern.MULTILINE);
	Pattern p3 = Pattern.compile("^job_number:\\s+", Pattern.MULTILINE);
	Pattern p4 = Pattern.compile("^scheduling info:\\s+There are no messages available", Pattern.MULTILINE);

	// slurmのjob status判定
	Pattern sp1 = Pattern.compile("^slurm_load_jobs error: Invalid job id specified", Pattern.MULTILINE);
	Pattern sp2 = Pattern.compile("^\\s+\\d+(_\\d+)?\\s+\\S+\\s+\\S+\\s+\\S+\\s+R\\s+", Pattern.MULTILINE);
	Pattern sp3 = Pattern.compile("^\\s+\\d+(_\\[\\d+-\\d+\\])?\\s+\\S+\\s+\\S+\\s+\\S+\\s+PD\\s+", Pattern.MULTILINE);

	/** このディレクトリ以下にWebAPIによるプログラム実行結果などジョブの情報が置かれる */
//	public static String workingDirRoot = Conf.workingDirBase;

	public String[] jobIds = null;
	
	/** Random Number Generator Object (singleton) */
	RNG engine = null;

	/** ロックオブジェクト */
//	private final static Object lock = new Object();
	

	public ImputationJobInfo(RNG e) {
		super(e);
	}
	
	/** requestIdの文字列をパースすることによりjobInfoオブジェクトを作る。
	 * 
	 * @param requestId
	 * @throws IOException jobIDファイルがない。requestIdの文字列がおかしいかもしれません。
	 */
	public ImputationJobInfo(String requestId) throws IOException {
		super(requestId);

		Matcher m = pRequestId.matcher(requestId);
		if (m.matches()) {
			  prefix    = m.group(1);
			  year      = m.group(2);
			  month     = m.group(3);
			  day       = m.group(4);
			  hour      = m.group(5);
			  min       = m.group(6);
			  sec       = m.group(7);
			  millisec  = m.group(8);
			  randomSuffix  = m.group(9);
		}

		jobIds = readSlurmJobId();
		jobId = jobIds[0];
		
	}
	
	/** UGEのqstat, qacctを呼び出し、jobIdで表されるジョブの現在の状況を返す.
	 * 
	 * ジョブの現在の状況は、以下のいずれかになる。 
	 * <ul>
	 * <li>"waiting"</li>
	 * <li>"running"</li>
	 * <li>"finished"</li>
	 * <li>"not-found"</li>
	 * </ul>
	 * @throws IOException 
	 * 
	 */

	public boolean existsFinishedFile() {
		String path = getWorkingDir() + ImputationController.FINISHED_FILE;
		return new File(path).exists();
	}

	public boolean existsFinishedFiles(int start, int end) {
		for (int i = start; i <= end; i++) {
			String path = getWorkingDir() + ImputationController.FINISHED_FILE + "_" + String.valueOf(i);
			boolean exists = new File(path).exists();
			if (exists == false) {
				return false;
			}
		}
		return true;
	}

	public boolean existsOutFile() {
		String path = getWorkingDir() + ImputationController.WABI_OUT_FILE;
		return new File(path).exists();
	}

	public boolean existsOutFile(String number) {
		String path = getWorkingDir() + ImputationController.BASE_NAME + ".imputed.chr" + number;
		return new File(path).exists();
	}

	public ArrayList<String> getSlurmStatus() throws IOException, JobIdNotInitializedException {
		ArrayList<String> status = null;
		
		Bash bash = new Bash();
		bash.setWorkingDirectory(new File(getWorkingDir()));

		// jobId が初期化できているか確認しておく
		// jobId は解析データダウンロードジョブのジョブID
		if (null==jobId || jobId.isEmpty()) {
			/*
			 * Note: jobId は、 qsub実行結果から文字列として切り出して
			 * BlastController.UGE_JOB_ID_FILE ファイルに記載されている筈です。
			 * 稀に BlastController.UGE_JOB_ID_FILE ファイルが存在しなかったり、
			 * 又は中身に jobId が記述されていない、という異常系が発生し得ますが、
			 * 万が一その状態 (jobId が空文字列) で qstat, qacc コマンドを実行すると、
			 * 予期しない実行結果になります。
			 * 例: qacct コマンドは、全ての jobId の情報を出力してしまい、
			 * 他利用者の実行情報が漏れることになる。
			 */
			throw new JobIdNotInitializedException();
		}

		// slurmのjob status取得
		ArrayList<String> squeueStatus = squeueStatus(bash);
		ArrayList<String> sacctStatus = sacctStatus(bash);

		int index[] = {0, 2, 4};

		if (existsFinishedFile()) {
			status = new ArrayList<String>();
			status.add("finished");
			Pattern p = Pattern.compile(": finished");
			Matcher m = null;
			for (int i : index) {
				m = p.matcher(sacctStatus.get(i));
				if (m.find()) {
					status.add(sacctStatus.get(i + 1));
				}
			}
		} else {
			Pattern p1 = Pattern.compile(": waiting");
			Pattern p2 = Pattern.compile(": running");
			Matcher m0 = p1.matcher(squeueStatus.get(0));
			Matcher m1 = p1.matcher(squeueStatus.get(2));
			Matcher m2 = p1.matcher(squeueStatus.get(4));
			Matcher m4 = p2.matcher(squeueStatus.get(0));
			Matcher m5 = p2.matcher(squeueStatus.get(2));
			Matcher m6 = p2.matcher(squeueStatus.get(4));

			if (m0.find() || m1.find() || m2.find()) {
				status = squeueStatus;
			} else if (m4.find() || m5.find() || m6.find()) {
				status = squeueStatus;
			} else {
				status = new ArrayList<String>();
				status.add("not-found");
				status.add(squeueStatus.get(1) + "\n" + sacctStatus.get(1) + "\n");
				status.add(squeueStatus.get(3) + "\n" + sacctStatus.get(3) + "\n");
				status.add(squeueStatus.get(5) + "\n" + sacctStatus.get(5) + "\n");
			}
		}
		
		return status;
	}
	
	public ArrayList<String> squeueStatus(Bash bash)  {
		ArrayList<String> result = new ArrayList<String>();
//		result.add("");
//		result.add("");
		
		Matcher m1  = null;
		Matcher m2  = null;
		Matcher m3  = null;

		try {
			for (String eachJobId : jobIds) {
				BashResult res = bash.system("squeue -j " + eachJobId);
			
				String stdout = res.getStdout();

				m1 = sp1.matcher(stdout); // not exists in the queue.
				m2 = sp2.matcher(stdout); // all jobs are running.
				m3 = sp3.matcher(stdout); // some jobs are waiting.

				if (m1.find()) {
					result.add("JOBID " + eachJobId + ": not exist\n");
					result.add(stdout + "\n");
				} else if (m3.find()) {
					result.add("JOBID " + eachJobId + ": waiting\n");
					result.add(stdout + "\n");
				} else if (m2.find()) {
					result.add("JOBID " + eachJobId + ": running\n");
					result.add(stdout + "\n");
				} else {
					result.add("JOBID " + eachJobId + ": \n");
					result.add(stdout + "\n");
				}
			}
			
		} catch (Exception e) {
			// nothing to do.
		}
		return result;
	}
	
	// 以下はシングルジョブの場合
	// アレイジョブの各ジョブのstatus取得をどうするか。
	@SuppressWarnings("finally")
	public ArrayList<String> sacctStatus(Bash bash) {
		ArrayList<String> result = new ArrayList<String>();
		
		try {
			for (String eachJobId : jobIds) {
				BashResult res = bash.system("sacct -j " + jobId);
				if (!res.getStdout().contentEquals("")) {
					result.add("JOBID " + eachJobId + ": finished");
					result.add("\n" + res.getStdout());
				}
			}
		} catch (Exception e) {
			
		} finally {
			return result;
		}
	}
	
	/** Working Directory内にMutipartFileを指定されたファイル名でセーブする。
	 * 
	 * @param filename
	 * @param data
	 * @throws IOException
	 */
	public void saveData(String filename, MultipartFile data) throws IOException {
		makeWorkingDir();
		String path = getWorkingDir() + filename;
		Integer fileSize = (int) (data.getSize());
		byte[] dataBinary = new byte[fileSize];
		try {
			dataBinary = data.getBytes();
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(dataBinary);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void copyFile(String sourceFilePath, String outputFileName) throws IOException {
		makeWorkingDir();
		Path sourcePath = Paths.get(sourceFilePath);
		Path destinationPath = Paths.get(getWorkingDir() + outputFileName);
		try {
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addLine(String FileName, String line) throws IOException {
		File file = new File(getWorkingDir() + FileName);
		try {
			FileWriter filewriter = new FileWriter(file, true);
			filewriter.write(line);
			filewriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** jobIDがかかれたファイルの中身を読んでjobIDを取得し、それを文字列として返す。
	 * /home/geadmin/UGEB/ugeb/common/settings.sh
	 * リクエストは既に実行されていてjobIDは既に発行されていると前提している。
	 * 
	 * @return jobID
	 * @throws IOException 
	 */
	public String readJobId() throws IOException {
		String path = getWorkingDir() + ImputationController.UGE_JOB_ID_FILE;
		//String id   = FileIO.readFile(path);
		String id = "";
		if (new File(path).exists())
			id = FileIO.readFile(path);
		
		return id.trim();
	}
	
	public String[] readSlurmJobId() throws IOException {
		String path = getWorkingDir() + ImputationController.SLURM_JOB_ID_FILE;
		String idstr = "";
		if (new File(path).exists())
			idstr = FileIO.readFile(path);
		String[] ids = idstr.split("\n", 0);
		return ids;
	}

	/** requestIdが存在しなければ作成し、オブジェクトに登録する。既にオブジェクトに登録されているのであれば何もしない。
	 * 
	 * @return requestId
	 * @throws IOException 
	 */
	public String generateRequestId() throws IOException {
		String id = null;
		if (requestId == null)
			id = reGenerateRequestId();
		else
			id = requestId;
		
		return id;
	}
	
	/** requestIdが既に発行されているかどうかに関わらずrequestIdを再発行し、オブジェクトに登録する。
	 *  対応するディレクトリも作成する。（同一IDが既にとられていないかを判定するため）.
	 * 
	 * @return requestId
	 * @throws IOException 
	 */
	public String reGenerateRequestId() throws IOException {
		Calendar d = Calendar.getInstance();
		year      = String.format("%1$tY", d);
		month     = String.format("%1$tm", d);
		day       = String.format("%1$td", d);
		hour      = String.format("%1$tH", d);
		min       = String.format("%1$tM", d);
		sec       = String.format("%1$tS", d);
		millisec  = Type.toString(d.get(Calendar.MILLISECOND));
		

		// TODO 乱数発生要注意
		Uniform unif = new Uniform(RNG.getEngine());
//		Uniform unif = new Uniform(engine.getEngine());
		randomSuffix  = Type.toString(unif.nextIntFromTo(0, 1000000), 6);
		
		requestId = ImputationController.outfilePrefix + ArrayUtil.join("-", new String[]{year, month+day, hour+min, sec, millisec, randomSuffix}).trim();
		String dir = getWorkingDir(); // ディレクトリの名前だけが作られる。
		//ID重複防止のためロックする
		synchronized (lock) {
			// IDの重複がある場合はID発行やり直し
			if (new File(dir).exists()) // そのディレクトリが既にある
				requestId = reGenerateRequestId();
		
			makeWorkingDir();
		}
		return requestId;
	}
	
	public boolean existsUserRequestFile() {
		String path = getWorkingDir() + ImputationController.USER_REQUEST_FILE;
		return new File(path).exists();
	}
	
	/** Jobが動作するWorking Directoryのフルパス名をStringとして返す.
	 * 
	 * makeWorkingDir()メソッドを呼ぶまではディレクトリの実体は作成されていないかもしれない。
	 * 
	 * @return Working Directoryのフルパス名
	 */
	public String getWorkingDir() {
		return workingDirRoot + ArrayUtil.join("/", new String[]{year, month+day, hour+min, sec, millisec, randomSuffix}).trim() + "/";
	}
	
	public LinkedHashMap<String, String> getInfo(boolean withWorkingDirRoot) {
		LinkedHashMap<String, String> info = new LinkedHashMap<String, String>();
		info.put("requestId", requestId);
		info.put("jobId", jobId);
		if (withWorkingDirRoot) {
			info.put("workingDirRoot", workingDirRoot);
		}
		info.put("year", year);
		info.put("month", month);
		info.put("day", day);
		info.put("hour", hour);
		info.put("min", min);
		info.put("sec", sec);
		info.put("millisec", millisec);
		info.put("randomId", randomSuffix);
		
		return info;
	}

	/** Slurmのsbatchを実行してジョブをサブミットし、結果としてSlurmのjobIDを返す。
	 * 
	 * @param jobName
	 * @param scriptName
	 * @return サブミットされたJobのjobID.
	 * @throws IOException
	 */
	public String sbatch(String dependJobId, String jobName, String scriptName, String ... args) throws IOException {
		jobId = null;
		String sbatch = null;
		BashResult res = null;
		
		String argString = String.join(" ", args);
		
		makeWorkingDir();
		Bash bash = new Bash();
		bash.setWorkingDirectory(new File(getWorkingDir()));
		
		if (dependJobId == null) {
			sbatch = "sbatch --job-name=" + jobName + " " + scriptName + " " + argString;
		} else {
			sbatch = "sbatch --dependency=afterany:" + dependJobId + " --job-name=" + jobName + " " + scriptName + " " + argString;
		}

		System.out.println(getWorkingDir() + "\n" + sbatch);

		res = bash.system(sbatch);
		String stdout = res.getStdout();
		String stderr = res.getStderr();

		System.out.println("sbatch output: \n" + stdout + "\n" + stderr);
		
		Pattern p = Pattern.compile("Submitted batch job (\\d+)");
		Matcher m = p.matcher(stdout);
		if (m.find()) {
			jobId = m.group(1);
		}
		return jobId;
		
	}

	public String[] getSbatchStdoutFilenames() {
		ArrayList<String> files = new ArrayList<>();
		for (String eachJobId : jobIds) {
			files.add(getWorkingDir() + "out." + eachJobId + ".log");
		}
		return files.toArray(new String[files.size()]);
	}

	public String[] getSbatchStderrFilenames() {
		ArrayList<String> files = new ArrayList<>();
		for (String eachJobId : jobIds) {
			files.add(getWorkingDir() + "error." + eachJobId + ".log");
		}
		return files.toArray(new String[files.size()]);
	}


}
