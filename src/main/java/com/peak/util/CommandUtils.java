package com.peak.util;

import java.io.*;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandUtils {
	public static Process exec(final List<String> command) {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.command(command);
		try {
			builder.redirectErrorStream(true);
			Process process = builder.start();
			//process.getOutputStream().close(); // 关掉输入流
			return process;
		} catch (IOException e) {
			log.error("{}", e);
		}
		return null;
	}
}
