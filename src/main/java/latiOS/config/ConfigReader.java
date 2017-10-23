package latiOS.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

import latiOS.exceptions.ConfigFormatException;
import latiOS.exceptions.ErrorCodes;
import net.dv8tion.jda.core.utils.SimpleLog;

public class ConfigReader {

	private FileInputStream configFile;
	
	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	protected ConfigReader(FileInputStream file) {
		configFile = file;
	}
	
	protected void readAll() throws IOException {
		Scanner s = new Scanner(configFile);
		int cur=0;
		String line="";
		while (s.hasNextLine()) {
			cur++;
			String lastLine=line;
			line=s.nextLine();
			if (!isValid(line,cur)) {
				System.exit(ErrorCodes.CONFIG_ERROR.getValue());
			}
			if (!(line.startsWith("#")||line.isEmpty())) {
				addToConfig(line,lastLine);
			}
		}
		s.close();
	}
	
	private boolean isValid(String line, int cur) {
		if (!(line.isEmpty()||line.startsWith("#")||line.startsWith(ConfigDataTypes.BOOLEAN.getPrefix())||line.startsWith(ConfigDataTypes.BOOLEAN_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.DOUBLE.getPrefix())||line.startsWith(ConfigDataTypes.DOUBLE_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.INT.getPrefix())||line.startsWith(ConfigDataTypes.INT_ARRAY.getPrefix())||line.startsWith(ConfigDataTypes.STRING.getPrefix())||line.startsWith(ConfigDataTypes.STRING_ARRAY.getPrefix()))) {
			log.fatal(new ConfigFormatException("Config File is formated incorrectly: Line "+cur+" is not valid"));
			return false;
		}
		if (line.startsWith(ConfigDataTypes.STRING.getPrefix())) {
			if (!Pattern.compile("^S{1}:{1}[a-zA-Z]+={1}.+?;{1}$").matcher(line).matches()){
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: String on line "+cur+" is not valid"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.INT.getPrefix())) {
			if (!Pattern.compile("^I{1}:{1}[a-zA-Z]+={1}-{0,1}[0-9]+;{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Integer on line "+cur+" is not valid"));
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
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Boolean on line "+cur+" is not valid"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.DOUBLE.getPrefix())) {
			if (!Pattern.compile("^D{1}:{1}[a-zA-Z]+={1}-{0,1}[0-9]+\\.{1}[0-9]+;{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Double on line "+cur+" is not valid"));
				return false;
			}else if (Double.parseDouble(line.replaceAll("^D{1}:{1}[a-zA-Z]+={1}", "").trim().replaceAll(";$", ""))>Double.MAX_VALUE) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Double on line "+cur+" is too large"));
				return false;
			}else if (Double.parseDouble(line.replaceAll("^D{1}:{1}[a-zA-Z]+={1}", "").trim().replaceAll(";$", ""))<-Double.MAX_VALUE) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Double on line "+cur+" is too small"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.STRING_ARRAY.getPrefix())) {
			if (!Pattern.compile("^A{1}\\[{1}S{1}\\]{1}:{1}[A-Za-z]+={1}<{1}(.+,?)+>{1};{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: String Array on line "+cur+" is not valid"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.INT_ARRAY.getPrefix())) {
			if (!Pattern.compile("^A{1}\\[{1}I{1}\\]{1}:{1}[A-Za-z]+={1}<{1}(-{0,1}[0-9]+,?)+>{1};{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Integer array on line "+cur+" is not valid"));
				return false;
			} else {  
				String[] i = line.replaceAll("^A{1}\\[{1}I{1}\\]{1}:{1}[A-Za-z]+={1}<{1}", "").trim().replaceAll(">{1};{1}$", "").split(",");
				for (int k=0;k<i.length;k++) {
					if (Long.parseLong(i[k])>Integer.MAX_VALUE||Long.parseLong(i[k])<Integer.MIN_VALUE) {
						log.fatal(new ConfigFormatException("Config File is formated incorrectly: Integer in array on line "+cur+" is too large or small"));
						return false;
					}
				}
			}
		}else if (line.startsWith(ConfigDataTypes.BOOLEAN_ARRAY.getPrefix())) {
			if (!Pattern.compile("^A{1}\\[{1}B{1}\\]{1}:{1}[A-Za-z]+={1}<{1}((true|false),?)+>{1};{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Boolean array on line "+cur+" is not valid"));
				return false;
			}
		}else if (line.startsWith(ConfigDataTypes.DOUBLE_ARRAY.getPrefix())) {
			if (!Pattern.compile("^A{1}\\[{1}D{1}\\]{1}:{1}[A-Za-z]+={1}<{1}(-{0,1}[0-9]+\\.{1}[0-9]+,?)+>{1};{1}$").matcher(line).matches()) {
				log.fatal(new ConfigFormatException("Config File is formated incorrectly: Double array on line "+cur+" is not valid"));
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
	
	private void addToConfig(String line, String lastLine) throws IOException {
		Config cfg = new Config();
		boolean isArray=line.replaceAll("^A?\\[?[SIDB]{1}\\]?:{1}[A-Za-z]+=?", "").startsWith("<")&&line.replaceAll(";{1}$", "").endsWith(">");
		String[] values= {""};
		if (isArray) {
			values = line.replaceAll("^A?\\[?[SIDB]{1}\\]?:{1}[A-Za-z]+=?<?", "").trim().replaceAll(">?;{1}$", "").split(",");
		}else {
			values[0] = line.replaceAll("^A?\\[?[SIDB]{1}\\]?:{1}[A-Za-z]+=?<?", "").trim().replaceAll(">?;{1}$", "");
		}
		String name = line.replaceAll("^A?\\[?[DIBS]{1}\\]?:{1}", "").replaceAll("={1}<?.+?>?;{1}$", "");
		ConfigDataTypes type = ConfigDataTypes.checkType(line.replaceAll("[A-Za-z]+={1}<?[A-Za-z0-9\\., -]+>?;{1}$", ""));
		String description=lastLine.replace("#", "");
		/*TODO
		 *Change how description saving works. The problem is that descriptions can be multi line and i need to save that
		 *Also, Need to save default values as well
		 *probably will save the as '{DEFAULT_VALUE}' after the normal value
		 */
		log.debug("Config Option "+name+" added with value "+Arrays.toString(values));
		cfg.addValue(name, type, description, isArray, values);
	}	
}