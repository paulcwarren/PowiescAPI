package pl.powiescdosukcesu.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.powiescdosukcesu.dtos.RegisterUserDTO;
import pl.powiescdosukcesu.entities.PowiesciUser;
import pl.powiescdosukcesu.services.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping("{name}")
	public PowiesciUser getUser(@PathVariable String name) {

		return userService.getUser(name);
	}

	@PostMapping
	public void saveUser(@Valid @RequestBody RegisterUserDTO user, BindingResult bindingResult) {
		
		userService.saveUser(user);
	}

	@GetMapping("/login")
	public ResponseEntity<String> isAuthenticated(Principal principal) {

		PowiesciUser user = userService.getUser(principal.getName());
		if (user != null)
			return new ResponseEntity<String>(user.getUserName(), HttpStatus.OK);
		else
			return new ResponseEntity<String>("failure", HttpStatus.UNAUTHORIZED);
	}
}
