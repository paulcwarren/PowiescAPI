package pl.powiescdosukcesu.book;

import lombok.Data;

@Data
public class AddCommentDTO {

    public String comment;
    public long bookId;
}
