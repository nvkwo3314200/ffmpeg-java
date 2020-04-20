package com.peak;

import com.peak.reader.SourceReader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.concurrent.ExecutionException;

@Slf4j
public class MainConvert {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String sourceFile = null;
        if(args != null && args.length > 0) {
            sourceFile = args[0];
            File file = new File(sourceFile);
            if(!file.exists() || file.isDirectory()) {
                log.error("{}, 不存在", sourceFile);
                return;
            }
        }
        SourceReader.locationSource(sourceFile);
        SourceConvert convert = new SourceConvert("mp4");
        convert.convertCall();
    }


}
