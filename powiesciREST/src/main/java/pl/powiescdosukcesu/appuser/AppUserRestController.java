package pl.powiescdosukcesu.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserRestController {

	private final AppUserService appUserService;

	@GetMapping("{name}")
	public AppUser getUser(@PathVariable String name) {

		return appUserService.getUser(name);
	}

	@PostMapping
	public void saveUser(@Valid @RequestBody RegisterUserDTO user, BindingResult bindingResult) {

		appUserService.saveUser(user);
	}

	@GetMapping("/login")
	public ResponseEntity<String> isAuthenticated(Principal principal) {

		AppUser user = appUserService.getUser(principal.getName());
		if (user != null)
			return new ResponseEntity<>(user.getUserName(), HttpStatus.OK);
		else
			return new ResponseEntity<>("failure", HttpStatus.UNAUTHORIZED);
	}
}
