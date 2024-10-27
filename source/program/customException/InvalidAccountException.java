package program.customException;

@SuppressWarnings("serial")
public class InvalidAccountException extends RuntimeException {

	public InvalidAccountException(String err) {
		super(err);
	}
	
}
