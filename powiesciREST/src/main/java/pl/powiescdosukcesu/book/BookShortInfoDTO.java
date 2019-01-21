package pl.powiescdosukcesu.book;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookShortInfoDTO {

	public String title;
	public byte[] file;
	public byte[] backgroundImage;
	public String username;
	public List<String> genres;
	public LocalDate createdDate;

}
