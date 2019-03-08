package pl.powiescdosukcesu.book;

import lombok.Data;

@Data
public class AddVoteDTO {

    private double rating;
    private long bookId;
}
