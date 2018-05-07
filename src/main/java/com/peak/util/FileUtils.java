package com.peak.util;

import java.io.File;

public class FileUtils {
	
	public static boolean checkFile(String path) {  
        File file = new File(path);  
        if (!file.isFile()) {  
            return false;  
        }  
        return true;  
    }  
	
	public static void createParent(String path) {
		File file = new File(path);
		File parent = file.getParentFile();
		if(!parent.exists()) {
			parent.mkdirs();
		}
	}
}
