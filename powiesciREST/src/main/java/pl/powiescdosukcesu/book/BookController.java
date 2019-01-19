package pl.powiescdosukcesu.book;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/books")
@CrossOrigin
@Log4j2
public class BookController {

	

	@Autowired
	private BookService bookService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<BookShortInfoDTO> getFiles() {

		List<Book> files = bookService.getFiles();
		return files.stream().map(file -> modelMapper.map(file, BookShortInfoDTO.class)).collect(Collectors.toList());
	}

	@GetMapping("/id/{fileId}")
	public ResponseEntity<Book> showContent(@PathVariable long fileId) {
		Book file = bookService.getFileById(fileId);
		return new ResponseEntity<>(file, HttpStatus.OK);

	}

	@GetMapping("/{keyword}")
	public ResponseEntity<List<Book>> filterByKeyword(@PathVariable String keyword) {
		List<Book> files = bookService.getFilesByKeyword(keyword);
		if (files == null)
			throw new BookNotFoundException("Dana powieść nie istnieje");

		return new ResponseEntity<>(files, HttpStatus.OK);
	}

	@GetMapping("/byGenre")
	public List<Book> filterByGenres(@RequestParam("genres") String[] genres) {

		return bookService.getFilesByGenres(genres);
	}

	@GetMapping("/byDate")
	public List<Book> filterByGenre(@RequestParam("creationDate") String creationDate) {

		return bookService.getFilesByDate(creationDate);
	}

	@DeleteMapping
	@PreAuthorize("#file.user.userName == #principal.getName()")
	public ResponseEntity<String> deleteBook(@RequestBody Book file, Principal principal) {

		bookService.deleteBook(file);

		return new ResponseEntity<String>("Udało się usunąć plik", HttpStatus.OK);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("#file.user.userName == #principal.getName()")
	public ResponseEntity<List<String>> updateBook(@Valid @RequestBody Book file, Principal principal, Errors errors) {

		List<String> errorMessages = new ArrayList<>();
		errors.getAllErrors().forEach(e -> errorMessages.add(e.getDefaultMessage()));

		if (errors.hasErrors()) {
			errorMessages.forEach(e -> log.info(e));
			return new ResponseEntity<List<String>>(errorMessages, HttpStatus.BAD_REQUEST);
		}

		bookService.updateFile(file);

		return new ResponseEntity<>(Arrays.asList("Plik został zaktualniony"), HttpStatus.OK);

	}

	@PostMapping
	public void processFile(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
			@RequestParam("genres") String[] genres, @RequestParam("backGroundImage") byte[] image, Principal principal)
			throws IOException {

		bookService.saveFile(file, title, genres, image, principal.getName());

	}


	@GetMapping("/images")
	public List<String> loadImages() {

		return bookService.loadImages();
	}

}
