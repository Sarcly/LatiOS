package latiOS.config;

public enum ConfigValues {
	BotToken("BotToken", ConfigDataTypes.STRING,""),
	BotName("BotName", ConfigDataTypes.STRING,""),
	//Testing values
	bool1("BotName", ConfigDataTypes.BOOLEAN,""),
	string1("BotName", ConfigDataTypes.STRING,""),
	double1("BotName", ConfigDataTypes.DOUBLE,""),
	int1("BotName", ConfigDataTypes.INT,"");
	
	private final String name;
	private final ConfigDataTypes type;
	private final String description;
	
	ConfigValues(String name, ConfigDataTypes type, String description) {
		this.name=name;
		this.type=type;
		this.description=description;
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
}
