package pl.powiescdosukcesu.book;

import lombok.Data;
import pl.powiescdosukcesu.appuser.DetailedUserInfoDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class FullBookInfoDTO {

    public long id;
    public String title;
    public List<String> genres;
    public LocalDate createdDate;
    public DetailedUserInfoDTO user;
    public String content;

}
