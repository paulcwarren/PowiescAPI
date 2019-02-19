package pl.powiescdosukcesu.book;

import lombok.Data;

import java.util.List;

@Data
public class BookCreationDTO {

    private String title;
    private String description;
    private List<String> genres;
    private String file;
}
