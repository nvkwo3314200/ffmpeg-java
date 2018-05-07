package com.peak.ffmpeg;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * -fromats ��ʾ���õĸ�ʽ�������ģ�Э��ġ�����
 *-f fmt ǿ�Ȳ��ø�ʽfmt
 *-i filename �����ļ�
 *-y ��������ļ�
 *-t duration ���ü�¼ʱ�� hh:mm:ss[.xxx]��ʽ�ļ�¼ʱ��Ҳ֧��
 *-ss position ������ָ����ʱ�� [-]hh:mm:ss[.xxx]�ĸ�ʽҲ֧��
 *-title string ���ñ���
 *-author string ��������
 *-copyright string ���ð�Ȩ
 *-comment string �������� 
 * ffmpeg Command
 * @author Pan
 * ffmpeg -i http://youku.cdn2-youku.com/20180424/10793_9dbaf4e1/index.m3u8 -acodec copy -vcodec copy -f mp4 ��������34.mp4
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
	 * * ��Ƶ����
	 * -vcodec codec ǿ��ʹ��codec����뷽ʽ�������copy��ʾԭʼ��������ݱ��뱻������
	 * @param copy
	 */
	public void vcodecEncode(boolean copy) {
		command.add("-vcodec");
		if(copy)
			command.add("copy");
	}
	
	
	
	/**
	 *  ��Ƶ����
	 * -acodec codec ʹ��codec����롣�����copy��ʾԭʼ��������ݱ��뱻������
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
