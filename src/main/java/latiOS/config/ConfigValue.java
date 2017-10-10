package latiOS.config;

public class ConfigValue extends IConfigValue{

	private final String defaultValue;
	private String value;
	
	protected ConfigValue(String name, ConfigDataTypes type, String description, boolean isArray, String defalutValue) {
		super(name, type, description, isArray);
		this.defaultValue=defalutValue;
	}
	
	protected ConfigValue(String name, ConfigDataTypes type, String description, boolean isArray, String defalutValue, String value) {
		super(name, type, description, isArray);
		this.defaultValue=defalutValue;
		this.value=value;
	}
	
	protected String getValue() {
		return value;
	}

	protected String getDefaultValue() {
		return defaultValue;
	}

	protected void changeValue(String newValue) {
		this.value=newValue;
	}
	
}
