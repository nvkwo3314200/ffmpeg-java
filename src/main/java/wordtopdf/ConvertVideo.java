package wordtopdf;

import java.io.File;  
import java.util.ArrayList;  
import java.util.Calendar;  
import java.util.List;  
  

public class ConvertVideo {
     private final static String PATH = "����Ҫת�����Ƶ·����";  
      
        public static void main(String[] args) {  
            if (!checkfile(PATH)) {   //�ж�·���ǲ���һ���ļ�
                System.out.println(PATH + " is not file");  
                return;  
            }  
            if (process()) {        //ִ��ת������
                System.out.println("ok");  
            }  
        }  
      
        private static boolean checkfile(String path) {  
            File file = new File(path);  
            if (!file.isFile()) {  
                return false;  
            }  
            return true;  
        }  
        
        private static boolean process() { 
            // �ж���Ƶ������
            int type = checkContentType();  
            boolean status = false;  
            //�����ffmpeg����ת��������ֱ��ת�룬��������mencoderת���AVI
            if (type == 0) {  
                System.out.println("ֱ�ӽ��ļ�תΪflv�ļ�");  
                status = processFLV(PATH);// ֱ�ӽ��ļ�תΪflv�ļ�  
            } else if (type == 1) {  
                String avifilepath = processAVI(type);  
                if (avifilepath == null)  
                    return false;// avi�ļ�û�еõ�  
                status = processFLV(avifilepath);// ��aviתΪflv  
            }  
            return status;  
        }  
      
        private static int checkContentType() {  
            String type = PATH.substring(PATH.lastIndexOf(".") + 1, PATH.length())  
                    .toLowerCase();  
            // ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�  
            if (type.equals("avi")) {  
                return 0;  
            } else if (type.equals("mpg")) {  
                return 0;  
            } else if (type.equals("wmv")) {  
                return 0;  
            } else if (type.equals("3gp")) {  
                return 0;  
            } else if (type.equals("mov")) {  
                return 0;  
            } else if (type.equals("mp4")) {  
                return 0;  
            } else if (type.equals("asf")) {  
                return 0;  
            } else if (type.equals("asx")) {  
                return 0;  
            } else if (type.equals("flv")) {  
                return 0;  
            }  
            // ��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��),  
            // �������ñ�Ĺ��ߣ�mencoder��ת��Ϊavi(ffmpeg�ܽ�����)��ʽ.  
            else if (type.equals("wmv9")) {  
                return 1;  
            } else if (type.equals("rm")) {  
                return 1;  
            } else if (type.equals("rmvb")) {  
                return 1;  
            }  
            return 9;  
        }  
           
      
        // ��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��), �������ñ�Ĺ��ߣ�mencoder��ת��Ϊavi(ffmpeg�ܽ�����)��ʽ.  
        private static String processAVI(int type) {  
            List<String> commend = new ArrayList<String>();  
            commend.add("D:\\ffmpeg\\mencoder");  
            commend.add(PATH);  
            commend.add("-oac");  
            commend.add("lavc");  
            commend.add("-lavcopts");  
            commend.add("acodec=mp3:abitrate=64");  
            commend.add("-ovc");  
            commend.add("xvid");  
            commend.add("-xvidencopts");  
            commend.add("bitrate=600");  
            commend.add("-of");  
            commend.add("avi");  
            commend.add("-o");  
            commend.add("�����ת�����Ƶ��·������סһ����.avi��׺���ļ�����");  
            try {  
                //�����߳���������ת��
                ProcessBuilder builder = new ProcessBuilder();  
                builder.command(commend);  
                builder.start();  
                return "�����ת�����Ƶ��·������סһ����.avi��׺���ļ�����";  
            } catch (Exception e) {  
                e.printStackTrace();  
                return null;  
            }  
        }  
      
        // ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�  
        private static boolean processFLV(String oldfilepath) {  
      
            if (!checkfile(PATH)) {  
                System.out.println(oldfilepath + " is not file");  
                return false;  
            }  
              
            // �ļ�����  
            Calendar c = Calendar.getInstance();  
            String savename = String.valueOf(c.getTimeInMillis())+ Math.round(Math.random() * 100000);  
            List<String> commend = new ArrayList<String>();  
            commend.add("D:\\ffmpeg\\ffmpeg");  
            commend.add("-i");  
            commend.add(oldfilepath);  
            commend.add("-ab");  
            commend.add("56");  
            commend.add("-ar");  
            commend.add("22050");  
            commend.add("-qscale");  
            commend.add("8");  
            commend.add("-r");  
            commend.add("15");  
            commend.add("-s");  
            commend.add("600x500");  
            commend.add("�����ת�����Ƶ��·������סһ����.flv��׺���ļ�����");  
      
            try {  
                Runtime runtime = Runtime.getRuntime();  
                Process proce = null; 
                //��Ƶ��ͼ�������ͼ��  8�Ǵ����8���ʱ���ͼ
                String cmd = "";  
                String cut = "     c:\\ffmpeg\\ffmpeg.exe   -i   "  
                        + oldfilepath  
                        + "   -y   -f   image2   -ss   8   -t   0.001   -s   600x500   c:\\ffmpeg\\output\\"  
                        + "a.jpg";  
                String cutCmd = cmd + cut;  
                proce = runtime.exec(cutCmd);  
                //�����߳��������ת��
                ProcessBuilder builder = new ProcessBuilder(commend);                 
                 builder.command(commend);  
                builder.start();  
      
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
                return false;  
            }  
        }  
}