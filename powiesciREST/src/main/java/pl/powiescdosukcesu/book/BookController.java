package pl.powiescdosukcesu.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
@Log4j2
@RequiredArgsConstructor
public class BookController {


    private final BookService bookService;

    private final ModelMapper modelMapper;


    @GetMapping
    public List<BookShortInfoDTO> getBooks() {

        return bookService.getBooks().stream()
                .map(file -> modelMapper.map(file, BookShortInfoDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/id/{fileId}")
    public ResponseEntity<Book> showContent(@PathVariable long fileId) {
        Book file = bookService.getBookById(fileId);
        return new ResponseEntity<>(file, HttpStatus.OK);

    }

    @GetMapping("/{keyword}")
    public ResponseEntity<List<Book>> filterByKeyword(@PathVariable String keyword) {
        List<Book> files = bookService.getBooksByKeyword(keyword);

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/genre")
    public List<Book> filterByGenres(@RequestParam("genres") String[] genres) {

        return bookService.getBooksByGenres(genres);
    }

    @GetMapping("/date")
    public List<Book> filterByGenre(@RequestParam("creationDate") LocalDate creationDate) {

        return bookService.getBooksByDate(creationDate);
    }

    @DeleteMapping
    @PreAuthorize("#file.user.username == #principal.getName()")
    public ResponseEntity<String> deleteBook(@RequestBody Book file, Principal principal) {

        bookService.deleteBook(file);

        return new ResponseEntity<>("Book successfully deleted", HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("#book.user.username == #principal.getName()")
    public ResponseEntity<List<String>> updateBook(@Valid @RequestBody Book book, Principal principal, Errors errors) {

        List<String> errorMessages = new ArrayList<>();
        errors.getAllErrors().forEach(e -> errorMessages.add(e.getDefaultMessage()));

        if (errors.hasErrors()) {
            errorMessages.forEach(log::info);
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        bookService.updateBook(book);

        return new ResponseEntity<>(Collections.singletonList("Book successfully updated"), HttpStatus.OK);

    }

    @PostMapping
    public void processFile(@RequestParam("file") MultipartFile file,
                            @RequestParam("title") String title,
                            @RequestParam("genres") String[] genres,
                            @RequestParam("backGroundImage") byte[] image,
                            Principal principal)
            throws IOException {

        bookService.saveFile(file, title, genres, image, principal.getName());

    }


    @GetMapping("/images")
    public List<String> loadImages() {

        return bookService.loadImages();
    }

}
