package latiOS.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import latiOS.exceptions.ConfigFormatException;
import latiOS.exceptions.ConfigValueNotFoundException;
import latiOS.gui.Gui;

public class Config {

	private static HashMap<String, ConfigValue> values = new HashMap<>();

	private static File cfgFile = new File("Configs/config.cfg");

	public Config() {
		super();
	}

	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String defaultValue,
			String value) throws IOException {
		String[] dv = { defaultValue };
		String[] v = { value };
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
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String defaultValue)
			throws IOException {
		String[] dv = { defaultValue };
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
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValue,
			String[] value) throws IOException {
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
	public void addValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValue)
			throws IOException {
		values.put(name, new ConfigValue(name, type, description, isArray, defaultValue));
		makeConfig();
	}

	public void changeValue(String name, String newValue) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value " + name + " does not exist");
		} else {
			values.get(name).changeValue(newValue);
			makeConfig();
		}
	}

	public void changeValue(String name, String[] newValue) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value " + name + " does not exist");
		} else {
			values.get(name).changeValues(newValue);
			makeConfig();
		}
	}

	public void removeValue(String name) throws ConfigValueNotFoundException, IOException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value " + name + " does not exist");
		} else {
			values.remove(name);
			makeConfig();
		}
	}

	public String getValue(String name) throws ConfigValueNotFoundException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value " + name + " does not exist");
		} else {
			if (values.get(name).getValue() == null) {
				return values.get(name).getDefaultValue();
			} else {
				return values.get(name).getValue();
			}
		}
	}

	public String[] getValues(String name) throws ConfigValueNotFoundException {
		if (!values.containsKey(name)) {
			throw new ConfigValueNotFoundException("Value " + name + " does not exist");
		} else {
			if (values.get(name).getValuesAsArray() == null) {
				return values.get(name).getDefaultValuesAsArray();
			} else {
				return values.get(name).getValuesAsArray();
			}
		}
	}

	public Map<String, ConfigValue> getAll() {
		return Collections.unmodifiableMap(values);
	}

	public void makeConfig() throws IOException {
		ConfigWriter cw = new ConfigWriter(new FileOutputStream(cfgFile));
		cw.makeHeader("Test");
		values.forEach((k, v) -> {
			try {
				cw.addValue(v);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void mkDirs() throws IOException {
		Files.createDirectories(Paths.get("./Configs"));
	}

	public boolean configExsists() {
		if (cfgFile.exists()) {
			return true;	
		}
		return false;
	}

	public void openGui() {
		Gui.start(null);
	}

	public void readConfig() throws IOException {
		ConfigReader cfgr = new ConfigReader(new FileInputStream(cfgFile));
		try {
			cfgr.readAll();
		} catch (ConfigFormatException e) {
			e.printStackTrace();
		}
	}
}