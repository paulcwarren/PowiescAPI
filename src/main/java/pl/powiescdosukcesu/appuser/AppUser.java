package pl.powiescdosukcesu.appuser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.roles.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"password", "roles", "books"})
public class AppUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4622874384853098221L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @NotNull
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotNull
    private String username;

    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JoinColumn(name = "image_id")
    @OneToOne(cascade = CascadeType.ALL)
    private AppUserImage userImage;

    @Column(name = "sex")
    @NotNull
    private String sex;

    @Column(name = "description")
    @Size(max = 100)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Book> books;

    public AppUser(String username, String password, String firstName, String lastName, String email, String sex) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
    }

    public void addBook(Book book) {

        if (books == null)
            books = new HashSet<>();
        book.setUser(this);
        this.getBooks().add(book);
    }

}
