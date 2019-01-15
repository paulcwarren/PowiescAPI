package pl.powiescdosukcesu.book;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class BookShortInfoDTO {

	private String title;
	private byte[] file;
	private byte[] backgroundImage;
	private String user;
	private List<Genre> genres;
	private LocalDate createdDate;

}
