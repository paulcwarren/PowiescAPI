package pl.powiescdosukcesu.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserRestController {

	private final AppUserService appUserService;

	@GetMapping("{name}")
	public ResponseEntity<AppUser> getUser(@PathVariable String name) {

		return new ResponseEntity<>(appUserService.getUser(name),HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<List<String>> saveUser(@Valid @RequestBody RegisterUserDTO user) {

		appUserService.saveUser(user);
		return new ResponseEntity<>(Collections.singletonList("Udana rejestracja"),HttpStatus.OK);
	}

	@GetMapping(value = "/{username}/image",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getUserImage(@PathVariable String username){

		return appUserService.getUser(username).getImage();
	}




}
