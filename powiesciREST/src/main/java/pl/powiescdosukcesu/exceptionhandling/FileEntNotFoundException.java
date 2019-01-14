package pl.powiescdosukcesu.exceptionhandling;

public class FileEntNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileEntNotFoundException() {
	
	}

	public FileEntNotFoundException(String message) {
		super(message);
		
	}

	public FileEntNotFoundException(Throwable cause) {
		super(cause);
		
	}

	public FileEntNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public FileEntNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
