package pl.powiescdosukcesu.book.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.files.BookContentFileStore;
import pl.powiescdosukcesu.book.repositories.BookRepository;

import java.util.Optional;

@RestController
public class FileContentController {

	@Autowired
    private BookRepository bookRepository;
	@Autowired
    private BookContentFileStore store;

	@RequestMapping(value="/bookStore/{fileId}", method = RequestMethod.GET)
	public ResponseEntity<?> getContent(@PathVariable("fileId") Long id) {

		Optional<Book> f = bookRepository.findById(id);
		if (f.isPresent()) {
			var inputStreamResource = new InputStreamResource(store.getContent(f.get().getFile()));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(f.get().getFile().getContentLength());
			headers.set("Content-Type", f.get().getFile().getMimeType());
			return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
		}
		return null;
	}
}