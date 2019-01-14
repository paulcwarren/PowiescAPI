package pl.powiescdosukcesu.controllers;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.powiescdosukcesu.dtos.FileEntShortInfoDTO;
import pl.powiescdosukcesu.entities.FileEnt;
import pl.powiescdosukcesu.entities.Genre;
import pl.powiescdosukcesu.entities.PowiesciUser;
import pl.powiescdosukcesu.exceptionhandling.FileEntNotFoundException;
import pl.powiescdosukcesu.repositories.GenreRepository;
import pl.powiescdosukcesu.services.FileService;
import pl.powiescdosukcesu.services.UserService;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class FileRestController {

	private Logger LOGGER = Logger.getLogger(getClass().getName());

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@Autowired
	private GenreRepository genreRep;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<FileEntShortInfoDTO> getFiles() {

		List<FileEnt> files = fileService.getFiles();
		return files.stream().map(file->modelMapper.map(file, FileEntShortInfoDTO.class)).collect(Collectors.toList());
	}

	@GetMapping("/id/{fileId}")
	public ResponseEntity<FileEnt> showContent(@PathVariable long fileId) {
		FileEnt file = fileService.getFileById(fileId);
		
		if(file==null)
			throw new FileEntNotFoundException("Dana powieść nie istnieje");
		return new ResponseEntity<>(file,HttpStatus.OK);

	}

	@GetMapping("/{keyword}")
	public ResponseEntity<List<FileEnt>> filterByKeyword(@PathVariable String keyword) {
		List<FileEnt> files = fileService.getFilesByKeyword(keyword);
		if(files==null)
			throw new FileEntNotFoundException("Dana powieść nie istnieje");
		
		return new ResponseEntity<>(files,HttpStatus.OK);
	}
	
	@GetMapping("/byGenre")
	public List<FileEnt> filterByGenres(@RequestParam("genres") String[] genres){
		
		return fileService.getFilesByGenres(genres);
	}
	
	@GetMapping("/byDate")
	public List<FileEnt> filterByGenre(@RequestParam("creationDate") LocalDate creationDate){
		
		return fileService.getFilesByDate(creationDate);
	}

	@DeleteMapping
	@PreAuthorize("#file.user.userName == #principal.getName()")
	public ResponseEntity<String> deleteBook(@RequestBody FileEnt file,Principal principal) {

		if(fileService.getFileById(file.getId())==null)
			throw new FileEntNotFoundException("Dana powieść nie istnieje");
		
		fileService.deleteBook(file);
		
		return new ResponseEntity<String>("Udało się usunąć plik",HttpStatus.OK);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("#file.user.userName == #principal.getName()")
	public ResponseEntity<List<String>>updateBook(@Valid@RequestBody FileEnt file,Principal principal,Errors errors) {

		List<String> errorMessages = new ArrayList<>();
		errors.getAllErrors().forEach(e->errorMessages.add(e.getDefaultMessage()));;
		if(errors.hasErrors()) {
			errorMessages.forEach(e->LOGGER.info(e));
			return new ResponseEntity<List<String>>(errorMessages,HttpStatus.BAD_REQUEST);
		}
		
		fileService.updateFile(file);

		return new ResponseEntity<>(Arrays.asList("Plik został zaktualniony"),HttpStatus.OK);

	}

	
	@PostMapping
	public void processFile(@RequestParam("file") MultipartFile file,
							@RequestParam("title") String title,
							@RequestParam("genres") String[] genres,
							@RequestParam("backGroundImage") byte[] image,
							Principal principal) {

		PowiesciUser user = userService.getUser(principal.getName());
		FileEnt fileEnt = null;
		Set<Genre> genresList = new TreeSet<>();

		Base64 base = new Base64();

		image = base.decode(image);

		for (String name : genres)
			genresList.add(genreRep.findGenreByName(name));

		try {
			fileEnt = new FileEnt(title, image, genresList, file.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

		fileService.saveFile(fileEnt, user);

	}

}
