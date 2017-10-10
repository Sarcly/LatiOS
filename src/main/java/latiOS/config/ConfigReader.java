package latiOS.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigReader {

	private FileInputStream configFile;
	private HashMap<IConfigValue, String> config = new HashMap<IConfigValue, String>();
	
	public ConfigReader(FileInputStream file) {
		configFile = file;
	}
}
