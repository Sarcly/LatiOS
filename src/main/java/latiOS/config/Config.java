package latiOS.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import latiOS.exceptions.ConfigValueNotFoundException;
import latiOS.gui.Gui;

public class Config {

	private static HashMap<String, ConfigValue> values = new HashMap<>();
	
	private static boolean wait = false;
	
	public Config() {
		super();
	}
	
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String defaultValue, String value) throws IOException {
		String[] dv = {defaultValue};
		String[] v = {value};
		values.put(name, new ConfigValue(name, type, description, isArray, dv, v));
		makeConfig();
	}
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @param description
	 * @param isArray
	 * @param defaultValue
	 * @throws IOException
	 */
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String defaultValue) throws IOException {
		String[] dv = {defaultValue};
		values.put(name, new ConfigValue(name, type, description, isArray, dv));
		makeConfig();
	}
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @param description
	 * @param isArray
	 * @param defaultValue
	 * @param value
	 * @throws IOException
	 */
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValue, String[] value) throws IOException {
		values.put(name, new ConfigValue(name, type, description, isArray, defaultValue, value));
		makeConfig();
	}
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @param description
	 * @param isArray
	 * @param defaultValue
	 * @throws IOException
	 */
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValue) throws IOException {
		values.put(name, new ConfigValue(name, type, description, isArray, defaultValue));
		makeConfig();
	}

	public void changeValue(String name, String newValue) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			values.get(name).changeValue(newValue);
			makeConfig();
		}
	}
	
	public void changeValue(String name, String[] newValue) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			values.get(name).changeValues(newValue);
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
			if (values.get(name).getValue()==null) {
				return values.get(name).getDefaultValue();
			}else {
				return values.get(name).getValue();
			}
		}	
	}
	
	public String[] getValues(String name) throws ConfigValueNotFoundException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value "+name+" does not exist");
		}else {
			if (values.get(name).getValuesAsArray()==null) {
				return values.get(name).getDefaultValuesAsArray();
			}else {
				return values.get(name).getValuesAsArray();
			}
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
				cw.addValue(v);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});	
	}
	
	public boolean configExsists() {
		File cfg = new File("Configs/config.cfg");
		if (cfg.exists()) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean waitForGui(boolean wait){
		while (wait) {
			//TODO do this better
			System.out.println("waiting...");
		}
		return true;
	} 
	
	public void release() {
		wait = false;
	}
	
	public boolean openGui() {
		Gui.start(null);
		return true;
	}
}