package ExeptionHandler;

public class UserServiceException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}