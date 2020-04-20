package com.peak.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;

import com.peak.reader.SourceReader;
import com.peak.util.CommandUtils;
import com.peak.util.ProcessInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Ffmpeg implements  Runnable{
	private String softPath;
	private String sourceFile;
	private String targetFile;
	private String key;
	private String name = SourceReader.getNAME();

	public Ffmpeg(String softPath, String sourceFile, String targetFile, String key) {
		super();
		this.softPath = softPath;
		this.sourceFile = sourceFile;
		this.targetFile = targetFile;
		this.key = key;
	}

	public void run() {
		log.info("{} {} 正在下载", name, key);
		List<String> command = new FfmpegCommand(softPath).getConvertMp4Cmd(sourceFile, targetFile);
		Process process = CommandUtils.exec(command);
		ProcessInfo info = new ProcessInfo();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		try {
			String readLine = br.readLine();
			while (readLine != null) {
				String proc = info.parse(readLine);
				if(proc != null) {
					log.info("{} {} 下载进度 {} ", name, key, proc); //既有正常输出和error message。
				}
				readLine = br.readLine();
			}
		} catch (IOException e) {
			log.error("{}", e);
		}
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			log.error("{}", e);
		}
	}

	public Boolean call() throws Exception {
		log.info("{} 正在下载", key);
		List<String> command = new FfmpegCommand(softPath).getConvertMp4Cmd(sourceFile, targetFile);
		CommandUtils.exec(command);
		return true;
	}
}
