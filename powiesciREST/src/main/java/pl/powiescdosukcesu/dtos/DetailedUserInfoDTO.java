package pl.powiescdosukcesu.dtos;

import lombok.Data;

@Data
public class DetailedUserInfoDTO {

		private String username;
		private String email;
		private String firstName;
		private String lastName;
		private String gender;
		private FileEntShortInfoDTO[] files;
		
}
