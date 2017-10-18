package latiOS.config;

public enum ConfigDataTypes {
	STRING("S:"),
	INT("I:"),
	DOUBLE("D:"),
	BOOLEAN("B:"),
	BOOLEAN_ARRAY("A[B]:"),
	INT_ARRAY("A[I]:"),
	DOUBLE_ARRAY("A[D]:"),
	STRING_ARRAY("A[S]:");
	
	private final String prefix;
	
	ConfigDataTypes(String prefix) {
		this.prefix=prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public static ConfigDataTypes checkType(String prefix) {
		switch(prefix){
			case "S:":return STRING;
			case "I:":return INT;
			case "B:":return BOOLEAN;
			case "D:":return DOUBLE;
			case "A[D]:":return DOUBLE_ARRAY;
			case "A[I]:":return INT_ARRAY;
			case "A[S]:":return STRING_ARRAY;
			case "A[B]:":return BOOLEAN_ARRAY;
			default:return STRING;
		}
	}
}
