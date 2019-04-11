package pl.powiescdosukcesu.book;

public class BookNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException() {
	
	}

	public BookNotFoundException(String message) {
		super(message);
		
	}

	public BookNotFoundException(Throwable cause) {
		super(cause);
		
	}

	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public BookNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
