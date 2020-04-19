package com.peak.reader;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.util.Properties;


@Slf4j
public class PropertyReader {
	private static String DEFAULT_SOURCE;

	static {
		DEFAULT_SOURCE = System.getProperty("user.dir") + "/" + "system.properties";
	}
	public static String GetValueByKey(String filePath, String key) {
		String source = filePath;
		if(source == null || "".equals(source)) source = DEFAULT_SOURCE;
        Properties pps = new Properties();
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(source), "UTF-8")){
            pps.load(isr);
            String value = pps.getProperty(key);
            return value;
        }catch (IOException e) {
        	log.error("{}", e);
        }
        return null;
    }
}
