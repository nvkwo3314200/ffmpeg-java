package com.peak;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import com.peak.ffmpeg.Ffmpeg;
import com.peak.reader.PropertyReader;
import com.peak.reader.SourceReader;
import com.peak.util.Constant;
import com.peak.util.FileUtils;

public class SourceConvert {
	private Executor exec;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private String softPath;
	private String sourceFile;
	private String targetFilePath;
	private String name;
	private String fmt;
	private Integer maxThread;
	private Integer count = 1;
	private Integer localMaxThread;
	private Long waitTime;
	
	public SourceConvert(String fmt) {
		String maxThreadStr = PropertyReader.GetValueByKey(null, Constant.MAX_THREAD);
		maxThread = (maxThreadStr == null || "".equals(maxThreadStr))? 100 : Integer.valueOf(maxThreadStr);
		exec = Executors.newFixedThreadPool(maxThread);
		softPath = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_SOFT_PATH);
		sourceFile = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_SOURCE_FILE);
		targetFilePath = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_TARGET_FILE_PATH);
		name = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_NAME);
		localMaxThread = Integer.valueOf(PropertyReader.GetValueByKey(null, Constant.LOCAL_MAX_THREAD));
		waitTime = Long.valueOf(PropertyReader.GetValueByKey(null, Constant.WAIT_TIME));
		this.fmt = fmt;
	}
	
	public void convert() {
		Map<String, String> sourceMap = SourceReader.getSourceByFile(sourceFile);
		for(String key : sourceMap.keySet()) {
			if(!checkKeyExist(key)) {
				String targetFile = createTargetFile(key);
				FileUtils.createParent(targetFile);
//				exec.execute(new Ffmpeg(softPath, sourceMap.get(key), targetFile));
			}
		}
	}
	
	public boolean convertCall() throws InterruptedException, ExecutionException {
		Map<String, String> sourceMap = SourceReader.getSourceByFile(sourceFile);
		StringBuffer sb = new StringBuffer();
		for(String key : sourceMap.keySet()) {
			if(!checkKeyExist(key)) {
				if(count % localMaxThread == 0) {
					if(executorService.awaitTermination(10, TimeUnit.SECONDS)) {
						executorService.shutdownNow();
					}
					Thread.sleep(waitTime * 1000);
				}
				count ++;
				if(executorService.isShutdown()) {
					executorService = Executors.newCachedThreadPool();
				}
				String targetFile = createTargetFile(key);
				FileUtils.createParent(targetFile);
				executorService.submit(new Ffmpeg(softPath, sourceMap.get(key), targetFile));
				FutureTask<Boolean> ft = (FutureTask<Boolean>) executorService.submit(new Ffmpeg(softPath, sourceMap.get(key), targetFile));
				if(!ft.get()) {
					sb.append(key).append("\n");
				}else {
					System.out.println(String.format("%s 正在下载", key));
				}
			}
		}
		System.out.println(sb);
		return sb.length() == 0? true : false;
	}

	private String createTargetFile(String key) {
		StringBuffer sb = new StringBuffer(targetFilePath)
				.append("/")
				.append(getFileName(key));
		return sb.toString();
	}
	
	private String getFileName(String key) {
		StringBuffer sb = new StringBuffer(name).append(key).append(".").append(fmt);
		return sb.toString();
	}

	private boolean checkKeyExist(String key) {
		File file = new File(targetFilePath);
		if(file.isDirectory()) {
			for(String item : file.list()) {
				if(new File(item).getName().equals(getFileName(key))) {
					return true;
				}
			}
		}
		return false;
	}
	
}
