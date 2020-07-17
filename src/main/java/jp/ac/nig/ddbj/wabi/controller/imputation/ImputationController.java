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
package jp.ac.nig.ddbj.wabi.controller.imputation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import jp.ac.nig.ddbj.wabi.controller.BadRequestException;
import jp.ac.nig.ddbj.wabi.controller.InternalServerErrorException;
import jp.ac.nig.ddbj.wabi.controller.NotFoundException;
import jp.ac.nig.ddbj.wabi.controller.WabiController;
import jp.ac.nig.ddbj.wabi.job.imputation.ImputationJobInfo;
import jp.ac.nig.ddbj.wabi.job.JobIdNotInitializedException;
import jp.ac.nig.ddbj.wabi.job.WabiJobInfo;
import jp.ac.nig.ddbj.wabi.report.WabiGetErrorReport;
import jp.ac.nig.ddbj.wabi.report.WabiGetHelpReport;
import jp.ac.nig.ddbj.wabi.report.imputation.ImputationErrorReport;
import jp.ac.nig.ddbj.wabi.report.imputation.ImputationGetErrorReport;
import jp.ac.nig.ddbj.wabi.report.imputation.ImputationGetHelpReport;
import jp.ac.nig.ddbj.wabi.report.imputation.ImputationGetReportOfStatus;
import jp.ac.nig.ddbj.wabi.report.imputation.ImputationIllegalRequestReport;
import jp.ac.nig.ddbj.wabi.report.imputation.ImputationPostReport;
import jp.ac.nig.ddbj.wabi.request.WabiGetHelpRequest;
import jp.ac.nig.ddbj.wabi.request.WabiGetRequest;
import jp.ac.nig.ddbj.wabi.request.WabiGetenvRequest;
import jp.ac.nig.ddbj.wabi.request.WabiRequest;
import jp.ac.nig.ddbj.wabi.request.imputation.ImputationGetRequest;
import jp.ac.nig.ddbj.wabi.request.imputation.ImputationRequest;
import jp.ac.nig.ddbj.wabi.util.Conf;
import jp.ac.nig.ddbj.wabi.util.ConfImputation;
import jp.ac.nig.ddbj.wabi.validator.WabiGetenvRequestValidator;
import jp.ac.nig.ddbj.wabi.validator.imputation.ImputationPostRequestValidator;
import jp.ac.nig.ddbj.wabi.validator.imputation.ImputationGetRequestValidator;
import net.ogalab.util.linux.Bash;
import net.ogalab.util.linux.BashResult;
import net.ogalab.util.rand.RNG;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ImputationController extends WabiController {

        public static final String DATA_FILE = "data.vcf.gz";
        public static final String BASE_NAME = "data";
	public static final String SLURM_JOB_ID_FILE = "slurm_job_id.txt";
	public static final String DATA_FILE_DOWNLOAD_SCRIPT_FILE_SOURCE = ConfImputation.downloadCommand;
	public static final String DATA_FILE_SNP_ANALYSIS_SCRIPT_FILE_SOURCE = ConfImputation.execSNPCommand;
	public static final String DATA_FILE_HLA_ANALYSIS_SCRIPT_FILE_SOURCE = ConfImputation.execHLACommand;
	public static final String RESULT_FILE_UPLOAD_SCRIPT_FILE_SOURCE = ConfImputation.uploadCommand;
//	public static final String JOB_MONITOR_SCRIPT_FILE_SOURCE = ConfImputation.monitorCommand;
	public static final String DATA_FILE_DOWNLOAD_SCRIPT_FILE = "wabi_imputation_download.sh";
	public static final String DATA_FILE_ANALYSIS_SCRIPT_FILE = "wabi_imputation_exec.sh";
	public static final String RESULT_FILE_UPLOAD_SCRIPT_FILE = "wabi_imputation_upload.sh";
//	public static final String JOB_MONITOR_SCRIPT_FILE = "wabi_imputation_monitor.sh";
	public static final String WABI_OUT_FILE = "wabi_imputation_result.tar.gz";
	public static final String ERR_OUT_FILE = "error_out";

	public static String outfilePrefix = "wabi_imputation_";
	
	public static Pattern patternGetenvPermittedRemoteAddr
		= Pattern.compile(ConfImputation.patternGetenvPermittedRemoteAddr);
	public static Pattern patternGetResultOfQsubPermittedRemoteAddr
		= Pattern.compile(ConfImputation.patternGetResultOfQsubPermittedRemoteAddr);
	public static Pattern patternGetStatusOfQsubPermittedRemoteAddr
		= Pattern.compile(ConfImputation.patternGetStatusOfQsubPermittedRemoteAddr);
//	public static Pattern patternTestSecurityApplicationScanPagePermittedRemoteAddr
//		= Pattern.compile(ConfImputation.patternTestSecurityApplicationScanPagePermittedRemoteAddr);
	
//	public static String singularityImagePath = ConfImputation.singularityImagePath;
//	public static String blastDbPath = ConfImputation.blastDbPath;

	@Inject
	MessageSource messageSource;

	RNG engine = null;

	@Inject
	public ImputationController(RNG engine) {
		super(engine);
	}

	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/*
		 * Note: 脆弱性対策
		 * 不正なパラメータを指定された時に HTTP 500 エラーになっていたので、
		 * それを回避するために、有効なパラメータ名を列挙して指定しておく。
		 */
		binder.setAllowedFields("format", "result", "address",
								"dataFile", "user_id", "analysis_id", "analysis_type", "download_path", "upload_path",
								"format", "info",
								"requestId", "format", "info",
								"helpCommand", "format");
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
	/**
	 * HTTP POSTメソッドで呼ばれた時の処理を行う。Job(wabi)の投入を行い、当該Job(wabi)のID
	 * (request-idと呼ぶ）を作成して返す.
	 * 
	 * @param request
	 * @return
	 */
	@Override
	@RequestMapping(value = "/imputation_dummy", method = RequestMethod.POST)
	public ModelAndView post(
			@ModelAttribute WabiRequest request,
			BindingResult errors
			) throws BadRequestException,
			         InternalServerErrorException {

		ModelAndView result = null;
		return result;
	}
	
	
	@RequestMapping(value = "/imputation", method = RequestMethod.POST)
	public ModelAndView post(
			@Validated @ModelAttribute ImputationRequest request,
			BindingResult errors
			) throws BadRequestException,
				     InternalServerErrorException, 
				     IOException {

		ModelAndView result = null;

		// ImputationRequestクラスのannotationによるvalidation
		if (errors.hasErrors()) {
			ImputationErrorReport report = new ImputationErrorReport(request);
			report.put("Message", "Error (POST parameters validation error)");
			for (ObjectError error : errors.getAllErrors()) {
			    report.put("AnnotationMessage", error.getDefaultMessage());
			}
			System.out.println(report.get("current-time") + "["
					+ getClass() + "#get] result: (" + report + ")");
			throw new BadRequestException(report);
		}

		try {
			// 現在時刻を使って新規request-idを作成する。
			ImputationJobInfo jobInfo = new ImputationJobInfo(engine);
			jobInfo.generateRequestId();

			// 新規Job(wabi)を実行する際のワーキングディレクトリ作成
			jobInfo.makeWorkingDir();

			//ジョブスクリプトをワーキングディレクトリにコピー
			jobInfo.copyFile(DATA_FILE_DOWNLOAD_SCRIPT_FILE_SOURCE, DATA_FILE_DOWNLOAD_SCRIPT_FILE);
			jobInfo.copyFile(RESULT_FILE_UPLOAD_SCRIPT_FILE_SOURCE, RESULT_FILE_UPLOAD_SCRIPT_FILE);
//			jobInfo.copyFile(JOB_MONITOR_SCRIPT_FILE_SOURCE, JOB_MONITOR_SCRIPT_FILE);

			if (request.getAnalysis_type() == 1) {
				jobInfo.copyFile(DATA_FILE_HLA_ANALYSIS_SCRIPT_FILE_SOURCE, DATA_FILE_ANALYSIS_SCRIPT_FILE);				
			} else if (request.getAnalysis_type() == 2) {
				jobInfo.copyFile(DATA_FILE_SNP_ANALYSIS_SCRIPT_FILE_SOURCE, DATA_FILE_ANALYSIS_SCRIPT_FILE);
			}

			// Request(user), infiniumDataFileをワーキングディレクトリ中にファイルとしてセーブ
			//dataFileを取り除かないとjson変換時にエラーになる。
			ImputationRequest request_dummy = new ImputationRequest();
			request_dummy.setUser_id(request.getUser_id());
			request_dummy.setAnalysis_id(request.getAnalysis_id());
			request_dummy.setAnalysis_type(request.getAnalysis_type());
			request_dummy.setDownload_path(request.getDownload_path());
			request_dummy.setUpload_path(request.getUpload_path());
			jobInfo.save(USER_REQUEST_FILE, request_dummy.toJsonStr());

			//jobInfo.save(DATA_FILE, request.getDataFile());
			//jobInfo.saveData(DATA_FILE, request.getDataFile());

			// セキュリティのためにリクエスト値をチェックする。
			// だが、先にImputationRequestクラスのannotationによるvalidationを行っているため、ここでやることはない。
			// qsubのコマンドラインオプションをきちんとチェックする場合、ここでやる。
			
			Validator validator = new ImputationPostRequestValidator();
			validator.validate(request, errors);
			if (errors.hasErrors()) {
				ImputationIllegalRequestReport report = new ImputationIllegalRequestReport(request);
				jobInfo.save(ILLEGAL_ARGUMENTS_FILE, errors.getAllErrors().toString());
				throw new BadRequestException(report);
			}

//			String jobName = "Imputation_job";

			// 解析ファイルダウンロードジョブをslurmに投入
			String jobId1 = jobInfo.sbatch(
				null, 
				"imputation_download", 
				DATA_FILE_DOWNLOAD_SCRIPT_FILE, 
				jobInfo.getWorkingDir(), 
				request.getDownload_path(), 
				DATA_FILE);

			// 解析ジョブをslurmに投入
			String jobId2 = jobInfo.sbatch(
				jobId1, 
				"imputation_exec",  
				DATA_FILE_ANALYSIS_SCRIPT_FILE, 
				jobInfo.getWorkingDir() + DATA_FILE);
			
			// 結果ファイルアップロードジョブをslurmに投入
			String jobId3 = jobInfo.sbatch(
				jobId2, 
				"imputation_upload", 
				RESULT_FILE_UPLOAD_SCRIPT_FILE, 
				jobInfo.getWorkingDir(), 
				BASE_NAME, 
				WABI_OUT_FILE, 
				request.getUpload_path());

			// jobId, jobInfoをファイルに保存
			ArrayList<String> jobIds = new ArrayList<String>();
			jobIds.add(jobId1);
			jobIds.add(jobId2);
			jobIds.add(jobId3);
			jobInfo.save(SLURM_JOB_ID_FILE, String.join("\n", jobIds));
			jobInfo.save(JOB_INFO_FILE, jobInfo.getInfoAsJson(true));

			// 新規作成ジョブに関するレポートを返す
			ImputationPostReport report = new ImputationPostReport(jobInfo, request);
//			result = new ModelAndView(request.getFormat(), "linked-hash-map", report);
			result = new ModelAndView("json", "linked-hash-map", report);
		} catch (IOException e) {
			e.printStackTrace();
			LinkedHashMap<String, Object> report = null;
			try {
				report = new ImputationErrorReport(request);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new InternalServerErrorException(report);
		}

		return result;
		
	}
	
	@Override
	public ModelAndView get(
			String requestId,
			WabiGetRequest request,
			BindingResult errors,
			HttpServletRequest req
			) throws IOException, BadRequestException, NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * GETメソッドで呼ばれた時の処理を行う。request-idで表されるJobの現在の状態を返す.
	 * 
	 * @param requestId
	 *            jobを特定するためのID文字列。
	 * @param request
	 *            GET入力データ (パラメータは format, imageId, info の 3種)
	 * @param req
	 *            HTTPリクエスト
	 * @param errors
	 *            エラー情報 (パラメータからモデルへの変換処理でのエラー情報を格納済み)CLUSTALW
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/imputation/{id}", method = RequestMethod.GET)
	public ModelAndView get(
			@PathVariable("id") String requestId,
			@Validated @ModelAttribute ImputationGetRequest request,
			BindingResult errors,
			HttpServletRequest req
				) throws IOException, BadRequestException, NotFoundException {

		request.setRequestId(requestId);
		
		ModelAndView result = null;

		
		// ImputationGetRequestクラスのannotationによるvalidation
		if (errors.hasErrors()) {
			ImputationGetErrorReport report = new ImputationGetErrorReport(request);
			report.put("Message", "Error (GET parameters validation error)");
			System.out.println(report.get("current-time") + "["
					+ getClass() + "#get] result: (" + report + ")");
			throw new BadRequestException(report);
		}

		// ジョブが3個投入されるように変わったので、statusの取り方を変える必要がある。
		// 個別の出力ファイルのダウンロード
		Pattern p = Pattern.compile("^result(\\d+)$");
		Matcher m = p.matcher(request.getInfo());
		if (m.find()) {
			ImputationJobInfo jobInfo = new ImputationJobInfo(requestId);
			if (jobInfo.existsOutFile(m.group(1))) {
				result = new ModelAndView(
						"binaryfile", 
						"filename", 
						jobInfo.getWorkingDir() + BASE_NAME + ".imputed.chr" + m.group(1) + "vcf.gz");
			} else {
				ImputationGetErrorReport report = new ImputationGetErrorReport(request);
				report.put("Message",
						"Error ( Result file of your request id have been NOT FOUND, or still running.)");
				System.out.println(report.get("current-time") + "["
						+ getClass() + "#get] result: (" + report + ")");
				throw new NotFoundException(report);
			}
		}

		if (request.getInfo().equals("status")) {
			try {
				ImputationGetReportOfStatus report = new ImputationGetReportOfStatus(requestId);
				result = new ModelAndView(request.getFormat(), "linked-hash-map", report);
			} catch (JobIdNotInitializedException e) {
				LinkedHashMap<String, Object> report = new ImputationGetErrorReport(request);
				report.put("Message", "Error (" + e + ")");
				System.out.println(report.get("current-time") + "["
						+ getClass() + "#get] status: (" + report + ")");
				throw new BadRequestException(report);
			}
		} else if (request.getInfo().equals("result")) {
			ImputationJobInfo jobInfo = new ImputationJobInfo(requestId);

			// tgzファイル生成済みの場合
			if (jobInfo.existsOutFile()) {
				result = new ModelAndView("binaryfile", "filename", jobInfo.getWorkingDir() + WABI_OUT_FILE);
			} else {
				ImputationGetErrorReport report = new ImputationGetErrorReport(request);
				report.put("Message",
						"Error ( Result file of your request id have been NOT FOUND, or still running.)");
				System.out.println(report.get("current-time") + "["
						+ getClass() + "#get] result: (" + report + ")");
				throw new NotFoundException(report);
			}

		} else if (request.getInfo().equals("request")) {
			ImputationJobInfo jobInfo = new ImputationJobInfo(requestId);

			if (jobInfo.existsUserRequestFile()) {
				String userRequestFile = jobInfo.getWorkingDir() + USER_REQUEST_FILE;
				result = new ModelAndView("requestfile", "filename", userRequestFile);
			} else {
				ImputationGetErrorReport report = new ImputationGetErrorReport(request);
				report.put("Message",
						"Unexpected error ( Results of your request id have been NOT FOUND.)");
				System.out.println(report.get("current-time") + "["
						+ getClass() + "#get] request: (" + report + ")");
				throw new NotFoundException(report);
			}
		} else if (request.getInfo().equals("result_stdout")) {
			result = getResultOfQsubOutput(requestId, request, req, true);
		} else if (request.getInfo().equals("result_stderr")) {
			result = getResultOfQsubOutput(requestId, request, req, false);
		}

		return result;

	}

	/**
	 * GETメソッドで呼ばれた時の処理を行う。request-idで表されるJobの現在の状態を返す.
	 * 
	 * @param request
	 *            GET入力データ (パラメータは format, info の 2種)
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/imputation", method = RequestMethod.GET)
	public ModelAndView getenv(@ModelAttribute WabiGetenvRequest request,
			BindingResult errors, HttpServletRequest req) throws IOException,
			NotFoundException, BadRequestException {

		ModelAndView result = null;
		String message = "imputation";
		result = new ModelAndView("text2", "message", message);

		return result;

	}

	/**
	 * Help 情報の使い方を返します。 /imputation/help/help_command の「help_command」の選択肢を返します。
	 */
	@RequestMapping(value = "/imputation/help", method = RequestMethod.GET)
	public ModelAndView help(@ModelAttribute WabiGetHelpRequest request,
			BindingResult errors, HttpServletRequest req) {
		return help(null, request, errors, req);
	}

	/**
	 * Help 情報を返します。
     * 例: /blast/help/list_program
	 * 
	 * help_command が省略された場合は、その使い方を返します。 /blast/help/help_command
	 * の「help_command」の選択肢を返します。
	 */
	@RequestMapping(value = "/imputation/help/{help_command}", method = RequestMethod.GET)
	public ModelAndView help(
			@PathVariable("help_command") String helpCommand,
			@ModelAttribute WabiGetHelpRequest request,
			BindingResult errors,
			HttpServletRequest req) {
		request.setHelpCommand(helpCommand);
		if (!"text".equals(request.getFormat())
				&& !"json".equals(request.getFormat())
				&& !"xml".equals(request.getFormat())) {
			request.setFormat("text");
		}

		boolean isPermittedRemoteAddrResultOfQsub = isPermittedRemoteAddr(req,
				patternGetResultOfQsubPermittedRemoteAddr);
		boolean isPermittedRemoteAddrGetenv = isPermittedRemoteAddr(req,
				patternGetenvPermittedRemoteAddr);
		WabiGetHelpReport report = new ImputationGetHelpReport(request,
				isPermittedRemoteAddrResultOfQsub, isPermittedRemoteAddrGetenv);
		return new ModelAndView(request.getFormat(), "linked-hash-map", report);
	}

	/**
	 * 計算を行うbash scriptを作成する.
	 * 
	 * <code>
	 * singularity exec --bind <blastDbPath> <singularityImagePath> <command> \
	 *     -db <blastDbPath><database> -query dataFile -out outFile
	 * </code>
	 * 
	 * @param request
	 * @param infile
	 * @param outfile
	 * @return
	 * @throws IOException
	 */
	@Override
	public String makeShellScript(
			WabiRequest request,
			String infile,
			String outfile,
			WabiJobInfo jobInfo
			) throws IOException {
		return null;
	}

	protected ModelAndView getResultOfQsubOutput(String requestId,
			WabiGetRequest request, HttpServletRequest req, boolean isStdout)
			throws IOException, NotFoundException {
		/*
		 * Note: qsub の標準出力にはシステム情報が含まれ得るので、 接続元IPアドレス で拒否します。 システム情報の例:
		 * パスやアカウント名など。
		 */
		checkRemoteAddr(req, patternGetResultOfQsubPermittedRemoteAddr);
		WabiJobInfo jobInfo = new ImputationJobInfo(requestId);
		String jobName = makeJobName(jobInfo);
		String qsubOutFilename = isStdout ? jobInfo
				.getQsubStdoutFilename(jobName) : jobInfo
				.getQsubStderrFilename(jobName);
		if (null == jobName || jobInfo.existsFile(qsubOutFilename)) {
			WabiGetErrorReport report = new ImputationGetErrorReport(request);
			report.put(
					"Message",
					"Error ( "
							+ (isStdout ? "Stdout" : "Stderr")
							+ " of your request id have been NOT FOUND, or still running.)");
			System.out.println(report.get("current-time") + "[" + getClass()
					+ "#get] " + (isStdout ? "result_stdout" : "result_stderr")
					+ ": (" + report + ")");
			throw new NotFoundException(report);
		}
		return new ModelAndView("bigfile", "filename", qsubOutFilename);
	}
	
	protected ModelAndView getResultOfSlurmOutput(String requestId,
			WabiGetRequest request, HttpServletRequest req, boolean isStdout)
			throws IOException, NotFoundException {

		checkRemoteAddr(req, patternGetResultOfQsubPermittedRemoteAddr);
		WabiJobInfo jobInfo = new ImputationJobInfo(requestId);
		String jobName = makeJobName(jobInfo);
		String[] slurmOutFilenames = isStdout ? jobInfo
				.getSbatchStdoutFilenames() : jobInfo
				.getSbatchStderrFilenames();
		if (null == jobName || jobInfo.existsFile(slurmOutFilenames[0])) {
			WabiGetErrorReport report = new ImputationGetErrorReport(request);
			report.put(
					"Message",
					"Error ( "
							+ (isStdout ? "Stdout" : "Stderr")
							+ " of your request id have been NOT FOUND, or still running.)");
			System.out.println(report.get("current-time") + "[" + getClass()
					+ "#get] " + (isStdout ? "result_stdout" : "result_stderr")
					+ ": (" + report + ")");
			throw new NotFoundException(report);
		}
		
		return new ModelAndView("bigfiles", "filenames", slurmOutFilenames);
	}

}
