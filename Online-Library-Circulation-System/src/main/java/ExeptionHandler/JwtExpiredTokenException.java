package ExeptionHandler;

public class JwtExpiredTokenException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtExpiredTokenException(String message) {
        super(message);
    }
}
