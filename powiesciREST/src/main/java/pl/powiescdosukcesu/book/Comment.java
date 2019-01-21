package pl.powiescdosukcesu.book;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.powiescdosukcesu.appuser.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
public class Comment implements Comparable<Comment> {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column
	private int id;

	@Column(name = "content")
	private String content;

	@Column(name = "creation_date")
	@CreatedDate
	private LocalDateTime creationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private AppUser user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	private Book file;



	public Comment(String content, AppUser user) {

		this.content = content;
		this.user = user;
	}

	@Override
	public int compareTo(Comment com) {

		if (this.creationDate.isAfter(com.getCreationDate()))
			return 1;
		else if (this.creationDate.isBefore(com.getCreationDate()))
			return -1;
		else
			return 0;

	}

}
