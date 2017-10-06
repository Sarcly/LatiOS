package latiOS.config;

public class ConfigValue {

	private final String name;
	private final ConfigDataTypes type;
	private final String description;
	private final Object defaultValue;
	private final Object[] defaultValues;
	private Object value;
	private Object[] values;
	private final boolean isArray;
	
	protected ConfigValue(String name, ConfigDataTypes type, String description, Object defaultValue, Object value) {
		this.name=name;
		this.type=type;
		this.description=description;
		this.defaultValue=defaultValue;
		this.value=value;
		this.defaultValues=null;
		this.values=null;
		this.isArray=false;
	}
	
	protected ConfigValue(String name, ConfigDataTypes type, String description, Object[] defaultValues, Object[] values) {
		this.name=name;
		this.type=type;
		this.description=description;
		this.defaultValues=defaultValues;
		this.values=values;
		this.defaultValue=null;
		this.value=null;
		this.isArray=true;
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
	
	protected Object getDefaultValue() {
		return defaultValue;
	}
	
	protected Object[] getDefaultValues() {
		return defaultValues;
	}
	
	protected Object getValue() {
		return value;
	}
	
	protected Object[] getValues() {
		return values;
	}
	
	protected boolean isArray() {
		return isArray;
	}
	
	protected void changeValue(Object v) {
		value = v;
	}
}
