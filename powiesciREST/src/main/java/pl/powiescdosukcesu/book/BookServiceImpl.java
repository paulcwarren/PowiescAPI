package pl.powiescdosukcesu.book;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserService;

@Service("bookService")
@Transactional
@Log4j2
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRep;

	private final AppUserService appUserService;

	private final GenreRepository genreRep;

	@Override
	public List<Book> getFiles() {

		Iterable<Book> iterableFiles = bookRep.findAll();

		List<Book> files = new ArrayList<>();

		iterableFiles.forEach(f -> files.add(f));

		return files;

	}

	@Override
	public Book getFileById(long id) {

		Optional<Book> optionalBook = bookRep.findById(id);
		if (optionalBook.isPresent()) {
			Book file = optionalBook.get();
			file.getComments();
			return file;
		} else {
			throw new BookNotFoundException();

		}

	}

	@Override
	public List<Book> getFilesByKeyword(String keyword) {

		List<Book> books = bookRep.findFilesByKeyword(keyword);
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new BookNotFoundException();

		}
	}

	@Override
	public List<Book> getFilesByGenres(String[] genres) {

		List<Book> books = bookRep.findByGenres(genres);
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new BookNotFoundException();

		}
	}

	@Override
	public void saveFile(MultipartFile file, String title, String[] genres, byte[] image, String username)
			throws IOException {

		AppUser user = appUserService.getUser(username);

		Set<Genre> genresList = new HashSet<>();

		Base64 base = new Base64();

		image = base.decode(image);

		for (String name : genres)
			genresList.add(genreRep.findGenreByName(name));

		Book book = Book.builder().title(title).backgroundImage(image).genres(genresList).file(file.getBytes())
				.user(user).build();
		book = new Book(title, image, genresList, file.getBytes());

		user.addFile(book);

		bookRep.save(book);
	}

	@Override
	@Async
	public void deleteBook(Book file) {
		Optional<Book> optionalBook = bookRep.findById(file.getId());
		if (optionalBook.isPresent()) {
			bookRep.delete(file);
		} else {
			throw new BookNotFoundException();
		}

	}

	@Override
	@Async
	public void deleteBookById(long id) {
		Optional<Book> optionalBook = bookRep.findById(id);
		if (optionalBook.isPresent()) {
			bookRep.deleteById(id);
		} else {
			throw new BookNotFoundException();
		}
	}

	@Override
	public void updateFile(Book file) {

		file.setUser(getFileById(file.getId()).getUser());
		bookRep.updateBook(file);

	}

	@Override
	public List<String> loadImages() {

		List<byte[]> images = bookRep.loadImages();
		List<byte[]> encodedImages = (List<byte[]>) images.stream().map(image -> {
			Base64 base = new Base64();
			return base.encode(image);
		}).collect(Collectors.toList());

		List<String> stringImages = new LinkedList<>();
		encodedImages.forEach(enc -> {
			try {
				stringImages.add(new String(enc, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.debug(e.getMessage());
			}
		});

		return stringImages;
	}

	@Override
	public List<Book> getFilesByDate(String dateName) {
		
		LocalDate date =LocalDate.parse(dateName);
		List<Book> books = bookRep.findByCreatedDate(date);
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new BookNotFoundException();

		}
	}

	@Override
	public void addComment(Book file, String content) {

		Comment comment = new Comment(content, file.getUser());
		file.addComment(comment);
		updateFile(file);

	}

}
