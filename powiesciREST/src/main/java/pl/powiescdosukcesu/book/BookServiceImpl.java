package pl.powiescdosukcesu.book;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pl.powiescdosukcesu.appuser.AppUser;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository fileRep;

	private Logger LOGGER = Logger.getLogger(getClass().getName());

	@Override
	public List<Book> getFiles() {

		long start = System.currentTimeMillis();
		Iterable<Book> iterableFiles = fileRep.findAll();

		LOGGER.info("Fetching time----->" + (System.currentTimeMillis() - start));
		List<Book> files = new ArrayList<>();

		iterableFiles.forEach(f -> files.add(f));

		return files;

	}

	@Override
	public Book getFileById(long id) {

		Optional<Book> optionalEntity = fileRep.findById(id);
		
		Book file = optionalEntity.get();
		file.getComments();
		return file;
	}

	@Override
	public List<Book> getFilesByKeyword(String keyword) {

		return fileRep.findFilesByKeyword(keyword);
	}
	
	@Override
	public List<Book> getFilesByGenres(String[] genres) {
		
		return fileRep.findByGenres(genres);
	}

	

	@Override
	public void saveFile(Book file, AppUser user) {

		file.setUser(user);
		user.addFile(file);
		
		fileRep.save(file);
	}

	@Override
	@Async
	public void deleteBook(Book file) {

		fileRep.delete(file);

	}
	
	@Override
	@Async
	public void deleteFileById(long id) {
		
		fileRep.deleteById(id);
	}

	@Override
	public void updateFile(Book file) {
		
		file.setUser(getFileById(file.getId()).getUser());
		fileRep.updateFile(file);
		
	}

	@Override
	public List<String> loadImages() {
		
		
		List<byte[]> images=fileRep.loadImages();
		List<byte[]> encodedImages = (List<byte[]>)images.stream().map(image -> {
			Base64 base = new Base64();
			return base.encode(image);
		}).collect(Collectors.toList());

		List<String> stringImages = new LinkedList<>();
		encodedImages.forEach(enc -> {
			try {
				stringImages.add(new String(enc, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});

		return stringImages;
	}

	@Override
	public List<Book> getFilesByDate(LocalDate date) {
		
		return fileRep.findByCreatedDate(date);
	}


}
