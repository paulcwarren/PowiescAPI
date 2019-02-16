package pl.powiescdosukcesu.appuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import pl.powiescdosukcesu.validation.annotations.NotUsed;
import pl.powiescdosukcesu.validation.annotations.PasswordMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@PasswordMatch(message = "Hasła się nie zgadzają")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDTO {


    @JsonProperty(value = "username",required = true)
    @NotBlank(message = "*Pole jest wymagane")
    @NotUsed(message = "*Nazwa użytkownika jest zajęta")
    @Size(min = 4,max = 30)
    private String username;

    @JsonProperty(value = "password",required = true)
    @NotBlank
    @Size(min = 8, message = "*Hasło jest za krótkie")
    private String password;

    @JsonProperty(value = "matchingPassword",required = true)
    @NotBlank
    @Size(min = 8)
    private String matchingPassword;

    private String image;

    private String firstName;

    private String lastName;

    @JsonProperty(value = "gender",required = true)
    @NotBlank
    private String gender;

    @JsonProperty(value = "email",required = true)
    @NotBlank
    @Email(message = "*Niepoprawny adres E-Mail")
    private String email;


}
