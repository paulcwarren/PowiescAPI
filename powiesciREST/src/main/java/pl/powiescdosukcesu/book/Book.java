package pl.powiescdosukcesu.book;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.powiescdosukcesu.appuser.AppUser;

@Entity
@Table(name = "files")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@Builder
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6235830792388288474L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;

	@Column(name = "title")
	@NotBlank
	@Size(min = 4)
	private String title;

	@Column(name = "image")
	private byte[] backgroundImage;

	@Column(name = "creation_date", updatable = false)
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate createdDate;

	@Column(name = "rating")
	private double rating;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "files_genres",
				joinColumns = @JoinColumn(name = "file_id"),
				inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genre> genres;

	@OneToMany(mappedBy = "file", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Comment> comments;

	@Column(name = "file", updatable = true)
	@NotNull
	private byte[] file;

	@ManyToOne(fetch = FetchType.EAGER,
			   cascade = {
					   CascadeType.DETACH,
					   CascadeType.MERGE,
					   CascadeType.PERSIST,
			           CascadeType.REFRESH 
			           })
	@JoinColumn(name = "user_id")
	private AppUser user;

	public Book() {
		
	}

	public Book(String title, byte[] image, Set<Genre> genres, byte[] file) {

		this.title = title;
		this.backgroundImage = image;
		this.genres = genres;
		this.file = file;

	}

	public void addComment(Comment comment) {

		if (this.comments == null)
			comments = new HashSet<>();
		comment.setFile(this);
		this.comments.add(comment);
	}

	public String getBase64Image() {
		Base64 base = new Base64();
		backgroundImage = base.encode(backgroundImage);

		try {
			return new String(backgroundImage, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "Error";
	}

	public String getContent() {
		String content = new String(file);
		return content;
	}

}