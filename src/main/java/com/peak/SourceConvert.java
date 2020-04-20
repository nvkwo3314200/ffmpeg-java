package com.peak;

import java.io.File;
import java.util.Map;
import java.util.concurrent.*;

import com.peak.ffmpeg.Ffmpeg;
import com.peak.reader.PropertyReader;
import com.peak.reader.SourceReader;
import com.peak.util.Constant;
import com.peak.util.FileUtils;
import com.peak.util.MyExecutorService;
import com.peak.util.ThreadUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SourceConvert {
	private String softPath;
	private String targetFilePath;
	private String name;
	private String fmt;
	private Integer maxThread;
	private Integer count = 1;
	private Integer coreThread;
	private Long waitTime;

	public SourceConvert(String fmt) {
		softPath = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_SOFT_PATH);
		name = SourceReader.getNAME();
		targetFilePath = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_TARGET_FILE_PATH);
		targetFilePath = String.format(targetFilePath, name);
		waitTime = Long.valueOf(PropertyReader.GetValueByKey(null, Constant.WAIT_TIME));
		this.fmt = fmt;
	}

	public void convert() {
		Map<String, String> sourceMap = SourceReader.getSourceMap();
		for(String key : sourceMap.keySet()) {
			if(!checkKeyExist(key)) {
				String targetFile = createTargetFile(key);
				FileUtils.createParent(targetFile);
			}
		}
	}

	public boolean convertCall() throws InterruptedException, ExecutionException {
		Map<String, String> sourceMap = SourceReader.getSourceMap();
		StringBuffer sb = new StringBuffer();
		for(String key : SourceReader.getSourceKeyList()) {
			if(!checkKeyExist(key)) {
				String targetFile = createTargetFile(key);
				FileUtils.createParent(targetFile);
				MyExecutorService.getFfmpegService().execute(new Ffmpeg(softPath, sourceMap.get(key), targetFile, key));
			}
		}
		for(;;) {
			if(MyExecutorService.getFfmpegService().getQueue().size() == 0) {
				MyExecutorService.getFfmpegService().shutdown();
				break;
			} else {
				ThreadUtil.sleepSecond(60);
			}
		}
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
