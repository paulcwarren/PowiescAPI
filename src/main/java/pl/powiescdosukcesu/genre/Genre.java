package pl.powiescdosukcesu.book;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "genres")
@Data
public class Genre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1125469983999991222L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @Column(name = "name",
			unique = true)
	private String name;

	public Genre() {

	}

	public Genre(String name) {

		this.name = name;
	}



}
