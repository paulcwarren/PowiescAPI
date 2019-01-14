package pl.powiescdosukcesu.controlleradvices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.powiescdosukcesu.errorresponses.FileEntErrorResponse;
import pl.powiescdosukcesu.exceptionhandling.FileEntNotFoundException;

@ControllerAdvice
public class FileRestControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<FileEntErrorResponse> handleException(FileEntNotFoundException exc){
		
		FileEntErrorResponse error = new FileEntErrorResponse(
											HttpStatus.NOT_FOUND.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
				
	}
	
}
