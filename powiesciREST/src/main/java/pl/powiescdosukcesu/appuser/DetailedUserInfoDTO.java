package pl.powiescdosukcesu.appuser;

import lombok.Data;
import pl.powiescdosukcesu.book.BookShortInfoDTO;

@Data
public class DetailedUserInfoDTO {

		private String username;
		private String email;
		private String firstName;
		private String lastName;
		private String gender;
		private BookShortInfoDTO[] files;
		
}
