package latiOS.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigReader {

	private FileInputStream configFile;
	private HashMap<ConfigValues, Object> config = new HashMap<ConfigValues, Object>();
	
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
				if (m.find()) {
					for (ConfigValues i: ConfigValues.values()) {
						System.out.print(m.group()+"==");
						System.out.println(i.getName());
						if (i.getName().equals(m.group(0))) {
							System.out.println("MY NAME JJJJJEEEEEFFFFFFFFFFFFFFFFFF");
						}
					}
				}
			}
		}
		s.close();
	}
}
