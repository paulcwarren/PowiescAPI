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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserService;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("bookService")
@Transactional
@Log4j2
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRep;

    private final AppUserService appUserService;

    private final GenreRepository genreRep;

    private final VoteRepository voteRepository;

    private final ModelMapper modelMapper;


    @Override
    public Page<BookShortInfoDTO> getBooks(Pageable pageable,
                                           final String keyword,
                                           final String createdDate) {

        Page<Book> entities = getCorrectPageByValues(pageable, keyword, createdDate);


        return mapToShort(entities);
    }

    private Page<Book> getCorrectPageByValues(Pageable pageable,
                                              final String keyword,
                                              final String createdDate) {
        final String language = "pl";

        final String datePattern = "yyyy-MM-dd";

        if (keyword != null && !keyword.isBlank())
            return bookRep.findFilesByKeyword(pageable, keyword);
        else if (createdDate != null && !createdDate.isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
            formatter = formatter.withLocale(new Locale(language));
            LocalDate date = LocalDate.parse(createdDate, formatter);

            return bookRep.findByCreatedDate(pageable, date);
        } else {
            return bookRep.findAll(pageable);
        }


    }

    @Override
    public Book getBookById(long id) {

        return bookRep.findById(id).orElseThrow(BookNotFoundException::new);

    }

    @Override
    public Page<BookShortInfoDTO> getBooksByKeyword(Pageable pageable,
                                                    final String keyword) {

        Page<Book> entities = bookRep.findFilesByKeyword(pageable, keyword);

        return mapToShort(entities);
    }

    @Override
    public List<Book> getBooksByGenres(final String[] genres) {

        return bookRep.findByGenres(genres);
    }

    @Override
    public Book saveBook(@NonNull BookCreationDTO bookDTO,
                         @NonNull final String username) {

        AppUser user = appUserService.getUser(username);

        Book book = convertFromCreationDtoToBook(bookDTO);
        book.setUser(user);

        bookRep.save(book);

        return book;

    }

    @Override
    public FullBookInfoDTO getBookByTitle(@NonNull String bookTitle) {

        Book book = bookRep.findOneByTitle(bookTitle).orElseThrow(BookNotFoundException::new);

        Hibernate.initialize(book.getComments());
        FullBookInfoDTO dto = modelMapper.map(book, FullBookInfoDTO.class);
        dto.setContent(new String(book.getFile()));


        return dto;
    }

    @Override
    public void addRating(@NonNull AddVoteDTO addVoteDTO,
                          @NonNull String username) {
        Book book = bookRep.findById(addVoteDTO.getBookId()).orElseThrow(BookNotFoundException::new);
        AppUser user = appUserService.getUser(username);
        Vote vote;
        if (isVotePresent(book, user)) {
            vote = voteRepository.findByUserAndBook(user, book).get();
            vote.setRating(addVoteDTO.getRating());
            voteRepository.save(vote);
            return;
        }
        vote = Vote.builder()
                .book(book)
                .rating(addVoteDTO.getRating())
                .user(user)
                .build();
        book.addVote(vote);
        updateBook(book);

    }

    private boolean isVotePresent(Book book, AppUser user) {
        return voteRepository.findByUserAndBook(user, book).isPresent();
    }


    @Override
    @Async
    public void deleteBook(@NonNull String bookToGetDeletedTitle,
                           @NonNull String authPrincipalName) {

        Book bookToGetDeleted = bookRep.findOneByTitle(bookToGetDeletedTitle)
                .orElseThrow(BookNotFoundException::new);

        if(!Objects.equals(bookToGetDeleted.getUser().getUsername(), authPrincipalName))
            throw new AccessDeniedException("User not the owner of the book");

        bookRep.delete(bookToGetDeleted);


    }

    @Override
    public Book updateBook(@NonNull Book book) {


        Optional<Book> optionalBook = bookRep.findOneByTitle(book.getTitle());
        if (optionalBook.isPresent()) {
            book.setUser(getBookById(book.getId()).getUser());
            bookRep.updateBook(book);
            return book;
        } else {
            throw new BookNotFoundException();
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
    public Book addComment(@NonNull AddCommentDTO addCommentDTO,
                           @NonNull final String username) {

        Book book = bookRep.findById(addCommentDTO.getBookId()).orElseThrow(BookNotFoundException::new);
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


    @Override
    public List<CommentDTO> getBookComments(final long bookId,
                                            final int pageNum) {

        final int defaultNumOfComments = 20;

        return bookRep.findById(bookId)
                .orElseThrow(BookNotFoundException::new)
                .getComments().stream()
                .map(com -> modelMapper.map(com, CommentDTO.class))
                .skip(defaultNumOfComments * pageNum)
                .limit(defaultNumOfComments)
                .collect(Collectors.toList());
    }

    private List<String> convertToNames(Set<Genre> genres) {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
    }


    private Page<BookShortInfoDTO> mapToShort(Page<Book> page) {
        return page.map(b -> BookShortInfoDTO.builder()
                .id(b.getId())
                .title(b.getTitle())
                .createdDate(b.getCreatedDate())
                .genres(convertToNames(b.getGenres()))
                .description(b.getDescription())
                .username(b.getUser().getUsername())
                .rating(b.getRating())
                .build());
    }

    private Book convertFromCreationDtoToBook(BookCreationDTO bookDTO){
        Set<Genre> genres = bookDTO.getGenres().stream().map(genreRep::findGenreByName).collect(Collectors.toSet());

        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .file(bookDTO.getFile().getBytes())
                .genres(genres)
                .description(bookDTO.getDescription())
                .build();

        if (bookDTO.getImage() != null)
            book.setBackgroundImage(java.util.Base64.getDecoder().decode(bookDTO.getImage()));

        return book;
    }

}
