package pl.powiescdosukcesu.book.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.content.commons.annotations.Content;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;
import pl.powiescdosukcesu.book.Book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Content
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookContentFile {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Book book;

    @ContentId
    private String contentId;

    @ContentLength
    private Long contentLength;

    @MimeType
    private String mimeType = "text/plain";
}
