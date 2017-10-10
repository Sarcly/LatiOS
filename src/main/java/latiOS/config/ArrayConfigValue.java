package latiOS.config;

public class ArrayConfigValue extends IConfigValue {

	private final String[] defaultValues;
	private String[] values;

	protected ArrayConfigValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValues) {
		super(name, type, description, isArray);
		this.defaultValues=defaultValues;
	}
	
	protected ArrayConfigValue(String name, ConfigDataTypes type, String description, boolean isArray, String[] defaultValues, String[] values) {
		super(name, type, description, isArray);
		this.defaultValues=defaultValues;
		this.values=values;
	}

	protected String[] getValues() {
		return values;
	}

	protected String[] getDefaultValues() {
		return defaultValues;
	}
	
	protected void changeValue(String[] newValue) {
		this.values=newValue;
	}
}
