package pl.powiescdosukcesu.book;

import java.io.Serializable;

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
public class Genre implements Comparable<Genre>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1125469983999991222L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
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
