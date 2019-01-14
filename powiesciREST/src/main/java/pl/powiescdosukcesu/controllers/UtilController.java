package pl.powiescdosukcesu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.powiescdosukcesu.services.FileService;

@RestController
public class UtilController {

	@Autowired
	private FileService fileService;
	
	@GetMapping("/images")
	public List<String> loadImages() {
		
		return fileService.loadImages();
	}
	
}
