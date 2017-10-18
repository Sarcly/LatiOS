package latiOS.exceptions;

public enum ErrorCodes {
	CONFIG_ERROR(3);
	
	public final int err;
	
	ErrorCodes(int err) {
		this.err=err;
	}
	
	public int getValue(){
		return err;
	}
}
