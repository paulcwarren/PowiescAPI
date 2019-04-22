package pl.powiescdosukcesu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.repositories.AppUserRepository;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.files.BookContentFile;
import pl.powiescdosukcesu.book.files.BookContentFileStore;
import pl.powiescdosukcesu.book.repositories.BookRepository;
import pl.powiescdosukcesu.genre.Genre;
import pl.powiescdosukcesu.genre.GenreRepository;
import pl.powiescdosukcesu.roles.Role;
import pl.powiescdosukcesu.roles.RoleRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@SpringBootApplication
@EnableWebMvc
public class PowiesciRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowiesciRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository repository,
                                  BookContentFileStore store,
                                  GenreRepository genreRepository,
                                  AppUserRepository appUserRepository,
                                  RoleRepository roleRepository,
                                  BCryptPasswordEncoder encoder) {
        return (args) -> {

            Role role = new Role("ROLE_NORMAL_USER");
            AppUser user = AppUser.builder()
                    .username("Jack")
                    .password(encoder.encode("haslo"))
                    .roles(Set.of(role))
                    .sex("MALE")
                    .email("didi@sf.ko")
                    .build();



            Genre genre = new Genre("Fantasy");
            genreRepository.save(genre);

            Book book = Book.builder()
                    .title("hermione")
                    .genres(Set.of(genre))
                    .description("hi there")
                    .build();

            book.setUser(user);
            user.addBook(book);

            appUserRepository.save(user);
            roleRepository.save(role);

            BookContentFile file = new BookContentFile();
            store.setContent(file, Files.newInputStream(Paths.get("/home/robertdev/Documents/day.csv")));
            book.setFile(file);

            repository.save(book);
        };
    }
}
