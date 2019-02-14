package pl.powiescdosukcesu.appuser;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserRestController {

	private final AppUserService appUserService;

	private final ModelMapper modelMapper;

	@GetMapping("{name}")
	public ResponseEntity<AppUser> getUser(@PathVariable String name) {

		return new ResponseEntity<>(appUserService.getUser(name),HttpStatus.OK);
	}

	//TODO
	@PostMapping("/register")
	public ResponseEntity<String> saveUser(@Valid @RequestBody RegisterUserDTO user, BindingResult bindingResult) {

		if(bindingResult.hasErrors())
			return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
		appUserService.saveUser(user);
		return new ResponseEntity<>("Successfull registration",HttpStatus.OK);
	}

	@GetMapping(value = "/{username}/image",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getUserImage(@PathVariable String username){

		return appUserService.getUser(username).getImage();
	}




}
