package ffmpeg;

import java.util.concurrent.ExecutionException;

import com.peak.SourceConvert;

public class MainTest {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		new SourceConvert("mp4").convertCall();
		System.out.println("=============END============");
	}
}