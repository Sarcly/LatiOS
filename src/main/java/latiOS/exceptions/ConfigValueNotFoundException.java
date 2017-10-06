package latiOS.exceptions;

public class ConfigValueNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigValueNotFoundException() {
		super();
	}
	
	public ConfigValueNotFoundException(String reason) {
		super(reason);
	}
}
