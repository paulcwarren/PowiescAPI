package pl.powiescdosukcesu.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.book.Book;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "books_votes")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private double rating;

    @CreatedDate
    @Column(name = "added_date")
    private LocalDateTime addedDate;

    @LastModifiedDate
    @Column(name = "changed_date")
    private LocalDateTime changedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
