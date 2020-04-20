package com.peak.util;

import lombok.Data;

@Data
public class ProcessInfo {
    private double totalSecond;
    private long currentSecond = 0;
    private double percent = 0;

    public String parse(String info) {
        if(totalSecond == 0) {
            totalSecond = Double.valueOf(parseDuration(info));
            if(totalSecond == 0) {
                return null;
            } else {
                return "0.00%";
            }
        } else {
            long currentSecondTemp = this.currentSecond;
            this.currentSecond = parseTime(info);
            if(currentSecondTemp == this.currentSecond) {
                return null;
            } else {
                percent = this.currentSecond / this.totalSecond;
                return String.format("%.2f%%", percent*100);
            }
        }
    }

    private long parseDuration(String info) {
        int location = -1;
        if((location = info.indexOf("Duration: ")) > -1) {
            String timeStr = info.substring(location + 10, location+18);
            return convertTimeStr2Second(timeStr);
        }
        return 0;
    }

    private long convertTimeStr2Second(String timeStr) {
        String[] timeArr = timeStr.split(":");
        return Long.valueOf(timeArr[0]) * 3600 + Long.valueOf(timeArr[1]) * 60 + Long.valueOf(timeArr[2]);
    }

    private long parseTime(String info) {
        int location = -1;
        if((location = info.indexOf(" time=")) > -1) {
            String timeStr = info.substring(location + 6, location+14);
            return convertTimeStr2Second(timeStr);
        } else {
            return currentSecond;
        }
    }

