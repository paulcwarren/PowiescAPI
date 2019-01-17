package pl.powiescdosukcesu.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.powiescdosukcesu.appuser.FileEntErrorResponse;

@ControllerAdvice
public class BookRestControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<FileEntErrorResponse> handleException(BookNotFoundException exc){
		
		FileEntErrorResponse error = new FileEntErrorResponse(
											HttpStatus.NOT_FOUND.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
				
	}
	
}
