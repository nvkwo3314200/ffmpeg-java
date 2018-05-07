package com.peak.util;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

public class CommandUtils {
	private static Logger logger = Logger.getLogger(CommandUtils.class); 
	
	public static Process exec(final List<String> command) {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.command(command);
		try {
			Process process = builder.start();
			return process;
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
}
