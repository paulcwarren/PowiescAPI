package pl.powiescdosukcesu.book;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import pl.powiescdosukcesu.appuser.AppUser;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
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
	@JsonIgnoreProperties(value = "comments")
	private AppUser user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	@JsonIgnoreProperties(value = "comments")
	private Book file;

	public Comment() {

	}

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
