package com.peak;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.peak.ffmpeg.Ffmpeg;
import com.peak.reader.PropertyReader;
import com.peak.reader.SourceReader;
import com.peak.util.Constant;
import com.peak.util.FileUtils;

public class SourceConvert {
	private Executor exec;
	private String softPath;
	private String sourceFile;
	private String targetFilePath;
	private String name;
	private String fmt;
	private Integer maxThread;
	
	public SourceConvert(String fmt) {
		String maxThreadStr = PropertyReader.GetValueByKey(null, Constant.MAX_THREAD);
		maxThread = (maxThreadStr == null || "".equals(maxThreadStr))? 100 : Integer.valueOf(maxThreadStr);
		exec = Executors.newFixedThreadPool(maxThread);
		softPath = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_SOFT_PATH);
		sourceFile = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_SOURCE_FILE);
		targetFilePath = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_TARGET_FILE_PATH);
		name = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_NAME);
		this.fmt = fmt;
	}
	
	public void convert() {
		Map<String, String> sourceMap = SourceReader.getSourceByFile(sourceFile);
		for(String key : sourceMap.keySet()) {
			if(!checkKeyExist(key)) {
				String targetFile = createTargetFile(key);
				FileUtils.createParent(targetFile);
				exec.execute(new Ffmpeg(softPath, sourceMap.get(key), targetFile));
			}
		}
	}
	
	public boolean convertCall() throws InterruptedException, ExecutionException {
		Map<String, String> sourceMap = SourceReader.getSourceByFile(sourceFile);
		StringBuffer sb = new StringBuffer();
		for(String key : sourceMap.keySet()) {
			if(!checkKeyExist(key)) {
				String targetFile = createTargetFile(key);
				FileUtils.createParent(targetFile);
				FutureTask<Boolean> ft = new FutureTask<Boolean>(new Ffmpeg(softPath, sourceMap.get(key), targetFile));
				ft.run();
				if(!ft.get()) {
					sb.append(key).append("\n");
				}else {
					System.out.println(String.format("%s ÕýÔÚÏÂÔØ", key));
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
