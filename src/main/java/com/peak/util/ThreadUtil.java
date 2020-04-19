package com.peak.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadUtil {

    public static void sleepSecond(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            log.error("{}", e);
        }
    }
}
