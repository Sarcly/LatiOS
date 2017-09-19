package latiOS.config;

import java.io.IOException;
import java.io.InputStream;

import net.dv8tion.jda.core.utils.SimpleLog;

public class ConfigWrite {
	
	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	public static void MakeConfig() throws IOException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream is = cl.getResourceAsStream("latiOS/config/Main Config Template.txt");
		System.out.println((char)is.read());
	}
}
