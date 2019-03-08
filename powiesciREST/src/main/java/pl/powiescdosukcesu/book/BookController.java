package pl.powiescdosukcesu.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.powiescdosukcesu.security.UserPrincipal;

import javax.validation.Valid;
import java.security.Principal;
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
    public Page<BookShortInfoDTO> getBooks(@PageableDefault(value = 12) Pageable pageable,
                                           @RequestParam(required = false) @Nullable String keyword) {

        if (keyword != null && !keyword.isBlank())
            return bookService.getBooksByKeyword(pageable, keyword);
        return bookService.getBooks(pageable);

    }


    @GetMapping(value="{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getBookImage(@PathVariable int id){

        return bookService.getBookById(id).getBackgroundImage();

    }

    @GetMapping("/title/{bookTitle}")
    public ResponseEntity<FullBookInfoDTO> getBookByTitle(@PathVariable String bookTitle) {

        return ResponseEntity.ok()
                .body(bookService.getBookByTitle(bookTitle));
    }



    @GetMapping("/genre")
    public List<Book> filterByGenres(@RequestParam("genres") String[] genres) {

        return bookService.getBooksByGenres(genres);
    }



    @DeleteMapping
    @PreAuthorize("isAuthenticated() and #book.user.getUsername() == #principal.getName()")
    public ResponseEntity<String> deleteBook(@RequestBody Book book, Principal principal) {

        bookService.deleteBook(book);
        return new ResponseEntity<>("Book successfully deleted", HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated() and #book.user.username == #principal.getName()")
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<String>> saveBook(@Valid @RequestBody BookCreationDTO book, @AuthenticationPrincipal UserPrincipal user) {

        bookService.saveBook(book, user);

        return new ResponseEntity<>(Collections.singletonList("Book successfully updated"), HttpStatus.OK);

    }

    @PostMapping("/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> postComment(@RequestBody AddCommentDTO addCommentDTO, @AuthenticationPrincipal UserPrincipal user) {

        System.out.println(addCommentDTO);
        bookService.addComment(addCommentDTO, user.getUsername());
        return ResponseEntity
                .ok()
                .body("Sucess");
    }

    @PostMapping("/rating")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity addRating(@RequestBody AddVoteDTO addVoteDTO, @AuthenticationPrincipal UserPrincipal user) {

        bookService.addRating(addVoteDTO, user.getUsername());
        return ResponseEntity.ok("Success");
    }


    @GetMapping("/images")
    public List<String> loadImages() {

        return bookService.loadImages();
    }


    //TODO
    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable long id) {
        return ResponseEntity.ok()
                .body(bookService.getBookById(id).getComments().stream()
                        .map(com -> modelMapper.map(com, CommentDTO.class)).collect(Collectors.toList()));
    }

}
