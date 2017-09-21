package latiOS.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			if (line.startsWith("B:")||line.startsWith("I:")||line.startsWith("D:")||line.startsWith("S:")||line.startsWith("A[S]:")||line.startsWith("A[I]:")||line.startsWith("A[B]:")||line.startsWith("A[D}:")) {
				Pattern p = Pattern.compile("(?<=:).+?(?==)");
				Matcher m = p.matcher(line);
				for (ConfigValues i: ConfigValues.values()) {
					System.out.println(m.group(1));
					if (i.getName().equals(m.group(1))) {
						//TODO
					}
				}
			}
		}
		s.close();
	}
}
