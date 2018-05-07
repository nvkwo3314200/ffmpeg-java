package com.peak.ffmpeg;

import java.util.List;
import java.util.concurrent.Callable;

import com.peak.util.CommandUtils;

public class Ffmpeg implements Runnable, Callable<Boolean>{
	private String softPath;
	private String sourceFile;
	private String targetFile;
	
	public Ffmpeg(String softPath, String sourceFile, String targetFile) {
		super();
		this.softPath = softPath;
		this.sourceFile = sourceFile;
		this.targetFile = targetFile;
	}

	public void run() {
		List<String> command = new FfmpegCommand(softPath).getConvertMp4Cmd(sourceFile, targetFile);
		CommandUtils.exec(command);
	}

	public Boolean call() throws Exception {
		List<String> command = new FfmpegCommand(softPath).getConvertMp4Cmd(sourceFile, targetFile);
		CommandUtils.exec(command);
		return true;
	}	
}
