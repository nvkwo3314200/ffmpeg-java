package com.peak.reader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class SourceReader {
	private static final Logger logger = Logger.getLogger(SourceReader.class);
	
	public static final String DEFAULT_SEPARATOR = "$";
	public static final String COMMEND_SEPARATOR = "#";
	
	public static Map<String, String> getSourceByFile(String filePath, String separator) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			List<String> list = FileUtils.readLines(new File(filePath), "UTF-8");
			for(String item : list) {
				if(item.trim().startsWith(COMMEND_SEPARATOR)) continue ;
				if(item == null || "".equals(item.trim())) continue;
				String sep = String.format("\\%s", separator);
				String[] spits = item.split(sep);
				map.put(spits[0], spits[1]);
			}
		} catch (IOException e) {
			logger.error(e);
		}
		return map;
	}
	
	public static Map<String, String> getSourceByFile(String filePath) {
		return getSourceByFile(filePath, DEFAULT_SEPARATOR);
	}
	
	
}
