package latiOS.config;

public class ConfigValue {
	
	private final String name;
	private final ConfigDataTypes type;
	private final String description;
	private final boolean isArray;
	private final String[] defaultValues;
	private String[] values;
	
	protected ConfigValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValues, String[] values) {
		this.name=name;
		this.type=type;
		this.description=description;
		this.isArray=isArray;
		this.defaultValues=defaultValues;
		this.values=values;
	}
	
	protected ConfigValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValues) {
		this.name=name;
		this.type=type;
		this.description=description;
		this.isArray=isArray;
		this.defaultValues=defaultValues;
		this.values=null;
	}
	
	protected String getName() {
		return name;
	}
	
	protected ConfigDataTypes getType() {
		return type;
	}
	
	protected String getDescription() {
		return description;
	}
	
	protected boolean isArray() {
		return isArray;
	}
	
	protected String[] getValuesAsArray() {
		if(values==null) {
			return null;
		}else {
			return values;
		}
	}
	
	protected String getValue() {
		if(values==null) {
			return null;
		}else {
			return values[0];
		}
	}
	
	protected String[] getDefaultValuesAsArray() {
		return defaultValues;
	}
	
	protected String getDefaultValue() {
		return defaultValues[0];
	}
	
	/**
	 * Changes the value of an array {@link latiOS.config.ConfigValue ConfigValue}
	 * @param values
	 * @return false if operation was not completed
	 */
	protected boolean changeValues(String[] values) {
		if (!this.isArray) {
			return false;
		}
		this.values=values;
		return true;
	}
	
	/**
	 * Changes the value of a {@link latiOS.config.ConfigValue ConfigValue}
	 * @param value
	 * @return false if operation was not completed
	 */
	protected boolean changeValue(String value) {
		if (this.isArray) {
			return false;
		}
		String[] v = {value};
		this.values=v;
		return true;
	}
}
