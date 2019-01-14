package pl.powiescdosukcesu.services;

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

import pl.powiescdosukcesu.entities.FileEnt;
import pl.powiescdosukcesu.entities.PowiesciUser;
import pl.powiescdosukcesu.repositories.FileRepository;

@Service
@Transactional
public class FileServiceImpl implements FileService {

	@Autowired
	private FileRepository fileRep;

	private Logger LOGGER = Logger.getLogger(getClass().getName());

	@Override
	public List<FileEnt> getFiles() {

		long start = System.currentTimeMillis();
		Iterable<FileEnt> iterableFiles = fileRep.findAll();

		LOGGER.info("Fetching time----->" + (System.currentTimeMillis() - start));
		List<FileEnt> files = new ArrayList<>();

		iterableFiles.forEach(f -> files.add(f));

		return files;

	}

	@Override
	public FileEnt getFileById(long id) {

		Optional<FileEnt> optionalEntity = fileRep.findById(id);
		
		FileEnt file = optionalEntity.get();
		file.getComments();
		return file;
	}

	@Override
	public List<FileEnt> getFilesByKeyword(String keyword) {

		return fileRep.findFilesByKeyword(keyword);
	}
	
	@Override
	public List<FileEnt> getFilesByGenres(String[] genres) {
		
		return fileRep.findByGenres(genres);
	}

	

	@Override
	public void saveFile(FileEnt file, PowiesciUser user) {

		file.setUser(user);
		user.addFile(file);
		
		fileRep.save(file);
	}

	@Override
	@Async
	public void deleteBook(FileEnt file) {

		fileRep.delete(file);

	}
	
	@Override
	@Async
	public void deleteFileById(long id) {
		
		fileRep.deleteById(id);
	}

	@Override
	public void updateFile(FileEnt file) {
		
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
	public List<FileEnt> getFilesByDate(LocalDate date) {
		
		return fileRep.findByCreatedDate(date);
	}


}
