package pl.powiescdosukcesu.appuser;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.powiescdosukcesu.validation.annotations.NotUsed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//@PasswordMatch
@Data
@NoArgsConstructor
public class RegisterUserDTO {


    @NotBlank(message = "*Pole jest wymagane")
    @NotUsed
    private String userName;

    @NotBlank
    @Size(min = 8, message = "*Hasło jest za krótkie")
    private String password;

    @NotBlank
    @Size(min = 8)
    private String matchingPassword;

    private String image;

    private String firstName;

    private String lastName;

    @NotBlank
    private String gender;

    @NotBlank
    @Email(message = "*Niepoprawny adres E-Mail")
    private String email;


}
