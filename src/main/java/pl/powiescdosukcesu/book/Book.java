package pl.powiescdosukcesu.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.book.files.BackgroundImage;
import pl.powiescdosukcesu.book.files.BookContentFile;
import pl.powiescdosukcesu.comments.Comment;
import pl.powiescdosukcesu.genre.Genre;
import pl.powiescdosukcesu.vote.Vote;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString(exclude = {"comments", "genres","user"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    @NotBlank
    @Size(min = 4, max = 50)
    private String title;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy/MM/dd ")
    private LocalDate createdDate;

    @Column(name ="last_modified_date")
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy/MM/dd ")
    private LocalDate lastModifiedDate;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "description")
    @Size(max = 100)
    private String description;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "books_genres", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Genre> genres;

    @OneToMany(
            mappedBy = "book",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments;

    @OneToMany(
            mappedBy = "book",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vote> votes;

    @JoinColumn(name="image_id")
    @OneToOne(cascade = CascadeType.ALL)
    private BackgroundImage backgroundImage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private BookContentFile file;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private AppUser user;

    public Book(String title, Set<Genre> genres) {

        this.title = title;
        this.genres = genres;
    }

    void addComment(Comment comment) {

        if (this.comments == null)
            comments = new ArrayList<>();
        comment.setBook(this);
        this.comments.add(comment);
    }

    void addVote(Vote vote) {

        if (this.votes == null)
            votes = new ArrayList<>();
        vote.setBook(this);
        this.votes.add(vote);
    }

}