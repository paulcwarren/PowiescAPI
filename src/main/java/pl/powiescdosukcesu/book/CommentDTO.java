package pl.powiescdosukcesu.book;

import lombok.Data;
import pl.powiescdosukcesu.appuser.AppUserShortInfoDTO;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    public String content;
    public AppUserShortInfoDTO user;
    public LocalDateTime creationDate;
}
