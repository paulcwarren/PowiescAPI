package pl.powiescdosukcesu.genre;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
public class Genre implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1125469983999991222L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(name = "name", unique = true)
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
