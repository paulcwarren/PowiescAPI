package pl.powiescdosukcesu.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class FileEntShortInfoDTO {

	private String title;
	private byte[] file;
	private byte[] backgroundImage;
	private String user;
	private String[] genres;
	private LocalDate createdDate;

}
