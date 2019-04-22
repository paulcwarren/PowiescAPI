package pl.powiescdosukcesu.appuser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;
import pl.powiescdosukcesu.appuser.services.AppUserService;

import java.io.IOException;

@RepositoryRestController
public class UserAuthAndRegistrationController {

    private final AppUserService appUserService;

    @Autowired
    public UserAuthAndRegistrationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/users")
    public AppUser registerUser(@RequestPart("user") RegisterUserDTO dto,
                                @RequestPart("image") MultipartFile image) throws IOException {

        return appUserService.registerUser(dto, image);
    }
}
