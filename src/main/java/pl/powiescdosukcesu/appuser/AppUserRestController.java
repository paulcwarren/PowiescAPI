package pl.powiescdosukcesu.appuser;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.powiescdosukcesu.security.JwtAuthenticationResponse;
import pl.powiescdosukcesu.security.JwtTokenProvider;
import pl.powiescdosukcesu.security.UserPrincipal;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserRestController {

	private final AppUserService appUserService;

    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;

    @GetMapping("/{name}")
	public ResponseEntity<AppUser> getUser(@PathVariable String name) {

		return new ResponseEntity<>(appUserService.getUser(name),HttpStatus.OK);
	}

	@PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
	public void saveUser(@Valid @RequestBody RegisterUserDTO user) {

		appUserService.saveUser(user);
	}

	@GetMapping(value = "/{username}/image",
                produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getUserImage(@PathVariable String username){

		return appUserService.getUser(username).getImage();
	}

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/user/me")
    public ResponseEntity<DetailedUserInfoDTO> getMyProfile(@AuthenticationPrincipal UserPrincipal currentUser) {

        DetailedUserInfoDTO dto = modelMapper.map(currentUser,DetailedUserInfoDTO.class);

        return ResponseEntity.ok(dto);

    }


}
