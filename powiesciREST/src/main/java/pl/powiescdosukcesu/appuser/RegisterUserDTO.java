package pl.powiescdosukcesu.appuser;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import pl.powiescdosukcesu.validation.annotations.NotUsed;

//@PasswordMatch
public class RegisterUserDTO {

	
	@NotBlank(message="*Pole jest wymagane")
	@NotUsed
	private String userName;

	@NotBlank
	@Size(min=8,message="*Hasło jest za krótkie")
	private String password;

	@NotBlank
	@Size(min=8)
	private String matchingPassword;

	private String image;
	
	private String firstName;

	private String lastName;
	
	@NotBlank
	private String gender;

	@NotBlank
	@Email(message="*Niepoprawny adres E-Mail")
	private String email;
	

	public RegisterUserDTO() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


}
