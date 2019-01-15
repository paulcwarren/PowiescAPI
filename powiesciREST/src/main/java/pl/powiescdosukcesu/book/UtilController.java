package pl.powiescdosukcesu.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/images")
	public List<String> loadImages() {
		
		return bookService.loadImages();
	}
	
}
