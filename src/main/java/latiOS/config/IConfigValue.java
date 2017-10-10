package latiOS.config;

public abstract class IConfigValue {
	
	private final String name;
	private final ConfigDataTypes type;
	private final String description;
	private final boolean isArray;
	
	protected IConfigValue(String name, ConfigDataTypes type, String description, boolean isArray) {
		this.name=name;
		this.type=type;
		this.description=description;
		this.isArray=isArray;
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
	
	protected abstract String getValue();
	
	protected abstract String getDefaultValue();
	
	protected abstract void changeValue(String newValue);
}