    public static void main(String[] args) {
        ProcessInfo info = new ProcessInfo();
        String a = "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-  libavutil      56. 15.100 / 56. 15.100\n" +
                "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-  libavcodec     58. 19.100 / 58. 19.100\n" +
                "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-  libavformat    58. 13.100 / 58. 13.100\n" +
                "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-  libavdevice    58.  4.100 / 58.  4.100\n" +
                "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-  libavfilter     7. 19.100 /  7. 19.100\n" +
                "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-  libswscale      5.  2.100 /  5.  2.100\n" +
                "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-  libswresample   3.  2.100 /  3.  2.100\n" +
                "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-  libpostproc    55.  2.100 / 55.  2.100\n" +
                "   INFO [20/04/2020 10:22:15][com.peak.util.CommandUtils]-[hls,applehttp @ 00000198af08a580] Opening 'https://www.bohogear.com:65/20200415/e2R8n9Iu/1300kb/hls/index.m3u8' for reading\n" +
                "   INFO [20/04/2020 10:22:16][com.peak.util.CommandUtils]-[hls,applehttp @ 00000198af08a580] Opening 'https://www.bohogear.com:65/20200415/e2R8n9Iu/1300kb/hls/4KV09Rmw.ts' for reading\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-[hls,applehttp @ 00000198af08a580] Opening 'https://www.bohogear.com:65/20200415/e2R8n9Iu/1300kb/hls/cLKRpLXM.ts' for reading\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-Input #0, hls,applehttp, from 'https://www.bohogear.com:65/20200415/e2R8n9Iu/index.m3u8':\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-  Duration: 00:31:56.32, start: 1.457000, bitrate: N/A\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-  Program 0 \n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Metadata:\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-      variant_bitrate : 1300000\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Stream #0:0: Video: h264 (High) ([27][0][0][0] / 0x001B), yuv420p, 868x328, 25 fps, 25 tbr, 90k tbn, 50 tbc\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Metadata:\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-      variant_bitrate : 1300000\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Stream #0:1: Audio: aac (LC) ([15][0][0][0] / 0x000F), 44100 Hz, stereo, fltp\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Metadata:\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-      variant_bitrate : 1300000\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-Output #0, mp4, to 'E:/ffmpeg/龙岭迷窟//龙岭迷窟第14集.mp4':\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-  Metadata:\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    encoder         : Lavf58.13.100\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Stream #0:0: Video: h264 (High) (avc1 / 0x31637661), yuv420p, 868x328, q=2-31, 25 fps, 25 tbr, 90k tbn, 90k tbc\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Metadata:\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-      variant_bitrate : 1300000\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Stream #0:1: Audio: aac (LC) (mp4a / 0x6134706D), 44100 Hz, stereo, fltp\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-    Metadata:\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-      variant_bitrate : 1300000\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-Stream mapping:\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-  Stream #0:0 -> #0:0 (copy)\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-  Stream #0:1 -> #0:1 (copy)\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-Press [q] to stop, [?] for help\n" +
                "   INFO [20/04/2020 10:22:18][com.peak.util.CommandUtils]-[https @ 00000198af500f00] Opening 'https://www.bohogear.com:65/20200415/e2R8n9Iu/1300kb/hls/fbGwqhAb.ts' for reading\n" +
                "   INFO [20/04/2020 10:22:19][com.peak.util.CommandUtils]-frame=  108 fps=0.0 q=-1.0 size=     256kB time=00:00:04.22 bitrate= 496.7kbits/s speed=7.53x    \n" +
                "   INFO [20/04/2020 10:22:19][com.peak.util.CommandUtils]-frame=  121 fps=103 q=-1.0 size=     256kB time=00:00:04.74 bitrate= 442.2kbits/s speed=4.05x    \n" +
                "   INFO [20/04/2020 10:22:19][com.peak.util.CommandUtils]-[https @ 00000198af7b4880] Opening 'https://www.bohogear.com:65/20200415/e2R8n9Iu/1300kb/hls/bAcXYHn3.ts' for reading\n" +
                "   INFO [20/04/2020 10:22:20][com.peak.util.CommandUtils]-frame=  199 fps=108 q=-1.0 size=     512kB time=00:00:07.86 bitrate= 533.5kbits/s speed=4.27x    \n" +
                "   INFO [20/04/2020 10:22:20][com.peak.util.CommandUtils]-[https @ 00000198af500f00] Opening 'https://www.bohogear.com:65/20200415/e2R8n9Iu/1300kb/hls/SDErZELP.ts' for reading\n" +
                "   INFO [20/04/2020 10:22:21][com.peak.util.CommandUtils]-frame=  259 fps= 90 q=-1.0 size=     768kB time=00:00:10.26 bitrate= 613.1kbits/s speed=3.56x    \n" +
                "   INFO [20/04/2020 10:22:21][com.peak.util.CommandUtils]-[https @ 00000198af7b4880] Opening 'https://www.bohogear.com:65/20200415/e2R8n9Iu/1300kb/hls/vgj3tQ1I.ts' for reading\n" +
                "   INFO [20/04/2020 10:22:21][com.peak.util.CommandUtils]-[https @ 00000198af500f00] Opening 'https://www.bohogear.com:65/20200415/e2R8n9Iu/1300kb/hls/DvtlVyX6.ts' for reading\n" +
                "   INFO [20/04/2020 10:22:22][com.peak.util.CommandUtils]-frame=  390 fps=106 q=-1.0 size=     768kB time=00:00:15.50 bitrate= 405.8kbits/s speed= 4.2x    \n" +
                "   INFO [20/04/2020 10:22:23][com.peak.util.CommandUtils]-frame=  413 fps= 89 q=-1.0 size=    1024kB time=00:00:16.42 bitrate= 510.8kbits/s speed=3.52x    \n" +
                "   INFO [20/04/2020 10:22:24][com.peak.util.CommandUtils]-frame=  427 fps= 78 q=-1.0 size=    1024kB time=00:00:16.98 bitrate= 494.0kbits/s speed= 3.1x    \n" +
                "   INFO [20/04/2020 10:22:25][com.peak.util.CommandUtils]-frame=  444 fps= 65 q=-1.0 size=    1024kB time=00:00:17.66 bitrate= 474.9kbits/s speed=2.57x    \n" +
                "   INFO [20/04/2020 10:22:26][com.peak.util.CommandUtils]-frame=  448 fps= 56 q=-1.0 size=    1280kB time=00:00:17.82 bitrate= 588.3kbits/s speed=2.24x    \n" +
                "   INFO [20/04/2020 10:22:27][com.peak.util.CommandUtils]-frame=  453 fps= 53 q=-1.0 size=    1280kB time=00:00:18.02 bitrate= 581.8kbits/s speed=2.11x    \n" +
                "   INFO [20/04/2020 10:22:28][com.peak.util.CommandUtils]-frame=  464 fps= 45 q=-1.0 size=    1280kB time=00:00:18.46 bitrate= 568.0kbits/s speed=1.79x    \n" +
                "   INFO [20/04/2020 10:22:29][com.peak.util.CommandUtils]-frame=  470 fps= 41 q=-1.0 size=    1280kB time=00:00:18.70 bitrate= 560.7kbits/s speed=1.63x    ";
        for(String info1 : a.split("\n")) {
            System.out.println(info.parse(info1));
        }
    }
}
