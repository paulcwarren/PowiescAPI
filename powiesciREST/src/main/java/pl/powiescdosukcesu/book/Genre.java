package pl.powiescdosukcesu.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "genres")
@Data
public class Genre implements Comparable<Genre> {

	@Column(name = "name")
	private String name;

	public Genre() {

	}

	public Genre(String name) {

		this.name = name;
	}

	@Override
	public int compareTo(Genre otherGenre) {
		
		return this.name.compareTo(otherGenre.getName());
	}

}
