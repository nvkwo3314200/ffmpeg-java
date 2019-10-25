package com.peak.ffmpeg;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * -fromats 显示可用的格式，编解码的，协议的。。。
 *-f fmt 强迫采用格式fmt
 *-i filename 输入文件
 *-y 覆盖输出文件
 *-t duration 设置纪录时间 hh:mm:ss[.xxx]格式的记录时间也支持
 *-ss position 搜索到指定的时间 [-]hh:mm:ss[.xxx]的格式也支持
 *-title string 设置标题
 *-author string 设置作者
 *-copyright string 设置版权
 *-comment string 设置评论 
 * ffmpeg Command
 * @author Pan
 * ffmpeg -i http://youku.cdn2-youku.com/20180424/10793_9dbaf4e1/index.m3u8 -acodec copy -vcodec copy -f mp4 三国机密34.mp4
 */
public class FfmpegCommand {
	private List<String> command = new ArrayList<String>();
	
	public FfmpegCommand(String softPath) {
		command.add(softPath + "//ffmpeg");
	}
	
	public void addInputFile(String filePath) {
		command.add("-i");
		command.add(filePath);
	}
	
	public void overrideFile() {
		command.add("-y");
	}
	
	public void fmt(String fmt) {
		command.add("-f");
		command.add(fmt);
	}
	
	public void add(String cmd) {
		command.add(cmd);
	}
	
	/**
	 * * 视频解码
	 * -vcodec codec 强制使用codec编解码方式。如果用copy表示原始编解码数据必须被拷贝。
	 * @param copy
	 */
	public void vcodecEncode(boolean copy) {
		command.add("-vcodec");
		if(copy)
			command.add("copy");
	}
	
	
	
	/**
	 *  音频解码
	 * -acodec codec 使用codec编解码。如果用copy表示原始编解码数据必须被拷贝。
	 * @param copy
	 */
	public void acodecEncode(boolean copy) {
		command.add("-acodec");
		if(copy)
			command.add("copy");
	}
	
	public List<String> getConvertMp4Cmd(String sourceFile, String targetFile){
		addInputFile(sourceFile);
		acodecEncode(true);
		vcodecEncode(true);
		fmt("mp4");
		add(targetFile);
		return command;
	}
}
