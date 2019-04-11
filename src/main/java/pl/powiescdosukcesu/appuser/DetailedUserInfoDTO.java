package pl.powiescdosukcesu.appuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedUserInfoDTO {

		private String username;
		private String email;
		private String firstName;
		private String lastName;
		private String gender;

		
}
