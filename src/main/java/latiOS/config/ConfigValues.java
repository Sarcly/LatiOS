package latiOS.config;

public enum ConfigValues {
	BotToken("BotToken", ConfigDataTypes.STRING,"",null),
	BotName("BotName", ConfigDataTypes.STRING,"",null),
	//Testing values
	bool1("BotName", ConfigDataTypes.BOOLEAN,"",true),
	string1("BotName", ConfigDataTypes.STRING,"","jeff"),
	double1("BotName", ConfigDataTypes.DOUBLE,"",69.69),
	int1("BotName", ConfigDataTypes.INT,"",42);
	
	private final String name;
	private final ConfigDataTypes type;
	private final String description;
	private final Object defaltValue;
	
	ConfigValues(String name, ConfigDataTypes type, String description, Object defalutValue) {
		this.name=name;
		this.type=type;
		this.description=description;
		this.defaltValue=defalutValue;
	}
	
	public String getName() {
		return name;
	}
	
	public ConfigDataTypes getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Object getDefaultValue() {
		return defaltValue;
	}
}
