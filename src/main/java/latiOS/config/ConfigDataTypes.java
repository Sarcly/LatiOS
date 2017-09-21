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
}
