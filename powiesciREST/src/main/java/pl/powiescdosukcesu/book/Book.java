package pl.powiescdosukcesu.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import pl.powiescdosukcesu.appuser.AppUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"comments","genres"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6235830792388288474L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;

	@Column(name = "title", nullable = false, unique = true)
	@NotBlank
	@Size(min = 4, max = 50)
	private String title;

	@Column(name = "image")
	private byte[] backgroundImage;

	@Column(name = "created_date", updatable = false)
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy/MM/dd ")
	private LocalDate createdDate;

	@Column(name = "rating")
	private double rating;

	@ManyToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	@JoinTable(name = "books_genres",
			joinColumns = @JoinColumn(name = "book_id"),
				inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@OnDelete(action = OnDeleteAction.CASCADE)
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

    @Column(name = "description")
	@Size(max = 100)
    private String description;

	@Column(name = "file")
	@NotNull
	private byte[] file;

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

	public Book(String title, byte[] image, Set<Genre> genres, byte[] file) {

		this.title = title;
		this.backgroundImage = image;
		this.genres = genres;
		this.file = file;

	}

	public void addComment(Comment comment) {

		if (this.comments == null)
			comments = new ArrayList<>();
        comment.setBook(this);
		this.comments.add(comment);
	}

	public void addVote(Vote vote) {

		if (this.votes == null)
			votes = new ArrayList<>();
		vote.setBook(this);
		this.votes.add(vote);
	}

	public String getContent(){
		return new String(this.getFile());
	}

}