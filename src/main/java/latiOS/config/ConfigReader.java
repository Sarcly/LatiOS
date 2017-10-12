package latiOS.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigReader {

	private FileInputStream configFile;
	
	public ConfigReader(FileInputStream file) {
		configFile = file;
	}
	
	protected void readAll() {
		Scanner s = new Scanner(configFile);
	}
}
