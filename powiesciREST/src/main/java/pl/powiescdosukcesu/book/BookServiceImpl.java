package pl.powiescdosukcesu.book;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserService;
import pl.powiescdosukcesu.security.UserPrincipal;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("bookService")
@Transactional
@Log4j2
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRep;

    private final AppUserService appUserService;

    private final GenreRepository genreRep;

    private final ModelMapper modelMapper;

    @Override
    public Page<BookShortInfoDTO> getBooks(Pageable pageable) {

        Page<Book> entities = bookRep.findAll(pageable);
        Page<BookShortInfoDTO> dtos = entities.map(b -> BookShortInfoDTO.builder()
                .id(b.getId())
                .title(b.getTitle())
                .createdDate(b.getCreatedDate())
                .genres(convertToNames(b.getGenres()))
                .username(b.getUser().getUsername())
                .build());

        return dtos;
    }

    @Override
    public Book getBookById(long id) {

        Optional<Book> optionalBook = bookRep.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new BookNotFoundException();

        }

    }

    @Override
    public List<Book> getBooksByKeyword(String keyword) {

        List<Book> books = bookRep.findFilesByKeyword(keyword);
        if (!books.isEmpty()) {
            return books;
        } else {
            throw new BookNotFoundException();

        }
    }

    @Override
    public List<Book> getBooksByGenres(String[] genres) {

        List<Book> books = bookRep.findByGenres(genres);
        if (!books.isEmpty()) {
            return books;
        } else {
            throw new BookNotFoundException();

        }
    }

    @Override
    public Book saveBook(@NonNull BookCreationDTO bookDTO, UserPrincipal userPrincipal) {

        AppUser user = appUserService.getUser(userPrincipal.getId());
        Set<Genre> genres = bookDTO.getGenres().stream().map(g -> genreRep.findGenreByName(g)).collect(Collectors.toSet());
        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .file(bookDTO.getFile().getBytes())
                .genres(genres)
                .description(bookDTO.getDescription())
                .user(user)
                .build();

        bookRep.save(book);

        return book;

    }

    @Override
    public FullBookInfoDTO getBookByTitle(@NonNull String bookTitle) {

        Book book = bookRep.findOneByTitle(bookTitle).orElseThrow(() -> new BookNotFoundException());

        Hibernate.initialize(book.getComments());
        FullBookInfoDTO dto = modelMapper.map(book, FullBookInfoDTO.class);
        dto.setContent(new String(book.getFile()));


        return dto;
    }


    @Override
    @Async
    public void deleteBook(Book book) {
        Optional<Book> optionalBook = bookRep.findOneByTitle(book.getTitle());
        if (optionalBook.isPresent()) {
            bookRep.delete(book);
        } else {
            throw new BookNotFoundException();
        }

    }

    @Override
    public Book updateBook(Book book) {

        if (book != null) {
            Optional<Book> optionalBook = bookRep.findOneByTitle(book.getTitle());
            if (optionalBook.isPresent()) {
                book.setUser(getBookById(book.getId()).getUser());
                bookRep.updateBook(book);
                return book;
            } else {
                throw new BookNotFoundException();
            }

        } else {
            throw new NullPointerException("File cannot be null");
        }


    }

    @Override
    public List<String> loadImages() {

        List<byte[]> images = bookRep.loadImages();
        List<byte[]> encodedImages = images.stream().map(image -> {
            Base64 base = new Base64();
            return base.encode(image);
        }).collect(Collectors.toList());

        List<String> stringImages = new LinkedList<>();
        encodedImages.forEach(enc -> stringImages.add(new String(enc, StandardCharsets.UTF_8)));

        return stringImages;
    }

    @Override
    public List<Book> getBooksByDate(LocalDate date) {

        List<Book> books = bookRep.findByCreatedDate(date);
        if (!books.isEmpty()) {
            return books;
        } else {
            throw new BookNotFoundException();

        }
    }

    @Override
    public Book addComment(@NonNull AddCommentDTO addCommentDTO, @NonNull String username) {

        Book book = bookRep.findById(addCommentDTO.getBookId()).orElseThrow(() -> new BookNotFoundException());
        AppUser user = appUserService.getUser(username);
        Comment comment = Comment.builder()
                .book(book)
                .content(addCommentDTO.getComment())
                .user(user)
                .build();
        book.addComment(comment);
        updateBook(book);

        return book;
    }

    protected List<String> convertToNames(Set<Genre> genres) {
        return genres.stream().map(g -> g.getName()).collect(Collectors.toList());
    }

}
