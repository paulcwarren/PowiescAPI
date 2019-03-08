package pl.powiescdosukcesu.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BookCreationDTO {

    @NotNull
    private String title;

    private String description;

    @NotNull
    private List<String> genres;

    @NotNull
    private String file;

    @JsonProperty(value = "image")
    private String image;
}
