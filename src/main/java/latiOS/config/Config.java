package latiOS.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import latiOS.exceptions.ConfigValueNotFoundException;

public class Config {

	private static HashMap<String, IConfigValue> values = new HashMap<>();
	
	public Config() {
		super();
	}
	
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String defaultValue, String value) throws IOException {
		values.put(name, new ConfigValue(name, type, description, isArray, defaultValue, value));
		makeConfig();
	}
	
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String defaultValue) throws IOException {
		values.put(name, new ConfigValue(name, type, description, isArray, defaultValue));
		makeConfig();
	}
	
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValue, String[] value) throws IOException {
		values.put(name, new ArrayConfigValue(name, type, description, isArray, defaultValue, value));
		makeConfig();
	}
	
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValue) throws IOException {
		values.put(name, new ArrayConfigValue(name, type, description, isArray, defaultValue));
		makeConfig();
	}
	
	
	public void changeValue(String name, String newValue) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			((ConfigValue)values.get(name)).changeValue(newValue);
			makeConfig();
		}
	}
	
	public void changeValue(String name, String[] newValue) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			((ArrayConfigValue)values.get(name)).changeValue(newValue);
			makeConfig();
		}
	}
	
	public void removeValue(String name) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			values.remove(name);
			makeConfig();
		}
	}
	
	public String getValue(String name) throws ConfigValueNotFoundException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			return ((ConfigValue)values.get(name)).getValue();				
		}	
	}
	
	public String[] getValues(String name) throws ConfigValueNotFoundException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			return ((ArrayConfigValue)values.get(name)).getValues();				
		}	
	}

	public Map<String, IConfigValue> getAll() {
		return Collections.unmodifiableMap(values);
	}
	
	public void makeConfig() throws IOException {
		ConfigWriter cw = new ConfigWriter(new FileOutputStream(new File("Configs/cfg.cfg")));
		cw.makeHeader("Test");
		values.forEach((k,v)->{
			try {
				if (v.isArray()) {
					cw.addValue(((ArrayConfigValue)v));
				}else{
					cw.addValue(((ConfigValue)v));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
	}
	
	
	
}
