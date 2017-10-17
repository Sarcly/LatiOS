package latiOS.config;

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

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
			if (!isValid(line,cur)) {
				System.exit(3);
			}
			temp+=line+"\n";
		}
		log.info(temp);
		s.close();
	}
	
	private boolean isValid(String line, int cur) {
		if (!(line.isEmpty()||line.startsWith("#")||line.startsWith(ConfigDataTypes.BOOLEAN.getPrefix())||line.startsWith(ConfigDataTypes.BOOLEAN_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.DOUBLE.getPrefix())||line.startsWith(ConfigDataTypes.DOUBLE_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.INT.getPrefix())||line.startsWith(ConfigDataTypes.INT_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.STRING.getPrefix())||line.startsWith(ConfigDataTypes.STRING_ARRAY.getPrefix()))) {
			log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
			return false;
		}
		if (line.startsWith(ConfigDataTypes.STRING.getPrefix())) {
			if (!Pattern.compile("^S{1}:{1}[a-zA-Z]+={1}[a-zA-Z0-9]+;{1}$").matcher(line).matches()){
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.INT.getPrefix())) {
			if (!Pattern.compile("^I{1}:{1}[a-zA-Z]+={1}-{0,1}[0-9]+;{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				return false;
			}else if (Long.parseLong(line.replaceAll("^I{1}:{1}[A-Za-z]+={1}", "").trim().replaceAll(";$", ""))>Integer.MAX_VALUE) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Integer on line "+cur+" is too large"));
				return false;
			}else if (Long.parseLong(line.replaceAll("^I{1}:{1}[A-Za-z]+={1}", "").trim().replaceAll(";$", ""))<Integer.MIN_VALUE) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Integer on line "+cur+" is too small"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.BOOLEAN.getPrefix())) {
			if (!Pattern.compile("^B{1}:{1}[a-zA-Z]+={1}(true|false);{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.DOUBLE.getPrefix())) {
			if (!Pattern.compile("^D{1}:{1}[a-zA-Z]+={1}-{0,1}[0-9]+\\.{1}[0-9]+;{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				return false;
			}else if (Double.parseDouble(line.replaceAll("^D{1}:{1}[a-zA-Z]+={1}", "").trim().replaceAll(";$", ""))>Double.MAX_VALUE) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Double on line "+cur+" is too large"));
				return false;
			}else if (Double.parseDouble(line.replaceAll("^D{1}:{1}[a-zA-Z]+={1}", "").trim().replaceAll(";$", ""))<-Double.MAX_VALUE) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Double on line "+cur+" is too small"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.STRING_ARRAY.getPrefix())) {
			if (!Pattern.compile("^A{1}\\[{1}S{1}\\]{1}:{1}[A-Za-z]+={1}<{1}([a-zA-Z]+,?)+>{1};{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.INT_ARRAY.getPrefix())) {
			if (!Pattern.compile("^A{1}\\[{1}I{1}\\]{1}:{1}[A-Za-z]+={1}<{1}(-{0,1}[0-9]+,?)+>{1};{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				return false;
			} else {  
				String[] i = line.replaceAll("^A{1}\\[{1}I{1}\\]{1}:{1}[A-Za-z]+={1}<{1}", "").trim().replaceAll(">{1};{1}$", "").split(",");
				for (int k=0;k<i.length;k++) {
					if (Long.parseLong(i[k])>Integer.MAX_VALUE||Long.parseLong(i[k])<Integer.MIN_VALUE) {
						log.fatal(new ConfigFormatException("Config File is formated incorrectly: Double in array on line "+cur+" is too large or small"));
						return false;
					}
				}
			}
		}else if (line.startsWith(ConfigDataTypes.BOOLEAN_ARRAY.getPrefix())) {
			if (!Pattern.compile("^A{1}\\[{1}B{1}\\]{1}:{1}[A-Za-z]+={1}<{1}((true|false),?)+>{1};{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.DOUBLE_ARRAY.getPrefix())) {
			if (!Pattern.compile("^A{1}\\[{1}D{1}\\]{1}:{1}[A-Za-z]+={1}<{1}(-{0,1}[0-9]+\\.{1}[0-9]+,?)+>{1};{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
				return false;
			} else {
				String[] i = line.replaceAll("^A{1}\\[{1}D{1}\\]{1}:{1}[A-Za-z]+={1}<{1}", "").trim().replaceAll(">{1};{1}$", "").split(",");
				for (int k=0;k<i.length;k++) {
					if (Double.parseDouble(i[k])>Double.MAX_VALUE||Double.parseDouble(i[k])<-Double.MAX_VALUE) {
						log.fatal(new ConfigFormatException("Config File is formated incorrectly: Double in array on line "+cur+" is too large or small"));
						return false;
					}
				}
			}
		}
		return true;
	}
}
