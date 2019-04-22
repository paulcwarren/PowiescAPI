package pl.powiescdosukcesu.appuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.powiescdosukcesu.validation.annotations.NotUsed;
import pl.powiescdosukcesu.validation.annotations.PasswordMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@PasswordMatch(message = "Passwords don't match")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDTO {

    @JsonProperty(value = "username",required = true)
    @NotUsed(message = "Username already in use")
    @NotBlank(message = "Field is mandatory")
    @Size(min = 4,max = 30)
    private String username;

    @JsonProperty(value = "password",required = true)
    @NotBlank(message = "Field is mandatory")
    @Size(min = 8, max = 40, message = "Password too short")
    private String password;

    @JsonProperty(value = "matchingPassword",required = true)
    @NotBlank(message = "Field is mandatory")
    @Size(min = 8, max = 40)
    private String matchingPassword;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "lastName")
    private String lastName;

    @JsonProperty(value = "sex", required = true)
    @NotBlank(message = "Field is mandatory")
    private String sex;

    @JsonProperty(value = "email",required = true)
    @NotBlank(message = "Field is mandatory")
    @Email(message = "Invalid E-Mail")
    private String email;
}
