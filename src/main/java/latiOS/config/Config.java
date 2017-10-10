package latiOS.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import latiOS.exceptions.ConfigValueNotFoundException;

public class Config {

	private static HashMap<String, ConfigValue> values;
	
	public Config() {
		super();
	}
	
	public void addValue(String name, ConfigDataTypes type, String description, Object defalutValue, Object value) throws IOException {
		values.put(name, new ConfigValue(name, type, description, defalutValue, value));
		makeConfig();
	}
	
	public void addValue(String name, ConfigDataTypes type, String description, Object defalutValue) throws IOException {
		values.put(name, new ConfigValue(name, type, description, defalutValue));
		makeConfig();
	}
	
	public void changeValue(String name, Object newValue) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			values.get(name).changeValue(newValue);
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
	
	public Object get(String name) throws ConfigValueNotFoundException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			return values.get(name).getValue();				
		}	
	}

	public Map<String, ConfigValue> getAll() {
		return Collections.unmodifiableMap(values);
	}
	
	public void makeConfig() throws IOException {
		ConfigWriter cw = new ConfigWriter(new FileOutputStream(new File("Configs/cfg.cfg")));
		cw.makeHeader("Test");
		values.forEach((k,v)->{
			try {
				cw.addValue(v,v.isArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
	}
	
	
	
}
