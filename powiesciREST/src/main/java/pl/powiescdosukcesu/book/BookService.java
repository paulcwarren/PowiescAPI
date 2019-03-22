package pl.powiescdosukcesu.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    Book getBookById(long id);

    void deleteBook(String bookTitle,String authenticationPrincipalName);

    Page<BookShortInfoDTO> getBooksByKeyword(Pageable pageable, String keyword);

    Page<BookShortInfoDTO> getBooks(Pageable pageable, String keyword,String createdDate);

    List<Book> getBooksByGenres(String[] genres);

    Book updateBook(Book book);

    List<String> loadImages();

    Book addComment(AddCommentDTO addCommentDTO, String username);

    Book saveBook(BookCreationDTO book, String username);

    FullBookInfoDTO getBookByTitle(String bookTitle);

    void addRating(AddVoteDTO addVoteDTO, String username);

    List<CommentDTO> getBookComments(String bookTitle, int pageNum);
}
