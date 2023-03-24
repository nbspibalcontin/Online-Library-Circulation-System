package ExeptionHandler;

public class BookTransactionException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookTransactionException(String message) {
        super(message);
    }

    public BookTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}