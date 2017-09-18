package latiOS.exceptions;

public class MultiServerException extends RuntimeException {

	public MultiServerException() {
		this("LatiOS can only be connected to ONE Discord Server!");
	}
	public MultiServerException(String reason) {
		super(reason);
	}
}
