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
	
	@Override
	protected String getValue() {
		return value;
	}

	@Override
	protected String getDefaultValue() {
		return defaultValue;
	}

	@Override
	protected void changeValue() {
		
	}
	
}
