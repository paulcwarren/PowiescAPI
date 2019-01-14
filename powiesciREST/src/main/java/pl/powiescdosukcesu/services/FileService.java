package pl.powiescdosukcesu.services;

import java.time.LocalDate;
import java.util.List;

import pl.powiescdosukcesu.entities.FileEnt;
import pl.powiescdosukcesu.entities.PowiesciUser;

public interface FileService {

	void saveFile(FileEnt file,PowiesciUser user);
	FileEnt getFileById(long id);
	void deleteBook(FileEnt file);
	List<FileEnt> getFilesByKeyword(String keyword);
	List<FileEnt> getFiles();
	List<FileEnt> getFilesByGenres(String[] genres);
	List<FileEnt> getFilesByDate(LocalDate date);
	void updateFile(FileEnt file);
	List<String> loadImages();
	void deleteFileById(long id);
}
