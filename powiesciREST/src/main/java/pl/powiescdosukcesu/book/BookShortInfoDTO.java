package pl.powiescdosukcesu.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BookShortInfoDTO {

    public long id;
    public String title;
    public String username;
    public List<String> genres;
    public String description;
    public double rating;
    public LocalDate createdDate;

}
