package latiOS.exceptions;

public class ConfigFormatException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ConfigFormatException() {
		super();
	}
	
	public ConfigFormatException(String reason) {
		super(reason);
	}
}
