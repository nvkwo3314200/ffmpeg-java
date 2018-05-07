package ffmpeg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.peak.util.CommandUtils;

public class ProcessTest {
	public static void main(String[] args) throws IOException {
		List<String> commands = getTestCommand1();
		Process pcs = CommandUtils.exec(commands);
		for(String str : IOUtils.readLines(pcs.getInputStream(), "GBK")) {
			System.out.println(str);
		}
	}
	
	private static List<String> getTestCommand1() {
		List<String> commands = new ArrayList<String>();
		commands.add("ipconfig");
		commands.add("/all");
		return commands;
	}
}
