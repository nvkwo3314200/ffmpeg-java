package com.peak.ffmpeg;

import java.util.List;
import java.util.concurrent.Callable;

import com.peak.util.CommandUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Ffmpeg implements  Runnable{
	private String softPath;
	private String sourceFile;
	private String targetFile;
	private String key;

	public Ffmpeg(String softPath, String sourceFile, String targetFile, String key) {
		super();
		this.softPath = softPath;
		this.sourceFile = sourceFile;
		this.targetFile = targetFile;
		this.key = key;
	}

	public void run() {
		log.info("{} 正在下载", key);
		List<String> command = new FfmpegCommand(softPath).getConvertMp4Cmd(sourceFile, targetFile);
		CommandUtils.exec(command);
	}

	public Boolean call() throws Exception {
		log.info("{} 正在下载", key);
		List<String> command = new FfmpegCommand(softPath).getConvertMp4Cmd(sourceFile, targetFile);
		CommandUtils.exec(command);
		return true;
	}
}
