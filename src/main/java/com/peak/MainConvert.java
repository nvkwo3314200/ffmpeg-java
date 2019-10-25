package com.peak;

import java.util.concurrent.ExecutionException;

public class MainConvert {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SourceConvert convert = new SourceConvert("mp4");
        convert.convertCall();
    }


}
