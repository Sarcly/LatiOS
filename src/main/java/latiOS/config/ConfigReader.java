package latiOS.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigReader {

	private FileInputStream configFile;
	private HashMap<ConfigValues, Object> config = new HashMap<>();
	
	public ConfigReader(FileInputStream file) {
		configFile = file;
	}
	
	public void read() {
		Scanner s = new Scanner(configFile);
		String line;
		while (s.hasNextLine()) {
			line = s.nextLine();
			if (line.startsWith("B:")) {
				
			}else if (line.startsWith("I:")) {
				
			}else if (line.startsWith("D:")) {
				
			}else if (line.startsWith("S:")) {
				
			}else if (line.startsWith("A[B]:")) {
				
			}else if (line.startsWith("A[I]:")) {
				
			}else if (line.startsWith("A[D]:")) {
				
			}else if (line.startsWith("A[S]:")) {
				
			}
		}
		s.close();
	}
}
