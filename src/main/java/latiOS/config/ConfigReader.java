package latiOS.config;

import java.io.FileInputStream;
import java.util.Scanner;

import latiOS.exceptions.ConfigFormatException;
import net.dv8tion.jda.core.utils.SimpleLog;

public class ConfigReader {

	private FileInputStream configFile;
	
	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	public ConfigReader(FileInputStream file) {
		configFile = file;
	}
	
	public void readAll() {
		Scanner s = new Scanner(configFile);
		int cur=0;
		String temp="\n";
		while (s.hasNextLine()) {
			cur++;
			String line=s.nextLine();
			if (!isValid(line)) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				System.exit(3);
			}
			temp+=line+"\n";
		}
		log.info(temp);
	}
	
	private boolean isValid(String line) { 
		if (!(line.isEmpty()||line.startsWith("#")||line.startsWith(ConfigDataTypes.BOOLEAN.getPrefix())||line.startsWith(ConfigDataTypes.BOOLEAN_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.DOUBLE.getPrefix())||line.startsWith(ConfigDataTypes.DOUBLE_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.INT.getPrefix())||line.startsWith(ConfigDataTypes.INT_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.STRING.getPrefix())||line.startsWith(ConfigDataTypes.STRING_ARRAY.getPrefix()))) {
			return false;
		}
		return true;
	}
}
