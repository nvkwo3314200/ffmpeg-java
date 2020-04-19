package com.peak.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.peak.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

@Slf4j
public class SourceReader {
	public static final String DEFAULT_SEPARATOR = "$";
	public static final String COMMEND_SEPARATOR = "#";
	private static List<String> list;

	static {
		String filePath = PropertyReader.GetValueByKey(null, Constant.PRO_KEY_SOURCE_FILE);
		try {
			list = FileUtils.readLines(new File(filePath), "UTF-8");
		} catch (IOException e) {
			log.error("读取资源文件失败， {}",e);
		}
	}

	public static Map<String, String> getSourceMap(String separator) {
		Map<String, String> map = new HashMap<String, String>();
		for(String item : list) {
			if(item.trim().startsWith(COMMEND_SEPARATOR)) continue ;
			if(item == null || "".equals(item.trim())) continue;
			String sep = String.format("\\%s", separator);
			String[] spits = item.split(sep);
			map.put(spits[0], spits[1]);
		}
		return map;
	}

	public static List<String> getSourceKeyList(String separator) {
		List<String> keyList = new ArrayList<>();
		for(String item : list) {
			if(item.trim().startsWith(COMMEND_SEPARATOR)) continue ;
			if(item == null || "".equals(item.trim())) continue;
			String sep = String.format("\\%s", separator);
			String[] spits = item.split(sep);
			keyList.add(spits[0]);
		}
		return keyList;
	}

	public static List<String> getSourceKeyList() {
		return getSourceKeyList(DEFAULT_SEPARATOR);
	}

	public static Map<String, String> getSourceMap() {
		return getSourceMap(DEFAULT_SEPARATOR);
	}


}
