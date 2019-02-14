package pl.powiescdosukcesu.book;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookShortInfoDTO {

    public long id;
    public String title;
    public String username;
    public List<String> genres;
    public LocalDate createdDate;

}
