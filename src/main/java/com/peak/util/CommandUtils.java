package com.peak.util;

import java.io.*;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandUtils {
	public static int exec(final List<String> command) {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.command(command);
		try {
			builder.redirectErrorStream(true);
			Process process = builder.start();
			//process.getOutputStream().close(); // 关掉输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			try {
				String readLine = br.readLine();
				while (readLine != null) {
					log.info(readLine); //既有正常输出和error message。
					readLine = br.readLine();
				}
			} catch (IOException e) {
				log.error("{}", e);
			}
			return process.waitFor();
		} catch (IOException | InterruptedException e) {
			log.error("{}", e);
		}
		return -1;
	}
}
