package pl.powiescdosukcesu.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "genres")
@Data
public class Genre implements Comparable<Genre> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	public Genre() {
		
	}
	public Genre(long id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public int compareTo(Genre o) {
		if (this.id > o.id)
			return 1;
		else if (this.id < o.id)
			return -1;
		else
			return 0;
	}

}
