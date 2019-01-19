package pl.powiescdosukcesu.appuser;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

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
			return new ResponseEntity<String>(user.getUserName(), HttpStatus.OK);
		else
			return new ResponseEntity<String>("failure", HttpStatus.UNAUTHORIZED);
	}
}
