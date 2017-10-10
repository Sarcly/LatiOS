package latiOS.config;

public class ArrayConfigValue extends IConfigValue {

	protected ArrayConfigValue(String name, ConfigDataTypes type, String description, boolean isArray) {
		super(name, type, description, isArray);

	}

	@Override
	protected String getValue() {
		return null;
	}

	@Override
	protected String getDefaultValue() {
		return null;
	}

	@Override
	protected void changeValue(String newValue) {
		this.value=newValue
	}

}
