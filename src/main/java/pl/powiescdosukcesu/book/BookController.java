package pl.powiescdosukcesu.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.powiescdosukcesu.security.UserPrincipal;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/books")
@CrossOrigin
@Log4j2
@RequiredArgsConstructor
public class BookController {


    private final BookService bookService;

    @GetMapping
    public Page<BookShortInfoDTO> getBooks(@PageableDefault(value = 12) Pageable pageable,
                                           @RequestParam(required = false) @Nullable final String keyword,
                                           @RequestParam(required = false) @Nullable final String createdDate) {

        return bookService.getBooks(pageable, keyword, createdDate);

    }


    @GetMapping(value = "{id}/image",
                produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getBookImage(@PathVariable int id) {

        return bookService.getBookById(id).getBackgroundImage();

    }

    @GetMapping("/title/{bookTitle}")
    public ResponseEntity<FullBookInfoDTO> getBookByTitle(@PathVariable final String bookTitle) {

        return ResponseEntity.ok(bookService.getBookByTitle(bookTitle));

    }



    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@RequestParam String bookToGetDeletedTitle ,
                           @AuthenticationPrincipal UserPrincipal principal) {

        bookService.deleteBook(bookToGetDeletedTitle,principal.getUsername());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated() and #book.user.username == #principal.getUsername()")
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@Valid @RequestBody Book book,
                           @AuthenticationPrincipal UserPrincipal principal) {

        bookService.updateBook(book);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public void saveBook(@Valid @RequestBody BookCreationDTO book,
                         @AuthenticationPrincipal UserPrincipal user) {

        bookService.saveBook(book, user.getUsername());

    }

    @PostMapping("/comment")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void postComment(@RequestBody AddCommentDTO addCommentDTO,
                            @AuthenticationPrincipal UserPrincipal user) {

        bookService.addComment(addCommentDTO, user.getUsername());
    }

    @PostMapping("/rating")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRating(@RequestBody AddVoteDTO addVoteDTO,
                          @AuthenticationPrincipal UserPrincipal user) {

        bookService.addRating(addVoteDTO, user.getUsername());

    }

    @GetMapping("/{bookId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable final long bookId,
                                                        @RequestParam final int page) {
        return ResponseEntity.ok()
                .body(bookService.getBookComments(bookId, page));
    }

}