package com.peak.util;

import com.peak.reader.PropertyReader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyExecutorService {
    private static ThreadPoolExecutor ffmpegService;
    private static ThreadPoolExecutor bufferService;
    static {
        String maxThreadStr = PropertyReader.GetValueByKey(null, Constant.MAX_THREAD);
        int coreThread = Integer.valueOf(PropertyReader.GetValueByKey(null, Constant.CORE_THREAD));
        int maxThread = (maxThreadStr == null || "".equals(maxThreadStr))? 100 : Integer.valueOf(maxThreadStr);
        ffmpegService = new ThreadPoolExecutor(coreThread, maxThread, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1024));
        bufferService = new ThreadPoolExecutor(coreThread, maxThread, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1024));

    }

    public static ThreadPoolExecutor getFfmpegService() {
        return ffmpegService;
    }

    public static ThreadPoolExecutor getBufferService() {
        return bufferService;
    }

}
