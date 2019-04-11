package pl.powiescdosukcesu.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddVoteDTO {

    private double rating;
    private long bookId;
}
